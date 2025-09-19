package com.example.listycitylab3;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
// Make sure this import is present
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

// Implement the new EditCityDialogListener
public class MainActivity extends AppCompatActivity implements AddCityFragment.AddCityDialogListener, EditCityFragment.EditCityDialogListener {

    private ArrayList<City> dataList;
    private ListView cityList;
    private CityArrayAdapter cityAdapter; // Use your custom CityArrayAdapter

    @Override
    public void addCity(City city) {
        cityAdapter.add(city);        // cityAdapter.notifyDataSetChanged(); // Not strictly needed as cityAdapter.add() often calls it
    }

    // Implement the method from EditCityDialogListener
    @Override
    public void editCity(City city, int position) {
        dataList.set(position, city); // Replace the city at the given position
        cityAdapter.notifyDataSetChanged(); // Refresh the ListView
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] cities = {"Edmonton", "Vancouver", "Toronto"};
        String[] provinces = {"AB", "BC", "ON"};

        dataList = new ArrayList<>();
        for (int i = 0; i < cities.length; i++) {
            dataList.add(new City(cities[i], provinces[i]));
        }

        cityList = findViewById(R.id.city_list);
        cityAdapter = new CityArrayAdapter(this, dataList); // Use your CityArrayAdapter
        cityList.setAdapter(cityAdapter);

        FloatingActionButton fab = findViewById(R.id.button_add_city);
        fab.setOnClickListener(v -> {
            new AddCityFragment().show(getSupportFragmentManager(), "ADD_CITY_FRAGMENT");
        });

        // Add OnItemClickListener for the ListView
        cityList.setOnItemClickListener((parent, view, position, id) -> {
            City cityToEdit = dataList.get(position); // Get the city to be edited
            // Create a new EditCityFragment instance and pass the city and position
            EditCityFragment.newInstance(cityToEdit, position).show(getSupportFragmentManager(), "EDIT_CITY_FRAGMENT");
        });
    }
}
