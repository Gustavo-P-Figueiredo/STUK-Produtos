package GusFigue.example.STUK_Produtos.Repository;

import GusFigue.example.STUK_Produtos.Entity.Produtos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProdutosRepository extends JpaRepository<Produtos, Long> {

    Optional<Produtos> findByDescricao(String descricao);
}

