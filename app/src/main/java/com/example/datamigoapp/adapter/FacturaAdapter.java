package com.example.datamigoapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.datamigoapp.R;
import com.example.datamigoapp.entity.Documento;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FacturaAdapter extends BaseAdapter implements Filterable {

    private final LayoutInflater inflater;
    private List<Documento> listaFacturasOut;
    private List<Documento> listaFacturasIn;

    public FacturaAdapter(Context context, List<Documento> listaFacturas) {
        this.listaFacturasOut = listaFacturas;
        this.listaFacturasIn = listaFacturas;
        this.inflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return listaFacturasOut.size();
    }

    @Override
    public Object getItem(int position) {
        return listaFacturasOut.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public Filter getFilter() {

        Filter filter = new Filter() {
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                listaFacturasOut =(List<Documento>) results.values;
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults results = new FilterResults();
                List<Documento> FilteredArrList = new ArrayList<>();

                if (listaFacturasIn == null){
                    listaFacturasIn = new ArrayList<>(listaFacturasOut);
                }

                if (constraint == null || constraint.length() == 0){
                    results.count = listaFacturasIn.size();
                    results.values =listaFacturasIn;
                }else{

                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < listaFacturasIn.size(); i++) {
                        String data =listaFacturasIn.get(i).getNit_proveedor();
                        if (data.toLowerCase().contains(constraint.toString())){
                            FilteredArrList.add(listaFacturasIn.get(i));
                        }
                    }

                    results.count =FilteredArrList.size();
                    results.values = FilteredArrList;
                }

                return results;
            }
        };
        return filter;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder holder;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(R.layout.factura_item_layout, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        holder.imagen.setImageResource(listaFacturasOut.get(position).getImagen());
        holder.txtNitProveedor.setText(listaFacturasOut.get(position).getNit_proveedor());
        holder.txtFecha.setText(listaFacturasOut.get(position).getFecha());
        holder.txtValor.setText(listaFacturasOut.get(position).getValor_factura());
        return convertView;
    }


    class ViewHolder{
        @BindView(R.id.imagen)
        ImageView imagen;
        @BindView(R.id.txtNitProveedor)
        TextView txtNitProveedor;
        @BindView(R.id.txtValor)
        TextView txtValor;
        @BindView(R.id.txtFecha)
        TextView txtFecha;

        public ViewHolder(View view) {
            ButterKnife.bind(this,view);
        }
    }
}
