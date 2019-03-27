package com.example.a14341.myapplication;

public class Question {
    private int mTextld;
    private boolean mAnswer;

    public Question(int textld, boolean answer) {
        mTextld = textld;
        mAnswer = answer;
    }

    public int getTextld() {
        return mTextld;
    }

    public void setTextld(int textld) {
        mTextld = textld;
    }

    public boolean isAnswer() {
        return mAnswer;
    }

    public void setAnswer(boolean answer) {
        mAnswer = answer;
    }
}
