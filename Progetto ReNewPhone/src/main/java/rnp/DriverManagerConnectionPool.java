package rnp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * La classe DriverManagerConnectionPool è una classe utilizzata per la gestione della connessione al database MySQL utilizzando la classe DriverManager di JDBC. 
 * In particolare, la classe gestisce una pool di connessioni al database per ottimizzare le performance dell'applicazione.
 * @category Connection Pool
 * @category MODIFICABILE 
 */
public class DriverManagerConnectionPool  {

	/**
	 * Lista di connessioni al db libere.
	 */
	private static List<Connection> freeDbConnections;

	/**
	 * Il metodo statico inizializzatore di classe viene eseguito una sola volta durante il caricamento della classe.
	 * Esso crea una lista vuota di connessioni libere e registra il driver MySQL JDBC.
	 */
	static {
		freeDbConnections = new LinkedList<Connection>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("DB driver not found:"+ e.getMessage());
		} 
	}
	
	/**
	 * Utilizzato per creare una nuova connessione al database utilizzando i parametri di connessione predefiniti
	 * (come nome db, username e password). 
	 * @return La connessione appena creata con l'autocommit disabilitato.
	 * @throws SQLException
	 */
	private static synchronized Connection createDBConnection() throws SQLException {
		// MODIFICABILE
		Connection newConnection = null;
		String ip = "localhost";
		String port = "3306";
		String db = "renewphonedb";
		String username = "root";
		String password = "password";

		newConnection = DriverManager.getConnection("jdbc:mysql://"+ ip+":"+ port+"/"+db+"?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", username, password);
		newConnection.setAutoCommit(false);
		return newConnection;
	}

	/**
	 * Se viene restituita una connessione, questa viene rimossa dalla lista delle connessioni libere. 
	 * Se la connessione ottenuta dalla lista libera è chiusa, viene chiusa e viene creata una nuova connessione. 
	 * Il metodo è sincronizzato per garantire l'accesso corretto alla lista delle connessioni libere da parte di thread concorrenti.
	 * @return Restituisce una connessione libera dalla pool, e se non esiste la crea.
	 * @throws SQLException
	 */
	public static synchronized Connection getConnection() throws SQLException {
		Connection connection;

		if (!freeDbConnections.isEmpty()) {
			connection = (Connection) freeDbConnections.get(0);
			freeDbConnections.remove(0);

			try {
				if (connection.isClosed())
					connection = getConnection();
			} catch (SQLException e) {
				connection.close();
				connection = getConnection();
			}
		} else {
			connection = createDBConnection();		
		}

		return connection;
	}

	/**
	 * Utilizzato per rilasciare una connessione utilizzata.
	 * La connessione viene aggiunta alla lista delle connessioni libere in modo che possa essere utilizzata da altri thread 
	 * che richiedono una connessione. Il metodo è sincronizzato per garantire l'accesso corretto alla lista delle connessioni 
	 * libere da parte di thread concorrenti.
	 * @param connection La connessione da rilasciare.
	 * @throws SQLException
	 */
	public static synchronized void releaseConnection(Connection connection) throws SQLException {
		if(connection != null) 
			freeDbConnections.add(connection);
	}
}
