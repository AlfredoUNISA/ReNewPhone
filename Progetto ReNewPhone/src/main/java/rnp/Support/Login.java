package rnp.Support;

import java.io.IOException;
import java.security.SecureRandom;
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

	/**
	 * Utilizzato per autentificare l'utente, creare una sessione e un cookie.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

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

			System.out.println("Normale: " + Integer.toString(id));
			String encr = null;
			String decript = null;
			try {
				encr = encrypt(Integer.toString(id));
				System.out.println("Criptata: " + encr);
				
				decript = decrypt(encr);
				System.out.println("Decriptata: " + decript);
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

	static final String KEY = "UnaChiaveFissa16"; // La chiave fissa deve avere una lunghezza di 16 byte (128 bit) per
	// l'algoritmo AES.

	public static String encrypt(String strToEncrypt) throws Exception {
		// Genera un vettore di inizializzazione (IV) casuale.
		byte[] iv = new byte[16];
		SecureRandom secureRandom = new SecureRandom();
		secureRandom.nextBytes(iv);
		GCMParameterSpec GCMPS= new GCMParameterSpec(iv.length, iv);

		// Crea l'oggetto SecretKeySpec con la chiave fissa.
		SecretKeySpec secretKeySpec = new SecretKeySpec(KEY.getBytes("UTF-8"), "AES");

		// Inizializza l'oggetto Cipher per l'operazione di crittografia.
		Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
		cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, GCMPS);

		// Crittografa la stringa fornita.
		byte[] encryptedBytes = cipher.doFinal(strToEncrypt.getBytes("UTF-8"));

		// Combina il vettore di inizializzazione e i dati crittografati come risultato
		// finale.
		byte[] combinedBytes = new byte[iv.length + encryptedBytes.length];
		System.arraycopy(iv, 0, combinedBytes, 0, iv.length);
		System.arraycopy(encryptedBytes, 0, combinedBytes, iv.length, encryptedBytes.length);

		// Converte il risultato in una stringa base64 per una migliore
		// rappresentazione.
		return Base64.getEncoder().encodeToString(combinedBytes);
	}

	public static String decrypt(String strToDecrypt) throws Exception {
		// Decodifica la stringa base64 per ottenere i byte combinati (IV + dati
		// crittografati).
		byte[] combinedBytes = Base64.getDecoder().decode(strToDecrypt);

		// Estrapola il vettore di inizializzazione (IV) dai byte combinati.
		byte[] iv = new byte[16];
		GCMParameterSpec GCMPS= new GCMParameterSpec(iv.length, iv);
		System.arraycopy(combinedBytes, 0, iv, 0, iv.length);

		// Crea l'oggetto SecretKeySpec con la chiave fissa.
		SecretKeySpec secretKeySpec = new SecretKeySpec(KEY.getBytes("UTF-8"), "AES");

		// Inizializza l'oggetto Cipher per l'operazione di decrittografia.
		Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
		cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, GCMPS);

		// Decrittografa i dati esclusi il vettore di inizializzazione (IV).
		byte[] encryptedBytes = new byte[combinedBytes.length - iv.length];
		System.arraycopy(combinedBytes, iv.length, encryptedBytes, 0, encryptedBytes.length);

		byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

		// Converte i byte decrittografati in una stringa.
		return new String(decryptedBytes, "UTF-8");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
}
