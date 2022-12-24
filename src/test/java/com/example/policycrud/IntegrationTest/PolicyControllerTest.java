package com.example.policycrud.IntegrationTest;

import com.example.policycrud.dtos.*;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static io.restassured.RestAssured.given;

public class PolicyControllerTest extends BaseIntegrationTest {


    @Test
    public void createPolicy_WhenPolicyStartDateInThePast_ShouldReturnBadRequest() {
        PolicyCreationRequest policyCreationRequest = createPolicyCreationRequest(LocalDate.now().minusDays(1L));

                given()
                        .contentType(ContentType.JSON)
                        .accept(ContentType.JSON)
                        .body(policyCreationRequest)
                        .when()
                        .post("/policies")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void createPolicy_WhenPolicyStartDateInFuture_ShouldReturnSuccess() {

        PolicyCreationRequest policyCreationRequest = createPolicyCreationRequest(LocalDate.now().plusDays(1L));

        PolicyCreationResponse response =
                given()
                        .contentType(ContentType.JSON)
                        .accept(ContentType.JSON)
                        .body(policyCreationRequest)
                        .when()
                        .post("/policies")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.CREATED.value())
                        .extract().as(PolicyCreationResponse.class);

        BigDecimal totalPremium = policyCreationRequest.getInsuredPersons().stream().map(InsuredPersonBaseDto::getPremium).reduce(BigDecimal.ZERO, BigDecimal::add);

        Assertions.assertEquals(response.getTotalPremium(), totalPremium);
        Assertions.assertEquals(response.getInsuredPersons().size(), policyCreationRequest.getInsuredPersons().size());
        Assertions.assertNotNull(response.getPolicyId());

    }

    @Test
    public void policyInformationModification_WhenEffectiveDateInPast_ShouldReturnBadRequest() {
        PolicyCreationRequest policyCreationRequest = createPolicyCreationRequest(LocalDate.now().plusDays(1L));

        PolicyCreationResponse creationResponse =
                given()
                        .contentType(ContentType.JSON)
                        .accept(ContentType.JSON)
                        .body(policyCreationRequest)
                        .when()
                        .post("/policies")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.CREATED.value())
                        .extract().as(PolicyCreationResponse.class);

        Assertions.assertNotNull(creationResponse.getPolicyId());
        //remove insuredPerson from list
        creationResponse.getInsuredPersons().remove(0);

        PolicyModificationRequest policyModificationRequest = new PolicyModificationRequest(creationResponse.getPolicyId(), LocalDate.now().minusDays(1), creationResponse.getInsuredPersons());

        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(policyModificationRequest)
                .when()
                .put("/policies/" + policyModificationRequest.getPolicyId())
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());

    }


    @Test
    public void policyInformationModification_WhenInsuredPolicyWithDeletedInsuredPerson_ShouldReturnUpdatedPolicyWithoutDeletedInsuredPerson() {
        PolicyCreationRequest policyCreationRequest = createPolicyCreationRequest(LocalDate.now().plusDays(1L));

        PolicyCreationResponse creationResponse =
                given()
                        .contentType(ContentType.JSON)
                        .accept(ContentType.JSON)
                        .body(policyCreationRequest)
                        .when()
                        .post("/policies")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.CREATED.value())
                        .extract().as(PolicyCreationResponse.class);

        Assertions.assertNotNull(creationResponse.getPolicyId());
        //remove insuredPerson "omar" from list
        creationResponse.getInsuredPersons().remove(0);

        PolicyModificationRequest policyModificationRequest = new PolicyModificationRequest(creationResponse.getPolicyId(), LocalDate.now().plusDays(1), creationResponse.getInsuredPersons());

        PolicyModificationResponse policyModificationResponse =
                given()
                        .contentType(ContentType.JSON)
                        .accept(ContentType.JSON)
                        .body(policyModificationRequest)
                        .when()
                        .put("/policies/" + policyModificationRequest.getPolicyId())
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.OK.value()).extract().as(PolicyModificationResponse.class);

        Assertions.assertEquals(1, policyModificationResponse.getInsuredPersons().size());


    }

    @Test
    public void policyInformationModification_WhenInsuredPolicyIsNotInTheList_ShouldReturnUpdatedPolicyIncludedTheNewPersonsInTheModificationRequest() {
        PolicyCreationRequest policyCreationRequest = createPolicyCreationRequest(LocalDate.now().plusDays(1L));

        PolicyCreationResponse creationResponse =
                given()
                        .contentType(ContentType.JSON)
                        .accept(ContentType.JSON)
                        .body(policyCreationRequest)
                        .when()
                        .post("/policies")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.CREATED.value())
                        .extract().as(PolicyCreationResponse.class);

        Assertions.assertNotNull(creationResponse.getPolicyId());


        InsuredPersonDto insuredPersonDto = new InsuredPersonDto();
        insuredPersonDto.setPremium(BigDecimal.TEN);
        insuredPersonDto.setFirstName("Jack");
        insuredPersonDto.setLastName("Test");
        creationResponse.getInsuredPersons().add(insuredPersonDto);

        PolicyModificationRequest policyModificationRequest = new PolicyModificationRequest(creationResponse.getPolicyId(), LocalDate.now().plusDays(1), creationResponse.getInsuredPersons());

        PolicyModificationResponse policyModificationResponse =
                given()
                        .contentType(ContentType.JSON)
                        .accept(ContentType.JSON)
                        .body(policyModificationRequest)
                        .when()
                        .put("/policies/" + policyModificationRequest.getPolicyId())
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.OK.value())
                        .extract()
                        .as(PolicyModificationResponse.class);

        Assertions.assertEquals(3, policyModificationResponse.getInsuredPersons().size());
        Assertions.assertNotNull(policyModificationResponse.getInsuredPersons().stream().filter(i -> "Jack" .equals(i.getFirstName())).findFirst().orElseGet(null));


    }

    @Test
    public void policyInformationModification_WhenPolicyIdIsNotExists_ShouldReturnBadRequest() {


        PolicyModificationRequest policyModificationRequest = new PolicyModificationRequest("111", LocalDate.now().minusDays(1), new ArrayList<>());


        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(policyModificationRequest)
                .when()
                .put("/policies/" + policyModificationRequest.getPolicyId())
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());


    }

    @Test
    public void getPolicyInformationWithId_WhenRequestDataAreNotProvided_ShouldReturnPolicyIsRunningToday() {
        PolicyCreationRequest policyCreationRequest = createPolicyCreationRequest(LocalDate.now());


        PolicyCreationResponse creationResponse =
                given()
                        .contentType(ContentType.JSON)
                        .accept(ContentType.JSON)
                        .body(policyCreationRequest)
                        .when()
                        .post("/policies")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.CREATED.value())
                        .extract().as(PolicyCreationResponse.class);

        Assertions.assertNotNull(creationResponse.getPolicyId());

        GetPolicyInformationResponse getPolicyInformationResponse =
                given()
                        .contentType(ContentType.JSON)
                        .accept(ContentType.JSON)
                        .when()
                        .get("/policies")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.OK.value())
                        .extract().as(GetPolicyInformationResponse.class);


        Assertions.assertEquals(creationResponse.getInsuredPersons(), getPolicyInformationResponse.getInsuredPersons());


    }


    @Test
    public void getPolicyInformationWithId_WhenRequestIsProvided_ShouldReturnPolicyOfRequestedDate() {
        PolicyCreationRequest policyCreationRequest1 = createPolicyCreationRequest(LocalDate.now());
        PolicyCreationRequest policyCreationRequest2 = createPolicyCreationRequest(LocalDate.now().plusDays(2));


        PolicyCreationResponse creationResponse1 =
                given()
                        .contentType(ContentType.JSON)
                        .accept(ContentType.JSON)
                        .body(policyCreationRequest1)
                        .when()
                        .post("/policies")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.CREATED.value())
                        .extract().as(PolicyCreationResponse.class);

        PolicyCreationResponse creationResponse2 =
                given()
                        .contentType(ContentType.JSON)
                        .accept(ContentType.JSON)
                        .body(policyCreationRequest2)
                        .when()
                        .post("/policies")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.CREATED.value())
                        .extract().as(PolicyCreationResponse.class);

        Assertions.assertNotNull(creationResponse1.getPolicyId());
        Assertions.assertNotNull(creationResponse2.getPolicyId());

        GetPolicyInformationResponse getPolicyInformationResponse =
                given()
                        .contentType(ContentType.JSON)
                        .accept(ContentType.JSON)
                        .when()
                        .queryParam("requestedDate", LocalDate.now().plusDays(2).format(DateTimeFormatter.ISO_DATE))
                        .get("/policies")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.OK.value())
                        .extract().as(GetPolicyInformationResponse.class);


        Assertions.assertEquals(creationResponse2.getInsuredPersons(), getPolicyInformationResponse.getInsuredPersons());
        Assertions.assertEquals(LocalDate.now().plusDays(2), getPolicyInformationResponse.getRequestDate());


    }

}

