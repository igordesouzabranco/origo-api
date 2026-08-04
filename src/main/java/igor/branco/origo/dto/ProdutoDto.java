package igor.branco.origo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ProdutoDto {

    @NotBlank(message = "Nome não pode ficar em branco")
    private String name;

    @NotNull(message = "Preço não pode ser nulo")
    @Positive(message = "Preço deve ser maior que zero")
    private BigDecimal price;

    @NotNull(message = "Quantidade não pode ser nula")
    @PositiveOrZero(message = "Quantidade deve ser zero ou maior")
    private Integer amount;
}
