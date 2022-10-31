package com.silenauth.simcheck.demo;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;

@Service
public class SIMProcessor {

    @Value("${tru.url:}")
    protected String URL;

    @Autowired
    private OAuth2RestTemplate truClient;

    public JSONObject doSim(String phoneNumber, String referenceId) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        JSONObject body = new JSONObject();
        body.put("phone_number", phoneNumber);
        if (referenceId!=null) body.put("reference_id", referenceId);
        try {

            HttpEntity<String> request = new HttpEntity<String>(body.toString(), headers);
            ResponseEntity<String> response = truClient.exchange(URL + "/sim_check/v0.1/checks", HttpMethod.POST,
                    request,
                    String.class);
            if (response.getStatusCode()
                    .equals(HttpStatus.CREATED)) {
                try {
                    JSONObject res = new JSONObject(response.getBody());
                    return res;
                } catch (JSONException e) {
                    System.out.println("Error: Cannot parse response " + response.getBody() + " " + e.getMessage());
                }
            }
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().is4xxClientError() || e.getStatusCode().is5xxServerError()) {
                return new JSONObject(e.getResponseBodyAsString());
            }
        } catch (RestClientException e) {
            System.out.println("Error: RestClientException " + e.getMessage());
        }
        return null;
    }

}
