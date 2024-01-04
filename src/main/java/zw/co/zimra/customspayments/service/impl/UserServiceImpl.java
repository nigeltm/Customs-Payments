package zw.co.zimra.customspayments.service.impl;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import zw.co.zimra.customspayments.dto.LoginResponse;
import zw.co.zimra.customspayments.exception.ResourceNotFoundException;
import zw.co.zimra.customspayments.model.User;
import zw.co.zimra.customspayments.repository.UserRepository;
import zw.co.zimra.customspayments.service.UserService;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static zw.co.zimra.customspayments.constants.Constants.ACTIVE_DIRECTORY_URL;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    HttpClient httpClient = HttpClient.newHttpClient();
    Gson gson = new Gson();


    public User saveUser(User user){
        return userRepository.save(user);
    }

    public User getUser(String username) {
        return userRepository.findByUsername(username).orElseThrow(()-> new ResourceNotFoundException(String.format("User with username: %s not found.",username)));
    }




    public LoginResponse login(String username, String password){
        LoginResponse loginResponse  = new LoginResponse();
        User user = userRepository.findByUsername(username)
                .orElseThrow(()->new ResourceNotFoundException("You are not authorised to access this system. Please contact Administrator."));


        String url = ACTIVE_DIRECTORY_URL+"?Username="+username+"&Password=" + URLEncoder.encode(password, StandardCharsets.UTF_8);
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json; charset=UTF-8")
                .build();
        try{
            HttpResponse<String> response = httpClient.send(httpRequest,HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();
            if(Objects.equals(responseBody, "true")){
                loginResponse.setAuthenticated(true);
                loginResponse.setUser(user);
            }else{
                loginResponse.setAuthenticated(false);
                loginResponse.setUser(null);
            }
        }catch(JsonSyntaxException exception){
            log.error(exception.getMessage());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        return loginResponse;
    }

}
