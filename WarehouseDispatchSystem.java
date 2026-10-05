
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

// Main class
public class WarehouseDispatchSystem extends JFrame {

    // Dispatch Order class
    static class DispatchOrder {
        int orderId;
        String productId;
        String productName;
        int quantity;
        String destination;
        String status;

        DispatchOrder(int orderId, String productId, String productName,
                      int quantity, String destination, String status) {
            this.orderId = orderId;
            this.productId = productId;
            this.productName = productName;
            this.quantity = quantity;
            this.destination = destination;
            this.status = status;
        }
    }

    // Store dispatch orders
    private ArrayList<DispatchOrder> orders = new ArrayList<>();

    private int nextOrderId = 1001;

    // GUI components
    private JTextField productIdField;
    private JTextField productNameField;
    private JTextField quantityField;
    private JTextField destinationField;
    private JTextField searchField;

    private JComboBox<String> statusComboBox;

    private JTable orderTable;
    private DefaultTableModel tableModel;

    private JLabel totalOrdersLabel;
    private JLabel totalQuantityLabel;

    public WarehouseDispatchSystem() {

        setTitle("Warehouse Dispatch System");
        setSize(1100, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        createGUI();
    }

    // Create complete GUI
    private void createGUI() {

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ================= HEADER =================

        JPanel headerPanel = new JPanel(new BorderLayout());

        JLabel titleLabel = new JLabel("WAREHOUSE DISPATCH SYSTEM");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));

        JLabel subtitleLabel = new JLabel(
                "Manage warehouse products and dispatch orders"
        );
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        JPanel titleBox = new JPanel();
        titleBox.setLayout(new BoxLayout(titleBox, BoxLayout.Y_AXIS));

        titleBox.add(titleLabel);
        titleBox.add(subtitleLabel);

        headerPanel.add(titleBox, BorderLayout.WEST);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // ================= INPUT PANEL =================

        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(
                BorderFactory.createTitledBorder("Dispatch Order Details")
        );

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Product ID
        gbc.gridx = 0;
        gbc.gridy = 0;

        inputPanel.add(new JLabel("Product ID:"), gbc);

        productIdField = new JTextField(12);

        gbc.gridx = 1;

        inputPanel.add(productIdField, gbc);

        // Product Name
        gbc.gridx = 2;

        inputPanel.add(new JLabel("Product Name:"), gbc);

        productNameField = new JTextField(15);

        gbc.gridx = 3;

        inputPanel.add(productNameField, gbc);

        // Quantity
        gbc.gridx = 0;
        gbc.gridy = 1;

        inputPanel.add(new JLabel("Quantity:"), gbc);

        quantityField = new JTextField(12);

        gbc.gridx = 1;

        inputPanel.add(quantityField, gbc);

        // Destination
        gbc.gridx = 2;

        inputPanel.add(new JLabel("Destination:"), gbc);

        destinationField = new JTextField(15);

        gbc.gridx = 3;

        inputPanel.add(destinationField, gbc);

        // Status
        gbc.gridx = 0;
        gbc.gridy = 2;

        inputPanel.add(new JLabel("Status:"), gbc);

        statusComboBox = new JComboBox<>(
                new String[]{
                        "Pending",
                        "Packed",
                        "Dispatched",
                        "Delivered"
                }
        );

        gbc.gridx = 1;

        inputPanel.add(statusComboBox, gbc);

        // Add Button
        JButton addButton = new JButton("Add Dispatch");

        gbc.gridx = 2;
        gbc.gridy = 2;

        inputPanel.add(addButton, gbc);

        // Clear Button
        JButton clearButton = new JButton("Clear");

        gbc.gridx = 3;

        inputPanel.add(clearButton, gbc);

        // ================= TABLE =================

        String[] columns = {
                "Order ID",
                "Product ID",
                "Product Name",
                "Quantity",
                "Destination",
                "Status"
        };

        tableModel = new DefaultTableModel(columns, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        orderTable = new JTable(tableModel);

        orderTable.setRowHeight(25);
        orderTable.getTableHeader().setFont(
                new Font("Arial", Font.BOLD, 13)
        );

        JScrollPane tableScrollPane = new JScrollPane(orderTable);

        // ================= SEARCH PANEL =================

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        searchPanel.add(new JLabel("Search:"));

        searchField = new JTextField(20);

        searchPanel.add(searchField);

        JButton searchButton = new JButton("Search");

        searchPanel.add(searchButton);

        JButton showAllButton = new JButton("Show All");

        searchPanel.add(showAllButton);

        // ================= ACTION BUTTONS =================

        JPanel actionPanel = new JPanel(
                new FlowLayout(FlowLayout.CENTER, 10, 5)
        );

        JButton updateButton = new JButton("Update Status");

        JButton deleteButton = new JButton("Delete Order");

        JButton refreshButton = new JButton("Refresh");

        actionPanel.add(updateButton);
        actionPanel.add(deleteButton);
        actionPanel.add(refreshButton);

        // ================= BOTTOM PANEL =================

        JPanel bottomPanel = new JPanel(new BorderLayout());

        JPanel statisticsPanel = new JPanel(
                new FlowLayout(FlowLayout.LEFT)
        );

        totalOrdersLabel = new JLabel("Total Orders: 0");

        totalQuantityLabel = new JLabel("Total Quantity: 0");

        statisticsPanel.add(totalOrdersLabel);
        statisticsPanel.add(Box.createHorizontalStrut(30));
        statisticsPanel.add(totalQuantityLabel);

        bottomPanel.add(statisticsPanel, BorderLayout.WEST);
        bottomPanel.add(actionPanel, BorderLayout.EAST);

        // ================= CENTER =================

        JPanel centerPanel = new JPanel(new BorderLayout(5, 5));

        centerPanel.add(inputPanel, BorderLayout.NORTH);
        centerPanel.add(searchPanel, BorderLayout.CENTER);
        centerPanel.add(tableScrollPane, BorderLayout.SOUTH);

        // Correct table expansion
        JPanel tablePanel = new JPanel(new BorderLayout(5, 5));

        tablePanel.add(inputPanel, BorderLayout.NORTH);
        tablePanel.add(searchPanel, BorderLayout.CENTER);
        tablePanel.add(tableScrollPane, BorderLayout.SOUTH);

        // Better layout
        JPanel contentPanel = new JPanel(new BorderLayout(5, 5));

        contentPanel.add(inputPanel, BorderLayout.NORTH);
        contentPanel.add(searchPanel, BorderLayout.CENTER);

        JPanel tableContainer = new JPanel(new BorderLayout());

        tableContainer.add(tableScrollPane, BorderLayout.CENTER);

        contentPanel.add(tableContainer, BorderLayout.SOUTH);

        // Main center layout
        JPanel mainCenter = new JPanel(new BorderLayout(5, 5));

        mainCenter.add(inputPanel, BorderLayout.NORTH);
        mainCenter.add(searchPanel, BorderLayout.CENTER);
        mainCenter.add(tableScrollPane, BorderLayout.SOUTH);

        // Use a proper combined panel
        JPanel middlePanel = new JPanel(new BorderLayout(5, 5));

        middlePanel.add(inputPanel, BorderLayout.NORTH);
        middlePanel.add(searchPanel, BorderLayout.CENTER);
        middlePanel.add(tableScrollPane, BorderLayout.SOUTH);

        // Use table with vertical expansion
        JPanel actualCenter = new JPanel(new BorderLayout(5, 5));

        actualCenter.add(inputPanel, BorderLayout.NORTH);
        actualCenter.add(searchPanel, BorderLayout.CENTER);
        actualCenter.add(tableScrollPane, BorderLayout.SOUTH);

        // Final content using vertical BoxLayout
        JPanel finalCenter = new JPanel();
        finalCenter.setLayout(new BorderLayout(5, 5));

        finalCenter.add(inputPanel, BorderLayout.NORTH);
        finalCenter.add(searchPanel, BorderLayout.CENTER);

        JPanel tableArea = new JPanel(new BorderLayout());
        tableArea.add(tableScrollPane, BorderLayout.CENTER);

        finalCenter.add(tableArea, BorderLayout.SOUTH);

        // Instead of complicated nested layouts,
        // use a split structure
        JPanel upperPanel = new JPanel(new BorderLayout());

        upperPanel.add(inputPanel, BorderLayout.CENTER);

        JPanel tableSection = new JPanel(new BorderLayout());

        tableSection.add(searchPanel, BorderLayout.NORTH);
        tableSection.add(tableScrollPane, BorderLayout.CENTER);

        JPanel centerSection = new JPanel(new BorderLayout(5, 5));

        centerSection.add(inputPanel, BorderLayout.NORTH);
        centerSection.add(tableSection, BorderLayout.CENTER);

        mainPanel.add(centerSection, BorderLayout.CENTER);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);

        // ================= BUTTON EVENTS =================

        addButton.addActionListener(e -> addDispatch());

        clearButton.addActionListener(e -> clearFields());

        deleteButton.addActionListener(e -> deleteOrder());

        updateButton.addActionListener(e -> updateStatus());

        refreshButton.addActionListener(e -> refreshTable());

        searchButton.addActionListener(e -> searchOrders());

        showAllButton.addActionListener(e -> refreshTable());

        searchField.addActionListener(e -> searchOrders());

        setVisible(true);
    }

    // ================= ADD DISPATCH =================

    private void addDispatch() {

        String productId = productIdField.getText().trim();
        String productName = productNameField.getText().trim();
        String quantityText = quantityField.getText().trim();
        String destination = destinationField.getText().trim();

        if (productId.isEmpty() ||
                productName.isEmpty() ||
                quantityText.isEmpty() ||
                destination.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all fields.",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        int quantity;

        try {

            quantity = Integer.parseInt(quantityText);

            if (quantity <= 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Quantity must be greater than zero.",
                        "Invalid Quantity",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid quantity.",
                    "Invalid Quantity",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        String status =
                statusComboBox.getSelectedItem().toString();

        DispatchOrder order = new DispatchOrder(
                nextOrderId++,
                productId,
                productName,
                quantity,
                destination,
                status
        );

        orders.add(order);

        refreshTable();

        clearFields();

        JOptionPane.showMessageDialog(
                this,
                "Dispatch order added successfully!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // ================= DELETE ORDER =================

    private void deleteOrder() {

        int selectedRow = orderTable.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select an order to delete.",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int orderId =
                Integer.parseInt(
                        tableModel.getValueAt(selectedRow, 0).toString()
                );

        int result = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete Order " + orderId + "?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (result == JOptionPane.YES_OPTION) {

            orders.removeIf(order -> order.orderId == orderId);

            refreshTable();

            JOptionPane.showMessageDialog(
                    this,
                    "Order deleted successfully."
            );
        }
    }

    // ================= UPDATE STATUS =================

    private void updateStatus() {

        int selectedRow = orderTable.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select an order.",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int orderId =
                Integer.parseInt(
                        tableModel.getValueAt(selectedRow, 0).toString()
                );

        String[] statuses = {
                "Pending",
                "Packed",
                "Dispatched",
                "Delivered"
        };

        String newStatus = (String) JOptionPane.showInputDialog(
                this,
                "Select new status:",
                "Update Status",
                JOptionPane.QUESTION_MESSAGE,
                null,
                statuses,
                statuses[0]
        );

        if (newStatus != null) {

            for (DispatchOrder order : orders) {

                if (order.orderId == orderId) {

                    order.status = newStatus;

                    break;
                }
            }

            refreshTable();

            JOptionPane.showMessageDialog(
                    this,
                    "Order status updated successfully."
            );
        }
    }

    // ================= SEARCH =================

    private void searchOrders() {

        String searchText =
                searchField.getText().trim().toLowerCase();

        tableModel.setRowCount(0);

        for (DispatchOrder order : orders) {

            if (
                    String.valueOf(order.orderId)
                            .contains(searchText)

                            ||

                    order.productId
                            .toLowerCase()
                            .contains(searchText)

                            ||

                    order.productName
                            .toLowerCase()
                            .contains(searchText)

                            ||

                    order.destination
                            .toLowerCase()
                            .contains(searchText)

                            ||

                    order.status
                            .toLowerCase()
                            .contains(searchText)
            ) {

                addOrderToTable(order);
            }
        }
    }

    // ================= REFRESH TABLE =================

    private void refreshTable() {

        tableModel.setRowCount(0);

        for (DispatchOrder order : orders) {

            addOrderToTable(order);
        }

        updateStatistics();
    }

    // ================= ADD ORDER TO TABLE =================

    private void addOrderToTable(DispatchOrder order) {

        tableModel.addRow(
                new Object[]{
                        order.orderId,
                        order.productId,
                        order.productName,
                        order.quantity,
                        order.destination,
                        order.status
                }
        );
    }

    // ================= CLEAR FIELDS =================

    private void clearFields() {

        productIdField.setText("");
        productNameField.setText("");
        quantityField.setText("");
        destinationField.setText("");

        statusComboBox.setSelectedIndex(0);

        productIdField.requestFocus();
    }

    // ================= STATISTICS =================

    private void updateStatistics() {

        int totalQuantity = 0;

        for (DispatchOrder order : orders) {

            totalQuantity += order.quantity;
        }

        totalOrdersLabel.setText(
                "Total Orders: " + orders.size()
        );

        totalQuantityLabel.setText(
                "Total Quantity: " + totalQuantity
        );
    }

    // ================= MAIN =================

    public static void main(String[] args) {

        try {

            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName()
            );

        } catch (Exception ignored) {
        }

        SwingUtilities.invokeLater(
                WarehouseDispatchSystem::new
        );
    
}
}
