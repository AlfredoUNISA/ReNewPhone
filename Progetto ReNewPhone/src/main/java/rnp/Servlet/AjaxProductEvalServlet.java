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
public class AjaxProductEvalServlet extends HttpServlet implements VariousHelper {
	private static final long serialVersionUID = 1L;
	private static ProductDAODataSource productDAO = new ProductDAODataSource();
	private static Gson gson = new GsonBuilder().create();

	private static final String CLASS_NAME = AjaxProductEvalServlet.class.getName();
	private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		switch (action) {
		case "getBrands":
			handleBrands(request, response);
			break;
		case "evaluate":
			handleEval(request, response);
			break;
		default:
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "Pagina non trovata");
			break;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	/**
	 * Ritorna un json con tutti i brand presenti nel db.
	 */
	private void handleBrands(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {

			String json = gson.toJson(productDAO.doRetrieveBrands());

			sendJsonResponse(response, json);
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, ANSI_RED + "ERROR [" + CLASS_NAME + "]: " + e.getMessage() + ANSI_RESET);
		}

	}

	private void handleEval(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int evaluation = 100;// Valore più basso valutabile
		// Ottenimento dei valori passati dalla richiesta ajax
		String brand = request.getParameter("brand");
		String condition = request.getParameter("condition");
		String model = request.getParameter("model");
		int storage = Integer.parseInt(request.getParameter("storage"));
		// Calcolo del valore in base ad una logica per marca
		switch (brand) {
		case "Apple":
			evaluation += 129;
			break;
		case "Samsung":
			evaluation += 100;
			break;
		case "Google":
			evaluation += 79;
			break;
		case "Xiaomi":
			evaluation += 59;
			break;
		default:
			evaluation += 29;
			break;
		}
		// Incremento valore in base allo spazio interno
		if (storage <= 32)
			;
		else if (storage <= 64)
			evaluation += 70;
		else if (storage <= 128)
			evaluation += 150;
		else if (storage <= 256)
			evaluation += 250;
		else
			evaluation += 350;
		//System.out.println(condition);
		switch (condition) {
		case "buona":
			evaluation += 60;
			break;
		case "ottima":
			evaluation += 100;
			break;
		case "accetabile":
			evaluation += 0;
			break;
		}
		if (model.toLowerCase().contains("pro") )
			evaluation += 130;
		if (model.toLowerCase().contains("pro max") || model.toLowerCase().contains("plus")|| model.toLowerCase().contains("ultra"))
			evaluation += 170;
		String json = gson.toJson(evaluation);

		sendJsonResponse(response, json);

	}
}