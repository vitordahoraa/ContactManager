package com.example.gerenciadorcontatos.Contatos;


import com.example.gerenciadorcontatos.database.database;
import com.example.gerenciadorcontatos.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class Listar extends Fragment {

    public Listar() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.contatos_listar, container, false);
        database databaseHelper = new database(getActivity());
        ListView lv = v.findViewById(R.id.listViewContatos);
        databaseHelper.getAllContatos(getContext(), lv);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tvId = view.findViewById(R.id.textViewIdListContato);
                Bundle b = new Bundle();
                b.putInt("id", Integer.parseInt(tvId.getText().toString()));

                Editar editar = new Editar();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                editar.setArguments(b);
                ft.replace(R.id.frameContatos, editar).commit();
            }
        });

        return v;
    }
}


