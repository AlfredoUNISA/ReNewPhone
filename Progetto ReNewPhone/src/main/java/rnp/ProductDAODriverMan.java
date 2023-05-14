package rnp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

/**
 * Fornisce le implementazioni per la gestione dei dati persistenti relativi ai prodotti in un database attraverso il Driver Manager.
 * La classe si occupa di eseguire le operazioni CRUD (create, retrieve, update e delete) sui dati nella tabella "product" della base di dati.
 * @deprecated Usare DAODataSource
 * @category Query con Driver Manager
 * @category MODIFICABILE
 */
public class ProductDAODriverMan implements IBeanDAO<ProductBean> /* MODIFICABILE */{

	private static final String TABLE_NAME = "products"; // MODIFICABILE

	/**
	 * Un bean "prodotto" viene inserito come una nuova riga nella tabella "TABLE_NAME" usando una connessione al database.
	 * @param product Prodotto da inserire
	 * @category MODIFICABILE
	 */
	@Override
	public synchronized void doSave(ProductBean product) throws SQLException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		// MODIFICABILE
		String insertSQL = "INSERT INTO " + ProductDAODriverMan.TABLE_NAME
				+ " (name, description, price, quantity, color, brand, category, state) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		try {
			connection = DriverManagerConnectionPool.getConnection();
			
			preparedStatement = connection.prepareStatement(insertSQL);
			
			// MODIFICABILE
			preparedStatement.setString(1, product.getName());
			preparedStatement.setString(2, product.getDescription());
			preparedStatement.setInt(3, product.getPrice());
			preparedStatement.setInt(4, product.getQuantity());
			preparedStatement.setString(5, product.getColor());
			preparedStatement.setString(6, product.getBrand());
			preparedStatement.setString(7, product.getCategory());
			preparedStatement.setString(8, product.getState());

			preparedStatement.executeUpdate();

			connection.commit();
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
	}

	/**
	 * Rimuove una riga dalla tabella "TABLE_NAME" in base al codice del prodotto.
	 * @param code Il codice del prodotto da rimuovere.
	 * @return L'esito della query.
	 * @category MODIFICABILE
	 */
	@Override
	public synchronized boolean doDelete(int id /* MODIFICABILE */) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		int result = 0;

		String deleteSQL = "DELETE FROM " + ProductDAODriverMan.TABLE_NAME + " WHERE id = ?"; // MODIFICABILE

		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(deleteSQL);
			
			preparedStatement.setInt(1, id); // MODIFICABILE

			result = preparedStatement.executeUpdate();
			connection.commit();

		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				DriverManagerConnectionPool.releaseConnection(connection);
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
	public synchronized Collection<ProductBean> doRetrieveAll(String order) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		Collection<ProductBean> products = new LinkedList<ProductBean>();

		String selectSQL = "SELECT * FROM " + ProductDAODriverMan.TABLE_NAME;

		if (order != null && !order.equals("")) {
			selectSQL += " ORDER BY " + order;
		}

		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				ProductBean bean = new ProductBean();

				// MODIFICABILE
				bean.setId(rs.getInt("ID"));
				bean.setName(rs.getString("NAME"));
				bean.setDescription(rs.getString("DESCRIPTION"));
				bean.setPrice(rs.getInt("PRICE"));
				bean.setQuantity(rs.getInt("QUANTITY"));
				bean.setColor(rs.getString("COLOR"));
				bean.setBrand(rs.getString("BRAND"));
				bean.setCategory(rs.getString("CATEGORY"));
				bean.setState(rs.getString("STATE"));
				
				products.add(bean);
			}

		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
		return products;
	}
	
	/**
	 * Seleziona una singola riga dalla tabella "TABLE_NAME" in base al codice del prodotto.
	 * @param code Il codice del prodotto da ottenere.
	 * @return Il bean ottenuto in base al codice.
	 * @category MODIFICABILE
	 */
	@Override
	public synchronized ProductBean doRetrieveByKey(int id /* MODIFICABILE */) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		ProductBean bean = new ProductBean();

		String selectSQL = "SELECT * FROM " + ProductDAODriverMan.TABLE_NAME + " WHERE id = ?"; // MODIFICABILE

		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setInt(1, id); // MODIFICABILE

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				// MODIFICABILE
				bean.setId(rs.getInt("ID"));
				bean.setName(rs.getString("NAME"));
				bean.setDescription(rs.getString("DESCRIPTION"));
				bean.setPrice(rs.getInt("PRICE"));
				bean.setQuantity(rs.getInt("QUANTITY"));
				bean.setColor(rs.getString("COLOR"));
				bean.setBrand(rs.getString("BRAND"));
				bean.setCategory(rs.getString("CATEGORY"));
				bean.setState(rs.getString("STATE"));
			}

		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
		return bean;
	}
}