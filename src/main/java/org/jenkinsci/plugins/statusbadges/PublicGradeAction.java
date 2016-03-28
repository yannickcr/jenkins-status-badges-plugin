package org.jenkinsci.plugins.statusbadges;

import hudson.Extension;
import hudson.model.UnprotectedRootAction;
import hudson.model.AbstractProject;
import java.io.IOException;

import org.kohsuke.stapler.HttpResponse;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

import java.awt.FontFormatException;

/**
 * Exposes the grade status badge via unprotected URL. http://localhost:8080/statusbadges-grade/icon?job=[JOBNAME]
 */
@Extension
public class PublicGradeAction
    implements UnprotectedRootAction
{
    private final ImageResolver iconResolver;

    private final GradeStatus gradeStatus;

    public PublicGradeAction()
    {
        iconResolver = new ImageResolver();
        gradeStatus = new GradeStatus();
    }

    @Override
    public String getUrlName()
    {
        return "statusbadges-grade";
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
        AbstractProject<?, ?> project = gradeStatus.getProject( job, req, rsp );
        double grade = gradeStatus.getGrade( project );
        return iconResolver.getGradeImage( grade, style );
    }

}
