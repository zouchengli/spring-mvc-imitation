package site.clzblog.imitation.spring.mvc.web.controller;

public class HttpController implements Controller {
    @Override
    public void handler() {
        System.out.println("Http");
    }
}
