package ESharing.Client.Views.AdvertisementView;

import ESharing.Client.Core.ViewHandler;
import ESharing.Client.Core.ViewModelFactory;
import ESharing.Client.Model.UserActions.LoggedUser;
import ESharing.Client.Views.ViewController;
import ESharing.Shared.Util.AdImages;
import ESharing.Shared.Util.Views;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Callback;
import org.controlsfx.control.PopOver;
import org.controlsfx.control.Rating;

import java.time.LocalDate;

public class AdvertisementViewController extends ViewController {

    @FXML private Label ownerPhoneNumberLabel;
    @FXML private Label ownerAccountCreationDate;
    @FXML private Button openOwnerProfileButton;
    @FXML private Rectangle titleImageRectangle;
    @FXML private ImageView adminBackArrow;
    @FXML private ScrollPane scrollPane;
    @FXML private JFXButton removeButton;
    @FXML private JFXButton reserveButton;
    @FXML private Rectangle mainImageRectangle;
    @FXML private Rectangle subImage1Rectangle;
    @FXML private Rectangle subImage2Rectangle;
    @FXML private Rectangle subImage3Rectangle;
    @FXML private Rectangle subImage4Rectangle;
    @FXML private Label titleLabel;
    @FXML private Label usernameLabel;
    @FXML private Circle avatarCircle;
    @FXML private Label warningLabel;
    @FXML private Pane warningPane;
    @FXML private Label typeLabel;
    @FXML private TextArea descriptionTextArea;
    @FXML private DatePicker datePicker;
    @FXML private Label priceLabel;
    @FXML private AnchorPane mainPane;


    private PopOver imagePopOver;
    private EventHandler<MouseEvent> mouseClickedEventHandler;
    private AdvertisementViewModel advertisementViewModel;
    private ViewHandler viewHandler;

    public void init()
    {
        viewHandler = ViewHandler.getViewHandler();
        advertisementViewModel = ViewModelFactory.getViewModelFactory().getAdvertisementViewModel();
        advertisementViewModel.loadDefaultView();

        titleLabel.textProperty().bind(advertisementViewModel.getTitleProperty());
        descriptionTextArea.textProperty().bind(advertisementViewModel.getDescriptionProperty());
        priceLabel.textProperty().bind(advertisementViewModel.getPriceProperty());
        warningLabel.textProperty().bindBidirectional(advertisementViewModel.getWarningProperty());
        warningPane.styleProperty().bindBidirectional(advertisementViewModel.getWarningStyleProperty());
        warningPane.visibleProperty().bindBidirectional(advertisementViewModel.getWarningVisibleProperty());
        typeLabel.textProperty().bind(advertisementViewModel.getTypeProperty());
        datePicker.valueProperty().bindBidirectional(advertisementViewModel.getDateProperty());
        usernameLabel.textProperty().bind(advertisementViewModel.getOwnerUsernameProperty());
        avatarCircle.fillProperty().bindBidirectional(advertisementViewModel.getAvatarCircleProperty());
        removeButton.visibleProperty().bindBidirectional(advertisementViewModel.getRemoveVisibleProperty());
        reserveButton.visibleProperty().bindBidirectional(advertisementViewModel.getReserveVisibleProperty());
        //ratings.ratingProperty().bindBidirectional(advertisementViewModel.getRatingProperty());
        adminBackArrow.visibleProperty().bindBidirectional(advertisementViewModel.getArrowBackProperty());

        datePicker.dayCellFactoryProperty().bindBidirectional(advertisementViewModel.getCellFactoryProperty());

        mainImageRectangle.fillProperty().bindBidirectional(advertisementViewModel.getMainImageProperty());
        subImage1Rectangle.fillProperty().bindBidirectional(advertisementViewModel.getSub1ImageProperty());
        subImage2Rectangle.fillProperty().bindBidirectional(advertisementViewModel.getSub2ImageProperty());
        subImage3Rectangle.fillProperty().bindBidirectional(advertisementViewModel.getSub3ImageProperty());
        subImage4Rectangle.fillProperty().bindBidirectional(advertisementViewModel.getSub4ImageProperty());
        titleImageRectangle.fillProperty().bindBidirectional(advertisementViewModel.getMainImageProperty());

        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        dataPickerEvent();
        initializeDatePicker();

    }


    public void onOpenCalendar() {
        datePicker.show();
    }

    public void onReserveButton() {
        advertisementViewModel.sendReservationRequest();
    }

    public void onGoToUserView() {
        if(LoggedUser.getLoggedUser().getUser().getUser_id() != LoggedUser.getLoggedUser().getSelectedAdvertisement().getOwner().getUser_id()) {
            LoggedUser.getLoggedUser().setSelectedView(Views.USER_VIEW);
            viewHandler.openMainAppView();
        }
    }

    public void onRemovedAction() {
        String requestResult = advertisementViewModel.sendAdvertisementRemoveRequest();
        if(requestResult != null && requestResult.equals("ADMIN"))
            viewHandler.openAdminMainView();
        else if(requestResult != null && requestResult.equals("USER"))
            viewHandler.openMainSettingView(mainPane);
    }

    private void initializeDatePicker() {
        Callback<DatePicker, DateCell> dayCellFactory =
                (final DatePicker datePicker) -> new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        System.out.println(LoggedUser.getLoggedUser().getSelectedAdvertisement());
                        boolean alreadySelected = advertisementViewModel.getSelectedDates().contains(item);
                        boolean loadedUnavailability = advertisementViewModel.getUnavailableDates().contains(item);
                        if(loadedUnavailability) {
                            setStyle("-fx-background-color: #DB5461;");
                            setDisable(true);
                        }
                        if(alreadySelected)
                            setStyle("-fx-background-color: #4CDBC4;");
                        if (item.isBefore(LocalDate.now()))
                            setDisable(true);
                        if(!LoggedUser.getLoggedUser().getSelectedAdvertisement().getUnavailability().isEmpty() && item.isAfter(LoggedUser.getLoggedUser().getSelectedAdvertisement().getCreationDate().plusDays(30)))
                            setDisable(true);
                        if (!empty)
                            addEventHandler(MouseEvent.MOUSE_CLICKED, mouseClickedEventHandler);
                        else
                            removeEventHandler(MouseEvent.MOUSE_CLICKED, mouseClickedEventHandler);
                    }
                };
        datePicker.setDayCellFactory(dayCellFactory);
    }

    private void dataPickerEvent()
    {
        datePicker.setOnAction(actionEvent -> {
            advertisementViewModel.addSelectedDate();
            advertisementViewModel.recalculatePrice();
        });

        mouseClickedEventHandler = clickEvent -> {
            if (clickEvent.getButton() == MouseButton.PRIMARY) {
                datePicker.show();
            }
            clickEvent.consume();
        };
    }

    public void goBack() {
        LoggedUser.getLoggedUser().selectAdvertisement(null);
        if(LoggedUser.getLoggedUser().getUser().isAdministrator())
            viewHandler.openAdminMainView();
        else {
            LoggedUser.getLoggedUser().setSelectedUser(null);
            viewHandler.openMainAppView();
        }
    }

    public void sendRatings() {
        advertisementViewModel.sendRatingRequest();
    }

    public void clearRatings() {
        advertisementViewModel.clearRatingsProperty();
    }

    public void fillRatings() {
        advertisementViewModel.setDefaultRatingsProperty();
    }

    public void onReportAdvertisement() {
        advertisementViewModel.reportAdvertisement();
    }

    public void openSub4Picture() {
        if(LoggedUser.getLoggedUser().getSelectedAdvertisement().getPhotos().containsKey(AdImages.SUB_IMAGE4.toString()))
            viewHandler.openPicturePreview(LoggedUser.getLoggedUser().getSelectedAdvertisement().getPhoto(AdImages.SUB_IMAGE4.toString()));
    }

    public void openSub3Picture() {
        if(LoggedUser.getLoggedUser().getSelectedAdvertisement().getPhotos().containsKey(AdImages.SUB_IMAGE3.toString()))
            viewHandler.openPicturePreview(LoggedUser.getLoggedUser().getSelectedAdvertisement().getPhoto(AdImages.SUB_IMAGE3.toString()));
    }

    public void openSub2Picture() {
        if(LoggedUser.getLoggedUser().getSelectedAdvertisement().getPhotos().containsKey(AdImages.SUB_IMAGE2.toString()))
            viewHandler.openPicturePreview(LoggedUser.getLoggedUser().getSelectedAdvertisement().getPhoto(AdImages.SUB_IMAGE2.toString()));
    }

    public void openSub1Picture() {
        if(LoggedUser.getLoggedUser().getSelectedAdvertisement().getPhotos().containsKey(AdImages.SUB_IMAGE1.toString()))
            viewHandler.openPicturePreview(LoggedUser.getLoggedUser().getSelectedAdvertisement().getPhoto(AdImages.SUB_IMAGE1.toString()));
    }

    public void openMainPicture() {
        viewHandler.openPicturePreview(LoggedUser.getLoggedUser().getSelectedAdvertisement().getPhoto(AdImages.MAIN_IMAGE.toString()));
    }

    public void onGoToDescription() {
        scrollPane.setVvalue(0.55);
    }

    public void onGoToGallery() {
        scrollPane.setVvalue(0.80);
    }

    public void onGoToOwner() {
        scrollPane.setVvalue(1);
    }

    public void showImageDescription() {
        Label description = new Label("Click the image to open a preview");
        imagePopOver = new PopOver(description);
        imagePopOver.show(subImage3Rectangle);
        ((Parent)imagePopOver.getSkin().getNode()).getStylesheets()
                .add(getClass().getResource("../../../Addition/Styles/Styles.css").toExternalForm());
    }

    public void hideImageDescription() {
        imagePopOver.hide();
    }
}
