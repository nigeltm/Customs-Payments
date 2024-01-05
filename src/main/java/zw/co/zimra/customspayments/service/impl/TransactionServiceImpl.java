package zw.co.zimra.customspayments.service.impl;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import zw.co.zimra.customspayments.dto.ArchivedTransaction;
import zw.co.zimra.customspayments.dto.PageContent;
import zw.co.zimra.customspayments.dto.Transaction;
import zw.co.zimra.customspayments.service.TransactionService;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static zw.co.zimra.customspayments.constants.Constants.CUSTOMS_TRANSACTIONS_URL;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    HttpClient httpClient = HttpClient.newHttpClient();
    Gson gson = new Gson();
    @Override
    public List<Transaction> getTransactions() {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(CUSTOMS_TRANSACTIONS_URL))
                .build();

        try {
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            int statusCode = response.statusCode();
            String responseBody = response.body();
            List<Transaction> transactions = new ArrayList<>();

            if(statusCode==200){
                if(responseBody!=null){
                    try{
                        zw.co.zimra.customspayments.dto.HttpResponse httpResponse
                                = gson.fromJson(responseBody,zw.co.zimra.customspayments.dto.HttpResponse.class);
                        ObjectMapper objectMapper = new ObjectMapper();
                        objectMapper.registerModule(new JavaTimeModule());

                        String jsonString  = objectMapper.writeValueAsString(httpResponse.getData().get("transactions"));
                        JsonFactory jsonFactory = new JsonFactory();
                        try (JsonParser jsonParser = jsonFactory.createParser(jsonString)) {
                            List<Transaction> transactionList = objectMapper.readValue(jsonParser, new TypeReference<List<Transaction>>() {});
                            transactions.addAll(transactionList);
                        }
                    return transactions;
                    }catch(JsonSyntaxException exception){
                        log.error(exception.getMessage());
                    }
                }else {
                    log.error("Request failed with status code: " + statusCode);
                    throw new RuntimeException("Request failed with status code "+statusCode);
                }
            }
        } catch (IOException | InterruptedException exception) {
            log.error(exception.getMessage());
        }
        return null;
    }

    @Override
    public PageContent getArchivedTransactions(LocalDateTime dateFrom, LocalDateTime dateTo, int page, int size) {
       HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(CUSTOMS_TRANSACTIONS_URL+"/archived?dateFrom="+dateFrom+"&dateTo="+dateTo+"&page="+page+"&size="+size))
                .build();

        try {
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            int statusCode = response.statusCode();
            String responseBody = response.body();
            List<ArchivedTransaction> transactions = new ArrayList<>();
            if(statusCode==200){
                if(responseBody!=null){
                    try{
                        zw.co.zimra.customspayments.dto.HttpResponse httpResponse
                                = gson.fromJson(responseBody,zw.co.zimra.customspayments.dto.HttpResponse.class);

                        ObjectMapper objectMapper = new ObjectMapper();
                        objectMapper.registerModule(new JavaTimeModule());

                        String jsonString  = objectMapper.writeValueAsString(httpResponse.getData().get("page"));
                        PageContent content = objectMapper.readValue(jsonString, PageContent.class);
//                        String jsonString1 = objectMapper.writeValueAsString(content.getContent());
//
//                        System.out.println("**********");
//                        System.out.println(jsonString1);
//                        System.out.println("**********");
//                        JsonFactory jsonFactory = new JsonFactory();
//                        try (JsonParser jsonParser = jsonFactory.createParser(jsonString1)) {
//                            List<ArchivedTransaction> transactionList = objectMapper.readValue(jsonParser, new TypeReference<List<ArchivedTransaction>>() {});
//                            transactions.addAll(transactionList);
//                        }
                        return content;
                    }catch(JsonSyntaxException exception){
                        log.error(exception.getMessage());
                    }
                }else {
                    log.error("Request failed with status code: " + statusCode);
                    throw new RuntimeException("Request failed with status code "+statusCode);
                }
            }
        } catch (IOException | InterruptedException exception) {
            log.error(exception.getMessage());
        }
        return null;
    }
}
