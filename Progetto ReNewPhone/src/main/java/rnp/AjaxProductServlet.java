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
		int productsPerLoading = 12;
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
			// Prendi tutti i prodotti
			LinkedList<ProductBean> listProducts = (LinkedList<ProductBean>) productDAO.doRetrieveAll(null);

			// Crea un Array List con i prodotti risultanti
			Collection<ProductBean> resultProducts = new ArrayList<>();

			// TODO: Implementare la mappa

			for (int i = (productsPerLoading * countLoadings); i < (productsPerLoading * (countLoadings + 1)); i++) {
				resultProducts.add(listProducts.get(i));
			}

			// System.out.println("size of listProducts: " + listProducts.size());
			// System.out.println("size of resultProducts: " + resultProducts.size());

			// Gson consente la serializzazione e deserializzazione di oggetti Java in
			// formato JSON e viceversa
			Gson gson = new Gson();
			JsonElement json = gson.toJsonTree(resultProducts);

			// Invia gli n (productsPerLoading) elementi alla pagina jsp come JSON
			request.removeAttribute("productsJson");
			request.setAttribute("productsJson", json);
			// System.out.println(json);

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");

			// Scrivi il JSON come risposta
			response.getWriter().write(json.toString());

		} catch (SQLException e) {
			System.out.println("ERROR: " + e);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
