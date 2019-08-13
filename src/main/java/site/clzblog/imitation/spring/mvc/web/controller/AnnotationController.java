package site.clzblog.imitation.spring.mvc.web.controller;

public class AnnotationController implements Controller {
    @Override
    public void handler() {
        System.out.println("Annotation");
    }
}
