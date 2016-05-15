package br.com.projectws.agendastartup.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.shapes.Shape;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import br.com.projectws.agendastartup.R;
import br.com.projectws.agendastartup.model.Cliente;
import br.com.projectws.agendastartup.utils.TagDecoration;

public class ClienteActivity extends AppCompatActivity {

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
                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

                String mensagem = "Olá " + cliente.getNome() + ", sou 'VENDEDOR' da loja 'LOJA'...";

                ClipData clip = ClipData.newPlainText("mensagem", mensagem);
                clipboard.setPrimaryClip(clip);

                Uri uri = Uri.parse("smsto:" + "+55" + cliente.getTelefone());

                Intent i = new Intent(Intent.ACTION_SENDTO, uri);

                i.setPackage("com.whatsapp");

                startActivity(i);
            }
        });
    }

    private void setView() {
        cliente = getIntent().getParcelableExtra("cliente");

        nome.setText(cliente.getNome());
        telefone.setText(cliente.getTelefone());

        TableRow interesseTableRow = new TableRow(this);

        for(int i = 1; i <= 3; i++) {
            TextView interesseTag = new TagDecoration(this);

            interesseTag.setText("interesse " + i);

            interesseTableRow.addView(interesseTag);
        }

        interessesTableLayout.addView(interesseTableRow);
    }
}
