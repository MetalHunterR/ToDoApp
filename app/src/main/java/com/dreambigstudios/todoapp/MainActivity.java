package com.dreambigstudios.todoapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> Items;
    private ArrayAdapter<String> ItemsAdapter;
    private ListView LVItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LVItems = (ListView) findViewById(R.id.lvItems);
        Items = new ArrayList<String>();
        OpenFile();

        ItemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Items);
        LVItems.setAdapter(ItemsAdapter);

        setupListViewListener();
    }

    public void onAddItem(View v) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();

        if(!itemText.isEmpty())
            ItemsAdapter.add(itemText);
        else
            Toast.makeText(this, "Can't add empty line", Toast.LENGTH_SHORT).show();

        etNewItem.setText("");

        WriteFile();
    }

    private void setupListViewListener() {
        LVItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Items.remove(i);
                ItemsAdapter.notifyDataSetChanged();
                WriteFile();

                return true;
            }
        });
    }

    private void OpenFile() {
        File filesDir = getFilesDir();
        File listFile = new File(filesDir, "list.txt");

        try {
            Items = new ArrayList<String>(FileUtils.readLines(listFile));
        } catch (IOException e) {
            Items = new ArrayList<String>();
        }
    }

    private void WriteFile() {
        File filesDir = getFilesDir();
        File listFile = new File(filesDir, "list.txt");

        try {
            FileUtils.writeLines(listFile, Items);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
