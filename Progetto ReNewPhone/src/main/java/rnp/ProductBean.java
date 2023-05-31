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
    int ram;
    float display_size;
    int storage;
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
	
	public String getModel() {
		String model;
		model=name.substring(0);
		
		model=model.replace("Pro", "")
				.replace("Max", "")
				.replace("Mini", "")
				.replace("Plus","")
				.replace("Ultra", "")
				.replace("gen","")
				.replace("Lite","").strip();
		

		if(model.endsWith("a") || model.endsWith("s")) {
			model=model.substring(0,model.length()-1);
		}
		return model;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getPrice() {
		return price;
	}
	public int getRam() {
		return ram;
	}

	public void setRam(int ram) {
		this.ram = ram;
	}

	public float getDisplay_size() {
		return display_size;
	}

	public void setDisplay_size(float display_size) {
		this.display_size = display_size;
	}

	public int getStorage() {
		return storage;
	}

	public void setStorage(int storage) {
		this.storage = storage;
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
		return "ProductBean [id=" + id + ", name=" + name + ", price=" + price
				+ ", quantity=" + quantity + ", color=" + color + ", brand=" + brand + ", category=" + category
				+ ", state=" + state + ", year=" + year + "]";
	}


}
