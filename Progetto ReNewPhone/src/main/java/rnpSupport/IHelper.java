package rnpSupport;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

public interface IHelper {
	/**
	 * Manda il json come risposta.
	 */
	default void sendJsonResponse(HttpServletResponse response, String jsonString) throws IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(jsonString);
	}

}
