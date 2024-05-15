package bll;
import java.util.ArrayList;
import java.util.List;
import bll.validators.OrderQuantityValidator;
import bll.validators.StockDecrementValidator;
import bll.validators.Validator;
import dao.OrdersDAO;
import model.Orders;

/**
 * Clasa de logica pentru manipularea comenzilor
 */

public class OrdersBLL {

    private List<Validator<Orders>> validators;
    private OrdersDAO ordersDAO;

    public OrdersBLL() {
        validators = new ArrayList<>();
        validators.add(new OrderQuantityValidator());
        validators.add(new StockDecrementValidator());
        this.ordersDAO = new OrdersDAO();
    }

    /**
     * Gaseste o comanda dupa ID
     *
     * @param id ID-ul comenzii de cautat
     * @return Comanda gasita sau null daca nu exista
     */
    public Orders findOrderById(int id) {
        return ordersDAO.findById(id);
    }

    /**
     * Insereaza o comanda in baza de date
     *
     * @param order Comanda de inserat
     * @return Comanda inserata
     * @throws IllegalArgumentException daca comanda nu respecta regulile de validare
     */
    public Orders insertOrder(Orders order) {
        for (Validator<Orders> validator : validators) {
            validator.validate(order);
        }
        return ordersDAO.insert(order);
    }

    /**
     * Returneaza o lista cu toate comenzile din baza de date
     *
     * @return Lista cu toate comenzile
     */
    public List<Orders> getAllOrders() {
        return ordersDAO.getAll();
    }

}
