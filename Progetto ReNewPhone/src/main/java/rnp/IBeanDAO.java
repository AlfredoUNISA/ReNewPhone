package rnp;

import java.sql.SQLException;
import java.util.Collection;

/**
 * Astrazione dei metodi necessari per gestire la persistenza di oggetti di tipo T in un database relazionale utilizzando JDBC.
 * @param <T> Oggetto generico che deve persistere.
 * @category Interfaccia
 */
public interface IBeanDAO<T> {
	
	public void doSave(T bean) throws SQLException;

	public boolean doDelete(int code) throws SQLException;

	public T doRetrieveByKey(int code) throws SQLException;
	
	public Collection<T> doRetrieveAll(String order) throws SQLException;
}



