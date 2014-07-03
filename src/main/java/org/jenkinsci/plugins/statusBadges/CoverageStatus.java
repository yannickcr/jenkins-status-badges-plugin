package org.jenkinsci.plugins.statusbadges;

import hudson.model.AbstractProject;
import jenkins.*;
import jenkins.model.*;
import hudson.*;
import hudson.model.*;

import java.io.IOException;
import java.io.File;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import org.apache.commons.lang.StringUtils;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class CoverageStatus extends Status {

    public int getCoverage(AbstractProject<?, ?> project, String plugin) throws IOException, ParserConfigurationException, SAXException, InterruptedException {
        AbstractBuild<?, ?> lastBuild = project.getLastBuild();
        String config = project.getConfigFile().toString();
        String workspace = lastBuild.getEnvironment().get("WORKSPACE");
        File configFile = new File(config);
        File workspaceDir = new File(workspace);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(configFile);

        NodeList pluginConfig = doc.getElementsByTagName(plugin).item(0).getChildNodes();

        String retportDir = "";
        String reportFileName = "";
        for (int j=0;j<pluginConfig.getLength(); j++) {
            if (pluginConfig.item(j).getNodeName() == "cloverReportDir") {
                retportDir = pluginConfig.item(j).getTextContent();
                continue;
            }
            if (pluginConfig.item(j).getNodeName() == "cloverReportFileName") {
                reportFileName = pluginConfig.item(j).getTextContent();
                continue;
            }
        }

        String[] fullPath = {workspaceDir.toString(), retportDir, reportFileName};
        String reportFullPath = StringUtils.join(fullPath, "/");

        File report = new File(reportFullPath);
        DocumentBuilderFactory reportDbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder reportDBuilder = reportDbFactory.newDocumentBuilder();
        Document reportDoc = reportDBuilder.parse(report);

        double elements = Integer.parseInt(reportDoc.getElementsByTagName("metrics").item(0).getAttributes().getNamedItem("elements").getNodeValue());
        double coveredelements = Integer.parseInt(reportDoc.getElementsByTagName("metrics").item(0).getAttributes().getNamedItem("coveredelements").getNodeValue());

        return (int) Math.floor((coveredelements / elements) * 100);
    }

}
