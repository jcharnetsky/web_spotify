package webspotify;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 *
 * @author Cardinals
 */
@EnableJpaRepositories(basePackages = "webspotify.repo")
@Configuration
@SpringBootApplication
public class SvrInitialize {
  public static void main(String... arguments) {
    SpringApplication.run(SvrInitialize.class, arguments);
  }
}
