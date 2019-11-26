package com.parishod.retrofitsample;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface EmployeesGetApi {

    @GET("employees")
    Call<List<Employee>> getEmployees();
}
