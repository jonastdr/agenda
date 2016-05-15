package br.com.projectws.agendastartup.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.projectws.agendastartup.R;
import br.com.projectws.agendastartup.model.Mensagem;

/**
 * Created by guilhermino on 5/15/16.
 */
public class MensagemAdapter extends RecyclerView.Adapter<MensagemAdapter.MyViewHolder> {
    private List<Mensagem> mensagemList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView titulo, mensagem;

        public MyViewHolder(View view) {
            super(view);
            titulo = (TextView) view.findViewById(R.id.titulo);
        }
    }

    public MensagemAdapter(List<Mensagem> mensagemList) {
        this.mensagemList = mensagemList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mensagem_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Mensagem mensagem = mensagemList.get(position);
        holder.titulo.setText(mensagem.getTitulo());
    }

    @Override
    public int getItemCount() {
        return mensagemList.size();
    }
    
}
