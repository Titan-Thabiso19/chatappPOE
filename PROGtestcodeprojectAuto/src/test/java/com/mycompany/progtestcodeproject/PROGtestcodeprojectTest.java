package com.mycompany.progtestcodeproject;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PROGtestcodeprojectTest {

    @Test
    public void testCheckUserName_Valid() {
        assertTrue(PROGtestcodeproject.CheckUserName("abc_d"));
    }

    @Test
    public void testCheckUserName_Invalid_NoUnderscore() {
        assertFalse(PROGtestcodeproject.CheckUserName("abcde"));
    }

    @Test
    public void testCheckUserName_Invalid_TooShort() {
        assertFalse(PROGtestcodeproject.CheckUserName("ab_"));
    }

    @Test
    public void testCheckPasswordComplexity_Valid() {
        assertTrue(PROGtestcodeproject.CheckPasswordComplexity("A1@bcdef"));
    }

    @Test
    public void testCheckPasswordComplexity_Invalid_NoUppercase() {
        assertFalse(PROGtestcodeproject.CheckPasswordComplexity("a1@bcdef"));
    }

    @Test
    public void testCheckPasswordComplexity_Invalid_NoNumber() {
        assertFalse(PROGtestcodeproject.CheckPasswordComplexity("A@bcdefg"));
    }

    @Test
    public void testCheckPasswordComplexity_Invalid_NoSpecialChar() {
        assertFalse(PROGtestcodeproject.CheckPasswordComplexity("A1bcdefg"));
    }

    @Test
    public void testCheckPasswordComplexity_Invalid_WrongLength() {
        assertFalse(PROGtestcodeproject.CheckPasswordComplexity("A1@bcde")); // Only 7 characters
    }

    @Test
    public void testCheckCellNumber_Valid_8Digits() {
        assertTrue(PROGtestcodeproject.CheckCellNumber("12345678"));
    }

    @Test
    public void testCheckCellNumber_Valid_10Digits() {
        assertTrue(PROGtestcodeproject.CheckCellNumber("1234567890"));
    }

    @Test
    public void testCheckCellNumber_Invalid_TooShort() {
        assertFalse(PROGtestcodeproject.CheckCellNumber("1234567"));
    }

    @Test
    public void testCheckCellNumber_Invalid_NonDigits() {
        assertFalse(PROGtestcodeproject.CheckCellNumber("1234abcd"));
    }

    @Test
    public void testReturnLoginStatus_CorrectCredentials() {
        assertTrue(PROGtestcodeproject.ReturnLoginStatus("user", "pass", "user", "pass"));
    }

    @Test
    public void testReturnLoginStatus_WrongPassword() {
        assertFalse(PROGtestcodeproject.ReturnLoginStatus("user", "pass", "user", "wrong"));
    }

    @Test
    public void testReturnLoginStatus_WrongUsername() {
        assertFalse(PROGtestcodeproject.ReturnLoginStatus("user", "pass", "wrong", "pass"));
    }
}
