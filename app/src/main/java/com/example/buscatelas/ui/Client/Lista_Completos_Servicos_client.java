package com.example.buscatelas.ui.Client;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.buscatelas.R;
import com.example.buscatelas.Utils.Authentication;
import com.example.buscatelas.Utils.Database;
import com.example.buscatelas.models.Client;
import com.example.buscatelas.models.Request;
import com.example.buscatelas.ui.WorkerAndClient.ServicoCompleto;
import com.example.buscatelas.ui.worker.Servico_worker;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Lista_Completos_Servicos_client#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Lista_Completos_Servicos_client extends Fragment {

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
    private Database databs;

    public Lista_Completos_Servicos_client() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Lista_Completos_Servicos_client.
     */
    // TODO: Rename and change types and number of parameters
    public static Lista_Completos_Servicos_client newInstance(String param1, String param2) {
        Lista_Completos_Servicos_client fragment = new Lista_Completos_Servicos_client();
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

        View v = inflater.inflate(R.layout.fragment_lista__completos__servicos_client, container, false);
        if(container!= null){
            container.removeAllViews();
        }

        lv = (ListView) v.findViewById(R.id.listaSevicos);

        al = new ArrayList<String>();
        aa = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_activated_1,al);
        lv.setAdapter(aa);
        //Authentication firebaseAuth = new Authentication(getActivity());
        //FirebaseUser client = firebaseAuth.getCurrentUser();
        //Client cli = databs.getClientById(client.getUid());
        //ArrayList<Request> req = cli.getPastRequests();

        //for (Request request: req) {
        //    al.add(request.getSpecialization()); // mudar para a especialidade
        //}
        al.add("Plumber, Sr. Joaquim");
        al.add("Painting, Gabriel");
        al.add("Painting, Gabriel");


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentTransaction fragmentTransaction = getActivity()
                        .getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_activity_client, new ServicoCompleto());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return v;
    }
}