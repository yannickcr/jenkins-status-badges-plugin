package org.jenkinsci.plugins.statusbadges;

import hudson.model.Job;
import hudson.model.Run;
import hudson.plugins.clover.CloverBuildAction;
import hudson.plugins.cobertura.CoberturaBuildAction;
import hudson.plugins.cobertura.targets.CoverageMetric;
import hudson.plugins.jacoco.JacocoBuildAction;

public class CoverageStatus
    extends BuildStatus
{

    public int getCoverage( Job<?, ?> project )
    {
        Run<?, ?> lastBuild = project.getLastBuild();
        
        try
        {
            CloverBuildAction cloverAction = lastBuild.getAction(CloverBuildAction.class);
            return cloverAction.getResult().getElementCoverage().getPercentage();
        }
        catch ( Exception cloverE )
        {
            // ignore.  if all fail, this will fall through to -1
        }

        try
        {
            CoberturaBuildAction coberturaAction = lastBuild.getAction(CoberturaBuildAction.class);
            return coberturaAction.getResult().getCoverage( CoverageMetric.LINE ).getPercentage();
        }
        catch ( Exception coberturaE )
        {
            // ignore.  if all fail, this will fall through to -1
        }

        try
        {
            JacocoBuildAction jacocoAction = lastBuild.getAction(JacocoBuildAction.class);
            return jacocoAction.getResult().getLineCoverage().getPercentage();
        }
        catch (Exception jacocoE)
        {
            // ignore.  if all fail, this will fall through to -1
        }


        return -1;
    }

}
