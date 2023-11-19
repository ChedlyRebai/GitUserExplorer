package com.chedly.git_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.chedly.git_app.model.GitUser;
import com.chedly.git_app.model.GitUsersResponse;
import com.chedly.git_app.model.UsersListViewModel;
import com.chedly.git_app.service.GitRepoServiceAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    List<GitUser> data=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editTextQuery=findViewById(R.id.editTextQuery);
        Button buttonSearch=findViewById(R.id.buttonSearch);
        ListView listViewUsers=findViewById(R.id.listviewusers);
        //ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,data);
        UsersListViewModel listviewmodal=new UsersListViewModel(this,R.layout.users_list_view_layout,data);
        listViewUsers.setAdapter(listviewmodal);
        final Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query=editTextQuery.getText().toString();
                Log.i("",query);
                GitRepoServiceAPI gitRepoServiceAPI=retrofit.create(GitRepoServiceAPI.class);
                Call<GitUsersResponse> callGitUsers=gitRepoServiceAPI.searchUsers(query);
                callGitUsers.enqueue(new Callback<GitUsersResponse>() {
                    @Override
                    public void onResponse(Call<GitUsersResponse> call, Response<GitUsersResponse> response) {
                        Log.i("info",call.request().url().toString());
                        //Toast.makeText(MainActivity.this,query, Toast.LENGTH_LONG).show();
                        if(!response.isSuccessful()){
                            //Toast.makeText(MainActivity.this,"sucees" , Toast.LENGTH_LONG).show();
                            Log.i("indo",String.valueOf(response.code()));
                        }

                        GitUsersResponse gitUsersResponse= response.body();
                        for(GitUser user:gitUsersResponse.users){
                            data.add(user);
                        }

                        listviewmodal.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<GitUsersResponse> call, Throwable t) {
                       // Toast.makeText(MainActivity.this,"fail" , Toast.LENGTH_LONG).show();
                        Log.e("error","Error");
                    }
                });

            }


        });


        listViewUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String login=data.get(position).login;
                Log.i("info",login);
                Intent intent=new Intent(getApplicationContext(), RepositoryActivity.class);
                intent.putExtra("user.login",login);
                startActivity(intent);
            }
        });


    }
}