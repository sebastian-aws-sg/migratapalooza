package com.dtcc.dnv.otc.legacy;

import java.util.StringTokenizer;

/**
 * Copyright © 2003 The Depository Trust & Clearing Company. All rights reserved.
 *
 * Depository Trust & Clearing Corporation (DTCC)
 * 55, Water Street,
 * New York, NY 10048
 * U.S.A
 * All Rights Reserved.
 *
 * This software may contain (in part or full) confidential/proprietary information of DTCC.
 * ("Confidential Information"). Disclosure of such Confidential
 * Information is prohibited and should be used only for its intended purpose
 * in accordance with rules and regulations of DTCC.
 * Form bean for a Struts application.
 *
 * @version 	1.0
 * @author     Sarosh Pasricha
 */

public class SpErrArea {
  private String spErrArea = null;
  private String paragraphName = null;
  private String tableName = null;
  private boolean isNotEmpty = false;
  public SpErrArea() {
  }
  public String getSpErrArea() {
    return spErrArea;
  }
  public void setSpErrArea(String spErrArea) {
    if(spErrArea != null &&
       spErrArea.trim().length() > 0) {
         this.spErrArea = spErrArea;
         StringTokenizer st = new StringTokenizer(spErrArea,",");
         if(st.hasMoreTokens()) {
           paragraphName = st.nextToken();
         }
         if(st.hasMoreTokens()) {
           tableName = st.nextToken();
         }
    }
  }
  public boolean isNotEmpty() {
    return isNotEmpty;
  }
  public String getParagraphName() {
    return paragraphName;
  }
  public String getTableName() {
    return tableName;
  }
  public void setNotEmpty(boolean isNotEmpty) {
    this.isNotEmpty = isNotEmpty;
  }
  public void setParagraphName(String paragraphName) {
    this.paragraphName = paragraphName;
  }
  public void setTableName(String tableName) {
    this.tableName = tableName;
  }
}
