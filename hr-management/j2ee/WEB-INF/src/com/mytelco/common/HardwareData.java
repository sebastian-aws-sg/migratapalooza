/*
 * Created on Oct 24, 2005
 *
 *  
 */
package com.mytelco.common;


/**
 * @author myTelco 
 * 
 * 
 */
public class HardwareData {

    private String mouseId;

    private String hardwareId;

    private String keyboardId;

    private String cpuId;

    private String monitorId;

    private int mouseInsValue;

    private int keyboardInsValue;

    private int cpuInsValue;
    
    private int monitorInsValue;
        
    
    public String gethardwareId() {
        return hardwareId;
    }

    public void sethardwareId(String hardwareId) {
        this.hardwareId = hardwareId;
    }

    
    public String getmouseId() {
        return mouseId;
    }

    public void setmouseId(String mouseId) {
        this.mouseId = mouseId;
    }

    
    public String getkeyboardId() {
        return keyboardId;
    }

    public void setkeyboardId(String keyboardId) {
        this.keyboardId = keyboardId;
    }

    
    public String getmonitorId() {
        return monitorId;
    }

    public void setmonitorId(String monitorId) {
        this.monitorId = monitorId;
    }

    
    public String getcpuId() {
        return cpuId;
    }

    public void setcpuId(String cpuId) {
        this.cpuId = cpuId;
    }

    
    public int getmouseInsValue() {
        return mouseInsValue;
    }

    public void setmouseInsValue(int mouseInsValue) {
        this.mouseInsValue = mouseInsValue;
    }

    
    public int getkeyboardInsValue() {
        return keyboardInsValue;
    }

    public void setkeyboardInsValue(int keyboardInsValue) {
        this.keyboardInsValue = keyboardInsValue;
    }

    
    public int getmonitorInsValue() {
        return monitorInsValue;
    }

    public void setmonitorInsValue(int monitorInsValue) {
        this.monitorInsValue = monitorInsValue;
    }


    public int getcpuInsValue() {
        return cpuInsValue;
    }

    public void setcpuInsValue(int cpuInsValue) {
        this.cpuInsValue = cpuInsValue;
    }
    
}