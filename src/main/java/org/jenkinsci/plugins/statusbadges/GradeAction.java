package org.jenkinsci.plugins.statusbadges;

import hudson.model.Action;
import hudson.model.Job;
import org.kohsuke.stapler.HttpResponse;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

import java.awt.*;
import java.io.IOException;

public class GradeAction
    implements Action
{
    private final GradeActionFactory factory;

    private final GradeStatus gradeStatus;

    public final Job<?, ?> project;

    public GradeAction( GradeActionFactory factory, Job<?, ?> project )
    {
        this.factory = factory;
        this.project = project;
        gradeStatus = new GradeStatus();
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
        return "statusbadges-grade";
    }

    public HttpResponse doIcon( StaplerRequest req, StaplerResponse rsp, @QueryParameter String style )
        throws IOException, FontFormatException
    {
        double grade = gradeStatus.getGrade( project );
        return factory.getGradeImage( grade, style );
    }
}
