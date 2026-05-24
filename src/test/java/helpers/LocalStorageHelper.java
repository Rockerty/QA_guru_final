package helpers;

public class LocalStorageHelper {

    public static String buildAuthData(String userId, String username, String accessToken, String refreshToken) {
        return String.format("""
            {
                "user": {
                    "id": %s,
                    "username": "%s",
                    "firstName": "",
                    "lastName": "",
                    "email": "",
                    "remoteAddr": "93.77.180.175"
                },
                "accessToken": "%s",
                "refreshToken": "%s",
                "isAuthenticated": true
            }
            """,
                userId, username, accessToken, refreshToken
        );
    }
}