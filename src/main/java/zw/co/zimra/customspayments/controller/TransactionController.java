package zw.co.zimra.customspayments.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import zw.co.zimra.customspayments.dto.HttpResponse;
import zw.co.zimra.customspayments.dto.Transaction;
import zw.co.zimra.customspayments.service.TransactionService;

import java.net.URI;
import java.util.List;
import java.util.Map;

import static java.time.LocalDateTime.now;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/customs/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    @GetMapping("")
    public ResponseEntity<HttpResponse> getTransactions(){
        List<Transaction> transactions = transactionService.getTransactions();
        HttpResponse httpResponse = new HttpResponse();
        httpResponse.setTimeStamp(now().toString());
        httpResponse.setData(Map.of("transactions",transactions));
        httpResponse.setMessage(transactions.size() + " transactions retrieved.");
        httpResponse.setStatus(HttpStatus.CREATED);
        httpResponse.setStatusCode(HttpStatus.CREATED.value());
        return ResponseEntity.created(getUri()).body(httpResponse);
    }
    private URI getUri() {
        return URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/users").toUriString());
    }
}