package ZCW.ChatApp.models;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class MessageModelTest {

    public class MessageTests {

        private Long testId = 1L;
        private String testSender = "Jeff";
        private String testContent = "testing";
        private Date testTimestamp = new Date(2020, 04, 03, 8, 15, 45);
        private String expectedMessage = "Message{id=null, sender='Jeff', messageContent='testing', timestamp=Thu Apr 03 08:15:45 EDT 3919,}";

        @Test
        public void messageConstructorTest() {
            //When
            Message testMessage = new Message(testSender, testContent, testTimestamp);
            String actualMessage = testMessage.toString();

            //Then
            Assert.assertEquals(expectedMessage, actualMessage);
        }

        @Test
        public void setAndGetIdTest() {
            //Given
            Message testMessage = new Message(testSender, testContent, testTimestamp);

            //When
            Assert.assertNull(testMessage.getId());
            testMessage.setId(testId);
            Long actualId = testMessage.getId();

            //Then
            Assert.assertEquals(testId, actualId);
        }

        @Test
        public void setAndGetSenderTest() {
            //Given
            Message testMessage = new Message(testSender, testContent, testTimestamp);

            //When
            Assert.assertEquals(testSender, testMessage.getSender());
            String newSender = "Sandy";
            testMessage.setSender(newSender);
            String actualSender = testMessage.getSender();

            //Then
            Assert.assertEquals(newSender, actualSender);
        }

        @Test
        public void setAndGetMessageContent() {
            //Given
            Message testMessage = new Message(testSender, testContent, testTimestamp);

            //When
            Assert.assertEquals(testContent, testMessage.getContent());
            String newMessageContent = "Another test";
            testMessage.setContent(newMessageContent);
            String actualMessageContent = testMessage.getContent();

            //Then
            Assert.assertEquals(newMessageContent, actualMessageContent);
        }

        @Test
        public void setAndGetTimestampTest() {
            Message testMessage = new Message(testSender, null, testContent);

            //When
            Assert.assertNull(testMessage.getTimestamp());
            Date newTimestamp = testTimestamp;
            testMessage.setTimestamp(newTimestamp);
            Date actualTimestamp = testMessage.getTimestamp();

            //Then
            Assert.assertEquals(newTimestamp, actualTimestamp);
        }

        }
    }
