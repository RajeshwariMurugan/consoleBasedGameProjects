package src;
import java.util.*;

class Email {
    String sender;
    String recipient;
    String subject;
    String body;
    String timestamp;
    boolean isRead;

    public Email(String sender, String recipient, String subject, String body) {
        this.sender = sender;
        this.recipient = recipient;
        this.subject = subject;
        this.body = body;
        this.timestamp = new Date().toString();
        this.isRead = false;
    }
}

public class MailManagementSystem {
    static Map<String, List<Email>> mailboxes = new HashMap<>();
    static Scanner scanner = new Scanner(System.in);
    static String currentUser = null;

    public static void main(String[] args) {
        // Initialize with some users
        mailboxes.put("alice", new ArrayList<>());
        mailboxes.put("bob", new ArrayList<>());
        
        while (true) {
            if (currentUser == null) {
                login();
            } else {
                showMenu();
            }
        }
    }

    static void login() {
        System.out.println("\nMail Management System");
        System.out.print("Enter username (or 'exit' to quit): ");
        String username = scanner.nextLine();
        
        if (username.equalsIgnoreCase("exit")) {
            System.exit(0);
        }
        
        if (mailboxes.containsKey(username)) {
            currentUser = username;
            System.out.println("Logged in as " + currentUser);
        } else {
            System.out.println("User not found. Try again.");
        }
    }

    static void showMenu() {
        System.out.println("\nWelcome, " + currentUser);
        System.out.println("1. Compose Email");
        System.out.println("2. View Inbox");
        System.out.println("3. View Sent Items");
        System.out.println("4. Logout");
        System.out.print("Enter choice: ");
        
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline
        
        switch (choice) {
            case 1:
                composeEmail();
                break;
            case 2:
                viewInbox();
                break;
            case 3:
                viewSentItems();
                break;
            case 4:
                currentUser = null;
                break;
            default:
                System.out.println("Invalid choice!");
        }
    }

    static void composeEmail() {
        System.out.print("Enter recipient: ");
        String recipient = scanner.nextLine();
        
        if (!mailboxes.containsKey(recipient)) {
            System.out.println("Recipient not found!");
            return;
        }
        
        System.out.print("Enter subject: ");
        String subject = scanner.nextLine();
        System.out.println("Enter body (type 'END' on a new line to finish):");
        
        StringBuilder body = new StringBuilder();
        String line;
        while (!(line = scanner.nextLine()).equals("END")) {
            body.append(line).append("\n");
        }
        
        Email email = new Email(currentUser, recipient, subject, body.toString());
        
        // Add to sender's sent items
        mailboxes.get(currentUser).add(email);
        // Add to recipient's inbox
        mailboxes.get(recipient).add(email);
        
        System.out.println("Email sent successfully!");
    }

    static void viewInbox() {
        List<Email> inbox = new ArrayList<>();
        for (Email email : mailboxes.get(currentUser)) {
            if (email.recipient.equals(currentUser)) {
                inbox.add(email);
            }
        }
        
        if (inbox.isEmpty()) {
            System.out.println("Your inbox is empty!");
            return;
        }
        
        System.out.println("\nInbox:");
        for (int i = 0; i < inbox.size(); i++) {
            Email email = inbox.get(i);
            String status = email.isRead ? "Read" : "Unread";
            System.out.printf("%d. [%s] From: %s - %s - %s\n", 
                i+1, status, email.sender, email.subject, email.timestamp);
        }
        
        System.out.print("\nEnter email number to view (0 to go back): ");
        int choice = scanner.nextInt() - 1;
        scanner.nextLine(); // consume newline
        
        if (choice >= 0 && choice < inbox.size()) {
            Email email = inbox.get(choice);
            email.isRead = true;
            System.out.println("\nFrom: " + email.sender);
            System.out.println("Subject: " + email.subject);
            System.out.println("Date: " + email.timestamp);
            System.out.println("\n" + email.body);
        }
    }

    static void viewSentItems() {
        List<Email> sent = new ArrayList<>();
        for (Email email : mailboxes.get(currentUser)) {
            if (email.sender.equals(currentUser)) {
                sent.add(email);
            }
        }
        
        if (sent.isEmpty()) {
            System.out.println("No sent items!");
            return;
        }
        
        System.out.println("\nSent Items:");
        for (int i = 0; i < sent.size(); i++) {
            Email email = sent.get(i);
            System.out.printf("%d. To: %s - %s - %s\n", 
                i+1, email.recipient, email.subject, email.timestamp);
        }
        
        System.out.print("\nEnter email number to view (0 to go back): ");
        int choice = scanner.nextInt() - 1;
        scanner.nextLine(); // consume newline
        
        if (choice >= 0 && choice < sent.size()) {
            Email email = sent.get(choice);
            System.out.println("\nTo: " + email.recipient);
            System.out.println("Subject: " + email.subject);
            System.out.println("Date: " + email.timestamp);
            System.out.println("\n" + email.body);
        }
    }
}