package com.example.danielmurphy.kiddoemrclient;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class AndroidListViewCursorAdaptorActivity extends Activity {

    private TipsDbAdpater dbHelper;
    private SimpleCursorAdapter dataAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

        dbHelper = new TipsDbAdpater(this);
        dbHelper.open();

        //Generate ListView from SQLite Database
        displayListView();
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, AndroidListViewCursorAdaptorActivity.class);
        startActivity(i);

        finish();
    }

    private void displayListView() {
        Cursor cursor = dbHelper.fetchAllTips();

        // The desired columns to be bound
        String[] columns = new String[] {
                TipsDbAdpater.KEY_TEXT,
                TipsDbAdpater.KEY_DATE
        };

        // the XML defined views which the data will be bound to
        int[] to = new int[] {
                R.id.text,
                R.id.date
        };

        // create the adapter using the cursor pointing to the desired data
        //as well as the layout information
        dataAdapter = new SimpleCursorAdapter(
                this, R.layout.country_info,
                cursor,
                columns,
                to,
                0);

        ListView listView = (ListView) findViewById(R.id.listView1);
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);


        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View view,
                                    int position, long id) {
                // Get the cursor, positioned to the corresponding row in the result set
                Cursor cursor = (Cursor) listView.getItemAtPosition(position);

                // Get the tip from this row in the database.
                String tip =
                        cursor.getString(cursor.getColumnIndexOrThrow(TipsDbAdpater.KEY_TEXT));
                Toast.makeText(getApplicationContext(),
                        tip, Toast.LENGTH_SHORT).show();

            }
        });

        dataAdapter.setFilterQueryProvider(new FilterQueryProvider() {
            public Cursor runQuery(CharSequence constraint) {
                return dbHelper.fetchTipsByName(constraint.toString());
            }
        });

    }
}
