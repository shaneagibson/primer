package uk.co.epsilontechnologies.primer.matcher;

import org.junit.Test;
import uk.co.epsilontechnologies.primer.domain.Matchable;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static uk.co.epsilontechnologies.primer.domain.StringMatchable.eq;

public class MapMatcherTest {

    private MapMatcher underTest;

    @Test
    public void shouldMatchExactlySameMap() {

        // arrange
        this.underTest = new MapMatcher();
        final Map<String,Matchable> primedMap = new HashMap<>();
        primedMap.put("key1", eq("value1"));
        primedMap.put("key2", eq("value2"));
        final Map<String,String> requestMap = new HashMap<>();
        requestMap.put("key1", "value1");
        requestMap.put("key2", "value2");

        // act
        final boolean result = this.underTest.match(primedMap, requestMap);

        // assert
        assertTrue(result);
    }

    @Test
    public void shouldNotMatchDifferentMap() {

        // arrange
        this.underTest = new MapMatcher();
        final Map<String,Matchable> primedMap = new HashMap<>();
        primedMap.put("key1", eq("value1"));
        primedMap.put("key2", eq("value2"));
        final Map<String,String> requestMap = new HashMap<>();
        requestMap.put("key3", "value3");
        requestMap.put("key4", "value4");

        // act
        final boolean result = this.underTest.match(primedMap, requestMap);

        // assert
        assertFalse(result);
    }

    @Test
    public void shouldMatchMapContainingSubset() {

        // arrange
        this.underTest = new MapMatcher();
        final Map<String,Matchable> primedMap = new HashMap<>();
        primedMap.put("key1", eq("value1"));
        primedMap.put("key2", eq("value2"));
        final Map<String,String> requestMap = new HashMap<>();
        requestMap.put("key1", "value1");
        requestMap.put("key2", "value2");
        requestMap.put("key3", "value3");

        // act
        final boolean result = this.underTest.match(primedMap, requestMap);

        // assert
        assertTrue(result);
    }

    @Test
    public void shouldNotMatchMapContainingSuperset() {

        // arrange
        this.underTest = new MapMatcher();
        final Map<String,Matchable> primedMap = new HashMap<>();
        primedMap.put("key1", eq("value1"));
        primedMap.put("key2", eq("value2"));
        primedMap.put("key3", eq("value3"));
        final Map<String,String> requestMap = new HashMap<>();
        requestMap.put("key1", "value1");
        requestMap.put("key2", "value2");

        // act
        final boolean result = this.underTest.match(primedMap, requestMap);

        // assert
        assertFalse(result);
    }

}
