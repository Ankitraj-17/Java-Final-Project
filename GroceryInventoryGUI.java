import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class GroceryInventoryGUI extends JFrame {

    private static final String CURRENCY = "Rs. ";
    private static final Color GREEN = new Color(24, 115, 65);
    private static final Font FONT = new Font("SansSerif", Font.PLAIN, 14);
    private static final Font BOLD = new Font("SansSerif", Font.BOLD, 14);

    private JTextField txtName = new JTextField(), txtPrice = new JTextField(), txtQty = new JTextField(), txtMin = new JTextField(), txtSearch = new JTextField(20);
    private JLabel lblEntryMsg = new JLabel("Enter details and click Add Product."), lblCalcTotal = new JLabel("Total Inventory Value: " + CURRENCY + "0.00");
    private JLabel lblStockStatus = new JLabel("Click to inspect low-stock products."), lblReportSummary = new JLabel("Summary: Products: 0 | Units: 0 | Value: " + CURRENCY + "0.00"), lblFooter = new JLabel("Ready.");
    private DefaultTableModel calcModel, stockModel, reportModel, searchModel;

    // Launch window and setup layout
    public GroceryInventoryGUI() {
        super("Grocery Inventory Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel lblTitle = new JLabel("Grocery Inventory Calculator", SwingConstants.CENTER);
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 20));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setOpaque(true);
        lblTitle.setBackground(GREEN);
        add(lblTitle, BorderLayout.NORTH);

        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(BOLD);
        tabs.addTab("Product Entry", buildEntryTab());
        tabs.addTab("Inventory Calculation", buildCalcTab());
        tabs.addTab("Stock Validation", buildStockTab());
        tabs.addTab("Product Search", buildSearchTab());
        tabs.addTab("Inventory Report", buildReportTab());

        tabs.addChangeListener(e -> {
            int idx = tabs.getSelectedIndex();
            if (idx == 1) refreshCalcTable();
            else if (idx == 2) refreshStockTable();
            else if (idx == 4) refreshReportTable();
        });

        add(tabs, BorderLayout.CENTER);
        lblFooter.setFont(FONT);
        add(lblFooter, BorderLayout.SOUTH);
    }

    // Helper to create a green button with macOS opacity support
    private JButton createGreenButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(BOLD);
        btn.setBackground(GREEN);
        btn.setForeground(Color.WHITE);
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        return btn;
    }

    // Helper to create a styled table inside a JScrollPane
    private JScrollPane createTablePanel(String[] cols, DefaultTableModel[] outModel) {
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        outModel[0] = model;
        JTable table = new JTable(model);
        table.setRowHeight(25);
        table.setFont(FONT);
        table.getTableHeader().setFont(BOLD);
        table.getTableHeader().setBackground(new Color(225, 242, 230));
        return new JScrollPane(table);
    }

    // Build Tab 1: Product Entry form
    private JPanel buildEntryTab() {
        JPanel panel = new JPanel(new BorderLayout(10, 15));
        JPanel form = new JPanel(new GridLayout(4, 2, 10, 12));
        form.add(new JLabel("Product Name:")); form.add(txtName);
        form.add(new JLabel("Unit Price (" + CURRENCY.trim() + "):")); form.add(txtPrice);
        form.add(new JLabel("Quantity in Stock:")); form.add(txtQty);
        form.add(new JLabel("Minimum Stock:")); form.add(txtMin);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        JButton btnAdd = createGreenButton("Add Product"), btnClear = new JButton("Clear");
        btnPanel.add(btnAdd); btnPanel.add(btnClear);

        JPanel center = new JPanel(new BorderLayout(10, 15));
        center.add(form, BorderLayout.NORTH);
        center.add(btnPanel, BorderLayout.CENTER);
        lblEntryMsg.setFont(FONT);
        lblEntryMsg.setHorizontalAlignment(SwingConstants.CENTER);
        center.add(lblEntryMsg, BorderLayout.SOUTH);
        panel.add(center, BorderLayout.NORTH);

        btnAdd.addActionListener(e -> addProduct());
        btnClear.addActionListener(e -> clearForm());
        return panel;
    }

    // Validate and add product to InventoryManager
    private void addProduct() {
        String name = txtName.getText().trim();
        if (name.isEmpty()) {
            lblEntryMsg.setText("[!] Product name cannot be blank.");
            lblEntryMsg.setForeground(Color.RED);
            return;
        }
        if (InventoryManager.nameExists(name)) {
            lblEntryMsg.setText("[!] Product '" + name + "' already exists.");
            lblEntryMsg.setForeground(Color.RED);
            return;
        }

        double price;
        try {
            price = Double.parseDouble(txtPrice.getText().trim());
            // Logical OR (||) check for valid non-negative price
            if (price < 0.0 || Double.isNaN(price) || Double.isInfinite(price)) {
                lblEntryMsg.setText("[!] Price cannot be negative or invalid.");
                lblEntryMsg.setForeground(Color.RED);
                return;
            }
        } catch (NumberFormatException ex) {
            lblEntryMsg.setText("[!] Price must be a valid number.");
            lblEntryMsg.setForeground(Color.RED);
            return;
        }

        int qty, min;
        try {
            qty = Integer.parseInt(txtQty.getText().trim());
            min = Integer.parseInt(txtMin.getText().trim());
            // Logical OR (||) check for valid non-negative ranges
            if (qty < 0 || min < 0 || min > 1000000) {
                lblEntryMsg.setText("[!] Quantity must be >= 0 and Min Stock between 0 and 1,000,000.");
                lblEntryMsg.setForeground(Color.RED);
                return;
            }
        } catch (NumberFormatException ex) {
            lblEntryMsg.setText("[!] Quantity and Min Stock must be integers.");
            lblEntryMsg.setForeground(Color.RED);
            return;
        }

        if (InventoryManager.isFull()) {
            lblEntryMsg.setText("[!] Inventory is full (max 100).");
            lblEntryMsg.setForeground(Color.RED);
            return;
        }

        InventoryManager.addProduct(name, price, qty, min);
        // Logical AND (&&) check if newly added item is below threshold
        if (qty < min && qty > 0) {
            lblEntryMsg.setText("[OK] Added '" + name + "'! (Already below minimum stock)");
            lblEntryMsg.setForeground(new Color(180, 100, 0));
        } else {
            lblEntryMsg.setText("[OK] Added '" + name + "' successfully!");
            lblEntryMsg.setForeground(new Color(0, 130, 0));
        }
        lblFooter.setText("Last action: Added product " + name);
        clearForm();
    }

    // Reset entry fields
    private void clearForm() {
        txtName.setText(""); txtPrice.setText(""); txtQty.setText(""); txtMin.setText("");
        txtName.requestFocus();
    }

    // Build Tab 2: Inventory Calculation
    private JPanel buildCalcTab() {
        JPanel panel = new JPanel(new BorderLayout(10, 10)), top = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
        JButton btn = createGreenButton("Calculate");
        btn.addActionListener(e -> refreshCalcTable());
        top.add(btn); top.add(lblCalcTotal);
        panel.add(top, BorderLayout.NORTH);

        DefaultTableModel[] holder = new DefaultTableModel[1];
        panel.add(createTablePanel(new String[]{"Name", "Price (" + CURRENCY.trim() + ")", "Quantity", "Value (" + CURRENCY.trim() + ")"}, holder), BorderLayout.CENTER);
        calcModel = holder[0];
        return panel;
    }

    // Populate Tab 2 calculation table
    private void refreshCalcTable() {
        calcModel.setRowCount(0);
        int count = InventoryManager.getProductCount();
        for (int i = 0; i < count; i++) {
            double val = InventoryManager.calculateValue(InventoryManager.getPrice(i), InventoryManager.getQuantity(i));
            calcModel.addRow(new Object[]{InventoryManager.getName(i), String.format("%.2f", InventoryManager.getPrice(i)), InventoryManager.getQuantity(i), String.format("%.2f", val)});
        }
        if (count > 0) {
            calcModel.addRow(new Object[]{"TOTAL", "", "", String.format("%.2f", InventoryManager.calculateTotalValue())});
        }
        lblCalcTotal.setText(String.format("Total Inventory Value: %s%.2f", CURRENCY, InventoryManager.calculateTotalValue()));
        lblFooter.setText("Last action: Calculated inventory values.");
    }

    // Build Tab 3: Stock Validation
    private JPanel buildStockTab() {
        JPanel panel = new JPanel(new BorderLayout(10, 10)), top = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
        JButton btn = createGreenButton("Show Low-Stock Products");
        btn.addActionListener(e -> refreshStockTable());
        top.add(btn); top.add(lblStockStatus);
        panel.add(top, BorderLayout.NORTH);

        DefaultTableModel[] holder = new DefaultTableModel[1];
        panel.add(createTablePanel(new String[]{"Name", "Current", "Min Stock", "Deficit", "Level"}, holder), BorderLayout.CENTER);
        stockModel = holder[0];
        return panel;
    }

    // Populate Tab 3 low stock table
    private void refreshStockTable() {
        stockModel.setRowCount(0);
        int count = InventoryManager.getProductCount(), low = 0;
        for (int i = 0; i < count; i++) {
            int q = InventoryManager.getQuantity(i), m = InventoryManager.getMinStock(i);
            if (InventoryManager.isLowStock(q, m)) {
                low++;
                stockModel.addRow(new Object[]{InventoryManager.getName(i), q, m, (m - q), (q == 0) ? "OUT OF STOCK" : "LOW"});
            }
        }
        lblStockStatus.setText(low == 0 ? "All products have sufficient stock." : "[!] Alert: " + low + " product(s) low on stock.");
        lblFooter.setText("Last action: Inspected low-stock products.");
    }

    // Build Tab 4: Product Search
    private JPanel buildSearchTab() {
        JPanel panel = new JPanel(new BorderLayout(10, 10)), top = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        JButton btn = createGreenButton("Search");
        top.add(new JLabel("Product Name:")); top.add(txtSearch); top.add(btn);
        panel.add(top, BorderLayout.NORTH);

        DefaultTableModel[] holder = new DefaultTableModel[1];
        panel.add(createTablePanel(new String[]{"Field", "Details"}, holder), BorderLayout.CENTER);
        searchModel = holder[0];

        ActionListener searchAct = e -> searchProduct();
        btn.addActionListener(searchAct);
        txtSearch.addActionListener(searchAct);
        return panel;
    }

    // Execute linear search through InventoryManager
    private void searchProduct() {
        searchModel.setRowCount(0);
        String query = txtSearch.getText().trim();
        if (query.isEmpty()) {
            lblFooter.setText("Last action: Please enter a name to search.");
            return;
        }
        int idx = InventoryManager.findProductIndex(query);
        if (idx == -1) {
            searchModel.addRow(new Object[]{"Result", "[!] Product '" + query + "' not found in inventory."});
            lblFooter.setText("Last action: Search - not found.");
            return;
        }
        double val = InventoryManager.calculateValue(InventoryManager.getPrice(idx), InventoryManager.getQuantity(idx));
        String status = InventoryManager.getStatus(InventoryManager.getQuantity(idx), InventoryManager.getMinStock(idx));

        searchModel.addRow(new Object[]{"Product Name", InventoryManager.getName(idx)});
        searchModel.addRow(new Object[]{"Unit Price", String.format("%s%.2f", CURRENCY, InventoryManager.getPrice(idx))});
        searchModel.addRow(new Object[]{"Stock Quantity", InventoryManager.getQuantity(idx) + " units"});
        searchModel.addRow(new Object[]{"Min Threshold", InventoryManager.getMinStock(idx) + " units"});
        searchModel.addRow(new Object[]{"Total Value", String.format("%s%.2f", CURRENCY, val)});
        searchModel.addRow(new Object[]{"Stock Status", status});
        lblFooter.setText("Last action: Found product " + InventoryManager.getName(idx));
    }

    // Build Tab 5: Inventory Report
    private JPanel buildReportTab() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        DefaultTableModel[] holder = new DefaultTableModel[1];
        JScrollPane scroll = createTablePanel(new String[]{"No.", "Name", "Price (" + CURRENCY.trim() + ")", "Quantity", "Min Stock", "Value (" + CURRENCY.trim() + ")", "Status"}, holder);
        reportModel = holder[0];
        JTable table = (JTable) scroll.getViewport().getView();

        // Highlight products without sufficient stock in soft red
        table.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable t, Object val, boolean isSel, boolean hasFocus, int row, int col) {
                Component c = super.getTableCellRendererComponent(t, val, isSel, hasFocus, row, col);
                Object status = t.getValueAt(row, 6);
                if ("RESTOCK".equals(status)) {
                    c.setBackground(new Color(255, 215, 215)); // Light red for low-stock items
                    c.setForeground(new Color(180, 0, 0));
                } else {
                    c.setBackground(isSel ? t.getSelectionBackground() : Color.WHITE);
                    c.setForeground(isSel ? t.getSelectionForeground() : Color.BLACK);
                }
                return c;
            }
        });

        panel.add(scroll, BorderLayout.CENTER);
        lblReportSummary.setFont(BOLD);
        panel.add(lblReportSummary, BorderLayout.SOUTH);
        return panel;
    }

    // Populate Tab 5 report table
    private void refreshReportTable() {
        reportModel.setRowCount(0);
        int count = InventoryManager.getProductCount(), totalUnits = 0;
        for (int i = 0; i < count; i++) {
            int q = InventoryManager.getQuantity(i);
            totalUnits += q;
            double val = InventoryManager.calculateValue(InventoryManager.getPrice(i), q);
            String st = InventoryManager.getStatus(q, InventoryManager.getMinStock(i));
            reportModel.addRow(new Object[]{(i + 1), InventoryManager.getName(i), String.format("%.2f", InventoryManager.getPrice(i)), q, InventoryManager.getMinStock(i), String.format("%.2f", val), st});
        }
        lblReportSummary.setText(String.format("Summary: Products: %d | Units: %d | Total Value: %s%.2f", count, totalUnits, CURRENCY, InventoryManager.calculateTotalValue()));
        lblFooter.setText("Last action: Refreshed inventory report.");
    }

    // Application entry point
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GroceryInventoryGUI().setVisible(true));
    }
}
