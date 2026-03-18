package mx.edu.caidt.proyecto_api.docente;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface DocenteRepository extends CrudRepository<Docente,Long> {
}
