package rnp.Servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import rnp.Bean.ProductBean;
import rnp.DAO.ProductDAODataSource;

/**
 * Servlet implementation class ProductServlet
 */
@WebServlet("/products")
public class ProductServlet extends HttpServlet implements ServletHelper {
	private static final long serialVersionUID = 1L;
	private static ProductDAODataSource productDAO = new ProductDAODataSource();
	
	private static final String CLASS_NAME = ProductServlet.class.getName();
	private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");

		// Esegui azioni opzionali
		if (action != null) {
			switch (action) {
			case "details":
				showRowDetails(request, response);
				break;
			case "delete":
				// TODO: da vedere se un utente è autorizzato a cancellare un prodotto
				deleteRow(request, response);
				break;
			default:
				response.sendError(HttpServletResponse.SC_NOT_FOUND, "Pagina non trovata");
				break;
			}
		}
		else {
			request.getServletContext().getRequestDispatcher("/ProductView.jsp").forward(request, response);
		}
	}

	/**
	 * Mostra i dettagli di una riga all'interno della jsp "ProductDetails".
	 * @category SELECT
	 */
	private void showRowDetails(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String name = request.getParameter("name");
		
		try {
			//request.removeAttribute("product-details");
			Gson gson = new GsonBuilder().create();
			String json = gson.toJson(productDAO.doRetrieveByName(name));
			
			request.setAttribute("product-details", json.substring(0, json.length()));
			request.getServletContext().getRequestDispatcher("/ProductDetails.jsp").forward(request, response);
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "ERROR [" + CLASS_NAME + "]: " + e.getMessage());
		}
	}

	/**
	 * Elimina una riga dalla table del database.
	 * @category DELETE
	 */
	private void deleteRow(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));

		try {
			if (!productDAO.doDelete(id)) {
				LOGGER.log(Level.WARNING, "ERROR [" + CLASS_NAME + "]: Product not found for deleteRow (id_product = " + id + ")");
			}
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "ERROR [" + CLASS_NAME + "]: " + e.getMessage());
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
