package GusFigue.example.STUK_Produtos;

import GusFigue.example.STUK_Produtos.DTO.FornecedorDTO;
import GusFigue.example.STUK_Produtos.Entity.Fornecedor;
import GusFigue.example.STUK_Produtos.Repository.FornecedorRepository;
import GusFigue.example.STUK_Produtos.Service.FornecedorService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FornecedorServiceTest {

    @Mock
    private FornecedorRepository fornecedorRepository;

    @InjectMocks
    private FornecedorService fornecedorService;

    private Fornecedor fornecedor;
    private FornecedorDTO fornecedorDTO;

    @BeforeEach
    void setUp() {
        fornecedor = new Fornecedor();
        fornecedor.setId(1L);
        fornecedor.setDescricao("Fornecedor Teste");
        fornecedor.setAtivo(true);
        fornecedor.setCnpj("12345678000199");

        fornecedorDTO = new FornecedorDTO(1L, "Fornecedor Teste", true, "12345678000199");
    }

    // ---------- cadastrarFornecedor ----------

    @Test
    void cadastrarFornecedor_deveSalvarComSucesso() {
        when(fornecedorRepository.save(any(Fornecedor.class))).thenReturn(fornecedor);

        FornecedorDTO resultado = fornecedorService.cadastrarFornecedor(fornecedorDTO);

        assertNotNull(resultado);
        assertEquals("Fornecedor Teste", resultado.descricao());
        assertEquals("12345678000199", resultado.cnpj());
        verify(fornecedorRepository, times(1)).save(any(Fornecedor.class));
    }

    // ---------- buscarFornecedorPorId ----------

    @Test
    void buscarFornecedorPorId_deveRetornarQuandoExiste() {
        when(fornecedorRepository.findById(1L)).thenReturn(Optional.of(fornecedor));

        FornecedorDTO resultado = fornecedorService.buscarFornecedorPorId(1L);

        assertEquals(1L, resultado.id());
        assertEquals("Fornecedor Teste", resultado.descricao());
    }

    @Test
    void buscarFornecedorPorId_deveLancarExcecaoQuandoNaoExiste() {
        when(fornecedorRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> fornecedorService.buscarFornecedorPorId(99L));
    }

    // ---------- listarFornecedores ----------

    @Test
    void listarFornecedores_deveRetornarPaginaDeDTOs() {
        Page<Fornecedor> paginaFornecedores = new PageImpl<>(List.of(fornecedor));

        when(fornecedorRepository.findAll(any(Pageable.class))).thenReturn(paginaFornecedores);

        Page<FornecedorDTO> resultado = fornecedorService.listarFornecedores(0, 10);

        assertEquals(1, resultado.getTotalElements());
        assertEquals("Fornecedor Teste", resultado.getContent().get(0).descricao());
    }

    // ---------- deletarPorId ----------

    @Test
    void deletarPorId_deveDeletarComSucesso() {
        when(fornecedorRepository.findById(1L)).thenReturn(Optional.of(fornecedor));
        doNothing().when(fornecedorRepository).deleteById(1L);

        FornecedorDTO resultado = fornecedorService.deletarPorId(1L);

        assertEquals(1L, resultado.id());
        verify(fornecedorRepository, times(1)).deleteById(1L);
    }

    @Test
    void deletarPorId_deveLancarExcecaoQuandoNaoExiste() {
        when(fornecedorRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> fornecedorService.deletarPorId(99L));

        verify(fornecedorRepository, never()).deleteById(any());
    }

    // ---------- atualizarFornecedor ----------

    @Test
    void atualizarFornecedor_deveAtualizarSomenteCamposNaoNulos() {
        when(fornecedorRepository.findById(1L)).thenReturn(Optional.of(fornecedor));
        when(fornecedorRepository.save(any(Fornecedor.class))).thenReturn(fornecedor);

        FornecedorDTO dtoParcial = new FornecedorDTO(null, "Novo Nome", null, null);

        fornecedorService.atualizarFornecedor(1L, dtoParcial);

        assertEquals("Novo Nome", fornecedor.getDescricao());
        // campos não enviados permanecem inalterados
        assertEquals(true, fornecedor.getAtivo());
        assertEquals("12345678000199", fornecedor.getCnpj());
    }

    @Test
    void atualizarFornecedor_deveLancarExcecaoQuandoNaoExiste() {
        when(fornecedorRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> fornecedorService.atualizarFornecedor(99L, fornecedorDTO));

        verify(fornecedorRepository, never()).save(any());
    }
}