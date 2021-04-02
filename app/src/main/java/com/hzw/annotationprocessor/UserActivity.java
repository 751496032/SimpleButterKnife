package com.hzw.annotationprocessor;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hzw.annotations.BindView;
import com.hzw.annotations.Builder;

/**
 * author:HZWei
 * date:  2020/8/19
 * desc:
 */
@Builder(R.layout.activity_user)
public class UserActivity extends AppCompatActivity {

    @BindView(R.id.tv_user_name)
    TextView mTvUserName;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTvUserName.setText("老王");
    }
}
