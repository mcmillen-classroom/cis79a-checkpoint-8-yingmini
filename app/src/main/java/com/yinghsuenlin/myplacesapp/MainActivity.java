package com.yinghsuenlin.myplacesapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private static final int REQUEST_PLACE_CREATE = 1;
    private static final int REQUEST_PLACE_EDIT = 2;
    private ListView mListView;
    private ArrayAdapter<Place> mArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView) findViewById(R.id.list_view);

        ArrayList<Place> myPlaces = new ArrayList<Place>();
        myPlaces.add(new Place("Laney College", "Near Lake Merritt"));
        myPlaces.add(new Place("Berkeley City College", "Near UC Berkeley"));
        myPlaces.add(new Place("College of Alameda", "On Alameda Island"));
        myPlaces.add(new Place("Merritt College", "Near Skyline"));

        mArrayAdapter = new PlaceArrayAdapter(this, myPlaces);

        mListView.setAdapter(mArrayAdapter);
        mListView.setOnItemClickListener(this);
        mListView.setOnCreateContextMenuListener(this);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.main_menu_create)
        {
            //td: launch the create place screen
            launchCreateActivity();
            return true;
        }

        return false;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item)
    {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        if (item.getItemId() == R.id.context_menu_edit)
        {
            Place place = mArrayAdapter.getItem(info.position);
            launchEditActivity(place, info.position);
            return true;
        }

        if (item.getItemId() == R.id.context_menu_delete)
        {
            Place place = mArrayAdapter.getItem(info.position);
            mArrayAdapter.remove(place);
        }

        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUEST_PLACE_CREATE)
        {
            if (data != null)
            {
                String name = data.getStringExtra("place_name");
                String description = data.getStringExtra("place_description");

                Place added = new Place(name, description);
                mArrayAdapter.add(added);
            }
        }

        if (resultCode == RESULT_OK && requestCode == REQUEST_PLACE_EDIT)
        {
            if (data != null)
            {
                int position = data.getIntExtra("place_position", -1);
                String name = data.getStringExtra("place_name");
                String description = data.getStringExtra("place_description");

                if (position != -1)
                {
                    Place place = mArrayAdapter.getItem(position);
                    place.setName(name);
                    place.setDescription(description);
                    mArrayAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    private void launchCreateActivity()
    {
        Intent createIntent = new Intent(this, PlaceCreateActivity.class);
        startActivityForResult(createIntent, REQUEST_PLACE_CREATE);
    }

    private void launchEditActivity(Place place, int position)
    {
        Intent editIntent = new Intent(this, PlaceEditActivity.class);
        editIntent.putExtra("place_position", position);
        editIntent.putExtra("place_name", place.getName());
        editIntent.putExtra("place_description", place.getDescription());
        startActivityForResult(editIntent, REQUEST_PLACE_EDIT);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
    {
        Place place = mArrayAdapter.getItem(position);
        launchEditActivity(place, position);

    }
}