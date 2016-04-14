package at.jkopec.dezsys09;
import at.jkopec.dezsys09.db.User;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;


/**
 * Created by jakubkopec on 14.04.16.
 */
public class EndpointTest {

    private RestTemplate restTemplate;
    private static String HOST = "localhost:34789";

    @Before
    public void before() {
        this.restTemplate = new RestTemplate();
    }

    @BeforeClass
    public static void startSpring() {
        String[] args = {"--spring.profiles.active=test", "--server.port=34789"};
        App.main(args);
    }

    //\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\

    @Test
    public void testRegisterSuccess() {
        User user = new User("test@test.com", "Test", "12345");
        String response = restTemplate.postForObject("http://" + HOST + "/register", user, String.class);
        assertEquals("User test@test.com saved!", response);
    }

    @Test(expected=HttpClientErrorException.class)
    public void testRegisterMissingName() {
        User user = new User("test1@test.com", null, "12345");
        restTemplate.postForObject("http://" + HOST + "/register", user, String.class);
    }

    @Test(expected=HttpClientErrorException.class)
    public void testRegisterMissingPassword() {
        User user = new User("test2@test.com", "Test", null);
        restTemplate.postForObject("http://" + HOST + "/register", user, String.class);
    }

    @Test(expected=HttpClientErrorException.class)
    public void testRegisterMissingEmail() {
        User user = new User(null, "Test", "12345");
        restTemplate.postForObject("http://" + HOST + "/register", user, String.class);
    }

    @Test(expected=HttpClientErrorException.class)
    public void testRegisterMissingParameters() {
        User user = new User();
        restTemplate.postForObject("http://" + HOST + "/register", user, String.class);
    }

    @Test(expected=HttpClientErrorException.class)
    public void testRegisterEmptyPassword() {
        User user = new User("test4@test.com", "Name", "");
        restTemplate.postForObject("http://" + HOST + "/register", user, String.class);
    }

    @Test(expected=HttpClientErrorException.class)
    public void testRegisterEmptyName() {
        User user = new User("test5@test.com", "", "12345");
        restTemplate.postForObject("http://" + HOST + "/register", user, String.class);
    }

    @Test(expected=HttpClientErrorException.class)
    public void testRegisterEmptyEmail() {
        User user = new User("", "Name", "12345");
        restTemplate.postForObject("http://" + HOST + "/register", user, String.class);
    }

    @Test(expected=HttpClientErrorException.class)
    public void testRegisterPasswordTooShort() {
        User user = new User("test6@test.com", "Name", "1234");
        restTemplate.postForObject("http://" + HOST + "/register", user, String.class);
    }

    @Test(expected=HttpClientErrorException.class)
    public void testAlreadyRegistered() {
        User user1 = new User("test3@test.com", "Test", "12345");
        restTemplate.postForObject("http://" + HOST + "/register", user1, String.class);
        User user2 = new User("test3@test.com", "Test", "12345");
        restTemplate.postForObject("http://" + HOST + "/register", user2, String.class);
    }

    //\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\

    @Test
    public void testLoginSuccess() {
        User user = new User("test7@test.com", "Test", "12345");
        ResponseEntity responseRegister = restTemplate.postForEntity("http://" + HOST + "/register", user, String.class);
        assertEquals(Response.Status.CREATED.getStatusCode(), responseRegister.getStatusCode().value());

        String response = restTemplate.postForObject("http://" + HOST + "/login", user, String.class);
        assertEquals("Welcome Test!", response);
    }

    @Test(expected=HttpClientErrorException.class)
    public void testLoginEmptyEmail() {
        User user = new User("", "1234");
        restTemplate.postForObject("http://" + HOST + "/login", user, String.class);
    }

    @Test(expected=HttpClientErrorException.class)
    public void testLoginEmptyPassword() {
        User user = new User("test@test.com", "");
        restTemplate.postForObject("http://" + HOST + "/login", user, String.class);
    }

    @Test(expected=HttpClientErrorException.class)
    public void testLoginNoEmail() {
        User user = new User(null, "1234");
        restTemplate.postForObject("http://" + HOST + "/login", user, String.class);
    }

    @Test(expected=HttpClientErrorException.class)
    public void testLoginNoPassword() {
        User user = new User("test@test.com", null);
        restTemplate.postForObject("http://" + HOST + "/login", user, String.class);
    }

    @Test(expected=HttpClientErrorException.class)
    public void testLoginWrongEmail() {
        User user = new User("test.at", "1234");
        restTemplate.postForObject("http://" + HOST + "/login", user, String.class);
    }

    @Test(expected=HttpClientErrorException.class)
    public void testLoginWrongPassword() {
        User user = new User("test@test.com", "54321");
        restTemplate.postForObject("http://" + HOST + "/login", user, String.class);
    }

    @Test(expected=HttpClientErrorException.class)
    public void testLoginNoEmailNoPassword() {
        User user = new User(null, null);
        restTemplate.postForObject("http://" + HOST + "/login", user, String.class);
    }

    //\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\
}
