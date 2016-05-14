package br.com.projectws.agendastartup.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import br.com.projectws.agendastartup.R;
import br.com.projectws.agendastartup.adapter.ClienteAdapter;
import br.com.projectws.agendastartup.model.Cliente;
import br.com.projectws.agendastartup.utils.DividerItemDecoration;

public class HomeActivity extends AppCompatActivity {
    private static final int REQUEST_CADASTRO = 200;
    private Button cadastrar;
    private List<Cliente> clienteList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ClienteAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        cadastrar = (Button) findViewById(R.id.cadastrarButton);
        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, CadastroActivity.class);
                startActivityForResult(intent, REQUEST_CADASTRO);
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new ClienteAdapter(clienteList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Cliente cliente = clienteList.get(position);
                try {
                    Intent intent = new Intent(HomeActivity.this, ClienteActivity.class);
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
    }

    private void prepareCliente() {
        Cliente cliente = new Cliente("Luiza", "4288888888", "sapato, blusa, tricole");
        clienteList.add(cliente);

        cliente = new Cliente("Maria", "4299999999" , "sapato, blusa, tricole");
        clienteList.add(cliente);

        cliente = new Cliente("Guilhermino", "4298424923" , "sapato, blusa, tricole");
        clienteList.add(cliente);

        cliente = new Cliente("Jonas", "4299603082" , "sapato, blusa, tricole");
        clienteList.add(cliente);

        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CADASTRO) {
            if (resultCode == RESULT_OK) {
                try {
                    Cliente cliente = new Cliente(data.getStringExtra("nome"),
                            data.getStringExtra("telefone"), data.getStringExtra("interesses"));
                    clienteList.add(cliente);
                    mAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }


    }


    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private HomeActivity.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final HomeActivity.ClickListener clickListener) {
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
