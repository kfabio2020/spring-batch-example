package fr.fabrice.springbatch.dto;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SuroitCoursesDTO {
    @CsvBindByName(column = "codeStifLigne")
    private String codeStifLigne;

    @CsvBindByName(column = "antenne")
    private String antenne;

    @CsvBindByName(column = "idtm")
    private String idtm;

    @CsvBindByName(column = "idmiss")
    private String idmiss;

    @CsvBindByName(column = "course")
    private String course;

    @CsvBindByName(column = "nommiss")
    private String nommiss;

    @CsvBindByName(column = "sens")
    private String sens;

    @CsvBindByName(column = "numseq")
    private String numseq;

    @CsvBindByName(column = "hrpass")
    private String hrpass;

    @CsvBindByName(column = "idptar")
    private String idptar;

    @CsvBindByName(column = "zdep")
    private String zdep;
}
