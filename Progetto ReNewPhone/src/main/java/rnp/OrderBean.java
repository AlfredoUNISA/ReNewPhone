package rnp;

import java.io.Serializable;

/**
 * Bean che contiene tutte le informazioni di un prodotto.
 * @category Bean
 * @category MODIFICABILE (tutto)
 */
public class OrderBean implements Serializable {

	private static final long serialVersionUID = 1L;

	int id;
    int id_user;
    int id_product;
    int quantity;
    
	public OrderBean() {
		id = -1;
		id_user = 0;
		id_product = 0;
		quantity = 0;
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

	public int getId_product() {
		return id_product;
	}
	public void setId_product(int id_product) {
		this.id_product = id_product;
	}

	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "OrderBean [id=" + id + ", id_user=" + id_user + ", id_product=" + id_product + ", quantity=" + quantity
				+ "]";
	}
	
}
