package org.jenkinsci.plugins.statusbadges;

import hudson.model.Job;
import hudson.model.Run;
import hudson.plugins.clover.CloverBuildAction;
import hudson.plugins.cobertura.CoberturaBuildAction;
import hudson.plugins.cobertura.targets.CoverageMetric;

public class CoverageStatus
    extends BuildStatus
{

    public int getCoverage( Job<?, ?> project )
    {
        Run<?, ?> lastBuild = project.getLastBuild();
        CloverBuildAction cloverAction;
        CoberturaBuildAction coberturaAction;
        int percentage;
        try
        {
            cloverAction = lastBuild.getAction( hudson.plugins.clover.CloverBuildAction.class );
            percentage = cloverAction.getResult().getElementCoverage().getPercentage();
        }
        catch ( Exception cloverE )
        {
            try
            {
                coberturaAction = lastBuild.getAction( hudson.plugins.cobertura.CoberturaBuildAction.class );
                percentage = coberturaAction.getResult().getCoverage( CoverageMetric.LINE ).getPercentage();
            }
            catch ( Exception coberturaE )
            {
                return -1;
            }
        }
        return percentage;
    }

}
