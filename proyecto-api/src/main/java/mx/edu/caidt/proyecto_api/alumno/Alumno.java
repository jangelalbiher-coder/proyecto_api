package mx.edu.caidt.proyecto_api.alumno;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import mx.edu.caidt.proyecto_api.docente.Docente;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Entity
@Table(name = "alumno")

public class Alumno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private int semestre;
    private String modalidad;
    private String carrera;

    @OneToMany
    @JoinColumn(name = "id" )
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Docente docente;


}
