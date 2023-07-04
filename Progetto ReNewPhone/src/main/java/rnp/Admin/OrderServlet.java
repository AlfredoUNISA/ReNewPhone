package rnp.Admin;

import java.io.IOException;
import java.sql.SQLException;
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

import rnp.Bean.OrderBean;
import rnp.DAO.OrderDAODataSource;
import rnp.Support.Login;

/**
 * Servlet per gestire le richieste relate alla manipolazione dei dati di un
 * database.
 */
@WebServlet("/orders")
public class OrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static OrderDAODataSource orderDAO = new OrderDAODataSource();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (!checkForAdmin(request, response)) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			System.out.println("test");
			return;
		}
		
		int page = Integer.parseInt(request.getParameter("page"));
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));

        // Calcola l'indice di inizio e fine per gli ordini
        int startIndex = (page - 1) * pageSize;
        int endIndex = startIndex + pageSize;

        // Recupera gli ordini dal database utilizzando OrderDAODataSource
        List<OrderBean> orders = null;
		try {
			orders = (List<OrderBean>) orderDAO.doRetrieveAll("id DESC");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
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
        
        response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse);
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
