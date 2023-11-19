package com.chedly.git_app.service;

import com.chedly.git_app.model.GitRepo;
import com.chedly.git_app.model.GitUsersResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GitRepoServiceAPI {
    @GET("search/users")
    public Call<GitUsersResponse> searchUsers(@Query("q") String query);
    @GET("users/{u}/repos")
    public Call<List<GitRepo>> userRepositories(@Path("u") String login);
}