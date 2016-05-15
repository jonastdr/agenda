package br.com.projectws.agendastartup.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.com.projectws.agendastartup.R;
import br.com.projectws.agendastartup.model.Cliente;

public class CadastroActivity extends AppCompatActivity {
    private String url = "login";
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

                Intent intent = new Intent();
                intent.putExtra("nome", nome);
                intent.putExtra("telefone", telefone);
                intent.putExtra("interesses", interreses);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
