package org.jenkinsci.plugins.statusBadges;

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
 * Exposes the checkstyle status badge via unprotected URL.
 *
 * http://localhost:8080/status-badges/checkstyle/icon?job=[JOBNAME]
 */
@Extension
public class PublicCheckstyleAction implements UnprotectedRootAction {
    private final ImageResolver iconResolver;
    private final CheckStyleStatus checkStyleStatus;
    public static Permission VIEW_STATUS;

    public PublicCheckstyleAction() throws IOException {
        iconResolver = new ImageResolver();
        checkStyleStatus = new CheckStyleStatus();
        VIEW_STATUS = checkStyleStatus.VIEW_STATUS;
    }

    public String getUrlName() {
        return "status-badges/checkstyle";
    }

    public String getIconFileName() {
        return null;
    }

    public String getDisplayName() {
        return null;
    }

    public HttpResponse doIcon(StaplerRequest req, StaplerResponse rsp, @QueryParameter String job) throws IOException, ParserConfigurationException, ServletException, InterruptedException, SAXException {
        AbstractProject<?, ?> project = checkStyleStatus.getProject(job, req, rsp);
        String[] files = checkStyleStatus.getReportFiles(project, "hudson.plugins.checkstyle.CheckStylePublisher");
        int errors = checkStyleStatus.searchForErrors(files);
        return iconResolver.getCheckstyleImage(errors);
    }

}
