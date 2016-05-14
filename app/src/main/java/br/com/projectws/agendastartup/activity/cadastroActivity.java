package br.com.projectws.agendastartup.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import br.com.projectws.agendastartup.R;

public class cadastroActivity extends AppCompatActivity {
    private String url = "login";
    private EditText nome, telefone;
    private Button salvar, cancelar;
    private RadioButton whats, call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        nome = (EditText) findViewById(R.id.nomeEditText);
        telefone = (EditText) findViewById(R.id.telefoneEditText);
        whats = (RadioButton) findViewById(R.id.whatsRadioButton);
        cancelar = (RadioButton) findViewById(R.id.callRadioButton);
        salvar = (Button) findViewById(R.id.salvarButton);
        cancelar = (Button) findViewById(R.id.cancelarButton);

//        cancelar.setOnClickListener();
    }
}
