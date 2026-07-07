package GusFigue.example.STUK_Produtos.Controller;

import GusFigue.example.STUK_Produtos.DTO.FornecedorDTO;
import GusFigue.example.STUK_Produtos.Service.FornecedorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Fornecedor", description = "Gerenciamento de fornecedores")
@RestController
@Validated
@RequestMapping("/fornecedor")
public class FornecedorController {
    @Autowired
    FornecedorService fornecedorService;

    @Operation(summary = "Cadastra um novo fornecedor")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Fornecedor criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos (ex: CNPJ inválido)")
    })
    @PostMapping("/cadastrar")
    public ResponseEntity<FornecedorDTO> cadastrarFornecedor(@Valid @RequestBody FornecedorDTO fornecedorDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(fornecedorService.cadastrarFornecedor(fornecedorDTO));
    }

    @Operation(summary = "Lista fornecedores paginados")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    @GetMapping
    public ResponseEntity<Page<FornecedorDTO>> listarForncedores(@RequestParam(defaultValue = "0") int pagina,
                                                                 @RequestParam(defaultValue = "10") int tamanho) {
        return ResponseEntity.ok(fornecedorService.listarFornecedores(pagina, tamanho));
    }

    @Operation(summary = "Busca um fornecedor por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Fornecedor encontrado"),
            @ApiResponse(responseCode = "404", description = "Fornecedor não encontrado")
    })
    @GetMapping("/buscarFornecedor")
    public ResponseEntity<FornecedorDTO> buscarFornecedorPorId(@RequestParam Long id) {
        return ResponseEntity.ok(fornecedorService.buscarFornecedorPorId(id));
    }

    @Operation(summary = "Remove um fornecedor por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Fornecedor removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Fornecedor não encontrado")
    })
    @DeleteMapping("/delete")
    public ResponseEntity<String> deletarPorId(@RequestParam Long id) {
        fornecedorService.deletarPorId(id);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Atualiza campos de um fornecedor (atualização parcial)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Fornecedor atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Fornecedor não encontrado")
    })
    @PutMapping("/atualizarFornecedor")
    public ResponseEntity<FornecedorDTO> atualizarFornecedor(@Valid @RequestParam Long id, @RequestBody FornecedorDTO fornecedores) {
        fornecedorService.atualizarFornecedor(id, fornecedores);

        return ResponseEntity.ok(fornecedorService.atualizarFornecedor(id, fornecedores));
    }
}
