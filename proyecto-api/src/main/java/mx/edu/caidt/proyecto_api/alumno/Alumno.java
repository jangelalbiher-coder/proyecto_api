package mx.edu.caidt.proyecto_api.alumno;

import jakarta.persistence.*;
import lombok.*;
import mx.edu.caidt.proyecto_api.docente.Docente;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Entity
@Table(name="alumno")
public class Alumno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAlumno;
    @Column(nullable=false,length=80)

    private String nombre;
    @Column(nullable=false,length=20)

    private String semestre;
    @Column(nullable=false,length=30)

    private String modalidad;
    @Column(nullable=false,length=60)

    private String carrera;

    @ManyToOne
    @JoinColumn(name="idDocente")
    private Docente docente;

    @OneToMany(mappedBy="idAlumno")
    private Alumno alumno;

}
