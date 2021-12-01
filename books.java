package com.example.assignment;

public class books {
    private String Name;
    private int Marks;
    private int Rollno;

    public books(String Name,int Marks,int Rollno) {
        this.Name=Name;
        this.Marks=Marks;
        this.Rollno=Rollno;

    }

    public String getName() {
        return Name;
    }

    public int getMarks() {
        return Marks;
    }

    public int getRollno() {
        return Rollno;
    }
}
