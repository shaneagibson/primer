package uk.co.epsilontechnologies.primer.matcher;

import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.XMLUnit;
import org.xml.sax.SAXException;

import java.io.IOException;

/**
 * Matcher for 'application/xml' request Content-Type.
 * Allows the Primer to match equivalent XML messages, rather than 'exact' string matching.
 *
 * @author Shane Gibson
 */
public class XmlBodyMatcher implements Matcher<String,String> {

    /**
     * Configures the XMLUnit to ignore whitespace in the XML String
     */
    static {
        XMLUnit.setIgnoreWhitespace(true);
    }

    /**
     * Matches the XML request body against the primed request body.
     * @param primedRequestBody the primed request body to match
     * @param requestBody the body of the request that has been issued
     * @return true if the request body matches the primed request body, false otherwise
     */
    @Override
    public boolean match(final String primedRequestBody, final String requestBody) {
        try {
            final Diff xmlDiff = new Diff(primedRequestBody, requestBody);
            return xmlDiff.similar();
        } catch (final SAXException | IOException e) {
            return false;
        }
    }

}