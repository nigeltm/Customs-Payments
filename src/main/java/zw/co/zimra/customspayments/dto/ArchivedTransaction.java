package zw.co.zimra.customspayments.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArchivedTransaction {
    @Id
    private String referenceNumber;
    private String accountNumber;
    private String bankName;
    private String bpNumber;
    private String clientName;
    private String taxCode;
    private String office;
    private String currency;
    private String amount;
    private String paymentDate;
    private String sap;
    private String customsReceiptNumber;
    private String customsMessage;
    private LocalDateTime modifiedAt;
    private String rrn;






}
