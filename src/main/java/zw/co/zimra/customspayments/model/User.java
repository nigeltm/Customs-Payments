package zw.co.zimra.customspayments.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String username;
    @NotNull
    private String firstName;
    private String middleName;
    @NotNull
    private String surname;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime lastLogin;

}
