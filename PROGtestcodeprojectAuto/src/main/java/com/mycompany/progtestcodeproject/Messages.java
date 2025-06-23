package com.mycompany.progtestcodeproject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class Messages {

    private static ArrayList<String> messageStore = new ArrayList<>();
    private static ArrayList<Contact> savedRecipients = new ArrayList<>();

    // Internal class for contact storage
    private static class Contact {
        String name;
        String number;

        Contact(String name, String number) {
            this.name = name;
            this.number = number;
        }

        @Override
        public String toString() {
            return name + " - " + number;
        }
    }

    public static void showMenu(String senderName, String senderSurname) {
        while (true) {
            String[] options = {"1. Send Message", "2. Review Messages", "3. Export Stored", "4. Exit"};

            int choice = JOptionPane.showOptionDialog(
                    null,
                    "📨 Messaging Menu\n" +
                    "Messages Sent: " + totalMessagesSent() + "\n\nChoose an option:",
                    "Messaging System",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    options,
                    options[0]
            );

            switch (choice) {
                case 0:
                    sendMessage(senderName, senderSurname);
                    break;
                case 1:
                    JOptionPane.showMessageDialog(null, "🛠️ Review feature coming soon!");
                    break;
                case 2:
                    saveStoredMessagesToJsonFile();
                    break;
                case 3:
                    JOptionPane.showMessageDialog(null, "👋 Goodbye!");
                    System.exit(0);
                    break;
                default:
                    return;
            }
        }
    }

    private static void sendMessage(String senderName, String senderSurname) {
        String recipientName;
        String recipientCell;

        String[] recipientOptions = {"Enter New Contact", "Choose Saved Contact"};
        int inputOption = JOptionPane.showOptionDialog(
                null,
                "📇 Choose how to select the recipient:",
                "Recipient Selection",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                recipientOptions,
                recipientOptions[0]
        );

        if (inputOption == 0) {
            recipientName = JOptionPane.showInputDialog("Enter recipient's name:");
            recipientCell = JOptionPane.showInputDialog("Enter recipient's number:");

            if (recipientName == null || recipientCell == null) return;

            if (!checkRecipientCell(recipientCell)) {
                JOptionPane.showMessageDialog(null, "❌ Invalid number. Must be 8–10 digits.");
                return;
            }

            final String number = recipientCell.trim();
            if (savedRecipients.stream().noneMatch(c -> c.number.equals(number))) {
                savedRecipients.add(new Contact(recipientName.trim(), number));
            }
        } else if (inputOption == 1) {
            if (savedRecipients.isEmpty()) {
                JOptionPane.showMessageDialog(null, "⚠️ No saved contacts.");
                return;
            }

            Contact selected = (Contact) JOptionPane.showInputDialog(
                    null,
                    "📞 Select a contact:",
                    "Contacts",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    savedRecipients.toArray(),
                    savedRecipients.get(0)
            );
            if (selected == null) return;

            recipientName = selected.name;
            recipientCell = selected.number;
        } else {
            return;
        }

        String messageContent = JOptionPane.showInputDialog("✉️ Type your message (Max 250 letters):");
        if (messageContent == null || messageContent.trim().isEmpty()) return;
        if (messageContent.length() > 250) {
            JOptionPane.showMessageDialog(null, "⚠️ Message too long. Max 250 letters.");
            return;
        }

        String messageID = generateHashMessageID(messageContent);
        JOptionPane.showMessageDialog(null, "🆔 Message ID: " + messageID);

        String[] actionOptions = {"Send", "Store", "Discard"};
        int action = JOptionPane.showOptionDialog(
                null,
                "📦 What would you like to do with the message?",
                "Message Action",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                actionOptions,
                actionOptions[0]
        );

        if (action == 0) {
            String formatted = "[SENT]\nTo: " + recipientName + " (" + recipientCell + ")\nFrom: " + senderName + " " + senderSurname +
                    "\nMessage ID: " + messageID + "\nMessage: " + messageContent;
            messageStore.add(formatted);
            JOptionPane.showMessageDialog(null, "✅ Message sent!");
        } else if (action == 1) {
            String json = "{\n  \"id\": \"" + messageID + "\",\n  \"recipient\": \"" + recipientName + " (" + recipientCell + ")\",\n  \"sender\": \"" + senderName + " " + senderSurname + "\",\n  \"content\": \"" + messageContent + "\"\n}";
            messageStore.add("[STORED] " + json);
            JOptionPane.showMessageDialog(null, "📦 Message stored.");
        } else {
            JOptionPane.showMessageDialog(null, "🗑️ Message discarded.");
        }
    }

    public static boolean checkRecipientCell(String number) {
        return number.matches("\\d{8,10}");
    }

    public static int totalMessagesSent() {
        int count = 0;
        for (String msg : messageStore) {
            if (msg.startsWith("[SENT]") && msg.contains("Message ID:")) {
                count++;
            }
        }
        return count;
    }

    public static String generateHashMessageID(String content) {
        try {
            String input = content + System.currentTimeMillis();
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(input.getBytes());

            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                hexString.append(String.format("%02x", b));
            }

            String numericOnly = hexString.toString().replaceAll("[^0-9]", "");
            if (numericOnly.length() < 10) {
                numericOnly = String.format("%-10s", numericOnly).replace(' ', '0');
            }

            return numericOnly.substring(0, 10);
        } catch (NoSuchAlgorithmException e) {
            return "0000000000";
        }
    }

    // ✅ Export to JSON File Method
    public static void saveStoredMessagesToJsonFile() {
        List<JsonObject> storedMessages = new ArrayList<>();

        for (String msg : messageStore) {
            if (msg.startsWith("[STORED]")) {
                String jsonString = msg.replaceFirst("\\[STORED\\]\\s*", "");
                try {
                    JsonObject json = JsonParser.parseString(jsonString).getAsJsonObject();
                    storedMessages.add(json);
                } catch (JsonSyntaxException e) {
                    System.err.println("❌ Skipped invalid JSON: " + e.getMessage());
                }
            }
        }

        if (storedMessages.isEmpty()) {
            JOptionPane.showMessageDialog(null, "⚠️ No stored messages to export.");
            return;
        }

        try (FileWriter writer = new FileWriter("stored_messages.json")) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(storedMessages, writer);
            JOptionPane.showMessageDialog(null, "✅ Stored messages saved to 'stored_messages.json'");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "❌ Failed to save file: " + e.getMessage());
        }
    }
}
