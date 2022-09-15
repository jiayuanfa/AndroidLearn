package com.example.androidlearn.ui;

import android.os.Bundle;
import android.os.FileUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidlearn.R;
import com.example.androidlearn.utils.GetJsonDataUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kunminx.linkage.LinkageRecyclerView;
import com.kunminx.linkage.bean.DefaultGroupedItem;

import java.util.List;

public class LinkRecycleViewJavaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_link_rv);

        LinkageRecyclerView linkageRecyclerView = findViewById(R.id.mRv);

        String jsonData = GetJsonDataUtil.getJson(this, "rv_data_json.json");

        List<DefaultGroupedItem> list = new Gson().fromJson(
                jsonData,
                new TypeToken<List<DefaultGroupedItem>>() {}.getType());
        linkageRecyclerView.init(list);
    }
}
