package common;

import java.io.Serializable;

/**
 * Created by Maka on 5/14/17.
 */

public class Equipo {

    private static final long serialVersionUID = 1L;
    String name;
    int equipo;
    int id;


    public Equipo(int id, String nombre,  int escudo) {
        this.name = nombre;
        this.equipo = equipo;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEquipo() {
        return equipo;
    }

    public void setEquipo(int equipo){
        this.equipo=equipo;
    }


}
