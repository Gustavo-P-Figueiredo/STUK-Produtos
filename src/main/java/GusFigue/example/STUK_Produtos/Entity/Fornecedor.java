package GusFigue.example.STUK_Produtos.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity (name = "Fornecedor")
@Table(name = "Fornecedor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Fornecedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;

    private Boolean ativo;

    private String cnpj;

    @OneToMany(mappedBy = "fornecedor",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Produtos> produtos;
}
