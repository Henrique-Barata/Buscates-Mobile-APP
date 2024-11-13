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

import com.example.buscatelas.Utils.Database;
import com.example.buscatelas.models.ServiceProvider;
import com.example.buscatelas.ui.Client.Lista_Completos_Servicos_client;
import com.example.buscatelas.ui.home.HomeFragment;
import com.example.buscatelas.ui.maps.MapsFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link maps_percurso_client#newInstance} factory method to
 * create an instance of this fragment.
 */
public class maps_percurso_client extends Fragment {

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


    public maps_percurso_client() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment maps_percurso.
     */
    // TODO: Rename and change types and number of parameters
    public static maps_percurso_client newInstance(String param1, String param2) {
        maps_percurso_client fragment = new maps_percurso_client();
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

        View view = inflater.inflate(R.layout.fragment_maps_percurso_client, container, false);

        if(container!= null){
            container.removeAllViews();
        }

        databs = new Database();

        closeButton = view.findViewById(R.id.button3);
        closeButton.setOnClickListener(new View.OnClickListener() {


        MapsFragment maps = new MapsFragment();

        FragmentContainerView mapsContainer = view.findViewById(R.id.mapsContainer2);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        //transaction.add(R.id.mapsContainer, maps);

            public void onClick(View v){
                builder = new AlertDialog.Builder(getContext());

                builder.setMessage("Henrique da Silva - Electric Job")
                        //adicionar os dados do provider
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

        closeButton = view.findViewById(R.id.button3);
        closeButton.setOnClickListener(new View.OnClickListener() {


            MapsFragment maps = new MapsFragment();

            FragmentContainerView mapsContainer = view.findViewById(R.id.mapsContainer2);
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            //transaction.add(R.id.mapsContainer, maps);

            public void onClick(View v){
                builder = new AlertDialog.Builder(getContext());

                builder.setMessage("João dos Silvas - Electric Job - 3.5 Avg Rating")
                        //adicionar os dados do provider
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

        Button passwordBtn = view.findViewById(R.id.button2);

        passwordBtn.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getActivity()
                        .getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_activity_client, new HomeFragment());
                fragmentTransaction.commit();
            }
        });

        ImageButton end = view.findViewById(R.id.imageButton7);

        end.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getActivity()
                        .getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_activity_client, new rate_provider());
                fragmentTransaction.commit();
            }
        });

        return view;
    }
}