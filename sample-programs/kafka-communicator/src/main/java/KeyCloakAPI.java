import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class KeyCloakAPI {

    private String apiBaseName;
    private String realm;
    private String clientID;
    private String user;
    private String pass;

    private String accessToken;
    private String refreshToken;


    public KeyCloakAPI(String apiBaseName, String realm, String clientID, String user, String pass){
        this.apiBaseName = apiBaseName;
        this.realm = realm;
        this.clientID = clientID;
        this.user = user;
        this.pass = pass;
    }

    public void authenticateWithKeycloak() throws IOException {
        URL url = new URL(apiBaseName + "/auth/realms/" + realm + "/protocol/openid-connect/token");

        String readLine = null;

        HttpURLConnection http = (HttpURLConnection)url.openConnection();
        http.setRequestMethod("POST");
        http.setDoOutput(true);

        //My program accepts json
        http.setRequestProperty("Accept", "application/json");

        //server receives x www form urlencoded
        http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");





        String data ="username=" + user + "&password=" + pass + "&grant_type=password&client_id=" + clientID;

        byte[] out = data.getBytes(StandardCharsets.UTF_8);

        OutputStream stream = http.getOutputStream();
        stream.write(out);


        BufferedReader in = new BufferedReader(
                new InputStreamReader(http.getInputStream()));
        StringBuffer response = new StringBuffer();
        while ((readLine = in .readLine()) != null) {
            response.append(readLine);
        } in .close();


        // print result

        String responseBody = http.getResponseMessage();

        System.out.println("Response code: " + http.getResponseCode() + " Body: " + responseBody);

        System.out.println("JSON String Result " + response.toString());


        http.disconnect();

        Gson gson = new Gson();

        AuthenticationObject ao = new AuthenticationObject();

        ao = gson.fromJson(response.toString(), AuthenticationObject.class);

        accessToken = ao.access_token;
        refreshToken = ao.refresh_token;



    }


    public void refreshAccessToken(){

    }

    public String getEvents() throws IOException {

        URL url = new URL(apiBaseName + "/auth/admin/realms/keycloak-demo/events");
        HttpURLConnection http = (HttpURLConnection)url.openConnection();

        http.setRequestProperty("Authorization","Bearer "+ accessToken);

        http.setRequestProperty("Content-Type","application/json");
        http.setRequestMethod("GET");


        System.out.println(http.getResponseCode() + " " + http.getResponseMessage());

        String readLine = null;

        BufferedReader in = new BufferedReader(
                new InputStreamReader(http.getInputStream()));
        StringBuffer response = new StringBuffer();
        while ((readLine = in .readLine()) != null) {
            response.append(readLine);
        } in .close();

        System.out.println("Events: " + response);

        http.disconnect();

        return response.toString();

    }



}
