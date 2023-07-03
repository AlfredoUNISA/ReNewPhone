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

import rnpBean.OrderBean;

/**
 * Fornisce l'accesso ai dati di un oggetto Bean in una base di dati relazionale attraverso un pool di connessioni DataSource. 
 * La classe si occupa di eseguire le operazioni CRUD (create, retrieve, update e delete) sui dati nella tabella "TABLE_NAME" della base di dati.
 * @category Query con Data Source
 * @implNote ATTENZIONE: Modificare web.xml (resource-ref con JNDI) e modificare context.xml (in META-INF)
 * @category MODIFICABILE
 */
public class OrderDAODataSource implements IBeanDAO<OrderBean> /* MODIFICABILE */ {

	private static DataSource ds;
	private static final String TABLE_NAME = "orders"; // MODIFICABILE

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
	 * @param order Oggetto da inserire
	 * @return L'id generato automaticamente dalla insert
	 * @category MODIFICABILE
	 */
	@Override
	public synchronized int doSave(OrderBean order) throws SQLException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		// MODIFICABILE
		String insertSQL = "INSERT INTO " + OrderDAODataSource.TABLE_NAME
                + " (id_user, total) VALUES (?, ?)";
		
		int generatedId = -1;

		try { 
			connection = ds.getConnection();

			preparedStatement = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);

			// MODIFICABILE
			preparedStatement.setInt(1, order.getId_user());
			preparedStatement.setInt(2, order.getTotal());

			preparedStatement.executeUpdate();
			
			connection.setAutoCommit(false);
			connection.commit();
			
			// Ottieni l'id generato
			try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	            	generatedId = generatedKeys.getInt(1);
	            }
	            else {
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
	 * @param id Il codice del prodotto da rimuovere.
	 * @return L'esito della query.
	 * @category MODIFICABILE
	 */
	@Override
	public synchronized boolean doDelete(int id /* MODIFICABILE */) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		int result = 0;


		String deleteSQL = "DELETE FROM " + OrderDAODataSource.TABLE_NAME + " WHERE id = ?"; // MODIFICABILE

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
	 * @param sort Specifica l'ordine di ordinamento dei risultati (se non è nullo aggiunge ORDER BY alla query).
	 * @return La collezione di oggetti contenente tutte le righe della tabella.
	 * @category MODIFICABILE
	 */
	@Override
	public synchronized Collection<OrderBean> doRetrieveAll(String sort) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		Collection<OrderBean> products = new LinkedList<OrderBean>();

		String selectSQL = "SELECT * FROM " + OrderDAODataSource.TABLE_NAME;

		if (sort != null && !sort.equals("")) {
			selectSQL += " ORDER BY " + sort;
		}

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			/*
			if(sort != null && !sort.equals(""))
				preparedStatement.setString(1, sort);
*/
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				OrderBean bean = new OrderBean();

				// MODIFICABILE
				bean.setId(rs.getInt("id"));
				bean.setId_user(rs.getInt("id_user"));
				bean.setTotal(rs.getInt("total"));
				
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
	 * @param id Il codice del prodotto da ottenere.
	 * @return Il bean ottenuto in base al codice.
	 * @category MODIFICABILE
	 */
	@Override
	public synchronized OrderBean doRetrieveByKey(int id /* MODIFICABILE */) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		OrderBean bean = new OrderBean();
		
		String selectSQL = "SELECT * FROM " + OrderDAODataSource.TABLE_NAME + " WHERE id = ?"; // MODIFICABILE
		
		try {
			connection = ds.getConnection();	
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setInt(1, id); // MODIFICABILE
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				// MODIFICABILE
				bean.setId(rs.getInt("id"));
				bean.setId_user(rs.getInt("id_user"));
				bean.setTotal(rs.getInt("total"));
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

