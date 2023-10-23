package rnp.Support;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import rnp.DAO.UserDAODataSource;
import rnp.Servlet.VariousHelper;

/**
 * Servlet implementation class Login
 */
@WebServlet("/login")
public class Login extends HttpServlet implements VariousHelper {
	private static final long serialVersionUID = 1L;

	private static final String CLASS_NAME = Login.class.getName();
	private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);
	private static String FILE_PATH="placeholder";

	/**
	 * Utilizzato per autentificare l'utente, creare una sessione e un cookie.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		FILE_PATH=request.getServletContext().getRealPath("//WEB-INF//key.txt");

		String email = request.getParameter("email");
		String password = request.getParameter("password");

		UserDAODataSource userDAO = new UserDAODataSource();
		int id = -1;

		try {
			id = userDAO.doRetrieveByCredentials(email, password);
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, ANSI_RED + "ERROR [" + CLASS_NAME + "]: " + e.getMessage() + ANSI_RESET);
		}

		if (id == -1) {
			// Errore con le credenziali
			response.sendRedirect("login.jsp");
		} else {
			// Accesso consentito
			HttpSession session = request.getSession();
			session.setAttribute("user", id);

			//System.out.println("Normale: " + Integer.toString(id));
			String encr = null;
			//String decript = null;
			try {
				encr = encrypt(Integer.toString(id));
				//System.out.println("Criptata: " + encr);
				
				//decript = decrypt(encr);
				//System.out.println("Decriptata: " + decript);
			} catch (Exception e) {
				LOGGER.log(Level.SEVERE, ANSI_RED + "ERROR [" + CLASS_NAME + "]: " + e.getMessage() + ANSI_RESET);
			}

			// Imposta un cookie persistente per l'identificazione futura dell'utente
			Cookie userCookie = new Cookie("userCookie", encr);
			userCookie.setMaxAge(COOKIE_DURATION);
			response.addCookie(userCookie);

			response.sendRedirect("index.jsp");
		}

	}

	/**
	 * @return true se l'id è di un admin, false altrimenti
	 */
	public static Boolean isAdmin(int id) {
		if (id == -10)
			return true;
		else
			return false;
	}
	
	private static String readFileAsString() {
        StringBuilder content = new StringBuilder();
        
        // Per motivi di portabilità del progetto, se il path non esiste viene utilitzzata la seguente key
        if(!new File(FILE_PATH).exists()) {
        	//System.out.println("Il path non esiste");
        	return "myFixedKey123456";	
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
                content.append(System.lineSeparator());
            }
        } catch (IOException e) {
			LOGGER.log(Level.SEVERE, ANSI_RED + "ERROR [" + CLASS_NAME + "]: " + e.getMessage() + ANSI_RESET);
		}

        return content.toString().strip();
    }
	
	static final String KEY = readFileAsString(); // La chiave fissa deve avere una lunghezza di 16 byte (128 bit) per
	// l'algoritmo AES.

	public static String encrypt(String strToEncrypt) throws Exception {
		try {
			//System.out.println(KEY + ", bytes: " + KEY.getBytes().length);
            byte[] keyBytes = KEY.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "AES");

            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            byte[] iv = new byte[12]; // Vettore di inizializzazione di 12 byte
            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(128, iv);

            cipher.init(Cipher.ENCRYPT_MODE, secretKey, gcmParameterSpec);

            byte[] encryptedBytes = cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, ANSI_RED + "ERROR [" + CLASS_NAME + "]: " + e.getMessage() + ANSI_RESET);
        }
        return null;
	}

	public static String decrypt(String strToDecrypt) throws Exception {
		try {
			//System.out.println(KEY + ", bytes: " + KEY.getBytes().length);
            byte[] keyBytes = KEY.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "AES");

            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            byte[] iv = new byte[12]; // Vettore di inizializzazione di 12 byte
            //System.out.println("iv len: " + iv.length);
            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(128, iv);

            cipher.init(Cipher.DECRYPT_MODE, secretKey, gcmParameterSpec);

            byte[] encryptedBytes = Base64.getDecoder().decode(strToDecrypt);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, ANSI_RED + "ERROR [" + CLASS_NAME + "]: " + e.getMessage() + ANSI_RESET);
        }
        return null;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
}
