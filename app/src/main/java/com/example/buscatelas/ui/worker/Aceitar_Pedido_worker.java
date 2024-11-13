package com.example.buscatelas.ui.worker;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.buscatelas.R;
import com.example.buscatelas.mapa_percurso_provider;
import com.example.buscatelas.ui.home.HomeFragmentProvider;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Aceitar_Pedido_worker#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Aceitar_Pedido_worker extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Aceitar_Pedido_worker() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Aceitar_Pedido_worker.
     */
    // TODO: Rename and change types and number of parameters
    public static Aceitar_Pedido_worker newInstance(String param1, String param2) {
        Aceitar_Pedido_worker fragment = new Aceitar_Pedido_worker();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_aceitar__pedido_worker, container, false);
        if(container!= null){
            container.removeAllViews();
        }


        //ir buscar o cliente pelo id do serviço
      //  TextView textView = v.findViewById(R.id.textView9);

        //textView.setText("João Neves");
        //TextView textView1 = v.findViewById(R.id.textView10);
        //textView.setText("Bursted Pipes on the hall bathroom");

        Button selecionarvolta = v.findViewById(R.id.volta);

        selecionarvolta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getActivity()
                        .getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_activity_provider, new HomeFragmentProvider());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        Button selecionaraceitar = v.findViewById(R.id.aceitar);

        selecionaraceitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getActivity()
                        .getSupportFragmentManager().beginTransaction();
                Toast.makeText(getContext(), "Pedido Aceite", Toast.LENGTH_SHORT).show();
                fragmentTransaction.replace(R.id.nav_host_fragment_activity_provider, new mapa_percurso_provider());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        return v;
    }
}