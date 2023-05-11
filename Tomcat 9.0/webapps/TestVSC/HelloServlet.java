

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/HelloServlet")
public class HelloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public HelloServlet() {
        super();
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	doPost(req, resp);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
    	response.setContentType("text/html");  
    	PrintWriter out = response.getWriter();

    	String first_name = request.getParameter("firstname");
    	String last_name = request.getParameter("lastname");
    	
    	String result = ""
    		+ "<html>"
    		+ "<head><title>Hello</title></head>"
    		+ "<body>"
    		+ "Hello " + first_name + " " + last_name + "!<br/>"
    		+ "<a href='Start.html'>Rerurn to Start</a></body>"
    		+ "</html>";

    	out.println(result);  
    }


}
