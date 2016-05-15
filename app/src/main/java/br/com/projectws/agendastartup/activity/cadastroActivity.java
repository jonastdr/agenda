package br.com.projectws.agendastartup.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

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

public class CadastroActivity extends AppCompatActivity {
    private final OkHttpClient mClient = new OkHttpClient();

    private String nome, telefone, interreses;
    private EditText nomeEditText, telefoneEditText, interessesEditText;
    private Button salvarButton, cancelarButton;
    private Cliente cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        nomeEditText = (EditText) findViewById(R.id.nomeEditText);
        telefoneEditText = (EditText) findViewById(R.id.telefoneEditText);
        telefoneEditText.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        interessesEditText = (EditText) findViewById(R.id.interreseEditText);
        salvarButton = (Button) findViewById(R.id.salvarButton);
        cancelarButton = (Button) findViewById(R.id.cancelarButton);

        cliente = getIntent().getParcelableExtra("cliente");

        if(cliente != null) {
            nomeEditText.setText(cliente.getNome());
            telefoneEditText.setText(cliente.getTelefone().substring(2, cliente.getTelefone().length()));
            interessesEditText.setText(cliente.getTags());
        }

        cancelarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        salvarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nome = nomeEditText.getText().toString();
                telefone = telefoneEditText.getText().toString();
                interreses = interessesEditText.getText().toString();

                /*Intent intent = new Intent();
                intent.putExtra("nome", nome);
                intent.putExtra("telefone", telefone);
                intent.putExtra("interesses", interreses);
                */
                send();
            }
        });
    }

    private void send() {

        RequestBody requestBody = new FormBody.Builder()
                .add("nome", nomeEditText.getText().toString())
                .add("numero", "55" + telefoneEditText.getText().toString())
                .add("telefone", "0")
                .add("whatsapp", "0")
                .add("perfil", interessesEditText.getText().toString())
                .add("id_usuario", "1")
                .build();

        Request request;

        if(cliente == null) {
            request = new Request.Builder()
                    .url("http://192.168.0.15:8000/api/v1/contato/create")
                    .post(requestBody)
                    .build();
        } else {
            request = new Request.Builder()
                    .url("http://192.168.0.15:8000/api/v1/contato/update/" + cliente.getId())
                    .post(requestBody)
                    .build();
        }

        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
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
                        CadastroActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(CadastroActivity.this, msg, Toast.LENGTH_SHORT).show();

                                finish();
                            }
                        });
                    } else {
                        CadastroActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(CadastroActivity.this, msg, Toast.LENGTH_SHORT).show();
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
