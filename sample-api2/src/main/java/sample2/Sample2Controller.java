package sample2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@EnableAutoConfiguration
public class Sample2Controller {

	@RequestMapping("/")
	@ResponseBody
	String home() {
		return "sample2";
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(Sample2Controller.class, args);
	}
}
