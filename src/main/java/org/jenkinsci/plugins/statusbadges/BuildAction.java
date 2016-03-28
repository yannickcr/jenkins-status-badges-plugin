package org.jenkinsci.plugins.statusbadges;

import hudson.model.AbstractProject;
import hudson.model.Action;
import org.kohsuke.stapler.HttpResponse;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;
import java.io.IOException;
import java.awt.FontFormatException;

public class BuildAction
    implements Action
{
    private final BuildActionFactory factory;

    public final AbstractProject<?, ?> project;

    public BuildAction( BuildActionFactory factory, AbstractProject<?, ?> project )
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
