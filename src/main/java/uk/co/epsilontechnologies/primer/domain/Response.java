package uk.co.epsilontechnologies.primer.domain;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface Response {

    void populate(HttpServletResponse httpServletResponse) throws IOException;

}
