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
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import me.xdrop.fuzzywuzzy.FuzzySearch;
import rnp.Bean.ProductBean;
import rnp.DAO.ProductDAODataSource;

/**
 * Servlet che ritorna un JSON a una richiesta AJAX
 */
@WebServlet("/AjaxProductServlet")
public class AjaxProductServlet extends HttpServlet implements VariousHelper {
	private static final long serialVersionUID = 1L;
	private static ProductDAODataSource productDAO = new ProductDAODataSource();
	
	private static final String CLASS_NAME = AjaxProductServlet.class.getName();
	private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int productsPerLoading = 12; // Default
		int countLoadings = 0;
		
		// Valori di default per i filtri
		int priceMin = Integer.MIN_VALUE;
		int priceMax = Integer.MAX_VALUE; 
		int memoryMin = Integer.MIN_VALUE; 
		int memoryMax = Integer.MAX_VALUE;
		int ramMin = Integer.MIN_VALUE; 
		int ramMax = Integer.MAX_VALUE;
		int yearMin = Integer.MIN_VALUE; 
		int yearMax = Integer.MAX_VALUE;
		
		String filterBrand = null;
		String nameFilter = null;
		
		// Ottenimento dei valori dei filtri
		if (request.getParameter("priceMin") != null && !request.getParameter("priceMin").isBlank()) {
			priceMin = Integer.parseInt(request.getParameter("priceMin"));
		}
		if (request.getParameter("priceMax") != null && !request.getParameter("priceMax").isBlank()) {
			priceMax = Integer.parseInt(request.getParameter("priceMax"));
		}
		if (request.getParameter("memoryMin") != null && !request.getParameter("memoryMin").isBlank()) {
			memoryMin = Integer.parseInt(request.getParameter("memoryMin"));
		}
		if (request.getParameter("memoryMax") != null && !request.getParameter("memoryMax").isBlank()) {
			memoryMax = Integer.parseInt(request.getParameter("memoryMax"));
		}
		if (request.getParameter("ramMin") != null && !request.getParameter("ramMin").isBlank()) {
			ramMin = Integer.parseInt(request.getParameter("ramMin"));
		}
		if (request.getParameter("ramMax") != null && !request.getParameter("ramMax").isBlank()) {
			ramMax = Integer.parseInt(request.getParameter("ramMax"));
		}
		if (request.getParameter("yearMin") != null && !request.getParameter("yearMin").isBlank()) {
			yearMin = Integer.parseInt(request.getParameter("yearMin"));
		}
		if (request.getParameter("yearMax") != null && !request.getParameter("yearMax").isBlank()) {
			yearMax = Integer.parseInt(request.getParameter("yearMax"));
		}
		if (request.getParameter("filterBrand") != null && !request.getParameter("filterBrand").isBlank()) {
			filterBrand = request.getParameter("filterBrand");
		}
		if (request.getParameter("nameFilter") != null && !request.getParameter("nameFilter").isBlank()) {
			nameFilter = request.getParameter("nameFilter");
		}

		if (request.getParameter("productsPerLoading") != null) {
			productsPerLoading = Integer.parseInt(request.getParameter("productsPerLoading"));
		}
		// System.out.println("productsPerLoading: " + countLoadings);

		if (request.getParameter("countLoadings") != null) {
			countLoadings = Integer.parseInt(request.getParameter("countLoadings"));
		}
		// System.out.println("countLoadings: " + countLoadings);

		loadProducts(request, response, productsPerLoading, countLoadings, priceMin, priceMax, memoryMin, memoryMax,
				ramMin, ramMax, filterBrand,yearMin,yearMax,nameFilter);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	protected void loadProducts(HttpServletRequest request, HttpServletResponse response, int productsPerLoading,
			int countLoadings, int priceMin, int priceMax, int memoryMin, int memoryMax, int ramMin, int ramMax,
			String filteredBrand, int yearMin, int yearMax, String nameFilter) throws ServletException, IOException {
		try {
			Boolean filterStringWasNull = true; // default
			Boolean filterNameWasNull = true; // default
			// Prendi tutti i prodotti dal database
			LinkedList<ProductBean> listProducts = (LinkedList<ProductBean>) productDAO.doRetrieveAll(null);

			// Crea una mappa per raggruppare i prodotti per nome
			Map<String, List<ProductBean>> groupedProducts = new HashMap<>();

			if (filteredBrand != null && filteredBrand.compareToIgnoreCase("Seleziona") != 0) // Controllo che i
																								// prodotti rientrino
																								// nei filtri
				filterStringWasNull = false;
			if (nameFilter != null && nameFilter.compareToIgnoreCase("Cerca...") != 0)  // Controllo che i
																						// prodotti rientrino
																						// nei filtri
				filterNameWasNull = false;
			// Aggiungi tutti i prodotti alla mappa raggruppandoli per nome
			for (ProductBean product : listProducts) {
				if (filterStringWasNull)
					filteredBrand = product.getBrand();
				if (filterNameWasNull)
					nameFilter = product.getName();
				String productName = product.getName();
				int score = FuzzySearch.partialRatio(nameFilter.toLowerCase(), product.getName().toLowerCase());

				if (priceMin <= product.getPrice() // Nel caso non ci siano filtri specifici
						&& priceMax >= product.getPrice()// Tutti i prodotti passeranno per via dei valori di default
						&& memoryMin <= product.getStorage() && memoryMax >= product.getStorage()
						&& ramMin <= product.getRam() && ramMax >= product.getRam()
						&& yearMin <= product.getYear() && yearMax >= product.getYear()
						&& filteredBrand.compareToIgnoreCase(product.getBrand()) == 0
						&& score>=67) {

					// Controlla se il nome del prodotto è già presente nella mappa
					if (groupedProducts.containsKey(productName)) {
						// Se il nome del prodotto è già presente, aggiungi il prodotto alla lista
						// corrispondente
						groupedProducts.get(productName).add(product);
					} else {
						// Se il nome del prodotto non è presente, crea una nuova lista e aggiungi il
						// prodotto
						List<ProductBean> productList = new ArrayList<>();
						productList.add(product);
						groupedProducts.put(productName, productList);
					}
				}
			}

			// Creazione della nuova mappa per i dati estrapolati
			Map<String, List<ProductBean>> extractedGroups = new LinkedHashMap<>();

			// Calcolo dell'indice di inizio e fine per l'estrazione dei dati
			int startIndex = productsPerLoading * countLoadings;
			int endIndex = productsPerLoading * (countLoadings + 1);

			// Iterazione sulla mappa dei gruppi
			int counter = 0;
			for (List<ProductBean> group : groupedProducts.values()) {
				// Verifica se l'indice corrente rientra nell'intervallo di estrazione
				if (counter >= startIndex && counter < endIndex) {
					// Ottieni il nome del gruppo corrente
					String groupName = group.get(0).getName(); // Verifica che il prodotto rientri nei filtri
					// Aggiungi il gruppo corrente alla nuova mappa
					extractedGroups.put(groupName, group);
				}

				counter++;

				// Verifica se abbiamo estratto il numero desiderato di elementi
				if (counter >= endIndex) {
					break;
				}
			}

			// Creazione della collezione dei gruppi estratti
			Collection<List<ProductBean>> resultGroups = extractedGroups.values();

			// Creazione di un oggetto JSON per i gruppi di prodotti
			JsonArray jsonGroups = new JsonArray();

			// Creazione di un oggetto Gson per la serializzazione degli oggetti Java in
			// JSON
			Gson gson = new GsonBuilder().setPrettyPrinting().create();

			// Creazione dei gruppi di prodotti in formato JSON
			for (List<ProductBean> group : resultGroups) {
				JsonObject jsonGroup = new JsonObject();

				// Aggiungi i campi vari del gruppo
				jsonGroup.addProperty("groupName", group.get(0).getName());
				jsonGroup.addProperty("model", group.get(0).getModel());
				jsonGroup.addProperty("brand", group.get(0).getBrand());
				jsonGroup.addProperty("year", group.get(0).getYear());

				// Creazione di un oggetto JSON per i valori minimi e massimi
				JsonObject jsonMinMax = new JsonObject();
				jsonMinMax.addProperty("minRam", Integer.MAX_VALUE);
				jsonMinMax.addProperty("maxRam", Integer.MIN_VALUE);
				jsonMinMax.addProperty("minDisplaySize", Float.MAX_VALUE);
				jsonMinMax.addProperty("maxDisplaySize", Float.MIN_VALUE);
				jsonMinMax.addProperty("minStorage", Integer.MAX_VALUE);
				jsonMinMax.addProperty("maxStorage", Integer.MIN_VALUE);
				jsonMinMax.addProperty("minPrice", Integer.MAX_VALUE);
				jsonMinMax.addProperty("maxPrice", Integer.MIN_VALUE);

				// Calcolo dei valori minimi e massimi all'interno del gruppo
				for (ProductBean product : group) {
					// Calcola il valore minimo e massimo per ram
					jsonMinMax.addProperty("minRam", Math.min(jsonMinMax.get("minRam").getAsInt(), product.getRam()));
					jsonMinMax.addProperty("maxRam", Math.max(jsonMinMax.get("maxRam").getAsInt(), product.getRam()));

					// Calcola il valore minimo e massimo per display size
					jsonMinMax.addProperty("minDisplaySize",
							Math.min(jsonMinMax.get("minDisplaySize").getAsFloat(), product.getDisplay_size()));
					jsonMinMax.addProperty("maxDisplaySize",
							Math.max(jsonMinMax.get("maxDisplaySize").getAsFloat(), product.getDisplay_size()));

					// Calcola il valore minimo e massimo per storage
					jsonMinMax.addProperty("minStorage",
							Math.min(jsonMinMax.get("minStorage").getAsInt(), product.getStorage()));
					jsonMinMax.addProperty("maxStorage",
							Math.max(jsonMinMax.get("maxStorage").getAsInt(), product.getStorage()));

					// Calcola il valore minimo e massimo per storage
					jsonMinMax.addProperty("minPrice",
							Math.min(jsonMinMax.get("minPrice").getAsInt(), product.getPrice()));
					jsonMinMax.addProperty("maxPrice",
							Math.max(jsonMinMax.get("maxPrice").getAsInt(), product.getPrice()));
				}

				// Aggiungi l'oggetto dei valori minimi e massimi al gruppo
				jsonGroup.add("minMaxValues", jsonMinMax);

				// Aggiungi il gruppo di prodotti all'array dei gruppi
				jsonGroups.add(jsonGroup);
			}

			// Converti l'array dei gruppi in formato JSON
			String jsonResult = gson.toJson(jsonGroups);
			// System.out.println(jsonResult.toString());
			// Scrivi il JSON come risposta
			sendJsonResponse(response, jsonResult);

		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, ANSI_RED + "ERROR [" + CLASS_NAME + "]: " + e.getMessage() + ANSI_RESET);
		}
	}

}
