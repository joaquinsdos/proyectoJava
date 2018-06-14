package es.sdos.joaquinruiz.proyectofinaljava.comparator;

import java.util.Comparator;

import es.sdos.joaquinruiz.proyectofinaljava.model.Product;

public class ListoCoparator implements Comparator<Product> {

    @Override
    public int compare(Product o1, Product o2) {
        return o1.getListo().compareTo(o2.getListo());
    }

}
