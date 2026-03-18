package mx.edu.caidt.proyecto_api.rubrica;

import mx.edu.caidt.proyecto_api.alumno.Alumno;
import mx.edu.caidt.proyecto_api.docente.Docente;
import mx.edu.caidt.proyecto_api.docente.DocenteRepository;
import mx.edu.caidt.proyecto_api.semestre.SemestreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/rubrica")
public class RubricaController {

    @Autowired
    private RubricaRepository rubricaRepository;

    @Autowired
    private SemestreRepository semestreRepository;

    @Autowired
    private DocenteRepository docenteRepository;


    @GetMapping
    public ResponseEntity<Iterable<Rubrica>> findAll(){
        return ResponseEntity.ok(rubricaRepository.findAll());
    }


    @GetMapping("/{idRubrica}")
    public ResponseEntity<Rubrica> findById(@PathVariable Long idRubrica) {
        Optional<Rubrica> rubricaOptional = rubricaRepository.findById(idRubrica);
        if (rubricaOptional.isPresent()) {
            return ResponseEntity.ok(rubricaOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping
    public ResponseEntity<Rubrica> create(@RequestBody Rubrica rubrica, UriComponentsBuilder uriBuilder) {
        Optional<Docente> docenteOptional = docenteRepository.findById(rubrica.getDocente().getIdDocente());
        if (!docenteOptional.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        rubrica.setDocente(docenteOptional.get());
        Rubrica created = rubricaRepository.save(rubrica);
        URI uri = uriBuilder.path("rubrica/{idRubrica}").buildAndExpand(created.getIdRubrica()).toUri();
        return ResponseEntity.created(uri).body(created);

    }



    @PutMapping("/{idRubrica}")
    public ResponseEntity<Void> update(@PathVariable Long idRubrica, @RequestBody Rubrica rubrica) {
        Optional<Docente> docenteOptional = docenteRepository.findById(rubrica.getDocente().getIdDocente());
        if(!docenteOptional.isPresent()){
            return ResponseEntity.unprocessableEntity().build();
        }
         Rubrica rubricaAnterior = rubricaRepository.findById(idRubrica).get();
        if(rubricaAnterior!=null){
            rubrica.setDocente(docenteOptional.get());
            rubrica.setIdRubrica(rubricaAnterior.getIdRubrica());
            rubricaRepository.save(rubrica);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }


    @DeleteMapping("/{idRubrica}")
    public ResponseEntity<Void> delete(@PathVariable Long idRubrica) {
        if (docenteRepository.findById(idRubrica).isPresent()) {
            docenteRepository.deleteById(idRubrica);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}












































