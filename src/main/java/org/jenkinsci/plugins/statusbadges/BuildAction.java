package org.jenkinsci.plugins.statusbadges;

import hudson.model.Action;
import hudson.model.Job;
import org.kohsuke.stapler.HttpResponse;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

import java.awt.*;
import java.io.IOException;

public class BuildAction
    implements Action
{
    private final BuildActionFactory factory;

    public final Job<?, ?> project;

    public BuildAction( BuildActionFactory factory, Job<?, ?> project )
    {
        this.factory = factory;
        this.project = project;
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
        return "statusbadges-build";
    }

    public HttpResponse doIcon( StaplerRequest req, StaplerResponse rsp, @QueryParameter String style )
        throws IOException, FontFormatException
    {
        return factory.getBuildImage( project.getIconColor(), style );
    }
}
