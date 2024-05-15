package model;
import dao.ProductsDAO;

/**
 * Clasa care reprezinta o comanda
 */
public class Orders {

    private int id;
    private int client_id;
    private int product_id;
    private int quantity;
    private int total;

    /**
     * Constructor pentru initializarea unei comenzi fara ID
     *
     * @param clientId   ID-ul clientului care plaseaza comanda
     * @param productId  ID-ul produsului din comanda
     * @param quantity   Cantitatea de produs din comanda
     * @param total      Costul total al comenzii
     */
    public Orders(int clientId, int productId, int quantity, int total) {
        this.client_id = clientId;
        this.product_id = productId;
        this.quantity = quantity;
        this.total = total;
    }

    /**
     * Constructor pentru initializarea unei comenzi cu ID
     *
     * @param id         ID-ul comenzii
     * @param clientId   ID-ul clientului care plaseaza comanda
     * @param productId  ID-ul produsului din comanda
     * @param quantity   Cantitatea de produs din comanda
     * @param total      Costul total al comenzii
     */
    public Orders(int id, int clientId, int productId, int quantity, int total) {
        this.id = id;
        this.client_id = clientId;
        this.product_id = productId;
        this.quantity = quantity;
        this.total = total;
    }

    /**
     * Returneaza ID-ul comenzii
     *
     * @return ID-ul comenzii
     */
    public int getId() {
        return id;
    }

    /**
     * Returneaza ID-ul produsului din comanda
     *
     * @return ID-ul produsului din comanda
     */
    public int getProduct_id() {
        return product_id;
    }

    /**
     * Returneaza cantitatea de produs din comanda
     *
     * @return Cantitatea de produs din comanda
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Returneaza produsul asociat comenzii
     *
     * @return Produsul asociat comenzii
     */
    public Products getProduct() {
        ProductsDAO productsDAO = new ProductsDAO();
        Products product = productsDAO.findById(product_id);
        return product;
    }

}
