package com.test.table.mvvmarchitecture;



import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_BUSINESS_REQUEST = 1;
    public static final int EDIT_BUSINESS_REQUEST = 2;

    private BusinessViewModel businessViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton buttonAddBusiness = findViewById(R.id.button_add_business);
        buttonAddBusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEditBusinessActivity.class);
                startActivityForResult(intent, ADD_BUSINESS_REQUEST);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final BusinessAdapter adapter = new BusinessAdapter();
        recyclerView.setAdapter(adapter);

        businessViewModel = ViewModelProviders.of(this).get(BusinessViewModel.class);
        businessViewModel.getAllBusinesses().observe(this, new Observer<List<Business>>() {
            @Override
            public void onChanged(@Nullable List<Business> businesses) {
                adapter.submitList(businesses);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                businessViewModel.delete(adapter.getBusinessAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Business deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new BusinessAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Business business) {
                Intent intent = new Intent(MainActivity.this, AddEditBusinessActivity.class);
                intent.putExtra(AddEditBusinessActivity.EXTRA_ID, business.getId());
                intent.putExtra(AddEditBusinessActivity.EXTRA_TITLE, business.getTitle());
                intent.putExtra(AddEditBusinessActivity.EXTRA_DESCRIPTION, business.getDescription());
                intent.putExtra(AddEditBusinessActivity.EXTRA_PRIORITY, business.getPriority());
                startActivityForResult(intent, EDIT_BUSINESS_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_BUSINESS_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddEditBusinessActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddEditBusinessActivity.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(AddEditBusinessActivity.EXTRA_PRIORITY, 1);

            Business business = new Business(title, description, priority);
            businessViewModel.insert(business);

            Toast.makeText(this, "Business saved", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_BUSINESS_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddEditBusinessActivity.EXTRA_ID, -1);

            if (id == -1) {
                Toast.makeText(this, "Business can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            String title = data.getStringExtra(AddEditBusinessActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddEditBusinessActivity.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(AddEditBusinessActivity.EXTRA_PRIORITY, 1);

            Business business = new Business(title, description, priority);
            business.setId(id);
            businessViewModel.update(business);

            Toast.makeText(this, "Business updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Business not saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_businesses:
                businessViewModel.deleteAllBusinesses();
                Toast.makeText(this, "All businesses deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
