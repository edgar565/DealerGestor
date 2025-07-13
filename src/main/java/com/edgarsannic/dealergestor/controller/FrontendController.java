package com.edgarsannic.dealergestor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FrontendController {
    @RequestMapping(value = { "/", "/{x:^(?!api).*$}/**" })
    public String index() {
        return "forward:/index.html";
    }
}