package rnp.Admin;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
import rnp.DAO.OrderDAODataSource;
import rnp.DAO.ProductDAODataSource;
import rnp.DAO.UserDAODataSource;
import rnp.Support.Login;

/**
 * Servlet per gestire le richieste relate alla manipolazione dei dati di un
 * database.
 */
@WebServlet("/orderDetails")
public class OrderDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static ItemsOrderDAODataSource itemsOrderDAO = new ItemsOrderDAODataSource();
	private static ProductDAODataSource productDAO = new ProductDAODataSource();
	private static UserDAODataSource userDAO = new UserDAODataSource();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (!checkForAdmin(request, response)) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			System.out.println("Non admin");
			return;
		}

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
				System.out.println(e.getMessage());
			}
			
			//System.out.println(itemsInsideOrder);
			
			UserBean user = null;
			try {
				user = userDAO.doRetrieveByKey(order.getId_user());
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
			
			List<ProductBean> products = new ArrayList<>();
			for (ItemOrderBean item : itemsInsideOrder) {
				
				ProductBean productDetails = null;
				try {
					productDetails = productDAO.doRetrieveByKey(item.getId_product());
					productDetails.setQuantity(item.getQuantity());
				} catch (SQLException e) {
					System.out.println(e.getMessage());
				}
				products.add(productDetails);
			}
		
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
	        
	        String jsonProd = gson.toJson(products);
	        String jsonUser = gson.toJson(user);
	        
	        // Costruisci un oggetto JSON per includere anche il conteggio totale
	        JsonObject jsonResponse = new JsonObject();
	        jsonResponse.add("products", gson.fromJson(jsonProd, JsonElement.class));
	        jsonResponse.add("user", gson.fromJson(jsonUser, JsonElement.class));
			
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(jsonResponse);
			
		}
	}

	/**
	 * Controlla se l'utente attuale è admin.
	 * 
	 * @return true se è admin, false altrimenti
	 */
	private boolean checkForAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Object session_obj = request.getSession().getAttribute("user");
		int userId = -1;

		if (session_obj != null)
			userId = (int) session_obj;

		if (!Login.isAdmin(userId))
			return false;
		else
			return true;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
