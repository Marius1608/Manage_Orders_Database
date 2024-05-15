package model;

import java.sql.Timestamp;

/**
 * Clasa care reprezinta factura
 */
public class Bill {

    private final int order_id;
    private final Timestamp dates;
    private final int total_amount;

    /**
     * Constructor pentru initializarea unei noi instanțe de factura
     *
     * @param orderId     ID-ul comenzii asociate facturii
     * @param date        Data emiterii facturii
     * @param totalAmount Totalul sumei facturate
     * @throws IllegalArgumentException Dacă orderId sau totalAmount nu sunt pozitive sau daca date este null
     */
    public Bill(int orderId, Timestamp date, int totalAmount) {
        if (orderId <= 0 || totalAmount <= 0) {
            throw new IllegalArgumentException("Order ID and total amount must be positive");
        }
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        this.order_id = orderId;
        this.dates = date;
        this.total_amount = totalAmount;
    }

}
