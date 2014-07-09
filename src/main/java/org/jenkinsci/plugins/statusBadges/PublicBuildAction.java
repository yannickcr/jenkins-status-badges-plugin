package org.jenkinsci.plugins.statusbadges;

import hudson.Extension;
import hudson.model.Item;
import hudson.model.UnprotectedRootAction;
import hudson.model.AbstractProject;
import hudson.security.ACL;
import hudson.security.Permission;
import hudson.security.PermissionScope;
import hudson.util.HttpResponses;

import java.io.IOException;

import javax.servlet.ServletException;

import jenkins.model.Jenkins;

import org.acegisecurity.context.SecurityContext;
import org.acegisecurity.context.SecurityContextHolder;
import org.kohsuke.stapler.HttpResponse;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

/**
 * Exposes the build status badge via unprotected URL.
 *
 * http://localhost:8080/status-badges/build/icon?job=[JOBNAME]
 */
@Extension
public class PublicBuildAction implements UnprotectedRootAction {
    private final ImageResolver iconResolver;
    private final BuildStatus buildStatus;
    final public static Permission VIEW_STATUS = new Permission(Item.PERMISSIONS, "ViewStatus", Messages._ViewStatus_Permission(), Item.READ, PermissionScope.ITEM);

    public PublicBuildAction() throws IOException {
        iconResolver = new ImageResolver();
        buildStatus = new BuildStatus();
    }

    public String getUrlName() {
        return "statusbadges-build";
    }

    public String getIconFileName() {
        return null;
    }

    public String getDisplayName() {
        return null;
    }

    public HttpResponse doIcon(StaplerRequest req, StaplerResponse rsp, @QueryParameter String job, @QueryParameter String style) throws IOException, ServletException {
        AbstractProject<?, ?> project = buildStatus.getProject(job, req, rsp);
        return iconResolver.getBuildImage(project.getIconColor(), style);
    }

}
