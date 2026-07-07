package GusFigue.example.STUK_Produtos.Service;

import GusFigue.example.STUK_Produtos.DTO.FornecedorDTO;
import GusFigue.example.STUK_Produtos.Entity.Fornecedor;
import GusFigue.example.STUK_Produtos.Repository.FornecedorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class FornecedorService {

    @Autowired
    private FornecedorRepository fornecedorRepository;

    private FornecedorDTO toDTO(Fornecedor f) {
        return new FornecedorDTO(
                f.getId(),
                f.getDescricao(),
                f.getAtivo(),
                f.getCnpj()
        );
    }

    public Page<FornecedorDTO> listarFornecedores(int numeroPagina, int tamanho) {
        Pageable pageable = PageRequest.of(numeroPagina, tamanho);
        return fornecedorRepository.findAll(pageable)
                .map(this::toDTO);
    }

    public FornecedorDTO buscarFornecedorPorId(Long id){
        return toDTO(fornecedorRepository.findById(id)
                .orElseThrow(() -> new  EntityNotFoundException("Fornecedor não encontrado")));
    }

    public FornecedorDTO cadastrarFornecedor(FornecedorDTO dto) {
        Fornecedor fornecedor = new Fornecedor();

        fornecedor.setDescricao(dto.descricao());
        fornecedor.setAtivo(dto.ativo());
        fornecedor.setCnpj(dto.cnpj());

        return toDTO(fornecedorRepository.save(fornecedor));
    }

    public FornecedorDTO deletarPorId(Long id) {
        Fornecedor fornecedor = fornecedorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Fornecedor não encontrado"));

         fornecedorRepository.deleteById(id);

        return toDTO(fornecedor);
    }

    public FornecedorDTO atualizarFornecedor (Long id, FornecedorDTO dto) {
        Fornecedor fornecedor = fornecedorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Fornecedor não encontrado"));

        if (dto.descricao() != null) fornecedor.setDescricao(dto.descricao());

        if (dto.cnpj() != null) fornecedor.setCnpj(dto.cnpj());

        if (dto.ativo() != null) fornecedor.setAtivo(dto.ativo());

        Fornecedor salvo = fornecedorRepository.save(fornecedor);

        return toDTO(salvo);
    }

}
