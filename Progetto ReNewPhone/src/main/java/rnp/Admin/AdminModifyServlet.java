package rnp.Admin;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import rnp.Bean.ProductBean;
import rnp.DAO.ProductDAODataSource;
import rnp.Servlet.VariousHelper;
import rnp.Support.Login;

/**
 * Servlet implementation class AdminAddServlet
 */
@WebServlet("/admin-modify")
@MultipartConfig
public class AdminModifyServlet extends HttpServlet implements VariousHelper {
	private static final long serialVersionUID = 1L;
	private static ProductDAODataSource productDAO = new ProductDAODataSource();
	
	private static final String CLASS_NAME = AdminModifyServlet.class.getName();
	private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			if (!checkForAdmin(request, response)) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
				return;
			}
			modifyRow(request, response);
		} catch (ServletException | IOException | SQLException | InterruptedException e) {
			LOGGER.log(Level.SEVERE, ANSI_RED + "ERROR [" + CLASS_NAME + "]: " + e.getMessage() + ANSI_RESET);
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

	/**
	 * Aggiunge una nuova riga alla table del database.
	 */
	private void modifyRow(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, InterruptedException, SQLException {
		
		int currentId = -1;
		String currentIdPar = request.getParameter("id");
		if(currentIdPar != null && !currentIdPar.isBlank())
			currentId = Integer.parseInt(currentIdPar);
		
		String name = null; 
		String namePar = request.getParameter("name"); 
		if(namePar != null && !namePar.isBlank())
			name = namePar;
		
		int ram = -1;
		String ramPar = request.getParameter("ram");
		if(ramPar != null && !ramPar.isBlank())
			ram = Integer.parseInt(ramPar);

		float display_size = -1;
		String display_sizePar = request.getParameter("display_size");
		if(display_sizePar != null && !display_sizePar.isBlank())
			display_size = Float.parseFloat(display_sizePar);

		int storage = -1;
		String storagePar = request.getParameter("storage");
		if(storagePar != null && !storagePar.isBlank())
			storage = Integer.parseInt(storagePar);

		int price = -1;
		String pricePar = request.getParameter("price");
		if(pricePar != null && !pricePar.isBlank())
			price = Integer.parseInt(pricePar);

		int quantity = -1;
		String quantityPar = request.getParameter("quantity");
		if(quantityPar != null && !quantityPar.isBlank())
			quantity = Integer.parseInt(quantityPar);

		String color = null;
		String colorPar = request.getParameter("color");
		if(colorPar != null && !colorPar.isBlank())
			color = colorPar;
		
		String brand = null;
		String brandPar = request.getParameter("brand");
		if(brandPar != null && !brandPar.isBlank())
			brand = brandPar;

		int year = -1;
		String yearPar = request.getParameter("year");
		if(yearPar != null && !display_sizePar.isBlank())
			year = Integer.parseInt(yearPar);


		String category = null;
		String categoryPar = request.getParameter("category");
		if(categoryPar != null && !categoryPar.isBlank())
			category = categoryPar;

		String state = null;
		String statePar = request.getParameter("state");
		if(statePar != null&& !statePar.isBlank())
			state = statePar;

		ProductBean product = new ProductBean();
		product.setId(currentId);
		product.setName(name);
		product.setRam(ram);
		product.setDisplay_size(display_size);
		product.setStorage(storage);
		product.setPrice(price);
		product.setQuantity(quantity);
		product.setColor(color);
		product.setBrand(brand);
		product.setYear(year);
		product.setCategory(category);
		product.setState(state);

		productDAO.doUpdate(product);

	}
}
