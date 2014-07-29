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

public class CheckstyleAction implements Action {
    private final CheckstyleActionFactory factory;
    private final CheckstyleStatus checkstyleStatus;
    private final String[] plugins = {"hudson.plugins.checkstyle.CheckStylePublisher"};
    public final AbstractProject project;

    public CheckstyleAction(CheckstyleActionFactory factory, AbstractProject project) {
        this.factory = factory;
        this.project = project;
        checkstyleStatus = new CheckstyleStatus();
    }

    public String getIconFileName() {
        return null;
    }

    public String getDisplayName() {
        return null;
    }

    public String getUrlName() {
        return "statusbadges-checkstyle";
    }

    public HttpResponse doIcon(StaplerRequest req, StaplerResponse rsp, @QueryParameter String style) throws IOException, ParserConfigurationException, ServletException, InterruptedException, SAXException, FontFormatException {
        int errors = checkstyleStatus.getCheckstyle(project);
        return factory.getCheckstyleImage(errors, style);
    }
}
