package igor.branco.origo.controller;

import igor.branco.origo.dto.ProdutoDto;
import igor.branco.origo.entity.Produto;
import igor.branco.origo.exception.ErrorResponse;
import igor.branco.origo.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor
@Tag(name = "Produtos", description = "Operações de CRUD para produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    @Operation(summary = "Listar produtos", description = "Retorna todos os produtos cadastrados")
    @ApiResponse(responseCode = "200", description = "Lista de produtos",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Produto.class))))
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Produto> findAll() {
        return produtoService.findAll();
    }

    @Operation(summary = "Buscar produto por ID", description = "Retorna um produto pelo identificador")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produto encontrado",
                    content = @Content(schema = @Schema(implementation = Produto.class))),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Produto findById(
            @Parameter(description = "ID do produto", example = "1") @PathVariable Long id) {
        return produtoService.findById(id);
    }

    @Operation(summary = "Criar produto", description = "Cadastra um novo produto no sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Produto criado",
                    content = @Content(schema = @Schema(implementation = Produto.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Produto createProduct(@Valid @RequestBody ProdutoDto produtoDto) {
        return produtoService.createProduct(produtoDto);
    }

    @Operation(summary = "Atualizar produto", description = "Atualiza os dados de um produto existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produto atualizado",
                    content = @Content(schema = @Schema(implementation = Produto.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Produto updateProduct(
            @Parameter(description = "ID do produto", example = "1") @PathVariable Long id,
            @Valid @RequestBody ProdutoDto produtoDto) {
        return produtoService.updateProduct(produtoDto, id);
    }

    @Operation(summary = "Excluir produto", description = "Remove um produto do sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Produto excluído"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(
            @Parameter(description = "ID do produto", example = "1") @PathVariable Long id) {
        produtoService.deleteProduct(id);
    }
}
