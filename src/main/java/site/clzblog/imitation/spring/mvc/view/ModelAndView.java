package site.clzblog.imitation.spring.mvc.view;

public class ModelAndView {
    private String viewName;

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public ModelAndView(String viewName) {
        this.viewName = viewName;
    }
}
