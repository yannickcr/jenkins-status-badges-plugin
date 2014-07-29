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

import java.awt.FontFormatException;

import javax.xml.parsers.ParserConfigurationException;

import jenkins.*;
import jenkins.model.*;
import hudson.*;
import hudson.model.*;

/**
 * Exposes the grade status badge via unprotected URL.
 *
 * http://localhost:8080/status-badges/grade/icon?job=[JOBNAME]
 */
@Extension
public class PublicGradeAction implements UnprotectedRootAction {
    private final ImageResolver iconResolver;
    private final GradeStatus gradeStatus;
    final public static Permission VIEW_STATUS = new Permission(Item.PERMISSIONS, "ViewStatus", Messages._ViewStatus_Permission(), Item.READ, PermissionScope.ITEM);

    public PublicGradeAction() throws IOException {
        iconResolver = new ImageResolver();
        gradeStatus = new GradeStatus();
    }

    public String getUrlName() {
        return "statusbadges-grade";
    }

    public String getIconFileName() {
        return null;
    }

    public String getDisplayName() {
        return null;
    }

    public HttpResponse doIcon(StaplerRequest req, StaplerResponse rsp, @QueryParameter String job, @QueryParameter String style) throws IOException, ParserConfigurationException, ServletException, InterruptedException, SAXException, FontFormatException {
        AbstractProject<?, ?> project = gradeStatus.getProject(job, req, rsp);
        double grade = gradeStatus.getGrade(project);
        return iconResolver.getGradeImage(grade, style);
    }

}
