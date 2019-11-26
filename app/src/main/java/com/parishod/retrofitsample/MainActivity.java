package com.parishod.retrofitsample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    public static final String BASE_URL = "http://dummy.restapiexample.com/api/v1/";
    private RecyclerView rv;
    List<Employee> employees;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter mAdapter;
    private ArrayList<String> mDataset = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv = findViewById(R.id.recycler_view);

        GridLayoutManager mGridLayoutManager = new GridLayoutManager(MainActivity.this, 2);
        rv.setLayoutManager(mGridLayoutManager);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        EmployeesGetApi service = retrofit.create(EmployeesGetApi.class);
        Call<List<Employee>> call = service.getEmployees();
        call.enqueue(new Callback<List<Employee>>() {
            @Override
            public void onResponse(Call<List<Employee>> call, Response<List<Employee>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                employees = response.body();
                for(Employee employee: employees){

                    mDataset.add(employee.getEmployee_name());
                }
                initRecyclerView();
            }

            @Override
            public void onFailure(Call<List<Employee>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Code: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    void initRecyclerView(){
        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(mDataset);
        rv.setAdapter(mAdapter);
    }
}
