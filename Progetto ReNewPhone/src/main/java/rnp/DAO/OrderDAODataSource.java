package rnp.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Collection;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import rnp.Bean.OrderBean;
import rnp.Servlet.VariousHelper;

/**
 * Fornisce l'accesso ai dati di un oggetto ProductBean in una base di dati
 * relazionale attraverso un pool di connessioni DataSource. La classe si occupa
 * di eseguire le operazioni CRUD (create, retrieve, update e delete) sui dati
 * nella tabella {@link #TABLE_NAME} della base di dati.
 * 
 * @implNote In {@code WEB-INF\web.xml} è stato aggiunto un tag resource-ref con
 *           JNDI.<br>
 *           In {@code META-INF\context.xml} sono stati aggiunti username,
 *           password e altri dati inerenti al DB.<br>
 *           Queste modifiche permettono di poter utilizzare tutti i DAO (serve
 *           farlo solo una volta).
 */
public class OrderDAODataSource implements MethodsDAO<OrderBean>, VariousHelper {

	private static DataSource dataSource;
	private static final String TABLE_NAME = "orders";

	private static final String CLASS_NAME = OrderDAODataSource.class.getName();
	private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);

	// Inizializzazione per il Data Source
	static {
		try {
			Context initialContext = new InitialContext();
			Context environmentContext = (Context) initialContext.lookup("java:comp/env");

			dataSource = (DataSource) environmentContext.lookup("jdbc/renewphonedb");

		} catch (NamingException e) {
			LOGGER.log(Level.SEVERE, ANSI_RED + "ERROR [" + CLASS_NAME + "]: " + e.getMessage() + ANSI_RESET);
		}
	}

	/**
	 * Un bean viene inserito come una nuova riga nella tabella {@link #TABLE_NAME}
	 * usando una connessione al database.
	 * 
	 * @param order Oggetto da inserire
	 * @return L'id generato automaticamente dalla insert
	 * @category INSERT
	 */
	@Override
	public synchronized int doSave(OrderBean order) throws SQLException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String insertSQL = "INSERT INTO " + OrderDAODataSource.TABLE_NAME
				+ " (id_user, total, order_date) VALUES (?, ?, ?)";

		int generatedId = -1;

		try {
			connection = dataSource.getConnection();

			preparedStatement = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);

			preparedStatement.setInt(1, order.getId_user());
			preparedStatement.setInt(2, order.getTotal());

			// Inserisce la data di oggi
			preparedStatement.setDate(3, new Date(Calendar.getInstance().getTime().getTime()));

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
	 * Rimuove una riga dalla tabella {@link #TABLE_NAME} in base al codice.
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

		String deleteSQL = "DELETE FROM " + OrderDAODataSource.TABLE_NAME + " WHERE id = ?";

		try {
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(deleteSQL);

			preparedStatement.setInt(1, id);

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
	 * Seleziona tutte le righe dalla tabella {@link #TABLE_NAME} e restituisce una
	 * collezione di oggetti.
	 * 
	 * @param sort Specifica l'ordine di ordinamento dei risultati (se non è nullo
	 *             aggiunge ORDER BY alla query).
	 * @return La collezione di oggetti contenente tutte le righe della tabella.
	 * @category SELECT
	 */
	@Override
	public synchronized Collection<OrderBean> doRetrieveAll(String sort) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		Collection<OrderBean> orders = new LinkedList<OrderBean>();

		String selectSQL = "SELECT * FROM " + OrderDAODataSource.TABLE_NAME;

		// Qui non è possibile usare prepared statement per la order by
		if (sort != null && !sort.equals("")) {
			// Effettua la validazione del parametro "order" per garantire che sia un valore
			// sicuro e consentito
			String[] allowedColumns = { "id", "id_user", "total", "order_date", "id ASC", "id_user ASC", "total ASC",
					"order_date ASC", "id DESC", "id_user DESC", "total DESC", "order_date DESC" };
			String sanitizedOrder = "";

			for (String column : allowedColumns) {
				if (column.equalsIgnoreCase(sort)) {
					sanitizedOrder = column;
					break;
				}
			}

			if (!sanitizedOrder.isEmpty()) {
				selectSQL += " ORDER BY " + sanitizedOrder;
			}
		}

		try {
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			/*
			 * if(sort != null && !sort.equals("")) preparedStatement.setString(1, sort);
			 */
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				OrderBean bean = new OrderBean();

				bean.setId(rs.getInt("id"));
				bean.setId_user(rs.getInt("id_user"));
				bean.setTotal(rs.getInt("total"));
				bean.setDate(rs.getDate("order_date"));

				orders.add(bean);
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
		return orders;
	}

	/**
	 * Seleziona una singola riga dalla tabella {@link #TABLE_NAME} in base al
	 * codice.
	 * 
	 * @param id Il codice del prodotto da ottenere.
	 * @return Il bean ottenuto in base al codice.
	 * @category SELECT
	 */
	@Override
	public synchronized OrderBean doRetrieveByKey(int id) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		OrderBean bean = new OrderBean();

		String selectSQL = "SELECT * FROM " + OrderDAODataSource.TABLE_NAME + " WHERE id = ?";

		try {
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setInt(1, id);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {

				bean.setId(rs.getInt("id"));
				bean.setId_user(rs.getInt("id_user"));
				bean.setTotal(rs.getInt("total"));
				bean.setDate(rs.getDate("order_date"));
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
	 * Seleziona una collezione di ordini dalla tabella {@link #TABLE_NAME} in base
	 * al codice utente.
	 * 
	 * @param id Il codice dell'utente.
	 * @return La collezione di bean.
	 * @category SELECT
	 */
	public synchronized Collection<OrderBean> doRetrieveByUser(int idUser, String sort) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		Collection<OrderBean> orders = new LinkedList<OrderBean>();

		String selectSQL = "SELECT * FROM " + OrderDAODataSource.TABLE_NAME + " WHERE id_user = ?";

		// Qui non è possibile usare prepared statement per la order by
		if (sort != null && !sort.equals("")) {
			// Effettua la validazione del parametro "order" per garantire che sia un valore
			// sicuro e consentito
			String[] allowedColumns = { "id", "id_user", "total", "order_date", "id ASC", "id_user ASC", "total ASC",
					"order_date ASC", "id DESC", "id_user DESC", "total DESC", "order_date DESC" };
			String sanitizedOrder = "";

			for (String column : allowedColumns) {
				if (column.equalsIgnoreCase(sort)) {
					sanitizedOrder = column;
					break;
				}
			}

			if (!sanitizedOrder.isEmpty()) {
				selectSQL += " ORDER BY " + sanitizedOrder;
			}
		}

		try {
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setInt(1, idUser);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				OrderBean bean = new OrderBean();

				bean.setId(rs.getInt("id"));
				bean.setId_user(rs.getInt("id_user"));
				bean.setTotal(rs.getInt("total"));
				bean.setDate(rs.getDate("order_date"));

				orders.add(bean);
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
		return orders;
	}
}
