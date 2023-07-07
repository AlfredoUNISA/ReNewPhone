package rnp.Bean;

import java.io.Serializable;

/**
 * Bean che contiene tutte le informazioni di una riga della tabella "order_items" del database.
 */
public class ItemOrderBean implements Serializable {
	private static final long serialVersionUID = 1L;

	int id;
	int id_order;
	int ordered_quantity;
	ProductBean productBean;
    
	public ItemOrderBean() {
		id = -1;
		id_order = 0;
		ordered_quantity = 0;
		productBean = null;
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

	public int getOrderedQuantity() {
		return ordered_quantity;
	}
	public void setOrderedQuantity(int quantity) {
		this.ordered_quantity = quantity;
	}

	public int getId_product() {
		return productBean.getId();
	}
	public void setId_product(int id_product) {
		productBean.setId(id_product);
	}

	public int getRam() {
		return productBean.getRam();
	}
	public void setRam(int ram) {
		productBean.setRam(ram);
	}

	public String getName() {
		return productBean.getName();
	}
	public void setName(String name) {
		productBean.setName(name);
	}

	public float getDisplaySize() {
		return productBean.getDisplay_size();
	}
	public void setDisplaySize(float displaySize) {
		productBean.setDisplay_size(displaySize);
	}

	public int getStorage() {
		return productBean.getStorage();
	}
	public void setStorage(int storage) {
		productBean.setStorage(storage);
	}

	public int getPrice() {
		return productBean.getPrice();
	}
	public void setPrice(int price) {
		productBean.setPrice(price);
	}

	public String getColor() {
		return productBean.getColor();
	}
	public void setColor(String color) {
		productBean.setColor(color);
	}

	public String getBrand() {
		return productBean.getBrand();
	}
	public void setBrand(String brand) {
		productBean.setBrand(brand);
	}

	public int getYear() {
		return productBean.getYear();
	}
	public void setYear(int year) {
		productBean.setYear(year);
	}

	public String getCategory() {
		return productBean.getCategory();
	}
	public void setCategory(String category) {
		productBean.setCategory(category);
	}

	public String getState() {
		return productBean.getState();
	}
	public void setState(String state) {
		productBean.setState(state);
	}
	
	public ProductBean getProductBean() {
		return productBean;
	}
	public void setProductBean(ProductBean productBean) {
		this.productBean = productBean;
	}

	@Override
	public String toString() {
		return "ItemOrderBean [id=" + id + ", id_order=" + id_order + ", ordered_quantity=" + ordered_quantity
				+ ", productBean=" + productBean + "]";
	}
	
}
