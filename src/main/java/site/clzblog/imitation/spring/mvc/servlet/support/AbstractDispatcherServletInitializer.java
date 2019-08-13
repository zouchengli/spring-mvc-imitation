package site.clzblog.imitation.spring.mvc.servlet.support;

import site.clzblog.imitation.spring.mvc.servlet.DispatcherServlet;
import site.clzblog.imitation.spring.mvc.web.WebApplicationInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

public class AbstractDispatcherServletInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) {
        ServletRegistration.Dynamic servlet = servletContext.addServlet("dispatcherServlet", new DispatcherServlet());
        servlet.addMapping("/");
        servlet.setLoadOnStartup(1);
    }
}
