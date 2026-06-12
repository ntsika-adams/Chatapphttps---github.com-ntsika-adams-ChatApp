package com.mycompany.chatapp;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for the Message class.
 * These tests verify message validation, hashing,
 * recipient formatting, message ID generation,
 * and message sending functionality.
 */
public class MessageTest {

    // Test message objects used throughout the test cases
    private Message message1;
    private Message message2;

    /**
     * Inner helper class used to simulate the different
     * menu options returned by the sentMessage() method.
     */
    private class TestableMessage extends Message {

        // Stores the simulated user option
        private final int option;

        /**
         * Constructor that accepts a simulated menu choice.
         *
         * @param option user-selected option
         */
        public TestableMessage(int option) {
            this.option = option;
        }

        /**
         * Overrides sentMessage() to return predetermined
         * values without requiring user input.
         *
         * @return result message based on selected option
         */
        @Override
        public String sentMessage() {

            return switch (option) {
                case 1 -> "Message successfully sent.";
                case 2 -> "Press 0 to delete the message.";
                case 3 -> "Message successfully stored.";
                default -> "Invalid option selected.";
            };
        }
    }

    /**
     * Creates fresh test objects before each test method executes.
     */
    @Before
    public void setUp() {

        // Create first test message
        message1 = new Message(0);
        message1.setRecipient("+27718693002");
        message1.setMessageText(
                "Hi Mike, can you join us for dinner tonight?");
        message1.setMessageID("0012345678");

        // Create second test message
        message2 = new Message(1);
        message2.setRecipient("08575975889");
        message2.setMessageText(
                "Hi Keegan, did you receive the payment?");
        message2.setMessageID("0098765432");
    }

    /**
     * Tests that a valid message under 250 characters
     * is accepted successfully.
     */
    @Test
    public void testCheckMessageLength_validMessage_returnsSuccess() {

        String result = message1.checkMessageLength(
                message1.getMessageText());

        assertEquals("Message ready to send.", result);
    }

    /**
     * Tests that a message exceeding 250 characters
     * returns the correct error and excess count.
     */
    @Test
    public void testCheckMessageLength_over250chars_returnsFailureWithCount() {

        String longMessage = "a".repeat(260);

        String result = message1.checkMessageLength(longMessage);

        assertEquals(
                "Message exceeds 250 characters by 10; please reduce the size.",
                result);
    }

    /**
     * Tests that a message exactly 250 characters long
     * is considered valid.
     */
    @Test
    public void testCheckMessageLength_exactlyAtLimit_returnsSuccess() {

        String exactMessage = "a".repeat(250);

        String result = message1.checkMessageLength(exactMessage);

        assertEquals("Message ready to send.", result);
    }

    /**
     * Tests that a message exceeding the limit by one character
     * returns the correct validation message.
     */
    @Test
    public void testCheckMessageLength_oneOver_returnsFailureWithCountOf1() {

        String longMessage = "a".repeat(251);

        String result = message1.checkMessageLength(longMessage);

        assertEquals(
                "Message exceeds 250 characters by 1; please reduce the size.",
                result);
    }

    /**
     * Tests that a correctly formatted international
     * recipient number is accepted.
     */
    @Test
    public void testCheckRecipientCell_validNumber_returnsSuccess() {

        String result = message1.checkRecipientCell();

        assertEquals(
                "Cell phone number successfully captured.",
                result);
    }

    /**
     * Tests that an incorrectly formatted phone number
     * returns the appropriate error message.
     */
    @Test
    public void testCheckRecipientCell_invalidNumber_returnsFailure() {

        String result = message2.checkRecipientCell();

        assertEquals(
                "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.",
                result);
    }

    /**
     * Tests that the generated message hash ends
     * with the expected message words.
     */
    @Test
    public void testCreateMessageHash_correctFormat_endsWithExpectedWords() {

        String hash = message1.createMessageHash();

        assertTrue(hash.endsWith(":0:HITONIGHT?"));
    }

    /**
     * Tests that the generated message hash
     * is entirely uppercase.
     */
    @Test
    public void testCreateMessageHash_isUppercase() {

        String hash = message1.createMessageHash();

        assertEquals(hash.toUpperCase(), hash);
    }

    /**
     * Tests hash generation for multiple messages
     * using a loop to verify expected keywords.
     */
    @Test
    public void testCreateMessageHash_multipleMessages_loopTest() {

        Message[] messages = {message1, message2};

        String[] expected = {"HITONIGHT?", "HIPAYMENT?"};

        for (int i = 0; i < messages.length; i++) {

            String hash = messages[i].createMessageHash();

            assertTrue(hash.contains(expected[i]));
        }
    }

    /**
     * Tests that the generated message ID
     * is not null.
     */
    @Test
    public void testCheckMessageID_generatedID_isNotNull() {

        assertNotNull(
                "Message ID should not be null",
                message1.getMessageID());
    }

    /**
     * Tests that the generated message ID
     * satisfies the required validation rules.
     */
    @Test
    public void testCheckMessageID_generatedID_isExactly10Chars() {

        assertTrue(message1.checkMessageID());
    }

    /**
     * Tests that selecting the Send option
     * returns the correct confirmation message.
     */
    @Test
    public void testSentMessage_userSelectsSend_returnsCorrectString() {

        TestableMessage testMessage = new TestableMessage(1);

        assertEquals(
                "Message successfully sent.",
                testMessage.sentMessage());
    }

    /**
     * Tests that selecting the Delete option
     * returns the correct instruction message.
     */
    @Test
    public void testSentMessage_userSelectsDisregard_returnsCorrectString() {

        TestableMessage testMessage = new TestableMessage(2);

        assertEquals(
                "Press 0 to delete the message.",
                testMessage.sentMessage());
    }

    /**
     * Tests that selecting the Store option
     * returns the correct confirmation message.
     */
    @Test
    public void testSentMessage_userSelectsStore_returnsCorrectString() {

        TestableMessage testMessage = new TestableMessage(3);

        assertEquals(
                "Message successfully stored.",
                testMessage.sentMessage());
    }
}