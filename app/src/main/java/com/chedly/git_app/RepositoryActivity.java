package com.chedly.git_app;

import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.chedly.git_app.model.GitRepo;
import com.chedly.git_app.service.GitRepoServiceAPI;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class RepositoryActivity extends AppCompatActivity {
    List<String> data=new ArrayList<>();
        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.repository_layout);
            Intent intent=getIntent();
            String login=intent.getStringExtra("user.login");
            setTitle("Repositories");
            TextView textViewLogin=findViewById(R.id.textviewuserlogin);
            ListView listViewRepositories=findViewById(R.id.listviewrepositories);
            ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,data);
            listViewRepositories.setAdapter(arrayAdapter);
            textViewLogin.setText(login);
            Retrofit retrofit=new Retrofit.Builder()
                    .baseUrl("https://api.github.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            GitRepoServiceAPI gitRepoServiceAPI=retrofit.create(GitRepoServiceAPI.class);
            Call<List<GitRepo>> reposCall=gitRepoServiceAPI.userRepositories(login);
            reposCall.enqueue(new Callback<List<GitRepo>>() {
                @Override
                public void onResponse(Call<List<GitRepo>> call, Response<List<GitRepo>> response) {
                    if(!response.isSuccessful()){
                        Log.e("error",String.valueOf(response.code()));
                        return;
                    }
                    List<GitRepo> gitRepos=response.body();
                    for(GitRepo gitRepo:gitRepos){
                        String content="";
                        content+=gitRepo.id+"\n";
                        content+=gitRepo.name+"\n";
                        content+=gitRepo.language+"\n";
                        content+=gitRepo.size+"\n";
                        data.add(content);
                    }
                    arrayAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<List<GitRepo>> call, Throwable t) {

                }
            });
        }



}