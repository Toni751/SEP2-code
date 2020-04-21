package ESharing.Client.Views;

import ESharing.Shared.TransferedObject.User;
import javafx.scene.Parent;
import javafx.scene.layout.Region;

/**
 * The abstract class used to store all common functions for all controllers
 * @version 1.0
 * @author Group1
 */
public abstract class ViewController{
    private Region root;


    /**
     * Initializes controller with all components
     */
    public void init(){}

    /**
     * Initializes controller with all components
     * @param loggedUser the User object which is current logged in the system
     */
    public void init(User loggedUser){}

    /**
     * Sets the root of the view
     * @param root the given root which is loaded from the fxml file
     */
    public void setRoot(Region root) {
        this.root = root;
    }

    /**
     * Returns a root of the view
     * @return the root of the view
     */
    public Parent getRoot() {
        return root;
    }
}
