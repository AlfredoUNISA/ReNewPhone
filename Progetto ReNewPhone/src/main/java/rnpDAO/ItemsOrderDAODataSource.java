package rnpDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.LinkedList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import rnpBean.ItemOrderBean;

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
public class ItemsOrderDAODataSource implements IBeanDAO<ItemOrderBean> /* MODIFICABILE */ {

	private static DataSource ds;
	private static final String TABLE_NAME = "order_items"; // MODIFICABILE

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
	 * @param order Oggetto da inserire
	 * @return L'id generato automaticamente dalla insert
	 * @category MODIFICABILE
	 */
	@Override
	public synchronized int doSave(ItemOrderBean item) throws SQLException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		// MODIFICABILE
		String insertSQL = "INSERT INTO " + ItemsOrderDAODataSource.TABLE_NAME
				+ " (id_order, id_product, quantity) VALUES (?, ?, ?)";

		int generatedId = -1;

		try {
			// Riduci il numero di prodotti in magazzino
			ProductDAODataSource productDAO = new ProductDAODataSource();
			productDAO.reduceStock(item.getId_product(), item.getQuantity());

			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);

			// MODIFICABILE
			preparedStatement.setInt(1, item.getId_order());
			preparedStatement.setInt(2, item.getId_product());
			preparedStatement.setInt(3, item.getQuantity());

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
	 * Rimuove una riga dalla tabella "TABLE_NAME" in base al codice.
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

		String deleteSQL = "DELETE FROM " + ItemsOrderDAODataSource.TABLE_NAME + " WHERE id = ?"; // MODIFICABILE

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
	 * Seleziona tutte le righe dalla tabella "TABLE_NAME" e restituisce una
	 * collezione di oggetti.
	 * 
	 * @param sort Specifica l'ordine di ordinamento dei risultati (se non è nullo
	 *             aggiunge ORDER BY alla query).
	 * @return La collezione di oggetti contenente tutte le righe della tabella.
	 * @category MODIFICABILE
	 */
	@Override
	public synchronized Collection<ItemOrderBean> doRetrieveAll(String sort) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		Collection<ItemOrderBean> products = new LinkedList<ItemOrderBean>();

		String selectSQL = "SELECT * FROM " + ItemsOrderDAODataSource.TABLE_NAME;

		if (sort != null && !sort.equals("")) {
			selectSQL += " ORDER BY ?";
		}

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			if(sort != null && !sort.equals(""))
				preparedStatement.setString(1, sort);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				ItemOrderBean bean = new ItemOrderBean();

				// MODIFICABILE
				bean.setId(rs.getInt("id"));
				bean.setId_order(rs.getInt("id_order"));
				bean.setId_product(rs.getInt("id_product"));
				bean.setQuantity(rs.getInt("quantity"));

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
	 * @param id Il codice del oggetto da ottenere.
	 * @return Il bean ottenuto in base al codice.
	 * @category MODIFICABILE
	 */
	@Override
	public synchronized ItemOrderBean doRetrieveByKey(int id /* MODIFICABILE */) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		ItemOrderBean bean = new ItemOrderBean();

		String selectSQL = "SELECT * FROM " + ItemsOrderDAODataSource.TABLE_NAME + " WHERE id = ?"; // MODIFICABILE

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setInt(1, id); // MODIFICABILE
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				// MODIFICABILE
				bean.setId(rs.getInt("id"));
				bean.setId_order(rs.getInt("id_order"));
				bean.setId_product(rs.getInt("id_product"));
				bean.setQuantity(rs.getInt("quantity"));
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
	 * Seleziona una singola riga dalla tabella "TABLE_NAME" in base al codice.
	 * 
	 * @param id Il codice del oggetto da ottenere.
	 * @return Il bean ottenuto in base al codice.
	 * @category MODIFICABILE
	 */
	public synchronized Collection<ItemOrderBean> doRetrieveByOrder(int id_order) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		Collection<ItemOrderBean> products = new LinkedList<ItemOrderBean>();

		String selectSQL = "SELECT * FROM " + ItemsOrderDAODataSource.TABLE_NAME + " WHERE id_order = ?";

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setInt(1, id_order); // MODIFICABILE

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				ItemOrderBean bean = new ItemOrderBean();

				// MODIFICABILE
				bean.setId(rs.getInt("id"));
				bean.setId_order(rs.getInt("id_order"));
				bean.setId_product(rs.getInt("id_product"));
				bean.setQuantity(rs.getInt("quantity"));

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
	 * Ritorna il numero di oggetti nella tabella con un certo id dell'ordine
	 * 
	 * @param id_order Id dell'ordine
	 */
	public synchronized int numberOfItems(int id_order) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String selectCountSQL = "SELECT COUNT(*) AS number FROM " + ItemsOrderDAODataSource.TABLE_NAME
				+ " WHERE id_order = ?"; // MODIFICABILE
		int result = 0;

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectCountSQL);
			preparedStatement.setInt(1, id_order); // MODIFICABILE
			ResultSet rs = preparedStatement.executeQuery();
			rs.next();
			result = rs.getInt("number");
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
}
