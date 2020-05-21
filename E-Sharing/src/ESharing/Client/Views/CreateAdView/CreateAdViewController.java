package ESharing.Client.Views.CreateAdView;

import ESharing.Client.Core.ViewModelFactory;
import ESharing.Client.Views.ViewController;
import ESharing.Shared.Util.AdImages;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.skins.JFXDatePickerSkin;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.awt.*;
import java.io.File;
import java.time.LocalDate;

public class CreateAdViewController extends ViewController {


    @FXML private Label warningLabel;
    @FXML private Pane warningPane;
    @FXML private ImageView mainImage;
    @FXML private ImageView subImage2;
    @FXML private ImageView subImage1;
    @FXML private ImageView subImage3;
    @FXML private ImageView subImage4;
    @FXML private JFXTextField titleTextField;
    @FXML private JFXComboBox<String> typeComboBox;
    @FXML private JFXTextField priceTextField;
    @FXML private JFXTextArea descriptionTextField;
    @FXML private DatePicker datePicker;

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

        typeComboBox.setItems(viewModel.getTypeItemsProperty());

        mainImage.imageProperty().bind(viewModel.getMainImageProperty());
        subImage1.imageProperty().bind(viewModel.getSubImage1Property());
        subImage2.imageProperty().bind(viewModel.getSubImage2Property());
        subImage3.imageProperty().bind(viewModel.getSubImage3Property());
        subImage4.imageProperty().bind(viewModel.getSubImage4Property());

        viewModel.setDefaultView();
        initializeDatePicker();
        dataPickerEvent();
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
}
