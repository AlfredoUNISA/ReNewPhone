package rnp.Support;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import me.xdrop.fuzzywuzzy.FuzzySearch;
import rnp.Bean.ProductBean;
import rnp.DAO.MethodsDAO;
import rnp.DAO.ProductDAODataSource;
import rnp.Servlet.VariousHelper;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet per gestire le richieste relate alla ricerca di prodotti in un database.
 */
@WebServlet("/search")
public class SearchControl extends HttpServlet implements VariousHelper {
	private static final long serialVersionUID = 1L;
	static ProductDAODataSource productDAO = new ProductDAODataSource();
	
	private static final String CLASS_NAME = SearchControl.class.getName();
	private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String query = request.getParameter("q");
		String sort = request.getParameter("sort");
		Collection<ProductBean> searchResults = new ArrayList<ProductBean>();

		// Esegui la ricerca approssimativa sui prodotti
		try {
			if (query != null && !query.isEmpty() && query.trim().length() > 0) {
				Collection<ProductBean> products = productDAO.doRetrieveAll(sort);
				for (ProductBean product : products) {
					// Calcola il punteggio di similarità tra la parola cercata e il nome del prodotto
					// https://github.com/xdrop/fuzzywuzzy
					int score = FuzzySearch.partialRatio(query.toLowerCase(), product.getName().toLowerCase());
					
					//System.out.println("Score tra (" + product.getName().toLowerCase() + ") e (" + query.toLowerCase() + ") = " + score);

					if (score >= 67) {
						searchResults.add(product);
					}
				}
			}
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, ANSI_RED + "ERROR [" + CLASS_NAME + "]: " + e.getMessage() + ANSI_RESET);
		}

		// Aggiungi i risultati della ricerca come attributo della richiesta
		request.setAttribute("searchResults", searchResults);
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/SearchPage.jsp"); // MODIFICABILE
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
