package mx.edu.caidt.proyecto_api.semestre;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import mx.edu.caidt.proyecto_api.rubrica.Rubrica;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Entity
@Table(name="semestre")
public class Semestre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSemestre;
    @Column(nullable=false,length=20)

    private String grado;
    @Column(nullable=false,length=60)

    private String carrera;
    @Column(nullable=false,length=30)

    private String periodo;

    @OneToMany(mappedBy="semestre")
    private List<Rubrica> rubricas;

}
