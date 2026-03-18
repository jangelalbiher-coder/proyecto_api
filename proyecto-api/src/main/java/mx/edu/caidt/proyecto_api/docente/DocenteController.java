package mx.edu.caidt.proyecto_api.docente;

import mx.edu.caidt.proyecto_api.alumno.Alumno;
import mx.edu.caidt.proyecto_api.alumno.AlumnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/docente")
public class DocenteController {

    @Autowired
    private DocenteRepository docenteRepository;
    @Autowired
    private AlumnoRepository alumnoRepository;

    @GetMapping
    public ResponseEntity<Iterable<Docente>> findAll() {
        return ResponseEntity.ok(docenteRepository.findAll());
    }


    @GetMapping("/{idDocente}")
    public ResponseEntity<Docente> findById(@PathVariable Long idDocente) {
        Optional<Docente> docenteOptional = docenteRepository.findById(idDocente);
        if (docenteOptional.isPresent()) {
            return ResponseEntity.ok(docenteOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping
    public ResponseEntity<Docente> create(@RequestBody Docente docente, UriComponentsBuilder uriBuilder) {
        Optional<Alumno> alumnoOptional = alumnoRepository.findById(docente.getAlumno().getIdAlumno());
        if (!alumnoOptional.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        docente.setAlumno(alumnoOptional.get());
        Docente created = docenteRepository.save(docente);
        URI uri = uriBuilder.path("alumno/{idDocente}").buildAndExpand(created.getIdDocente()).toUri();
        return ResponseEntity.created(uri).body(created);

    }



    @PutMapping("/{idDocente}")
    public ResponseEntity<Void> update(@PathVariable Long idDocente, @RequestBody Docente docente) {
        Optional<Alumno> alumnoOptional = alumnoRepository.findById(docente.getAlumno().getIdAlumno());
        if(!alumnoOptional.isPresent()){
            return ResponseEntity.unprocessableEntity().build();
        }
        Docente docenteAnterior= docenteRepository.findById(idDocente).get();
        if(docenteAnterior!=null){
            docente.setAlumno(alumnoOptional.get());
            docente.setIdDocente(docenteAnterior.getIdDocente());
            docenteRepository.save(docente);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }


    @DeleteMapping("/{idDocente}")
    public ResponseEntity<Void> delete(@PathVariable Long idDocente) {
        if (docenteRepository.findById(idDocente).isPresent()) {
            docenteRepository.deleteById(idDocente);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}