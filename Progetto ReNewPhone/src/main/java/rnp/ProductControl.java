package rnp;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// ORDINE DI CHIAMATE: jsp (product bean) -> product control -> ProductDAO -> Connection Pool

/**
 * Servlet per gestire le richieste relate alla manipolazione dei dati di un database (PRODOTTI).
 * @category Servlet
 * @category MODIFICABILE
 */
@WebServlet("/products")
public class ProductControl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// SCEGLIERE SE UTILIZZARE IL DATA SOURCE O IL DRIVER MANAGER
	// ProductDAODataSource usa il DataSource (TRUE)
	// ProductDAODriverMan usa il DriverManager	(FALSE)
	static boolean isDataSource = true;
	
	static IBeanDAO<ProductBean> productDAO;
	
	static {
		if (isDataSource) {
			productDAO = new ProductDAODataSource();
		} else {
			productDAO = new ProductDAODriverMan();
		}
	}
	
	public ProductControl() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Parametro della richiesta HTTP
		String action = request.getParameter("action");

		try {
			if (action != null) {
				if (action.equalsIgnoreCase("read")) {
					/* 
					 * Se l'azione è "read", il servlet legge il parametro "id" dalla richiesta e 
					 * richiama il metodo "doRetrieveByKey()" per OTTENERE il prodotto con l'ID specificato.
					 */
					int id = Integer.parseInt(request.getParameter("id")); // MODIFICABILE
					
					request.removeAttribute("product");
					request.setAttribute("product", productDAO.doRetrieveByKey(id));
				} 
				else if (action.equalsIgnoreCase("delete")) {
					/* 
					 * Se l'azione è "delete", il servlet legge il parametro "id" dalla richiesta e 
					 * richiama il metodo "doDelete()" per ELIMINARE il prodotto con l'ID specificato. 
					 */
					int id = Integer.parseInt(request.getParameter("id")); // MODIFICABILE
					productDAO.doDelete(id);
				} 
				else if (action.equalsIgnoreCase("insert")) {
					/* 
					 * Se l'azione è "insert", il servlet legge i parametri "name", "description", "price" e "quantity" 
					 * dalla richiesta e crea un nuovo oggetto ProductBean con questi valori. 
					 * Quindi richiama il metodo "doSave()" per SALVARE il nuovo prodotto nel database. 
					 */
					// MODIFICABILE
					String name = request.getParameter("name");
					String description = request.getParameter("description");
					int price = Integer.parseInt(request.getParameter("price"));
					int quantity = Integer.parseInt(request.getParameter("quantity"));
					String color = request.getParameter("color");
					String brand = request.getParameter("brand");
					String category = request.getParameter("category");
					String state = request.getParameter("state");
					int year= Integer.parseInt(request.getParameter("year"));

					// MODIFICABILE
					ProductBean bean = new ProductBean();
					bean.setName(name);
					bean.setDescription(description);
					bean.setPrice(price);
					bean.setQuantity(quantity);
					bean.setColor(color);
					bean.setBrand(brand);
					bean.setCategory(category);
					bean.setState(state);
					bean.setYear(year);
					
					productDAO.doSave(bean);
				}
			}
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		}

		/*
		 * Legge il parametro "sort" dalla richiesta e richiama il metodo "doRetrieveAll()" 
		 * per recuperare tutti i prodotti dal database e ordinarli in base al valore del parametro "sort". 
		 */
		
		String sort = request.getParameter("sort");

		try {
			request.removeAttribute("products");
			request.setAttribute("products", productDAO.doRetrieveAll(sort));
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		}

		/*
		 * Imposta la lista di prodotti come attributo della richiesta HTTP e 
		 * reindirizza la richiesta alla pagina JSP "ProductView.jsp".
		 */
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/ProductView.jsp"); // MODIFICABILE
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
