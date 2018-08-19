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


        //TODO - Generar Cabecera del archivo en un metodo

        try {
            PrintWriter out = new PrintWriter(
                    new BufferedWriter(
                            new FileWriter("/Users/forduz/Documents/Trabajo/GrupoASD/2018/users/output/nuevos_usuarios_ldap.txt", true)));

            out.println("dn: ou=system");
            out.println("objectClass: organizationalUnit");
            out.println("objectClass: top");
            out.println("ou: system");
            out.println("description: The System context entry");
            out.println("ds-sync-state: 00000148ad2a9b39545a0000000a");
            out.println("ds-sync-state: 00000148806b576b23ac00000001");
            out.println("entryUUID: a44476d5-2f94-3e09-9677-c5a739bab7dd");
            out.println("ds-sync-generation-id: 2281514");
            out.println("");
            out.println("dn: ou=groups,ou=system");
            out.println("objectClass: organizationalUnit");
            out.println("objectClass: top");
            out.println("ou: groups");
            out.println("entryUUID: 26444922-6c7a-33bc-b605-4bdb0355ad5e");
            out.println("");
            out.println("dn: ou=users,ou=system");
            out.println("objectClass: organizationalUnit");
            out.println("objectClass: top");
            out.println("ou: users");
            out.println("entryUUID: 7b931310-2d67-3e74-8e14-99dc8cdf079b");
            out.flush();
            out.close();
        } catch (IOException e) {

        }

        try {
            List<Usuario> usuarioList = Files
                    .lines(path)
                    .map(cadena -> new Usuario(cadena.split(",")))
                    .collect(Collectors.toList());

            usuarioList = deleteRepetidos(usuarioList);
            usuarioList.forEach(usuario -> write(usuario));

            //Grupo votos
            //List<Person> beerDrinkers = persons.stream()
            //    .filter(p -> p.getAge() > 16).collect(Collectors.toList());
            List<Usuario> miembrosVotos = usuarioList.stream().filter(p -> p.getVotacion() == true)
                    .collect(Collectors.toList());
            writeGroup("votos", miembrosVotos, "uniqueMember", "cn: votos\n" +
                    "entryUUID: 7e4abcc9-47c8-444b-a41e-e59ef9c5bee8\n" +
                    "createTimestamp: 20150512192421Z\n" +
                    "creatorsName: cn=Directory Manager,cn=Root DNs,cn=config\n" +
                    "modifyTimestamp: 20150525180031Z\n" +
                    "modifiersName: cn=Directory Manager,cn=Root DNs,cn=config");

        } catch (IOException ex) {

        }


    }

    private static void writeGroup(String group, List<Usuario> usuarios, String memberTag, String endGrup) {
        try {
            PrintWriter out = new PrintWriter(
                    new BufferedWriter(
                            new FileWriter("/Users/forduz/Documents/Trabajo/GrupoASD/2018/users/output/nuevos_usuarios_ldap.txt", true)));

            out.println("dn: cn=" + group + ",ou=groups,ou=system");
            out.println("objectClass: organizationalUnit");
            out.println("objectClass: top");

            for (int i = 0; i < usuarios.size(); i++) {
                Usuario u = usuarios.get(i);
                out.println(memberTag + ": uid=" + u.getUsuario() + ",ou=users,ou=system");
            }

            out.println(endGrup);
            out.flush();
            out.close();
        } catch (IOException e) {

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
                    guardado.setUsuario(guardado.getUsuario() + '_' + guardado.getDepartamento() + '_' + guardado.getMunicipio());
                    cleans.add(guardado);
                    //TODO - Validar que el nuevo usuario no exista.
                } else {
                    cleans.add(guardado);
                }

            }

        }
        return cleans;
    }
}
