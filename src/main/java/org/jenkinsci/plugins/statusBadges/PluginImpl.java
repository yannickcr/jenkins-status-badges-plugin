package org.jenkinsci.plugins.statusbadges;

import hudson.Plugin;

public class PluginImpl extends Plugin {

    @Override
    public void start() throws Exception {
        PublicBuildAction.VIEW_STATUS.toString();
        PublicCheckstyleAction.VIEW_STATUS.toString();
    }

}
