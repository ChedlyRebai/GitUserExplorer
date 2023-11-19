package com.chedly.git_app.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.app.Activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chedly.git_app.R;

import java.net.URL;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UsersListViewModel extends ArrayAdapter<GitUser> {
    private int resource;
    private List<GitUser> users;
    public UsersListViewModel(@NonNull Context context, int resource, List<GitUser> data) {
        super(context, resource,data);
        this.users=data;
        this.resource=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listViewItem=convertView;
        if(listViewItem==null){
            listViewItem= LayoutInflater.from(getContext()).inflate(resource,parent,false);
        }
        CircleImageView imageViewUser=listViewItem.findViewById(R.id.imageviewUser);
        TextView textViewLogin=listViewItem.findViewById(R.id.textviewLogin);
        TextView textViewScore=listViewItem.findViewById(R.id.textviewScore);
        textViewLogin.setText(getItem(position).login);
        textViewScore.setText(String.valueOf(getItem(position).score));
        new Thread(()->{
            try {
                URL url=new URL(getItem(position).avatarUrl);
                Bitmap bitmap= BitmapFactory.decodeStream(url.openStream());
                ((Activity)getContext()).runOnUiThread(()->{
                    imageViewUser.setImageBitmap(bitmap);
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        //imageViewUser.setImageBitmap();
        return listViewItem;
    }

}