package com.google.sps.data;

public class ClassData{
    public String schoolName, department, professor, className, semester;
    public Long classYear,timestamp;
    public ClassData(String schoolName, String department, String professor, String semester, Long classYear, String className){
        this.schoolName = schoolName;
        this.department = department;
        this.professor = professor;
        this.className = className;
        this.classYear = classYear;
        //this.timestamp = timestamp;
        this.semester = semester;
    }

    


}