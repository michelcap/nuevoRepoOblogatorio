package Entidades;

public class Piloto implements Comparable<Piloto> {
    private final String nombre;
    private final int conteo;

    public Piloto(String nombre, int conteo) {
        this.nombre = nombre;
        this.conteo = conteo;
    }

    public String getNombre() {
        return nombre;
    }

    public int getConteo() {
        return conteo;
    }

    @Override
    public int compareTo(Piloto other) {
        return Integer.compare(this.conteo, other.conteo);
    }

    @Override
    public String toString() {
        return "Piloto{" +
                "nombre='" + nombre + '\'' +
                ", conteo=" + conteo +
                '}';
    }
}

