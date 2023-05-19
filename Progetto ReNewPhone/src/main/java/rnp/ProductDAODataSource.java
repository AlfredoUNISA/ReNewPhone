package rnp;

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

/**
 * Fornisce l'accesso ai dati di un oggetto ProductBean in una base di dati
 * relazionale attraverso un pool di connessioni DataSource. La classe si occupa
 * di eseguire le operazioni CRUD (create, retrieve, update e delete) sui dati
 * nella tabella "product" della base di dati.
 * 
 * @category Query con Data Source
 * @implNote ATTENZIONE: Modificare web.xml (resource-ref con JNDI) e modificare
 *           context.xml (in META-INF)
 * @category MODIFICABILE
 */
public class ProductDAODataSource implements IBeanDAO<ProductBean> /* MODIFICABILE */ {

	private static DataSource ds;
	private static final String TABLE_NAME = "products"; // MODIFICABILE

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
	 * Un bean "prodotto" viene inserito come una nuova riga nella tabella
	 * "TABLE_NAME" usando una connessione al database.
	 * 
	 * @param product Prodotto da inserire
	 * @return L'id generato automaticamente dalla insert
	 * @category MODIFICABILE
	 */
	@Override
	public synchronized int doSave(ProductBean product) throws SQLException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		// MODIFICABILE
		String insertSQL = "INSERT INTO " + ProductDAODataSource.TABLE_NAME
				+ " (name, description, price, quantity, color, brand, year, category, state) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

		int generatedId = -1;

		try {
			connection = ds.getConnection();

			preparedStatement = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);

			// MODIFICABILE
			preparedStatement.setString(1, product.getName());
			preparedStatement.setString(2, product.getDescription());
			preparedStatement.setInt(3, product.getPrice());
			preparedStatement.setInt(4, product.getQuantity());
			preparedStatement.setString(5, product.getColor());
			preparedStatement.setString(6, product.getBrand());
			preparedStatement.setInt(7, product.getYear());
			preparedStatement.setString(8, product.getCategory());
			preparedStatement.setString(9, product.getState());

			preparedStatement.executeUpdate();
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
	 * Rimuove una riga dalla tabella "TABLE_NAME" in base al codice del prodotto.
	 * 
	 * @param code Il codice del prodotto da rimuovere.
	 * @return L'esito della query.
	 * @category MODIFICABILE
	 */
	@Override
	public synchronized boolean doDelete(int id /* MODIFICABILE */) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		int result = 0;

		String deleteSQL = "DELETE FROM " + ProductDAODataSource.TABLE_NAME + " WHERE id = ?"; // MODIFICABILE

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
	 * @param order Specifica l'ordine di ordinamento dei risultati (se non è nullo
	 *              aggiunge ORDER BY alla query).
	 * @return La collezione di oggetti contenente tutte le righe della tabella.
	 * @category MODIFICABILE
	 */
	@Override
	public synchronized Collection<ProductBean> doRetrieveAll(String order) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		Collection<ProductBean> products = new LinkedList<ProductBean>();

		String selectSQL = "SELECT * FROM " + ProductDAODataSource.TABLE_NAME;

		if (order != null && !order.equals("")) {
			selectSQL += " ORDER BY " + order;
		}

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				ProductBean bean = new ProductBean();

				// MODIFICABILE
				bean.setId(rs.getInt("id"));
				bean.setName(rs.getString("name"));
				bean.setDescription(rs.getString("description"));
				bean.setPrice(rs.getInt("price"));
				bean.setQuantity(rs.getInt("quantity"));
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
	 * Seleziona una singola riga dalla tabella "TABLE_NAME" in base al codice del
	 * prodotto.
	 * 
	 * @param code Il codice del prodotto da ottenere.
	 * @return Il bean ottenuto in base al codice.
	 * @category MODIFICABILE
	 */
	@Override
	public synchronized ProductBean doRetrieveByKey(int id /* MODIFICABILE */) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		ProductBean bean = new ProductBean();

		String selectSQL = "SELECT * FROM " + ProductDAODataSource.TABLE_NAME + " WHERE id = ?"; // MODIFICABILE

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setInt(1, id); // MODIFICABILE
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				// MODIFICABILE
				bean.setId(rs.getInt("id"));
				bean.setName(rs.getString("name"));
				bean.setDescription(rs.getString("description"));
				bean.setPrice(rs.getInt("price"));
				bean.setQuantity(rs.getInt("quantity"));
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
}
