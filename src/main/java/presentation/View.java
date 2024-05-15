package presentation;
import javax.swing.*;
import java.awt.*;

/**
 * Clasa View reprezinta interfata grafica a aplicatiei
 * Aceasta contine elementele vizuale precum butoane, campuri de text si tabele pentru gestionarea clientilor, produselor, comenzilor si facturilor
 */
public class View extends JFrame {

    private JFrame frame;
    private JButton btnAddClient, btnEditClient, btnDeleteClient;
    private JButton btnAddProduct, btnEditProduct, btnDeleteProduct;
    private JButton btnCreateOrder;
    private JTable tableClients, tableProducts, tableOrders,tableBills;
    private JTextField txtClientId, txtClientName, txtClientEmail,txtClientAge;
    private JTextField txtProductId, txtProductName, txtProductPrice, txtProductStock;
    private JComboBox<String> comboClient, comboProduct;
    private JTextField txtOrderQuantity;
    private JTextArea txtAreaMessages;
    private JButton btnAddBill;
    private JTextField txtBillOrderId;

    /**
     * Constructorul clasei View
     * Initializeaza interfata grafica
     */
    public View() {
        initialize();
    }

    /**
     * Metoda privata care initializeaza componentele interfetei grafice
     */
    private void initialize() {

        frame = new JFrame();
        frame.setTitle("Orders Management System");
        frame.setBounds(100, 100, 800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.setBackground(Color.cyan);

        Color cyanColor = new Color(0, 200, 200);

        btnAddClient = new JButton("Add Client");
        btnAddClient.setBounds(20, 20, 120, 30);
        btnAddClient.setBackground(cyanColor);
        frame.getContentPane().add(btnAddClient);

        btnEditClient = new JButton("Edit Client");
        btnEditClient.setBounds(20, 60, 120, 30);
        btnEditClient.setBackground(cyanColor);
        frame.getContentPane().add(btnEditClient);

        btnDeleteClient = new JButton("Delete Client");
        btnDeleteClient.setBounds(20, 100, 120, 30);
        btnDeleteClient.setBackground(cyanColor);
        frame.getContentPane().add(btnDeleteClient);

        Color cyanTextColor = new Color(0, 200, 200);

        btnAddProduct = new JButton("Add Product");
        btnAddProduct.setBounds(400, 20, 120, 30);
        btnAddProduct.setBackground(cyanColor);
        frame.getContentPane().add(btnAddProduct);

        btnEditProduct = new JButton("Edit Product");
        btnEditProduct.setBounds(400, 60, 120, 30);
        btnEditProduct.setBackground(cyanColor);
        frame.getContentPane().add(btnEditProduct);

        btnDeleteProduct = new JButton("Delete Product");
        btnDeleteProduct.setBounds(400, 100, 120, 30);
        btnDeleteProduct.setBackground(cyanColor);
        frame.getContentPane().add(btnDeleteProduct);



        btnCreateOrder = new JButton("Create Order");
        btnCreateOrder.setBounds(780, 20, 120, 30);
        btnCreateOrder.setBackground(cyanColor);
        frame.getContentPane().add(btnCreateOrder);


        tableClients = new JTable();
        JScrollPane scrollPaneClients = new JScrollPane();
        scrollPaneClients.setBounds(20, 150, 400, 200);
        scrollPaneClients.setViewportView(tableClients);
        frame.getContentPane().add(scrollPaneClients);

        tableProducts = new JTable();
        JScrollPane scrollPaneProducts = new JScrollPane();
        scrollPaneProducts.setBounds(440, 150, 400, 200);
        scrollPaneProducts.setViewportView(tableProducts);
        frame.getContentPane().add(scrollPaneProducts);

        tableOrders = new JTable();
        JScrollPane scrollPaneOrders = new JScrollPane();
        scrollPaneOrders.setBounds(860, 150, 400, 200);
        scrollPaneOrders.setViewportView(tableOrders);
        frame.getContentPane().add(scrollPaneOrders);



        JLabel lblClientId = new JLabel("ID:");
        lblClientId.setBounds(20, 370, 50, 20);
        frame.getContentPane().add(lblClientId);

        txtClientId = new JTextField();
        txtClientId.setBounds(80, 370, 100, 20);
        txtClientId.setBackground(cyanTextColor);
        frame.getContentPane().add(txtClientId);

        JLabel lblClientName = new JLabel("Name:");
        lblClientName.setBounds(20, 400, 50, 20);
        frame.getContentPane().add(lblClientName);

        txtClientName = new JTextField();
        txtClientName.setBounds(80, 400, 100, 20);
        txtClientName.setBackground(cyanTextColor);
        frame.getContentPane().add(txtClientName);

        JLabel lblClientEmail = new JLabel("Email:");
        lblClientEmail.setBounds(20, 430, 50, 20);
        frame.getContentPane().add(lblClientEmail);

        txtClientEmail = new JTextField();
        txtClientEmail.setBounds(80, 430, 100, 20);
        txtClientEmail.setBackground(cyanTextColor);
        frame.getContentPane().add(txtClientEmail);

        JLabel lblClientAge = new JLabel("Age:");
        lblClientAge.setBounds(20, 460, 50, 20);
        frame.getContentPane().add(lblClientAge);

        txtClientAge = new JTextField();
        txtClientAge.setBounds(80, 460, 100, 20);
        txtClientAge.setBackground(cyanTextColor);
        frame.getContentPane().add(txtClientAge);


        JLabel lblProductId = new JLabel("ID:");
        lblProductId.setBounds(400, 370, 50, 20);
        frame.getContentPane().add(lblProductId);

        txtProductId = new JTextField();
        txtProductId.setBounds(460, 370, 100, 20);
        txtProductId.setBackground(cyanTextColor);
        frame.getContentPane().add(txtProductId);

        JLabel lblProductName = new JLabel("Name:");
        lblProductName.setBounds(400, 400, 50, 20);
        frame.getContentPane().add(lblProductName);

        txtProductName = new JTextField();
        txtProductName.setBounds(460, 400, 100, 20);
        txtProductName.setBackground(cyanTextColor);
        frame.getContentPane().add(txtProductName);

        JLabel lblProductPrice = new JLabel("Price:");
        lblProductPrice.setBounds(400, 430, 50, 20);
        frame.getContentPane().add(lblProductPrice);

        txtProductPrice = new JTextField();
        txtProductPrice.setBounds(460, 430, 100, 20);
        txtProductPrice.setBackground(cyanTextColor);
        frame.getContentPane().add(txtProductPrice);

        JLabel lblProductStock = new JLabel("Stock:");
        lblProductStock.setBounds(400, 460, 50, 20);
        frame.getContentPane().add(lblProductStock);

        txtProductStock = new JTextField();
        txtProductStock.setBounds(460, 460, 100, 20);
        txtProductStock.setBackground(cyanTextColor);
        frame.getContentPane().add(txtProductStock);



        comboClient = new JComboBox<>();
        comboClient.setBounds(20, 490, 200, 20);
        frame.getContentPane().add(comboClient);

        comboProduct = new JComboBox<>();
        comboProduct.setBounds(400, 490, 200, 20);
        frame.getContentPane().add(comboProduct);



        JLabel lblOrderQuantity = new JLabel("Quantity:");
        lblOrderQuantity.setBounds(20, 520, 70, 20);
        frame.getContentPane().add(lblOrderQuantity);

        txtOrderQuantity = new JTextField();
        txtOrderQuantity.setBounds(100, 520, 100, 20);
        txtOrderQuantity.setBackground(cyanTextColor);
        frame.getContentPane().add(txtOrderQuantity);



        txtAreaMessages = new JTextArea();
        txtAreaMessages.setEditable(false);
        JScrollPane scrollPaneMessages = new JScrollPane(txtAreaMessages);
        scrollPaneMessages.setBounds(20, 550, 730, 100);
        frame.getContentPane().add(scrollPaneMessages);



        JLabel lblBills = new JLabel("Bills:");
        lblBills.setBounds(860, 370, 50, 20);
        frame.getContentPane().add(lblBills);

        tableBills = new JTable();
        JScrollPane scrollPaneBills = new JScrollPane();
        scrollPaneBills.setBounds(860, 400, 400, 200);
        scrollPaneBills.setViewportView(tableBills);
        frame.getContentPane().add(scrollPaneBills);

        btnAddBill = new JButton("Add Bill");
        btnAddBill.setBounds(780, 60, 120, 30);
        btnAddBill.setBackground(cyanColor);
        frame.getContentPane().add(btnAddBill);

        txtBillOrderId = new JTextField();
        txtBillOrderId.setBounds(860, 620, 100, 20);
        txtBillOrderId.setBackground(cyanTextColor);
        frame.getContentPane().add(txtBillOrderId);


    }

    /**
     * Metoda care afiseaza fereastra interfetei grafice
     */
    public void show() {
        frame.setVisible(true);
    }


    public JButton getBtnAddClient() {
        return btnAddClient;
    }

    public JButton getBtnEditClient() {
        return btnEditClient;
    }

    public JButton getBtnDeleteClient() {
        return btnDeleteClient;
    }


    public JButton getBtnAddProduct() {
        return btnAddProduct;
    }

    public JButton getBtnEditProduct() {
        return btnEditProduct;
    }

    public JButton getBtnDeleteProduct() {
        return btnDeleteProduct;
    }


    public JButton getBtnCreateOrder() {
        return btnCreateOrder;
    }


    public JTextField getTxtClientId() {
        return txtClientId;
    }

    public JTextField getTxtClientName() {
        return txtClientName;
    }

    public JTextField getTxtClientEmail() {
        return txtClientEmail;
    }

    public JTextField getTxtClientAge() {
        return txtClientAge;
    }


    public JTextField getTxtProductId() {
        return txtProductId;
    }

    public JTextField getTxtProductName() {
        return txtProductName;
    }

    public JTextField getTxtProductPrice() {
        return txtProductPrice;
    }

    public JTextField getTxtProductStock() {
        return txtProductStock;
    }


    public JComboBox<String> getComboClient() {
        return comboClient;
    }

    public JComboBox<String> getComboProduct() {
        return comboProduct;
    }


    public JTextField getTxtOrderQuantity() {
        return txtOrderQuantity;
    }

    public JTextArea getTxtAreaMessages() {
        return txtAreaMessages;
    }


    public JTable getTableClients() {
        return tableClients;
    }

    public JTable getTableProducts() {
        return tableProducts;
    }

    public JTable getTableOrders() {
        return tableOrders;
    }



    public JTable getTableBills() { return tableBills; }

    public JButton getBtnAddBill() { return btnAddBill; }

    public JTextField getTxtBillOrderId() { return txtBillOrderId; }

}



