package GusFigue.example.STUK_Produtos.DTO;

import GusFigue.example.STUK_Produtos.Entity.Canais;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;


public record ProdutosDTO(Long id,
                          @NotBlank(message = "Informe uma descrição")
                          String descricao,

                          @NotNull(message = "Informe o valor do produto")
                          @Positive(message = "O valor deve ser maior que zero")
                          Double valor,

                          @NotNull(message = "Informe o peso do produto")
                          @Positive(message = "O peso deve ser maior que zero")
                          Double peso,

                          @NotNull(message = "Informe se o produto está ativo")
                          Boolean ativo,

                          @NotNull(message = "Informe o canal de venda do produto")
                          Canais canal,

                          @NotNull(message = "Produto deve ter um fornecedor")
                          Long fornecedorId)
{}
