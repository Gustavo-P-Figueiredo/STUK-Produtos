package GusFigue.example.STUK_Produtos.DTO;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CNPJ;


public record FornecedorDTO(Long id,

                            @NotBlank(message = "Informe uma descrição")
                            String descricao,

                            @NotNull(message = "Informe se o fornecedor está ativo")
                            Boolean ativo,

                            @CNPJ(message = "CNPJ invalido")
                            String cnpj )
{ }


