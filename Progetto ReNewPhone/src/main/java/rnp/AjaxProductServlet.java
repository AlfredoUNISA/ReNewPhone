package rnp;

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

/**
 * Servlet implementation class AjaxProductServlet
 */
@WebServlet("/AjaxProductServlet")
public class AjaxProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static ProductDAODataSource productDAO = new ProductDAODataSource();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int productsPerLoading = 12; // Default
		int countLoadings = 0;

		if (request.getParameter("productsPerLoading") != null) {
			productsPerLoading = Integer.parseInt(request.getParameter("productsPerLoading"));
		}
		System.out.println("productsPerLoading: " + countLoadings);
		
		if (request.getParameter("countLoadings") != null) {
			countLoadings = Integer.parseInt(request.getParameter("countLoadings"));
		}
		System.out.println("countLoadings: " + countLoadings);

		try {
			// Prendi tutti i prodotti dal database
			LinkedList<ProductBean> listProducts = (LinkedList<ProductBean>) productDAO.doRetrieveAll(null);
			
			// Crea una mappa per raggruppare i prodotti per nome
			Map<String, List<ProductBean>> groupedProducts = new HashMap<>();

			// Aggiungi tutti i prodotti alla mappa raggruppandoli per nome
			for (ProductBean product : listProducts) {
				String productName = product.getName();
			  
				// Controlla se il nome del prodotto è già presente nella mappa
				if (groupedProducts.containsKey(productName)) {
					// Se il nome del prodotto è già presente, aggiungi il prodotto alla lista corrispondente
					groupedProducts.get(productName).add(product);
				} else {
					// Se il nome del prodotto non è presente, crea una nuova lista e aggiungi il prodotto
					List<ProductBean> productList = new ArrayList<>();
					productList.add(product);
					groupedProducts.put(productName, productList);
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
			        String groupName = group.get(0).getName();

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

			// Creazione di un oggetto Gson per la serializzazione degli oggetti Java in JSON
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
			        jsonMinMax.addProperty("minDisplaySize", Math.min(jsonMinMax.get("minDisplaySize").getAsFloat(), product.getDisplay_size()));
			        jsonMinMax.addProperty("maxDisplaySize", Math.max(jsonMinMax.get("maxDisplaySize").getAsFloat(), product.getDisplay_size()));
			        
			        // Calcola il valore minimo e massimo per storage
			        jsonMinMax.addProperty("minStorage", Math.min(jsonMinMax.get("minStorage").getAsInt(), product.getStorage()));
			        jsonMinMax.addProperty("maxStorage", Math.max(jsonMinMax.get("maxStorage").getAsInt(), product.getStorage()));
			        
			        // Calcola il valore minimo e massimo per storage
			        jsonMinMax.addProperty("minPrice", Math.min(jsonMinMax.get("minPrice").getAsInt(), product.getPrice()));
			        jsonMinMax.addProperty("maxPrice", Math.max(jsonMinMax.get("maxPrice").getAsInt(), product.getPrice()));
			    }
			    
			    // Aggiungi l'oggetto dei valori minimi e massimi al gruppo
			    jsonGroup.add("minMaxValues", jsonMinMax);
			    
			    
			    // Aggiungi il gruppo di prodotti all'array dei gruppi
			    jsonGroups.add(jsonGroup);
			}

			// Converti l'array dei gruppi in formato JSON
			String jsonResult = gson.toJson(jsonGroups);

			

		
/*
			// Aggiungi i prodotti alla lista risultante
			for (int i = (productsPerLoading * countLoadings); i < (productsPerLoading * (countLoadings + 1)); i++) {
				resultProducts.add(resultGroups.);
			}
*/
			
			// Scrivi il JSON come risposta
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(jsonResult);

		} catch (SQLException e) {
			System.out.println("ERROR: " + e);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}