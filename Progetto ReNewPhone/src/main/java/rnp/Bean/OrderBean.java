package rnp.Bean;

import java.io.Serializable;
import java.sql.Date;

/**
 * Bean che contiene tutte le informazioni di una riga della tabella "orders" del database.
 */
public class OrderBean implements Serializable {
	private static final long serialVersionUID = 1L;

	int id;
    int id_user;
    int total;
    Date order_date;
    
	public OrderBean() {
		id = -1;
		id_user = 0;
		total = 0;
		order_date = null;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public int getId_user() {
		return id_user;
	}
	public void setId_user(int id_user) {
		this.id_user = id_user;
	}

	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	
	public Date getDate() {
		return order_date;
	}
	public void setDate(Date date) {
		this.order_date = date;
	}

	@Override
	public String toString() {
		return "OrderBean [id=" + id + ", id_user=" + id_user + ", total=" + total + ", order_date=" + order_date + "]";
	}
}
