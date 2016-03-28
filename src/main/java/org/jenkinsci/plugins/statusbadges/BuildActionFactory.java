package org.jenkinsci.plugins.statusbadges;

import hudson.Extension;
import hudson.model.AbstractProject;
import hudson.model.Action;
import hudson.model.BallColor;
import hudson.model.TransientProjectActionFactory;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

import java.awt.FontFormatException;

@Extension
public class BuildActionFactory extends TransientProjectActionFactory {

    private final ImageResolver iconResolver;

    public BuildActionFactory() {
        iconResolver = new ImageResolver();
    }

    @Override
    public Collection<? extends Action> createFor(AbstractProject target) {
        return Collections.singleton(new BuildAction(this,target));
    }

    public StatusImage getBuildImage(BallColor ballColor, String style) throws IOException, FontFormatException {
        return iconResolver.getBuildImage(ballColor, style);
    }

}
