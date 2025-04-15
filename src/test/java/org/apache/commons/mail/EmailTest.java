package org.apache.commons.mail;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import java.util.Date;
import java.util.List;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;

import java.util.Properties;

public class EmailTest {

    private EmailConcrete email; // Use SimpleEmail instead of Email
    private String[] TestEmails = {"test1@abc.com", "test2@abc.com"};

    @Before
    public void setUp() {
        email = new EmailConcrete(); // Instantiate SimpleEmail
    }
    
    @After
    public void tearDown() {
    	email = null; // Clear up resources
    }
    
    // Test for verifying 2 valid email's were added
    @Test
    public void testAddBcc() throws EmailException {
    	email.addBcc(TestEmails); 
    	
    	//Verifies 2 email's were added successfully
    	assertEquals(2, email.getBccAddresses().size());
    }
    
    // Test for adding multiple valid BCC email's
    @Test
    public void testAddBcc_ValidEmails() throws EmailException {
    	email.addBcc(TestEmails); 
    	
    	// Verifies the email's were added successfully
    	assertNotNull("Email object should not be null", email);
    }
    
    // Test adding null input
    @Test(expected = EmailException.class)
    public void testAddBcc_NullInput() throws EmailException {
    	try {
            email.addBcc((String[]) null); // Should throw an EmailException
    	} catch (EmailException e) {
    		String message = "Address List provided was invalid";
    		assertEquals(message, e.getMessage()); // Verifies the exception message thrown is same as message
    		throw e;
    	}
    }

    // Test adding an empty array
    @Test(expected = EmailException.class)
    public void testAddBcc_EmptyArray() throws EmailException {
    	try {
            email.addBcc(new String[0]); // Should throw an EmailException
    	} catch (EmailException e) {
    		String message = "Address List provided was invalid";
    		assertEquals(message, e.getMessage()); // Verifies the exception message thrown is same as message
    		throw e;
    	}
    }

    // Test adding an invalid email
    @Test(expected = EmailException.class)
    public void testAddBcc_InvalidEmail() throws EmailException {
    	try {
            email.addBcc("invalid-email"); // Should throw an EmailException
    	} catch (EmailException e) {
    		String message = "javax.mail.internet.AddressException: Missing final '@domain' in string ``invalid-email''";
    		assertEquals(message, e.getMessage()); // Verifies the exception message thrown is same as message
    		throw e;
    	}
    }

    // Test adding valid CC email
    @Test
    public void testAddCc_ValidEmail() throws EmailException {
        email.addCc("test@example.com");
        
        // Verifies the email was added successfully
        assertNotNull("Email object should not be null", email);
    }
    
    // Test adding invalid CC email
    @Test(expected = EmailException.class)
    public void testAddCc_InvalidEmail() throws EmailException {
    	try {
        	email.addCc("invalid-email"); // Should throw an EmailException
    	} catch (EmailException e) {
    		String message = "javax.mail.internet.AddressException: Missing final '@domain' in string ``invalid-email''";
    		assertEquals(message, e.getMessage()); // Verifies the exception message thrown is same as message
    		throw e;
    	}
    }
    
    // Test adding null CC email
    @Test(expected = EmailException.class)
    public void testAddCc_EmptyInput() throws EmailException {
    	try {
        	email.addCc(""); // Should throw an EmailException
    	} catch (EmailException e) {
    		String message = "javax.mail.internet.AddressException: Illegal address in string ``''";
    		assertEquals(message, e.getMessage()); // Verifies the exception message thrown is same as message
    		throw e;
    	}
    }

    // Test adding a valid header
    @Test
    public void testAddHeader_ValidHeader() throws EmailException {
        email.addHeader("Send To", "Value");
        
        // Verifies the header was added successfully
        assertNotNull("Email object should not be null", email);
    }
    
    // Test adding an empty name for header
    @Test(expected = IllegalArgumentException.class)
    public void testAddHeader_EmptyName() throws EmailException {
    	try {
        	email.addHeader("", "Value"); // Should throw an EmailException
    	} catch (IllegalArgumentException e) {
    		String message = "name can not be null or empty";
    		assertEquals(message, e.getMessage()); // Verifies the exception message thrown is same as message
    		throw e;
    	}
    }
    
    // Test adding a null name for header
    @Test(expected = IllegalArgumentException.class)
    public void testAddHeader_NullName() throws EmailException {
    	try {
    		email.addHeader((String)null, "Value"); // Should throw an EmailException
    	} catch (IllegalArgumentException e) {
    		String message = "name can not be null or empty";
    		assertEquals(message, e.getMessage()); // Verifies the exception message thrown is same as message
    		throw e;
    	}
    }
    
    // Test adding an empty value for header
    @Test(expected = IllegalArgumentException.class)
    public void testAddHeader_EmptyValue() throws EmailException {
    	try {
        	email.addHeader("Name", ""); // Should throw an EmailException
    	} catch (IllegalArgumentException e) {
    		String message = "value can not be null or empty";
    		assertEquals(message, e.getMessage()); // Verifies the exception message thrown is same as message
    		throw e;
    	}
    }
    
    // Test adding a null value for header
    @Test(expected = IllegalArgumentException.class)
    public void testAddHeader_NullValue() throws EmailException {
    	try {
        	email.addHeader("Name", (String)null); // Should throw an EmailException
    	} catch (IllegalArgumentException e) {
    		String message = "value can not be null or empty";
    		assertEquals(message, e.getMessage()); // Verifies the exception message thrown is same as message
    		throw e;
    	}
    }
    
    // Test adding valid ReplyTo email addresses
    @Test
    public void testReplyTo_ValidEmail() throws EmailException {
    	email.addReplyTo("test@example.com", "Bob");
    	email.addReplyTo("test2@example.com", "Bill");
    	email.addReplyTo("test3@example.com", "Joe");
    	List<InternetAddress> replyToAddr = email.getReplyToAddresses();
    	assertEquals(3, email.getReplyToAddresses().size()); // Verifies reply list has 3 emails
    	assertNotNull("Reply to addresses shouldn't be null", replyToAddr); // Verifies reply list isn't null
    }
    
    // Test adding invalid ReplyTo email addresses
    @Test(expected = EmailException.class)
    public void testReplyTo_InvalidEmail() throws EmailException {
    	try {
    		email.addReplyTo("", "Bob");
    	} catch (EmailException e) {
    		String message = "javax.mail.internet.AddressException: Illegal address in string ``''";
    		assertEquals(message, e.getMessage()); // Verifies the exception message thrown is same as message
    		throw e;
    	}
    }
    
    // Test case given in class, buildMimeMessage null check
    @Test(expected = RuntimeException.class)
    public void testbuildMimeMessageNullCheck() throws Exception {
    	try {
    		email.setHostName("localhost");
    		email.setSmtpPort(1234);
    		email.setFrom("a@b.com");
    		email.addTo("c@d.com");
    		email.setSubject("test mail");
    		email.setCharset("ISO-8859-1");
    		email.setContent("test content", "text/plain");
    		email.buildMimeMessage();
    		email.buildMimeMessage();
    	} catch (RuntimeException re) {
    		String message = "The MimeMessage is already built.";
    		assertEquals(message, re.getMessage()); // Verifies the exception message thrown is same as message
    		throw re;
    	}
    }
    
    // Test for buildMimeMessage without a from address
    @Test(expected = EmailException.class)
    public void testBuildMimeMessage_NoFromAddress() throws EmailException {
    	try {
        	email.setHostName("mail.host");
        	email.buildMimeMessage(); // throw exception for noFromAddress
    	} catch (EmailException e) {
    		String message = "From address required";
    		assertEquals(message, e.getMessage()); // Verifies the exception message thrown is same as message
    		throw e;
    	}
    }
    
    // Test for buildMimeMessage with from address but no receiver
    @Test(expected=EmailException.class)
    public void testBuildMimeMessage_WithNoReceiver() throws EmailException {
    	try {
        	email.setHostName("mail.host");
        	email.setSubject("Test Subject");
        	email.setFrom("test@mail.com");
        	email.buildMimeMessage();
    	} catch (EmailException e) {
    		String message = "At least one receiver address required";
    		assertEquals(message, e.getMessage()); // Verifies the exception message thrown is same as message
    		throw e;
    	}
    }

    // Test for buildMimeMessage with a subject but no from address
	@Test(expected=EmailException.class)
    public void testBuildMimeMessage_WithSubject() throws EmailException {
		try {
	    	email.setHostName("mail.host");
	    	email.setSubject("Test Subject");
	    	email.buildMimeMessage();
		} catch (EmailException e) {
    		String message = "From address required";
    		assertEquals(message, e.getMessage()); // Verifies the exception message thrown is same as message
    		throw e;
    	}
    }
	
	// Test for valid buildMimeMessage with reply list
	@Test
	public void testBuildMimeMessage_WithReplyList() throws EmailException {
	    email.setHostName("mail.host");
	    email.setFrom("from@example.com");
	    email.addTo("to@example.com");
	    email.addCc("cc@example.com");
	    email.addReplyTo("tester@example.com");
	    email.buildMimeMessage();
	    assertEquals(1, email.replyList.size());
	}
	
	// Test for valid buildMimeMessage with headers
	@Test
	public void testBuildMimeMessage_WithHeaders() throws EmailException {
	    email.setHostName("mail.host");
	    email.setFrom("from@example.com");
	    email.addTo("to@example.com");
	    email.addCc("cc@example.com");
	    email.addHeader("Testing", "Big test");
	    email.buildMimeMessage();
	    assertEquals(1, email.headers.size());
	}
	
	// Test for valid mime message with cc
	@Test
	public void testBuildMimeMessage_WithCC() throws EmailException {
	    email.setHostName("mail.host");
	    email.setFrom("from@example.com");
	    email.addTo("to@example.com");
	    email.addCc("cc@example.com");
	    email.buildMimeMessage();
	    assertEquals(1, email.ccList.size());
	}
	
	// Test for valid mime message with bcc
	@Test
	public void testBuildMimeMessage_WithBCC() throws EmailException {
	    email.setHostName("mail.host");
	    email.setFrom("from@example.com");
	    email.addTo("to@example.com");
	    email.addCc("cc@example.com");
	    email.addBcc("test@example.com");
	    email.buildMimeMessage();
	    assertEquals(1, email.bccList.size());
	}
	
	// Test for valid mime message with content type
	@Test
	public void testBuildMimeMessage_WithContent() throws EmailException {
	    email.setHostName("mail.host");
	    email.setFrom("from@example.com");
	    email.setContent("Test", "Test");
	    email.addTo("to@example.com");
	    email.addCc("cc@example.com");
	    email.addBcc("test@example.com");
	    email.buildMimeMessage();
	    assertEquals(email.contentType, "Test");
	}

    // Standard getHostName test and seeing if set name is equal to get name
    @Test
    public void testGetHostName() {
    	email.setHostName("testhost@name.com");
        String hostName = email.getHostName();
        assertEquals("Expected hostname", "testhost@name.com", hostName);
    }
    
    // Test when session is not null
    @Test
    public void testGetHostName_WhenSessionIsNotNull() {
        Properties properties = new Properties();

        Session session = Session.getInstance(properties);

        // Set the session in the email object
        email.setMailSession(session);
        
        String hostName = email.getHostName();
        assertNull("Host name should be null when MAIL_HOST is not set", hostName);
    }

    // Test when session is null and hostName is not empty
    @Test
    public void testGetHostName_WhenSessionIsNullAndHostNameIsNotEmpty() {
        email.setHostName("testhost@name.com");

        // Verify that getHostName returns the hostName
        String hostName = email.getHostName();
        assertEquals("Host name should match the set hostName", "testhost@name.com", hostName);
    }
    
    // Test getSentDate with valid date
    @Test
    public void testGetSentDate_DateNotNull() {
    	Date testDate = new Date();
    	email.setSentDate(testDate);
    	
    	Date sentDate = email.getSentDate();
    	assertNotNull("Sent date should not be null", sentDate);
    }
    
    // Test getSentDate after setting sentDate to null;
    @Test
    public void testGetSentDate_DateIsNull() {
    	email.setSentDate((Date)null);
    	Date sentDate = email.getSentDate();
    	assertEquals("Sent date should be new Date", sentDate, new Date());
    }

    // Test example socket connection timeout
    @Test
    public void testGetSocketConnectionTimeout() {
        int timeout = email.getSocketConnectionTimeout();
        assertEquals("Expected timeout value", 60000, timeout);
    }

    // Test valid set from
    @Test
    public void testSetFrom() throws EmailException {
        email.setFrom("test123@a.com");
        InternetAddress fromAddress = email.getFromAddress();
        assertNotNull("From address shouldn't be null", fromAddress);
        assertEquals("From address should match", "test123@a.com", fromAddress.getAddress());
    }
    
    // Test getMailSession with empty host name
    @Test(expected=EmailException.class)
    public void testGetMailSession_EmptyHostName() throws EmailException {
    	try {
        	email.setHostName("");
        	email.getMailSession();
    	} catch (EmailException e) {
    		String message = "Cannot find valid hostname for mail session";
    		assertEquals(message, e.getMessage()); // Verifies the exception message thrown is same as message
    		throw e;
    	}
    }
    
    // Test for getMailSession with SSLConnect set to true
    @Test
    public void testGetMailSession_SSLConnect() throws EmailException {
    	email.setHostName("TestHost");
    	email.setSSLOnConnect(true);
    	email.getMailSession();
    	assertEquals(email.isSSLOnConnect(), true);
    }
}
