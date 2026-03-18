package mx.edu.caidt.proyecto_api.sistema_web;

import jakarta.persistence.*;
import lombok.*;
import mx.edu.caidt.proyecto_api.docente.Docente;
import mx.edu.caidt.proyecto_api.rubrica.Rubrica;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Entity
@Table(name="sistema_web")
public class SistemaWeb {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSistema;
    @Column(nullable=false,length=80)

    private String nombre;
    @Column(nullable=false,length=40)

    private String plataforma;
    @Column(nullable=false,length=20)

    private String fechaDeCreacion;

    @OneToMany(mappedBy="sistema")
    private  List<Docente> docentes;

}