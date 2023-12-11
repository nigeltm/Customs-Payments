package zw.co.zimra.customspayments.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.util.Map;


@JsonInclude(JsonInclude.Include.NON_DEFAULT)

public class HttpResponse {

    private String timeStamp;
    private int statusCode;
    private HttpStatus status;
    private String reason;
    private String message;
    private String developerMessage;
    private Map<?,?> data;


    public HttpResponse() {
    }

    public HttpResponse(String timeStamp, int statusCode, HttpStatus status, String reason, String message, String developerMessage, Map<?, ?> data) {
        this.timeStamp = timeStamp;
        this.statusCode = statusCode;
        this.status = status;
        this.reason = reason;
        this.message = message;
        this.developerMessage = developerMessage;
        this.data = data;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }

    public void setDeveloperMessage(String developerMessage) {
        this.developerMessage = developerMessage;
    }

    public Map<?, ?> getData() {
        return data;
    }

    public void setData(Map<?, ?> data) {
        this.data = data;
    }
}
