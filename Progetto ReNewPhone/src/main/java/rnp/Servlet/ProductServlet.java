package rnp.Servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
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
public class ProductServlet extends HttpServlet implements VariousHelper {
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
			List<ProductBean> list = (List<ProductBean>) productDAO.doRetrieveByName(name);
			String json = gson.toJson(list);
			
			request.setAttribute("product-details-list", list);
			request.setAttribute("product-details-json", json); // Verrà utilizzato nel js
			request.getServletContext().getRequestDispatcher("/ProductDetails.jsp").forward(request, response);
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, ANSI_RED + "ERROR [" + CLASS_NAME + "]: " + e.getMessage() + ANSI_RESET);
		}
	}

	/**
	 * Elimina una riga dalla table del database.
	 * @category DELETE
	 */
	/*
	private void deleteRow(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));

		try {
			if (!productDAO.doDelete(id)) {
				LOGGER.log(Level.WARNING, ANSI_YELLOW + "WARNING [" + CLASS_NAME + "]: Product not found for deleteRow (id_product = " + id + ")" + ANSI_RESET);
			}
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, ANSI_RED + "ERROR [" + CLASS_NAME + "]: " + e.getMessage() + ANSI_RESET);
			if (e instanceof java.sql.SQLIntegrityConstraintViolationException) {
				// TODO: fare qualcosa se delle tabelle sono dipendenti da certi valori in
				// questa riga da eliminare
			}
		}
	}
	*/
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
