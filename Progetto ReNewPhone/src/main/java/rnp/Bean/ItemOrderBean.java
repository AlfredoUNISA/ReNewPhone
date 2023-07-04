package rnp.Bean;

import java.io.Serializable;

/**
 * Bean che contiene tutte le informazioni di una riga della tabella "order_items" del database.
 */
public class ItemOrderBean implements Serializable {
	private static final long serialVersionUID = 1L;

	int id;
	int id_order;
	int id_product;
	int quantity;
    
	public ItemOrderBean() {
		id = -1;
		id_order = 0;
		id_product = 0;
		quantity = 0;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public int getId_order() {
		return id_order;
	}
	public void setId_order(int id_order) {
		this.id_order = id_order;
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
		return "OrderItemBean [id=" + id + ", id_order=" + id_order + ", id_product=" + id_product + ", quantity="
				+ quantity + "]";
	}
	
}
