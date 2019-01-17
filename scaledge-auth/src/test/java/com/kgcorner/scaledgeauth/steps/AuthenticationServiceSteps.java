package com.kgcorner.scaledgeauth.steps;

import com.kgcorner.models.Token;
import com.kgcorner.scaledgeauth.AuthServiceTestConf;
import com.kgcorner.scaledgeauth.Constants;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.Date;


@AuthServiceTestConf
public class AuthenticationServiceSteps {
    private RestTemplate restTemplate = new RestTemplate();
    private static final String LOGIN_URL = "/login";
    private String basicToken = null;
    private ResponseEntity<Token> loginResponseEntity;

    @Given("^user exists in system with username \"([^\"]*)\" and password \"([^\"]*)\"$")
    public void userExistsInSystemWithUsernameAndPassword(String username, String password) throws Throwable {
        basicToken = username+":"+password;
        basicToken = new String(Base64.getEncoder().encode(basicToken.getBytes()));
        basicToken = "Basic " + basicToken;
    }

    @When("^User attempts to login$")
    public void userAttemptsToLogin() throws Throwable {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", basicToken);
        String url = Constants.HOST + LOGIN_URL;
        HttpEntity<String> httpEntity = new HttpEntity<>(null, headers);
        loginResponseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, Token.class);
    }

    @Then("^server should return with status code '(\\d+)' and Token object as json$")
    public void serverShouldReturnWithStatusCodeAndTokenObjectAsJson(int status) throws Throwable {
        Assert.assertEquals("Status on login is not matching", status,
                loginResponseEntity.getStatusCode().value());
        Token token = loginResponseEntity.getBody();
        Assert.assertNotNull("Token received on login is null", token);
        Assert.assertNotNull("Access Token received on login is null", token.getAccessToken());
        Assert.assertNotNull("Refresh Token received on login is null", token.getRefreshToken());
        Assert.assertNotNull("Expire date received on login is null", token.getExpiresOn());
        Assert.assertTrue("Expire date is past date",
                (new Date().getTime() - token.getExpiresOn().getTime()) < 0);
    }
}
