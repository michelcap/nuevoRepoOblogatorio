package Entidades;

import TADs.Lista.*;

public class User implements Comparable<User> {

    LL<Tweet> listaTweets = new LL<>();

    public LL<Tweet> getListaTweets() {
        return listaTweets;
    }

    private long id;

    private String name;

    private String verificado;

    private int CantidadTweets;

    private Integer favourites;

    public Integer getFavourites() {
        return favourites;
    }

    public void setFavourites(Integer favourites) {
        this.favourites = favourites;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getVerificado() {
        return verificado;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCantidadTweets() {
        return CantidadTweets;
    }

    @Override
    public int compareTo(User o) {
        return this.name.compareTo(o.name);
    }


    public User(String name, String verificado) {
        this.name = name;
        this.verificado = verificado;
    }


//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        User user = (User) o;
//        return CantidadTweets == user.CantidadTweets;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return name.equals(user.name);
    }
}
