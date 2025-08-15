package com.jobportal.job_portal;


import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.boot.builder.SpringApplicationBuilder;
import com.jobportal.job_portal.JobPortalApplication;

public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(JobPortalApplication.class);  // Reference the correct class here
    }

}
