package br.com.projectws.agendastartup.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class MensagemCadastroActivity extends AppCompatActivity {

    private final OkHttpClient mClient = new OkHttpClient();
    private Cliente cliente;

    private Button enviarButton, cancelarButton;

    private EditText titulo, mensagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensagem_cadastro);

        enviarButton = (Button) findViewById(R.id.enviarButton);
        cancelarButton = (Button) findViewById(R.id.cancelarButton);

        titulo = (EditText) findViewById(R.id.tituloEditText);
        mensagem = (EditText) findViewById(R.id.mensagemEditText);

        enviarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    run();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        cancelarButton.setOnClickListener(new View.OnClickListener() {
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

    public void run() throws Exception {
        RequestBody requestBody = new FormBody.Builder()
                .add("titulo", titulo.getText().toString())
                .add("mensagem", mensagem.getText().toString())
                .build();

        Request request = new Request.Builder()
                .url("http://192.168.0.15:8000/mensagem/create")
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
