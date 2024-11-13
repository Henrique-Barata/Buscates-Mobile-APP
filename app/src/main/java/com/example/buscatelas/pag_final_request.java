package com.example.buscatelas;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;

import com.example.buscatelas.ui.home.HomeFragment;
import com.example.buscatelas.ui.home.HomeFragmentProvider;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link pag_final_request#newInstance} factory method to
 * create an instance of this fragment.
 */
public class pag_final_request extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public pag_final_request() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment pag_final_request.
     */
    // TODO: Rename and change types and number of parameters
    public static pag_final_request newInstance(String param1, String param2) {
        pag_final_request fragment = new pag_final_request();
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

        View view = inflater.inflate(R.layout.fragment_pag_final_request, container, false);

        if (container != null) {
            container.removeAllViews();
        }

        Button passwordBtn = view.findViewById(R.id.button11);
        RatingBar ratingBar = view.findViewById(R.id.ratingBar2);
        ratingBar.setRating(3.8f);
        passwordBtn.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getActivity()
                        .getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_activity_provider, new HomeFragmentProvider());
                fragmentTransaction.commit();
            }
        });
        return view;
    }
}