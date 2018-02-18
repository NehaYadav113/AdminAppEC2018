package com.dev.manan.adminappec2018;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Himanshu on 2/18/18.
 */

public class AddPostFragment extends DialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.add_post_fragment, container, false);
        getDialog().setTitle("Write Post");


        return rootView;
    }
}
