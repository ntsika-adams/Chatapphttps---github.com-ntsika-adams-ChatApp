package com.mycompany.chatapp;

import java.util.Scanner;

/**
 * Main class - Entry point for ChatApp.
 * Handles Part 1 (login) and Part 2 (messaging) integration.
 */
public class Main {

    public static void main(String[] args) {

        // ---------------- PART 1 ----------------//
        try (Scanner scanner = new Scanner(System.in)) {
            
            Login login = new Login();
            
            System.out.println("=== ChatApp Registration ===");
            
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            
            System.out.print("Enter password: ");
            String password = scanner.nextLine();
            
            System.out.print("Enter cell phone number (+27XXXXXXXXX): ");
            String cellPhone = scanner.nextLine();
            
            String registrationResult =
                    login.registerUser(username, password, cellPhone);
            
            System.out.println(registrationResult);
            
            // Stop if registration fails
            if (!registrationResult.contains("successfully")) {
                
                System.out.println("Registration failed.");
                scanner.close();
                return;
            }
            
            // ---------------- LOGIN ----------------
            System.out.println("\n=== ChatApp Login ===");
            
            System.out.print("Enter username: ");
            String loginUsername = scanner.nextLine();
            
            System.out.print("Enter password: ");
            String loginPassword = scanner.nextLine();
            
            boolean loggedIn =
                    login.loginUser(loginUsername, loginPassword);
            
            // ---------------- PART 2 ----------------
            if (loggedIn) {
                /*if user has successfully logged in then it will show the next message*/
                System.out.println("Welcome to ChatApp.");
                
                boolean running = true;
                
                while (running) {
                    
                    System.out.println("\n=== Main Menu ===");
                    System.out.println("1) Send Messages");
                    System.out.println("2) Show recently sent messages");
                    System.out.println("3) Quit");
                    
                    System.out.print("Enter option: ");
                    
                    int choice;
                    
                    try {
                        
                        choice = scanner.nextInt();
                        scanner.nextLine();
                        
                    } catch (Exception e) {
                        
                        scanner.nextLine();
                        
                        System.out.println(
                                "Invalid input. Please enter a number.");
                        
                        continue;
                    }
                    
                    switch (choice) {
                        
                        case 1 -> sendMessages(scanner);
                            
                        case 2 -> System.out.println("Coming Soon.");
                            
                        case 3 -> {
                            running = false;
                            System.out.println(
                                    "Thank you for using ChatApp.");
                        }
                            
                        default -> System.out.println(
                                    "Invalid option. Please select 1, 2, or 3.");
                    }
                }
                
            } else {
                
                System.out.println(
                        "Login failed. Access denied.");
            }
        }
    }

    /**
     * Handles sending messages.
     *
     * @param scanner Scanner object
     */
    private static void sendMessages(Scanner scanner) {

        System.out.print(
                "\nHow many messages would you like to send? ");

        int numMessages;

        try {

            numMessages = scanner.nextInt();
            scanner.nextLine();

        } catch (Exception e) {

            scanner.nextLine();

            System.out.println(
                    "Invalid number entered.");

            return;
        }

        // For loop
        for (int i = 0; i < numMessages; i++) {

            int messageNumber = i;

            System.out.println(
                    "\n--- Message " + (i + 1) + " ---");

            // Create message object
            Message message = new Message(messageNumber);

            // ---------------- Recipient ----------------
            boolean validRecipient = false;

            while (!validRecipient) {

                System.out.print(
                        "Enter recipient (+27XXXXXXXXX): ");

                String recipient = scanner.nextLine();

                message.setRecipient(recipient);

                String recipientResult =
                        message.checkRecipientCell();

                System.out.println(recipientResult);

                if (recipientResult.equals(
                        "Cell phone number successfully captured.")) {

                    validRecipient = true;
                }
            }

            // ---------------- Message Text ----------------
            boolean validMessage = false;

            while (!validMessage) {

                System.out.print(
                        "Enter your message: ");

                String messageText = scanner.nextLine();

                String lengthResult =
                        message.checkMessageLength(messageText);

                System.out.println(lengthResult);

                if (lengthResult.equals(
                        "Message ready to send.")) {

                    message.setMessageText(messageText);

                    validMessage = true;
                }
            }

            // ---------------- Message ID ----------------
            if (message.checkMessageID()) {

                System.out.println(
                        "Message ID created successfully.");
            }

            // ---------------- Message Hash ----------------
            String hash = message.createMessageHash();

            System.out.println(
                    "Message Hash: " + hash);

            // ---------------- Send Message ----------------
            String actionResult =
                    message.sentMessage();

            System.out.println(actionResult);

            // ---------------- Display Message Details ----------------
            System.out.println(
                    "\n=== Message Details ===");

            System.out.println(message.printMessages());
        }

        // ---------------- Summary ----------------
        System.out.println("\n=== Summary ===");

        System.out.println(
                "Total messages: "
                        + new Message().returnTotalMessages());
    }
}