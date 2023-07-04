package rnpServlet;

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

import rnpBean.ProductBean;
import rnpDAO.ProductDAODataSource;

/**
 * Servlet implementation class AjaxProductEvalServlet
 */
@WebServlet("/AjaxProductEvalServlet")
public class AjaxProductEvalServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static ProductDAODataSource productDAO = new ProductDAODataSource();
	private static Gson gson = new GsonBuilder().create();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String action= request.getParameter("action");
			switch(action) {
			case "getBrands":
				handleBrands(request, response);
				break;
			case "evaluate":
				handleEval(request,response);
				break;
			default:
				response.sendError(HttpServletResponse.SC_NOT_FOUND, "Pagina non trovata");
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
			
			
			String json = gson.toJson(productDAO.doRetrieveBrands());
			
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void handleEval(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		int evaluation=100;//Valore più basso valutabile
		//Ottenimento dei valori passati dalla richiesta ajax
		String brand=request.getParameter("brand");
		String condition=request.getParameter("condition");
		String model=request.getParameter("model");
		int storage=Integer.parseInt(request.getParameter("storage"));
		//Calcolo del valore in base ad una logica per marca
		switch(brand) {
		case "Apple":
			evaluation+=59;
			break;
		case "Samsung":
			evaluation+=39;
			break;
		case "Google":
			evaluation+=39;
			break;
		case "Xiaomi":
			evaluation+=19;
			break;
		default:	
			evaluation+=29;
			break;
		}
		//Incremento valore in base allo spazio interno
		if(storage<=32);
		else if(storage<=64)
			evaluation+=50;
		else if(storage<=128)
			evaluation+=150;
		else if(storage<=256)
			evaluation+=200;
		else 
			evaluation+=300;
		
		switch(condition) {
		case "Buono":
			evaluation+=40;
			break;
		case "Ottimo":
			evaluation+=60;
			break;
		case "Accetabile":
			evaluation+=0;
			break;
		}
		if(model.toLowerCase().contains("pro") || model.toLowerCase().contains("max"))
			evaluation+=150;
		String json = gson.toJson(evaluation);
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);
			
	}
}