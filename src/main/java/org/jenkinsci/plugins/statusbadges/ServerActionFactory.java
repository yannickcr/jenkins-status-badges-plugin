package org.jenkinsci.plugins.statusbadges;

import hudson.Extension;
import hudson.model.Action;
import hudson.model.BallColor;
import hudson.model.Job;
import jenkins.model.TransientActionFactory;

import java.awt.*;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

@Extension
public class ServerActionFactory extends TransientActionFactory<Job> {

    private final ImageResolver iconResolver;

    public ServerActionFactory() {
        iconResolver = new ImageResolver();
    }

    @Override
    public Class<Job> type() {
        return Job.class;
    }

    @Override
    public Collection<? extends Action> createFor( Job target ) {
        return Collections.singleton( new ServerAction( this, target ) );
    }

    public StatusImage getServerImage( BallColor ballColor, String style ) throws IOException, FontFormatException {
        return iconResolver.getServerImage( ballColor, style );
    }

}
