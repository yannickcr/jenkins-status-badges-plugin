package org.jenkinsci.plugins.statusbadges;

import hudson.model.AbstractProject;
import jenkins.*;
import jenkins.model.*;
import hudson.*;
import hudson.model.*;
import hudson.plugins.checkstyle.CheckStyleResultAction;

import java.util.List;

public class CheckstyleStatus extends GenericStatus {

    public int getCheckstyle(AbstractProject<?, ?> project) {
        AbstractBuild<?, ?> lastBuild = project.getLastBuild();
        CheckStyleResultAction action;
        try {
            action = lastBuild.getAction(hudson.plugins.checkstyle.CheckStyleResultAction.class);
        } catch(Exception e) {
            return -1;
        }
        return action.getResult().getNumberOfWarnings();
    }

}
