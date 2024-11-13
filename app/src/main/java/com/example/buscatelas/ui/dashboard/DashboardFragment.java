package com.example.buscatelas.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.buscatelas.ClientActivity;
import com.example.buscatelas.R;
import com.example.buscatelas.Utils.Authentication;
import com.example.buscatelas.databinding.FragmentDashboardBinding;

import com.example.buscatelas.models.Client;
import com.example.buscatelas.ui.Client.Lista_Completos_Servicos_client;
import com.example.buscatelas.ui.settings.ChangeEmailFragment;
import com.example.buscatelas.ui.worker.Lista_Completos_Servicos_worker;
import com.example.buscatelas.ui.worker.Lista_pedidosPendentes_worker;
import com.example.buscatelas.ui.worker.Servico_worker;
import com.example.buscatelas.ui.worker.lista_servicos_worker;
import com.google.firebase.auth.FirebaseUser;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private FragmentDashboardBinding binding;
    private ClientActivity databs;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        if(container!= null){
            container.removeAllViews();
        }

        //tirar da base de dados os dados

        //Authentication firebaseAuth = new Authentication(getActivity());
        //FirebaseUser client = firebaseAuth.getCurrentUser();
        //Client cli = databs.getClientById(client.getUid());

        TextView myTextView = (TextView) root.findViewById(R.id.workerName);
        //myTextView.setText(cli.getName());
        myTextView.setText("José Medeiros");
        TextView myTextView2 = (TextView) root.findViewById(R.id.workerEmail);
        //myTextView2.setText(cli.getEmail());
        myTextView2.setText("zezinho@gmail.com");
        TextView myTextView3 = (TextView) root.findViewById(R.id.textView24);
        //myTextView3.setText(cli.getPhoneNumber());
        myTextView3.setText("965 554 237");


        Button about = root.findViewById(R.id.button10);

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getActivity()
                        .getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_activity_client, new about());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}