package rnpSupport;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

import me.xdrop.fuzzywuzzy.FuzzySearch;
import rnpBean.ProductBean;
import rnpDAO.MethodsDAO;
import rnpDAO.ProductDAODataSource;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet per gestire le richieste relate alla ricerca di prodotti in un database.
 * @category Servlet
 */
@WebServlet("/search")
public class SearchControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static MethodsDAO<ProductBean> productDAO = new ProductDAODataSource();

	public SearchControl() {
		super();
	}

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
				/*
				for (ProductBean product : searchResults) {
					System.out.println("------");
				    System.out.println(product);
				}
				*/
			}
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
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
