package castellet.dam.m12.uf2.hibernate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

//@Entity
//@Table(name = "Users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;


    /*************************************************************
     * Imaginem que tenim una entitat Sport
     * 
     * Imaginem que un usuari pot tenir un esport preferit
     * Vinculem les dues entitats
     * amb una relació ManyToOne (molts usuaris poden tenir el mateix esport preferit)
     ************************************************************/
    // @ManyToOne
    // @JoinColumn(name = "sport_id")
    // private Sport sport;


    // Constructor
    public User() {}

    public User(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return id + " - " + name;
    }
}
