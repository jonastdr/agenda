package br.com.projectws.agendastartup.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import br.com.projectws.agendastartup.R;
import br.com.projectws.agendastartup.model.Mensagem;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MensagemCadastroActivity extends AppCompatActivity {

//    private Toolbar toolbar;

    private final OkHttpClient mClient = new OkHttpClient();
    private Mensagem mensagem;

    private Button enviarButton, cancelarButton;

    private EditText tituloEditText, mensagemEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensagem_cadastro);

//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        enviarButton = (Button) findViewById(R.id.enviarButton);
        cancelarButton = (Button) findViewById(R.id.cancelarButton);

        tituloEditText = (EditText) findViewById(R.id.tituloEditText);
        mensagemEditText = (EditText) findViewById(R.id.mensagemEditText);

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

        mensagem = getIntent().getParcelableExtra("mensagem");

        if(mensagem != null) {
            tituloEditText.setText(mensagem.getTitulo());
            mensagemEditText.setText(mensagem.getMensagem());
        }
    }

    public void run() throws Exception {
        RequestBody requestBody = new FormBody.Builder()
                .add("title", tituloEditText.getText().toString())
                .add("message", mensagemEditText.getText().toString())
                .add("id_usuario", "1")
                .build();

        Request request;

        if(mensagem == null) {
            request = new Request.Builder()
                    .url("http://192.168.0.15:8000/api/v1/message/create")
                    .post(requestBody)
                    .build();
        } else {
            request = new Request.Builder()
                    .url("http://192.168.0.15:8000/api/v1/message/update/" + mensagem.getId())
                    .post(requestBody)
                    .build();
        }

        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(MensagemCadastroActivity.this, "Falha no envio.", Toast.LENGTH_SHORT).show();

                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                try {
                    final JSONObject jsonResponse = new JSONObject(response.body().string());

                    System.out.println(jsonResponse);

                    String status = jsonResponse.getString("status");
                    final String msg = jsonResponse.getString("msg").toString();

                    if (new String("success").equals(status)) {
                        MensagemCadastroActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MensagemCadastroActivity.this, msg, Toast.LENGTH_SHORT).show();

                                finish();
                            }
                        });
                    } else {
                        MensagemCadastroActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MensagemCadastroActivity.this, msg, Toast.LENGTH_SHORT).show();
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
