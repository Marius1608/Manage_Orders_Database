package start;
import bll.BillBLL;
import bll.ClientsBLL;
import bll.OrdersBLL;
import bll.ProductsBLL;
import presentation.Controller;
import presentation.View;

/**
 * Clasa Start est e punctul de intrare în aplicație
 * Initializeaza componentele necesare si lanseaza aplicația
 */

public class Start {

	/**
	 * Metoda principala a aplicatiei
	 *
	 * @param args Argumentele de linie de comanda
	 */
	public static void main(String[] args) {

			View view = new View();
			ClientsBLL clientsBLL1 = new ClientsBLL();
			ProductsBLL productsBLL1 = new ProductsBLL();
			OrdersBLL ordersBLL1 = new OrdersBLL();
			BillBLL billBLL1=new BillBLL();
			Controller controller = new Controller(view, clientsBLL1, productsBLL1, ordersBLL1,billBLL1);
			view.setVisible(true);

	}
}
