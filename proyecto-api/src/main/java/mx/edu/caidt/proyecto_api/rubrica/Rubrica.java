package mx.edu.caidt.proyecto_api.rubrica;

import jakarta.persistence.*;
import lombok.*;
import mx.edu.caidt.proyecto_api.docente.Docente;
import mx.edu.caidt.proyecto_api.semestre.Semestre;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Entity
@Table(name="rubrica")
public class Rubrica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRubrica;
    @Column(nullable=false,length=80)

    private String nombre;
    @Column(nullable=false,length=40)

    private String tipo;
    @Column(nullable=false,length=150)

    private String criterio;

    @Column(nullable=false,precision=5,scale=2)

    private Double puntaje;

    @ManyToOne
    @JoinColumn(name="docente_id")
    private Docente docente;

    @ManyToOne
    @JoinColumn(name="semestre_id")
    private Semestre semestre;



}
