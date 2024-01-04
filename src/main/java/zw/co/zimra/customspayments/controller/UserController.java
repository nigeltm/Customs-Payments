package zw.co.zimra.customspayments.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import zw.co.zimra.customspayments.dto.HttpResponse;
import zw.co.zimra.customspayments.dto.LoginRequest;
import zw.co.zimra.customspayments.dto.LoginResponse;
import zw.co.zimra.customspayments.model.User;
import zw.co.zimra.customspayments.service.UserService;

import java.net.URI;
import java.util.Map;

import static java.time.LocalDateTime.now;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PostMapping("/login")
    public ResponseEntity<HttpResponse> login(@RequestBody  @Valid LoginRequest loginRequest){
        LoginResponse response = userService.login(loginRequest.getUsername(), loginRequest.getPassword());
        HttpResponse httpResponse = new HttpResponse();
        httpResponse.setTimeStamp(now().toString());
        if(response.isAuthenticated()){
            httpResponse.setData(Map.of("user", response));
            httpResponse.setMessage("Login successful for "+ loginRequest.getUsername());
            httpResponse.setStatus(HttpStatus.OK);
            httpResponse.setStatusCode(HttpStatus.OK.value());
            return ResponseEntity.ok().body(httpResponse);
        }else{
            httpResponse.setData(Map.of("user", response));
            httpResponse.setMessage("Login failed for "+ loginRequest.getUsername());
            httpResponse.setStatus(HttpStatus.BAD_REQUEST);
            httpResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(httpResponse);
        }

    }

    @PostMapping("")
    public ResponseEntity<HttpResponse> saveUser(@RequestBody @Valid User user){
        HttpResponse httpResponse = new HttpResponse();
        httpResponse.setTimeStamp(now().toString());
        httpResponse.setData(Map.of("user",userService.saveUser(user)));
        httpResponse.setMessage("User created successfully.");
        httpResponse.setStatus(HttpStatus.CREATED);
        httpResponse.setStatusCode(HttpStatus.CREATED.value());
        return ResponseEntity.created(getUri()).body(httpResponse);
    }

    @GetMapping("")
    public ResponseEntity<HttpResponse> getUser(@RequestParam("username") String username){
        HttpResponse httpResponse = new HttpResponse();
        httpResponse.setTimeStamp(now().toString());
        httpResponse.setData(Map.of("user",userService.getUser(username)));
        httpResponse.setMessage("User retrieved successfully.");
        httpResponse.setStatus(HttpStatus.OK);
        httpResponse.setStatusCode(HttpStatus.OK.value());
        return ResponseEntity.ok().body(httpResponse);
    }

    private URI getUri() {
        return URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/users").toUriString());
    }
}
