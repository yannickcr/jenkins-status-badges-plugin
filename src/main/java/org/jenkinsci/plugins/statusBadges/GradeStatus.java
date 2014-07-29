package org.jenkinsci.plugins.statusbadges;

import hudson.model.AbstractProject;
import jenkins.*;
import jenkins.model.*;
import hudson.*;
import hudson.model.*;
import hudson.plugins.checkstyle.CheckStyleResultAction;

import java.util.List;

public class GradeStatus extends GenericStatus {

    public double getGrade(AbstractProject<?, ?> project) {
        AbstractBuild<?, ?> lastBuild = project.getLastBuild();
        CheckStyleResultAction action;
        double grade = 0;
        double checkstyle = getCheckstyle(lastBuild);
        grade = checkstyle / 1;
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
        } else {
            checkstyle = 2;
        }

        return checkstyle;
    }

}

