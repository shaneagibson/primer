package uk.co.epsilontechnologies.primer.domain;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class JsonMatchableTest {

    private JsonMatchable underTest;

    @Test
    public void shouldMatchSameJson() {

        // arrange
        this.underTest = new JsonMatchable("{ \"one\" : \"valueABC\", \"two\" : \"value123\" }");

        // act
        boolean result = this.underTest.match("{ \"one\" : \"valueABC\", \"two\" : \"value123\" }");

        // assert
        assertTrue(result);
    }

    @Test
    public void shouldMatchJsonWithAdditionalWhitespace() {

        // arrange
        this.underTest = new JsonMatchable("{\"one\":\"valueABC\",\"two\":\"value123\"}");

        // act
        boolean result = this.underTest.match("{ \"one\" : \"valueABC\", \"two\" : \"value123\" }");

        // assert
        assertTrue(result);
    }

    @Test
    public void shouldMatchJsonWithoutWhitespace() {

        // arrange
        this.underTest = new JsonMatchable("{ \"one\" : \"valueABC\" , \"two\" : \"value123\" }");

        // act
        boolean result = this.underTest.match("{\"one\":\"valueABC\",\"two\":\"value123\"}");

        // assert
        assertTrue(result);
    }

    @Test
    public void shouldNotMatchJsonSubset() {

        // arrange
        this.underTest = new JsonMatchable("{\"one\":\"valueABC\"}");

        // act
        boolean result = this.underTest.match("{\"one\":\"valueABC\",\"two\":\"value123\"}");

        // assert
        assertFalse(result);
    }

    @Test
    public void shouldNotMatchJsonSuperset() {

        // arrange
        this.underTest = new JsonMatchable("{\"one\":\"valueABC\",\"two\":\"value123\"}");

        // act
        boolean result = this.underTest.match("{\"one\":\"valueABC\"}");

        // assert
        assertFalse(result);
    }

}

