package com.gapxco;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CreateUsers {

    public static void main(String args[]) {
        Path path = Paths.get("/Users/forduz/Documents/Trabajo/GrupoASD/2018/users/output/usuarios.csv");

        try {
            List<Usuario> usuarioList = Files
                    .lines(path)
                    .map(cadena -> new Usuario(cadena.split(",")))
                    .collect(Collectors.toList());

            usuarioList = deleteRepetidos(usuarioList);
            usuarioList.forEach(usuario -> write(usuario));
        } catch (IOException ex) {

        }
    }


    private static void write(Usuario usuario) {
        try {
            PrintWriter out = new PrintWriter(
                    new BufferedWriter(
                            new FileWriter("/Users/forduz/Documents/Trabajo/GrupoASD/2018/users/output/nuevos_usuarios_ldap.txt", true)));

            out.println("dn: uid=" + usuario.getUsuario() + ",ou=users,ou=system");
            out.println("objectClass: person");
            out.println("objectClass: inetOrgPerson");
            out.println("objectClass: organizationalPerson");
            out.println("objectClass: top");
            out.println("title: na");
            out.println("uid: " + usuario.getUsuario());
            out.println("description: menudep");
            out.println("cn: Usuario");
            out.println("sn: Tecnico");
            out.println("userPassword: " + usuario.getPassword());
            out.println("l: " + usuario.getMunicipio());
            out.println("st: " + usuario.getDepartamento());
            out.println();
            out.flush();
            out.close();
        } catch (IOException e) {

        }

        try {
            PrintWriter out = new PrintWriter(
                    new BufferedWriter(
                            new FileWriter("/Users/forduz/Documents/Trabajo/GrupoASD/2018/users/output/users_passwords.txt", true)));

            out.println("Usuario: " + usuario.getUsuario() + ", Password: " + usuario.getPasswordPlain());
            out.flush();
            out.close();
        } catch (IOException e) {

        }
    }

    private static List<Usuario> deleteRepetidos(List<Usuario> usuarios) {
        List<Usuario> cleans = new ArrayList<>();

        for (int i = 0; i < usuarios.size(); i++) {
            Usuario guardado = usuarios.get(i);
            if (cleans.isEmpty()) {
                cleans.add(guardado);
            } else {
                int index = cleans.indexOf(guardado);
                if (index >= 0) {
                    Usuario clean = cleans.get(index);
                    Usuario nuevo =
                            new Usuario("" + clean.getDepartamento(), "" + clean.getMunicipio(), clean.getUsuario() + ""
                                    + clean.getDepartamento(), clean.getDescripcion(), clean.getCantidad() + 1);
                    cleans.add(nuevo);
                } else {
                    cleans.add(guardado);
                }

            }

        }
        return cleans;
    }
}
