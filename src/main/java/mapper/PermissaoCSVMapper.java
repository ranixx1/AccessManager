package mapper;

import java.time.LocalTime;

import java.util.*;

import model.Permissao;
import util.CSVReader;

public class PermissaoCSVMapper {
    public static List<Permissao>load(String path) {
        List<Permissao> permis = new ArrayList<>();
        
        for(String[] l : CSVReader.read(path)){
            permis.add(new Permissao(
                Long.parseLong(l[0]),      // id
                Long.parseLong(l[1]),      // usuario_id
                Long.parseLong(l[2]),      // setor_id 
                LocalTime.parse(l[3]),     // horario_inicio
                LocalTime.parse(l[4]),     // horario_fim
                Boolean.parseBoolean(l[5])

            ));
        }
        return permis;
    }
}
