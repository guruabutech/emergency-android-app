package com.pk.eager.ReportObject;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by kimpham on 7/9/17.
 */

public class Report implements Parcelable {
    String type;
    ArrayList<Question> questions;

    //constructors
    public Report(String type, ArrayList<Question> question){
        this.type = type;
        this.questions = question;
    }

    public boolean isEmpty(){
        for(Question q : questions){
            if(q.empty())
                return true;
        }
        return false;
    }


    //class methods
    public String getType(){
        return type;
    }

    public ArrayList<Question> getQuestions(){
        return questions;
    }

    public void addReportItem(Question item){
        questions.add(item);
    }

    public void setSingleChoice(int questionNum, Choice choice){
        if(questions.get(questionNum).choice.size() > 0)
            questions.get(questionNum).choice.remove(0);
        questions.get(questionNum).choice.add(choice);
    }

    public void setMultiChoice(int questionNum, Choice choice){
        if(!questions.get(questionNum).choice.contains(choice))
            questions.get(questionNum).choice.add(choice);

    }

    public void removeOneChoiceQuestion(int questionNum){
        questions.get(questionNum).choice.remove(0);
    }

    public void removeMultiChoiceQuestion(int questionNum, Choice toRemoveChoice){
        if(questions.get(questionNum).choice.contains(toRemoveChoice)){
            questions.get(questionNum).choice.remove(toRemoveChoice);
        }
    }

    public void addSubChoice(int question, String choice, String subchoice){
        int c = questions.get(question).choice.indexOf(new Choice(choice));
        if(c!=-1)
            questions.get(question).choice.get(c).subItems.add(subchoice);
    }

    public void replaceSubChoice(int question, String choice, String subchoice) {
        ArrayList<String > sub = new ArrayList<>();
        sub.add(subchoice);
        int c = questions.get(question).choice.indexOf(new Choice(choice));
        if(c!=-1)
            questions.get(question).choice.get(c).subItems = sub;
    }

    public void removeSubChoice(int question, String choice, String subchoice){
        int c = questions.get(question).choice.indexOf(new Choice(choice));
        questions.get(question).choice.get(c).subItems.remove(subchoice);
    }

    public String toString(){
        if(questions.size() == 0)
            return "";
        String s = "";
        for(Question q: questions){
            if(q.choice.size() != 0)
                s+=q.toString()+"\n";
        }
        if(s.isEmpty())
            return "";
        return s;
    }

    //Parceble implementation
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(type);
        dest.writeList(questions);
    }

    public static final Parcelable.Creator<Report> CREATOR
            = new Parcelable.Creator<Report>() {
        public Report createFromParcel(Parcel in) {
            return new Report(in);
        }

        public Report[] newArray(int size) {
            return new Report[size];
        }
    };

    private Report(Parcel in) {
        type = in.readString();
        questions = in.readArrayList(Question.class.getClassLoader());
    }

}
