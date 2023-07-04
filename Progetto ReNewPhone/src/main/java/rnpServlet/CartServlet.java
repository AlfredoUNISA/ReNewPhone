package rnpServlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import rnpBean.CartBean;
import rnpBean.ItemOrderBean;
import rnpBean.OrderBean;
import rnpBean.ProductBean;
import rnpDAO.CartDAODataSource;
import rnpDAO.ItemsOrderDAODataSource;
import rnpDAO.OrderDAODataSource;
import rnpDAO.ProductDAODataSource;
import rnpSupport.Login;

/**
 * Servlet implementation class CartServlet
 */
@WebServlet("/my-cart")
public class CartServlet extends HttpServlet {
	private static final Logger logger = Logger.getLogger(CartServlet.class.getName());
	private static final long serialVersionUID = 1L;
	private static CartDAODataSource cartDAO = new CartDAODataSource();
	private static ProductDAODataSource productDAO = new ProductDAODataSource();
	private Gson gson = new GsonBuilder().setPrettyPrinting().create();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");

		String userParam = request.getParameter("user");
		int id_user = -1;
		if (userParam != null)
			id_user = Integer.parseInt(userParam);

		String productParam = request.getParameter("product");
		int id_product = -1;
		if (productParam != null) {
			id_product = Integer.parseInt(productParam);
		}

		// Esegui azioni opzionali
		if (action != null) {
			switch (action) {
			case "show":
				showAllRows(request, response, id_user);
				break;
			case "add":
				try {
					addRow(request, response, id_user, id_product);
				} catch (ServletException | IOException | SQLException e) {
					System.out.println(e.getMessage());
				}
				break;
			case "delete":
				deleteRow(request, response, id_user, id_product);
				break;
			case "finalize":
				finalizeOrder(request, response, id_user);
				break;
			default:
				response.sendRedirect(request.getContextPath());
				break;
			}
		} else {
			request.getServletContext().getRequestDispatcher("/CartView.jsp").forward(request, response);
		}
	}

	private String emptyCartJsonString = null;

	/**
	 * Crea ed imposta un cartCookie "vuoto", con sum=0 e cartList.size=0
	 * 
	 * @return json "vuoto"
	 */
	private Cookie createNewEmptyCartCookie(HttpServletResponse response) {
		// Crea il json con oggetti "vuoti"
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("sum", 0);

		JsonElement cartElements = gson.toJsonTree(new ArrayList<>());
		jsonObject.add("cartList", cartElements);

		emptyCartJsonString = jsonObject.toString();

		// Codifica la stringa per evitare caratteri illegali nel cookie ed imposta il
		// cookie
		Cookie cartCookie = new Cookie("cartCookie", encodeJsonString(emptyCartJsonString));
		cartCookie.setMaxAge(Login.COOKIE_DURATION);
		response.addCookie(cartCookie);

		return cartCookie;
	}

	/**
	 * Manda il json come risposta.
	 */
	private void sendJsonResponse(HttpServletResponse response, String jsonString) throws IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(jsonString);
	}

	/**
	 * Ritorna il cookie con il nome specificato, null se non esiste.
	 */
	private Cookie getCookie(String name, HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		Cookie result = null;
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(name)) {
					result = cookie;
					break;
				}
			}
		}
		return result;
	}

	/**
	 * Ritorna la stringa "decodificata" contenuta nel CartCookie.
	 */
	private String decodeJsonString(Cookie cartCookie) {
		return new String(Base64.getDecoder().decode(cartCookie.getValue()));
	}

	/**
	 * Ritorna la stringa "codificata" per poterla includere nel CartCookie.
	 */
	private String encodeJsonString(String json) {
		return Base64.getEncoder().encodeToString(json.getBytes());
	}

	/**
	 * Ritorna un json alla pagina jsp contenente il carrello nel database o nel
	 * cookie.
	 */
	private void showAllRows(HttpServletRequest request, HttpServletResponse response, int usr)
			throws ServletException, IOException {
		try {
			if (usr == -1) {
				// ---- UTENTE NON REGISTRATO ----
				String jsonString = null;

				// Ottieni il cookie
				Cookie cartCookie = getCookie("cartCookie", request);

				if (cartCookie == null) {
					// Se il cookie non esiste creane uno nuovo
					cartCookie = createNewEmptyCartCookie(response);
					jsonString = emptyCartJsonString; // Prodotto dalla funzione
				} else {
					// Se il cookie esiste

					// Ottieni il json
					JsonObject jsonObj = gson.fromJson(decodeJsonString(cartCookie), JsonObject.class);
					jsonString = jsonObj.toString();
				}
				// Manda il json come risposta
				sendJsonResponse(response, jsonString);
			} else {
				// ---- UTENTE REGISTRATO ----

				// Ottieni le righe dal database
				List<CartBean> cartList = (List<CartBean>) cartDAO.doRetrieveByUser(usr, null);
				// Somma totale degli oggetti nel carrello
				int sum = 0;
				// Lista di oggetti da mandare con il json
				List<CartDataToSend> listToSend = new ArrayList<>();

				// Inserisci i dati dal database nella lista per il json
				for (CartBean cartBean : cartList) {
					ProductBean product = productDAO.doRetrieveByKey(cartBean.getId_product());

					CartDataToSend toSend = new CartDataToSend(product.getName(), product.getId(),
							cartBean.getQuantity(), product.getPrice());

					sum += product.getPrice() * cartBean.getQuantity();
					listToSend.add(toSend);
				}

				Cookie cartCookie = getCookie("cartCookie", request);
				JsonObject jsonObjectCookie = null;

				// Se esiste un cartCookie, allora è un utente non registrato che ha fatto il
				// login: includi il carrello del cookie nel carrello dell'utente
				if (cartCookie != null) {
					// Ottieni il json dal cookie
					jsonObjectCookie = gson.fromJson(decodeJsonString(cartCookie), JsonObject.class);

					// Prendi l'array
					JsonArray cartCookieList = jsonObjectCookie.getAsJsonArray("cartList");

					// Itera su tutti gli elementi dell'array ottenuto dal cookie sotto forma di
					// JsonObject
					for (JsonElement cartElement : cartCookieList) {
						JsonObject current = cartElement.getAsJsonObject();

						// Prendi tutti i parametri necessari
						int idCookie = current.get("id").getAsInt();
						String nameCookie = current.get("name").getAsString();
						int priceCookie = current.get("price").getAsInt();
						int quantityCookie = current.get("quantity").getAsInt();

						// Controlla se i parametri sono presenti nella lista da mandare
						boolean isPresent = false;
						for (CartDataToSend cartDataToSend : listToSend) {
							if (idCookie == cartDataToSend.getId() && nameCookie == cartDataToSend.getName()
									&& priceCookie == cartDataToSend.getPrice()
									&& quantityCookie == cartDataToSend.getQuantity())
								isPresent = true;
						}

						// Se i parametri non sono presenti nella lista da mandare, aggiungili alla
						// lista e al database
						if (!isPresent) {
							// LISTA DA MANDARE
							CartDataToSend toSendCookie = new CartDataToSend(nameCookie, idCookie, quantityCookie,
									priceCookie);
							sum += priceCookie * quantityCookie;

							listToSend.add(toSendCookie);

							// DATABASE
							CartBean cartElementCookie = new CartBean();
							cartElementCookie.setId_user(usr);
							cartElementCookie.setId_product(idCookie);
							cartElementCookie.setQuantity(quantityCookie);

							cartDAO.doSave(cartElementCookie);
						}

					}
					// Invalida il cartCookie
					cartCookie.setMaxAge(0);
					response.addCookie(cartCookie);
				}

				// Crea il json della risposta
				JsonObject jsonObject = new JsonObject();
				
				// Aggiungi al json la somma
				if (listToSend.size() == 0)
					sum = 0;
				jsonObject.addProperty("sum", sum);

				// Aggiungi al json la lista da mandare
				jsonObject.add("cartList", gson.toJsonTree(listToSend));

				// Manda il json come risposta
				sendJsonResponse(response, jsonObject.toString());
			}
		} catch (SQLException e) {
			printError(e);
		}
	}

	/**
	 * Aggiunge una nuova riga alla table del database.
	 */
	private void addRow(HttpServletRequest request, HttpServletResponse response, int id_user, int id_product)
			throws ServletException, IOException, SQLException {
		int quantity = Integer.parseInt(request.getParameter("quantity"));

		if (id_user == -1) {
			// ---- UTENTE NON REGISTRATO ----

			// Prendi il cookie "cartCookie" dalla richiesta
			Cookie cartCookie = getCookie("cartCookie", request);

			// Se il cookie non esiste creane uno nuovo
			if (cartCookie == null) {
				cartCookie = createNewEmptyCartCookie(response);
			}

			// Ottieni il json
			JsonObject jsonObject = gson.fromJson(decodeJsonString(cartCookie), JsonObject.class);

			int sum = jsonObject.get("sum").getAsInt();
			JsonArray cartList = jsonObject.getAsJsonArray("cartList");

			ProductBean product = productDAO.doRetrieveByKey(id_product);

			// Aggiungi il prodotto a cartList
			CartDataToSend toAdd = new CartDataToSend(product.getName(), product.getId(), quantity, product.getPrice());
			JsonObject toAddJson = gson.toJsonTree(toAdd).getAsJsonObject();
			cartList.add(toAddJson);

			// Aggiorna la somma totale
			sum += quantity * product.getPrice();

			// Aggiorna i valori del json
			jsonObject.add("cartList", cartList);
			jsonObject.addProperty("sum", sum);

			// Elimina il cookie vecchio
			cartCookie.setMaxAge(0);

			// Crea il cookie con il json aggiornato
			Cookie updatedCartCookie = new Cookie("cartCookie", encodeJsonString(jsonObject.toString()));
			updatedCartCookie.setMaxAge(Login.COOKIE_DURATION);

			// Aggiungi il cookie alla risposta
			response.addCookie(updatedCartCookie);
		} else {
			// ---- UTENTE REGISTRATO ----

			CartBean cart = new CartBean();
			cart.setId_user(id_user);
			cart.setId_product(id_product);
			cart.setQuantity(quantity);

			try {
				cartDAO.doSave(cart);
			} catch (SQLException e) {
				printError(e);
				if (e instanceof java.sql.SQLIntegrityConstraintViolationException) {
					// TODO: fare qualcosa se ci sono duplicati
				}
			}
		}
	}

	/**
	 * Elimina una riga dalla table del database.
	 */
	private void deleteRow(HttpServletRequest request, HttpServletResponse response, int id_user, int id_product)
			throws ServletException, IOException {

		if (id_user == -1) {
			Cookie cartCookie = getCookie("cartCookie", request);

			JsonObject jsonObject = gson.fromJson(decodeJsonString(cartCookie), JsonObject.class);

			int sum = jsonObject.get("sum").getAsInt();
			JsonArray cartList = jsonObject.getAsJsonArray("cartList");

			for (JsonElement element : cartList) {
				CartDataToSend cartItem = gson.fromJson(element, CartDataToSend.class);

				if (cartItem.id == id_product) {
					cartList.remove(element);
					sum -= cartItem.price * cartItem.quantity;
					break;
				}
			}

			// Aggiorna i valori del json
			jsonObject.add("cartList", cartList);
			jsonObject.addProperty("sum", sum);

			// Elimina il cookie vecchio
			cartCookie.setMaxAge(0);

			// Crea il cookie aggiornato
			Cookie updatedCartCookie = new Cookie("cartCookie", encodeJsonString(jsonObject.toString()));
			updatedCartCookie.setMaxAge(Login.COOKIE_DURATION);

			// Aggiungi il cookie alla risposta
			response.addCookie(updatedCartCookie);
		} else {
			try {
				if (!cartDAO.doDeleteSingleRow(id_user, id_product)) {
					logger.log(Level.WARNING, "*Cart row not found for showRowDetails (id_user = " + id_user
							+ ", id_product = " + id_product + ")");
				}
			} catch (SQLException e) {
				printError(e);
				if (e instanceof java.sql.SQLIntegrityConstraintViolationException) {
					// TODO: fare qualcosa se delle tabelle sono dipendenti da certi valori in
					// questa riga da eliminare
				}
			}
		}
	}

	/**
	 * Finalizza l'ordine mettendo tutti gli oggetti del carrello in un ordine e
	 * rimuove tutti gli elementi del carrello. Solo gli utenti registrati possono
	 * entrare qui.
	 */
	private void finalizeOrder(HttpServletRequest request, HttpServletResponse response, int id_user)
			throws ServletException, IOException {
		try {
			Collection<CartBean> cart = cartDAO.doRetrieveByUser(id_user, null);

			String totalParam = request.getParameter("total");
			int total = 0;
			if (totalParam != null) {
				total = Integer.parseInt(totalParam);
			}

			if (cart != null && !cart.isEmpty()) {
				OrderDAODataSource orderDAO = new OrderDAODataSource();
				ItemsOrderDAODataSource itemsOrderDAO = new ItemsOrderDAODataSource();
				ProductDAODataSource productDAO = new ProductDAODataSource();

				Iterator<?> checkIter = cart.iterator();
				while (checkIter.hasNext()) {
					CartBean cart_bean = (CartBean) checkIter.next();
					ProductBean productCheck = productDAO.doRetrieveByKey(cart_bean.getId_product());

					if (productCheck.getQuantity() < cart_bean.getQuantity())
						throw new SQLException("Not Enough in stock for " + productCheck.getName() + " ("
								+ productCheck.getQuantity() + " in stock, " + cart_bean.getQuantity() + " asked).");
				}

				OrderBean order_bean = new OrderBean();
				order_bean.setId_user(id_user);
				order_bean.setTotal(total);

				int generatedOrderId = orderDAO.doSave(order_bean);

				Iterator<?> it = cart.iterator();
				while (it.hasNext()) {
					CartBean cart_bean = (CartBean) it.next();

					ItemOrderBean item_order_bean = new ItemOrderBean();
					item_order_bean.setId_order(generatedOrderId);
					item_order_bean.setId_product(cart_bean.getId_product());
					item_order_bean.setQuantity(cart_bean.getQuantity());

					itemsOrderDAO.doSave(item_order_bean);
				}

				cartDAO.doDelete(id_user);
			}
		} catch (SQLException e) {
			printError(e);
			// Fare qualcosa se non ci sono abbastanza oggetti in stock
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	protected final void printError(SQLException e) {
		logger.log(Level.SEVERE, "ERROR: " + e);
	}

}

class CartDataToSend {
	String name;
	int id;
	int quantity;
	int price;

	public CartDataToSend(String name, int id, int quantity, int price) {
		this.name = name;
		this.id = id;
		this.quantity = quantity;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	public int getQuantity() {
		return quantity;
	}

	public int getPrice() {
		return price;
	}

}
