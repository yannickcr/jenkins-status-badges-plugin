package org.jenkinsci.plugins.statusbadges;

import hudson.model.AbstractProject;
import jenkins.*;
import jenkins.model.*;
import hudson.*;
import hudson.model.*;
import hudson.plugins.clover.CloverBuildAction;

import java.util.List;

public class CoverageStatus extends Status {

    public int getCoverage(AbstractProject<?, ?> project) {
        AbstractBuild<?, ?> lastBuild = project.getLastBuild();
        CloverBuildAction action;
        try {
            action = lastBuild.getAction(hudson.plugins.clover.CloverBuildAction.class);
        } catch(Exception e) {
            return -1;
        }
        return action.getResult().getElementCoverage().getPercentage();
    }

}
