package br.com.projectws.agendastartup.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import br.com.projectws.agendastartup.R;

public class MensagemActivity extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceStage) {
        View rootView = inflater.inflate(R.layout.activity_mensagem, container, false);

        Button novaBtn = (Button) rootView.findViewById(R.id.novaBtn);

        assert novaBtn != null;
        novaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MensagemCadastroActivity.class);

                startActivity(intent);
            }
        });


        return rootView;
    }

}
