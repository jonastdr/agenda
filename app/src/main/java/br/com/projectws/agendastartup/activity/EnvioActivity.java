package br.com.projectws.agendastartup.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import br.com.projectws.agendastartup.R;
import br.com.projectws.agendastartup.model.Cliente;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class EnvioActivity extends AppCompatActivity {

    TextView nomeTelefone, mensagem;

    final OkHttpClient mClient = new OkHttpClient();

    Cliente cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_envio);

        nomeTelefone = (TextView) findViewById(R.id.nomeTelefoneTextView);
        mensagem = (TextView) findViewById(R.id.mensagemEditText);

        setView();

        Button envioBtn = (Button) findViewById(R.id.enviarButton);

        assert envioBtn != null;
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
    }

    private void setView() {
        cliente = getIntent().getParcelableExtra("cliente");

        nomeTelefone.setText(cliente.getNome() + " - " + cliente.getTelefone());
    }

    public void run() throws Exception {
        Log.d("envio", "envio de mensagem...");

        RequestBody requestBody = new FormBody.Builder()
                .add("message", "mensagem de texto...")
                .add("target", cliente.getTelefone())
                .build();

        Request request = new Request.Builder()
                .url("http://192.168.0.15:8000/")
                .post(requestBody)
                .build();

        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {

            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                if (!response.isSuccessful()) throw new IOException("Erro de Conexão" + response);
                try {
                    JSONObject jsonResponse = new JSONObject(response.body().string());
                    String status = jsonResponse.getString("status");

                    Log.d("resultado", jsonResponse.toString());

                    System.out.println(status);
                    if (new String("success").equals(status)) {
                        finish();

                        EnvioActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "Mensagem enviada.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        EnvioActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "Falha no envio da mensagem.", Toast.LENGTH_SHORT).show();
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
