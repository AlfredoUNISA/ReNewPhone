package rnp;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

/**
 * Servlet implementation class AjaxProductServlet
 */
@WebServlet("/AjaxProductServlet")
public class AjaxProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static ProductDAODataSource productDAO = new ProductDAODataSource();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int productsPerLoading = 12; // Default
		int countLoadings = 0;

		if (request.getParameter("productsPerLoading") != null) {
			productsPerLoading = Integer.parseInt(request.getParameter("productsPerLoading"));
		}
		System.out.println("productsPerLoading: " + countLoadings);
		
		if (request.getParameter("countLoadings") != null) {
			countLoadings = Integer.parseInt(request.getParameter("countLoadings"));
		}
		System.out.println("countLoadings: " + countLoadings);

		try {
			// Prendi tutti i prodotti dal database
			LinkedList<ProductBean> listProducts = (LinkedList<ProductBean>) productDAO.doRetrieveAll(null);

			// Crea un Array List con i prodotti risultanti
			Collection<ProductBean> resultProducts = new ArrayList<>();

			// TODO: Implementare la mappa

			// Aggiungi i prodotti alla lista risultante
			for (int i = (productsPerLoading * countLoadings); i < (productsPerLoading * (countLoadings + 1)); i++) {
				resultProducts.add(listProducts.get(i));
			}

			// Gson consente la serializzazione e deserializzazione di oggetti Java in formato JSON e viceversa
			Gson gson = new Gson();
			
			// Scrivi il JSON come risposta
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(gson.toJsonTree(resultProducts).toString());

		} catch (SQLException e) {
			System.out.println("ERROR: " + e);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
