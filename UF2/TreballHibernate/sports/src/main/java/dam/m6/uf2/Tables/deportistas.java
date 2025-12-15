package dam.m6.uf2.Tables;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "deportistas")

public class deportistas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod")
    private int cod;

    @Column(name = "nombre")
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "cod_deporte")
    private deportes sport;

    // Constructor
    public deportistas() {
    }

    public deportistas(int cod, String nombre, deportes sport) {
        this.cod = cod;
        this.nombre = nombre;
        this.sport = sport;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public deportes getSport() {
        return sport;
    }

    public void setSport(deportes sport) {
        this.sport = sport;
    }

    @Override
    public String toString() {
        return cod + " - " + nombre + " (" + sport.getNombre() + ")" + "\n";
    }

}
