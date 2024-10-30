/*
 * Created on Oct 17, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtcc.dnv.mcx.beans;

import java.io.InputStream;

/**
 * Copyright © 2003 The Depository Trust & Clearing Company. All rights
 * reserved.
 * 
 * Depository Trust & Clearing Corporation (DTCC) 55, Water Street, New York, NY
 * 10048, U.S.A All Rights Reserved.
 * 
 * This software may contain (in part or full) confidential/proprietary
 * information of DTCC. ("Confidential Information"). Disclosure of such
 * Confidential Information is prohibited and should be used only for its
 * intended purpose in accordance with rules and regulations of DTCC.
 * 
 * @author Elango TR
 * @date Oct 17, 2007
 * @version 1.0
 * 
 * 
 */

public class UploadBean
{
    private String fileName = "";
    private String fileType = "";
    private int fileSize = 0;
    private String contentType = "";
    private InputStream inputStream = null;
    private String executedDate = "";
    private String cpViewable = "";
    private String mcaNames = "";
    
    /**
     * @return Returns the contentType.
     */
    public String getContentType()
    {
        return contentType;
    }
    /**
     * @param contentType The contentType to set.
     */
    public void setContentType(String contentType)
    {
        this.contentType = contentType;
    }
    /**
     * @return Returns the fileName.
     */
    public String getFileName()
    {
        return fileName;
    }
    /**
     * @param fileName The fileName to set.
     */
    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }
    /**
     * @return Returns the fileType.
     */
    public String getFileType()
    {
        return fileType;
    }
    /**
     * @param fileType The fileType to set.
     */
    public void setFileType(String fileType)
    {
        this.fileType = fileType;
    }
    /**
     * @return Returns the inputStream.
     */
    public InputStream getInputStream()
    {
        return inputStream;
    }
    /**
     * @param inputStream The inputStream to set.
     */
    public void setInputStream(InputStream inputStream)
    {
        this.inputStream = inputStream;
    }
    /**
     * @return Returns the fileSize.
     */
    public int getFileSize()
    {
        return fileSize;
    }
    /**
     * @param fileSize The fileSize to set.
     */
    public void setFileSize(int fileSize)
    {
        this.fileSize = fileSize;
    }    
    /**
     * @return Returns the executedDate.
     */
    public String getExecutedDate()
    {
        return executedDate;
    }
    /**
     * @param executedDate The executedDate to set.
     */
    public void setExecutedDate(String executedDate)
    {
        this.executedDate = executedDate;
    }    
    /**
     * @return Returns the mcaNames.
     */
    public String getMcaNames()
    {
        return mcaNames;
    }
    /**
     * @param mcaNames The mcaNames to set.
     */
    public void setMcaNames(String mcaNames)
    {
        this.mcaNames = mcaNames;
    }    
    /**
     * @return Returns the cpViewable.
     */
    public String getCpViewable()
    {
        return cpViewable;
    }
    /**
     * @param cpViewable The cpViewable to set.
     */
    public void setCpViewable(String cpViewable)
    {
        this.cpViewable = cpViewable;
    }
}