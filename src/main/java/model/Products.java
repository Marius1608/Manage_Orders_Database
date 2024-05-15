package model;

/**
 * Clasa care reprezinta un produs
 */
public class Products {

    private int id;
    private String name;
    private int price;
    private int stock;

    /**
     * Constructor pentru initializarea unui produs cu ID
     *
     * @param id     ID-ul produsului
     * @param name   Numele produsului
     * @param price  Pretul produsului
     * @param stock  Stocul disponibil pentru produs
     */
    public Products(int id, String name, int price, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    /**
     * Constructor pentru initializarea unui produs fara ID
     *
     * @param name   Numele produsului
     * @param price  Pretul produsului
     * @param stock  Stocul disponibil pentru produs
     */
    public Products(String name, int price, int stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    /**
     * Returneaza ID-ul produsului
     *
     * @return ID-ul produsului
     */
    public int getId() {
        return id;
    }

    /**
     * Returneaza numele produsului
     *
     * @return Numele produsului
     */
    public String getName() {
        return name;
    }

    /**
     * Returneaza pretul produsului
     *
     * @return Pretul produsului
     */
    public int getPrice() {
        return price;
    }

    /**
     * Returneaza stocul disponibil pentru produs
     *
     * @return Stocul disponibil pentru produs
     */
    public int getStock() {
        return stock;
    }

    /**
     * Seteaza ID-ul produsului
     *
     * @param id ID-ul produsului
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Seteaza stocul disponibil pentru produs
     *
     * @param stock Stocul disponibil pentru produs
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Returneaza reprezentarea sub forma de sir a numelui produsului
     *
     * @return Reprezentarea sub forma de sir a numelui produsului
     */
    @Override
    public String toString() {
        return name;
    }
}
