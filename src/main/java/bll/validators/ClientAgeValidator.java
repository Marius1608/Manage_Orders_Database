package bll.validators;
import model.Clients;

/**
 * Validator pentru verificarea varstei unui client
 */
public class ClientAgeValidator implements Validator<Clients> {

    private final static int MIN_AGE = 18;
    private final static int MAX_AGE = 100;

    /**
     * Verifica daca varsta clientului respecta limitele permise
     *
     * @param client Clientul de validat
     * @throws IllegalArgumentException daca varsta clientului este in afara intervalului permis
     */
    public void validate(Clients client) {
        if (client.getAge() < MIN_AGE || client.getAge() > MAX_AGE) {
            throw new IllegalArgumentException("The Client Age limit is not respected!");
        }
    }
}
