package org.jenkinsci.plugins.statusbadges;

import hudson.model.BallColor;

import java.io.IOException;
import java.awt.FontFormatException;
import java.util.Locale;

public class ImageResolver
{

    public StatusImage getServerImage( BallColor ballColor, String style)
        throws IOException, FontFormatException
    {
        String subject = "server";
        String status = "unknown";
        String color;

        if ( ballColor.isAnimated() )
        {
            status = "checking";
        }
        switch ( ballColor )
        {
            case RED:
            case ABORTED:
                status = "down";
                // fall through
            case RED_ANIME:
            case ABORTED_ANIME:
                color = "red";
                break;
            case YELLOW:
                status = "unstable";
                // fall through
            case YELLOW_ANIME:
                color = "yellow";
                break;
            case BLUE:
                status = "up";
                // fall through
            case BLUE_ANIME:
                color = "brightgreen";
                break;
            case DISABLED:
            case DISABLED_ANIME:
            case GREY:
            case GREY_ANIME:
            case NOTBUILT:
            case NOTBUILT_ANIME:
            default:
                color = "lightgrey";
                break;
        }

        return new StatusImage( subject, status, color, style );
    }

    public StatusImage getBuildImage( BallColor ballColor, String style )
        throws IOException, FontFormatException
    {
        String subject = "build";
        String status = "unknown";
        String color;

        if ( ballColor.isAnimated() )
        {
            status = "running";
        }
        switch ( ballColor )
        {
            case RED:
            case ABORTED:
                status = "failing";
                // fall through
            case RED_ANIME:
            case ABORTED_ANIME:
                color = "red";
                break;
            case YELLOW:
                status = "unstable";
                // fall through
            case YELLOW_ANIME:
                color = "yellow";
                break;
            case BLUE:
                status = "passing";
                // fall through
            case BLUE_ANIME:
                color = "brightgreen";
                break;
            case DISABLED:
            case DISABLED_ANIME:
            case GREY:
            case GREY_ANIME:
            case NOTBUILT:
            case NOTBUILT_ANIME:
            default:
                color = "lightgrey";
                break;
        }

        return new StatusImage( subject, status, color, style );
    }

    public StatusImage getGradeImage( double grade, String style )
        throws IOException, FontFormatException
    {
        String subject = "grade";
        String status = String.format( Locale.US, "%.1f", grade );
        String color = "lightgrey";

        if ( grade > 3.5 )
        {
            color = "brightgreen";
        }
        else if ( grade > 3 )
        {
            color = "green";
        }
        else if ( grade > 2.5 )
        {
            color = "yellowgreen";
        }
        else if ( grade > 2 )
        {
            color = "yellow";
        }
        else if ( grade > 1 )
        {
            color = "orange";
        }
        else if ( grade >= 0 )
        {
            color = "red";
        }
        else
        {
            status = "unknown";
        }

        return new StatusImage( subject, status, color, style );
    }

    public StatusImage getCoverageImage( int coverage, String style )
        throws IOException, FontFormatException
    {
        String subject = "coverage";
        String status = String.valueOf( coverage ) + "%";
        String color = "lightgrey";

        if ( coverage > 90 )
        {
            color = "brightgreen";
        }
        else if ( coverage > 80 )
        {
            color = "green";
        }
        else if ( coverage > 70 )
        {
            color = "yellowgreen";
        }
        else if ( coverage > 60 )
        {
            color = "yellow";
        }
        else if ( coverage > 50 )
        {
            color = "orange";
        }
        else if ( coverage >= 0 )
        {
            color = "red";
        }
        else
        {
            status = "unknown";
        }

        return new StatusImage( subject, status, color, style );
    }

}
