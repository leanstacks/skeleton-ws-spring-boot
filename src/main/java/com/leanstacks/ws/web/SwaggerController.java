package com.leanstacks.ws.web;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Profile("docs")
@Controller
public class SwaggerController {

    @RequestMapping("/docs")
    public String getDocsPage() {
        return "swagger-ui.html";
    }

}
