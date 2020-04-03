package ZCW.ChatApp.models;

import org.junit.Assert;
import org.junit.Test;
import java.util.Date;

public class MessageModelTest {

        private Long testId = 1L;
        private String testSender = "Jeff";
        private String testContent = "testing";
        private Date testTimestamp = new Date();
        private String expectedMessage = "Message{sender='Jeff', messageContent='testing',testTimeStamp}";


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

}

//        @Test
//        public void setAndGetTimestampTest() {
//            Message testMessage = new Message(testSender, null, testContent);
//
//            //When
//            Assert.assertNull(testMessage.getTimestamp());
//            Date newTimestamp = testTimestamp;
//            testMessage.setTimestamp(newTimestamp);
//            Date actualTimestamp = testMessage.getTimestamp();
//
//            //Then
//            Assert.assertEquals(newTimestamp, actualTimestamp);
//        }
//
//        }
//    }
