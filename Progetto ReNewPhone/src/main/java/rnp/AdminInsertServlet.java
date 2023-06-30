package rnp;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;


/**
 * Servlet implementation class AdminAddServlet
 */
@WebServlet("/AdminInsert")
@MultipartConfig
public class AdminInsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    Part filePart = request.getPart("file");
	    String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
	    InputStream fileContent = filePart.getInputStream();
	}

}
