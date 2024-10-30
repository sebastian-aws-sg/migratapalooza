/*
 * Created on Aug 18, 2005
 *
 *
 */
package com.mytelco.client;

import org.apache.struts.action.ActionForm;

/**
 *
 * @author myTelco
 * @version 1.0
 */
public class HardwareCMForm extends ActionForm implements java.io.Serializable {

	private String hardwareId;
	private String mouseId;
	private String keyboardId;
	private String cpuId;
	private String monitorId;
	private Integer mouseInsValue;
	private Integer keyboardInsValue;
	private Integer cpuInsValue;
	private Integer monitorInsValue;
	
   private String logout = "false";


    /** Creates new form_logon */
    public HardwareCMForm() {
    }


    public String getHardwareId() {
        return hardwareId;
    }

    public void setDeveloperId(String hardwareId) {
        this.hardwareId = hardwareId;
    }

    public String getMouseId() {
        return mouseId;
    }

    public void setMouseId(String mouseId) {
        this.mouseId = mouseId;
    }

    public String getKeyboardId() {
        return keyboardId;
    }

    public void setKeyboardId(String keyboardId) {
        this.keyboardId = keyboardId;
    }

    public String getCpuId() {
        return cpuId;
    }

    public void setCpuId(String cpuId) {
        this.cpuId = cpuId;
    }

    public String getMonitorId() {
        return monitorId;
    }

    public void setMonitorId(String monitorId) {
        this.monitorId = monitorId;
    }

    public Integer getMouseInsValue() {
        return mouseInsValue;
    }

    public void setMouseInsValue(Integer mouseInsValue) {
        this.mouseInsValue = mouseInsValue;
    }

    public Integer getCpuInsValue() {
        return cpuInsValue;
    }

    public void setCpuInsValue(Integer cpuInsValue) {
        this.cpuInsValue = cpuInsValue;
    }

    public Integer getKeyboardInsValue() {
        return keyboardInsValue;
    }

    public void setKeyboardInsValue(Integer keyboardInsValue) {
        this.keyboardInsValue = keyboardInsValue;
    }

    public Integer getMonitorInsValue() {
        return monitorInsValue;
    }

    public void setMonitorInsValue(Integer monitorInsValue) {
        this.monitorInsValue = monitorInsValue;
    }

    public String getLogout() {
		return logout;
	}

	public void setLogout(String logout) {
		this.logout = logout;
	}
}

