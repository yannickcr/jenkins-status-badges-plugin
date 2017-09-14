package org.jenkinsci.plugins.statusbadges;

import hudson.model.Action;
import hudson.model.Job;
import org.kohsuke.stapler.HttpResponse;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

import java.awt.*;
import java.io.IOException;

public class CoverageAction
    implements Action
{
    private final CoverageActionFactory factory;

    private final CoverageStatus coverageStatus;

    public final Job<?, ?> project;

    public CoverageAction( CoverageActionFactory factory, Job<?, ?> project )
    {
        this.factory = factory;
        this.project = project;
        coverageStatus = new CoverageStatus();
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

    @Override
    public String getUrlName()
    {
        return "statusbadges-coverage";
    }

    public HttpResponse doIcon( StaplerRequest req, StaplerResponse rsp, @QueryParameter String style )
        throws IOException, FontFormatException
    {
        int coverage = coverageStatus.getCoverage( project );
        return factory.getCoverageImage( coverage, style );
    }
}
