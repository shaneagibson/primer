package uk.co.epsilontechnologies.primer;

import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.XMLUnit;
import org.xml.sax.SAXException;

import java.io.IOException;

public class XmlBodyMatcher implements Matcher<String,String> {

    static {
        XMLUnit.setIgnoreWhitespace(true);
    }

    @Override
    public boolean match(final String primedRequestBody, final String requestBody) {
        try {
            final Diff xmlDiff = new Diff(primedRequestBody, requestBody);
            return xmlDiff.similar();
        } catch (SAXException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
    }

}