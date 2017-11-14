package web_spotify.controllers;

import web_spotify.Utils.DBUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class ServletInitializer extends SpringBootServletInitializer {
  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(ServletInitializer.class);
  }

  public static void main(String[] args) throws Exception {
//    DBUtils.testReport();
    SpringApplication.run(ServletInitializer.class, args);
  }
}
