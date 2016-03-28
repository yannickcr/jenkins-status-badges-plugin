package org.jenkinsci.plugins.statusbadges;

import hudson.model.AbstractProject;
import hudson.security.ACL;
import hudson.util.HttpResponses;

import jenkins.model.Jenkins;

import org.acegisecurity.context.SecurityContext;
import org.acegisecurity.context.SecurityContextHolder;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

public class BuildStatus
{

    public AbstractProject<?, ?> getProject( String job, StaplerRequest req, StaplerResponse rsp )
        throws HttpResponses.HttpResponseException
    {
        AbstractProject<?, ?> p;

        SecurityContext orig = ACL.impersonate( ACL.SYSTEM );
        try
        {
            p = Jenkins.getInstance().getItemByFullName( job, AbstractProject.class );
        }
        finally
        {
            SecurityContextHolder.setContext( orig );
        }

        if ( p == null )
        {
            throw org.kohsuke.stapler.HttpResponses.notFound();
        }

        return p;
    }

}
