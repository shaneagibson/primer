package uk.co.epsilontechnologies.primer.domain;

import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.XMLUnit;
import org.xml.sax.SAXException;

import java.io.IOException;

public class XmlMatchable implements Matchable {

    public static Matchable xml(final String xml) {
        return new XmlMatchable(xml);
    }

    /**
     * Configures the XMLUnit to ignore whitespace in the XML String
     */
    static {
        XMLUnit.setIgnoreWhitespace(true);
    }

    private final String primedString;

    XmlMatchable(String primedString) {
        this.primedString = primedString;
    }

    @Override
    public boolean match(final String requestString) {
        try {
            final Diff xmlDiff = new Diff(primedString, requestString);
            return xmlDiff.similar();
        } catch (final SAXException | IOException e) {
            return false;
        }
    }

}
