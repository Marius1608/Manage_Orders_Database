package presentation;
import bll.BillBLL;
import bll.ClientsBLL;
import bll.OrdersBLL;
import bll.ProductsBLL;
import model.Bill;
import model.Clients;
import model.Orders;
import model.Products;
import start.TableUtils;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static start.TableUtils.populateComboBox;

/**
 * Clasa Controller reprezinta controlerul aplicatiei, care gestioneaza interactiunea dintre model si view
 */
public class Controller {

    private View view;
    private ClientsBLL clientsBLL;
    private ProductsBLL productsBLL;
    private OrdersBLL ordersBLL;
    private BillBLL billBLL;

    /**
     * Constructorul clasei Controller
     * @param view Interfata grafica a aplicatiei
     * @param clientsBLL Obiectul de acces la date pentru clienti
     * @param productsBLL Obiectul de acces la date pentru produse
     * @param ordersBLL Obiectul de acces la date pentru comenzi
     * @param billBLL Obiectul de acces la date pentru facturi
     */
    public Controller(View view, ClientsBLL clientsBLL, ProductsBLL productsBLL, OrdersBLL ordersBLL,BillBLL billBLL) {
        this.view = view;
        this.clientsBLL = clientsBLL;
        this.productsBLL = productsBLL;
        this.ordersBLL = ordersBLL;
        this.billBLL = billBLL;

        initView();
        initListeners();
    }

    /**
     * Initializeaza componentele interfetei grafice.
     */
    private void initView() {
        refreshClientsTable();
        refreshProductsTable();
        refreshOrdersTable();
        refreshBillsTable();
        populateClientCombo();
        populateProductCombo();
    }

    /**
     * Initializeaza ascultatorii pentru actiunile utilizatorului.
    */
    private void initListeners() {

        view.getBtnAddClient().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addClient();
            }
        });

        view.getBtnEditClient().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              editClient();
            }
        });

        view.getBtnDeleteClient().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              deleteClient();
            }
        });


        view.getBtnAddProduct().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              addProduct();
            }
        });

        view.getBtnEditProduct().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
             editProduct();
            }
        });

        view.getBtnDeleteProduct().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               deleteProduct();
            }
        });



        view.getBtnCreateOrder().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    createOrder();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        view.getBtnAddBill().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addBill();
            }
        });
    }


    /**
     * Actualizeaza tabelul de clienti
     */
    public void refreshClientsTable() {
        List<Clients> clients = clientsBLL.getAllClients();
        TableUtils.populateTable(view.getTableClients(), clients);
    }

    /**
     * Actualizeaza tabelul de produse
     */
    private void refreshProductsTable() {
        List<Products> products = productsBLL.getAllProducts();
        TableUtils.populateTable(view.getTableProducts(), products);
    }

    /**
     * Actualizeaza tabelul de comenzi
     */
    private void refreshOrdersTable() {
        List<Orders> orders = ordersBLL.getAllOrders();
        TableUtils.populateTable(view.getTableOrders(), orders);
    }

    /**
     * Actualizeaza tabelul de facturi
     */
    private void refreshBillsTable() {
        List<Bill> bills = billBLL.getAllBills();
        TableUtils.populateTable(view.getTableBills(), bills);
    }


    /**
     * Populează combobox-ul pentru clienți cu datele din baza de date
     */
    private void populateClientCombo() {
        List<Clients> clientList = clientsBLL.getAllClients();
        populateComboBox(view.getComboClient(), clientList);
    }

    /**
     * Populează combobox-ul pentru produse cu datele din baza de date
     */
    private void populateProductCombo() {
        List<Products> productList = productsBLL.getAllProducts();
        populateComboBox(view.getComboProduct(), productList);
    }

    /**
     * Este responsabila pentru golirea campurilor de introducere a datelor pentru produs
     * Aceasta asigura ca dupa adaugarea sau editarea unui produs, campurile sunt resetate la valorile implicite
     */
    private void clearProductFields() {

        view.getTxtProductId().setText("");
        view.getTxtProductName().setText("");
        view.getTxtProductPrice().setText("");
        view.getTxtProductStock().setText("");
    }

    /**
     * Este responsabila pentru golirea campurilor de introducere a datelor pentru client
     * Aceasta asigura ca dupa adaugarea sau editarea unui produs, campurile sunt resetate la valorile implicite
     */
    private void clearClientFields() {

        view.getTxtClientId().setText("");
        view.getTxtClientName().setText("");
        view.getTxtClientEmail().setText("");
        view.getTxtClientAge().setText("");
    }


    /**
     *  Se ocupa de adaugarea unui nou client n sistem
     *  Ea preia informatiile introduse în campurile de introducere, valideaza varsta și apoi adauga clientul în baza de date
     *  In cazul în care varsta este în afara intervalului permis (18 - 100), un mesaj corespunzator este afișat în zona de mesaje
     */
    private void addClient() {

        String name = view.getTxtClientName().getText();
        String email = view.getTxtClientEmail().getText();
        int age = Integer.parseInt(view.getTxtClientAge().getText());


        Clients client = new Clients(name, email,age);
        if (age<18 || age>100) {
            view.getTxtAreaMessages().setText("Age must be between 18 and 100");
            return;
        }
        clientsBLL.insertClient(client);
        refreshClientsTable();
        populateClientCombo();
    }

    /**
     * Permite editarea informatiilor despre un client existent in sistem
     * Ea extrage datele introduse pentru clientul curent, valideaza informatiile si actualizeaza inregistrarea clientului in baza de date
     */
    public void editClient(){

        int selectedRow = view.getTableClients().getSelectedRow();
        if (selectedRow != -1) {
            try {
                int clientId = Integer.parseInt(view.getTxtClientId().getText());
                String name = view.getTxtClientName().getText();
                String email = view.getTxtClientEmail().getText();
                int age = Integer.parseInt(view.getTxtClientAge().getText());

                Clients updatedClient = new Clients(clientId, name, email, age);
                clientsBLL.updateClient(updatedClient);

                refreshClientsTable();
                populateClientCombo();
                clearClientFields();
            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(view, "Invalid input! Please enter valid data.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     *  Sterge un client existent din sistem
     *  Aceasta identifica clientul selectat in tabel, extrage ID-ul acestuia si il utilizeaza pentru a sterge inregistrarea corespunzatoare din baza de date
     */
    public void deleteClient() {
        int selectedRow = view.getTableClients().getSelectedRow();
        if (selectedRow != -1) {
            int clientId = Integer.parseInt(view.getTxtClientId().getText());
            clientsBLL.deleteClient(clientId);
            refreshClientsTable();
            populateClientCombo();
            clearClientFields();
        } else {
            JOptionPane.showMessageDialog(view, "Please select a client to delete.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }



    /**
     *  Se ocupa de adaugarea unui nou client n sistem
     *  Ea preia informatiile introduse în campurile de introducere, valideaza varsta și apoi adauga clientul în baza de date
     *  In cazul în care varsta este în afara intervalului permis (18 - 100), un mesaj corespunzator este afișat în zona de mesaje
     */
    public void addProduct(){

        String name = view.getTxtProductName().getText();
        int price = Integer.parseInt(view.getTxtProductPrice().getText());
        int stock = Integer.parseInt(view.getTxtProductStock().getText());

        Products newProduct = new Products(name, price, stock);
        productsBLL.insertProduct(newProduct);

        refreshProductsTable();
        populateProductCombo();
    }

    /**
     * Permite editarea informatiilor despre un produs existent in sistem
     * Ea extrage datele introduse pentru produsul curent, valideaza informatiile si actualizeaza inregistrarea produsului in baza de date
     */
    public void editProduct(){

        int selectedRow = view.getTableProducts().getSelectedRow();
        if (selectedRow != -1) {
            try {

                int productId = Integer.parseInt(view.getTxtProductId().getText());
                String name = view.getTxtProductName().getText();
                int price = Integer.parseInt(view.getTxtProductPrice().getText());
                int stock = Integer.parseInt(view.getTxtProductStock().getText());

                Products updatedProduct = new Products(productId, name, price, stock);
                productsBLL.updateProduct(updatedProduct);

                refreshProductsTable();
                populateProductCombo();

                clearProductFields();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view, "Invalid input! Please enter valid data.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    /**
     *  Sterge un produs existent din sistem
     *  Aceasta identifica produsul selectat in tabel, extrage ID-ul acestuia si il utilizeaza pentru a sterge inregistrarea corespunzatoare din baza de date
     */
    public void deleteProduct() {
        int selectedRow = view.getTableProducts().getSelectedRow();
        if (selectedRow != -1) {
            int productId = Integer.parseInt(view.getTxtProductId().getText());
            productsBLL.deleteProduct(productId);
            refreshProductsTable();
            populateProductCombo();
            clearProductFields();
        } else {
            JOptionPane.showMessageDialog(view, "Please select a product to delete.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    /**
     *  Este responsabila pentru crearea unei noi comenzi in sistem
     *  Ea extrage numele clientului si numele produsului selectate din combobox-uri, precum si cantitatea introdusa in campul corespunzator
     *  Apoi, se verifica daca clientul si produsul sunt disponibile si daca cantitatea de produse este suficienta
     *  Daca totul este in regula, se creeaza o noua comanda si se actualizeaza stocul de produse
     *  De asemenea, se calculeaza pretul total al comenzii, se adauga o noua factura pentru comanda si se actualizeaza tabelele corespunzatoare
     *  Daca apar erori in proces, utilizatorul este informat printr-un mesaj corespunzator
     * @throws SQLException
     */
    public void createOrder() throws SQLException {

        String clientName = view.getComboClient().getSelectedItem().toString();
        String productName = view.getComboProduct().getSelectedItem().toString();
        int quantity = Integer.parseInt(view.getTxtOrderQuantity().getText());

        Clients client = clientsBLL.getClientByName(clientName);
        if (client == null) {
            view.getTxtAreaMessages().setText("Client not found.");
            return;
        }

        Products product = productsBLL.getProductByName(productName);
        if (product == null) {
            view.getTxtAreaMessages().setText("Product not found.");
            return;
        }

        if (quantity<0) {
            view.getTxtAreaMessages().setText("No negative numbers");
            return;
        }

        if (product.getStock() < quantity) {
            view.getTxtAreaMessages().setText("Under-stock: Not enough products available.");
            return;
        }

        int price=quantity*product.getPrice();
        Orders order = new Orders(client.getId(), product.getId(), quantity,price);
        ordersBLL.insertOrder(order);

        product.setStock(product.getStock() - quantity);
        productsBLL.updateProduct(product);

        int orderId=order.getId();
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        Timestamp timestamp=Timestamp.valueOf(date.atTime(time));
        int totalAmount = calculateTotalAmount(orderId);
        Bill newBill = new Bill(orderId, timestamp, totalAmount);
        billBLL.insertBill(newBill);
        refreshBillsTable();

        view.getTxtAreaMessages().setText("Order created successfully.");
        refreshProductsTable();
        refreshOrdersTable();
    }

    /**
     * Adauga o noua factura in sistem
     * Utilizatorul introduce ID-ul comenzii pentru care se emite factura
     * Metoda verifica daca ID-ul introdus este valid si calculeaza pretul total al comenzii
     * Apoi, o noua factura este creata si adaugata in baza de date
     * Daca apare vreo eroare, utilizatorul este informat printr-un mesaj de eroare
     */
    private void addBill() {
        try {
            int orderId = Integer.parseInt(view.getTxtBillOrderId().getText());
            LocalDate date = LocalDate.now();
            LocalTime time = LocalTime.now();
            Timestamp timestamp=Timestamp.valueOf(date.atTime(time));
            int totalAmount = calculateTotalAmount(orderId);
            Bill newBill = new Bill(orderId, timestamp, totalAmount);
            billBLL.insertBill(newBill);
            refreshBillsTable();
            JOptionPane.showMessageDialog(view, "Bill added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Invalid order ID! Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "An error occurred while adding the bill: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     *  Calculeaza pretul total al unei comenzi pe baza ID-ului comenzii
     *  Aceasta extrage cantitatea si pretul produsului din baza de date si returneaza produsul dintre cele doua valori
     * @param orderId
     * @return
     * @throws SQLException
     */
    private int calculateTotalAmount(int orderId) throws SQLException {
        Orders order = ordersBLL.findOrderById(orderId);
        int quantity = order.getQuantity();
        int productPrice = productsBLL.findProductById(order.getProduct_id()).getPrice();
        return productPrice * quantity;
    }


}
