package com.mycompany.chatapp;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import org.json.JSONObject;

/**
 * Message class for ChatApp.
 * Handles message validation, hashing, sending, and storage.
 *
 * JSON library attribution:
 * https://mvnrepository.com/artifact/org.json/json
 */
public class Message {

    private String messageID;
    private int messageNumber;
    private String recipient;
    private String messageText;
    private String messageHash;
    private String sendStatus;

    private static int totalMessages = 0;

    /**
     * Default constructor.
     */
    public Message() {
        this.messageID = generateMessageID();
    }

    /**
     * Constructor with message number.
     *
     * @param messageNumber message counter
     */
    public Message(int messageNumber) {
        this.messageNumber = messageNumber;
        this.messageID = generateMessageID();
    }

    /**
     * Generates a random 10-digit ID.
     *
     * @return message ID
     */
    private String generateMessageID() {

        Random random = new Random();
        StringBuilder id = new StringBuilder();

        for (int i = 0; i < 10; i++) {
            id.append(random.nextInt(10));
        }

        return id.toString();
    }

    /**
     * Checks if message ID is valid.
     *
     * @return true if ID length <= 10
     */
    public boolean checkMessageID() {

        return messageID != null
                && messageID.length() <= 10;
    }

    /**
     * Validates recipient cell number.
     *
     * @return validation result message
     */
    public String checkRecipientCell() {

        if (recipient != null
                && recipient.matches("^\\+27\\d{9}$")) {

            return "Cell phone number successfully captured.";
        }

        return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
    }

    /**
     * Validates message length.
     *
     * @param message message text
     * @return validation result
     */
    public String checkMessageLength(String message) {

        if (message.length() <= 250) {
            return "Message ready to send.";
        }

        int over = message.length() - 250;

        return "Message exceeds 250 characters by "
                + over
                + "; please reduce the size.";
    }

    /**
     * Creates message hash.
     *
     * Format:
     * First 2 digits of ID : message number : first word + last word
     *
     * @return message hash
     */
    public String createMessageHash() {

        String idPart = messageID.substring(0, 2);

        String[] words = messageText.split(" ");

        String firstWord = words[0];
        String lastWord = words[words.length - 1];

        messageHash = idPart
                + ":"
                + messageNumber
                + ":"
                + firstWord
                + lastWord;

        return messageHash.toUpperCase();
    }

    /**
     * Handles send message options.
     *
     * @return status message
     */
    public String sentMessage() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("What would you like to do with this message?");
        System.out.println("1) Send Message");
        System.out.println("2) Disregard Message");
        System.out.println("3) Store Message to send later");

        int option = scanner.nextInt();

        switch (option) {

            case 1 -> {
                sendStatus = "Sent";
                totalMessages++;
                return "Message successfully sent.";
            }

            case 2 -> {
                sendStatus = "Disregarded";
                return "Press 0 to delete the message.";
            }

            case 3 -> {
                sendStatus = "Stored";
                storeMessage();
                totalMessages++;
                return "Message successfully stored.";
            }

            default -> {
                return "Invalid option selected.";
            }
        }
    }

    /**
     * Displays message details.
     *
     * @return formatted message details
     */
    public String printMessages() {

        return "Message ID: " + messageID
                + "\nMessage Hash: " + messageHash
                + "\nRecipient: " + recipient
                + "\nMessage: " + messageText;
    }

    /**
     * Returns total messages.
     *
     * @return total message count
     */
    public int returnTotalMessages() {

        return totalMessages;
    }

    /**
     * Stores message in JSON file.
     */
    public void storeMessage() {

        JSONObject obj = new JSONObject();

        obj.put("messageID", messageID);
        obj.put("messageHash", messageHash);
        obj.put("recipient", recipient);
        obj.put("message", messageText);
        obj.put("status", sendStatus);

        try (FileWriter writer = new FileWriter("messages.json", true)) {

            writer.write(obj.toString());
            writer.write(System.lineSeparator());

        } catch (IOException e) {

            System.out.println("Error writing to JSON file.");
        }
    }

    // Getters and Setters

    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public int getMessageNumber() {
        return messageNumber;
    }

    public void setMessageNumber(int messageNumber) {
        this.messageNumber = messageNumber;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageHash() {
        return messageHash;
    }

    public void setMessageHash(String messageHash) {
        this.messageHash = messageHash;
    }

    public String getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(String sendStatus) {
        this.sendStatus = sendStatus;
    }
}