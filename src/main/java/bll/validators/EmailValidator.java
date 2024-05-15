package bll.validators;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import model.Clients;

/**
 * Validator pentru verificarea formatului unei adrese de email
 */
public class EmailValidator implements Validator<Clients> {

	private final static String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

	/**
	 * Verifica dacă adresa de email a clientului respecta formatul
	 *
	 * @param client Clientul de validat
	 * @throws IllegalArgumentException dacă adresa de email nu respectă formatul
	 */
	public void validate(Clients client) {
		String email = client.getEmail();
		Pattern pattern = Pattern.compile(EMAIL_REGEX);
		Matcher matcher = pattern.matcher(email);
		if (!matcher.matches()) {
			throw new IllegalArgumentException("Invalid email format!");
		}
	}
}
