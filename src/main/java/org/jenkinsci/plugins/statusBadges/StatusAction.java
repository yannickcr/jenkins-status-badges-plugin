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

public class StatusAction implements Action {
    private final StatusActionFactory factory;
    public final AbstractProject project;

    public StatusAction(StatusActionFactory factory, AbstractProject project) {
        this.factory = factory;
        this.project = project;
    }

    public String getIconFileName() {
        return Jenkins.RESOURCE_PATH + "/plugin/status-badges/images/24x24/shield.png";
    }

    public String getDisplayName() {
        return Messages.StatusAction_DisplayName();
    }

    public String getUrlName() {
        return "statusbadges";
    }
}
