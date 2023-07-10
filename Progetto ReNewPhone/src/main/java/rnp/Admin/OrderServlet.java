package rnp.Admin;

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
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import rnp.Bean.OrderBean;
import rnp.DAO.OrderDAODataSource;
import rnp.Servlet.VariousHelper;
import rnp.Support.Login;

/**
 * Servlet per gestire le richieste relate alla manipolazione dei dati di un
 * database.
 */
@WebServlet("/orders")
public class OrderServlet extends HttpServlet implements VariousHelper {
	private static final long serialVersionUID = 1L;
	private static OrderDAODataSource orderDAO = new OrderDAODataSource();
	
	private static final String CLASS_NAME = OrderServlet.class.getName();
	private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		boolean isAdmin = checkForAdmin(request, response); 
		int page = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("pageSize"));

		// Calcola l'indice di inizio e fine per gli ordini
		int startIndex = (page - 1) * pageSize;
		int endIndex = startIndex + pageSize;
		
		// Recupera gli ordini dal database utilizzando OrderDAODataSource
        List<OrderBean> orders = null;
		try {
			if (isAdmin) {
				orders = (List<OrderBean>) orderDAO.doRetrieveAll("id DESC");				
			} else if(unregistered) {
				response.sendRedirect("index.jsp");
			} else {
				orders = (List<OrderBean>) orderDAO.doRetrieveByUser(user, "id DESC");
			}
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, ANSI_RED + "ERROR [" + CLASS_NAME + "]: " + e.getMessage() + ANSI_RESET);
		}
		//System.out.println(orders);

		// Costruisci una sotto-lista di ordini per la pagina corrente
        List<OrderBean> ordersForPage = orders.subList(startIndex, Math.min(endIndex, orders.size()));
        //System.out.println(ordersForPage);
        
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        
        String json = gson.toJson(ordersForPage);
        
        // Costruisci un oggetto JSON per includere anche il conteggio totale
        JsonObject jsonResponse = new JsonObject();
        jsonResponse.add("orders", gson.fromJson(json, JsonElement.class));
        jsonResponse.addProperty("totalCount", orders.size());
		
        //System.out.println(jsonResponse);
        sendJsonResponse(response, jsonResponse);
	}

	private int user = -1;
	private boolean unregistered = true;
	/**
	 * Controlla se l'utente attuale è admin.
	 * 
	 * @return true se è admin, false altrimenti
	 */
	private boolean checkForAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Object session_obj = request.getSession().getAttribute("currentUserId");
		int userId = -1;

		if (session_obj != null)
			userId = (int) session_obj;

		if(userId > 0)
			unregistered = false;
		user = userId;
	
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
