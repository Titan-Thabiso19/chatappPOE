package com.mycompany.progtestcodeproject;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for PROGtestcodeproject and Messages classes.
 * Covers username, password, cell number validation, and message ID hashing.
 * Developed for clarity, correctness, and completeness.
 */
public class PROGtestcodeprojectTest {

    // ============================
    // Username Validation Tests
    // ============================
    @Nested
    @DisplayName("Username Validation")
    class UsernameTests {

        @Test
        @DisplayName("✅ Valid: username with underscore and 5 characters")
        void validUsername() {
            assertTrue(PROGtestcodeproject.CheckUserName("abc_d"));
        }

        @Test
        @DisplayName("❌ Invalid: too short (less than 5)")
        void usernameTooShort() {
            assertFalse(PROGtestcodeproject.CheckUserName("a_d"));
        }

        @Test
        @DisplayName("❌ Invalid: no underscore")
        void usernameNoUnderscore() {
            assertFalse(PROGtestcodeproject.CheckUserName("abcde"));
        }
    }

    // ============================
    // Password Validation Tests
    // ============================
    @Nested
    @DisplayName("Password Complexity")
    class PasswordTests {

        @Test
        @DisplayName("✅ Valid: 8 chars with uppercase, digit & special character")
        void validPassword() {
            assertTrue(PROGtestcodeproject.CheckPasswordComplexity("A1@bcdef"));
        }

        @Test
        @DisplayName("❌ Invalid: no special character")
        void passwordMissingSpecial() {
            assertFalse(PROGtestcodeproject.CheckPasswordComplexity("Abc12345"));
        }

        @Test
        @DisplayName("❌ Invalid: less than 8 characters")
        void passwordTooShort() {
            assertFalse(PROGtestcodeproject.CheckPasswordComplexity("A@b3"));
        }
    }

    // ============================
    // Cell Number Validation Tests
    // ============================
    @Nested
    @DisplayName("Cell Number Validation")
    class CellNumberTests {

        @Test
        @DisplayName("✅ Valid: exactly 8 to 10 digits")
        void validCellNumber() {
            assertTrue(Messages.checkRecipientCell("012345678"));
            assertTrue(Messages.checkRecipientCell("0123456789"));
        }

        @Test
        @DisplayName("❌ Invalid: too short or too long")
        void invalidCellNumber() {
            assertFalse(Messages.checkRecipientCell("1234567"));     // 7 digits
            assertFalse(Messages.checkRecipientCell("12345678901")); // 11 digits
        }
    }

    // ============================
    // Message Hashing Tests
    // ============================
    @Nested
    @DisplayName("Message Hash ID Generation")
    class MessageHashTests {

        @Test
        @DisplayName("✅ Valid: generates 10-digit numeric hash")
        void hashLengthAndFormat() {
            String hash = Messages.generateHashMessageID("Test message!");
            assertNotNull(hash);
            assertEquals(10, hash.length(), "Hash should be 10 digits");
            assertTrue(hash.matches("\\d{10}"), "Hash must only contain digits");
        }
    }
}
