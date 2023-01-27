import Entities.Booking;
import Entities.BookingDates;
import Entities.User;
import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.text.SimpleDateFormat;

import static io.restassured.RestAssured.given;
import static io.restassured.config.LogConfig.logConfig;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

public class BookingTests {
    public static String token = "";
    public static Faker faker;
    private static RequestSpecification request;
    private static Booking booking;
    private static BookingDates bookingDates;
    private static User user;

    @BeforeAll
    public static void Setup(){
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        faker = new Faker();
        user = new User(faker.name().username(),
                faker.name().firstName(),
                faker.name().lastName(),
                faker.internet().safeEmailAddress(),
                faker.internet().password(8,10),
                faker.phoneNumber().toString());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        booking = new Booking(user.getFirstname(), user.getLastname(),
                (float)faker.number().randomDouble(2,50,100000),
                true,bookingDates,"");

    }
    @BeforeEach
    void setRequest(){
        request = given().config(RestAssured.config().logConfig(logConfig().enableLoggingOfRequestAndResponseIfValidationFails()))
                .contentType(ContentType.JSON)
                .auth().basic("admin", "password123");
    }

    @Test
    public void getAllBookingByIds_returnOk(){
        Response response = request
                .when()
                .get("/booking")
                .then()
                .extract()
                .response();
        Assertions.assertNotNull(response);
        Assertions.assertEquals(200, response.statusCode());
    }
    @Test
    public void getAllBookingsByUserFirstName_BookingExists_returnOk(){
        request
                .when()
                .queryParam("firstName", faker.name().firstName())
                .get("/booking")
                .then()
                .assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .and()
                .body("results", hasSize(greaterThan(0)));

    }

    @Test
    public void createBooking_WithValidData_returnOk(){
            request
                    .contentType(ContentType.JSON)
                    .when()
                    .body(booking)
                    .post("/booking")
                    .then()
                    .body(matchesJsonSchemaInClasspath("createBookingRequestSchema.json"))
                    .and()
                    .assertThat()
                    .statusCode(200)
                    .contentType(ContentType.JSON).and().time(lessThan(2000L))
                    .extract()
                    .jsonPath();
        }
}




