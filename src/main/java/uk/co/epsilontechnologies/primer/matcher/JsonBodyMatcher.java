package uk.co.epsilontechnologies.primer.matcher;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Matcher for 'application/json' request Content-Type.
 * Allows the Primer to match equivalent JSON messages, rather than 'exact' string matching.
 *
 * @author Shane Gibson
 */
public class JsonBodyMatcher implements Matcher<String,String> {

    /**
     * Matches the JSON request body against the primed request body.
     * @param primedRequestBody the primed request body to match
     * @param requestBody the body of the request that has been issued
     * @return true if the request body matches the primed request body, false otherwise
     */
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