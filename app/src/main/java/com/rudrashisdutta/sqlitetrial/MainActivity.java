package com.rudrashisdutta.sqlitetrial;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rudrashisdutta.sqlitetrial.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private EditText key;
    private EditText value;
    private Button save;
    private FloatingActionButton order;
    private ListView storage;

    private DataBase dataBase;

    private String KEY = null;
    private String VALUE = null;
    private String ORDER = null;
    private boolean isAsc;

    private LinkedHashMap<String, String> data = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        key = binding.key;
        value = binding.value;;
        save = binding.save;
        order = binding.order;
        storage = binding.storage;
        dataBase = new DataBase(this);
        setIsAsc(true);

        key.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                KEY = charSequence.toString();
                KEY = KEY.trim();
                if(KEY.isEmpty()){
                    KEY = null;
                } else{
                    KEY = KEY.replaceAll("'", "''");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        value.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                VALUE = charSequence.toString();
                VALUE = VALUE.trim();
                if(VALUE.isEmpty()){
                    VALUE = null;
                } else{
                    VALUE = VALUE.replaceAll("'", "''");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        save.setOnClickListener(view -> {
            if(KEY != null && VALUE != null) {
                dataBase.save(KEY, VALUE);
                updateList(ORDER);
            }
            key.setText(null);
            value.setText(null);
        });
        save.setOnLongClickListener(view -> {
            try {
                dataBase.empty();
                updateList(ORDER);
            } catch (Exception e){
                e.printStackTrace();
            }
            return true;
        });

        order.setOnClickListener(view -> setIsAsc(!isAsc));

        storage.setOnItemClickListener((adapterView, view, i, l) -> {
            updateList(ORDER);
            String KEY = ((TextView)view).getText().toString();
            String VALUE = data.get(KEY);
            Intent showDetails = new Intent(getApplicationContext(), Value.class);
            showDetails.putExtra(Value.KEY, KEY);
            showDetails.putExtra(Value.VALUE, VALUE);
            startActivity(showDetails);
        });
        storage.setOnItemLongClickListener((adapterView, view, i, l) -> {
            try {
                String KEY = ((TextView)view).getText().toString();
                dataBase.delete(KEY);
                updateList(ORDER);
            } catch (Exception e){
                e.printStackTrace();
            }
            return true;
        });
    }

    private void setIsAsc(boolean isAsc){
        if(isAsc){
            this.isAsc = true;
            ORDER = DataBase.ASCENDING;
            order.animate().rotation(180).setDuration(100).start();
        } else{
            this.isAsc = false;
            ORDER = DataBase.DESCENDING;
            order.animate().rotation(0).setDuration(100).start();
        }
        try{
            updateList(ORDER);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private boolean updateList(String order){
        boolean success = false;
        try{
            data = dataBase.get(order);
            List<String> keys = new ArrayList<>(data.keySet());
            Log.d("LIST", keys.toString());
            ListAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, keys);
            storage.setAdapter(adapter);
            success = true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return success;
    }
}