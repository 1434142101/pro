package com.example.a14341.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;

public class AnswerActivity extends AppCompatActivity {

    private TextView mAnswerTextView;
    private static final String EXTRA_ANSWER_SHOWN="answer_shown";//返回的键值
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();//隐藏标题栏
        setContentView(R.layout.activity_answer);

        mAnswerTextView=findViewById(R.id.Answer_Text_View);
        Intent date=getIntent();//获取传过来的Intent对象
        String answer=date.getStringExtra("msg");//
        mAnswerTextView.setText(answer);//显示到文本组件中
        date.putExtra(EXTRA_ANSWER_SHOWN,"您已查看了答案");
        setResult(Activity.RESULT_OK,date);
        mImageView=findViewById(R.id.imageView);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        mImageView.setImageResource(R.drawable.animation_frame);
        AnimationDrawable animationDrawable = (AnimationDrawable) mImageView.getDrawable();
        animationDrawable.start();//启动动画
    }
}
