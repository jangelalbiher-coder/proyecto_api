package mx.edu.caidt.proyecto_api.docente;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import mx.edu.caidt.proyecto_api.alumno.Alumno;
import mx.edu.caidt.proyecto_api.rubrica.Rubrica;
import mx.edu.caidt.proyecto_api.sistema_web.SistemaWeb;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Entity
@Table(name="docente")
public class Docente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDocente;
    @Column(nullable=false,length=80)

    private String nombre;
    @Column(nullable=false,length=60)

    private String asignatura;
    @Column(nullable=false,length=20)

    private String grupo;
    @Column(nullable=false,length=80,unique=true)

    private String correo;
    @Column(nullable=false,length=60)

    private String carrera;

    @ManyToOne
    @JoinColumn(name="sistema_id")
    private SistemaWeb sistemaWeb;

    @JoinColumn(name="docente")
    private Alumno alumno;

    @OneToMany(mappedBy="docente")
    private List<Rubrica> rubricas;

}