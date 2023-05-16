package rnp;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SearchControl
 */
@WebServlet("/search")
public class SearchControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static IBeanDAO<ProductBean> productDAO = new ProductDAODataSource();
	
	
    public SearchControl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Parametro della richiesta HTTP
		String searchTerm = request.getParameter("searchTerm");
		List<ProductBean> productBeans = new ArrayList<>();
		List<ProductBean> searchResults = new ArrayList<>();

		
		// Esegui la ricerca approssimativa sui prodotti
		if (searchTerm != null && searchTerm.trim().length() > 0) {
			Collection<?> productsColl = (Collection<?>) request.getAttribute("products");
			
			// Aggiungi ogni elemento della collezione alla lista di ProductBean
			for (Object product : productsColl) {
				productBeans.add((ProductBean) product);
			}
			
		    for (ProductBean product : productBeans) {
		        // Calcola la distanza di Levenshtein tra la parola cercata e il nome del prodotto
		        int distance = levenshteinDistance(product.getName().toLowerCase(), searchTerm.toLowerCase());

		        // Considera i prodotti con una distanza di Levenshtein <= 2 come corrispondenze
		        if (distance <= 2) {
		            searchResults.add(product);
		        }
		    }
		    
		    // Aggiungi i risultati della ricerca come attributo della richiesta
		    request.setAttribute("searchResults", searchResults);
		}
		
		/*
		 * Legge il parametro "sort" dalla richiesta e richiama il metodo "doRetrieveAll()" 
		 * per recuperare tutti i prodotti dal database e ordinarli in base al valore del parametro "sort". 
		 */
		String sort = request.getParameter("sort");

		try {
			request.removeAttribute("products");
			request.setAttribute("products", productDAO.doRetrieveAll(sort));
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		}

		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/SearchPage.jsp"); // MODIFICABILE
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	// Funzione per la ricerca approssimativa usando l'algoritmo di Levenshtein
	public static int levenshteinDistance(String s, String t) {
	    int m = s.length();
	    int n = t.length();
	    int[][] distance = new int[m + 1][n + 1];

	    for (int i = 0; i <= m; i++) {
	        distance[i][0] = i;
	    }

	    for (int j = 0; j <= n; j++) {
	        distance[0][j] = j;
	    }

	    for (int j = 1; j <= n; j++) {
	        for (int i = 1; i <= m; i++) {
	            if (s.charAt(i - 1) == t.charAt(j - 1)) {
	                distance[i][j] = distance[i - 1][j - 1];
	            } else {
	                distance[i][j] = Math.min(distance[i - 1][j], Math.min(distance[i][j - 1], distance[i - 1][j - 1])) + 1;
	            }
	        }
	    }

	    return distance[m][n];
	}
}
