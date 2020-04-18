package ESharing.Client.Views;

import ESharing.Client.Views.MainAccountSetting.MainAccountSettingController;
import ESharing.Client.Views.MainAppView.MainAppViewController;
import ESharing.Client.Views.SignInView.SignInViewController;
import ESharing.Client.Views.SignUpView.SignUpViewController;
import ESharing.Client.Views.WelcomeView.WelcomeViewController;
import ESharing.Shared.TransferedObject.User;
import ESharing.Shared.Util.Views;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ViewControllerFactory {
    private static Map<Views, ViewController> viewControllers = new HashMap<>();

    public static ViewController getViewController(Views id)
    {
        ViewController viewController = viewControllers.get(id);
        if(viewController == null)
        {
            if(id == Views.WELCOME_VIEW) viewController = creatNewViewControllerObject("WelcomeView/WelcomeView.fxml");
            else if(id == Views.SIGN_IN_VIEW) viewController = creatNewViewControllerObject("SignInView/SignIn.fxml");
            else if(id == Views.SIGN_UP_VIEW) viewController = creatNewViewControllerObject("SignUpView/SignUp.fxml");
            else if(id == Views.MAIN_APP_VIEW) viewController = creatNewViewControllerObject("MainAppView/MainAppView.fxml");
            else if(id == Views.MAIN_USER_SETTING_VIEW) viewController = creatNewViewControllerObject("MainAccountSetting/MainAccountSettingView.fxml");
            viewControllers.put(id, viewController);
        }
        System.out.println(viewController);
        return viewController;
    }

    private static ViewController creatNewViewControllerObject(String fxmlPath)
    {
        ViewController controller = null;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ViewControllerFactory.class.getResource(fxmlPath));
            Region root = loader.load();
            controller = loader.getController();
            controller.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return controller;
    }
}
