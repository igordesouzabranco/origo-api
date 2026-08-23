package igor.branco.origo.mcp;

import igor.branco.origo.dto.ProdutoDto;
import igor.branco.origo.entity.Produto;
import igor.branco.origo.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.ai.mcp.annotation.McpToolParam;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoMcpTools {

    private final ProdutoService produtoService;

    @McpTool(description = "Lista todos os produtos cadastrados no estoque")
    public List<Produto> listarProdutos() {
        return produtoService.findAll();
    }

    @McpTool(description = "Busca um produto pelo ID")
    public Produto buscarProduto(@McpToolParam(description = "ID do produto") Long id) {
        return produtoService.findById(id);
    }

    @McpTool(description = "Cadastra um novo produto no estoque")
    public Produto criarProduto(
            @McpToolParam(description = "Nome do produto") String nome,
            @McpToolParam(description = "Preço unitário, maior que zero") BigDecimal preco,
            @McpToolParam(description = "Quantidade em estoque, zero ou maior") Integer quantidade) {
        return produtoService.createProduct(
                ProdutoDto.builder().name(nome).price(preco).amount(quantidade).build());
    }

    @McpTool(description = "Atualiza os dados de um produto existente")
    public Produto atualizarProduto(
            @McpToolParam(description = "ID do produto") Long id,
            @McpToolParam(description = "Novo nome") String nome,
            @McpToolParam(description = "Novo preço") BigDecimal preco,
            @McpToolParam(description = "Nova quantidade") Integer quantidade) {
        return produtoService.updateProduct(
                ProdutoDto.builder().name(nome).price(preco).amount(quantidade).build(), id);
    }

    @McpTool(description = "Remove um produto do estoque")
    public void removerProduto(@McpToolParam(description = "ID do produto") Long id) {
        produtoService.deleteProduct(id);
    }
}
