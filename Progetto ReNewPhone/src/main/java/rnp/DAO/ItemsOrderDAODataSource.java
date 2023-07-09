package rnp.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import rnp.Bean.ItemOrderBean;
import rnp.Servlet.VariousHelper;

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
public class ItemsOrderDAODataSource implements MethodsDAO<ItemOrderBean>, VariousHelper {
	private static DataSource dataSource;
	private static final String TABLE_NAME = "order_items";

	private static final String CLASS_NAME = ItemsOrderDAODataSource.class.getName();
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
	public synchronized int doSave(ItemOrderBean item) throws SQLException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String insertSQL = "INSERT INTO " + ItemsOrderDAODataSource.TABLE_NAME
				+ " (id_order, id_product, ordered_quantity, name,"
				+ " ram, display_size, storage, price,"
				+ " color, brand, year, category,"
				+ " state) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		int generatedId = -1;

		try {
			// Riduci il numero di prodotti in magazzino
			ProductDAODataSource productDAO = new ProductDAODataSource();
			productDAO.reduceStock(item.getId_product(), item.getOrderedQuantity());

			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);

			preparedStatement.setInt(1, item.getId_order());
			preparedStatement.setInt(2, item.getId_product());
			preparedStatement.setInt(3, item.getOrderedQuantity());
			
			preparedStatement.setString(4, item.getName());
			preparedStatement.setInt(5, item.getRam());
			preparedStatement.setFloat(6, item.getDisplaySize());
			preparedStatement.setInt(7, item.getStorage());
			preparedStatement.setInt(8, item.getPrice());
			preparedStatement.setString(9, item.getColor());
			preparedStatement.setString(10, item.getBrand());
			preparedStatement.setInt(11, item.getYear());
			preparedStatement.setString(12, item.getCategory());
			preparedStatement.setString(13, item.getState());

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

		String deleteSQL = "DELETE FROM " + ItemsOrderDAODataSource.TABLE_NAME + " WHERE id = ?";

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
	public synchronized Collection<ItemOrderBean> doRetrieveAll(String sort) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		Collection<ItemOrderBean> products = new LinkedList<>();

		String selectSQL = "SELECT * FROM " + ItemsOrderDAODataSource.TABLE_NAME;

		if (sort != null && !sort.equals("")) {
			selectSQL += " ORDER BY ?";
		}

		try {
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			if (sort != null && !sort.equals(""))
				preparedStatement.setString(1, sort);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				ItemOrderBean bean = new ItemOrderBean();

				bean.setId(rs.getInt("id"));
				bean.setId_order(rs.getInt("id_order"));
				bean.setId_product(rs.getInt("id_product"));
				bean.setOrderedQuantity(rs.getInt("ordered_quantity"));
				
				bean.setName(rs.getString("name"));
				bean.setRam(rs.getInt("ram"));
				bean.setDisplaySize(rs.getFloat("display_size"));
				bean.setStorage(rs.getInt("storage"));
				bean.setPrice(rs.getInt("price"));
				bean.setColor(rs.getString("color"));
				bean.setBrand(rs.getString("brand"));
				bean.setYear(rs.getInt("year"));
				bean.setCategory(rs.getString("category"));
				bean.setState(rs.getString("state"));

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
	 * Seleziona una singola riga dalla tabella {@link #TABLE_NAME} in base al
	 * codice.
	 * 
	 * @param id Il codice del oggetto da ottenere.
	 * @return Il bean ottenuto in base al codice.
	 * @category SELECT
	 */
	@Override
	public synchronized ItemOrderBean doRetrieveByKey(int id) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		ItemOrderBean bean = new ItemOrderBean();

		String selectSQL = "SELECT * FROM " + ItemsOrderDAODataSource.TABLE_NAME + " WHERE id = ?";

		try {
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setInt(1, id);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {

				bean.setId(rs.getInt("id"));
				bean.setId_order(rs.getInt("id_order"));
				bean.setId_product(rs.getInt("id_product"));
				bean.setOrderedQuantity(rs.getInt("quantity"));
				
				bean.setName(rs.getString("name"));
				bean.setRam(rs.getInt("ram"));
				bean.setDisplaySize(rs.getFloat("display_size"));
				bean.setStorage(rs.getInt("storage"));
				bean.setPrice(rs.getInt("price"));
				bean.setColor(rs.getString("color"));
				bean.setBrand(rs.getString("brand"));
				bean.setYear(rs.getInt("year"));
				bean.setCategory(rs.getString("category"));
				bean.setState(rs.getString("state"));
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
	 * Seleziona una singola riga dalla tabella {@link #TABLE_NAME} in base al
	 * codice dell'ordine.
	 * 
	 * @param id Il codice del oggetto da ottenere.
	 * @return Il bean ottenuto in base al codice.
	 * @category SELECT
	 */
	public synchronized Collection<ItemOrderBean> doRetrieveByOrder(int id_order) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		Collection<ItemOrderBean> products = new LinkedList<ItemOrderBean>();

		String selectSQL = "SELECT * FROM " + ItemsOrderDAODataSource.TABLE_NAME + " WHERE id_order = ?";

		try {
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setInt(1, id_order);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				ItemOrderBean bean = new ItemOrderBean();

				bean.setId(rs.getInt("id"));
				bean.setId_order(rs.getInt("id_order"));
				bean.setId_product(rs.getInt("id_product"));
				bean.setOrderedQuantity(rs.getInt("ordered_quantity"));
				
				bean.setName(rs.getString("name"));
				bean.setRam(rs.getInt("ram"));
				bean.setDisplaySize(rs.getFloat("display_size"));
				bean.setStorage(rs.getInt("storage"));
				bean.setPrice(rs.getInt("price"));
				bean.setColor(rs.getString("color"));
				bean.setBrand(rs.getString("brand"));
				bean.setYear(rs.getInt("year"));
				bean.setCategory(rs.getString("category"));
				bean.setState(rs.getString("state"));

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
	 * @category SELECT
	 */
	public synchronized int numberOfItems(int id_order) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String selectCountSQL = "SELECT COUNT(*) AS number FROM " + ItemsOrderDAODataSource.TABLE_NAME
				+ " WHERE id_order = ?";
		int result = 0;

		try {
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(selectCountSQL);
			preparedStatement.setInt(1, id_order);
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
