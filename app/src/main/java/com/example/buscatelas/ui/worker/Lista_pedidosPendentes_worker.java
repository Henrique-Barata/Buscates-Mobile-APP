package com.example.buscatelas.ui.worker;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.buscatelas.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Lista_pedidosPendentes_worker#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Lista_pedidosPendentes_worker extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ListView lv;
    ArrayList<String> al;
    ArrayAdapter<String> aa;

    public Lista_pedidosPendentes_worker() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Lista_pedidosPendentes_worker.
     */
    // TODO: Rename and change types and number of parameters
    public static Lista_pedidosPendentes_worker newInstance(String param1, String param2) {
        Lista_pedidosPendentes_worker fragment = new Lista_pedidosPendentes_worker();
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

        View view = inflater.inflate(R.layout.fragment_lista_pedidos_pendentes_worker, container, false);

        if(container!= null){
            container.removeAllViews();
        }

        lv = (ListView) view.findViewById(R.id.listaPedidos);

        al = new ArrayList<String>();
        aa = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_activated_1,al);
        lv.setAdapter(aa);
        al.add("Maria- 24/12/22-18:30h");
        al.add("Maria- 24/12/22-18:30h");
        al.add("Maria- 24/12/22-18:30h");



        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment_activity_client, new Aceitar_Pedido_worker());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;
    }
}