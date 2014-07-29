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
public class GradeActionFactory extends TransientProjectActionFactory {

    private final ImageResolver iconResolver;

    public GradeActionFactory() {
        iconResolver = new ImageResolver();
    }

    @Override
    public Collection<? extends Action> createFor(AbstractProject target) {
        return Collections.singleton(new GradeAction(this,target));
    }

    public StatusImage getGradeImage(double grade, String style) throws IOException, FontFormatException {
        return iconResolver.getGradeImage(grade, style);
    }

}
