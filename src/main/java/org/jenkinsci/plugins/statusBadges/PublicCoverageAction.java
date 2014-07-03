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

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;

import jenkins.*;
import jenkins.model.*;
import hudson.*;
import hudson.model.*;

/**
 * Exposes the coverage status badge via unprotected URL.
 *
 * http://localhost:8080/status-badges/coverage/icon?job=[JOBNAME]
 */
@Extension
public class PublicCoverageAction implements UnprotectedRootAction {
    private final ImageResolver iconResolver;
    private final CoverageStatus coverageStatus;
    final public static Permission VIEW_STATUS = new Permission(Item.PERMISSIONS, "ViewStatus", Messages._ViewStatus_Permission(), Item.READ, PermissionScope.ITEM);

    public PublicCoverageAction() throws IOException {
        iconResolver = new ImageResolver();
        coverageStatus = new CoverageStatus();
    }

    public String getUrlName() {
        return "statusbadges-coverage";
    }

    public String getIconFileName() {
        return null;
    }

    public String getDisplayName() {
        return null;
    }

    public HttpResponse doIcon(StaplerRequest req, StaplerResponse rsp, @QueryParameter String job, @QueryParameter String style) throws IOException, ParserConfigurationException, ServletException, InterruptedException, SAXException {
        AbstractProject<?, ?> project = coverageStatus.getProject(job, req, rsp);
        int coverage = coverageStatus.getCoverage(project, "hudson.plugins.clover.CloverPublisher");
        return iconResolver.getCoverageImage(coverage, style);
    }

}
