package GusFigue.example.STUK_Produtos.DTO;

import GusFigue.example.STUK_Produtos.Entity.Canais;


public record ProdutosDTO(Long ID, String descricao, Double valor, Double peso, Boolean ativo, Canais canal) {

}
