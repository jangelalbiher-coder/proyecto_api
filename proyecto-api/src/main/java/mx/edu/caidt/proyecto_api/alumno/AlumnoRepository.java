package mx.edu.caidt.proyecto_api.alumno;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface AlumnoRepository extends CrudRepository <Alumno,Long> {

}
