package uk.co.epsilontechnologies.primer.server.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"uk.co.epsilontechnologies.primer.server.engine"})
public class ApplicationConfiguration {

}