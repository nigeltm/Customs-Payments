package zw.co.zimra.customspayments.controller;


import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import zw.co.zimra.customspayments.dto.ArchivedTransaction;
import zw.co.zimra.customspayments.dto.HttpResponse;
import zw.co.zimra.customspayments.dto.PageContent;
import zw.co.zimra.customspayments.dto.Transaction;
import zw.co.zimra.customspayments.service.TransactionService;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
        httpResponse.setStatus(HttpStatus.OK);
        httpResponse.setStatusCode(HttpStatus.OK.value());
        return ResponseEntity.ok().body(httpResponse);
    }


    @GetMapping("/archived")
    public ResponseEntity<HttpResponse> getArchivedTransactions(@RequestParam LocalDateTime dateFrom,
                                                                @RequestParam LocalDateTime dateTo,
                                                                @RequestParam Optional<Integer> page,
                                                                @RequestParam Optional<Integer> size){
        PageContent transactions = transactionService.getArchivedTransactions(dateFrom,
                dateTo,
                page.orElse(0),
                size.orElse(10));
        HttpResponse httpResponse = new HttpResponse();
        httpResponse.setTimeStamp(now().toString());
        httpResponse.setData(Map.of("page",transactions));
       // httpResponse.setMessage(transactions.size() + " transactions retrieved.");
        httpResponse.setStatus(HttpStatus.OK);
        httpResponse.setStatusCode(HttpStatus.OK.value());
        return ResponseEntity.ok().body(httpResponse);
    }

    @GetMapping("/cbz")
    public ResponseEntity<HttpResponse> getArchivedCBZTransactions(@PathParam("dateFrom") String dateFrom, @PathParam("dateTo") String dateTo){
        List<ArchivedTransaction> transactions = transactionService.getArchivedCBZTransactions(dateFrom, dateTo);
        HttpResponse httpResponse = new HttpResponse();
        httpResponse.setTimeStamp(now().toString());
        httpResponse.setData(Map.of("transactions",transactions));
        httpResponse.setMessage(transactions.size() + " transactions retrieved.");
        httpResponse.setStatus(HttpStatus.OK);
        httpResponse.setStatusCode(HttpStatus.OK.value());
        return ResponseEntity.ok().body(httpResponse);
    }
    private URI getUri() {
        return URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/users").toUriString());
    }
}
