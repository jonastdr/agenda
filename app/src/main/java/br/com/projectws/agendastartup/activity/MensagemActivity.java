package br.com.projectws.agendastartup.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import br.com.projectws.agendastartup.R;
import br.com.projectws.agendastartup.adapter.ClienteAdapter;
import br.com.projectws.agendastartup.model.Cliente;
import br.com.projectws.agendastartup.utils.DividerItemDecoration;

public class MensagemActivity extends Fragment {
    protected RecyclerView recyclerView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceStage) {
        View rootView = inflater.inflate(R.layout.activity_mensagem, container, false);

        Button novaBtn = (Button) rootView.findViewById(R.id.novaBtn);

        assert novaBtn != null;
        novaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MensagemCadastroActivity.class);

                startActivity(intent);
            }
        });

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        mAdapter = new ClienteAdapter(clienteList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity().getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Cliente cliente = clienteList.get(position);
                try {
                    Intent intent = new Intent(getActivity(), ClienteActivity.class);
                    Bundle mbundle = new Bundle();
                    mbundle.putParcelable("cliente", cliente);
                    intent.putExtras(mbundle);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        prepareCliente();


        return rootView;
    }

}
