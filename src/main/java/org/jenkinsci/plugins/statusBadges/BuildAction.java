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

public class BuildAction implements Action {
    private final BuildActionFactory factory;
    private final BuildStatus buildStatus;
    public final AbstractProject project;

    public BuildAction(BuildActionFactory factory, AbstractProject project) {
        this.factory = factory;
        this.project = project;
        buildStatus = new BuildStatus();
    }

    public String getIconFileName() {
        return null;
    }

    public String getDisplayName() {
        return null;
    }

    public String getUrlName() {
        return "statusbadges-build";
    }

    public HttpResponse doIcon(StaplerRequest req, StaplerResponse rsp, @QueryParameter String style) throws IOException, ServletException, FontFormatException {
        return factory.getBuildImage(project.getIconColor(), style);
    }
}
