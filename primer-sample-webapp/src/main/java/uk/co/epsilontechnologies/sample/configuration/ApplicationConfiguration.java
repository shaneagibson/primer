package uk.co.epsilontechnologies.sample.configuration;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.client.RestTemplate;

@Configuration
@ComponentScan(basePackages = {
        "uk.co.epsilontechnologies.sample.model",
        "uk.co.epsilontechnologies.sample.service",
        "uk.co.epsilontechnologies.sample.jms",
        "uk.co.epsilontechnologies.sample.gateway" })
@Import(value = { JmsConfiguration.class })
public class ApplicationConfiguration {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public PropertyPlaceholderConfigurer propertyPlaceholderConfigurer() {
        final PropertyPlaceholderConfigurer propertyPlaceholderConfigurer = new PropertyPlaceholderConfigurer();
        propertyPlaceholderConfigurer.setLocation(new ClassPathResource("configuration.properties"));
        return propertyPlaceholderConfigurer;
    }

}