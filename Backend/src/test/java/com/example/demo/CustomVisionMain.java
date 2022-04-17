package com.example.demo;

import net.minidev.json.JSONObject;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

public class CustomVisionMain {
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Training-Key", "5a585b5fabb24dddbe89f6406afbf71a");
        headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<JSONObject> entity = new HttpEntity<>(new JSONObject(), headers);
        String URL = "https://westus2.api.cognitive.microsoft.com/customvision/v3.3/training/projects/cd621fcb-704f-48ab-bdb0-ad1e4e72e2b2/train";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> result = restTemplate.postForEntity(URL, entity, String.class);

    }

}
