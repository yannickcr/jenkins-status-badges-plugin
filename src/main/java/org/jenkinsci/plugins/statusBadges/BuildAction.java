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
import static javax.servlet.http.HttpServletResponse.SC_NOT_MODIFIED;

public class BuildAction implements Action {
    private final BuildActionFactory factory;
    public final AbstractProject project;

    public BuildAction(BuildActionFactory factory, AbstractProject project) {
        this.factory = factory;
        this.project = project;
    }

    public String getIconFileName() {
        return Jenkins.RESOURCE_PATH + "/plugin/status-badges/images/24x24/shield.png";
    }

    public String getDisplayName() {
        return Messages.BuildAction_DisplayName();
    }

    public String getUrlName() {
        return "statusbadges-build";
    }

    public HttpResponse doIcon(StaplerRequest req, StaplerResponse rsp, @QueryParameter String style) throws IOException {
        return factory.getBuildImage(project.getIconColor(), style);
    }
}
