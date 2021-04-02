`SimpleButterKnife` 是参考于框架<a href="https://github.com/JakeWharton/butterknife">butterknife</a>手写的，主要实现了Android View初始化与点击事件绑定，使用相对简单些。


一、在Application中的初始化
```

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化
        XBinding.init(this);
    }
}

```


二、在Activity添加布局注解`@Builder`，其他与butterknife的使用是一样的



```
//xml布局注解
@Builder(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {
    //view初始化绑定
    @BindView(R.id.tv_hello)
    TextView mTvHello;
    @BindView(R.id.tv_butter_knife)
    TextView mTvButterKnife;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTvHello.setText("hello x-binding");//222000


    }

    //点击事件绑定
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

```

