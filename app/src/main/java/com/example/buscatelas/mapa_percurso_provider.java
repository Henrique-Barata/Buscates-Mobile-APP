package com.example.buscatelas;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.buscatelas.Utils.Database;
import com.example.buscatelas.ui.maps.MapsFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link mapa_percurso_provider#newInstance} factory method to
 * create an instance of this fragment.
 */
public class mapa_percurso_provider extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Database databs;
    private Button closeButton;
    private AlertDialog.Builder builder;

    public mapa_percurso_provider() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment mapa_percurso_provider.
     */
    // TODO: Rename and change types and number of parameters
    public static mapa_percurso_provider newInstance(String param1, String param2) {
        mapa_percurso_provider fragment = new mapa_percurso_provider();
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
        View view = inflater.inflate(R.layout.fragment_mapa_percurso_provider, container, false);

        if(container!= null){
            container.removeAllViews();
        }

        databs = new Database();

        MapsFragment maps = new MapsFragment();

        FragmentContainerView mapsContainer = view.findViewById(R.id.mapsContainer2);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        //transaction.add(R.id.mapsContainer, maps);

        closeButton = view.findViewById(R.id.button5);
        closeButton.setOnClickListener(new View.OnClickListener() {


            public void onClick(View v){
                builder = new AlertDialog.Builder(getContext());

                builder.setMessage("Aníbal Vidreio    931245422")
                        //adicionar os dados do client
                        .setCancelable(false)
                        .setPositiveButton("", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        })
                        .setNegativeButton("voltar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Perform some action on click of NO button
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();

            }
        });

        ImageButton selecionaraceitar = view.findViewById(R.id.button6);

        selecionaraceitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getActivity()
                        .getSupportFragmentManager().beginTransaction();
                Toast.makeText(getContext(), "Trabalho terminado", Toast.LENGTH_SHORT).show();
                fragmentTransaction.replace(R.id.nav_host_fragment_activity_provider, new pag_final_request());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        return view;
    }
}