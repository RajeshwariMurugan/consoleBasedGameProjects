package src;
import java.util.*;

class GroceryItem {
    String name;
    double price;
    int quantity;

    public GroceryItem(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
}

public class GroceryManager {
    static List<GroceryItem> inventory = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initializeInventory();
        
        while (true) {
            System.out.println("\nGrocery Management System");
            System.out.println("1. View Inventory");
            System.out.println("2. Add Item");
            System.out.println("3. Update Item");
            System.out.println("4. Remove Item");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            
            switch (choice) {
                case 1:
                    viewInventory();
                    break;
                case 2:
                    addItem();
                    break;
                case 3:
                    updateItem();
                    break;
                case 4:
                    removeItem();
                    break;
                case 5:
                    System.exit(0);
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    static void initializeInventory() {
        inventory.add(new GroceryItem("Apple", 0.50, 100));
        inventory.add(new GroceryItem("Milk", 2.99, 50));
        inventory.add(new GroceryItem("Bread", 1.99, 75));
    }

    static void viewInventory() {
        System.out.println("\nCurrent Inventory:");
        System.out.println("----------------------------------");
        System.out.printf("%-20s %-10s %-8s\n", "Item", "Price", "Quantity");
        System.out.println("----------------------------------");
        
        for (GroceryItem item : inventory) {
            System.out.printf("%-20s $%-9.2f %-8d\n", 
                item.name, item.price, item.quantity);
        }
        
        System.out.println("----------------------------------");
    }

    static void addItem() {
        System.out.print("Enter item name: ");
        String name = scanner.nextLine();
        System.out.print("Enter price: ");
        double price = scanner.nextDouble();
        System.out.print("Enter quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine(); // consume newline
        
        inventory.add(new GroceryItem(name, price, quantity));
        System.out.println("Item added successfully!");
    }

    static void updateItem() {
        viewInventory();
        System.out.print("Enter item number to update: ");
        int itemNum = scanner.nextInt() - 1;
        scanner.nextLine(); // consume newline
        
        if (itemNum < 0 || itemNum >= inventory.size()) {
            System.out.println("Invalid item number!");
            return;
        }
        
        GroceryItem item = inventory.get(itemNum);
        System.out.print("Enter new price (current: " + item.price + "): ");
        double newPrice = scanner.nextDouble();
        System.out.print("Enter new quantity (current: " + item.quantity + "): ");
        int newQuantity = scanner.nextInt();
        scanner.nextLine(); // consume newline
        
        item.price = newPrice;
        item.quantity = newQuantity;
        System.out.println("Item updated successfully!");
    }

    static void removeItem() {
        viewInventory();
        System.out.print("Enter item number to remove: ");
        int itemNum = scanner.nextInt() - 1;
        scanner.nextLine(); // consume newline
        
        if (itemNum < 0 || itemNum >= inventory.size()) {
            System.out.println("Invalid item number!");
        } else {
            String removedName = inventory.remove(itemNum).name;
            System.out.println(removedName + " removed from inventory!");
        }
    }
}