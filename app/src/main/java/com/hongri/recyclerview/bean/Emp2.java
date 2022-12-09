package com.hongri.recyclerview.bean;

/**
 * @author：hongri
 * @date：12/9/22
 * @description：
 */
public class Emp2 {
    private int empno;
    private String ename;

    public int getEmpno() {
        return empno;
    }

    public void setEmpno(int empno) {
        this.empno = empno;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public Emp2(int empno, String ename) {
        super();
        this.empno = empno;
        this.ename = ename;
    }

    @Override
    public String toString() {
        return "empno:\t" + empno + "\tename:\t" + ename;
    }
}
