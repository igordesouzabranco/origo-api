package igor.branco.origo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Dados para criar ou atualizar um produto")
public class ProdutoDto {

    @NotBlank(message = "Nome não pode ficar em branco")
    @Schema(description = "Nome do produto", example = "Notebook")
    private String name;

    @NotNull(message = "Preço não pode ser nulo")
    @Positive(message = "Preço deve ser maior que zero")
    @Schema(description = "Preço unitário", example = "2499.90")
    private BigDecimal price;

    @NotNull(message = "Quantidade não pode ser nula")
    @PositiveOrZero(message = "Quantidade deve ser zero ou maior")
    @Schema(description = "Quantidade em estoque", example = "10")
    private Integer amount;
}
