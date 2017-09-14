package org.jenkinsci.plugins.statusbadges;

import hudson.Extension;
import hudson.model.Action;
import hudson.model.Job;
import jenkins.model.TransientActionFactory;

import java.awt.*;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

@Extension
public class CoverageActionFactory
    extends TransientActionFactory<Job>
{

    private final ImageResolver iconResolver;

    public CoverageActionFactory()
    {
        iconResolver = new ImageResolver();
    }

    @Override
    public Class<Job> type() {
        return Job.class;
    }

    @Override
    public Collection<? extends Action> createFor( Job target )
    {
        return Collections.singleton( new CoverageAction( this, target ) );
    }

    public StatusImage getCoverageImage( int coverage, String style )
        throws IOException, FontFormatException
    {
        return iconResolver.getCoverageImage( coverage, style );
    }

}
