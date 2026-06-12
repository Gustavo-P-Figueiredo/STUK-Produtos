package GusFigue.example.STUK_Produtos.Service;

import GusFigue.example.STUK_Produtos.DTO.ProdutosDTO;
import GusFigue.example.STUK_Produtos.Entity.Fornecedor;
import GusFigue.example.STUK_Produtos.Entity.Produtos;
import GusFigue.example.STUK_Produtos.Repository.FornecedorRepository;
import GusFigue.example.STUK_Produtos.Repository.ProdutosRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutosService {

    @Autowired
    private ProdutosRepository produtosRepository;

    @Autowired
    private FornecedorRepository fornecedorRepository;

    private ProdutosDTO toDTO(Produtos p) {
        return new ProdutosDTO(
                p.getID(),
                p.getDescricao(),
                p.getValor(),
                p.getPeso(),
                p.getAtivo(),
                p.getCanal(),
                p.getFornecedor_ID().getID()
        );
    }

    public List<ProdutosDTO> listarProdutos() {
        return produtosRepository.findAll().stream()
                .map(this::toDTO)
                .toList();
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
        produto.setFornecedor_ID(fornecedor);

        return toDTO(produtosRepository.save(produto));
    }

    public ProdutosDTO buscarProduto(Long ID) {
        return toDTO(produtosRepository.findById(ID)
                .orElseThrow(() -> new EntityNotFoundException("produto não encontrado")));

    }

    public ProdutosDTO deletarProdutos(Long ID) {
        Produtos produto = produtosRepository.findById(ID)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));

        produtosRepository.deleteById(ID);

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
            produto.setFornecedor_ID(fornecedor);
        }

        Produtos salvo = produtosRepository.save(produto);

        return toDTO(salvo);
    }

}