package ESharing.Shared.Util;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.scene.control.ProgressBar;
import javafx.util.Duration;

/**
 * A class with all static method which are used in the system
 * @version 1.0
 * @author Group1
 */
public class GeneralFunctions {


    /**
     * Fades in and fades out the given JavaFX in the given time
     * @param fadeType the type of animation
     * @param node the JavaFX node which will be animated
     * @param durationInMilliseconds the time of the animation
     */
    public static void fadeNode(String fadeType, Node node,int durationInMilliseconds)
    {
        FadeTransition fade = new FadeTransition(Duration.millis(durationInMilliseconds), node);
        if(fadeType.equalsIgnoreCase("fadeIn"))
        {
            fade.setFromValue(0.1);
            fade.setToValue(10);
        }
        else if(fadeType.equalsIgnoreCase("fadeOut"))
        {
            fade.setFromValue(10);
            fade.setToValue(0.1);
        }
        fade.play();
    }

    /**
     * Checking given javafx node and fills the JavFx progressbar
     * @param progressBar the JavaFX progressBar
     * @param node the JavaFx node which is checked
     * @param toAddOrRemove the amount progressbar of increasing or decreasing
     */
    public static void setFormProgressBar(ProgressBar progressBar, Node node, double toAddOrRemove)
    {
        if(node instanceof JFXPasswordField)
        {
            if(((JFXPasswordField) node).getText() != null && !((JFXPasswordField) node).getText().equals(""))
                changeFromProgress(progressBar, toAddOrRemove, "add");
            else
                changeFromProgress(progressBar, toAddOrRemove, "remove");
        }
        if(node instanceof JFXTextField)
        {
            if(((JFXTextField) node).getText() != null && !((JFXTextField) node).getText().equals(""))
                changeFromProgress(progressBar, toAddOrRemove, "add");
            else
                changeFromProgress(progressBar, toAddOrRemove, "remove");
        }
    }

    /**
     * Sets value of the given JavaFx progressbar
     * @param progressBar the given JavaFx progressbar
     * @param toAddOrRemove the amount progressbar of increasing or decreasing
     * @param type the type of action
     */
    private static void changeFromProgress(ProgressBar progressBar, double toAddOrRemove, String type)
    {
        double progress = progressBar.getProgress();
        if (type.equalsIgnoreCase("remove")) {

            if(progress != 0)
            {
                progress -= toAddOrRemove;
                progressBar.setProgress(progress);
            }
        }
        else
        {
            if(progress != 1)
            {
                progress += toAddOrRemove;
                progressBar.setProgress(progress);
            }
        }
    }


}
