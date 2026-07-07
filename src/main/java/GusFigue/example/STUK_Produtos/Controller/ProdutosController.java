package GusFigue.example.STUK_Produtos.Controller;

import GusFigue.example.STUK_Produtos.DTO.ProdutosDTO;
import GusFigue.example.STUK_Produtos.Service.ProdutosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Produtos", description = "Gerenciamento do catálogo de produtos")
@RestController
@RequestMapping("/produtos")
public class ProdutosController {

    @Autowired
    private ProdutosService produtosService;

    @Operation(summary = "Lista produtos paginados")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    @GetMapping
    public ResponseEntity<Page<ProdutosDTO>> listarProdutos(@RequestParam(defaultValue = "0") int pagina,
                                                            @RequestParam(defaultValue = "10") int tamanho) {
        return ResponseEntity.ok(produtosService.listarProdutos(pagina, tamanho));
    }

    @Operation(summary = "Cadastra um novo produto")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Produto criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Fornecedor não encontrado")
    })
    @PostMapping("/cadastrarProduto")
    public ResponseEntity<ProdutosDTO> cadastrarProduto (@Valid @RequestBody  ProdutosDTO produtosDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(produtosService.cadastrarProduto(produtosDTO));
    }

    @Operation(summary = "Busca um produto por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produto encontrado"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @GetMapping("/buscarProduto")
    public ResponseEntity<ProdutosDTO> buscarProduto (@RequestParam Long id) {
        return ResponseEntity.ok(produtosService.buscarProduto(id));
    }

    @Operation(summary = "Remove um produto por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Produto removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteProdutos(@RequestParam Long id) {
        produtosService.deletarProdutos(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Atualiza campos de um produto")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto ou fornecedor não encontrado")
    })
    @PutMapping("/atualizarProduto")
    public ResponseEntity<ProdutosDTO> atualizarProduto(@RequestParam Long id, @RequestBody ProdutosDTO produtosDTO) {
        return ResponseEntity.ok(produtosService.atualizarProdutos(id, produtosDTO));
    }

}
