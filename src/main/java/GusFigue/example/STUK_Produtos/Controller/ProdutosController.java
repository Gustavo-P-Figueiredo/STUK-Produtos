package GusFigue.example.STUK_Produtos.Controller;

import GusFigue.example.STUK_Produtos.DTO.ProdutosDTO;
import GusFigue.example.STUK_Produtos.Service.ProdutosService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutosController {

    @Autowired
    private ProdutosService produtosService;

    @GetMapping
    public ResponseEntity<List<ProdutosDTO>> listarProdutos() {
        return ResponseEntity.ok(produtosService.listarProdutos());
    }

    @PostMapping("/cadastrarProduto")
    public ProdutosDTO cadastrarProduto (@Valid @RequestBody  ProdutosDTO produtosDTO) {
        return produtosService.cadastrarProduto(produtosDTO);
    }

    @GetMapping("/buscarProduto")
    public ProdutosDTO buscarProduto (@Valid @RequestParam Long ID) {
        return produtosService.buscarProduto(ID);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteProdutos(@Valid @RequestParam Long ID) {
        ProdutosDTO produtos = produtosService.deletarProdutos(ID);

        return ResponseEntity.ok(produtos + " deletado");
    }

    @PutMapping("/atualizarProduto")
    public ResponseEntity<String> atualizarProduto(@Valid @RequestBody ProdutosDTO produtos, @RequestParam Long ID) {
        produtosService.atualizarProdutos(ID, produtos);

        return ResponseEntity.ok("Dados atualizado com sucesso!");
    }

}
