package com.example.gerenciadorcontatos.Contatos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.gerenciadorcontatos.R;
import com.example.gerenciadorcontatos.database.database;

import java.util.ArrayList;


public class Adicionar extends Fragment{
    EditText etNome;
    EditText etTelefone;
    Spinner sTipoTelefone;
    ArrayList<Integer> listContatosId;
    ArrayList<String> listContatosName;
    database db;

    public Adicionar(){};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.contatos_adicionar, container, false);

        sTipoTelefone = v.findViewById(R.id.spinnerTipoTelefoneContato);
        etNome = v.findViewById(R.id.editTextNomeContato);
        etTelefone = v.findViewById(R.id.editTextTelefoneContato);

        db = new database(getActivity());

        listContatosId = new ArrayList<>();
        listContatosName = new ArrayList<>();
        db.getAllTipoTelefone(listContatosId, listContatosName);
    //    Toast.makeText(getActivity(),listContatosName.get(0),Toast.LENGTH_LONG).show();

        ArrayAdapter<String> sTipoTelefoneArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, listContatosName);
        sTipoTelefone.setAdapter(sTipoTelefoneArrayAdapter);

        Button btnAdicionar = v.findViewById(R.id.buttonAdicionarContato);

        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { add(); }
        });
        return v;
    };

    public void add(){
        if (sTipoTelefone.getSelectedItem() == null) {
            Toast.makeText(getActivity(), "Por favor, selecione o tipo de contato!", Toast.LENGTH_LONG).show();
        } else if (etNome.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe o nome!", Toast.LENGTH_LONG).show();
        } else if (etTelefone.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe o telefone!", Toast.LENGTH_LONG).show();
        }  else {
            Contatos c = new Contatos();
            database helper = new database(getActivity());
            String TipoTelefone = sTipoTelefone.getSelectedItem().toString();

            c.setTipoTelefone(helper.getTipoContatoId(TipoTelefone));
            c.setNome(etNome.getText().toString());
            c.setTelefone(etTelefone.getText().toString());
            helper.createContatos(c);
            //Toast.makeText(getActivity(), "Contato salvo!", Toast.LENGTH_LONG).show();

            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameContatos, new Listar()).commit();
        }
    }



}
