package rnp;

import java.io.Serializable;

/**
 * Bean che contiene tutte le informazioni di un prodotto.
 * @category Bean
 * @category MODIFICABILE (tutto)
 */
public class UserBean implements Serializable {

	private static final long serialVersionUID = 1L;

	int id;
    String name;
    String email;
    String password;
    String address;
    String city;
    String cap;
    String phone;
    
	public UserBean() {
		id = -1;
		name = "";
		email = "";
		password = "";
		address = "";
		city = "";
		cap = "";
		phone = "";
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

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}

	public String getCap() {
		return cap;
	}
	public void setCap(String cap) {
		this.cap = cap;
	}

	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "UserBean [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", address="
				+ address + ", city=" + city + ", cap=" + cap + ", phone=" + phone + "]";
	}
	

}
