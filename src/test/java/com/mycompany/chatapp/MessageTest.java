package com.mycompany.chatapp;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for Message class.
 */
public class MessageTest {

    private Message message1;
    private Message message2;

    /**
     * Helper class to simulate user choices.
     */
    private class TestableMessage extends Message {

        private final int option;

        public TestableMessage(int option) {
            this.option = option;
        }

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
     * Creates fresh objects before every test.
     */
    @Before
    public void setUp() {

        // Message 1
        message1 = new Message(0);
        message1.setRecipient("+27718693002");
        message1.setMessageText(
                "Hi Mike, can you join us for dinner tonight?");
        message1.setMessageID("0012345678");

        // Message 2
        message2 = new Message(1);
        message2.setRecipient("08575975889");
        message2.setMessageText(
                "Hi Keegan, did you receive the payment?");
        message2.setMessageID("0098765432");
    }

    @Test
    public void testCheckMessageLength_validMessage_returnsSuccess() {

        String result = message1.checkMessageLength(
                message1.getMessageText());

        assertEquals("Message ready to send.", result);
    }

    @Test
    public void testCheckMessageLength_over250chars_returnsFailureWithCount() {

        String longMessage = "a".repeat(260);

        String result = message1.checkMessageLength(longMessage);

        assertEquals(
                "Message exceeds 250 characters by 10; please reduce the size.",
                result);
    }

    @Test
    public void testCheckMessageLength_exactlyAtLimit_returnsSuccess() {

        String exactMessage = "a".repeat(250);

        String result = message1.checkMessageLength(exactMessage);

        assertEquals("Message ready to send.", result);
    }

    @Test
    public void testCheckMessageLength_oneOver_returnsFailureWithCountOf1() {

        String longMessage = "a".repeat(251);

        String result = message1.checkMessageLength(longMessage);

        assertEquals(
                "Message exceeds 250 characters by 1; please reduce the size.",
                result);
    }

    @Test
    public void testCheckRecipientCell_validNumber_returnsSuccess() {

        String result = message1.checkRecipientCell();

        assertEquals(
                "Cell phone number successfully captured.",
                result);
    }

    @Test
    public void testCheckRecipientCell_invalidNumber_returnsFailure() {

        String result = message2.checkRecipientCell();

        assertEquals(
                "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.",
                result);
    }

    @Test
    public void testCreateMessageHash_correctFormat_endsWithExpectedWords() {

        String hash = message1.createMessageHash();

        assertTrue(hash.endsWith(":0:HITONIGHT?"));
    }

    @Test
    public void testCreateMessageHash_isUppercase() {

        String hash = message1.createMessageHash();

        assertEquals(hash.toUpperCase(), hash);
    }

    @Test
    public void testCreateMessageHash_multipleMessages_loopTest() {

        Message[] messages = {message1, message2};

        String[] expected = {"HITONIGHT?", "HIPAYMENT?"};

        for (int i = 0; i < messages.length; i++) {

            String hash = messages[i].createMessageHash();

            assertTrue(hash.contains(expected[i]));
        }
    }

    @Test
    public void testCheckMessageID_generatedID_isNotNull() {

        assertNotNull(
                "Message ID should not be null",
                message1.getMessageID());
    }

    @Test
    public void testCheckMessageID_generatedID_isExactly10Chars() {

        assertTrue(message1.checkMessageID());
    }

    @Test
    public void testSentMessage_userSelectsSend_returnsCorrectString() {

        TestableMessage testMessage = new TestableMessage(1);

        assertEquals(
                "Message successfully sent.",
                testMessage.sentMessage());
    }

    @Test
    public void testSentMessage_userSelectsDisregard_returnsCorrectString() {

        TestableMessage testMessage = new TestableMessage(2);

        assertEquals(
                "Press 0 to delete the message.",
                testMessage.sentMessage());
    }

    @Test
    public void testSentMessage_userSelectsStore_returnsCorrectString() {

        TestableMessage testMessage = new TestableMessage(3);

        assertEquals(
                "Message successfully stored.",
                testMessage.sentMessage());
    }
}