package rnp.Admin;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
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
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import rnp.Bean.ItemOrderBean;
import rnp.Bean.OrderBean;
import rnp.Bean.ProductBean;
import rnp.Bean.UserBean;
import rnp.DAO.ItemsOrderDAODataSource;
import rnp.DAO.UserDAODataSource;
import rnp.Servlet.VariousHelper;

/**
 * Servlet per gestire le richieste relate alla manipolazione dei dati di un
 * database.
 */
@WebServlet("/orderDetails")
public class OrderDetailsServlet extends HttpServlet implements VariousHelper {
	private static final long serialVersionUID = 1L;
	
	private static ItemsOrderDAODataSource itemsOrderDAO = new ItemsOrderDAODataSource();
	private static UserDAODataSource userDAO = new UserDAODataSource();
	
	private static final String CLASS_NAME = OrderDetailsServlet.class.getName();
	private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String idStr = request.getParameter("id");
		String id_userStr = request.getParameter("id_user");
		String totalStr = request.getParameter("total");

		int id = -1;
		int id_user = -1;
		int total = -1;

		boolean error = false;

		if (idStr != null || id_userStr != null || totalStr != null) {
			id = Integer.parseInt(idStr);
			id_user = Integer.parseInt(id_userStr);
			total = Integer.parseInt(totalStr);
		} else {
			error = true;
		}

		if (!error) {
			OrderBean order = new OrderBean();
			order.setId(id);
			order.setId_user(id_user);
			order.setTotal(total);

			// System.out.println(order);
			
			List<ItemOrderBean> itemsInsideOrder = null;
			try {
				itemsInsideOrder = (List<ItemOrderBean>) itemsOrderDAO.doRetrieveByOrder(order.getId());
			} catch (SQLException e) {
				LOGGER.log(Level.SEVERE, ANSI_RED + "ERROR [" + CLASS_NAME + "]: " + e.getMessage() + ANSI_RESET);
			}
			
			//System.out.println(itemsInsideOrder);
			
			// Crea le informazioni dell'utente da mandare
			UserBean user = null;
			try {
				user = userDAO.doRetrieveByKey(order.getId_user());
			} catch (SQLException e) {
				LOGGER.log(Level.SEVERE, ANSI_RED + "ERROR [" + CLASS_NAME + "]: " + e.getMessage() + ANSI_RESET);
			}
			
			List<ProductBean> products = new ArrayList<>();
			for (ItemOrderBean item : itemsInsideOrder) {
				ProductBean productDetails = item.getProductBean();

				// Utilizza la quantità di ProductBean come quantità ordinata di questo prodotto
				productDetails.setQuantity(item.getOrderedQuantity());
				
				products.add(productDetails);
			}
		
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
	        
	        String jsonProd = gson.toJson(products);
	        String jsonUser = gson.toJson(user);
	        
	        // Costruisci un oggetto JSON per includere anche il conteggio totale
	        JsonObject jsonResponse = new JsonObject();
	        jsonResponse.add("products", gson.fromJson(jsonProd, JsonElement.class));
	        jsonResponse.add("user", gson.fromJson(jsonUser, JsonElement.class));
			
	        sendJsonResponse(response, jsonResponse);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
