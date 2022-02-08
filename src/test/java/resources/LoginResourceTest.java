package resources;

import nl.han.dea.spotitubeherkansing.resources.LoginResource;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import java.io.IOException;

import static org.glassfish.jersey.internal.guava.Predicates.equalTo;
import static org.junit.jupiter.api.Assertions.*;

public class LoginResourceTest {

    private LoginResource sut;

    @BeforeEach
    public void setup(){
        sut = new LoginResource();
    }

    @Test
    void testTest(){

        assertEquals(1, 1);
    }
    @Test
    void return200code() throws IOException {
        HttpUriRequest request = new HttpGet( "http://localhost:8080/spotitube-herkansing-1.0-SNAPSHOT/" );

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute( request );

        assertEquals(202,httpResponse.getStatusLine().getStatusCode());
    }

}
