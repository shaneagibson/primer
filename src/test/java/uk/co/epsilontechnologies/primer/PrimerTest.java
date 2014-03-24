package uk.co.epsilontechnologies.primer;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import uk.co.epsilontechnologies.primer.domain.PrimedInvocation;
import uk.co.epsilontechnologies.primer.server.PrimerServer;

import java.util.List;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class PrimerTest {

    private Primer underTest;

    @Mock
    private List<PrimedInvocation> mockPrimedInvocations;

    @Mock
    private PrimerServer mockPrimerServer;

    @Before
    public void setUp() {
        initMocks(this);
        this.underTest = new Primer(mockPrimerServer, mockPrimedInvocations);
    }

    @Test
    public void shouldStart() {

        // act
        this.underTest.start();

        // assert
        verify(mockPrimerServer).start();
    }

    @Test
    public void shouldStop() {

        // act
        this.underTest.stop();

        // assert
        verify(mockPrimedInvocations).clear();
        verify(mockPrimerServer).stop();
    }

    @Test
    public void shouldReset() {

        // act
        this.underTest.reset();

        // assert
        verify(mockPrimedInvocations).clear();
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowIllegalStateExceptionWhenVerifyWithPrimedInvocations() {

        // arrange
        when(mockPrimedInvocations.isEmpty()).thenReturn(false);

        // act
        this.underTest.verify();

        // assert
        fail("Expected exception was not thrown");
    }

    @Test
    public void shouldVerifySuccessfully() {

        // arrange
        when(mockPrimedInvocations.isEmpty()).thenReturn(true);

        // act
        this.underTest.verify();

        // assert
        // no exception was thrown
    }

}
