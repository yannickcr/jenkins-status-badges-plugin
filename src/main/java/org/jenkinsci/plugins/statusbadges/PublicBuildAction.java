package org.jenkinsci.plugins.statusbadges;

import hudson.Extension;
import hudson.model.Job;
import hudson.model.UnprotectedRootAction;
import hudson.model.AbstractProject;
import java.io.IOException;

import org.kohsuke.stapler.HttpResponse;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

import java.awt.FontFormatException;

/**
 * Exposes the build status badge via unprotected URL. http://localhost:8080/statusbadges-build/icon?job=[JOBNAME]
 */
@Extension
public class PublicBuildAction
    implements UnprotectedRootAction
{
    private final ImageResolver iconResolver;

    private final BuildStatus buildStatus;

    public PublicBuildAction()
    {
        iconResolver = new ImageResolver();
        buildStatus = new BuildStatus();
    }

    @Override
    public String getUrlName()
    {
        return "statusbadges-build";
    }

    @Override
    public String getIconFileName()
    {
        return null;
    }

    @Override
    public String getDisplayName()
    {
        return null;
    }

    public HttpResponse doIcon( StaplerRequest req, StaplerResponse rsp, @QueryParameter String job,
                                @QueryParameter String style )
        throws IOException, FontFormatException
    {
        Job<?, ?> project = buildStatus.getProject( job, req, rsp );
        return iconResolver.getBuildImage( project.getIconColor(), style );
    }

}
