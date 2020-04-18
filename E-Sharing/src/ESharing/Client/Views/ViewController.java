package ESharing.Client.Views;

import ESharing.Shared.TransferedObject.User;
import javafx.scene.Parent;
import javafx.scene.layout.Region;

public abstract class ViewController{

    private Region root;

    public void init(){}

    public void init(User loggedUser){}

    public void setRoot(Region root) {
        this.root = root;
    }

    public Parent getRoot() {
        return root;
    }
}
