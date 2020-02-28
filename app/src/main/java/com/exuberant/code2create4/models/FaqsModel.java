package com.exuberant.code2create4.models;

public class FaqsModel {

    private int question;
    private int answer;

    public Integer getQuestion() {
        return question;
    }

    public void setQuestion(Integer question) {
        this.question = question;
    }

    public Integer getAnswer() {
        return answer;
    }

    public void setAnswer(Integer answer) {
        this.answer = answer;
    }

    public FaqsModel(int question, int answer) {
        this.question = question;
        this.answer = answer;
    }
}
