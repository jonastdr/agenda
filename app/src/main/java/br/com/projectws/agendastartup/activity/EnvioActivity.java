package br.com.projectws.agendastartup.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

import br.com.projectws.agendastartup.R;
import br.com.projectws.agendastartup.model.Cliente;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class EnvioActivity extends AppCompatActivity {
    private final OkHttpClient mClient = new OkHttpClient();
    private TextView nomeTelefone, mensagem;
    private Button envioBtn, cancelarBtn;
    private Cliente cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_envio);

        nomeTelefone = (TextView) findViewById(R.id.nomeTelefoneTextView);
        mensagem = (TextView) findViewById(R.id.mensagemEditText);

        setView();
        envioBtn = (Button) findViewById(R.id.enviarButton);
        cancelarBtn = (Button) findViewById(R.id.cancelarButton);

        envioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    run();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        cancelarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setView() {
        cliente = getIntent().getParcelableExtra("cliente");

        nomeTelefone.setText(cliente.getNome() + " - " + cliente.getTelefone());
    }

    public void run() throws Exception {

        RequestBody requestBody = new FormBody.Builder()
                .add("message", "mensagem de texto...")
                .add("target", cliente.getTelefone())
                .build();

        Request request = new Request.Builder()
                .url("http://192.168.0.15:8000")
                .post(requestBody)
                .build();

        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                System.out.println(response.body().string());
            }
        });

    }
}
