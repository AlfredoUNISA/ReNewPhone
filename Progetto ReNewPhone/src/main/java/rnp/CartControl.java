package rnp;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet per gestire le richieste relate alla manipolazione dei dati di un
 * database.
 * 
 * @category Servlet
 * @category MODIFICABILE
 */
@WebServlet("/my-cart")
public class CartControl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// MODIFICABILE
	static CartDAODataSource cartDAO = new CartDAODataSource();

	public CartControl() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Parametro della richiesta HTTP
		String action = request.getParameter("action");
		String userParam = request.getParameter("usr");
		int id_user = -1; // Valore predefinito in caso di parametro null o non valido

		if (userParam != null) {
			id_user = Integer.parseInt(userParam);
		}

		try {
			if (action != null && id_user != -1) {
				if (action.equalsIgnoreCase("read")) {
					/*
					 * Se l'azione è "read", il servlet richiama il metodo "doRetrieveByUser()" per
					 * OTTENERE tutti gli oggetti del carrello dell'utente.
					 */

					// MODIFICABILE TODO : DA CAMBIARE
					
				} else if (action.equalsIgnoreCase("delete")) {
					/*
					 * Se l'azione è "delete", il servlet legge il parametro id_product e richiama
					 * il metodo "doDelete()" per ELIMINARE la riga specificata.
					 */
					int id_product = Integer.parseInt(request.getParameter("id_product")); // MODIFICABILE
					cartDAO.doDeleteSingleRow(id_user, id_product);
				} else if (action.equalsIgnoreCase("insert")) {
					/*
					 * Se l'azione è "insert", il servlet legge i parametri "name", "email",
					 * "password", "address"... dalla richiesta e crea un nuovo oggetto CartBean con
					 * questi valori. Quindi richiama il metodo "doSave()" per SALVARE il nuovo
					 * utente nel database.
					 */
					// MODIFICABILE
					int id_product_toInsert = Integer.parseInt(request.getParameter("id_product"));
					int quantity_of_product = Integer.parseInt(request.getParameter("quantity"));

					// MODIFICABILE
					CartBean bean = new CartBean();
					bean.setId_user(id_user);
					bean.setId_product(id_product_toInsert);
					bean.setQuantity(quantity_of_product);

					cartDAO.doSave(bean);
				}
			}
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
			System.out.println("Class:" + e.getClass());
		}

		/*
		 * Legge il parametro "sort" dalla richiesta e richiama il metodo
		 * "doRetrieveAll()" per recuperare tutti i prodotti dal database e ordinarli in
		 * base al valore del parametro "sort".
		 */

		String sort = request.getParameter("sort");

		try {
			// MODIFICABILE
			request.removeAttribute("cart");
			request.setAttribute("cart", cartDAO.doRetrieveByUser(id_user, sort));
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		}

		/*
		 * Imposta la lista di prodotti come attributo della richiesta HTTP e
		 * reindirizza la richiesta alla pagina JSP "ProductView.jsp".
		 */
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/CartView.jsp"); // MODIFICABILE
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
