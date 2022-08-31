package com.example.androidlearn.dataSave;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidlearn.R;

/**
 * SharedPreferences的使用
 * SharedPreferences其实最终存储到xml文件中，这个文件位于你程序包名下的shared_prefs文件夹中
 */
public class SharePreferencesJavaActivity extends AppCompatActivity {

    private EditText mEditText;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_share_preferences);

        mEditText = findViewById(R.id.mEt);
        sharedPreferences = getSharedPreferences("book", MODE_PRIVATE);

        String content = sharedPreferences.getString("text", "");
        mEditText.setText(content);
    }

    @Override
    protected void onStop() {
        super.onStop();

        // 此处保存数据
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("text", mEditText.getText().toString());
        editor.apply();
    }
}
