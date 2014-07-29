package org.jenkinsci.plugins.statusbadges;

import hudson.model.AbstractProject;
import hudson.model.Action;
import hudson.util.HttpResponses;
import hudson.util.IOUtils;
import jenkins.model.Jenkins;
import org.kohsuke.stapler.HttpResponse;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import java.awt.FontFormatException;
import static javax.servlet.http.HttpServletResponse.SC_NOT_MODIFIED;

public class GradeAction implements Action {
    private final GradeActionFactory factory;
    private final GradeStatus gradeStatus;
    public final AbstractProject project;

    public GradeAction(GradeActionFactory factory, AbstractProject project) {
        this.factory = factory;
        this.project = project;
        gradeStatus = new GradeStatus();
    }

    public String getIconFileName() {
        return null;
    }

    public String getDisplayName() {
        return null;
    }

    public String getUrlName() {
        return "statusbadges-grade";
    }

    public HttpResponse doIcon(StaplerRequest req, StaplerResponse rsp, @QueryParameter String style) throws IOException, ParserConfigurationException, ServletException, InterruptedException, SAXException, FontFormatException {
        double grade = gradeStatus.getGrade(project);
        return factory.getGradeImage(grade, style);
    }
}
