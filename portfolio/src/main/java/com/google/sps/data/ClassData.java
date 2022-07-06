package com.google.sps.data;

public class ClassData{
    private String schoolName, department, professor, semester, className;
    private Long classYear,timestamp;
    public ClassData(String schoolName, String department, String professor, String semester, Long classYear, String className, Long timestamp){
        this.schoolName = schoolName;
        this.department = department;
        this.professor = professor;
        this.semester = semester;
        this.className = className;
        this.classYear = classYear;
        this.timestamp = timestamp;
    }

}