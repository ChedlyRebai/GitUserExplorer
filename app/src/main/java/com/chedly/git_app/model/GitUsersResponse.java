    package com.chedly.git_app.model;

    import com.chedly.git_app.model.GitUser;
    import com.google.gson.annotations.SerializedName;

    import java.util.ArrayList;
    import java.util.List;

    public class GitUsersResponse {
        @SerializedName("total_count")
        public int totalCount;
        @SerializedName("items")
        public List<GitUser> users=new ArrayList<>();
    }