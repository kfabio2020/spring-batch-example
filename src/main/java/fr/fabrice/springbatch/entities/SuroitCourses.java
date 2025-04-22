package fr.fabrice.springbatch.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Table(name = "suroit_courses")
public class SuroitCourses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String codeStifLigne;

    @Column
    private String antenne;

    @Column
    private String idtm;

    @Column
    private String idmiss;

    @Column
    private String course;

    @Column
    private String nommiss;

    @Column
    private String sens;

    @Column
    private String numseq;

    @Column
    private String hrpass;

    @Column
    private String idptar;

    @Column
    private String zdep;
}


