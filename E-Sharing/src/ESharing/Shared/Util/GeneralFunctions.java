package ESharing.Shared.Util;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.scene.control.ProgressBar;
import javafx.util.Duration;

public class GeneralFunctions {


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
