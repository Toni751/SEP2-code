package ESharing.Client.Core;

import ESharing.Client.Views.AdminAdvertisementsView.AdminAdvertisementsViewModel;
import ESharing.Client.Views.AdminDashboardView.AdminDashboardViewModel;
import ESharing.Client.Views.AdminEditUserView.AdminEditUserViewModel;
import ESharing.Client.Views.AdvertisementView.AdvertisementViewModel;
import ESharing.Client.Views.ChatView.ChatViewModel;
import ESharing.Client.Views.CreateAdView.CreateAdViewModel;
import ESharing.Client.Views.EditAdminView.EditAdminViewModel;
import ESharing.Client.Views.MainAccountSetting.MainSettingViewModel;
import ESharing.Client.Views.MainAdminView.MainAdminViewModel;
import ESharing.Client.Views.MainAppView.MainAppViewModel;
import ESharing.Client.Views.AdminUsersView.ManageUsersViewModel;
import ESharing.Client.Views.ReservationView.ReservationViewModel;
import ESharing.Client.Views.SignInView.SignInViewModel;
import ESharing.Client.Views.SignUpView.SignUpViewModel;
import ESharing.Client.Views.UserAddressSettingView.UserAddressSettingViewModel;
import ESharing.Client.Views.UserAdvertisementView.UserAdvertisementViewModel;
import ESharing.Client.Views.UserInfoSettingView.UserInfoSettingViewModel;
import ESharing.Client.Views.UserView.UserViewModel;
import ESharing.Client.Views.WelcomeView.WelcomeViewModel;

/**
 * The class responsible for managing all view models
 * @version 1.0
 * @author Group1
 */
public class ViewModelFactory {

    private WelcomeViewModel welcomeViewModel;
    private SignInViewModel signInViewModel;
    private SignUpViewModel signUpViewModel;
    private MainAppViewModel mainAppViewModel;
    private MainSettingViewModel mainSettingViewModel;
    private UserInfoSettingViewModel userInfoSettingViewModel;
    private UserAddressSettingViewModel userAddressSettingViewModel;
    private ManageUsersViewModel manageUsersViewModel;
    private MainAdminViewModel mainAdminViewModel;
    private AdminDashboardViewModel adminDashboardViewModel;
    private AdminEditUserViewModel adminEditUserViewModel;
    private ChatViewModel chatViewModel;
    private EditAdminViewModel editAdminViewModel;
    private CreateAdViewModel adViewModel;
    private AdminAdvertisementsViewModel adminAdvertisementsViewModel;
    private AdvertisementViewModel advertisementViewModel;
    private UserViewModel userViewModel;
    private UserAdvertisementViewModel userAdvertisementViewModel;
    private ReservationViewModel reservationViewModel;
    private static ViewModelFactory viewModelFactory;

    /**
     * A private constructor initializes all view model
     */
    private ViewModelFactory()
    {
        welcomeViewModel = new WelcomeViewModel();
        signInViewModel = new SignInViewModel();
        signUpViewModel = new SignUpViewModel();
        mainAppViewModel = new MainAppViewModel();
        mainSettingViewModel = new MainSettingViewModel();
        userAddressSettingViewModel = new UserAddressSettingViewModel();
        userInfoSettingViewModel = new UserInfoSettingViewModel();
        mainAdminViewModel = new MainAdminViewModel();
        manageUsersViewModel = new ManageUsersViewModel();
        adminDashboardViewModel = new AdminDashboardViewModel();
        adminEditUserViewModel = new AdminEditUserViewModel();
        chatViewModel= new ChatViewModel();
        editAdminViewModel = new EditAdminViewModel();
        adViewModel = new CreateAdViewModel();
        advertisementViewModel = new AdvertisementViewModel();
        adminAdvertisementsViewModel = new AdminAdvertisementsViewModel();
        userViewModel = new UserViewModel();
        userAdvertisementViewModel = new UserAdvertisementViewModel();
        reservationViewModel = new ReservationViewModel();
    }

    /**
     * Returns ViewModelFactory object if it exists, otherwise creates new object
     * @return the ViewModelFactory object
     */
    public static ViewModelFactory getViewModelFactory()
    {
        if(viewModelFactory == null)
            viewModelFactory = new ViewModelFactory();
        return viewModelFactory;
    }

    /**
     * Returns initialized view model of the welcome view
     * @return the initialized welcomeViewModel
     */
    public WelcomeViewModel getWelcomeViewModel() {
        return welcomeViewModel;
    }

    /**
     * Returns initialized view model of the signIn view
     * @return the initialized signInViewModel
     */
    public SignInViewModel getSignInViewModel() {
        return signInViewModel;
    }

    /**
     * Returns initialized view model of the signUp view
     * @return the initialized signUpViewModel
     */
    public SignUpViewModel getSignUpViewModel() {
        return signUpViewModel;
    }

    /**
     * Returns initialized view model of the main user setting view
     * @return the initialized MainSettingViewModel
     */
    public MainSettingViewModel getMainSettingViewModel() {
        return mainSettingViewModel;
    }

    /**
     * Returns initialized view model of the main system view
     * @return the initialized MainAppViewModel
     */
    public MainAppViewModel getMainAppViewModel() {
        return mainAppViewModel;
    }

    /**
     * Returns initialized view model of the user address setting view
     * @return the initialized UserAddressSettingViewModel
     */
    public UserAddressSettingViewModel getUserAddressSettingViewModel() {
        return userAddressSettingViewModel;
    }

    /**
     * Returns initialized view model of the user information setting view
     * @return the initialized UserInfoSettingViewModel
     */
    public UserInfoSettingViewModel getUserInfoSettingViewModel() {
        return userInfoSettingViewModel;
    }

    /**
     * Returns initialized view model of the admin main view
     * @return the initialized MainAdminViewModel
     */
    public MainAdminViewModel getMainAdminViewModel() {
        return mainAdminViewModel;
    }

    /**
     * Returns initialized view model of the admin manage users view
     * @return the initialized ManageUsersViewModel
     */
    public ManageUsersViewModel getManageUsersViewModel() {
        return manageUsersViewModel;
    }

    /**
     * Returns initialized view model of the admin dashboard view
     * @return the initialized AdminDashboardViewModel
     */
    public AdminDashboardViewModel getAdminDashboardViewModel() {
        return adminDashboardViewModel;
    }

    /**
     * Returns initialized view model of the admin edit user view
     * @return the initialized AdminEditUserViewModel
     */
    public AdminEditUserViewModel getAdminEditUserViewModel() {
        return adminEditUserViewModel;
    }

    /**
     * Returns initialized view model of the chat view
     * @return the initialized ChatViewModel
     */
    public ChatViewModel getChatViewModel() {
        return chatViewModel;
    }

    /**
     * Returns initialized view model of the edit admin account view
     * @return the initialized EditAdminViewModel
     */
    public EditAdminViewModel getEditAdminViewModel() {
        return editAdminViewModel;
    }

    /**
     * Returns initialized view model of the create advertisement view
     * @return the initialized CreateAdViewModel
     */
    public CreateAdViewModel getAdViewModel() {
        return adViewModel;
    }

    public AdminAdvertisementsViewModel getAdminAdvertisementsViewModel() {
        return adminAdvertisementsViewModel;
    }

    public AdvertisementViewModel getAdvertisementViewModel() {
        return advertisementViewModel;
    }

    public UserViewModel getUserViewModel() {
        return userViewModel;
    }

    public UserAdvertisementViewModel getUserAdvertisementViewModel() {
        return userAdvertisementViewModel;
    }

    public ReservationViewModel getReservationViewModel() {
        return reservationViewModel;
    }
}
