package com.gapxco;

import java.security.NoSuchAlgorithmException;
import java.util.Objects;

public class Usuario {

    private int departamento;

    private int municipio;

    private String usuario;

    private String descripcion;

    private String password = "";

    private String passwordPlain = "";

    private Boolean prensa;

    private Boolean votacion;

    private Boolean tec;

    private Boolean auditoria;

    private int cantidad;

    public Usuario(String departamento, String municipio, String usuario, String descripcion, int cantidad) {
        this.departamento = Integer.parseInt(departamento);
        this.municipio = Integer.parseInt(municipio);
        this.usuario = eliminarParentesis(usuario.toLowerCase());
        this.descripcion = descripcion + " " + extraerParentesis(usuario);
        this.passwordPlain = PasswordMethods.generatePassword();
        try {
            this.password = PasswordMethods.generateSSHA(passwordPlain.getBytes());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        this.cantidad = cantidad;

    }

    public Usuario(String[] campos) {
        this.departamento = Integer.parseInt(campos[0]);
        this.municipio = Integer.parseInt(campos[1]);

        String temp = campos[2].replace(" ", "").replace('-','_')
                .replace('(','_').replace(')','_').replace('.','_');
        temp = temp.toLowerCase();
        this.usuario = temp;//eliminarParentesis(temp.replace("ñ", "n"));

        this.descripcion = campos[3];

        this.setPrensa(campos[4]);
        this.setVotacion(campos[5]);
        this.setTec(campos[6]);
        this.setAuditoria(campos[7]);

        this.passwordPlain = campos[8];

        try {
            this.password = PasswordMethods.generateSSHA(passwordPlain.getBytes());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        this.cantidad = 1;
    }

    public int getDepartamento() {
        return departamento;
    }

    public void setDepartamento(int departamento) {
        this.departamento = departamento;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordPlain() {
        return passwordPlain;
    }

    public Boolean getAuditoria() {
        return auditoria;
    }

    public void setAuditoria(Boolean auditoria) {
        this.auditoria = auditoria;
    }

    public Boolean getPrensa() {
        return prensa;
    }

    public void setPrensa(Boolean prensa) {
        this.prensa = prensa;
    }

    public void setPrensa(String prensa) {
        if (prensa != null && prensa.replaceAll(" ", "").isEmpty()) {
            this.prensa = true;
        } else {
            this.prensa = false;
        }
    }

    public void setVotacion(String votacion) {
        if (votacion != null && votacion.replaceAll(" ", "").isEmpty()) {
            this.votacion = true;
        } else {
            this.votacion = false;
        }
    }

    public void setAuditoria(String auditoria) {
        if (auditoria != null && auditoria.replaceAll(" ", "").isEmpty()) {
            this.auditoria = true;
        } else {
            this.auditoria = false;
        }
    }

    public void setTec(String tec) {
        if (tec != null && tec.replaceAll(" ", "").isEmpty()) {
            this.tec = true;
        } else {
            this.tec = false;
        }
    }

    public Boolean getVotacion() {
        return votacion;
    }

    public void setVotacion(Boolean votacion) {
        this.votacion = votacion;
    }

    public Boolean getTec() {
        return tec;
    }

    public void setTec(Boolean tec) {
        this.tec = tec;
    }

    public void setPasswordPlain(String passwordPlain) {
        this.passwordPlain = passwordPlain;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getMunicipio() {
        return municipio;
    }

    public void setMunicipio(int municipio) {
        this.municipio = municipio;
    }

    private String extraerParentesis(String cadena) {
        String convertida = "";
        try {
            int indexInicial = cadena.indexOf("(");
            int indexFinal = cadena.lastIndexOf(")");

            convertida = cadena.substring(indexInicial, indexFinal + 1);
        } catch (Exception ex) {
            convertida = cadena;
        }
        return convertida;
    }

    private String eliminarParentesis(String cadena) {
        String convertida = "";
        try {
            int indexInicial = cadena.indexOf("(");
            convertida = cadena.substring(0, indexInicial);
        } catch (Exception ex) {
            convertida = cadena;
        }
        return convertida;
    }


    @Override
    public String toString() {
        return "Usuario{" +
                "departamento='" + departamento + '\'' +
                ", usuario='" + usuario + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario)) return false;
        Usuario usuario1 = (Usuario) o;
        return Objects.equals(getUsuario(), usuario1.getUsuario());
    }


}
