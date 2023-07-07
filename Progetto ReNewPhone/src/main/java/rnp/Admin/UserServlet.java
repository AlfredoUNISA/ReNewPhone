package rnp.Admin;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import rnp.Bean.UserBean;
import rnp.DAO.UserDAODataSource;
import rnp.Servlet.VariousHelper;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/users")
public class UserServlet extends HttpServlet implements VariousHelper {
	private static final long serialVersionUID = 1L;
	private static UserDAODataSource userDAO = new UserDAODataSource();
	
	private static final String CLASS_NAME = UserServlet.class.getName();
	private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);

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
			request.removeAttribute("users");
			request.setAttribute("users", userDAO.doRetrieveAll(sort));
			request.getServletContext().getRequestDispatcher("/UserView.jsp").forward(request, response);
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, ANSI_RED + "ERROR [" + CLASS_NAME + "]: " + e.getMessage() + ANSI_RESET);
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
			UserBean user = userDAO.doRetrieveByKey(id);
			if (user != null) {
				request.removeAttribute("user-details");
				request.setAttribute("user-details", user);
			} else {
				LOGGER.log(Level.WARNING, ANSI_YELLOW + "WARNING [" + CLASS_NAME + "]: User not found for showRowDetails (id_user = " + id + ")" + ANSI_RESET);
			}
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, ANSI_RED + "ERROR [" + CLASS_NAME + "]: " + e.getMessage() + ANSI_RESET);
		}
	}

	/**
	 * Aggiunge una nuova riga alla table del database.
	 */
	private void addRow(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// MODIFICABILE
		String name = request.getParameter("name");
		String surname = request.getParameter("surname");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String address = request.getParameter("address");
		String city = request.getParameter("city");
		String cap = request.getParameter("cap");
		String phone = request.getParameter("phone");

		// MODIFICABILE
		UserBean user = new UserBean();
		user.setName(name);
		user.setSurname(surname);
		user.setEmail(email);
		user.setPassword(password);
		user.setAddress(address);
		user.setCity(city);
		user.setCap(cap);
		user.setPhone(phone);

		try {
			userDAO.doSave(user);
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, ANSI_RED + "ERROR [" + CLASS_NAME + "]: " + e.getMessage() + ANSI_RESET);
			if (e instanceof java.sql.SQLIntegrityConstraintViolationException) {
				// TODO: fare qualcosa se ci sono duplicati
			}
		}
	}

	/**
	 * Elimina una riga dalla table del database.
	 */
	private void deleteRow(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));

		try {
			if (!userDAO.doDelete(id)) {
				LOGGER.log(Level.WARNING, ANSI_YELLOW + "WARNING [" + CLASS_NAME + "]: User not found for deleteRow (id_user = " + id + ")" + ANSI_RESET);
			}
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, ANSI_RED + "ERROR [" + CLASS_NAME + "]: " + e.getMessage() + ANSI_RESET);
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
