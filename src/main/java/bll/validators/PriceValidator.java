package bll.validators;
import model.Products;

/**
 * Validator pentru verificarea pretului unui produs
 */
public class PriceValidator implements Validator<Products> {

    /**
     * Verifica dacă pretul produsului este un numar pozitiv
     *
     * @param product Produsul de validat
     * @throws IllegalArgumentException daca pretul produsului este negativ
     */
    public void validate(Products product) {
        if (product.getPrice() < 0) {
            throw new IllegalArgumentException("Price cannot be negative!");
        }
    }
}
