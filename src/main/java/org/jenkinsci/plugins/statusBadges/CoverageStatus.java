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

import java.util.ArrayList;
import java.util.List;

public class CoverageStatus extends Status {

    public int getCoverage(AbstractProject<?, ?> project, String[] plugins) throws IOException, ParserConfigurationException, SAXException, InterruptedException {
        AbstractBuild<?, ?> lastBuild = project.getLastBuild();
        String config = project.getConfigFile().toString();
        String workspace = lastBuild.getEnvironment().get("WORKSPACE");
        File configFile = new File(config);
        File workspaceDir = new File(workspace);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(configFile);
        List<String> reportFiles = new ArrayList<String>();

        for(int k=0;k<plugins.length; k++) {

            NodeList pluginSections = doc.getElementsByTagName(plugins[k]);

            if (pluginSections.getLength() == 0) {
                continue;
            }

            NodeList pluginConfig = pluginSections.item(0).getChildNodes();

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
            reportFiles.add(StringUtils.join(fullPath, "/"));
        }

        String[] reportFilesArray = new String[ reportFiles.size() ];
        reportFiles.toArray(reportFilesArray);

        int coverage = 0;

        if (reportFilesArray.length == 0) {
            return -1;
        }

        for(int i=0; i<reportFilesArray.length; i++) {
            coverage += parseFile(reportFilesArray[i]);
        }

        return (int) Math.floor(coverage / reportFilesArray.length);
    }

    public int parseFile(String path) throws IOException, ParserConfigurationException, SAXException {
        File report = new File(path);
        DocumentBuilderFactory reportDbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder reportDBuilder = reportDbFactory.newDocumentBuilder();
        Document reportDoc = reportDBuilder.parse(report);

        double elements = Integer.parseInt(reportDoc.getElementsByTagName("metrics").item(0).getAttributes().getNamedItem("elements").getNodeValue());
        double coveredelements = Integer.parseInt(reportDoc.getElementsByTagName("metrics").item(0).getAttributes().getNamedItem("coveredelements").getNodeValue());

        return (int) Math.floor((coveredelements / elements) * 100);
    }

}
