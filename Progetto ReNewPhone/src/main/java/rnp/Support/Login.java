package rnp.Support;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import rnp.DAO.UserDAODataSource;
import rnp.Servlet.ServletHelper;

/**
 * Servlet implementation class Login
 */
@WebServlet("/login")
public class Login extends HttpServlet implements ServletHelper{
	private static final long serialVersionUID = 1L;
	
	private static final String CLASS_NAME = Login.class.getName();
	private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);
	
	/**
	 * Utilizzato per autentificare l'utente, creare una sessione e un cookie.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String email = request.getParameter("email");
		String password = request.getParameter("password");

		UserDAODataSource userDAO = new UserDAODataSource();
		int id = -1;

		try {
			id = userDAO.doRetrieveByCredentials(email, password);
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "ERROR [" + CLASS_NAME + "]: " + e.getMessage());
		}

		if (id == -1) {
			// Errore con le credenziali
			response.sendRedirect("login.jsp");
		} else {
			// Accesso consentito
			HttpSession session = request.getSession();
			session.setAttribute("user", id);

			// Imposta un cookie persistente per l'identificazione futura dell'utente
			Cookie userCookie = new Cookie("userCookie", Integer.toString(id));
			userCookie.setMaxAge(COOKIE_DURATION);
			response.addCookie(userCookie);

			response.sendRedirect("index.jsp");
		}

	}
	
	/**
	 * @return true se l'id è di un admin, false altrimenti
	 */
	public static Boolean isAdmin(int id) {
		if (id == -10)
			return true;
		else
			return false;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
}
