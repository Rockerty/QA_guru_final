package api;

import api.club.ClubApiClient;
import api.login.LoginApiClient;
import api.logout.LogoutApiClient;
import api.registration.RegistrationApiClient;
import api.update.UpdateUserApiClient;

public class ApiClient {

    public final LoginApiClient login = new LoginApiClient();
    public final LogoutApiClient logout = new LogoutApiClient();
    public final RegistrationApiClient registration = new RegistrationApiClient();
    public final UpdateUserApiClient updateUser = new UpdateUserApiClient();
    public final ClubApiClient club = new ClubApiClient();
}
