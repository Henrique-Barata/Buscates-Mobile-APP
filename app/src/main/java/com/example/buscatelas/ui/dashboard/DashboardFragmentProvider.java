package com.example.buscatelas.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.buscatelas.ClientActivity;
import com.example.buscatelas.Login;
import com.example.buscatelas.R;
import com.example.buscatelas.Utils.Authentication;
import com.example.buscatelas.Utils.Database;
import com.example.buscatelas.databinding.FragmentDashboardBinding;
import com.example.buscatelas.models.Client;
import com.example.buscatelas.models.ServiceProvider;
import com.google.firebase.auth.FirebaseUser;

import java.security.Provider;

public class DashboardFragmentProvider extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private FragmentDashboardBinding binding;
    private Database databs;

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
        //FirebaseUser pro = firebaseAuth.getCurrentUser();
        //ServiceProvider provider = databs.getServiceProviderById(pro.getUid());

        TextView myTextView = (TextView) root.findViewById(R.id.workerName);
        //myTextView.setText(cli.getName());
        myTextView.setText("Pedro Alves");
        TextView myTextView2 = (TextView) root.findViewById(R.id.workerEmail);
        //myTextView2.setText(cli.getEmail());
        myTextView2.setText("Pedrocas1904@gmail.com");
        TextView myTextView3 = (TextView) root.findViewById(R.id.textView24);
        //myTextView3.setText(cli.getPhoneNumber());
        myTextView3.setText("964 789 020");

        Button about = root.findViewById(R.id.button10);

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getActivity()
                        .getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_activity_provider, new about());
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


    public void lixo(View root) {
        RadioButton radio1 = (RadioButton) root.findViewById(R.id.radioButton1);

        radio1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Success!",
                        Toast.LENGTH_SHORT).show();
            }
        });

    }

}