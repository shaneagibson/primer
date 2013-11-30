package uk.co.epsilontechnologies.primer;

import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
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
public class ConstructorInitializedPrimerTest extends AbstractPrimerTest {

    private static final Primer primer = new Primer("/test", 8500);

    @BeforeClass
    public static void setUpClass() {
        primer.start();
    }

    @AfterClass
    public static void tearDownClass() {
        primer.stop();
    }

    @After
    public void tearDown() {
        primer.reset();
    }

    @Override
    public Primer primer() {
        return primer;
    }

}
