package bll;
import dao.BillDAO;
import model.Bill;
import java.util.List;

/**
 * Clasa de logica pentru manipularea facturilor
 */

public class BillBLL {

    private BillDAO billDAO;

    /**
     * Constructor pentru clasa BillBLL
     * Initializeaza obiectul BillDAO
     */
    public BillBLL() {
        this.billDAO = new BillDAO();
    }

    /**
     * Insereaza o factura în baza de date
     *
     * @param bill Factura de inserat
     */
    public void insertBill(Bill bill) {
        billDAO.insert(bill);
    }

    /**
     * Returneaza o lista cu toate facturile din baza de date
     *
     * @return Lista cu toate facturile
     */
    public List<Bill> getAllBills() {
        return billDAO.getAll();
    }

}
