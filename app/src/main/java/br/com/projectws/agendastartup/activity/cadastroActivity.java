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

    private String nome, telefone, endereco, email;
    private EditText nomeEditText, telefoneEditText, enderecoEditText, emailEditText;
    private Button salvarButton, cancelarButton, excluirButton;
    private Cliente cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        nomeEditText = (EditText) findViewById(R.id.nomeEditText);
        telefoneEditText = (EditText) findViewById(R.id.telefoneEditText);
        telefoneEditText.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        enderecoEditText = (EditText) findViewById(R.id.enderecoEditText);
        emailEditText = (EditText) findViewById(R.id.emailEditText);
        excluirButton = (Button) findViewById(R.id.excluirButton);
        salvarButton = (Button) findViewById(R.id.salvarButton);
        cancelarButton = (Button) findViewById(R.id.cancelarButton);

        cliente = getIntent().getParcelableExtra("cliente");

        if(cliente != null) {
            excluirButton.setVisibility(View.VISIBLE);

            nomeEditText.setText(cliente.getNome());
            telefoneEditText.setText(cliente.getTelefone().substring(2, cliente.getTelefone().length()));
            enderecoEditText.setText(cliente.getEndereco());
            emailEditText.setText(cliente.getEmail());
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
                endereco = enderecoEditText.getText().toString();
                email = emailEditText.getText().toString();

                send();
            }
        });
    }

    private void send() {
        if(nome.equals("")) {
            Toast.makeText(CadastroActivity.this, "Deve informar um nome.", Toast.LENGTH_SHORT).show();

            return;
        }
        if(telefone.equals("")) {
            Toast.makeText(CadastroActivity.this, "Deve informar um telefone.", Toast.LENGTH_SHORT).show();

            return;
        }
        if(endereco.equals("")) {
            Toast.makeText(CadastroActivity.this, "Deve informar um endereço.", Toast.LENGTH_SHORT).show();

            return;
        }
        if(email.equals("")) {
            Toast.makeText(CadastroActivity.this, "Deve informar um email.", Toast.LENGTH_SHORT).show();

            return;
        }

        HomeActivity.addContato(new Cliente(
                nome,
                telefone,
                endereco,
                email
        ));

        Toast.makeText(CadastroActivity.this, "Contato Cadastrado.", Toast.LENGTH_SHORT).show();

        finish();
    }
}
