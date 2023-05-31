package rnp;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ProductServlet
 */
@WebServlet("/products") //	MODIFICABILE
public class ProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static ProductDAODataSource productDAO = new ProductDAODataSource();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		String sort = request.getParameter("sort");

		// Esegui azioni opzionali
		if (action != null) {
			switch (action) {
			case "details":
				showRowDetails(request, response);
				break;
			case "add":
				addRow(request, response);
				break;
			case "delete":
				deleteRow(request, response);
				break;
			default:
				response.sendRedirect(request.getContextPath());
				break;
			}
		}

		// Ricarica tutte le righe e forward alla jsp
		showAllRows(request, response, sort);
	}

	/**
	 * Mostra tutte le righe all'interno della tabella principale. Incaricato di
	 * fare il forward verso la jsp.
	 * 
	 * @param sort Specifica l'ordine di ordinamento dei risultati
	 */
	private void showAllRows(HttpServletRequest request, HttpServletResponse response, String sort)
			throws ServletException, IOException {
		try {
			// MODIFICABILE
			request.removeAttribute("products");
			request.setAttribute("products", productDAO.doRetrieveAll(sort));
			request.getServletContext().getRequestDispatcher("/ProductView.jsp").forward(request, response);
		} catch (SQLException e) {
			System.out.println("ERROR: " + e);
		}
	}

	/**
	 * Mostra i dettagli di una riga all'interno della tabella "Dettagli".
	 */
	private void showRowDetails(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		
		try {
			// MODIFICABILE
			ProductBean product = productDAO.doRetrieveByKey(id);
			if (product != null) {
				request.removeAttribute("product-details");
				request.setAttribute("product-details", product);
			} else {
				System.out.println("**404** Product not found for showRowDetails (id_product = " + id + ")");
			}
		} catch (SQLException e) {
			System.out.println("ERROR: " + e);
		}
	}

	/**
	 * Aggiunge una nuova riga alla table del database.
	 */
	private void addRow(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// MODIFICABILE
		String name = request.getParameter("name");
		int ram= Integer.parseInt(request.getParameter("ram"));
		float display_size= Float.parseFloat(request.getParameter("display_size"));
		int storage= Integer.parseInt(request.getParameter("storage"));
		int price = Integer.parseInt(request.getParameter("price"));
		int quantity = Integer.parseInt(request.getParameter("quantity"));
		String color = request.getParameter("color");
		String brand = request.getParameter("brand");
		int year = Integer.parseInt(request.getParameter("year"));
		String category = request.getParameter("category");
		String state = request.getParameter("state");

		// MODIFICABILE
		ProductBean product = new ProductBean();
		product.setName(name);
		product.setRam(ram);
		product.setDisplay_size(display_size);
		product.setStorage(storage);
		product.setPrice(price);
		product.setQuantity(quantity);
		product.setColor(color);
		product.setBrand(brand);
		product.setYear(year);
		product.setCategory(category);
		product.setState(state);

		try {
			productDAO.doSave(product);
		} catch (SQLException e) {
			System.out.println("ERROR: " + e);
		}
	}

	/**
	 * Elimina una riga dalla table del database.
	 */
	private void deleteRow(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));

		try {
			if (!productDAO.doDelete(id)) {
				System.out.println("**404** Product not found for deleteRow (id_product = " + id + ")");
			}
		} catch (SQLException e) {
			System.out.println("ERROR: " + e);
			if (e instanceof java.sql.SQLIntegrityConstraintViolationException) {
				// TODO: fare qualcosa se delle tabelle sono dipendenti da certi valori in
				// questa riga da eliminare
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
