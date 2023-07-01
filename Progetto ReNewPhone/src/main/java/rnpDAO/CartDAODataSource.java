package rnpDAO;

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

import rnpBean.CartBean;

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
public class CartDAODataSource implements IBeanDAO<CartBean> /* MODIFICABILE */ {
	private static final Logger logger = Logger.getLogger(CartDAODataSource.class.getName());
	private static DataSource ds;
	private static final String TABLE_NAME = "carts"; // MODIFICABILE
	private static final String selectPrototype="SELECT * FROM " + CartDAODataSource.TABLE_NAME;
	// Inizializzazione per il Data Source
	static {
		try {
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");

			ds = (DataSource) envCtx.lookup("jdbc/renewphonedb"); // MODIFICABILE

		} catch (NamingException e) {
			logger.log(Level.SEVERE,"Error:" + e.getMessage());
		}
	}

	/**
	 * Un bean viene inserito come una nuova riga nella tabella "TABLE_NAME" usando
	 * una connessione al database.
	 * 
	 * @param cart_row Oggetto da inserire
	 * @return Zero
	 * @category MODIFICABILE
	 */
	@Override
	public synchronized int doSave(CartBean cart_row) throws SQLException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		// MODIFICABILE
		String insertSQL = "INSERT INTO " + CartDAODataSource.TABLE_NAME
				+ " (id_user, id_product, quantity) VALUES (?, ?, ?)";
		
		try {
			connection = ds.getConnection();

			preparedStatement = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);

			// MODIFICABILE
			preparedStatement.setInt(1, cart_row.getId_user());
			preparedStatement.setInt(2, cart_row.getId_product());
			preparedStatement.setInt(3, cart_row.getQuantity());

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
		return 0;
	}

	/**
	 * Rimuove UNA SINGOLA riga dalla tabella "TABLE_NAME" in base ad entrambe le
	 * chiavi primarie.
	 * 
	 * @param id_user    Il codice UTENTE della riga da rimuovere dalla tabella.
	 * @param id_product Il codice PRODOTTO della riga da rimuovere dalla tabella.
	 * @return L'esito della query.
	 * @category MODIFICABILE
	 */
	public synchronized boolean doDeleteSingleRow(int id_user, int id_product /* MODIFICABILE */) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		int result = 0;

		String deleteSQL = "DELETE FROM " + CartDAODataSource.TABLE_NAME + " WHERE id_user = ? AND id_product = ?"; // MODIFICABILE

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(deleteSQL);

			// MODIFICABILE
			preparedStatement.setInt(1, id_user);
			preparedStatement.setInt(2, id_product);

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
	 * !!! ATTENZIONE !!! Elimina tutte le righe dalla tabella "TABLE_NAME" in base
	 * all'id dell'UTENTE. Utilizzare la doDeleteSingleRow() se si vuole eliminare
	 * una sola riga.
	 * 
	 * @param id_user Il codice UTENTE della riga da rimuovere dalla tabella.
	 * @return L'esito della query.
	 * @category MODIFICABILE
	 */
	@Override
	public synchronized boolean doDelete(int id_user) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		int result = 0;

		String deleteSQL = "DELETE FROM " + CartDAODataSource.TABLE_NAME + " WHERE id_user = ?"; // MODIFICABILE

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(deleteSQL);

			// MODIFICABILE
			preparedStatement.setInt(1, id_user);

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
	 * Seleziona TUTTE le righe dalla tabella "TABLE_NAME" e restituisce una
	 * collezione di oggetti.
	 * 
	 * @param order Specifica l'ordine di ordinamento dei risultati (se non è nullo
	 *              aggiunge ORDER BY alla query).
	 * @return La collezione di oggetti contenente tutte le righe della tabella.
	 * @category MODIFICABILE
	 */
	@Override
	public synchronized Collection<CartBean> doRetrieveAll(String order) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		Collection<CartBean> carts = new LinkedList<CartBean>();

		String selectSQL=selectPrototype;

		if (order != null && !order.equals("")) {
			selectSQL += " ORDER BY ?";
		}

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			if(order != null && !order.equals(""))
				preparedStatement.setString(1, order);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				CartBean bean = new CartBean();

				// MODIFICABILE
				bean.setId_user(rs.getInt("id_user"));
				bean.setId_product(rs.getInt("id_product"));
				bean.setQuantity(rs.getInt("quantity"));

				carts.add(bean);
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
		return carts;
	}

	/**
	 * Seleziona una SINGOLA riga dalla tabella "TABLE_NAME" in base ad entrambe le
	 * chiavi primarie.
	 * 
	 * @param id_user    Il codice UTENTE di cui si vuole ottenere la riga.
	 * @param id_product Il codice PRODOTTO di cui si vuole ottenere la riga.
	 * @return Il bean ottenuto in base al codice.
	 * @category MODIFICABILE
	 */
	public synchronized CartBean doRetrieveByPrimaryKeys(int id_user, int id_product /* MODIFICABILE */)
			throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		CartBean bean = new CartBean();

		String selectSQL = selectPrototype + " WHERE id_user = ? AND id_product = ?"; // MODIFICABILE

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);

			// MODIFICABILE
			preparedStatement.setInt(1, id_user);
			preparedStatement.setInt(2, id_product);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				// MODIFICABILE
				bean.setId_user(rs.getInt("id_user"));
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
	 * Seleziona una COLLEZIONE di righe dalla tabella "TABLE_NAME" in base all'id
	 * dell'UTENTE.
	 * 
	 * @param id_user Il codice UTENTE di cui si vogliono ottenere gli oggetti nel
	 *                carrello.
	 * @param order   Specifica l'ordine di ordinamento dei risultati (se non è
	 *                nullo aggiunge ORDER BY alla query).
	 * @return La collezione di oggetti contenente tutte le righe inerenti
	 *         all'utente.
	 * @category MODIFICABILE
	 */
	public synchronized Collection<CartBean> doRetrieveByUser(int id_user, String order) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		Collection<CartBean> carts = new LinkedList<CartBean>();

		String selectSQL = selectPrototype + " WHERE id_user = ?"; // MODIFICABILE

		if (order != null && !order.equals("")) {
			selectSQL += " ORDER BY " + order;
		}

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);

			// MODIFICABILE
			preparedStatement.setInt(1, id_user);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				CartBean bean = new CartBean();

				// MODIFICABILE
				bean.setId_user(rs.getInt("id_user"));
				bean.setId_product(rs.getInt("id_product"));
				bean.setQuantity(rs.getInt("quantity"));

				carts.add(bean);
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
		return carts;
	}

	/**
	 * !!! ATTENZIONE !!! Sono forzato dall'interfaccia a creare questo metodo.
	 * Usare doRetrieveByPrimaryKeys() oppure doRetrieveByUser().
	 * 
	 * @return null ed un messaggio in sout.
	 * @deprecated
	 */
	@Override
	public synchronized CartBean doRetrieveByKey(int NON_USARE_QUESTO_METODO) throws SQLException {
		System.out.println("Non usare questo metodo, usa doRetrieveByPrimaryKeys() oppure doRetrieveByUser()");
		return null;
	}

}
