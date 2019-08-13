package site.clzblog.imitation.spring.mvc.controller;

import site.clzblog.imitation.spring.mvc.annotation.Controller;
import site.clzblog.imitation.spring.mvc.annotation.RequestMapping;

@Controller
public class IndexController {
    @RequestMapping("/index")
    public String index() {
        return "index";
    }
}
