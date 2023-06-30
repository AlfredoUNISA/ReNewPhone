package rnpBean;

import java.io.Serializable;

/**
 * Bean che contiene tutte le informazioni di un ordine.
 * @category Bean
 */
public class OrderBean implements Serializable {

	private static final long serialVersionUID = 1L;

	int id;
    int id_user;
    int total;
    
	public OrderBean() {
		id = -1;
		id_user = 0;
		total = 0;
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

	@Override
	public String toString() {
		return "OrderBean [id=" + id + ", id_user=" + id_user + ", total=" + total + "]";
	}
	
}
