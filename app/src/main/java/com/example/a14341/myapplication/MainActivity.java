package com.example.a14341.myapplication;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.MessageQueue;
import android.os.PatternMatcher;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button mButtonTrue;
    private Button mButtonFalse;
    private TextView mQuestionTextView;
    private Button mButtonNext;
    private Button mButtonAnswer;
    private int mQuestionsIndex = 0;
    private Question[] mQuestions=new Question[]{
            new Question(R.string.Q1,false),
            new Question(R.string.Q2,true),
            new Question(R.string.Q3,true),
            new Question(R.string.Q4,true),
            new Question(R.string.Q5,true),
            new Question(R.string.Q6,true),
            new Question(R.string.Q7,true),
            new Question(R.string.Q8,true),
    };
    private static final String TAG="MainActivity";//日志来源
    private static final String KEY_INDEX="index";//索引键
    private static final int REQUEST_CODE_ANSWER=10;//请求代码
    private TranslateAnimation mTranslateAnimation;//平移动画
    private AlphaAnimation mAlphaAnimation;


    private void updateQuestion() {
    int i = mQuestions[mQuestionsIndex].getTextld();
          mQuestionTextView.setText(i);
//        mTranslateAnimation=new TranslateAnimation(-10,10,0,0);
//        mTranslateAnimation.setDuration(50); //动画持续时间
//        mTranslateAnimation.setRepeatCount(5); //重复次数(不包括第一次)
//        mTranslateAnimation.setRepeatMode(Animation.REVERSE);//动画执行模式，Animation.RESTART:从头开始，Animation.REVERSE:逆序
//        mQuestionTextView.startAnimation(mTranslateAnimation);
          Animation set=AnimationUtils.loadAnimation(this,R.anim.animation_list);
          mQuestionTextView.startAnimation(set);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState!=null){
            mQuestionsIndex=savedInstanceState.getInt(KEY_INDEX,0);
            Log.d(TAG, "Bundlu恢复状态");
        }
        Log.i(TAG,"onCreate()创建界面");
        setContentView(R.layout.activity_main);
        mButtonTrue = findViewById(R.id.button_true);
        mButtonTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkQuestion(true);
            }
        });
        mButtonFalse = findViewById(R.id.button_false);
        mButtonFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkQuestion(false);
            }
        });
        mQuestionTextView = findViewById(R.id.textView);
        updateQuestion();//更新题目

        mButtonNext = findViewById(R.id.button_next);
        mButtonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mQuestionsIndex = (mQuestionsIndex + 1) % mQuestions.length;
                updateQuestion();
                mButtonNext.setEnabled(false);
                if (mQuestionsIndex == mQuestions.length - 1) {
                    Toast.makeText(MainActivity.this, R.string.last, Toast.LENGTH_SHORT).show();
                    mButtonNext.setText(R.string.TextView_reset);
                    updateButtonNext(R.drawable.ic_reset);
                }
                if (mQuestionsIndex == 0) {
                    mButtonNext.setText(R.string.Button_Next);
                    updateButtonNext(R.drawable.ic_you);
                }
            }
        });

        mButtonAnswer=findViewById(R.id.button_Tips);
        mButtonAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp;
                if (mQuestions[mQuestionsIndex].isAnswer()){
                    temp="正确";
                }else {
                    temp="错误";
                }
                Intent intent=new Intent(MainActivity.this,AnswerActivity.class);
                intent.putExtra("msg",temp);//数据附加到intent中
//                if (ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.CALL_PHONE)!=PackageManager.PERMISSION_DENIED){
//                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE},1);
//                }
//                     Intent intent=new Intent(Intent.ACTION_CALL)
//                     Intent.setData.Uri.parse("tel:13342840648"));
//                     Intent intent=new Intent(Intent.ACTION_DIAL);
                //startActivity(intent);
                startActivityForResult(intent,REQUEST_CODE_ANSWER);//返回启动办法
           }
       });
    }
    private void checkQuestion(boolean userAnswer)
    {
     boolean trueAnswer=mQuestions[mQuestionsIndex].isAnswer();
     int message;
     if(userAnswer==trueAnswer){
         message=R.string.yes;
         mButtonNext.setEnabled(true);
     }else{
        message=R.string.no;
        mButtonNext.setEnabled(false);
     }
     Toast.makeText(MainActivity.this,message,Toast.LENGTH_SHORT).show();
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void updateButtonNext(int imageID){
        Drawable d = getDrawable(imageID);
        d.setBounds(0,0,d.getMinimumWidth(),d.getMinimumHeight());
        mButtonNext.setCompoundDrawables(null,null,d,null);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart()使界面可见");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume()界面前台显示");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause()界面离开前台");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop()界面不可见");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy()销毁"+TAG);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "我又肥来了");
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG,"onSaveInstanceState()保存状态");
        outState.putInt(KEY_INDEX,mQuestionsIndex);//形成键值对

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_CODE_ANSWER && requestCode == Activity.RESULT_OK){
            String temp=data.getStringExtra("answer_shown");
            Toast.makeText(MainActivity.this,temp,Toast.LENGTH_LONG).show();
        }
    }
}
