package com.zuulDemo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.zuulDemo.model.Image;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.codec.binary.Base64;
import org.assertj.core.api.SoftAssertions;
import org.json.JSONException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.io.IOException;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SpringBootWebApplicationTests {

    private RequestSpecification request = RestAssured.with();
    private Image imageResponse;
    private SoftAssertions softly = new SoftAssertions();
    private Response response;

    @Autowired
    private Image imageRequest;

    @Value("${tomcat.port}")
    String port;

    @Before
    public void createObjects() throws JSONException, IOException {

        // Prepare request headers and baseURI
        request.given().baseUri("http://localhost:" + port + "/api/")
                .contentType(ContentType.JSON);

        //Send POST request
        response = sendRequest(imageRequest, "/image");

        // Deserialize response object
        imageResponse = new ObjectMapper().readValue(response.asString(), Image.class);

    }

    // Verify "test" value after passing demo proxy is base 64 encoded
    @Test
    public void testImageEncoding() {

        // Validate that value of "image" in response is indeed "test"
        String actualValue = imageResponse.getImage();
        boolean isBase64 = Base64.isArrayByteBase64(actualValue.getBytes());
        softly.assertThat(isBase64).isTrue();
    }

    @Test
    // Verify the encoded value is in fact test
    public void testImageHasCorrectValue() {

        String actualBase64Value = imageResponse.getImage();
        String actualStringValue = new String(Base64.decodeBase64(actualBase64Value.getBytes()));
        softly.assertThat(actualStringValue).isEqualTo(imageRequest.getImage());
    }

    @After
    public void performAsserts() {
        // Perform all assertions after all tests have been executed
        softly.assertAll();
    }


    public Response sendRequest(Image imageRequest, String endpoint) throws JsonProcessingException {
        Response response = null;

        // Set values in member variables to create Request POJO
        imageRequest.setImage("test");

        // Serialize Image class and send POST request to /image Endpoint and save response
        ObjectWriter writer = new ObjectMapper().writer().withDefaultPrettyPrinter();
        response = request.when().body(writer.writeValueAsString(imageRequest)).post(endpoint);
        return response;
    }
}
