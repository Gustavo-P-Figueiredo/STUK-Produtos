package GusFigue.example.STUK_Produtos.Controller;

import GusFigue.example.STUK_Produtos.DTO.ProdutosDTO;
import GusFigue.example.STUK_Produtos.Entity.Produtos;
import GusFigue.example.STUK_Produtos.Service.ProdutosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/produtos")
public class ProdutosContoller {

    @Autowired
    private ProdutosService produtosService;

    @GetMapping
    public ResponseEntity<List<ProdutosDTO>> mostrarProdutos() {
        List<Produtos> produtos = produtosService.listarProdutos();

        List<ProdutosDTO> listaProdutos = produtos.stream()
                .map(u -> new ProdutosDTO(
                        u.getID(),
                        u.getDescricao(),
                        u.getValor(),
                        u.getPeso(),
                        u.getAtivo(),
                        u.getCanal()
                ))
                .toList();
        return ResponseEntity.ok(listaProdutos);
    }

    @PostMapping("/cadastrarProduto")
    private ProdutosDTO cadastrarProduto (@RequestBody ProdutosDTO produtosDTO) {
        return produtosService.cadastrarProduto(produtosDTO);
    }

    @GetMapping("/buscarProduto")
    private Optional<Produtos> buscarProduto (@RequestParam Long ID) {
        return produtosService.buscarProduto(ID);
    }

    @DeleteMapping
    @RequestMapping("/delete")
    public ResponseEntity<String> deleteProdutos(@RequestParam Long ID) {
        Produtos produtos = produtosService.deletarProdutos(ID);

        ProdutosDTO dto = new ProdutosDTO(
                produtos.getID(),
                produtos.getDescricao(),
                produtos.getValor(),
                produtos.getPeso(),
                produtos.getAtivo(),
                produtos.getCanal()
        );

        return ResponseEntity.ok(dto + " deletado");
    }

    @PutMapping
    @RequestMapping("/atualizarUsuario")
    public ResponseEntity<String> atualizarUsuario(@RequestBody ProdutosDTO produtos, @RequestParam Long ID) {
        produtosService.atualizarProdutos(ID, produtos);

        return ResponseEntity.ok("Dados atualizado com sucesso!");
    }

}
