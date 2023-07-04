package rnp.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.Collection;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import rnp.Bean.UserBean;

/**
 * Fornisce l'accesso ai dati di un oggetto Bean in una base di dati relazionale
 * attraverso un pool di connessioni DataSource. La classe si occupa di eseguire
 * le operazioni CRUD (create, retrieve, update e delete) sui dati nella tabella
 * {@link #TABLE_NAME} della base di dati.
 * 
 * @implNote In {@code WEB-INF\web.xml} è stato aggiunto un tag resource-ref con
 *           JNDI.<br>
 *           In {@code META-INF\context.xml} sono stati aggiunti username,
 *           password e altri dati inerenti al DB.<br>
 *           Queste modifiche permettono di poter utilizzare tutti i DAO (serve
 *           farlo solo una volta).
 */
public class UserDAODataSource implements MethodsDAO<UserBean> {

	private static DataSource dataSource;
	private static final String TABLE_NAME = "users";
	
	private static final String CLASS_NAME = UserDAODataSource.class.getName();
	private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);

	// Inizializzazione per il Data Source
	static {
		try {
			Context initialContext = new InitialContext();
			Context environmentContext = (Context) initialContext.lookup("java:comp/env");

			dataSource = (DataSource) environmentContext.lookup("jdbc/renewphonedb");

		} catch (NamingException e) {
			LOGGER.log(Level.SEVERE, "ERROR [" + CLASS_NAME + "]: " + e.getMessage());
		}
	}

	/**
	 * Un bean viene inserito come una nuova riga nella tabella {@link #TABLE_NAME} usando
	 * una connessione al database.
	 * 
	 * @category INSERT
	 */
	@Override
	public synchronized int doSave(UserBean user) throws SQLException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		PreparedStatement stmt = null;

		
		String insertSQL = "INSERT INTO " + UserDAODataSource.TABLE_NAME
				+ " (name, surname, email, password, address, city, cap, phone) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		int generatedId = -1;

		try {
			connection = dataSource.getConnection();
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
							LOGGER.log(Level.SEVERE, "ERROR [" + CLASS_NAME + "]: No ID obtained in doSave.");
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
	 * @category DELETE
	 */
	@Override
	public synchronized boolean doDelete(int id) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		int result = 0;

		String deleteSQL = "DELETE FROM " + UserDAODataSource.TABLE_NAME + " WHERE id = ?"; 

		try {
			connection = dataSource.getConnection();
			connection.setAutoCommit(false);

			// Verifica se ci sono dipendenze nelle tabelle correlate
			boolean hasDependencies = checkDependencies(connection, id);

			if (hasDependencies) {
				// Elimina le dipendenze nelle tabelle correlate
				deleteDependencies(connection, id);
			}
			
			preparedStatement = connection.prepareStatement(deleteSQL);
			preparedStatement.setInt(1, id); 
			result = preparedStatement.executeUpdate();

			connection.commit();
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "ERROR [" + CLASS_NAME + "]: " + e.getMessage());

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
	 * Seleziona tutte le righe dalla tabella {@link #TABLE_NAME} e restituisce una
	 * collezione di oggetti.
	 * 
	 * @param order Specifica l'ordine di ordinamento dei risultati (se non è nullo
	 *              aggiunge ORDER BY alla query).
	 * @return La collezione di oggetti contenente tutte le righe della tabella.
	 * @category SELECT
	 */
	@Override
	public synchronized Collection<UserBean> doRetrieveAll(String order) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		Collection<UserBean> products = new LinkedList<UserBean>();

		String selectSQL = "SELECT * FROM " + UserDAODataSource.TABLE_NAME;

		if (order != null && !order.equals("")) {
			selectSQL += " ORDER BY ?";
		}

		try {
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			if(order != null && !order.equals(""))
				preparedStatement.setString(1, order);
			
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				UserBean bean = new UserBean();

				
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
	 * Seleziona una singola riga dalla tabella {@link #TABLE_NAME} in base al codice.
	 * 
	 * @param id Il codice dell'utente da ottenere.
	 * @return Il bean ottenuto in base al codice.
	 * @category SELECT
	 */
	@Override
	public synchronized UserBean doRetrieveByKey(int id) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		UserBean bean = new UserBean();

		String selectSQL = "SELECT * FROM " + UserDAODataSource.TABLE_NAME + " WHERE id = ?"; 

		try {
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setInt(1, id); 
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				
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
	
	/**
	 * Controlla se un utente ha correttamente fatto l'accesso al suo account.
	 * @return L'id dell'utente se ha avuto successo, -1 altrimenti
	 * @category SELECT
	 */
	public synchronized int doRetrieveByCredentials(String email, String password) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String selectSQL = "SELECT id FROM " + UserDAODataSource.TABLE_NAME + " WHERE email = ? AND password = ?"; 

		int result = -1;
		
		try {
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, password);
			
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				
				result = rs.getInt("id");
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
		return result;
	}

	// TODO: verifica
	/**
	 * Controlla se ci sono dipendenze in altre tabelle.
	 * @category OTHER
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
			LOGGER.log(Level.SEVERE, "ERROR [" + CLASS_NAME + "]: " + e.getMessage());
		}
	
		return hasDependencies;
	}

	// TODO: verifica
	/**
	 * Elimina le dipendenze in altre tabelle.
	 * @category OTHER
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
			LOGGER.log(Level.SEVERE, "ERROR [" + CLASS_NAME + "]: " + e.getMessage());
		}
	}

}
