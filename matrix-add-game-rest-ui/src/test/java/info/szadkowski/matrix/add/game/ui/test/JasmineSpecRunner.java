package info.szadkowski.matrix.add.game.ui.test;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@Controller
public class JasmineSpecRunner {
  @RequestMapping("/")
  public String home() {
    return "forward:/test.html";
  }

  public static void main(String[] args) {
    new SpringApplicationBuilder(JasmineSpecRunner.class).properties("server.port=9999").run(args);
  }
}
