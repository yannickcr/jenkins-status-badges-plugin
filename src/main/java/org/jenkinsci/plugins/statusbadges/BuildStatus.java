package org.jenkinsci.plugins.statusbadges;

import hudson.model.Job;
import hudson.security.ACL;
import hudson.util.HttpResponses;
import jenkins.model.Jenkins;
import org.acegisecurity.context.SecurityContext;
import org.acegisecurity.context.SecurityContextHolder;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

public class BuildStatus
{

    public Job<?, ?> getProject( String job, StaplerRequest req, StaplerResponse rsp )
        throws HttpResponses.HttpResponseException
    {
        Job<?, ?> p;

        SecurityContext orig = ACL.impersonate( ACL.SYSTEM );
        try
        {
            p = Jenkins.getInstance().getItemByFullName( job, Job.class );
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
