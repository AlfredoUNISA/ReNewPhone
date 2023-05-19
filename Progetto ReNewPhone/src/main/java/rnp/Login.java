package rnp;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Login
 */
@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		UserDAODataSource userDAO = new UserDAODataSource();
		int id = -1;
		
		try {
			id = userDAO.doRetrieveByCredentials(email, password);
		} catch (SQLException e) {
			System.out.println("ERROR: " + e);
		}
		
		if (id == -1) {
			System.out.println("Login non andato a buon fine");
			response.sendRedirect("access_page.jsp");
		} else {
			System.out.println("Hello user " + id);
			HttpSession session = request.getSession();
			session.setAttribute("user", id);	
			
			response.sendRedirect("index.jsp");
		}
		
	}

}
