package com.mycompany.progtestcodeproject;

import javax.swing.JOptionPane;

public class PROGtestcodeproject {

    public static void main(String[] args) {
        String registeredUsername;
        String registeredPassword;
        String cellphoneNumber;

        // === USER REGISTRATION ===
        String name = prompt("Enter your name:");
        if (name == null) return;

        String surname = prompt("Enter your surname:");
        if (surname == null) return;

        // === USERNAME REGISTRATION ===
        while (true) {
            registeredUsername = prompt("Enter a username (5 characters & must include an underscore '_'):");
            if (registeredUsername == null) return;

            if (CheckUserName(registeredUsername)) break;
            JOptionPane.showMessageDialog(null, "❌ Invalid username format. Must be 5 characters and include an underscore.");
        }

        // === PASSWORD REGISTRATION ===
        while (true) {
            registeredPassword = prompt("Enter a password (8 chars, 1 uppercase, 1 number, 1 special char):");
            if (registeredPassword == null) return;

            if (CheckPasswordComplexity(registeredPassword)) break;
            JOptionPane.showMessageDialog(null, "❌ Invalid password format.");
        }

        // === CELLPHONE NUMBER REGISTRATION ===
        while (true) {
            cellphoneNumber = prompt("Enter your cellphone number (8–10 digits):");
            if (cellphoneNumber == null) return;

            if (CheckCellNumber(cellphoneNumber)) break;
            JOptionPane.showMessageDialog(null, "❌ Invalid cellphone number.");
        }

        // === REGISTRATION SUCCESSFUL ===
        JOptionPane.showMessageDialog(null, "✅ Registration complete!\nWelcome, " + name + " " + surname + "!");

        // === LOGIN SECTION ===
        LoginUser(registeredUsername, registeredPassword, name, surname);
    }

    // === Prompt Helper ===
    private static String prompt(String message) {
        String input = JOptionPane.showInputDialog(message);
        return (input != null && !input.trim().isEmpty()) ? input.trim() : null;
    }

    // === Username Validation ===
    public static boolean CheckUserName(String username) {
        return username.length() == 5 && username.contains("_");
    }

    // === Password Validation ===
    public static boolean CheckPasswordComplexity(String password) {
        return password.length() == 8 &&
               password.matches(".*[A-Z].*") &&
               password.matches(".*\\d.*") &&
               password.matches(".*[^a-zA-Z0-9].*");
    }

    // === Cell Number Validation ===
    public static boolean CheckCellNumber(String number) {
        return number.matches("\\d{8,10}");
    }

    // === Login Handling ===
    public static void LoginUser(String registeredUser, String registeredPass, String name, String surname) {
        int attempts = 3;
        while (attempts-- > 0) {
            String loginUser = prompt("Enter your username:");
            if (loginUser == null) return;

            String loginPass = prompt("Enter your password:");
            if (loginPass == null) return;

            if (ReturnLoginStatus(registeredUser, registeredPass, loginUser, loginPass)) {
                JOptionPane.showMessageDialog(null, "✅ Login successful!");
                Messages.showMenu(name, surname); // Moves to Part 2
                return;
            } else {
                JOptionPane.showMessageDialog(null, "❌ Login failed. Attempts remaining: " + attempts);
            }
        }
        JOptionPane.showMessageDialog(null, "⛔ Too many failed attempts. Exiting.");
    }

    // === Login Matching ===
    public static boolean ReturnLoginStatus(String registeredUser, String registeredPass, String loginUser, String loginPass) {
        return registeredUser.equals(loginUser) && registeredPass.equals(loginPass);
    }
}
