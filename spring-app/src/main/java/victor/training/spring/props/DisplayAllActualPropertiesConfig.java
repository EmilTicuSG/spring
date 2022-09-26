package victor.training.spring.props;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.Comparator;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

//@Configuration
@Slf4j
public class DisplayAllActualPropertiesConfig {
    @ConfigurationProperties
    @Bean
    public Properties allProps() {
        return new Properties();
    }

    @Bean
    public String test() {
        log.info("All props: \n" + allProps().entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey(Comparator.comparing(Object::toString)))
                .map(e -> e.getKey() + " = " + e.getValue())
                .collect(Collectors.joining("\n")));
        return "DONE";
    }
}