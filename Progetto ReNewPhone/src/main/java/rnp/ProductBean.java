package rnp;

import java.io.Serializable;

/**
 * Bean che contiene tutte le informazioni di un prodotto.
 * @category Bean
 * @category MODIFICABILE (tutto)
 */
public class ProductBean implements Serializable {

	private static final long serialVersionUID = 1L;

	int id;
    String name;
    String description;
    int price;
    int quantity;
    String color;
    String brand;
    String category;
    String state;
    int year;

	public ProductBean() {
		id = -1;
		name = "";
		description = "";
		price = 0;
		quantity = 0;
		color = "";
		brand = "";
		category = "";
		state = "";
		year=0;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}

	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}

	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "ProductBean [id=" + id + ", name=" + name + ", description=" + description + ", price=" + price
				+ ", quantity=" + quantity + ", color=" + color + ", brand=" + brand + ", category=" + category
				+ ", state=" + state + ", year=" + year + "]";
	}


}
