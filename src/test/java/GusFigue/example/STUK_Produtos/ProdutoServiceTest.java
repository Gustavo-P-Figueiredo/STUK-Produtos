package GusFigue.example.STUK_Produtos;

import GusFigue.example.STUK_Produtos.DTO.ProdutosDTO;
import GusFigue.example.STUK_Produtos.Entity.Canais;
import GusFigue.example.STUK_Produtos.Entity.Fornecedor;
import GusFigue.example.STUK_Produtos.Entity.Produtos;
import GusFigue.example.STUK_Produtos.Repository.FornecedorRepository;
import GusFigue.example.STUK_Produtos.Repository.ProdutosRepository;
import GusFigue.example.STUK_Produtos.Service.ProdutosService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProdutosServiceTest {

    @Mock
    private ProdutosRepository produtosRepository;

    @Mock
    private FornecedorRepository fornecedorRepository;

    @InjectMocks
    private ProdutosService produtosService;

    private Fornecedor fornecedor;
    private Produtos produto;
    private ProdutosDTO produtoDTO;

    @BeforeEach
    void setUp() {
        fornecedor = new Fornecedor();
        fornecedor.setId(1L);
        fornecedor.setDescricao("Fornecedor Teste");
        fornecedor.setAtivo(true);
        fornecedor.setCnpj("12345678000199");

        produto = new Produtos();
        produto.setId(1L);
        produto.setDescricao("Produto Teste");
        produto.setValor(100.0);
        produto.setPeso(2.5);
        produto.setAtivo(true);
        produto.setCanal(Canais.ECOMMERCE);
        produto.setFornecedor(fornecedor);

        produtoDTO = new ProdutosDTO(
                1L, "Produto Teste", 100.0, 2.5, true, Canais.ECOMMERCE, 1L
        );
    }

    // ---------- cadastrarProduto ----------

    @Test
    void cadastrarProduto_deveSalvarComSucesso() {
        when(fornecedorRepository.findById(1L)).thenReturn(Optional.of(fornecedor));
        when(produtosRepository.save(any(Produtos.class))).thenReturn(produto);

        ProdutosDTO resultado = produtosService.cadastrarProduto(produtoDTO);

        assertNotNull(resultado);
        assertEquals("Produto Teste", resultado.descricao());
        assertEquals(1L, resultado.fornecedorId());
        verify(produtosRepository, times(1)).save(any(Produtos.class));
    }

    @Test
    void cadastrarProduto_deveLancarExcecaoQuandoFornecedorNaoExiste() {
        when(fornecedorRepository.findById(99L)).thenReturn(Optional.empty());

        ProdutosDTO dtoComFornecedorInvalido = new ProdutosDTO(
                null, "Produto X", 50.0, 1.0, true, Canais.LOJA_FISICA, 99L
        );

        assertThrows(EntityNotFoundException.class,
                () -> produtosService.cadastrarProduto(dtoComFornecedorInvalido));

        verify(produtosRepository, never()).save(any());
    }

    // ---------- buscarProduto ----------

    @Test
    void buscarProduto_deveRetornarQuandoExiste() {
        when(produtosRepository.findById(1L)).thenReturn(Optional.of(produto));

        ProdutosDTO resultado = produtosService.buscarProduto(1L);

        assertEquals(1L, resultado.id());
        assertEquals("Produto Teste", resultado.descricao());
    }

    @Test
    void buscarProduto_deveLancarExcecaoQuandoNaoExiste() {
        when(produtosRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> produtosService.buscarProduto(99L));
    }

    // ---------- listarProdutos ----------

    @Test
    void listarProdutos_deveRetornarPaginaDeDTOs() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Produtos> paginaProdutos = new PageImpl<>(List.of(produto));

        when(produtosRepository.findAll(any(Pageable.class))).thenReturn(paginaProdutos);

        Page<ProdutosDTO> resultado = produtosService.listarProdutos(0, 10);

        assertEquals(1, resultado.getTotalElements());
        assertEquals("Produto Teste", resultado.getContent().get(0).descricao());
    }

    // ---------- deletarProdutos ----------

    @Test
    void deletarProdutos_deveDeletarComSucesso() {
        when(produtosRepository.findById(1L)).thenReturn(Optional.of(produto));
        doNothing().when(produtosRepository).deleteById(1L);

        ProdutosDTO resultado = produtosService.deletarProdutos(1L);

        assertEquals(1L, resultado.id());
        verify(produtosRepository, times(1)).deleteById(1L);
    }

    @Test
    void deletarProdutos_deveLancarExcecaoQuandoNaoExiste() {
        when(produtosRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> produtosService.deletarProdutos(99L));

        verify(produtosRepository, never()).deleteById(any());
    }

    // ---------- atualizarProdutos ----------

    @Test
    void atualizarProdutos_deveAtualizarSomenteCamposNaoNulos() {
        when(produtosRepository.findById(1L)).thenReturn(Optional.of(produto));
        when(produtosRepository.save(any(Produtos.class))).thenReturn(produto);

        ProdutosDTO dtoParcial = new ProdutosDTO(
                null, "Novo Nome", null, null, null, null, null
        );

        produtosService.atualizarProdutos(1L, dtoParcial);

        assertEquals("Novo Nome", produto.getDescricao());
        // campos não enviados permanecem inalterados
        assertEquals(100.0, produto.getValor());
        assertEquals(2.5, produto.getPeso());
        verify(fornecedorRepository, never()).findById(any());
    }

    @Test
    void atualizarProdutos_deveTrocarFornecedorQuandoInformado() {
        Fornecedor novoFornecedor = new Fornecedor();
        novoFornecedor.setId(2L);

        when(produtosRepository.findById(1L)).thenReturn(Optional.of(produto));
        when(fornecedorRepository.findById(2L)).thenReturn(Optional.of(novoFornecedor));
        when(produtosRepository.save(any(Produtos.class))).thenReturn(produto);

        ProdutosDTO dtoComNovoFornecedor = new ProdutosDTO(
                null, null, null, null, null, null, 2L
        );

        produtosService.atualizarProdutos(1L, dtoComNovoFornecedor);

        assertEquals(2L, produto.getFornecedor().getId());
    }

    @Test
    void atualizarProdutos_deveLancarExcecaoQuandoFornecedorNovoNaoExiste() {
        when(produtosRepository.findById(1L)).thenReturn(Optional.of(produto));
        when(fornecedorRepository.findById(99L)).thenReturn(Optional.empty());

        ProdutosDTO dtoComFornecedorInvalido = new ProdutosDTO(
                null, null, null, null, null, null, 99L
        );

        assertThrows(EntityNotFoundException.class,
                () -> produtosService.atualizarProdutos(1L, dtoComFornecedorInvalido));

        verify(produtosRepository, never()).save(any());
    }

    @Test
    void atualizarProdutos_deveLancarExcecaoQuandoProdutoNaoExiste() {
        when(produtosRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> produtosService.atualizarProdutos(99L, produtoDTO));
    }
}