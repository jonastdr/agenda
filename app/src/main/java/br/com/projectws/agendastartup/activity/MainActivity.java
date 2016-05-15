package br.com.projectws.agendastartup.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import br.com.projectws.agendastartup.model.Mensagem;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private final OkHttpClient mClient = new OkHttpClient();
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    protected List<Cliente> clienteList = new ArrayList<>();
    protected List<Mensagem> mensagemList = new ArrayList<>();
    private Cliente cliente;
    private Mensagem mensagem;
    protected ClienteAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        try {
            prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public List<Cliente> lista() {
        return clienteList;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new MensagemActivity(), "MENSAGENS");
        adapter.addFragment(new HomeActivity(), "CLIENTES");
        adapter.addFragment(new FiltroActivity(), "FILTROS");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    private void prepare() throws Exception {

        RequestBody requestBody = new FormBody.Builder()
                .build();

        Request request = new Request.Builder()
                .url("http://192.168.0.15:8000/api/v1/contato/filter")
                .post(requestBody)
                .build();

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
                        clienteList = new ArrayList<>();
                        JSONArray contato = jsonResponse.getJSONObject("options").getJSONArray("contato");
                        for(int i = 0; i < contato.length(); i++) {
                            JSONObject cont = (JSONObject) contato.get(i);
                            JSONObject descricao = (JSONObject) cont.getJSONArray("perfil").get(0);
                            System.out.println(descricao);
                            cliente = new Cliente(
                                    cont.getString("id"),
                                    cont.getString("nome"),
                                    cont.getString("numero"),
                                    descricao.getString("descricao")
                            );
                            clienteList.add(cliente);
                        }

                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "Erro de Conexão", Toast.LENGTH_SHORT);
                            }
                        });
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
