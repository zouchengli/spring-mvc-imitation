package site.clzblog.imitation.spring.mvc.web;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.annotation.HandlesTypes;
import java.lang.reflect.Method;
import java.util.Set;

@HandlesTypes(WebApplicationInitializer.class)
public class SpringServletContainerInitializer implements ServletContainerInitializer {
    @Override
    public void onStartup(Set<Class<?>> set, ServletContext servletContext) {
        set.forEach(c -> {
            try {
                Method onStartup = c.getMethod("onStartup", ServletContext.class);
                onStartup.invoke(c.newInstance(), servletContext);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
