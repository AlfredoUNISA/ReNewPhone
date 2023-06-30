package rnpServlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import rnpBean.CartBean;
import rnpBean.ItemOrderBean;
import rnpBean.OrderBean;
import rnpBean.ProductBean;
import rnpDAO.CartDAODataSource;
import rnpDAO.ItemsOrderDAODataSource;
import rnpDAO.OrderDAODataSource;
import rnpDAO.ProductDAODataSource;

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

		HttpSession session = request.getSession();
		Object session_obj = session.getAttribute("user");
		int id_user = -1;
		if (session_obj != null)
			id_user = (int) session_obj;

		String productParam = request.getParameter("product");
		int id_product = -1;
		if (productParam != null) {
			id_product = Integer.parseInt(productParam);
		}

		// Esegui azioni opzionali
		if (action != null) {
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
			case "finalize":
				finalizeOrder(request, response, id_user);
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
				System.out.println("**404** Cart row not found for showRowDetails (id_user = " + id_user
						+ ", id_product = " + id_product + ")");
			}
		} catch (SQLException e) {
			System.out.println("ERROR: " + e);
		}
	}

	/**
	 * Aggiunge una nuova riga alla table del database.
	 */
	private void addRow(HttpServletRequest request, HttpServletResponse response, int id_user, int id_product)
			throws ServletException, IOException {
		int quantity = Integer.parseInt(request.getParameter("quantity"));
			
		/*
		if (id_user == -1) {
			// Utente non registrato, gestisci il carrello nel cookie

			// Prendi il cookie "cartCookie" dalla richiesta
			Cookie[] cookies = request.getCookies();
			Cookie cartCookie = null;
			if (cookies != null) {
				for (Cookie cookie : cookies) {
					if (cookie.getName().equals("cartCookie")) {
						cartCookie = cookie;
						break;
					}
				}
			}

			// Crea o aggiorna il carrello nel cookie
			if (cartCookie == null) {
				// Il cookie "cartCookie" non esiste, crea un nuovo carrello e aggiungi
				// l'oggetto al carrello esistente
				cartCookie = new Cookie("cartCookie", id_product + ":" + quantity);
			} else {
				// Il cookie "cartCookie" esiste, aggiungi l'oggetto al carrello esistente
				String cartValue = cartCookie.getValue();
				cartValue += "a" + id_product + ":" + quantity;
				cartCookie.setValue(cartValue);
			}

			// Aggiorna il tempo di vita del cookie (opzionale)
			cartCookie.setMaxAge(Login.COOKIE_DURATION); // Imposta il tempo di vita del cookie (in secondi)

			// Aggiungi il cookie alla risposta
			response.addCookie(cartCookie);
		} else {
			*/
			// Utente registrato
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
		//}
	}

	/**
	 * Elimina una riga dalla table del database.
	 */
	private void deleteRow(HttpServletRequest request, HttpServletResponse response, int id_user, int id_product)
			throws ServletException, IOException {

		try {
			if (!cartDAO.doDeleteSingleRow(id_user, id_product)) {
				System.out.println("**404** Cart row not found for showRowDetails (id_user = " + id_user
						+ ", id_product = " + id_product + ")");
			}
		} catch (SQLException e) {
			System.out.println("ERROR: " + e);
			if (e instanceof java.sql.SQLIntegrityConstraintViolationException) {
				// TODO: fare qualcosa se delle tabelle sono dipendenti da certi valori in
				// questa riga da eliminare
			}
		}
	}

	/**
	 * Finalizza l'ordine mettendo tutti gli oggetti del carrello in un ordine e
	 * rimuove tutti gli elementi del carrello.
	 */
	private void finalizeOrder(HttpServletRequest request, HttpServletResponse response, int id_user)
			throws ServletException, IOException {
		try {
			Collection<CartBean> cart = (Collection<CartBean>) cartDAO.doRetrieveByUser(id_user, null);

			String totalParam = request.getParameter("total");
			int total = 0;
			if (totalParam != null) {
				total = Integer.parseInt(totalParam);
			}

			if (cart != null && cart.size() != 0) {
				OrderDAODataSource orderDAO = new OrderDAODataSource();
				ItemsOrderDAODataSource itemsOrderDAO = new ItemsOrderDAODataSource();
				ProductDAODataSource productDAO = new ProductDAODataSource();

				Iterator<?> checkIter = cart.iterator();
				while (checkIter.hasNext()) {
					CartBean cart_bean = (CartBean) checkIter.next();
					ProductBean productCheck = productDAO.doRetrieveByKey(cart_bean.getId_product());

					if (productCheck.getQuantity() < cart_bean.getQuantity())
						throw new SQLException("Not Enough in stock for " + productCheck.getName() + " ("
								+ productCheck.getQuantity() + " in stock, " + cart_bean.getQuantity() + " asked).");
				}

				OrderBean order_bean = new OrderBean();
				order_bean.setId_user(id_user);
				order_bean.setTotal(total);

				int generatedOrderId = orderDAO.doSave(order_bean);

				Iterator<?> it = cart.iterator();
				while (it.hasNext()) {
					CartBean cart_bean = (CartBean) it.next();

					ItemOrderBean item_order_bean = new ItemOrderBean();
					item_order_bean.setId_order(generatedOrderId);
					item_order_bean.setId_product(cart_bean.getId_product());
					item_order_bean.setQuantity(cart_bean.getQuantity());

					itemsOrderDAO.doSave(item_order_bean);
				}

				cartDAO.doDelete(id_user);
			}
		} catch (SQLException e) {
			System.out.println("ERROR: " + e);
			// Fare qualcosa se non ci sono abbastanza oggetti in stock
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}