import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by smoldyrev on 23.03.17.
 */
@Configuration
@ComponentScan(basePackages={"ru/innopolis/smoldyrev/"})
@ImportResource("../webapp/WEB-INF/dispatcher-servlet.xml")
public class TestConfiguration {
}
