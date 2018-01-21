package org.jenkinsci.plugins.statusbadges;

import hudson.model.Action;
import hudson.model.Job;
import jenkins.model.Jenkins;

public class StatusAction
    implements Action
{

    public final Job<?, ?> project;

    public StatusAction( StatusActionFactory factory, Job<?, ?> project )
    {
        this.project = project;
    }

    @Override
    public String getIconFileName()
    {
        return Jenkins.RESOURCE_PATH + "/plugin/status-badges/images/24x24/shield.png";
    }

    @Override
    public String getDisplayName()
    {
        return Messages.StatusAction_DisplayName();
    }

    @Override
    public String getUrlName()
    {
        return "statusbadges";
    }
}
