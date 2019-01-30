package com.kgcorner.scaledgeauth.steps;

import com.kgcorner.dto.Token;
import com.kgcorner.dto.UserPreview;
import com.kgcorner.scaledgeauth.ApplicationProperties;
import com.kgcorner.scaledgeauth.AuthServiceTestConf;
import com.kgcorner.scaledgeauth.Constants;
import com.kgcorner.util.GsonUtil;
import com.kgcorner.util.JwtUtility;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.Date;


@AuthServiceTestConf
public class AuthenticationServiceSteps {
    private RestTemplate restTemplate = new RestTemplate();
    private static final String LOGIN_URL = "/login";
    private static final String REFRESH_URL = "/refresh?refresh-token=%s";
    private static final String VALIDATE_URL = "/validates";
    private String basicToken = null;
    private ResponseEntity<Token> loginResponseEntity;
    private ResponseEntity<UserPreview> userPreviewResponseEntity;
    private static final String USER_CLAIM_KEY = "user";

    @Autowired
    private ApplicationProperties properties;
    private ResponseEntity<Token> refreshTokenResponseEntity;

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
        try {
            loginResponseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, Token.class);
        } catch (HttpClientErrorException x) {
            loginResponseEntity = new ResponseEntity(x.getStatusCode());
        }
    }

    @Then("^server should return with status code '(\\d+)'.*$")
    public void serverShouldReturnWithStatusCodeAndTokenObjectAsJson(int status) throws Throwable {
        Assert.assertEquals("Status on login is not matching", status,
                loginResponseEntity.getStatusCode().value());
        if(status == 200) {
            Token token = loginResponseEntity.getBody();
            Assert.assertNotNull("Token received on login is null", token);
            Assert.assertNotNull("Access Token received on login is null", token.getAccessToken());
            Assert.assertNotNull("Refresh Token received on login is null", token.getRefreshToken());
            Assert.assertNotNull("Expire date received on login is null", token.getExpiresOn());
            Assert.assertTrue("Expire date is past date",
                    (new Date().getTime() - token.getExpiresOn().getTime()) < 0);
        }
    }

    @And("^user usage non-basic token$")
    public void userUsageNonBasicToken() throws Throwable {
        basicToken = basicToken.replace("Basic ","Non_basic ");
    }

    @And("^user usage empty basic token$")
    public void userUsageEmptyBasicToken() throws Throwable {
        basicToken = "Basic ";
    }

    @And("^user usage basic token in invalid format$")
    public void userUsageBasicTokenInInvalidFormat() throws Throwable {
        basicToken = "asadjsljdlfk";
        basicToken = new String(Base64.getEncoder().encode(basicToken.getBytes()));
        basicToken = "Basic " + basicToken;
    }

    @When("^User asks to validate received JWT$")
    public void userAsksToValidateReceivedJWT() throws Throwable {
        Token token = loginResponseEntity.getBody();
        Assert.assertNotNull(token);
        String accessToken = token.getAccessToken();
        String bearerToken = "Bearer " + accessToken;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", bearerToken);
        String url = Constants.HOST + VALIDATE_URL;
        HttpEntity<String> httpEntity = new HttpEntity<>(null, headers);
        try {
            userPreviewResponseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, UserPreview.class);
        } catch (HttpClientErrorException x) {
            userPreviewResponseEntity = new ResponseEntity(x.getStatusCode());
        }
    }

    @Then("^server should return UserPreview object which JWT belongs to and status code '(\\d+)'$")
    public void serverShouldReturnUserPreviewObjectWhichJWTBelongsToAndStatusCode(int status) throws Throwable {
        Assert.assertEquals("Status is not matching for JWT validate",
                status, userPreviewResponseEntity.getStatusCode().value());
        UserPreview userPreview = userPreviewResponseEntity.getBody();
        String claim = JwtUtility.getClaim(USER_CLAIM_KEY, loginResponseEntity.getBody().getAccessToken());
        UserPreview userPreviewInJwt = GsonUtil.getGson().fromJson(claim, UserPreview.class);
        Assert.assertEquals("Response of validate does not contain same user name", userPreviewInJwt.getName()
                , userPreview.getName());
        Assert.assertEquals("Response of validate does not contain same user id", userPreviewInJwt.getId()
                , userPreview.getId());
    }

    @When("^User attempts validate api with invalid JWT$")
    public void userAttemptsValidateApiWithInvalidJWT() throws Throwable {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer sdjkaskldjklklasdjklsad");
        String url = Constants.HOST + VALIDATE_URL;
        HttpEntity<String> httpEntity = new HttpEntity<>(null, headers);
        try {
            userPreviewResponseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, UserPreview.class);
        } catch (HttpClientErrorException x) {
            userPreviewResponseEntity = new ResponseEntity(x.getStatusCode());
        }
    }

    @Then("^Server should respond with status '(\\d+)'$")
    public void serverShouldRespondWithStatus(int status) throws Throwable {
        Assert.assertEquals("Status is not matching for validate api while using invalid JWT",
                status, userPreviewResponseEntity.getStatusCode().value());
    }

    @When("^User attempts validate api with expired JWT$")
    public void userAttemptsValidateApiWithExpiredJWT() throws Throwable {
        String salt = properties.getAuthSecretKey();
        String token = JwtUtility.createJWTToken(salt, null, -1000);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer "+token);
        String url = Constants.HOST + VALIDATE_URL;
        HttpEntity<String> httpEntity = new HttpEntity<>(null, headers);
        try {
            userPreviewResponseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, UserPreview.class);
        } catch (HttpClientErrorException x) {
            userPreviewResponseEntity = new ResponseEntity(x.getStatusCode());
        }
    }

    @When("^User requests to refresh token$")
    public void userRequestsToRefreshToken() throws Throwable {
        String refreshToken =loginResponseEntity.getBody().getRefreshToken();
        String url = Constants.HOST + REFRESH_URL;
        url = String.format(url, refreshToken);
        refreshTokenResponseEntity = restTemplate.getForEntity(url, Token.class);
    }

    @Then("^server should return new token and status '(\\d+)'$")
    public void serverShouldReturnNewTokenAndStatus(int status) throws Throwable {
        Assert.assertEquals("Status is not matching for refresh token", status,
                refreshTokenResponseEntity.getStatusCode().value());
        if(status == 200) {
            Token newToken = refreshTokenResponseEntity.getBody();
            Token oldToken = loginResponseEntity.getBody();
            Assert.assertNotEquals("refreshed access token is same as old one",
                    newToken.getAccessToken().equals(oldToken.getAccessToken()));
            Assert.assertNotEquals("refreshed refresh token is same as old one",
                    newToken.getRefreshToken().equals(oldToken.getRefreshToken()));
            Assert.assertNotEquals("refreshed expiresOn value of new token is same as old one",
                    newToken.getExpiresOn().equals(oldToken.getExpiresOn()));
        }
    }

    @When("^User requests to refresh token using invalid token$")
    public void userRequestsToRefreshTokenUsingInvalidToken() throws Throwable {
        String url = Constants.HOST + REFRESH_URL;
        url = String.format(url, "invalid refresh token");
        try {
            refreshTokenResponseEntity = restTemplate.getForEntity(url, Token.class);
        } catch (HttpClientErrorException x) {
            refreshTokenResponseEntity = new ResponseEntity<>(x.getStatusCode());
        }
    }
}
