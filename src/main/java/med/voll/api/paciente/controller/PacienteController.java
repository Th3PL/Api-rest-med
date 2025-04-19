package med.voll.api.paciente.controller;

import med.voll.api.paciente.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    PacieteRepository repository;

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody DadosCadastroPaciente dados){
        Paciente paciente = new Paciente(dados);
        repository.save(paciente);
    }

    @GetMapping
    public Page<DadosListagemPaciente> listar(@PageableDefault(size=5, sort={"nome"}) Pageable paginacao){
        return repository.findAll(paginacao).map(DadosListagemPaciente::new);
    }

    @PutMapping
    @Transactional
    public void atualizar(@RequestBody DadosAtualizacaoPaciente dados){
        Paciente pacinete = repository.getReferenceById(dados.id());
        pacinete.atualizarDados(dados);


    }

    @DeleteMapping("/{id}")
    @Transactional
    public void remover(@PathVariable Long id){
        var paciente = repository.getReferenceById(id);
        paciente.excluir();
    }
}
