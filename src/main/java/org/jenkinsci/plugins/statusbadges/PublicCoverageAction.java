package org.jenkinsci.plugins.statusbadges;

import hudson.Extension;
import hudson.model.Job;
import hudson.model.UnprotectedRootAction;
import org.kohsuke.stapler.HttpResponse;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

import java.awt.*;
import java.io.IOException;

/**
 * Exposes the coverage status badge via unprotected URL. http://localhost:8080/statusbadges-coverage/icon?job=[JOBNAME]
 */
@Extension
public class PublicCoverageAction
    implements UnprotectedRootAction
{
    private final ImageResolver iconResolver;

    private final CoverageStatus coverageStatus;

    public PublicCoverageAction()
    {
        iconResolver = new ImageResolver();
        coverageStatus = new CoverageStatus();
    }

    @Override
    public String getUrlName()
    {
        return "statusbadges-coverage";
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
        Job<?, ?> project = coverageStatus.getProject( job, req, rsp );
        int coverage = coverageStatus.getCoverage( project );
        return iconResolver.getCoverageImage( coverage, style );
    }

}
