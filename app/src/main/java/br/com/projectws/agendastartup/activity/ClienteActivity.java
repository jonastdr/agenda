package br.com.projectws.agendastartup.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import br.com.projectws.agendastartup.R;
import br.com.projectws.agendastartup.model.Cliente;

public class ClienteActivity extends AppCompatActivity {
    TextView nome, telefone, email, endereco;

    private ImageButton alterarButton;

    private Button excluirButton, enviarButton, telefonarButton;

    Cliente cliente;
    private static ClienteActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        instance = this;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);

        nome = (TextView) findViewById(R.id.nomeTextView);
        telefone = (TextView) findViewById(R.id.telefoneTextView);
        email = (TextView) findViewById(R.id.emailTextView);
        endereco = (TextView) findViewById(R.id.enderecoTextView);

        setView();

        alterarButton = (ImageButton) findViewById(R.id.alterarButton);
        excluirButton = (Button) findViewById(R.id.excluirButton);
        telefonarButton = (Button) findViewById(R.id.telefonarButton);
        enviarButton = (Button) findViewById(R.id.enviarButton);

        alterarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClienteActivity.this, CadastroActivity.class);
                Bundle mbundle = new Bundle();
                mbundle.putParcelable("cliente", cliente);
                intent.putExtras(mbundle);
                startActivityForResult(intent, 200);
            }
        });

        excluirButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                excluir();
            }
        });

        telefonarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                telefonar();
            }
        });

        enviarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email();
            }
        });

    }

    private void excluir() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(ClienteActivity.this);

        dialog.setTitle("Exclusão");
        dialog.setMessage("Tem certeza que deseja excluir este contato?");

        dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                HomeActivity.removeCliente(cliente);

                finish();
            }
        });

        dialog.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        dialog.show();
    }

    private void email() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/html");
        intent.putExtra(Intent.EXTRA_EMAIL, cliente.getEmail());
        intent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
        intent.putExtra(Intent.EXTRA_TEXT, "Meu Email");

        startActivity(Intent.createChooser(intent, "Send Email"));
    }

    private void telefonar() {
        Uri uri = Uri.parse("tel:" + cliente.getTelefone());
        Intent intent = new Intent(Intent.ACTION_DIAL,uri);

        startActivity(intent);
    }

    public static void updCliente(Cliente cliente) {
        instance.cliente = cliente;

        instance.nome.setText(cliente.getNome());
        instance.telefone.setText(cliente.getTelefone());
        instance.endereco.setText(cliente.getEndereco());
        instance.email.setText(cliente.getEmail());
    }

    private void setView() {
        cliente = getIntent().getParcelableExtra("cliente");

        nome.setText(cliente.getNome());
        telefone.setText(cliente.getTelefone());
        endereco.setText(cliente.getEndereco());
        email.setText(cliente.getEmail());
    }
}
