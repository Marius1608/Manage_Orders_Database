package bll.validators;
import model.Products;

/**
 * Validator pentru verificarea stocului unui produs
 */
public class StockValidator implements Validator<Products> {

    /**
     * Verifica daca stocul unui produs este un numar pozitiv sau zero
     *
     * @param product Produsul de validat
     * @throws IllegalArgumentException dacă stocul produsului este negativ
     */
    public void validate(Products product) {
        if (product.getStock() < 0) {
            throw new IllegalArgumentException("Stock cannot be negative!");
        }
    }
}
