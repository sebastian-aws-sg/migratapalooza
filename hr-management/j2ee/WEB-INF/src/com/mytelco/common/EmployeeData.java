/*
 * Created on Aug 19, 2005
 *
 *  
 */
package com.mytelco.common;

import java.util.Date;

/**
 * @author myTelco 
 * 
 * 
 */
public class EmployeeData {

    private String empNo;

    private String firstName;

    private String lastName;

    private String midinit;

    private String workDept;

    private String phoneNo;

    private Date hireDate;

    private String job;

    private int edLevel;

    private String sex;

    private Date birthDate;

    private double salary;

    private double bonus;

    private double comm;

    private Date createDate;

    /**
     * @return Returns the birthDate.
     */
    public Date getBirthDate() {
        return birthDate;
    }

    /**
     * @param birthDate
     *            The birthDate to set.
     */
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * @return Returns the bonus.
     */
    public double getBonus() {
        return bonus;
    }

    /**
     * @param bonus
     *            The bonus to set.
     */
    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    /**
     * @return Returns the comm.
     */
    public double getComm() {
        return comm;
    }

    /**
     * @param comm
     *            The comm to set.
     */
    public void setComm(double comm) {
        this.comm = comm;
    }

    /**
     * @return Returns the edLevel.
     */
    public int getEdLevel() {
        return edLevel;
    }

    /**
     * @param edLevel
     *            The edLevel to set.
     */
    public void setEdLevel(int edLevel) {
        this.edLevel = edLevel;
    }

    /**
     * @return Returns the empNo.
     */
    public String getEmpNo() {
        return empNo;
    }

    /**
     * @param empNo
     *            The empNo to set.
     */
    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    /**
     * @return Returns the firstName.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName
     *            The firstName to set.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return Returns the hireDate.
     */
    public Date getHireDate() {
        return hireDate;
    }

    /**
     * @param hireDate
     *            The hireDate to set.
     */
    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    /**
     * @return Returns the job.
     */
    public String getJob() {
        return job;
    }

    /**
     * @param job
     *            The job to set.
     */
    public void setJob(String job) {
        this.job = job;
    }

    /**
     * @return Returns the lastName.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName
     *            The lastName to set.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return Returns the midinit.
     */
    public String getMidinit() {
        return midinit;
    }

    /**
     * @param midinit
     *            The midinit to set.
     */
    public void setMidinit(String midinit) {
        this.midinit = midinit;
    }

    /**
     * @return Returns the phoneNo.
     */
    public String getPhoneNo() {
        return phoneNo;
    }

    /**
     * @param phoneNo
     *            The phoneNo to set.
     */
    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    /**
     * @return Returns the salary.
     */
    public double getSalary() {
        return salary;
    }

    /**
     * @param salary
     *            The salary to set.
     */
    public void setSalary(double salary) {
        this.salary = salary;
    }

    /**
     * @return Returns the sex.
     */
    public String getSex() {
        return sex;
    }

    /**
     * @param sex
     *            The sex to set.
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * @return Returns the workDept.
     */
    public String getWorkDept() {
        return workDept;
    }

    /**
     * @param workDept
     *            The workDept to set.
     */
    public void setWorkDept(String workDept) {
        this.workDept = workDept;
    }
    
    public Date getCreateDate() {
        return createDate;
    }
    
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}