package GusFigue.example.STUK_Produtos.Service;

import GusFigue.example.STUK_Produtos.DTO.FornecedorDTO;
import GusFigue.example.STUK_Produtos.Entity.Fornecedor;
import GusFigue.example.STUK_Produtos.Entity.Produtos;
import GusFigue.example.STUK_Produtos.Repository.FornecedorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FornecedorService {

    @Autowired
    private FornecedorRepository fornecedorRepository;

    private FornecedorDTO toDTO(Fornecedor f) {
        return new FornecedorDTO(
                f.getID(),
                f.getDescricao(),
                f.getAtivo(),
                f.getCnpj()
        );
    }

    public List<FornecedorDTO> listarFornecedores() {
        return fornecedorRepository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    public FornecedorDTO buscarFornecedorPorId(Long ID){
        return toDTO(fornecedorRepository.findById(ID)
                .orElseThrow(() -> new  EntityNotFoundException("Fornecedor não encontrado")));
    }

    public FornecedorDTO cadastrarFornecedor(FornecedorDTO dto) {
        Fornecedor fornecedor = new Fornecedor();

        fornecedor.setDescricao(dto.descricao());
        fornecedor.setAtivo(dto.ativo());
        fornecedor.setCnpj(dto.cnpj());

        return toDTO(fornecedorRepository.save(fornecedor));
    }

    public FornecedorDTO deletarPorId(Long ID) {
        Fornecedor fornecedor = fornecedorRepository.findById(ID)
                .orElseThrow(() -> new EntityNotFoundException("Fornecedor não encontrado"));

         fornecedorRepository.deleteById(ID);

        return toDTO(fornecedor);
    }

    public FornecedorDTO atualizarFornecedor (Long ID, FornecedorDTO dto) {
        Fornecedor fornecedor = fornecedorRepository.findById(ID)
                .orElseThrow(() -> new EntityNotFoundException("Fornecedor não encontrado"));

        if (dto.descricao() != null) fornecedor.setDescricao(dto.descricao());

        if (dto.cnpj() != null) fornecedor.setCnpj(dto.cnpj());

        if (dto.ativo() != null) fornecedor.setAtivo(dto.ativo());

        Fornecedor salvo = fornecedorRepository.save(fornecedor);

        return toDTO(salvo);
    }

}
