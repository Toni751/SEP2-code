package ESharing.Client.Views.CreateAdView;

import ESharing.Client.Core.ViewModelFactory;
import ESharing.Client.Views.ViewController;
import ESharing.Shared.Util.AdImages;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.controlsfx.control.PopOver;
import java.io.File;
import java.time.LocalDate;

public class CreateAdViewController extends ViewController {

    @FXML private Label warningLabel;
    @FXML private Pane warningPane;
    @FXML private Pane mainPicturePane;
    @FXML private ImageView mainImage;
    @FXML private ImageView subImage2;
    @FXML private ImageView subImage1;
    @FXML private ImageView subImage3;
    @FXML private ImageView subImage4;
    @FXML private TextField titleTextField;
    @FXML private ComboBox<String> typeComboBox;
    @FXML private TextField priceTextField;
    @FXML private TextArea descriptionTextField;
    @FXML private DatePicker datePicker;
    @FXML private ScrollPane scrollPane;
    @FXML private Rectangle mainImageRectangle;
    @FXML private Rectangle subImage1Rectangle;
    @FXML private Rectangle subImage2Rectangle;
    @FXML private Rectangle subImage3Rectangle;
    @FXML private Rectangle subImage4Rectangle;

    private PopOver popOver;
    private EventHandler<MouseEvent> mouseClickedEventHandler;
    private CreateAdViewModel viewModel;

    public void init()
    {
        viewModel = ViewModelFactory.getViewModelFactory().getAdViewModel();

        titleTextField.textProperty().bindBidirectional(viewModel.getTitleProperty());
        priceTextField.textProperty().bindBidirectional(viewModel.getPriceProperty());
        descriptionTextField.textProperty().bindBidirectional(viewModel.getDescriptionProperty());
        warningLabel.textProperty().bind(viewModel.getWarningProperty());
        datePicker.valueProperty().bindBidirectional(viewModel.getDateProperty());
        typeComboBox.valueProperty().bindBidirectional(viewModel.getTypeValueProperty());
        warningPane.visibleProperty().bindBidirectional(viewModel.warningVisibleProperty());
        warningPane.styleProperty().bindBidirectional(viewModel.getWarningStyleProperty());
        mainPicturePane.visibleProperty().bindBidirectional(viewModel.getMainPicturePaneVisible());
        mainImageRectangle.fillProperty().bind(viewModel.getMainImageRectangleFill());
        subImage1Rectangle.fillProperty().bind(viewModel.getSubImage1RectangleFill());
        subImage2Rectangle.fillProperty().bind(viewModel.getSubImage2RectangleFill());
        subImage3Rectangle.fillProperty().bind(viewModel.getSubImage3RectangleFill());
        subImage4Rectangle.fillProperty().bind(viewModel.getSubImage4RectangleFill());

        typeComboBox.setItems(viewModel.getTypeItemsProperty());

        mainImage.imageProperty().bind(viewModel.getMainImageProperty());
        subImage1.imageProperty().bind(viewModel.getSubImage1Property());
        subImage2.imageProperty().bind(viewModel.getSubImage2Property());
        subImage3.imageProperty().bind(viewModel.getSubImage3Property());
        subImage4.imageProperty().bind(viewModel.getSubImage4Property());

        viewModel.setDefaultView();
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        initializeDatePicker();
        dataPickerEvent();

        addFocusEvent(titleTextField);
        addFocusEvent(typeComboBox);
        addFocusEvent(descriptionTextField);
        addFocusEvent(priceTextField);
        addFocusEvent(mainImage);
    }

    public void addImage(MouseEvent mouseEvent) {
        ImageView selectedImage = (ImageView) mouseEvent.getSource();
        if(selectedImage.getId().equals(mainImage.getId())) {
            selectAndLoadImage(AdImages.MAIN_IMAGE.toString());
        }
        else if(selectedImage.getId().equals(subImage1.getId())) {
            selectAndLoadImage(AdImages.SUB_IMAGE1.toString());
        }
        else if(selectedImage.getId().equals(subImage2.getId())) {
            selectAndLoadImage(AdImages.SUB_IMAGE2.toString());
        }
        else if(selectedImage.getId().equals(subImage3.getId())) {
            selectAndLoadImage(AdImages.SUB_IMAGE3.toString());
        }
        else if(selectedImage.getId().equals(subImage4.getId())) {
            selectAndLoadImage(AdImages.SUB_IMAGE4.toString());
        }
    }

    public void onAddAdvertisementAction() {
        viewModel.addAdvertisementRequest();
    }

    public void openCalendar() {
        datePicker.show();
    }

    public void clearForm(){
        viewModel.setDefaultView();
    }

    public void showImagePopup() {
        Label imageDescription = new Label("Images should be in .png or .jpg format");
        popOver = new PopOver(imageDescription);
        popOver.show(subImage3Rectangle);
    }

    public void hideImagePopup() {
        popOver.hide();
    }

    private void addFocusEvent(Node node){
        Label description = null;
        if(node == titleTextField)
            description = new Label("This is the most important part in your advertisement");
        else if(node == typeComboBox)
            description = new Label("Select type of your vehicle");
        else if(node == descriptionTextField)
            description = new Label("Describe your vehicle");
        else if(node == priceTextField)
            description = new Label("Select price per day");
        else if(node == mainImage)
            description= new Label("Select images of your vehicle");

        Label finalDescription = description;
        node.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if(!oldValue) {
                popOver = new PopOver(finalDescription);
                popOver.show(node);
                ((Parent)popOver.getSkin().getNode()).getStylesheets()
                        .add(getClass().getResource("../../../Addition/Styles/Styles.css").toExternalForm());

            }
        });
    }

    private void initializeDatePicker() {
        Callback<DatePicker, DateCell> dayCellFactory =
                (final DatePicker datePicker) -> new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        boolean alreadySelected = viewModel.getSelectedDates().contains(item);
                        setStyle(alreadySelected ? "-fx-background-color: #DB5461;" : "");
                        if (item.isBefore(LocalDate.now())) {
                            setDisable(true);
                        }
                        if(item.isAfter(LocalDate.now().plusDays(30))){
                            setDisable(true);
                        }
                        if (!empty) {
                            addEventHandler(MouseEvent.MOUSE_CLICKED, mouseClickedEventHandler);
                        }
                        else {
                            removeEventHandler(MouseEvent.MOUSE_CLICKED, mouseClickedEventHandler);
                        }
                    }
                };
        datePicker.setDayCellFactory(dayCellFactory);
    }

    private void dataPickerEvent()
    {
        datePicker.setOnAction(actionEvent -> {
            viewModel.addNewSelectedDate();
        });

        mouseClickedEventHandler = clickEvent -> {
            if (clickEvent.getButton() == MouseButton.PRIMARY) {
                datePicker.show();
            }
            clickEvent.consume();
        };
    }

    private void selectAndLoadImage(String imageId)
    {
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png");
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(filter);
        fileChooser.setTitle("Select Image");
        File selectedImage = fileChooser.showOpenDialog(new Stage());
        if(selectedImage != null) {
            Image image = new Image(selectedImage.toURI().toString());
            viewModel.addImageFile(imageId, selectedImage);
            viewModel.setImage(imageId, image);
        }
    }
}
