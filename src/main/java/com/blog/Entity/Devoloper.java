package com.blog.Entity;

public class Devoloper {
    private String name;
    private String studentId;
    private int years;
    private String department;


    public void setName(String name) {
        this.name = name;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public void setYears(int years) {
        this.years = years;
    }

    public void setDepartment(String department) {
        this.department = department;
    }



    public String getName() {
        return name;
    }

    public String getStudentId() {
        return studentId;
    }

    public int getYears() {
        return years;
    }

    public String getDepartment() {
        return department;
    }

    @Override
    public String toString() {
        return "Devoloper{" +
                "name='" + name + '\'' +
                ", studentId='" + studentId + '\'' +
                ", years=" + years +
                ", department='" + department + '\'' +
                '}';
    }
}
