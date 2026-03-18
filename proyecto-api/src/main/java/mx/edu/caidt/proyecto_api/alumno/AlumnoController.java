package mx.edu.caidt.proyecto_api.alumno;

import mx.edu.caidt.proyecto_api.docente.Docente;
import mx.edu.caidt.proyecto_api.docente.DocenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/alumno")
public class AlumnoController {

    @Autowired
    private AlumnoRepository alumnoRepository;
    @Autowired
    private DocenteRepository DocenteRepository;

    @GetMapping
    public ResponseEntity<Iterable<Alumno>> findAll(){

        return ResponseEntity.ok(alumnoRepository.findAll());

    }

    @GetMapping("/{idAlumno}")
    public ResponseEntity<Alumno> findById(@PathVariable Long idAlumno){

        Optional<Alumno> alumnoOpcional= alumnoRepository.findById(idAlumno);
        if(alumnoOpcional.isPresent()){
            return ResponseEntity.ok(alumnoOpcional.get());
        }else{
        return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Alumno> create(@RequestBody Alumno alumno, UriComponentsBuilder uriBuilder){
        Optional<Docente> docenteOptional = DocenteRepository.findById(alumno.getDocente().getIdDocente());
        if (!docenteOptional.isPresent()){
            return ResponseEntity.unprocessableEntity().build();
        }
        alumno.setDocente(docenteOptional.get());
        Alumno created = alumnoRepository.save(alumno);
        URI uri = uriBuilder.path("alumno/{idAlumno}").buildAndExpand(created.getIdAlumno()).toUri();
        return ResponseEntity.created(uri).body(created);

    }

    @PutMapping("/{idAlumno}")
    public ResponseEntity<Void> update(@PathVariable Long idAlumno,@RequestBody Alumno alumno){
        Optional<Docente> docenteOptional = DocenteRepository.findById(alumno.getDocente().getIdDocente());

        if(!docenteOptional.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        Alumno alumnoAnterior = alumnoRepository.findById(idAlumno).get();
        if(alumnoAnterior != null) {
            alumno.setDocente(docenteOptional.get());
            alumno.setIdAlumno(alumnoAnterior.getIdAlumno());
            alumnoRepository.save(alumno);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();

    }

    @DeleteMapping("/{idAlumno}")
    public ResponseEntity<Void> delete(@PathVariable Long idAlumno) {
        if (alumnoRepository.findById(idAlumno).isPresent()) {
            alumnoRepository.deleteById(idAlumno);
            return ResponseEntity.noContent().build();

        }
        return ResponseEntity.notFound().build();

    }
}
