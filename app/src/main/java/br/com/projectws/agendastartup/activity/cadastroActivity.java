package br.com.projectws.agendastartup.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.com.projectws.agendastartup.R;

public class CadastroActivity extends AppCompatActivity {
    private String url = "login";
    private EditText nome, telefone, interesses;
    private Button salvar, cancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        nome = (EditText) findViewById(R.id.nomeEditText);
        telefone = (EditText) findViewById(R.id.telefoneEditText);
        interesses = (EditText) findViewById(R.id.interreseEditText);
        salvar = (Button) findViewById(R.id.salvarButton);
        cancelar = (Button) findViewById(R.id.cancelarButton);

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
