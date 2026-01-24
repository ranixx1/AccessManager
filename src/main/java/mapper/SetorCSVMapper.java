package mapper;

import java.util.*;

import model.Setor;
import util.CSVReader;

public class SetorCSVMapper {

    public static List<Setor> load(String path) {
        List<Setor> setores = new ArrayList<>();

        for (String[] l : CSVReader.read(path)) {
            setores.add(new Setor(
                Long.parseLong(l[0]),
                l[1]
            ));
        }

        return setores;
    }
}
