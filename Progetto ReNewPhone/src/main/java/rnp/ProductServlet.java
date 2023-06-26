package rnp;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.*;

/**
 * Servlet implementation class ProductServlet
 */
@WebServlet("/products") //	MODIFICABILE
public class ProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static ProductDAODataSource productDAO = new ProductDAODataSource();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		String sort = request.getParameter("sort");
		int productsPerPage=0,currentPage=0;
		if(request.getParameter("productsPerPage") != null || request.getParameter("page")!= null) {
			productsPerPage= Integer.parseInt(request.getParameter("productsPerPage"));
			currentPage= Integer.parseInt(request.getParameter("page"));
		}else {
			productsPerPage= 9;
		}
		// Esegui azioni opzionali
		if (action != null) {
			switch (action) {
			case "details":
				showRowDetails(request, response);
				break;
			case "add":
				addRow(request, response);
				break;
			case "delete":
				// TODO: da vedere se un utente è autorizzato a cancellare un prodotto
				deleteRow(request, response);
				break;
			case "getProducts":
				showAllRows(request, response, "id",productsPerPage,currentPage);
				break;
			default:
				response.sendRedirect(request.getContextPath());
				break;
			}
		}
		else {
			showAllRows(request, response, "id",productsPerPage,1);
		}
	}


	/**
	 * Mostra tutte le righe all'interno della tabella principale. Incaricato di
	 * fare il forward verso la jsp.
	 * 
	 * @param sort Specifica l'ordine di ordinamento dei risultati
	 */
	/*private void showAllRows(HttpServletRequest request, HttpServletResponse response, String sort)
			throws ServletException, IOException {
		try {
			Collection<ProductBean> products = productDAO.doRetrieveAll(sort);
			
			Gson gson = new Gson();
			String json = gson.toJson(products);
			request.removeAttribute("productsJson");
			request.setAttribute("productsJson", json);
			request.getServletContext().getRequestDispatcher("/ProductView.jsp").forward(request, response);
		} catch (SQLException e) {
			System.out.println("ERROR: " + e);
		}
	}*/
	
	private void showAllRows(HttpServletRequest request, HttpServletResponse response, String sort, int productsPerPage, int pageNumber)
			throws ServletException, IOException {
		try {
			Collection<ProductBean> products = productDAO.doRetrieveAll(sort);
			LinkedList<ProductBean> Ar= (LinkedList<ProductBean>) (products);
			Collection<ProductBean> resultProducts = new ArrayList<>(); 
			for(int i=productsPerPage*(pageNumber-1);  i<(productsPerPage*pageNumber); i++) {
				resultProducts.add(Ar.get(i));
			}
			System.out.println("size: " + products.size());
			Gson gson = new Gson();
			JsonElement json = gson.toJsonTree(resultProducts);
			
			//Invio il numero di prodotti nel magazzino per permettere la suddivisione in più pagine
			
			request.removeAttribute("productNum");
			request.setAttribute("productsNum", products.size());
			
			//Invio i productsPerPage elementi alla pagina jsp come JSON
			request.removeAttribute("productsJson");
			request.setAttribute("productsJson", json);
			request.getServletContext().getRequestDispatcher("/ProductView.jsp").forward(request, response);
		} catch (SQLException e) {
			System.out.println("ERROR: " + e);
		}
	}
	

	/**
	 * Mostra i dettagli di una riga all'interno della jsp "ProductDetails".
	 */
	private void showRowDetails(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String name = request.getParameter("name");
		
		try {
			// MODIFICABILE
			//request.removeAttribute("product-details");
			request.setAttribute("product-details", productDAO.doRetrieveByName(name));
			request.getServletContext().getRequestDispatcher("/ProductDetails.jsp").forward(request, response);
		} catch (SQLException e) {
			System.out.println("ERROR: " + e);
		}
	}

	/**
	 * Aggiunge una nuova riga alla table del database.
	 */
	private void addRow(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// MODIFICABILE
		String name = request.getParameter("name");
		int ram= Integer.parseInt(request.getParameter("ram"));
		float display_size= Float.parseFloat(request.getParameter("display_size"));
		int storage= Integer.parseInt(request.getParameter("storage"));
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

		try {
			productDAO.doSave(product);
		} catch (SQLException e) {
			System.out.println("ERROR: " + e);
		}
	}

	/**
	 * Elimina una riga dalla table del database.
	 */
	private void deleteRow(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));

		try {
			if (!productDAO.doDelete(id)) {
				System.out.println("**404** Product not found for deleteRow (id_product = " + id + ")");
			}
		} catch (SQLException e) {
			System.out.println("ERROR: " + e);
			if (e instanceof java.sql.SQLIntegrityConstraintViolationException) {
				// TODO: fare qualcosa se delle tabelle sono dipendenti da certi valori in
				// questa riga da eliminare
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
