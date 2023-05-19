package rnp;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet per gestire le richieste relate alla manipolazione dei dati di un database.
 */
@WebServlet("/orders")
public class OrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static OrderDAODataSource orderDAO = new OrderDAODataSource();

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
			request.removeAttribute("orders");
			request.setAttribute("orders", orderDAO.doRetrieveAll(sort));
			request.getServletContext().getRequestDispatcher("/OrderView.jsp").forward(request, response);
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
			OrderBean order = orderDAO.doRetrieveByKey(id);
			if (order != null) {
				request.removeAttribute("order-details");
				request.setAttribute("order-details", order);
			} else {
				System.out.println("**404** Order not found for showRowDetails (id_order = " + id + ")");
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
		int id_user = Integer.parseInt(request.getParameter("id_user"));
		int total = Integer.parseInt(request.getParameter("total"));

		// MODIFICABILE
		OrderBean order = new OrderBean();
		order.setId_user(id_user);
		order.setTotal(total);

		try {
			orderDAO.doSave(order);
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
	private void deleteRow(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));

		try {
			if (!orderDAO.doDelete(id)) {
				System.out.println("**404** Order not found for deleteRow (id_order = " + id + ")");
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
