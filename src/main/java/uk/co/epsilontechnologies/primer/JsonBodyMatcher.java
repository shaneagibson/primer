package uk.co.epsilontechnologies.primer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonBodyMatcher implements Matcher<String,String> {

    @Override
    public boolean match(final String primedRequestBody, final String requestBody) {
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            final JsonNode primedRequestJson = objectMapper.readTree(primedRequestBody);
            final JsonNode requestJson = objectMapper.readTree(requestBody);
            return requestJson.equals(primedRequestJson);
        } catch (final IOException e) {
            return false;
        }
    }

}