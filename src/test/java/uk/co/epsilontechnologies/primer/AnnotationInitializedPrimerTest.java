package uk.co.epsilontechnologies.primer;

import org.apache.commons.io.IOUtils;
import org.junit.*;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static uk.co.epsilontechnologies.primer.PrimerStatics.*;

/**
 * Test case for key Primer functionality.
 *
 * @author Shane Gibson
 */
public class AnnotationInitializedPrimerTest extends AbstractPrimerTest {

    @Primable(contextPath = "/test", port = 8500)
    private Primer primer;

    @Before
    public void setUp() {
        initPrimers(this);
        primer.start();
    }

    @After
    public void tearDown() {
        primer.stop();
    }

    @Override
    public Primer primer() {
        return primer;
    }

}
