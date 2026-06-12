package GusFigue.example.STUK_Produtos.Controller;

import GusFigue.example.STUK_Produtos.DTO.FornecedorDTO;
import GusFigue.example.STUK_Produtos.Service.FornecedorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fornecedor")
public class FornecedorController {
    @Autowired
    FornecedorService fornecedorService;

    @PostMapping("/cadastrar")
    public FornecedorDTO cadastrarFornecedor(@Valid @RequestBody FornecedorDTO fornecedorDTO) {
        return fornecedorService.cadastrarFornecedor(fornecedorDTO);
    }

    @GetMapping
    public ResponseEntity<List<FornecedorDTO>> listarForncedores() {
        return ResponseEntity.ok(fornecedorService.listarFornecedores());
    }

    @GetMapping("/buscarFornecedor")
    public FornecedorDTO buscarFornecedorPorId(@Valid @RequestParam Long ID) {
        return fornecedorService.buscarFornecedorPorId(ID);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deletarPorId(@Valid @RequestParam Long ID) {
        FornecedorDTO fornecedores = fornecedorService.deletarPorId(ID);

        return ResponseEntity.ok(fornecedores + " Deletado");
    }

    @PutMapping("/AtualizarFornecedor")
    public ResponseEntity<String> atualizarFornecedor(@Valid @RequestParam Long ID, @RequestBody FornecedorDTO fornecedores) {
        fornecedorService.atualizarFornecedor(ID, fornecedores);

        return ResponseEntity.ok(fornecedores + "Atualizado");
    }
}
