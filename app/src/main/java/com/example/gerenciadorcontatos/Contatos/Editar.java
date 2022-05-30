package com.example.gerenciadorcontatos.Contatos;

import com.example.gerenciadorcontatos.R;
import com.example.gerenciadorcontatos.database.database;
import android.app.AlertDialog;
import android.content.DialogInterface;
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

import java.util.ArrayList;

public class Editar extends Fragment{

    EditText etNome;
    EditText etTelefone;
    Spinner sTipoTelefone;
    ArrayList<Integer> listTipoTelefoneId;
    ArrayList<String> listTipoTelefoneNome;
    database databaseHelper;
    Contatos c;

    public Editar() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.contatos_editar, container, false);
        Bundle bundle = getArguments();
        int id_contato = bundle.getInt("id");
        databaseHelper = new database(getActivity());
        c = databaseHelper.getContatoById(id_contato);

        sTipoTelefone = v.findViewById(R.id.spinnerTipoTelefoneContato);
        etNome = v.findViewById(R.id.editTextNomeContato);
        etTelefone = v.findViewById(R.id.editTextTelefoneContato);

        listTipoTelefoneId = new ArrayList<>();
        listTipoTelefoneNome = new ArrayList<>();
        databaseHelper.getAllTipoTelefone(listTipoTelefoneId,listTipoTelefoneNome);


        ArrayAdapter<String> sTipoTelefoneArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, listTipoTelefoneNome);
        sTipoTelefone.setAdapter(sTipoTelefoneArrayAdapter);


        etNome.setText(c.getNome());
        etTelefone.setText(c.getTelefone());
        sTipoTelefone.setSelection(listTipoTelefoneId.indexOf(c.getTipoTelefone()));

        Button btnEditar = v.findViewById(R.id.buttonEditarBebe);

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editar(id_contato);
            }
        });

        Button btnExcluir = v.findViewById(R.id.buttonExcluirBebe);

        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(R.string.dialog_excluir_contatos);
                builder.setPositiveButton(R.string.sim, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        excluir(id_contato);
                    }
                });
                builder.setNegativeButton(R.string.nao, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Não faz nada
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        return v;
    }

    private void editar (int id) {
        if (etNome.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe o nome!", Toast.LENGTH_LONG).show();
        } else if (etTelefone.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe o telefone!", Toast.LENGTH_LONG).show();
        } else {
            database databaseHelper = new database(getActivity());
            Contatos c = new Contatos();
            c.setId(id);
            String TipoTelefoneNome = sTipoTelefone.getSelectedItem().toString();
            c.setTipoTelefone(listTipoTelefoneId.get(listTipoTelefoneNome.indexOf(TipoTelefoneNome)));
            c.setNome(etNome.getText().toString());
            c.setTelefone(etTelefone.getText().toString());
            databaseHelper.updateContatos(c);
            Toast.makeText(getActivity(), "Contato editado!", Toast.LENGTH_LONG).show();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameContatos, new Listar()).commit();
        }
    }

    private void excluir (int id) {
        c = new Contatos();
        c.setId(id);
        databaseHelper.deleteContato(c);
        Toast.makeText(getActivity(), "Contato excluído!", Toast.LENGTH_LONG).show();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameContatos, new Listar()).commit();
    }
}
