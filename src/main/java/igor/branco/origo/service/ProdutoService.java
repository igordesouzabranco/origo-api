package igor.branco.origo.service;

import igor.branco.origo.dto.ProdutoDto;
import igor.branco.origo.entity.Produto;
import igor.branco.origo.exception.NotFoundException;
import igor.branco.origo.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public List<Produto> findAll() {
        return produtoRepository.findAll();
    }

    public Produto findById(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Produto não encontrado"));
    }

    public Produto createProduct(ProdutoDto produtoDto) {
        Produto produto = Produto.builder()
                .name(produtoDto.getName())
                .price(produtoDto.getPrice())
                .amount(produtoDto.getAmount())
                .build();

        return produtoRepository.save(produto);
    }

    public Produto updateProduct(ProdutoDto produtoDto, Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Produto não encontrado"));

        produto.setName(produtoDto.getName());
        produto.setPrice(produtoDto.getPrice());
        produto.setAmount(produtoDto.getAmount());

        return produtoRepository.save(produto);
    }

    public void deleteProduct(Long id) {
        if (!produtoRepository.existsById(id)) {
            throw new NotFoundException("Produto não encontrado");
        }
        produtoRepository.deleteById(id);
    }
}
