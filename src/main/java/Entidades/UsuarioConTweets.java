package Entidades;

import TADs.Lista.LL;

import java.util.Objects;

public class UsuarioConTweets   implements Comparable<UsuarioConTweets>{
    private String name;

    private String isVerified;

     LL<Tweet> listaTweetsCargados;
     
//     private int cantidadTweets = listaTweetsCargados.size();

    public UsuarioConTweets(String name, String isVerified, LL<Tweet> listaTweetsCargados) {
        this.name = name;
        this.isVerified = isVerified;
        this.listaTweetsCargados = listaTweetsCargados;
    }

    public String getName() {
        return name;
    }

    public String getIsVerified() {
        return isVerified;
    }

    public LL<Tweet> getListaTweetsCargados() {
        return listaTweetsCargados;
    }

    @Override
    public int compareTo(UsuarioConTweets o) {
        return Integer.compare(this.listaTweetsCargados.size(), o.listaTweetsCargados.size());
    }




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsuarioConTweets that = (UsuarioConTweets) o;
        return Objects.equals(listaTweetsCargados.size(), that.listaTweetsCargados.size());
    }


}
