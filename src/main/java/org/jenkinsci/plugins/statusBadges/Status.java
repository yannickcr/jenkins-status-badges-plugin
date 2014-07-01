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

public class Status {

    final public static Permission VIEW_STATUS = new Permission(Item.PERMISSIONS, "ViewStatus", Messages._ViewStatus_Permission(), Item.READ, PermissionScope.ITEM);

    public AbstractProject<?, ?> getProject(String job, StaplerRequest req, StaplerResponse rsp) throws IOException, HttpResponses.HttpResponseException {
        AbstractProject<?, ?> p;

        SecurityContext orig = ACL.impersonate(ACL.SYSTEM);
        try {
            p = Jenkins.getInstance().getItemByFullName(job, AbstractProject.class);
        } finally {
            SecurityContextHolder.setContext(orig);
        }

        if(p == null || !(p.hasPermission(VIEW_STATUS))){
            throw HttpResponses.notFound();
        }

        return p;
    }

}
