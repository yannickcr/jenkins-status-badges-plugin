package org.jenkinsci.plugins.statusbadges;

import hudson.model.AbstractProject;
import jenkins.*;
import jenkins.model.*;
import hudson.*;
import hudson.model.*;
import hudson.plugins.checkstyle.CheckStyleResultAction;
import hudson.plugins.dry.DryResultAction;

import java.util.List;

public class GradeStatus extends GenericStatus {

    public double getGrade(AbstractProject<?, ?> project) {
        AbstractBuild<?, ?> lastBuild = project.getLastBuild();
        CheckStyleResultAction action;
        double grade = 0;
        double checkstyle = getCheckstyle(lastBuild);
        double duplicate = getDuplicate(lastBuild);
        grade = (checkstyle + duplicate) / 2;
        return grade;
    }

    public double getCheckstyle(AbstractBuild<?, ?> lastBuild) {
        CheckStyleResultAction action;
        double checkstyle = 4;
        try {
            action = lastBuild.getAction(hudson.plugins.checkstyle.CheckStyleResultAction.class);
        } catch(Exception e) {
            return checkstyle;
        }

        int unHealthy = Integer.parseInt(action.getHealthDescriptor().getUnHealthy());
        int healthy   = Integer.parseInt(action.getHealthDescriptor().getHealthy());
        int warnings  = action.getResult().getNumberOfWarnings();

        if (warnings >= unHealthy) {
            checkstyle = 0;
        } else if (warnings >= healthy) {
            checkstyle = 2;
        }

        return checkstyle;
    }

    public double getDuplicate(AbstractBuild<?, ?> lastBuild) {
        DryResultAction action;
        double duplicate = 4;
        try {
            action = lastBuild.getAction(hudson.plugins.dry.DryResultAction.class);
        } catch(Exception e) {
            return duplicate;
        }

        int unHealthy = Integer.parseInt(action.getHealthDescriptor().getUnHealthy());
        int healthy   = Integer.parseInt(action.getHealthDescriptor().getHealthy());
        int warnings  = action.getResult().getNumberOfWarnings();

        if (warnings >= unHealthy) {
            duplicate = 0;
        } else if (warnings >= healthy) {
            duplicate = 2;
        }

        return duplicate;
    }

}

