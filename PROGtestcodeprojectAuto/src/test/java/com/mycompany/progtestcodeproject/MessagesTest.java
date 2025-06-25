package com.mycompany.progtestcodeproject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MessagesTest {

    private Messages messages;

    @BeforeEach
    public void setup() {
        messages = new Messages();
    }

    @Test
    public void testAddSentMessage() {
        messages.addSentMessage("Alice", "Bob", "Hello Bob!");
        String json = messages.exportStoredMessagesToJson();
        assertTrue(json.contains("Alice"));
        assertTrue(json.contains("Hello Bob!"));
    }

    @Test
    public void testDiscardMessageByHash() {
        messages.addSentMessage("Alice", "Bob", "Discard this one");
        String hash = Messages.generateMessageHash("Discard this one");
        boolean result = messages.discardMessageByHash(hash);
        assertTrue(result);
    }

    @Test
    public void testSearchByMessageId() {
        messages.addSentMessage("Alice", "Charlie", "Secret message");
        String messageId = Messages.generateHashMessageID("Secret message");
        messages.searchByMessageId(messageId); // Should print a result — manual test
    }

    @Test
    public void testSearchMessagesByRecipient() {
        messages.addSentMessage("Alice", "Dave", "Hey Dave");
        messages.addSentMessage("Alice", "Dave", "Follow-up");
        messages.searchMessagesByRecipient("Dave"); // Should print both messages — manual test
    }

    @Test
    public void testExportStoredMessagesToJson() {
        messages.addSentMessage("John", "Jane", "Message A");
        String json = messages.exportStoredMessagesToJson();
        assertTrue(json.contains("Message A"));
        assertTrue(json.contains("Jane"));
    }
}
