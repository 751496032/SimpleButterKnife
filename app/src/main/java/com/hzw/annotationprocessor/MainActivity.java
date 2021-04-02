package com.hzw.annotationprocessor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.hzw.annotations.BindView;
import com.hzw.annotations.Builder;
import com.hzw.annotations.OnClick;


@Builder(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {
    @BindView(R.id.tv_hello)
    TextView mTvHello;
    @BindView(R.id.tv_butter_knife)
    TextView mTvButterKnife;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTvHello.setText("hello x-binding");//222000


    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @OnClick({R.id.btn_show_hello,R.id.btn_show_butter_knife,R.id.btn_goto_user})
    public void onViewClick(View view) {
        switch (view.getId()){
            case R.id.btn_show_hello:
                String hello = mTvHello.getText().toString();
                Toast.makeText(this, hello, Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_show_butter_knife:
                String butterKnife = mTvButterKnife.getText().toString();
                Toast.makeText(this, butterKnife, Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_goto_user:
                startActivity(new Intent(this,UserActivity.class));
                break;
        }

    }
}
