package uk.co.epsilontechnologies.primer.rest.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"uk.co.epsilontechnologies.primer.rest.engine"})
public class ApplicationConfiguration {

}