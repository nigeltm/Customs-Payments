package zw.co.zimra.customspayments.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import zw.co.zimra.customspayments.dto.HttpResponse;
import zw.co.zimra.customspayments.model.User;
import zw.co.zimra.customspayments.service.UserService;

import java.net.URI;
import java.util.Map;

import static java.time.LocalDateTime.now;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")

public class UserController {

    private final UserService userService;

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
