package rnp;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CartServlet
 */
@WebServlet("/my-cart")
public class CartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static CartDAODataSource cartDAO = new CartDAODataSource();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		String sort = request.getParameter("sort");
		
		String userParam = request.getParameter("user");
		int id_user = -1;
		if (userParam != null) {
			id_user = Integer.parseInt(userParam); 
		}
		
		String productParam = request.getParameter("product");
		int id_product = -1;
		if (productParam != null) {
			id_product = Integer.parseInt(productParam); 
		}

		// Esegui azioni opzionali
		if (action != null && id_product != -1) {
			switch (action) {
			case "details":
				showRowDetails(request, response, id_user, id_product);
				break;
			case "add":
				addRow(request, response, id_user, id_product);
				break;
			case "delete":
				deleteRow(request, response, id_user, id_product);
				break;
			default:
				response.sendRedirect(request.getContextPath());
				break;
			}
		}

		// Ricarica tutte le righe e forward alla jsp
		showAllRows(request, response, id_user, sort);
	}

	/**
	 * Mostra tutte le righe all'interno della tabella principale. Incaricato di
	 * fare il forward verso la jsp.
	 * 
	 * @param sort Specifica l'ordine di ordinamento dei risultati
	 */
	private void showAllRows(HttpServletRequest request, HttpServletResponse response, int usr, String sort)
			throws ServletException, IOException {
		try {
			// MODIFICABILE
			request.removeAttribute("cart");
			request.setAttribute("cart", cartDAO.doRetrieveByUser(usr, sort));
			request.getServletContext().getRequestDispatcher("/CartView.jsp").forward(request, response);
		} catch (SQLException e) {
			System.out.println("ERROR: " + e);
		}
	}

	/**
	 * Mostra i dettagli di una riga all'interno della tabella "Dettagli".
	 */
	private void showRowDetails(HttpServletRequest request, HttpServletResponse response, int id_user, int id_product)
			throws ServletException, IOException {
		
		try {
			// MODIFICABILE
			CartBean cart = cartDAO.doRetrieveByPrimaryKeys(id_user, id_product);
			if (cart != null) {
				request.removeAttribute("cart-details");
				request.setAttribute("cart-details", cart);
			} else {
				System.out.println("**404** Cart row not found for showRowDetails (id_user = " + id_user + ", id_product = " + id_product + ")");
			}
		} catch (SQLException e) {
			System.out.println("ERROR: " + e);
		}
	}

	/**
	 * Aggiunge una nuova riga alla table del database.
	 */
	private void addRow(HttpServletRequest request, HttpServletResponse response, int id_user, int id_product) throws ServletException, IOException {
		// MODIFICABILE
		int quantity = Integer.parseInt(request.getParameter("quantity"));

		// MODIFICABILE
		CartBean cart = new CartBean();
		cart.setId_user(id_user);
		cart.setId_product(id_product);
		cart.setQuantity(quantity);

		try {
			cartDAO.doSave(cart);
		} catch (SQLException e) {
			System.out.println("ERROR: " + e);
			if (e instanceof java.sql.SQLIntegrityConstraintViolationException) {
				// TODO: fare qualcosa se ci sono duplicati
			}
		}
	}

	/**
	 * Elimina una riga dalla table del database.
	 */
	private void deleteRow(HttpServletRequest request, HttpServletResponse response, int id_user, int id_product)
			throws ServletException, IOException {

		try {
			if (!cartDAO.doDeleteSingleRow(id_user, id_product)) {
				System.out.println("**404** Cart row not found for showRowDetails (id_user = " + id_user + ", id_product = " + id_product + ")");
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
