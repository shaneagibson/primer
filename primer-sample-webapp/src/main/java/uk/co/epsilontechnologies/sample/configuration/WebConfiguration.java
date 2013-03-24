package uk.co.epsilontechnologies.sample.configuration;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;

@EnableWebMvc
@Configuration
@ComponentScan(basePackages = {"uk.co.epsilontechnologies.sample.web"})
public class WebConfiguration {

    @Bean
    public ObjectMapper jacksonObjectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public MappingJacksonHttpMessageConverter mappingJacksonHttpMessageConverter(final ObjectMapper jacksonObjectMapper) {
        final MappingJacksonHttpMessageConverter mappingJacksonHttpMessageConverter = new MappingJacksonHttpMessageConverter();
        mappingJacksonHttpMessageConverter.setObjectMapper(jacksonObjectMapper);
        return mappingJacksonHttpMessageConverter;
    }

    @Bean
    public AnnotationMethodHandlerAdapter annotationMethodHandlerAdapter(final MappingJacksonHttpMessageConverter mappingJacksonHttpMessageConverter) {
        final AnnotationMethodHandlerAdapter annotationMethodHandlerAdapter = new AnnotationMethodHandlerAdapter();
        annotationMethodHandlerAdapter.setMessageConverters(new HttpMessageConverter[] { mappingJacksonHttpMessageConverter });
        return annotationMethodHandlerAdapter;
    }

}
