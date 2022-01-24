package com.newtech.newtech_sfm.Fragement;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.newtech.newtech_sfm.R;

public class PrisePhoto extends Fragment {

    private PrisePhotoViewModel mViewModel;
    private ViewReferencement.OnFragmentInteractionListener onFragmentInteractionListener;

    public static PrisePhoto newInstance() {
        return new PrisePhoto();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.prise_photo_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(PrisePhotoViewModel.class);
        // TODO: Use the ViewModel
    }



}
