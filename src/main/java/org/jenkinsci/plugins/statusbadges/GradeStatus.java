package org.jenkinsci.plugins.statusbadges;

import hudson.model.AbstractProject;
import hudson.model.*;
import hudson.plugins.checkstyle.CheckStyleResultAction;
import hudson.plugins.dry.DryResultAction;

public class GradeStatus
    extends BuildStatus
{

    public double getGrade( AbstractProject<?, ?> project )
    {
        AbstractBuild<?, ?> lastBuild = project.getLastBuild();
        double grade = 0;
        double checkstyle = getCheckstyle( lastBuild );
        double duplicate = getDuplicate( lastBuild );
        grade = ( checkstyle + duplicate ) / 2;
        return grade;
    }

    public double getCheckstyle( AbstractBuild<?, ?> lastBuild )
    {
        CheckStyleResultAction action;
        double checkstyle = 4;
        action = lastBuild.getAction( hudson.plugins.checkstyle.CheckStyleResultAction.class );
        if ( action != null )
        {
            int unHealthy = Integer.parseInt( action.getHealthDescriptor().getUnHealthy() );
            int healthy = Integer.parseInt( action.getHealthDescriptor().getHealthy() );
            int warnings = action.getResult().getNumberOfWarnings();
            if ( warnings >= unHealthy )
            {
                checkstyle = 0;
            }
            else if ( warnings >= healthy )
            {
                checkstyle = 2;
            }
        }
        return checkstyle;
    }

    public double getDuplicate( AbstractBuild<?, ?> lastBuild )
    {
        DryResultAction action;
        double duplicate = 4;
        action = lastBuild.getAction( hudson.plugins.dry.DryResultAction.class );
        if ( action != null )
        {
            int unHealthy = Integer.parseInt( action.getHealthDescriptor().getUnHealthy() );
            int healthy = Integer.parseInt( action.getHealthDescriptor().getHealthy() );
            int warnings = action.getResult().getNumberOfWarnings();

            if ( warnings >= unHealthy )
            {
                duplicate = 0;
            }
            else if ( warnings >= healthy )
            {
                duplicate = 2;
            }
        }
        return duplicate;
    }

}
