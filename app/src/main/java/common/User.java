package common;

import java.io.Serializable;

/**
 * Created by Maka on 5/14/17.
 */

public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    String name;
    int equipo;
    int id;


    public User(String name, int equipo, int id) {
        this.name = name;
        this.equipo = equipo;
        this.id=id;

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

    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id=id;
    }


}
