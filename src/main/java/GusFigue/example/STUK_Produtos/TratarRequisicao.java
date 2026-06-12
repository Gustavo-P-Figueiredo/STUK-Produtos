package GusFigue.example.STUK_Produtos;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class TratarRequisicao {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, String>> tratarErro404(EntityNotFoundException ex) {
        Map<String, String> erro = new HashMap<>();
        erro.put("erro", "Informação não encontrada");
        erro.put("detalhes", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> tratarErro400(MethodArgumentNotValidException ex) {
        Map<String, String> erros = new HashMap<>();
        for (FieldError erro : ex.getFieldErrors()) {
            erros.put(erro.getField(), erro.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erros);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> tratarErro500(Exception ex) {
        Map<String, String> erro = new HashMap<>();
        erro.put("erro", "Erro interno do servidor");
        erro.put("detalhes", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> tratarErroNegocio(IllegalArgumentException ex) {
        Map<String, String> erro = new HashMap<>();
        erro.put("erro", "Erro de negócio");
        erro.put("detalhes", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }
}