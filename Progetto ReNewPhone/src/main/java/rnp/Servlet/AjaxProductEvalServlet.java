package rnp.Servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import rnp.DAO.ProductDAODataSource;

/**
 * Servlet implementation class AjaxProductEvalServlet
 */
@WebServlet("/AjaxProductEvalServlet")
public class AjaxProductEvalServlet extends HttpServlet implements ServletHelper {
	private static final long serialVersionUID = 1L;
	private static ProductDAODataSource productDAO = new ProductDAODataSource();
	
	private static final String CLASS_NAME = AjaxProductEvalServlet.class.getName();
	private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String action= request.getParameter("action");
			switch(action) {
			case "getBrands":
					handleBrands(request, response);
				break;
			case "getModel":
				
			break;
			}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	private void handleBrands(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			
			Gson gson = new GsonBuilder().create();
			String json = gson.toJson(productDAO.doRetrieveBrands());
			
			sendJsonResponse(response, json);
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "ERROR [" + CLASS_NAME + "]: " + e.getMessage());
		}
		
	}

}