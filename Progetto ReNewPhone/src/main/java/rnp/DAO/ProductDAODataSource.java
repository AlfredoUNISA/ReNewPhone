package rnp.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import rnp.Bean.ProductBean;
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
public class ProductDAODataSource implements MethodsDAO<ProductBean>, VariousHelper {
	private static DataSource dataSource;
	private static final String TABLE_NAME = "products";
	
	private static final String CLASS_NAME = ProductDAODataSource.class.getName();
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
	 * Un bean "prodotto" viene inserito come una nuova riga nella tabella
	 * {@link #TABLE_NAME} usando una connessione al database.
	 * 
	 * @param product Prodotto da inserire
	 * @return L'id generato automaticamente dalla insert
	 * @category INSERT
	 */
	@Override
	public synchronized int doSave(ProductBean product) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		
		String insertSQL = "INSERT INTO " + ProductDAODataSource.TABLE_NAME
				+ " (name, ram, display_size, storage, price, quantity, color, brand, year, category, state) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		int generatedId = -1;

		try {
			connection = dataSource.getConnection();

			preparedStatement = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);

			
			preparedStatement.setString(1, product.getName());
			preparedStatement.setInt(2, product.getRam());
			preparedStatement.setFloat(3, product.getDisplay_size());
			preparedStatement.setInt(4, product.getStorage());
			preparedStatement.setInt(5, product.getPrice());
			preparedStatement.setInt(6, product.getQuantity());
			preparedStatement.setString(7, product.getColor());
			preparedStatement.setString(8, product.getBrand());
			preparedStatement.setInt(9, product.getYear());
			preparedStatement.setString(10, product.getCategory());
			preparedStatement.setString(11, product.getState());

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
	 * Rimuove una riga dalla tabella {@link #TABLE_NAME} in base al codice del prodotto.
	 * 
	 * @param code Il codice del prodotto da rimuovere.
	 * @return L'esito della query.
	 * @category DELETE
	 */
	@Override
	public synchronized boolean doDelete(int id) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		int result = 0;

		String deleteSQL = "DELETE FROM " + ProductDAODataSource.TABLE_NAME + " WHERE id = ?"; 

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
	 * @param order Specifica l'ordine di ordinamento dei risultati (se non è nullo
	 *              aggiunge ORDER BY alla query).
	 * @return La collezione di oggetti contenente tutte le righe della tabella.
	 * @category SELECT
	 */
	@Override
	public synchronized Collection<ProductBean> doRetrieveAll(String order) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		Collection<ProductBean> products = new LinkedList<>();

		String selectSQL = "SELECT * FROM " + ProductDAODataSource.TABLE_NAME;

		if (order != null && !order.equals("")) {
			selectSQL += " ORDER BY ?";
		}

		try {
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			if (order != null && !order.equals(""))
				preparedStatement.setString(1, order);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				ProductBean bean = new ProductBean();

				
				bean.setId(rs.getInt("id"));
				bean.setName(rs.getString("name"));
				bean.setRam(rs.getInt("ram"));
				bean.setDisplay_size(rs.getFloat("display_size"));
				bean.setStorage(rs.getInt("storage"));
				bean.setPrice(rs.getInt("price"));
				bean.setQuantity(rs.getInt("quantity"));
				bean.setColor(rs.getString("color"));
				bean.setBrand(rs.getString("brand"));
				bean.setYear(rs.getInt("year"));
				bean.setCategory(rs.getString("category"));
				bean.setState(rs.getString("state"));
				bean.setModel();

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
	 * Seleziona tutte le righe dalla tabella {@link #TABLE_NAME} e restituisce una
	 * collezione di oggetti.
	 * 
	 * @param order Specifica l'ordine di ordinamento dei risultati (se non è nullo
	 *              aggiunge ORDER BY alla query).
	 * @return La collezione di oggetti contenente tutte le righe della tabella con un nome specifico.
	 * @category SELECT
	 */
	public synchronized Collection<ProductBean> doRetrieveByName(String name) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		Collection<ProductBean> products = new LinkedList<>();

		String selectSQL = "SELECT * FROM " + ProductDAODataSource.TABLE_NAME + " WHERE name LIKE ?";

		try {
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);

			if (name != null && !name.equals("")) {
				preparedStatement.setString(1, name);
			}
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				ProductBean bean = new ProductBean();

				
				bean.setId(rs.getInt("id"));
				bean.setName(rs.getString("name"));
				bean.setRam(rs.getInt("ram"));
				bean.setDisplay_size(rs.getFloat("display_size"));
				bean.setStorage(rs.getInt("storage"));
				bean.setPrice(rs.getInt("price"));
				bean.setQuantity(rs.getInt("quantity"));
				bean.setColor(rs.getString("color"));
				bean.setBrand(rs.getString("brand"));
				bean.setYear(rs.getInt("year"));
				bean.setCategory(rs.getString("category"));
				bean.setState(rs.getString("state"));
				bean.setModel();

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
	 * Seleziona una singola riga dalla tabella {@link #TABLE_NAME} in base al codice del
	 * prodotto.
	 * 
	 * @param code Il codice del prodotto da ottenere.
	 * @return Il bean ottenuto in base al codice.
	 * @category SELECT
	 */
	@Override
	public synchronized ProductBean doRetrieveByKey(int id) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		ProductBean bean = new ProductBean();

		String selectSQL = "SELECT * FROM " + ProductDAODataSource.TABLE_NAME + " WHERE id = ?"; 

		try {
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setInt(1, id); 
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				
				bean.setId(rs.getInt("id"));
				bean.setName(rs.getString("name"));
				bean.setRam(rs.getInt("ram"));
				bean.setDisplay_size(rs.getFloat("display_size"));
				bean.setStorage(rs.getInt("storage"));
				bean.setPrice(rs.getInt("price"));
				bean.setQuantity(rs.getInt("quantity"));
				bean.setColor(rs.getString("color"));
				bean.setBrand(rs.getString("brand"));
				bean.setYear(rs.getInt("year"));
				bean.setCategory(rs.getString("category"));
				bean.setState(rs.getString("state"));
				bean.setModel();
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
	 * Ottiene tutti i brand che si trovano nella tabella {@link #TABLE_NAME}.
	 * @return La collezione di stringhe contenente tutti i brand.
	 * @category SELECT
	 */
	public synchronized Collection<String> doRetrieveBrands() throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
	
		Collection<String> brands = new ArrayList<>();
		String selectSQL = "SELECT brand FROM " + ProductDAODataSource.TABLE_NAME + " GROUP BY brand";
	
		try {
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				brands.add(rs.getString("brand"));
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
		return brands;
	}

	/**
	 * Riduce di una quantità specificata la quantità di un prodotto nella tabella {@link #TABLE_NAME}.
	 * @param id L'id del prodotto
	 * @param qty La quantità da rimuovere
	 * @category UPDATE
	 */
	public synchronized void reduceStock(int id, int qty) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String updateSQL = "UPDATE " + ProductDAODataSource.TABLE_NAME + " SET quantity = quantity - ? WHERE id = ?"; 

		ProductBean product = doRetrieveByKey(id);
		if (product.getQuantity() < qty)
			throw new SQLException("Not Enough in stock");

		try {
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(updateSQL);
			preparedStatement.setInt(1, qty); 
			preparedStatement.setInt(2, id); 

			preparedStatement.executeUpdate();
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
	
	public synchronized void doUpdate(ProductBean product) throws SQLException {
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;

	    String updateSQL = "UPDATE " + ProductDAODataSource.TABLE_NAME + " SET"
	    		+ " name = ?, ram = ?, display_size = ?, storage = ?,"
	    		+ " price = ?, quantity = ?, color = ?, brand = ?,"
	    		+ " year = ?, category = ?, state = ?"
	    		+ " WHERE id = ?";

	    try {
	        connection = dataSource.getConnection();
	        preparedStatement = connection.prepareStatement(updateSQL);

	        preparedStatement.setString(1, product.getName());
	        preparedStatement.setInt(2, product.getRam());
	        preparedStatement.setFloat(3, product.getDisplay_size());
	        preparedStatement.setInt(4, product.getStorage());
	        
	        preparedStatement.setInt(5, product.getPrice());
	        preparedStatement.setInt(6, product.getQuantity());
	        preparedStatement.setString(7, product.getColor());
	        preparedStatement.setString(8, product.getBrand());
	        
	        preparedStatement.setInt(9, product.getYear());
	        preparedStatement.setString(10, product.getCategory());
	        preparedStatement.setString(11, product.getState());
	        
	        preparedStatement.setInt(12, product.getId());

	        preparedStatement.executeUpdate();
	        
	        connection.setAutoCommit(false);
	        connection.commit();
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

}
