package rnp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Collection;
import java.util.LinkedList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * Fornisce l'accesso ai dati di un oggetto Bean in una base di dati relazionale attraverso un pool di connessioni DataSource. 
 * La classe si occupa di eseguire le operazioni CRUD (create, retrieve, update e delete) sui dati nella tabella "TABLE_NAME" della base di dati.
 * @category Query con Data Source
 * @implNote ATTENZIONE: Modificare web.xml (resource-ref con JNDI) e modificare context.xml (in META-INF)
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
	 * Un bean viene inserito come una nuova riga nella tabella "TABLE_NAME" usando una connessione al database.
	 * @param user Oggetto da inserire
	 * @category MODIFICABILE
	 */
	@Override
	public synchronized void doSave(UserBean user) throws SQLException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		PreparedStatement stmt = null;

		// MODIFICABILE
		String insertSQL = "INSERT INTO " + UserDAODataSource.TABLE_NAME
                + " (name, email, password, address, city, cap, phone) VALUES (?, ?, ?, ?, ?, ?, ?)";


		try { 
			connection = ds.getConnection();
			//Query per verificare se esiste già un user con la stessa mail
			String query = "SELECT COUNT(*) FROM " + UserDAODataSource.TABLE_NAME+ " WHERE email = ?";
            stmt = connection.prepareStatement(query);
            stmt.setString(1, user.getEmail());
            ResultSet rs = stmt.executeQuery();

            // Analizza il risultato della query
            if (rs.next()) {
                int count = rs.getInt(1);
                if (count > 0) {	//Controlla se esistono altre mail uguali
                   throw new SQLIntegrityConstraintViolationException();
                } else {	//Nel caso non sia presente nessuna mail uguale a quella da inserire
                	preparedStatement = connection.prepareStatement(insertSQL);

        			// MODIFICABILE
        			preparedStatement.setString(1, user.getName());
        			preparedStatement.setString(2, user.getEmail());
        			preparedStatement.setString(3, user.getPassword());
        			preparedStatement.setString(4, user.getAddress());
        			preparedStatement.setString(5, user.getCity());
        			preparedStatement.setString(6, user.getCap());
        			preparedStatement.setString(7, user.getPhone());

        			preparedStatement.executeUpdate();

        			connection.commit(); // TODO: Vedere se disabilitare autocommit=true

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
	}

	/**
	 * Rimuove una riga dalla tabella "TABLE_NAME" in base al codice.
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
			preparedStatement = connection.prepareStatement(deleteSQL);
			
			preparedStatement.setInt(1, id); // MODIFICABILE

			result = preparedStatement.executeUpdate();

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
	 * Seleziona tutte le righe dalla tabella "TABLE_NAME" e restituisce una collezione di oggetti.
	 * @param order Specifica l'ordine di ordinamento dei risultati (se non è nullo aggiunge ORDER BY alla query).
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

