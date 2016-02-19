package org.jenkinsci.plugins.statusbadges;

import hudson.Extension;
import hudson.model.AbstractProject;
import hudson.model.Action;
import hudson.model.TransientProjectActionFactory;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

import java.awt.FontFormatException;

@Extension
public class CoverageActionFactory
    extends TransientProjectActionFactory
{

    private final ImageResolver iconResolver;

    public CoverageActionFactory()
    {
        iconResolver = new ImageResolver();
    }

    @Override
    public Collection<? extends Action> createFor( AbstractProject target )
    {
        return Collections.singleton( new CoverageAction( this, target ) );
    }

    public StatusImage getCoverageImage( int coverage, String style )
        throws IOException, FontFormatException
    {
        return iconResolver.getCoverageImage( coverage, style );
    }

}
