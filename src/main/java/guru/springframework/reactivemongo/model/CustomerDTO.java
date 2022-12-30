package guru.springframework.reactivemongo.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Created by jt, Spring Framework Guru.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDTO {

    private String id;

    @NotBlank
    private String customerName;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
}
