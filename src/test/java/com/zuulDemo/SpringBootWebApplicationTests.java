package com.zuulDemo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zuulDemo.model.Image;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SpringBootWebApplicationTests {

    private RequestSpecification request = RestAssured.with();
    private Response response;

    @Autowired
    private Image image;

    @Before
    public void createObjects() throws JSONException {
        request.given().baseUri("http://localhost:8081/api/")
        .contentType(ContentType.JSON);
    }
    
    @Test
    public void testImageEncoding() throws Exception {

        String requestBody ="{\"image\":\"test\"}";
        response = request.when().body(requestBody).post("/image");
        image = new ObjectMapper().readValue(response.asString(), Image.class);
        System.out.println("RESPONSE IS: " + image.getImage());


    }
}
