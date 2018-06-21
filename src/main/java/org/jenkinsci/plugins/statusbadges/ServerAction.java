package org.jenkinsci.plugins.statusbadges;

import hudson.model.Action;
import hudson.model.Job;
import org.kohsuke.stapler.HttpResponse;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

import java.awt.*;
import java.io.IOException;

public class ServerAction
    implements Action
{
    private final ServerActionFactory factory;

    public final Job<?, ?> project;

    public ServerAction( ServerActionFactory factory, Job<?, ?> project )
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
        return "statusbadges-server";
    }

    public HttpResponse doIcon( StaplerRequest req, StaplerResponse rsp, @QueryParameter String style )
        throws IOException, FontFormatException
    {
        return factory.getServerImage( project.getIconColor(), style );
    }
}
