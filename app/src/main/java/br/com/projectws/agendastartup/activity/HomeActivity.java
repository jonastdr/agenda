package br.com.projectws.agendastartup.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.projectws.agendastartup.R;
import br.com.projectws.agendastartup.adapter.ClienteAdapter;
import br.com.projectws.agendastartup.model.Cliente;
import br.com.projectws.agendastartup.utils.DividerItemDecoration;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HomeActivity extends Fragment {
    private final OkHttpClient mClient = new OkHttpClient();
    protected static final int REQUEST_CADASTRO = 200;
    protected Button cadastrar;
    protected List<Cliente> clienteList = new ArrayList<>();
    protected RecyclerView recyclerView;
    protected ClienteAdapter mAdapter;
    private Cliente cliente;

    private static HomeActivity instance;

    public HomeActivity() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        instance = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceStage) {
        View rootView = inflater.inflate(R.layout.activity_home, container, false);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        cadastrar = (Button) rootView.findViewById(R.id.cadastrarButton);
        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CadastroActivity.class);
                startActivityForResult(intent, REQUEST_CADASTRO);
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


        /*if (clienteList.isEmpty()) {
        try {
            prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
        }*/

        return rootView;
    }

    private void prepare() throws Exception {

        RequestBody requestBody = new FormBody.Builder()
                .build();

        Request request = new Request.Builder()
                    .url("http://192.168.0.15:8000/api/v1/contato/filter")
                .post(requestBody)
                    .build();

        clienteList.add(cliente);

        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) throw new IOException("Erro de Conexão" + response);
                try {
                    JSONObject jsonResponse = new JSONObject(response.body().string());

                    String status = jsonResponse.getString("status");
                    if (new String("success").equals(status)) {
                        JSONArray contato = jsonResponse.getJSONObject("options").getJSONArray("contato");

                        for(int i = 0; i < contato.length(); i++) {
                            JSONObject cont = (JSONObject) contato.get(i);

                            cliente = new Cliente(
                                    cont.getString("nome"),
                                    cont.getString("numero"),
                                    cont.getString("endereco"),
                                    cont.getString("email")
                            );
                            clienteList.add(cliente);
                        }

                    } else {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getContext(), "Erro de Conexão", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.notifyDataSetChanged();
                        }
                    });


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void addContato(Cliente contato) {
        instance.clienteList.add(contato);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CADASTRO) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    Cliente cliente = new Cliente(
                            data.getStringExtra("nome"),
                            data.getStringExtra("telefone"),
                            data.getStringExtra("endereco"),
                            data.getStringExtra("email")
                    );
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
