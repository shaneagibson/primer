package uk.co.epsilontechnologies.primer.domain;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class StringMatchableTest {

    private StringMatchable underTest;

    @Test
    public void shouldMatchSameStringValue() {

        // arrange
        this.underTest = new StringMatchable("abc123");

        // act
        final boolean result = this.underTest.match("abc123");

        // assert
        assertTrue(result);

    }

    @Test
    public void shouldNotMatchDifferentStringValue() {

        // arrange
        this.underTest = new StringMatchable("123abc");

        // act
        final boolean result = this.underTest.match("abc123");

        // assert
        assertFalse(result);

    }

}
