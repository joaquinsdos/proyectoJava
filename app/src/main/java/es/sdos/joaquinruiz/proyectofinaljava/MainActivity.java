package es.sdos.joaquinruiz.proyectofinaljava;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import es.sdos.joaquinruiz.proyectofinaljava.adapter.ProductAdapter;
import es.sdos.joaquinruiz.proyectofinaljava.comparator.ListoComparator;
import es.sdos.joaquinruiz.proyectofinaljava.model.Product;
import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    private final List<Product> lista = new ArrayList<>();
    private ProductAdapter adapter = new ProductAdapter();
    private Realm realm;
    private RecyclerView main__list__products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        realm = Realm.getDefaultInstance();
        RealmResults<Product> results = realm.where(Product.class).findAll();
        lista.addAll(results);
        adapter.setLista(lista);
        main__list__products = findViewById(R.id.main__list__products);
        main__list__products.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        main__list__products.setAdapter(adapter);
        reloadList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.main__btn_action__new) {
            newProductDialog();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void newProductDialog() {
        final View dialogView = getLayoutInflater().inflate(R.layout.dialog_new_product, null, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Nuevo elemento");
        builder.setView(dialogView)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        realm.beginTransaction();

                        Number nuevoID = realm.where(Product.class).max("id");
                        if (nuevoID == null) {
                            nuevoID = 0;
                        }
                        Product product = realm.createObject(Product.class, nuevoID.intValue()+1);
                        TextInputEditText campoTexto = dialogView.findViewById(R.id.dialog__txt__name);
                        product.setName(campoTexto.getText().toString());
                        lista.add(0, product);
                        realm.copyToRealm(product);
                        realm.commitTransaction();
                        reloadList();
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }


    private void reloadList() {
        Collections.sort(lista, new ListoComparator());
        adapter.notifyDataSetChanged();
    }
}
