package org.jenkinsci.plugins.statusbadges;

import hudson.model.AbstractProject;
import hudson.model.Action;
import jenkins.model.Jenkins;

public class StatusAction
    implements Action
{

    public final AbstractProject<?, ?> project;

    public StatusAction( StatusActionFactory factory, AbstractProject<?, ?> project )
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
