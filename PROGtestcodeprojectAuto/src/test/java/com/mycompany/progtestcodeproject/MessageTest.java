package com.mycompany.progtestcodeproject;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    @Test
    public void testMessageIDFormat() {
        Message msg = new Message("Alice", "Smith", "Bob", "0831234567", "Hello Bob!");
        assertNotNull(msg.getMessageID());
        assertTrue(msg.getMessageID().startsWith("MSG"), "Message ID should start with 'MSG'");
        assertTrue(msg.getMessageID().length() >= 7, "Message ID should be at least 7 characters long");
    }

    @Test
    public void testTimestampIsGenerated() {
        Message msg = new Message("Alice", "Smith", "Bob", "0831234567", "Test message");
        assertNotNull(msg.getTimestamp(), "Timestamp should not be null");
        assertTrue(msg.getTimestamp().matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}"),
                   "Timestamp should match format yyyy-MM-dd HH:mm:ss");
    }

    @Test
    public void testToStringContent() {
        Message msg = new Message("Alice", "Smith", "Bob", "0831234567", "Test message");
        String result = msg.toString();
        assertTrue(result.contains("Message ID"), "toString should contain 'Message ID'");
        assertTrue(result.contains("To: Bob"), "toString should show recipient name");
        assertTrue(result.contains("Test message"), "toString should contain message content");
    }
}

