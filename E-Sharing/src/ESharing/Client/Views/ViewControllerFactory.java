package ESharing.Client.Views;

import ESharing.Shared.Util.Views;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Region;
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
            else if(id == Views.FAILED_CONNECTION_VIEW) viewController = creatNewViewControllerObject("FailedConnectionView/FailedConnectionView.fxml");
            else if(id == Views.USER_ADDRESS_SETTING_VIEW) viewController = creatNewViewControllerObject("UserAddressSettingView/UserAddressSettingView.fxml");
            else if(id == Views.USER_INFO_SETTING_VIEW) viewController = creatNewViewControllerObject("UserInfoSettingView/UserInfoSettingView.fxml");
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
