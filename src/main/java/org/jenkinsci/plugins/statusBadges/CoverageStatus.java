package org.jenkinsci.plugins.statusbadges;

import hudson.model.AbstractProject;
import jenkins.*;
import jenkins.model.*;
import hudson.*;
import hudson.model.*;
import hudson.plugins.clover.CloverBuildAction;
import hudson.plugins.cobertura.CoberturaBuildAction;
import hudson.plugins.cobertura.targets.CoverageMetric;

import java.util.List;

public class CoverageStatus extends GenericStatus {

    public int getCoverage(AbstractProject<?, ?> project) {
        AbstractBuild<?, ?> lastBuild = project.getLastBuild();
        CloverBuildAction cloverAction;
        CoberturaBuildAction coberturaAction;
        int percentage;
        try {
            cloverAction = lastBuild.getAction(hudson.plugins.clover.CloverBuildAction.class);
            percentage = cloverAction.getResult().getElementCoverage().getPercentage();
        } catch(Exception cloverE) {
            try {
                coberturaAction = lastBuild.getAction(hudson.plugins.cobertura.CoberturaBuildAction.class);
                percentage = coberturaAction.getResult().getCoverage(CoverageMetric.LINE).getPercentage();
            } catch(Exception coberturaE) {
                return -1;
            }
        }
        return percentage;
    }

}
