package ua.igorg.userfusion.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Home redirection to OpenAPI api documentation
 *
 * @Author igorg
 * @create 04.06.2024
 */
@Controller
public class HomeController {

	@RequestMapping("/")
	public String index() {
		return "redirect:swagger-ui.html";
	}

}