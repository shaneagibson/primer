package uk.co.epsilontechnologies.primer.matcher;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class StringMatcherTest {

    private StringMatcher underTest;

    @Test
    public void shouldMatchSameStringValue() {

        // arrange
        this.underTest = new StringMatcher();

        // act
        final boolean result = this.underTest.match("abc123", "abc123");

        // assert
        assertTrue(result);
    }

    @Test
    public void shouldNotMatchDifferentStringValue() {

        // arrange
        this.underTest = new StringMatcher();

        // act
        final boolean result = this.underTest.match("123abc", "abc123");

        // assert
        assertFalse(result);
    }

}
