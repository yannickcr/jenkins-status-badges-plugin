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

public class CheckstyleStatus extends Status {

    public String[] getReportFiles(AbstractProject<?, ?> project, String[] plugins) throws IOException, ParserConfigurationException, SAXException, InterruptedException {
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

            String pattern = "";
            for (int j=0;j<pluginConfig.getLength(); j++) {
                if (pluginConfig.item(j).getNodeName() == "pattern") {
                    pattern = pluginConfig.item(j).getTextContent();
                    break;
                }
            }

            String[] files = hudson.Util.createFileSet(workspaceDir, pattern).toString().split(";");
            for (int i = 0; i < files.length; i++) {
                String[] fullPath = {workspaceDir.toString(), files[i]};
                reportFiles.add(StringUtils.join(fullPath, "/"));
            }

        }

        String[] output = new String[ reportFiles.size() ];
        reportFiles.toArray(output);
        return output;
    }

    public int searchForError(String path) throws IOException, ParserConfigurationException, SAXException {
        File file = new File(path);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(file);
        return doc.getElementsByTagName("error").getLength();
    }

    public int searchForErrors(String[] files) throws IOException, ParserConfigurationException, SAXException {
        int errors = 0;
        if (files.length == 0) {
            return -1;
        }
        for (int i = 0; i < files.length; i++) {
            errors += searchForError(files[i]);
        }
        return errors;
    }

}
