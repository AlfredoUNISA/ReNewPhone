package rnp.Servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import rnp.Bean.ProductBean;
import rnp.DAO.ProductDAODataSource;

/**
 * Servlet implementation class AjaxProductEvalServlet
 */
@WebServlet("/AjaxProductEvalServlet")
public class AjaxProductEvalServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static ProductDAODataSource productDAO = new ProductDAODataSource();
	
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
			
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}