package pl.bromanowski.airportapplication.external.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import pl.bromanowski.airportapplication.external.converter.StringToWeightUnitConverter;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Override
    public void addFormatters(final FormatterRegistry registry) {
        registry.addConverter(new StringToWeightUnitConverter());
    }
}
