package org.jenkinsci.plugins.statusbadges;

import hudson.model.AbstractProject;
import hudson.model.Action;
import hudson.util.HttpResponses;
import hudson.util.IOUtils;
import jenkins.model.Jenkins;
import org.kohsuke.stapler.HttpResponse;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import java.awt.FontFormatException;
import static javax.servlet.http.HttpServletResponse.SC_NOT_MODIFIED;

public class CoverageAction implements Action {
    private final CoverageActionFactory factory;
    private final CoverageStatus coverageStatus;
    private final String[] plugins = {"hudson.plugins.clover.CloverPublisher"};
    public final AbstractProject project;

    public CoverageAction(CoverageActionFactory factory, AbstractProject project) {
        this.factory = factory;
        this.project = project;
        coverageStatus = new CoverageStatus();
    }

    public String getIconFileName() {
        return null;
    }

    public String getDisplayName() {
        return null;
    }

    public String getUrlName() {
        return "statusbadges-coverage";
    }

    public HttpResponse doIcon(StaplerRequest req, StaplerResponse rsp, @QueryParameter String style) throws IOException, ParserConfigurationException, ServletException, InterruptedException, SAXException, FontFormatException {
        int coverage = coverageStatus.getCoverage(project);
        return factory.getCoverageImage(coverage, style);
    }
}
