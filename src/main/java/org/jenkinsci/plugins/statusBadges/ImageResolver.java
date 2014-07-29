package org.jenkinsci.plugins.statusbadges;

import hudson.model.BallColor;
import java.io.IOException;
import java.awt.FontFormatException;

public class ImageResolver {

    public StatusImage getBuildImage(BallColor ballColor, String style) throws IOException, FontFormatException {
        String subject = "build";
        String status = "unknown";
        String color = "lightgrey";

        if (ballColor.isAnimated()) {
            status = "running";
            color = "blue";
        } else {
            switch (ballColor) {
                case RED:
                case ABORTED:
                    status = "failing";
                    color = "red";
                    break;
                case YELLOW:
                    status = "unstable";
                    color = "yellow";
                    break;
                case BLUE:
                    status = "passing";
                    color = "brightgreen";
                    break;
            }
        }

        return new StatusImage(subject, status, color, style);
    }

    public StatusImage getGradeImage(double grade, String style) throws IOException, FontFormatException {
        String subject = "grade";
        String status = String.valueOf(grade);
        String color = "lightgrey";

        if (grade > 3.5) {
            color = "brightgreen";
        } else if (grade > 3) {
            color = "green";
        } else if (grade > 2.5) {
            color = "yellowgreen";
        } else if (grade > 2) {
            color = "yellow";
        } else if (grade > 1) {
            color = "orange";
        } else if (grade >= 0) {
            color = "red";
        } else {
            status = "unknown";
        }

        return new StatusImage(subject, status, color, style);
    }

    public StatusImage getCoverageImage(int coverage, String style) throws IOException, FontFormatException {
        String subject = "coverage";
        String status = String.valueOf(coverage) + "%";
        String color = "lightgrey";

        if (coverage > 90) {
            color = "brightgreen";
        } else if (coverage > 80) {
            color = "green";
        } else if (coverage > 70) {
            color = "yellowgreen";
        } else if (coverage > 60) {
            color = "yellow";
        } else if (coverage > 50) {
            color = "orange";
        } else if (coverage >= 0) {
            color = "red";
        } else {
            status = "unknown";
        }

        return new StatusImage(subject, status, color, style);
    }

}
