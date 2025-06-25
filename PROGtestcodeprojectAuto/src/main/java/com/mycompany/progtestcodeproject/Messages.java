package com.mycompany.progtestcodeproject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javax.swing.JOptionPane;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class Messages {

    // Inner Message class
    public static class Message {
        private String sender;
        private String recipient;
        private String content;
        private String messageId;
        private String messageHash;

        public Message(String sender, String recipient, String content) {
            this.sender = sender;
            this.recipient = recipient;
            this.content = content;
            this.messageId = generateHashMessageID(content);
            this.messageHash = generateMessageHash(content);
        }

        public String getSender() {
            return sender;
        }

        public String getRecipient() {
            return recipient;
        }

        public String getContent() {
            return content;
        }

        public String getMessageId() {
            return messageId;
        }

        public String getMessageHash() {
            return messageHash;
        }
    }

    // Lists for storing messages
    private final List<Message> sentMessages = new ArrayList<>();
    private final List<Message> discardedMessages = new ArrayList<>();
    private final List<Message> storedMessages = new ArrayList<>();

    // Add a message to sent messages and stored messages
    public void addSentMessage(String sender, String recipient, String content) {
        Message message = new Message(sender, recipient, content);
        sentMessages.add(message);
        storedMessages.add(message);
        JOptionPane.showMessageDialog(null, "✅ Message sent and stored successfully!");
    }

    // Discard a message by hash and move it to discardedMessages
    public boolean discardMessageByHash(String hash) {
        for (Message msg : new ArrayList<>(storedMessages)) {
            if (msg.getMessageHash().equals(hash)) {
                storedMessages.remove(msg);
                discardedMessages.add(msg);
                JOptionPane.showMessageDialog(null, "🗑️ Message discarded successfully.");
                return true;
            }
        }
        JOptionPane.showMessageDialog(null, "❌ No message found with that hash.");
        return false;
    }

    // Display sender and recipient of all messages
    public void displayAllSendersAndRecipients() {
        StringBuilder sb = new StringBuilder("📨 Sender and Recipient of all stored messages:\n");
        for (Message msg : storedMessages) {
            sb.append(String.format("From: %s ➡️ To: %s%n", msg.getSender(), msg.getRecipient()));
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    // Display the longest sent message content
    public void displayLongestSentMessage() {
        if (sentMessages.isEmpty()) {
            JOptionPane.showMessageDialog(null, "⚠️ No sent messages available.");
            return;
        }
        Message longest = sentMessages.get(0);
        for (Message msg : sentMessages) {
            if (msg.getContent().length() > longest.getContent().length()) {
                longest = msg;
            }
        }
        String output = String.format("📏 Longest sent message:\nFrom: %s\nTo: %s\nMessage: %s",
                longest.getSender(), longest.getRecipient(), longest.getContent());
        JOptionPane.showMessageDialog(null, output);
    }

    // Search message by ID and display recipient and message content
    public void searchByMessageId(String messageId) {
        for (Message msg : storedMessages) {
            if (msg.getMessageId().equals(messageId)) {
                String output = String.format("🔍 Message found:\nRecipient: %s\nContent: %s",
                        msg.getRecipient(), msg.getContent());
                JOptionPane.showMessageDialog(null, output);
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "❌ No message found with that Message ID.");
    }

    // Search messages sent to a particular recipient
    public void searchMessagesByRecipient(String recipient) {
        StringBuilder sb = new StringBuilder(String.format("📋 Messages sent to %s:\n", recipient));
        boolean found = false;
        for (Message msg : storedMessages) {
            if (msg.getRecipient().equalsIgnoreCase(recipient)) {
                sb.append(String.format("From: %s, Message: %s%n", msg.getSender(), msg.getContent()));
                found = true;
            }
        }
        if (!found) {
            JOptionPane.showMessageDialog(null, "❌ No messages found for that recipient.");
        } else {
            JOptionPane.showMessageDialog(null, sb.toString());
        }
    }

    // Hashing methods
    public static String generateHashMessageID(String content) {
        // For simplicity: use first 8 chars of SHA-256 hash as message ID
        return generateSHA256Hash(content).substring(0, 8);
    }

    public static String generateMessageHash(String content) {
        return generateSHA256Hash(content);
    }

    private static String generateSHA256Hash(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found");
        }
    }

    // Optional: method to export stored messages to JSON string
    public String exportStoredMessagesToJson() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(storedMessages);
    }

    // The interactive menu to use after login
    public static void showMenu(String name, String surname) {
        Messages messages = new Messages();

        while (true) {
            String choice = JOptionPane.showInputDialog(
                    "📋 Select an option:\n" +
                    "1. Send a Message\n" +
                    "2. Display All Sender/Recipients\n" +
                    "3. Display Longest Sent Message\n" +
                    "4. Search by Message ID\n" +
                    "5. Search Messages by Recipient\n" +
                    "6. Discard a Message by Hash\n" +
                    "7. Export Messages to JSON\n" +
                    "0. Exit"
            );

            if (choice == null || choice.equals("0")) {
                JOptionPane.showMessageDialog(null, "👋 Exiting. Goodbye!");
                break;
            }

            switch (choice) {
                case "1":
                    String recipient = JOptionPane.showInputDialog("Enter recipient name:");
                    if (recipient == null || recipient.trim().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "❌ Recipient name cannot be empty.");
                        break;
                    }
                    String cell = JOptionPane.showInputDialog("Enter recipient number:");
                    if (cell == null || cell.trim().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "❌ Recipient number cannot be empty.");
                        break;
                    }
                    String content = JOptionPane.showInputDialog("Enter your message:");
                    if (content == null || content.trim().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "❌ Message content cannot be empty.");
                        break;
                    }
                    messages.addSentMessage(name + " " + surname, recipient, content);
                    break;

                case "2":
                    messages.displayAllSendersAndRecipients();
                    break;

                case "3":
                    messages.displayLongestSentMessage();
                    break;

                case "4":
                    String id = JOptionPane.showInputDialog("Enter message ID:");
                    if (id == null || id.trim().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "❌ Message ID cannot be empty.");
                        break;
                    }
                    messages.searchByMessageId(id);
                    break;

                case "5":
                    String recipientSearch = JOptionPane.showInputDialog("Enter recipient name:");
                    if (recipientSearch == null || recipientSearch.trim().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "❌ Recipient name cannot be empty.");
                        break;
                    }
                    messages.searchMessagesByRecipient(recipientSearch);
                    break;

                case "6":
                    String hash = JOptionPane.showInputDialog("Enter message hash to discard:");
                    if (hash == null || hash.trim().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "❌ Hash cannot be empty.");
                        break;
                    }
                    messages.discardMessageByHash(hash);
                    break;

                case "7":
                    String json = messages.exportStoredMessagesToJson();
                    JOptionPane.showMessageDialog(null, "📤 Exported Messages:\n" + json);
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "❌ Invalid choice.");
                    break;
            }
        }
    }
}
