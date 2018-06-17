package es.sdos.joaquinruiz.proyectofinaljava.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import es.sdos.joaquinruiz.proyectofinaljava.R;
import es.sdos.joaquinruiz.proyectofinaljava.comparator.ListoComparator;
import es.sdos.joaquinruiz.proyectofinaljava.model.Product;
import io.realm.Realm;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private List<Product> lista = new ArrayList<>();

    public ProductAdapter() {
        super();
    }

    public void setLista(List<Product> lista) {
        this.lista = lista;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_producs, parent, false);
        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(lista, this);
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private Realm realm = Realm.getDefaultInstance();
        private CheckBox checkBox;
        private ImageButton deleteButton;

        ViewHolder(View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.list__checkbox__comprado);
            deleteButton = itemView.findViewById(R.id.list__btn__delete);
        }

        void bind(final List<Product> list, final ProductAdapter productAdapter) {
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Collections.sort(lista, new ListoComparator());
                    productAdapter.notifyDataSetChanged();
                }
            });

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    realm.beginTransaction();
                    lista.get(getLayoutPosition()).setListo(isChecked);
                    realm.copyToRealmOrUpdate(lista.get(getLayoutPosition()));
                    realm.commitTransaction();
                }
            });

            checkBox.setChecked(lista.get(getLayoutPosition()).getListo());
            checkBox.setText(lista.get(getLayoutPosition()).getName());
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getLayoutPosition() != -1) {
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                lista.get(getLayoutPosition()).deleteFromRealm();
                                lista.remove(getLayoutPosition());
                            }
                        });
                        productAdapter.notifyItemRemoved(getLayoutPosition());
                    }
                }
            });
        }
    }
}