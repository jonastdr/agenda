package br.com.projectws.agendastartup.activity;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.com.projectws.agendastartup.R;
import br.com.projectws.agendastartup.adapter.ClienteAdapter;
import br.com.projectws.agendastartup.model.Cliente;
import br.com.projectws.agendastartup.utils.DividerItemDecoration;

public class FiltroActivity extends Fragment {
    protected List<Cliente> clienteList = new ArrayList<>();
    protected RecyclerView recyclerView;
    protected ClienteAdapter mAdapter;

    EditText pesquisaEditText;
    Button filtrarButton;

    public FiltroActivity() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceStage) {
        View rootView = inflater.inflate(R.layout.activity_filtro, container, false);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

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
                    startActivityForResult(intent, 200);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        pesquisaEditText = (EditText)rootView.findViewById(R.id.pesquisaEditText);

        filtrarButton = (Button) rootView.findViewById(R.id.filtrarButton);
        filtrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filtrar();
            }
        });

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        filtrar();
    }

    private void filtrar() {
        List<Cliente> clientes = HomeActivity.getList();

        clienteList.clear();

        for(Cliente cliente: clientes) {
            if(
                cliente.getNome().contains(pesquisaEditText.getText().toString()) ||
                cliente.getTelefone().contains(pesquisaEditText.getText().toString()) ||
                cliente.getEndereco().contains(pesquisaEditText.getText().toString())
            ) {
                clienteList.add(cliente);
            }
        }

        Collections.sort(clienteList, new Comparator() {
            public int compare(Object synchronizedListOne, Object synchronizedListTwo) {

                return ((Cliente) synchronizedListOne).getNome()
                        .compareTo(((Cliente) synchronizedListTwo).getNome());
            }
        });

        mAdapter.notifyDataSetChanged();
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    /**
     * Lista de clientes / contatos
     */
    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private FiltroActivity.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final FiltroActivity.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildAdapterPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

}
