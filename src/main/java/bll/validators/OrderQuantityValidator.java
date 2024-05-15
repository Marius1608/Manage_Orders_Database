package bll.validators;
import model.Orders;
import model.Products;

/**
 * Validator pentru verificarea cantitatii de produse dintr-o comanda
 */
public class OrderQuantityValidator implements Validator<Orders> {

    /**
     * Verifica daca cantitatea de produse din comanda respecta stocul disponibil
     *
     * @param order Comanda de validat
     * @throws IllegalArgumentException daca cantitatea de produse din comanda depaseste stocul disponibil
     */
    public void validate(Orders order) {
        Products product = order.getProduct();
        if (product.getStock() < order.getQuantity()) {
            throw new IllegalArgumentException("There are not enough products in stock!");
        }
    }
}
