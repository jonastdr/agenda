package br.com.projectws.agendastartup.activity;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

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
    private String[] array = {"mensagem1", "mensagem2"};
    TextView nome, telefone;
    private AlertDialog alert;

    private ImageButton alterarButton;

    Cliente cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);

        nome = (TextView) findViewById(R.id.nomeTextView);
        telefone = (TextView) findViewById(R.id.telefoneTextView);

        setView();

        Button enviarBtn = (Button) findViewById(R.id.enviarBtn);

        assert enviarBtn != null;
        enviarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    Uri uri = Uri.parse("smsto:" + "+" + cliente.getTelefone());

                    Intent i = new Intent(Intent.ACTION_SENDTO, uri);

                    Bundle bundle = i.getExtras();

                    System.out.println(bundle);

                    //sendWhatsIntent();
                    //Dialog();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        alterarButton = (ImageButton) findViewById(R.id.alterarButton);

        alterarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClienteActivity.this, CadastroActivity.class);
                Bundle mbundle = new Bundle();
                mbundle.putParcelable("cliente", cliente);
                intent.putExtras(mbundle);
                startActivity(intent);
            }
        });
    }

    public void Dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Escolha uma mensagem!");
        builder.setItems(array, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert = builder.create();
        alert.show();

    }


    @TargetApi(Build.VERSION_CODES.M)
    private void setView() {
        cliente = getIntent().getParcelableExtra("cliente");

        nome.setText(cliente.getNome());
        telefone.setText(cliente.getTelefone().substring(2, cliente.getTelefone().length()));
        telefone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
    }
}
