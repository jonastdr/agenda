package br.com.projectws.agendastartup.activity;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
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

public class ClienteActivity extends AppCompatActivity {
    private final OkHttpClient mClient = new OkHttpClient();

    TextView nome, telefone;

    TableLayout interessesTableLayout;

    Cliente cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);

        nome = (TextView) findViewById(R.id.nomeTextView);
        telefone = (TextView) findViewById(R.id.telefoneTextView);

        interessesTableLayout = (TableLayout) findViewById(R.id.interessesTableLayout);

        setView();

        Button enviarBtn = (Button) findViewById(R.id.enviarBtn);

        assert enviarBtn != null;
        enviarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    sendWhatsapp();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void setView() {
        cliente = getIntent().getParcelableExtra("cliente");

        nome.setText(cliente.getNome());
        telefone.setText(cliente.getTelefone());
        telefone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        TableRow interesseTableRow = new TableRow(this);

        for(int i = 1; i <= 3; i++) {
            TextView interesseTag = new TextView(this);

            interesseTag.setBackgroundResource(R.drawable.shape_tag);
            interesseTag.setPadding(15,15,15,15);
            interesseTag.setTextColor(Color.WHITE);
            interesseTag.setText("interesse " + i);

            interesseTableRow.addView(interesseTag);
        }

        interessesTableLayout.addView(interesseTableRow);
    }

    private void sendWhatsapp() {

        RequestBody requestBody = new FormBody.Builder()
                .add("message", "mensagem de texto...")
                .add("target", cliente.getTelefone())
                .build();

        Request request = new Request.Builder()
                .url("http://192.168.0.15:8000/api/v1/message/send")
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
                try {
                    JSONObject jsonResponse = new JSONObject(response.body().string());

                    System.out.println(jsonResponse);

                    String status = jsonResponse.getString("status");
                    if (new String("success").equals(status)) {
                        ClienteActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ClienteActivity.this, "Mensagem enviada.", Toast.LENGTH_SHORT).show();

                                finish();
                            }
                        });
                    } else {
                        ClienteActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ClienteActivity.this, "A mensagem foi salva no clipboard.", Toast.LENGTH_SHORT).show();

                                sendWhatsIntent();

                                finish();
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void sendWhatsIntent() {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

        String mensagem = "mensagem...";

        ClipData clip = ClipData.newPlainText("mensagem", mensagem);
        clipboard.setPrimaryClip(clip);

        Uri uri = Uri.parse("smsto:" + "+55" + cliente.getTelefone());

        Intent i = new Intent(Intent.ACTION_SENDTO, uri);

        i.setPackage("com.whatsapp");

        startActivity(i);
    }
}
