package uk.co.epsilontechnologies.primer.domain;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Matchable implementation for JSON content. Determines if the request JSON matches the primed JSON structure.
 */
public class JsonMatchable implements Matchable {

    /**
     * Statically constructs a JSON Matcher for the given primed JSON structure.
     * @param json
     * @return
     */
    public static Matchable json(final String json) {
        return new JsonMatchable(json);
    }

    /**
     * The primed JSON structure.
     */
    private final String primedString;

    /**
     * Constructor for JSON Matchable
     * @param primedString the primed json string
     */
    JsonMatchable(final String primedString) {
        this.primedString = primedString;
    }

    /**
     * Determines if the given request string matches the primed JSON structure for this instance.
     * @param requestString the request string to match
     * @return true if the JSON structures match, false otherwise.
     */
    @Override
    public boolean match(final String requestString) {
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            final JsonNode primedRequestJson = objectMapper.readTree(primedString);
            final JsonNode requestJson = objectMapper.readTree(requestString);
            return requestJson.equals(primedRequestJson);
        } catch (final IOException e) {
            return false;
        }
    }

}