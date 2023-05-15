package rnp;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet per gestire le richieste relatice alla manipolazione dei dati di un database.
 * @category Servlet
 * @category MODIFICABILE
 */
@WebServlet("/orders")
public class OrderControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// MODIFICABILE
	static IBeanDAO<OrderBean> orderDAO = new OrderDAODataSource();
	
	public OrderControl() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Parametro della richiesta HTTP
		String action = request.getParameter("action");

		try {
			if (action != null) {
				if (action.equalsIgnoreCase("read")) {
					/* 
					 * Se l'azione è "read", il servlet legge il parametro "id" dalla richiesta e 
					 * richiama il metodo "doRetrieveByKey()" per OTTENERE l'order con l'ID specificato.
					 */
					int id = Integer.parseInt(request.getParameter("id")); // MODIFICABILE
					
					// MODIFICABILE
					request.removeAttribute("order");
					request.setAttribute("order", orderDAO.doRetrieveByKey(id));
				} 
				else if (action.equalsIgnoreCase("delete")) {
					/* 
					 * Se l'azione è "delete", il servlet legge il parametro "id" dalla richiesta e 
					 * richiama il metodo "doDelete()" per ELIMINARE l'utente con l'ID specificato. 
					 */
					int id = Integer.parseInt(request.getParameter("id")); // MODIFICABILE
					orderDAO.doDelete(id);
				} 
				else if (action.equalsIgnoreCase("insert")) {
					/* 
					 * Se l'azione è "insert", il servlet legge i parametri "id_user", "id_product" e "quantity"
					 * dalla richiesta e crea un nuovo oggetto OrderBean con questi valori. 
					 * Quindi richiama il metodo "doSave()" per SALVARE il nuovo utente nel database. 
					 */
					// MODIFICABILE
					int id_user = Integer.parseInt(request.getParameter("id_user"));
					int id_product = Integer.parseInt(request.getParameter("id_product"));
					int quantity = Integer.parseInt(request.getParameter("quantity"));
					
					// MODIFICABILE
					OrderBean bean = new OrderBean();
					bean.setId_user(id_user);
					bean.setId_product(id_product);
					bean.setQuantity(quantity);
					
					orderDAO.doSave(bean);
				}
			}
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		}

		/*
		 * Legge il parametro "sort" dalla richiesta e richiama il metodo "doRetrieveAll()" 
		 * per recuperare tutti i prodotti dal database e ordinarli in base al valore del parametro "sort". 
		 */
		
		String sort = request.getParameter("sort");

		try {
			// MODIFICABILE
			request.removeAttribute("orders");
			request.setAttribute("orders", orderDAO.doRetrieveAll(sort));
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		}

		/*
		 * Imposta la lista di prodotti come attributo della richiesta HTTP e 
		 * reindirizza la richiesta alla pagina JSP "ProductView.jsp".
		 */
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/OrderView.jsp"); // MODIFICABILE
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
