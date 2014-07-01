package org.jenkinsci.plugins.statusbadges;

import hudson.util.IOUtils;
import jenkins.model.Jenkins;
import org.kohsuke.stapler.HttpResponse;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

import javax.servlet.ServletException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;
import java.util.HashMap;

import java.awt.geom.AffineTransform;
import java.awt.font.FontRenderContext;
import java.awt.Font;

import static javax.servlet.http.HttpServletResponse.*;

class StatusImage implements HttpResponse {
    private final String payload;
    private final String etag;
    private final String length;
    private final Map<String, String> colors = new HashMap<String, String>() {
        {
            put("red",         "#e05d44");
            put("brightgreen", "#44cc11");
            put("green",       "#97CA00");
            put("yellowgreen", "#a4a61d");
            put("yellow",      "#dfb317");
            put("orange",      "#fe7d37");
            put("lightgrey",   "#9f9f9f");
            put("blue",        "#007ec6");
        };
    };

    StatusImage(String subject, String status, String colorName) throws IOException {
        etag = Jenkins.RESOURCE_PATH + '/' + subject + status + colorName;

        URL image = new URL(Jenkins.getInstance().pluginManager.getPlugin("status-badges").baseResourceURL, "status/default.svg");
        InputStream s = image.openStream();

        double[] widths = {measureText(subject) + 10, measureText(status) + 10};

        String color = colors.get(colorName);

        if (color == null) {
            color = '#' + colorName;
        }

        try {
            payload = IOUtils.toString(s, "utf-8")
                .replace("{{fullwidth}}",  String.valueOf(widths[0] + widths[1]))
                .replace("{{fullwidth}}",  String.valueOf(widths[0]))
                .replace("{{blockPos}}",   String.valueOf(widths[0]))
                .replace("{{blockWidth}}", String.valueOf(widths[1]))
                .replace("{{subjectPos}}", String.valueOf((widths[0] / 2) + 1))
                .replace("{{statusPos}}",  String.valueOf(widths[0] + (widths[1] / 2) - 1))
                .replace("{{subject}}",    subject)
                .replace("{{status}}",     status)
                .replace("{{color}}",      color)
            ;
        } finally {
            IOUtils.closeQuietly(s);
        }

        length = Integer.toString(payload.length());
    }

    public double measureText(String text) {
        AffineTransform af = new AffineTransform();
        FontRenderContext fr = new FontRenderContext(af, true, true);
        Font f = new Font("Verdana, 'DejaVu Sans'", 0, 11);
        return f.getStringBounds(text, fr).getWidth();
    }

    public void generateResponse(StaplerRequest req, StaplerResponse rsp, Object node) throws IOException, ServletException {
        String v = req.getHeader("If-None-Match");
        if (etag.equals(v)) {
            rsp.setStatus(SC_NOT_MODIFIED);
            return;
        }

        rsp.setHeader("ETag", etag);
        rsp.setHeader("Expires","Fri, 01 Jan 1984 00:00:00 GMT");
        rsp.setHeader("Cache-Control", "no-cache, private");
        rsp.setHeader("Content-Type", "image/svg+xml;charset=utf-8");
        rsp.setHeader("Content-Length", length);
        rsp.getOutputStream().write(payload.getBytes());
    }
}
