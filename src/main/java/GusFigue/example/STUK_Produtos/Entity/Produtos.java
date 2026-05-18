package GusFigue.example.STUK_Produtos.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity (name = "Produtos")
@Table (name = "Produtos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Produtos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    private String descricao;

    private Double valor;

    private Double peso;

    private Boolean ativo;

    @Enumerated(EnumType.STRING)
    private Canais canal;
}
