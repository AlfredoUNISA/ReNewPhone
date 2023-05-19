package rnp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.Collection;
import java.util.LinkedList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * Fornisce l'accesso ai dati di un oggetto Bean in una base di dati relazionale
 * attraverso un pool di connessioni DataSource. La classe si occupa di eseguire
 * le operazioni CRUD (create, retrieve, update e delete) sui dati nella tabella
 * "TABLE_NAME" della base di dati.
 * 
 * @category Query con Data Source
 * @implNote ATTENZIONE: Modificare web.xml (resource-ref con JNDI) e modificare
 *           context.xml (in META-INF)
 * @category MODIFICABILE
 */
public class UserDAODataSource implements IBeanDAO<UserBean> /* MODIFICABILE */ {

	private static DataSource ds;
	private static final String TABLE_NAME = "users"; // MODIFICABILE

	// Inizializzazione per il Data Source
	static {
		try {
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");

			ds = (DataSource) envCtx.lookup("jdbc/renewphonedb"); // MODIFICABILE

		} catch (NamingException e) {
			System.out.println("Error:" + e.getMessage());
		}
	}

	/**
	 * Un bean viene inserito come una nuova riga nella tabella "TABLE_NAME" usando
	 * una connessione al database.
	 * 
	 * @param user Oggetto da inserire
	 * @return
	 * @category MODIFICABILE
	 */
	@Override
	public synchronized int doSave(UserBean user) throws SQLException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		PreparedStatement stmt = null;

		// MODIFICABILE
		String insertSQL = "INSERT INTO " + UserDAODataSource.TABLE_NAME
				+ " (name, surname, email, password, address, city, cap, phone) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		int generatedId = -1;

		try {
			connection = ds.getConnection();
			// Query per verificare se esiste già un user con la stessa mail
			String query = "SELECT COUNT(*) FROM " + UserDAODataSource.TABLE_NAME + " WHERE email = ?";
			stmt = connection.prepareStatement(query);
			stmt.setString(1, user.getEmail());
			ResultSet rs = stmt.executeQuery();

			// Analizza il risultato della query
			if (rs.next()) {
				int count = rs.getInt(1);
				if (count > 0) { // Controlla se esistono altre mail uguali
					throw new SQLIntegrityConstraintViolationException();
				} else { // Nel caso non sia presente nessuna mail uguale a quella da inserire
					preparedStatement = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);

					// MODIFICABILE
					preparedStatement.setString(1, user.getName());
					preparedStatement.setString(2, user.getSurname());
					preparedStatement.setString(3, user.getEmail());
					preparedStatement.setString(4, user.getPassword());
					preparedStatement.setString(5, user.getAddress());
					preparedStatement.setString(6, user.getCity());
					preparedStatement.setString(7, user.getCap());
					preparedStatement.setString(8, user.getPhone());

					preparedStatement.executeUpdate();

					connection.setAutoCommit(false);
					connection.commit();

					// Ottieni l'id generato
					try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
						if (generatedKeys.next()) {
							generatedId = generatedKeys.getInt(1);
						} else {
							System.out.println("ERROR: No ID obtained in OrderDAODataSource's doSave.");
						}
					}
				}
			}
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				if (connection != null)
					connection.close();
			}
		}
		return generatedId;
	}

	/**
	 * Rimuove una riga dalla tabella "users" in base al codice.
	 * 
	 * @param id Il codice del prodotto da rimuovere.
	 * @return L'esito della query.
	 * @category MODIFICABILE
	 */
	@Override
	public synchronized boolean doDelete(int id /* MODIFICABILE */) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		int result = 0;

		String deleteSQL = "DELETE FROM " + UserDAODataSource.TABLE_NAME + " WHERE id = ?"; // MODIFICABILE

		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);

			// Verifica se ci sono dipendenze nelle tabelle correlate
			boolean hasDependencies = checkDependencies(connection, id);

			if (hasDependencies) {
				// Elimina le dipendenze nelle tabelle correlate
				deleteDependencies(connection, id);
			}
			
			preparedStatement = connection.prepareStatement(deleteSQL);
			preparedStatement.setInt(1, id); // MODIFICABILE
			result = preparedStatement.executeUpdate();

			connection.commit();
		} catch (SQLException e) {
			System.out.println("ERROR: " + e);

			if (connection != null) {
				connection.rollback(); // Annulla la transazione in caso di errore
			}
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				if (connection != null)
					connection.close();
			}
		}
		return (result != 0);
	}

	/**
	 * Controlla se ci sono dipendenze in altre tabelle.
	 */
	private boolean checkDependencies(Connection connection, int userId) throws SQLException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		boolean hasDependencies = false;

		try {
			// Verifica la tabella orders per dipendenze
			String checkOrdersSQL = "SELECT COUNT(*) FROM orders WHERE id_user = ?";
			preparedStatement = connection.prepareStatement(checkOrdersSQL);
			preparedStatement.setInt(1, userId);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				int ordersCount = resultSet.getInt(1);
				if (ordersCount > 0) {
					hasDependencies = true;
				}
			}

			if (!hasDependencies) {
				// Verifica la tabella carts per dipendenze
				String checkCartsSQL = "SELECT COUNT(*) FROM carts WHERE id_user = ?";
				preparedStatement = connection.prepareStatement(checkCartsSQL);
				preparedStatement.setInt(1, userId);
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					int cartsCount = resultSet.getInt(1);
					if (cartsCount > 0) {
						hasDependencies = true;
					}
				}
			}

			if (resultSet != null) {
				resultSet.close();
			}
			if (preparedStatement != null) {
				preparedStatement.close();
			}
		} catch (SQLException e) {
			System.out.println("ERROR: " + e);
		}

		return hasDependencies;
	}

	/**
	 * Elimina le dipendenze in altre tabelle.
	 */
	private void deleteDependencies(Connection connection, int userId) throws SQLException {
		PreparedStatement preparedStatement = null;

		try {
			// TODO: Elimina le righe correlate nella tabella orders

			// Elimina le righe correlate nella tabella carts
			String deleteCartsSQL = "DELETE FROM carts WHERE id_user = ?";
			preparedStatement = connection.prepareStatement(deleteCartsSQL);
			preparedStatement.setInt(1, userId);
			preparedStatement.executeUpdate();

			if (preparedStatement != null) {
				preparedStatement.close();
			}
		} catch (SQLException e) {
			System.out.println("ERROR: " + e);
		}
	}

	/**
	 * Seleziona tutte le righe dalla tabella "TABLE_NAME" e restituisce una
	 * collezione di oggetti.
	 * 
	 * @param order Specifica l'ordine di ordinamento dei risultati (se non è nullo
	 *              aggiunge ORDER BY alla query).
	 * @return La collezione di oggetti contenente tutte le righe della tabella.
	 * @category MODIFICABILE
	 */
	@Override
	public synchronized Collection<UserBean> doRetrieveAll(String order) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		Collection<UserBean> products = new LinkedList<UserBean>();

		String selectSQL = "SELECT * FROM " + UserDAODataSource.TABLE_NAME;

		if (order != null && !order.equals("")) {
			selectSQL += " ORDER BY " + order;
		}

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				UserBean bean = new UserBean();

				// MODIFICABILE
				bean.setId(rs.getInt("id"));
				bean.setName(rs.getString("name"));
				bean.setSurname(rs.getString("surname"));
				bean.setEmail(rs.getString("email"));
				bean.setPassword(rs.getString("password"));
				bean.setAddress(rs.getString("address"));
				bean.setCity(rs.getString("city"));
				bean.setCap(rs.getString("cap"));
				bean.setPhone(rs.getString("phone"));

				products.add(bean);
			}
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				if (connection != null)
					connection.close();
			}
		}
		return products;
	}

	/**
	 * Seleziona una singola riga dalla tabella "TABLE_NAME" in base al codice.
	 * 
	 * @param id Il codice dell'utente da ottenere.
	 * @return Il bean ottenuto in base al codice.
	 * @category MODIFICABILE
	 */
	@Override
	public synchronized UserBean doRetrieveByKey(int id /* MODIFICABILE */) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		UserBean bean = new UserBean();

		String selectSQL = "SELECT * FROM " + UserDAODataSource.TABLE_NAME + " WHERE id = ?"; // MODIFICABILE

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setInt(1, id); // MODIFICABILE
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				// MODIFICABILE
				bean.setId(rs.getInt("id"));
				bean.setName(rs.getString("name"));
				bean.setSurname(rs.getString("surname"));
				bean.setEmail(rs.getString("email"));
				bean.setPassword(rs.getString("password"));
				bean.setAddress(rs.getString("address"));
				bean.setCity(rs.getString("city"));
				bean.setCap(rs.getString("cap"));
				bean.setPhone(rs.getString("phone"));
			}
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				if (connection != null)
					connection.close();
			}
		}
		return bean;
	}

}
