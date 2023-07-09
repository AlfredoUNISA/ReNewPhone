package rnp.Admin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import rnp.Bean.ProductBean;
import rnp.DAO.ProductDAODataSource;
import rnp.Servlet.VariousHelper;
import rnp.Support.Login;

/**
 * Servlet implementation class AdminAddServlet
 */
@WebServlet("/admin-add")
@MultipartConfig
public class AdminAddServlet extends HttpServlet implements VariousHelper {
	private static final long serialVersionUID = 1L;
	private static ProductDAODataSource productDAO = new ProductDAODataSource();
	
	private static final String CLASS_NAME = AdminAddServlet.class.getName();
	private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);
	
	private static final String UPLOAD_DIRECTORY = "C:\\Users\\Alfredo\\Desktop\\ImmaginiRNP";

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
			addRow(request, response);
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
	 * Salva l'immagine data nella directory ASSOLUTA del server.
	 */
	private void storeImage(HttpServletRequest request, String name) throws IOException, ServletException, SQLException {
		Part filePart = request.getPart("file");
		// String fileName =
		// Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
		InputStream fileContent = filePart.getInputStream();

		// Crea il percorso completo per il file (rinomina il file)
		
		List<ProductBean> listTmp = (List<ProductBean>) productDAO.doRetrieveByName(name);
		//System.out.println(listTmp.get(0).getModel());
		
		String filePath = UPLOAD_DIRECTORY + File.separator + listTmp.get(0).getModel() + ".jpg";

		// Salva il file sul server
		File file = new File(filePath);
		try (OutputStream outputStream = new FileOutputStream(file)) {
			int bytesRead;
			byte[] buffer = new byte[8192]; // Dimensione del buffer
			while ((bytesRead = fileContent.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}
		}
	}

	/**
	 * Aggiunge una nuova riga alla table del database.
	 */
	private void addRow(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, InterruptedException, SQLException {
		// MODIFICABILE
		String name = request.getParameter("name");
		int ram = Integer.parseInt(request.getParameter("ram"));
		float display_size = Float.parseFloat(request.getParameter("display_size"));
		int storage = Integer.parseInt(request.getParameter("storage"));
		int price = Integer.parseInt(request.getParameter("price"));
		int quantity = Integer.parseInt(request.getParameter("quantity"));
		String color = request.getParameter("color");
		String brand = request.getParameter("brand");
		int year = Integer.parseInt(request.getParameter("year"));
		String category = request.getParameter("category");
		String state = request.getParameter("state");

		// MODIFICABILE
		ProductBean product = new ProductBean();
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

		boolean error = false;
		try {
			productDAO.doSave(product);
		} catch (SQLException e) {
			error = true;
			LOGGER.log(Level.SEVERE, ANSI_RED + "ERROR [" + CLASS_NAME + "]: " + e.getMessage() + ANSI_RESET);
		}

		if (!error) {
			storeImage(request, name);
			TimeUnit.SECONDS.sleep(2);
			response.sendRedirect("products?action=details&name=" + name);
		}
	}
}
