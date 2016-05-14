package br.com.projectws.agendastartup.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.projectws.agendastartup.R;
import br.com.projectws.agendastartup.model.Cliente;

/**
 * Created by guilhermino on 5/14/16.
 */
public class ClienteAdapter extends RecyclerView.Adapter<ClienteAdapter.MyViewHolder> {

    private List<Cliente> clientesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView nome, telefone, tags;

        public MyViewHolder(View view) {
            super(view);
            nome = (TextView) view.findViewById(R.id.nome);
            telefone = (TextView) view.findViewById(R.id.data);
            tags = (TextView) view.findViewById(R.id.tags);
        }
    }


    public ClienteAdapter(List<Cliente> clientesList) {
        this.clientesList = clientesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cliente_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Cliente cliente = clientesList.get(position);
        holder.nome.setText(cliente.getNome());
        holder.tags.setText(cliente.getTags());
    }

    @Override
    public int getItemCount() {
        return clientesList.size();
    }

}
