package bll.validators;
import dao.ProductsDAO;
import model.Orders;
import model.Products;

/**
 * Validator pentru verificarea decrementului stocului in urma unei comenzi
 */
public class StockDecrementValidator implements Validator<Orders> {

    /**
     * Verifica dacă decrementul stocului in urma unei comenzi este valid
     *
     * @param order Comanda de validat
     * @throws IllegalArgumentException daca cantitatea din comanda depaseste stocul disponibil sau daca produsul nu exista
     */
    public void validate(Orders order) {
        ProductsDAO productsDAO = new ProductsDAO();
        int productId = order.getProduct().getId();
        Products product = productsDAO.findById(productId);
        if (product != null) {
            int newStock = product.getStock() - order.getQuantity();
            if (newStock < 0) {
                throw new IllegalArgumentException("Invalid order quantity, stock cannot be negative!");
            }
        } else {
            throw new IllegalArgumentException("Product not found!");
        }
    }
}
