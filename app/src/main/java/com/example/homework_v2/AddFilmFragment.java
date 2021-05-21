package com.example.homework_v2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.chip.Chip;

import java.util.ArrayList;
import java.util.List;


public class AddFilmFragment extends Fragment {

    Chip chip1;

    public static final String TAG = "NewsDetailedFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //при пересоздании фрагмент не будет убиваться
        setRetainInstance(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_film, container, false);

        Toolbar mToolbar = (Toolbar) view.findViewById(R.id.simple_toolbar);
        AppCompatActivity activity = (AppCompatActivity)getActivity();
        activity.setSupportActionBar(mToolbar);

        chip1 = (Chip) view.findViewById(R.id.chip1);
        chip1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(getActivity(), "Check changed to " + isChecked, Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        view.findViewById(R.id.tip1).setOnClickListener(v->{
//            //передаем инфо в 1 фрагмент
//            Bundle result = new Bundle();
//            result.putString("bundleKey", ((EditText) v).getText().toString());
//            getParentFragmentManager().setFragmentResult("key1", result);
//        }) ;

        view.findViewById(R.id.btn_add_film).setOnClickListener(v -> {
            EditText name = view.findViewById(R.id.tip1);
            EditText description = view.findViewById(R.id.tip2);
            Bundle result = new Bundle();
            result.putString("name", name.getText().toString());
            result.putString("description", description.getText().toString());
            getParentFragmentManager().setFragmentResult("key1", result);
            getParentFragmentManager().setFragmentResult("key1", result);

            Toast.makeText(getActivity(), "Фильм добавлен", Toast.LENGTH_SHORT).show();
        });

    }

}