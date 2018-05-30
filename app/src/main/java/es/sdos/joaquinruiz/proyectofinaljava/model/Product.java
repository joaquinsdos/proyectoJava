package es.sdos.joaquinruiz.proyectofinaljava.model;

import java.util.Objects;

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

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", listo=" + listo +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (id != null ? !id.equals(product.id) : product.id != null) return false;
        if (name != null ? !name.equals(product.name) : product.name != null) return false;
        return listo != null ? listo.equals(product.listo) : product.listo == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (listo != null ? listo.hashCode() : 0);
        return result;
    }
}
