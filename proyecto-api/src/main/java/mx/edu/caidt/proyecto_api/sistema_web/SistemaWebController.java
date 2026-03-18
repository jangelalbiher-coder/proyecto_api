package mx.edu.caidt.proyecto_api.sistema_web;

import mx.edu.caidt.proyecto_api.docente.Docente;
import mx.edu.caidt.proyecto_api.docente.DocenteRepository;
import mx.edu.caidt.proyecto_api.rubrica.Rubrica;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/sistema")
public class SistemaWebController {

    @Autowired
    private SistemaWebRepository sistemaRepository;

    @Autowired
    private DocenteRepository docenteRepository;


        @GetMapping
        public ResponseEntity<Iterable<SistemaWeb>> findAll(){

            return ResponseEntity.ok(sistemaRepository.findAll());
        }


        @GetMapping("/{idSistema}")
        public ResponseEntity<SistemaWeb> findById(@PathVariable Long idSistema){
            Optional<SistemaWeb> sistemaOptional = sistemaRepository.findById(idSistema);
            if(sistemaOptional.isPresent()){

                return ResponseEntity.ok(sistemaOptional.get());
            }

            return ResponseEntity.notFound().build();
        }


        @PostMapping
        public ResponseEntity<SistemaWeb> create(@RequestBody SistemaWeb sistema, UriComponentsBuilder uriBuilder){
            Optional<Docente> docenteOptional = docenteRepository.findById(sistema.getDocentes().get(0).getIdDocente());
            if(!docenteOptional.isPresent()){
                return ResponseEntity.unprocessableEntity().build();
            }
            sistema.getDocentes().get(0).setSistemaWeb(sistema);
            SistemaWeb created = sistemaRepository.save(sistema);
            URI uri = uriBuilder.path("sistema/{idSistema}").buildAndExpand(created.getIdSistema()).toUri();

            return ResponseEntity.created(uri).body(created);

        }


        @PutMapping("/{idSistema}")
        public ResponseEntity<Void> update(@PathVariable Long idSistema, @RequestBody SistemaWeb sistema){
            Optional<Docente> docenteOptional = docenteRepository.findById(sistema.getDocentes().get(0).getIdDocente());
            if(!docenteOptional.isPresent()){
                return ResponseEntity.unprocessableEntity().build();
            }
            SistemaWeb sistemaAnterior = sistemaRepository.findById(idSistema).get();
            if(sistemaAnterior!=null){
                sistema.getDocentes().get(0).setSistemaWeb(sistema);
                sistema.setIdSistema(sistemaAnterior.getIdSistema());
                sistemaRepository.save(sistema);
                return ResponseEntity.ok().build();
            }

            return ResponseEntity.notFound().build();
        }


        @DeleteMapping("/{idSistema}")
        public ResponseEntity<Void> delete(@PathVariable Long idSistema){
            if(sistemaRepository.findById(idSistema).isPresent()){
                sistemaRepository.deleteById(idSistema);
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.notFound().build();
        }

    }





