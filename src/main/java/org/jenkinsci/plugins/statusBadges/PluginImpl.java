package org.jenkinsci.plugins.statusBadges;

import hudson.Plugin;

public class PluginImpl extends Plugin {

    @Override
    public void start() throws Exception {
        PublicBuildAction.VIEW_STATUS.toString();
        //PublicCheckstyleAction.VIEW_STATUS.toString();
    }

}
