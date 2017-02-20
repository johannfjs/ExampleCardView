package com.jcodee.examplerecyclerview.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jcodee.examplerecyclerview.R;
import com.jcodee.examplerecyclerview.adapters.PersonaAdapter;
import com.jcodee.examplerecyclerview.interfaces.OnLoadMoreListener;
import com.jcodee.examplerecyclerview.models.Persona;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PersonaAdapter personaAdapter;
    private ArrayList<Persona> lista = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (int i = 0; i < 30; i++) {
            Persona user = new Persona();
            user.setNombre("Nombre " + i);
            user.setApellido("Apellido " + i);
            lista.add(user);
        }
        recyclerView = (RecyclerView) findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        personaAdapter = new PersonaAdapter(MainActivity.this, lista, recyclerView);
        recyclerView.setAdapter(personaAdapter);
        personaAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                lista.add(null);
                personaAdapter.notifyItemInserted(lista.size() - 1);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        lista.remove(lista.size() - 1);
                        personaAdapter.notifyItemRemoved(lista.size());
                        int index = lista.size();
                        int end = index + 20;
                        for (int i = index; i < end; i++) {
                            Persona user = new Persona();
                            user.setNombre("Nombre " + i);
                            user.setApellido("Apellido " + i);
                            lista.add(user);
                        }
                        personaAdapter.notifyDataSetChanged();
                        personaAdapter.setLoaded();
                    }
                }, 2000);
            }
        });
    }
}