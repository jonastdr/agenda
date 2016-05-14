package br.com.projectws.agendastartup.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import br.com.projectws.agendastartup.R;

public class MensagemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensagem);

        Button novaBtn = (Button) findViewById(R.id.novaBtn);

        assert novaBtn != null;
        novaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Activity cadastro = new MensagemCadastroActivity();
            }
        });
    }
}
