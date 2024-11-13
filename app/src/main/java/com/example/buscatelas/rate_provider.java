package com.example.buscatelas;

import android.media.MediaMetadata;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;

import com.example.buscatelas.Utils.Database;
import com.example.buscatelas.models.Request;
import com.example.buscatelas.models.ServiceProvider;
import com.example.buscatelas.ui.home.HomeFragment;

import java.security.Provider;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link rate_provider#newInstance} factory method to
 * create an instance of this fragment.
 */
public class rate_provider extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Database databs;
    private RatingBar ratingBar;

    public rate_provider() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment rate_provider.
     */
    // TODO: Rename and change types and number of parameters
    public static rate_provider newInstance(String param1, String param2) {
        rate_provider fragment = new rate_provider();
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
        View view = inflater.inflate(R.layout.fragment_rate_provider, container, false);

        if (container != null) {
            container.removeAllViews();
        }

        MyApplication app = (MyApplication) getActivity().getApplication();
        Request req = app.getCurrentRequest();

        databs = new Database();

        Button save = view.findViewById(R.id.button7);

        save.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                ServiceProvider provider = app.getCurrentProvider();
                int num = provider.getPastRequests().size();
                float rat = provider.getRating()/num;
                ratingBar = (RatingBar) view.findViewById(R.id.ratingBar3);
                rat += ratingBar.getRating()/num;
                provider.setRating(rat);

                FragmentTransaction fragmentTransaction = getActivity()
                        .getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_activity_client, new HomeFragment());
                fragmentTransaction.commit();
            }
        });
        //ServiceProvider prov = req.getServiceProvider();

        Button passwordBtn = view.findViewById(R.id.button7);

        passwordBtn.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getActivity()
                        .getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_activity_client, new HomeFragment());
                fragmentTransaction.commit();
            }
        });
        return view;
    }
}