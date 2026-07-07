package GusFigue.example.STUK_Produtos.Service;

import GusFigue.example.STUK_Produtos.DTO.ProdutosDTO;
import GusFigue.example.STUK_Produtos.Entity.Fornecedor;
import GusFigue.example.STUK_Produtos.Entity.Produtos;
import GusFigue.example.STUK_Produtos.Repository.FornecedorRepository;
import GusFigue.example.STUK_Produtos.Repository.ProdutosRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProdutosService {

    @Autowired
    private ProdutosRepository produtosRepository;

    @Autowired
    private FornecedorRepository fornecedorRepository;

    private ProdutosDTO toDTO(Produtos p) {
        return new ProdutosDTO(
                p.getId(),
                p.getDescricao(),
                p.getValor(),
                p.getPeso(),
                p.getAtivo(),
                p.getCanal(),
                p.getFornecedor().getId()
        );
    }

    public Page<ProdutosDTO> listarProdutos(int numeroPagina, int tamanho) {
        Pageable pageable = PageRequest.of(numeroPagina, tamanho);
        return produtosRepository.findAll(pageable)
                .map(this::toDTO);

    }

    public ProdutosDTO cadastrarProduto(ProdutosDTO dto) {

        Produtos produto = new Produtos();

        produto.setDescricao(dto.descricao());
        produto.setValor(dto.valor());
        produto.setPeso(dto.peso());
        produto.setAtivo(dto.ativo());
        produto.setCanal(dto.canal());

        Fornecedor fornecedor = fornecedorRepository
                .findById(dto.fornecedorId())
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Fornecedor não encontrado"
                        ));
        produto.setFornecedor(fornecedor);

        return toDTO(produtosRepository.save(produto));
    }

    public ProdutosDTO buscarProduto(Long id) {
        return toDTO(produtosRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("produto não encontrado")));

    }

    public ProdutosDTO deletarProdutos(Long id) {
        Produtos produto = produtosRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));

        produtosRepository.deleteById(id);

        return toDTO(produto);
    }

    public ProdutosDTO atualizarProdutos(Long id, ProdutosDTO dto) {
        Produtos produto = produtosRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));

        if (dto.descricao() != null) {
            produto.setDescricao(dto.descricao());
        }

        if (dto.valor() != null) {
            produto.setValor(dto.valor());
        }

        if (dto.peso() != null) {
            produto.setPeso(dto.peso());
        }

        if (dto.ativo() != null) {
            produto.setAtivo(dto.ativo());
        }

        if (dto.canal() != null) {
            produto.setCanal(dto.canal());
        }

        if (dto.fornecedorId() != null) {
            Fornecedor fornecedor = fornecedorRepository.findById(dto.fornecedorId())
                    .orElseThrow(() -> new EntityNotFoundException("Fornecedor não encontrado"));
            produto.setFornecedor(fornecedor);
        }

        Produtos salvo = produtosRepository.save(produto);

        return toDTO(salvo);
    }

}