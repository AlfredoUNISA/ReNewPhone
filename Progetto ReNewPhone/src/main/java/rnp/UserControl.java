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
@WebServlet("/users")
public class UserControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// MODIFICABILE
	static IBeanDAO<UserBean> userDAO = new UserDAODataSource();
	
	public UserControl() {
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
					 * richiama il metodo "doRetrieveByKey()" per OTTENERE l'user con l'ID specificato.
					 */
					int id = Integer.parseInt(request.getParameter("id")); // MODIFICABILE
					
					// MODIFICABILE
					request.removeAttribute("user");
					request.setAttribute("user", userDAO.doRetrieveByKey(id));
				} 
				else if (action.equalsIgnoreCase("delete")) {
					/* 
					 * Se l'azione è "delete", il servlet legge il parametro "id" dalla richiesta e 
					 * richiama il metodo "doDelete()" per ELIMINARE l'utente con l'ID specificato. 
					 */
					int id = Integer.parseInt(request.getParameter("id")); // MODIFICABILE
					userDAO.doDelete(id);
				} 
				else if (action.equalsIgnoreCase("insert")) {
					/* 
					 * Se l'azione è "insert", il servlet legge i parametri "name", "email", "password", "address"... 
					 * dalla richiesta e crea un nuovo oggetto UserBean con questi valori. 
					 * Quindi richiama il metodo "doSave()" per SALVARE il nuovo utente nel database. 
					 */
					// MODIFICABILE
					String name = request.getParameter("name");
					String email = request.getParameter("email");
					String password = request.getParameter("password");
					String address = request.getParameter("address");
					String city = request.getParameter("city");
					String cap = request.getParameter("cap");
					String phone = request.getParameter("phone");


					// MODIFICABILE
					UserBean bean = new UserBean();
					bean.setName(name);
					bean.setEmail(email);
					bean.setPassword(password);
					bean.setAddress(address);
					bean.setCity(city);
					bean.setCap(cap);
					bean.setPhone(phone);

					
					userDAO.doSave(bean);
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
			request.removeAttribute("users");
			request.setAttribute("users", userDAO.doRetrieveAll(sort));
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		}

		/*
		 * Imposta la lista di prodotti come attributo della richiesta HTTP e 
		 * reindirizza la richiesta alla pagina JSP "ProductView.jsp".
		 */
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/UserView.jsp"); // MODIFICABILE
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
