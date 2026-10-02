public class InventoryManager {

    // Maximum limit and parallel arrays to store product details
    private static final int MAX_PRODUCTS = 100;
    private static String[] names = new String[MAX_PRODUCTS];
    private static double[] prices = new double[MAX_PRODUCTS];
    private static int[] quantities = new int[MAX_PRODUCTS];
    private static int[] minStock = new int[MAX_PRODUCTS];
    private static int productCount = 0;

    // Check whether a product with the same name already exists (case-insensitive)
    public static boolean nameExists(String name) {
        for (int i = 0; i < productCount; i++) {
            if (names[i].equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    // Calculate value for a single item (price * quantity)
    public static double calculateValue(double price, int qty) {
        return price * qty;
    }

    // Calculate sum of values for all active products
    public static double calculateTotalValue() {
        double total = 0.0;
        for (int i = 0; i < productCount; i++) {
            total = total + calculateValue(prices[i], quantities[i]);
        }
        return total;
    }

    // Check if quantity is below minimum stock threshold
    public static boolean isLowStock(int qty, int min) {
        return qty < min;
    }

    // Determine stock status using explicit if-else without ternary operator
    public static String getStatus(int qty, int min) {
        if (isLowStock(qty, min)) {
            return "RESTOCK";
        } else {
            return "OK";
        }
    }

    // Hand-written linear search by product name (case-insensitive) returning index or -1
    public static int findProductIndex(String name) {
        for (int i = 0; i < productCount; i++) {
            if (names[i].equalsIgnoreCase(name)) {
                return i;
            }
        }
        return -1;
    }

    // Validation: Demonstrates logical OR (||) operator for price validation
    public static boolean isValidPrice(double price) {
        if (price < 0.0 || Double.isNaN(price) || Double.isInfinite(price)) {
            return false;
        }
        return true;
    }

    // Validation: Quantity non-negative check
    public static boolean isValidQuantity(int qty) {
        return qty >= 0;
    }

    // Validation: Demonstrates logical OR (||) operator for boundary check
    public static boolean isValidMinStock(int min) {
        if (min < 0 || min > 1000000) {
            return false;
        }
        return true;
    }

    // Demonstrates logical AND (&&): warn if the item is added already low on stock
    public static boolean isAddedLowStock(int qty, int min) {
        return (qty < min && qty > 0);
    }

    // Check if inventory has reached maximum capacity
    public static boolean isFull() {
        return productCount >= MAX_PRODUCTS;
    }

    // Add a new product into parallel arrays and increment productCount
    public static boolean addProduct(String name, double price, int qty, int min) {
        if (isFull()) {
            return false;
        }
        names[productCount] = name;
        prices[productCount] = price;
        quantities[productCount] = qty;
        minStock[productCount] = min;
        productCount++;
        return true;
    }

    // Get current product count
    public static int getProductCount() {
        return productCount;
    }

    // Get product name at index i
    public static String getName(int i) {
        return names[i];
    }

    // Get product price at index i
    public static double getPrice(int i) {
        return prices[i];
    }

    // Get product quantity at index i
    public static int getQuantity(int i) {
        return quantities[i];
    }

    // Get product minimum stock at index i
    public static int getMinStock(int i) {
        return minStock[i];
    }

    // Get maximum capacity constant
    public static int getMaxProducts() {
        return MAX_PRODUCTS;
    }
}
