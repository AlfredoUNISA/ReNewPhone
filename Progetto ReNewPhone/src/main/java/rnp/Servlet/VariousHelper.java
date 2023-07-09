package rnp.Servlet;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

/**
 * Interfaccia con varie funzioni e costanti utili a tutte le classi
 */
public interface VariousHelper {
	
	// Valori di tempo per i cookie 
	public static final int MINUTE = 60;
	public static final int HOUR = MINUTE * 60;
	public static final int DAY = HOUR * 24;
	
	public static final int COOKIE_DURATION = 1 * HOUR;
	
	// Colori console
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_YELLOW = "\033[0;33m";
	
	/**
	 * Ritorna il cookie con il nome specificato, null se non esiste.
	 * 
	 * @return il cookie se questo esiste, null altrimenti
	 */
	default Cookie getCookie(String name, HttpServletRequest request) {
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
	 * Manda un json (STRINGA) come risposta.
	 */
	default void sendJsonResponse(HttpServletResponse response, String jsonString) throws IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(jsonString);
	}
	
	/**
	 * Manda un json (JSONOBJECT) come risposta.
	 */
	default void sendJsonResponse(HttpServletResponse response, JsonObject json) throws IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(json);
	}
}
