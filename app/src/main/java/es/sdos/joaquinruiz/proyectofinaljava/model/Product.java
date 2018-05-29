package es.sdos.joaquinruiz.proyectofinaljava.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Product extends RealmObject {

    @PrimaryKey
    private Integer id;
    private String name;
    private Boolean listo;

    public Product(Integer id, String name, Boolean listo) {
        this.id = id;
        this.name = name;
        this.listo = listo;
    }

    public Product() {
        this.listo=false;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getListo() {
        return listo;
    }

    public void setListo(Boolean listo) {
        this.listo = listo;
    }
}
