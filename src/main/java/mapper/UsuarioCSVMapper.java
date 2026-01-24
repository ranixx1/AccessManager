package mapper;

import enums.Role;
import model.Usuario;
import util.CSVReader;

import java.util.*;

public class UsuarioCSVMapper {
    public static List<Usuario> load(String path) {
        List<Usuario> usuarios = new ArrayList<>();

        for (String[] l : CSVReader.read(path)) {
            usuarios.add(new Usuario(
                    Long.parseLong(l[0]),
                    l[1],
                    l[2],
                    Role.valueOf(l[3]),
                    Boolean.parseBoolean(l[4])));
        }
        return usuarios;
    }
}
