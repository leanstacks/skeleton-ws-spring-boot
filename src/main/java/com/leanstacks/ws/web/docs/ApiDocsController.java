package com.leanstacks.ws.web.docs;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * The ApiDocsController is a Spring MVC web controller class which serves the
 * Swagger user interface HTML page.
 * 
 * @author Matt Warman
 */
@Profile("docs")
@Controller
public class ApiDocsController {

    /**
     * Request handler to serve the Swagger user interface HTML page configured
     * to the mapped context path.
     * 
     * @return A String name of the Swagger user interface HTML page name.
     */
    @RequestMapping("/docs")
    public String getSwaggerApiDocsPage() {
        return "redirect:swagger-ui.html";
    }

}
