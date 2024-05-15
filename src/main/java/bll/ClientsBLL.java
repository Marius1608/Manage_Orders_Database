package bll;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import bll.validators.ClientAgeValidator;
import bll.validators.EmailValidator;
import bll.validators.Validator;
import dao.ClientsDAO;
import model.Clients;

/**
 * Clasa de logica pentru manipularea clientilor
 */

public class ClientsBLL {

    private List<Validator<Clients>> validators;
    private ClientsDAO clientsDAO;

    /**
     * Constructor pentru clasa ClientsBLL
     * Initializează lista de validatori și obiectul ClientsDAO
     */
    public ClientsBLL() {
        validators = new ArrayList<>();
        validators.add(new EmailValidator());
        validators.add(new ClientAgeValidator());
        clientsDAO = new ClientsDAO();
    }

    /**
     * Gaseste un client dupa ID
     *
     * @param id ID-ul clientului de cautat
     * @return Clientul gasit sau null dacă nu exista
     */
    public Clients findClientById(int id) {
        return clientsDAO.findById(id);
    }

    /**
     * Insereaza un client în baza de date
     *
     * @param client Clientul de inserat
     * @return Clientul inserat
     * @throws IllegalArgumentException daca clientul nu respecta regulile de validare
     */
    public Clients insertClient(Clients client) {
        for (Validator<Clients> validator : validators) {
            validator.validate(client);
        }
        return clientsDAO.insert(client);
    }

    /**
     * Actualizeaza un client în baza de date
     *
     * @param client Clientul de actualizat
     * @throws IllegalArgumentException daca clientul nu respecta regulile de validare
     */
    public void updateClient(Clients client) {
        for (Validator<Clients> validator : validators) {
            validator.validate(client);
        }
        clientsDAO.update(client);
    }

    /**
     * Sterge un client din baza de date
     *
     * @param clientId ID-ul clientului de sters
     * @throws NoSuchElementException daca clientul nu poate fi gasit în baza de date
     */
    public void deleteClient(int clientId) {
        clientsDAO.delete(clientId);
    }

    /**
     * Returneaza o lista cu toti clientii din baza de date
     *
     * @return Lista cu toți clienții
     */
    public List<Clients> getAllClients() {
        return clientsDAO.getAll();
    }

    /**
     * Gaseste un client după nume
     *
     * @param name Numele clientului de cautat
     * @return Clientul gasit sau null dacă nu exista
     */
    public Clients getClientByName(String name) {
        return clientsDAO.getByName(name);
    }
}
