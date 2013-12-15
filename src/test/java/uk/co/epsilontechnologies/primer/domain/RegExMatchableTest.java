package uk.co.epsilontechnologies.primer.domain;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RegExMatchableTest {

    private RegExMatchable underTest;

    @Test
    public void shouldMatchEqualRegExExpression() {

        // arrange
        this.underTest = new RegExMatchable(" \"blah\" : \"([a-z]{5})\" ");

        // act
        final boolean result = this.underTest.match(" \"blah\" : \"value\" ");

        // assert
        assertTrue(result);
    }

    @Test
    public void shouldNotMatchUnequalRegExExpression() {

        // arrange
        this.underTest = new RegExMatchable(" \"blah\" : \"([a-z]{5})\" ");

        // act
        final boolean result = this.underTest.match(" \"blah\" : \"values\" ");

        // assert
        assertFalse(result);
    }

}
