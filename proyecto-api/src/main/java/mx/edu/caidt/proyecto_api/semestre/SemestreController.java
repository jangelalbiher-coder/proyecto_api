package mx.edu.caidt.proyecto_api.semestre;

import mx.edu.caidt.proyecto_api.docente.Docente;
import mx.edu.caidt.proyecto_api.rubrica.Rubrica;
import mx.edu.caidt.proyecto_api.rubrica.RubricaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/semestre")
public class SemestreController {

        @Autowired
        private SemestreRepository semestreRepository;

        @Autowired
        private RubricaRepository rubricaRepository;


        @GetMapping
        public ResponseEntity<Iterable<Semestre>> findAll(){

            return ResponseEntity.ok(semestreRepository.findAll());
        }


        @GetMapping("/{idSemestre}")
        public ResponseEntity<Semestre> findById(@PathVariable Long idSemestre){
            Optional<Semestre> semestreOptional = semestreRepository.findById(idSemestre);
            if(semestreOptional.isPresent()){

                return ResponseEntity.ok(semestreOptional.get());
            }

            return ResponseEntity.notFound().build();
        }


        @PostMapping
        public ResponseEntity<Semestre> create(@RequestBody Semestre semestre, UriComponentsBuilder uriBuilder){
            Optional<Rubrica> rubricaOptional = rubricaRepository.findById(semestre.getRubricas().get(0).getIdRubrica());
            if(!rubricaOptional.isPresent()){
                return ResponseEntity.unprocessableEntity().build();
            }
            semestre.getRubricas().get(0).setSemestre(semestre);
            Semestre created = semestreRepository.save(semestre);
            URI uri = uriBuilder.path("semestre/{idSemestre}").buildAndExpand(created.getIdSemestre()).toUri();
            return ResponseEntity.created(uri).body(created);

        }


        @PutMapping("/{idSemestre}")
        public ResponseEntity<Void> update(@PathVariable Long idSemestre, @RequestBody Semestre semestre){
            Optional<Rubrica> rubricaOptional = rubricaRepository.findById(semestre.getRubricas().get(0).getIdRubrica());
            if(!rubricaOptional.isPresent()){
                return ResponseEntity.unprocessableEntity().build();
            }
            Semestre semestreAnterior = semestreRepository.findById(idSemestre).get();
            if(semestreAnterior!=null){
                semestre.getRubricas().get(0).setSemestre(semestre);
                semestre.setIdSemestre(semestreAnterior.getIdSemestre());
                semestreRepository.save(semestre);
                return ResponseEntity.ok().build();
            }

            return ResponseEntity.notFound().build();
        }


        @DeleteMapping("/{idSemestre}")
        public ResponseEntity<Void> delete(@PathVariable Long idSemestre){

            if(semestreRepository.findById(idSemestre).isPresent()){

                semestreRepository.deleteById(idSemestre);

                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.notFound().build();
        }

    }


