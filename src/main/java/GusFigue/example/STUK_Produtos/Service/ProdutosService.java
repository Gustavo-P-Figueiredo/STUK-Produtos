package GusFigue.example.STUK_Produtos.Service;

import GusFigue.example.STUK_Produtos.DTO.ProdutosDTO;
import GusFigue.example.STUK_Produtos.Entity.Produtos;
import GusFigue.example.STUK_Produtos.Repository.ProdutosRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutosService {

    @Autowired
    private ProdutosRepository produtosRepository;

    public List<Produtos> listarProdutos() {
        return produtosRepository.findAll();
    }

    public ProdutosDTO cadastrarProduto(ProdutosDTO dto) {
        Produtos produto = new Produtos();

        produto.setDescricao(dto.descricao());
        produto.setValor(dto.valor());
        produto.setPeso(dto.peso());
        produto.setAtivo(dto.ativo());
        produto.setCanal(dto.canal());

        Produtos salvo = produtosRepository.save(produto);

        return new ProdutosDTO(
                salvo.getID(),
                salvo.getDescricao(),
                salvo.getValor(),
                salvo.getPeso(),
                salvo.getAtivo(),
                salvo.getCanal()
        );
    }

    public Optional<Produtos> buscarProduto(Long ID) {
        Produtos produto = (Produtos) produtosRepository.findById(ID)
                .orElseThrow(() -> new EntityNotFoundException("produto não encontrado"));
        return Optional.of(produto);
    }

    public Produtos deletarProdutos(Long ID) {
        Produtos produtos = (Produtos) produtosRepository.findById(ID)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));
        produtosRepository.deleteById(ID);
        return produtos;
    }

    public Produtos atualizarProdutos(Long ID, ProdutosDTO dto) {
        Produtos produtos = (Produtos) produtosRepository.findById(ID)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));

        if (dto.descricao() != null) {
            produtos.setDescricao(dto.descricao());
        }

        if (dto.valor() != null) {
            produtos.setValor(dto.valor());
        }

        if (dto.peso() != null) {
            produtos.setPeso(dto.peso());
        }

        if (dto.ativo() != null) {
            produtos.setAtivo(dto.ativo());
        }

        if (dto.canal() != null) {
            produtos.setCanal(dto.canal());
        }

        return produtosRepository.save(produtos);
    }
}
