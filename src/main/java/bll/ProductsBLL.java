package bll;
import java.util.ArrayList;
import java.util.List;
import bll.validators.PriceValidator;
import bll.validators.StockValidator;
import bll.validators.Validator;
import dao.ProductsDAO;
import model.Products;

/**
 * Clasa de logica pentru manipularea produselor
 *
 */

public class ProductsBLL {

    private List<Validator<Products>> validators;
    private ProductsDAO productsDAO;

    /**
     * Constructor pentru clasa ProductsBLL
     * Initializeaza lista de validatori si obiectul ProductsDAO
     */
    public ProductsBLL() {
        validators = new ArrayList<>();
        validators.add(new PriceValidator());
        validators.add(new StockValidator());
        productsDAO = new ProductsDAO();
    }

    /**
     * Gaseste un produs dupa ID
     *
     * @param id ID-ul produsului de cautat
     * @return Produsul gasit sau null daca nu exista
     */
    public Products findProductById(int id) {
        return productsDAO.findById(id);
    }

    /**
     * Insereaza un produs in baza de date
     *
     * @param product Produsul de inserat
     * @return Produsul inserat
     * @throws IllegalArgumentException daca produsul nu respecta regulile de validare
     */
    public Products insertProduct(Products product) {
        for (Validator<Products> validator : validators) {
            validator.validate(product);
        }
        return productsDAO.insert(product);
    }

    /**
     * Actualizeaza un produs in baza de date
     *
     * @param product Produsul de actualizat
     * @throws IllegalArgumentException daca produsul nu respecta regulile de validare
     */
    public void updateProduct(Products product) {
        for (Validator<Products> validator : validators) {
            validator.validate(product);
        }
        productsDAO.update(product);
    }

    /**
     * Sterge un produs din baza de date
     *
     * @param productId ID-ul produsului de sters
     */
    public void deleteProduct(int productId) {
        productsDAO.delete(productId);
    }

    /**
     * Returneaza o lista cu toate produsele din baza de date
     *
     * @return Lista cu toate produsele
     */
    public List<Products> getAllProducts() {
        return productsDAO.getAll();
    }

    /**
     * Gaseste un produs dupa nume.
     *
     * @param name Numele produsului de cautat
     * @return Produsul gasit sau null daca nu exista
     */
    public Products getProductByName(String name) {
        return productsDAO.getByName(name);
    }
}
