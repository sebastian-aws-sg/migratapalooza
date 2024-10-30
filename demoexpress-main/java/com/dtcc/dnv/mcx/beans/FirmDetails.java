/*
 * Created on Aug 30, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dtcc.dnv.mcx.beans;

import java.util.List;


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
 * @author Nithya R
 * @date Sep 25, 2007
 * @version 1.0
 * 
 * Bean for displaying the firm details in the popup.
 * 
 */
public class FirmDetails {
			private String dealerid;
			private String dealerName;
			private String primaryName;
			private String primaryEmail;
			private String primaryPhone;
			private String secondaryName;
			private String secondaryEmail;
			private String secondaryPhone;
			
			private List contactDetails;
		
			/**
			 * @return
			 */
			public String getDealerid() {
				return dealerid;
			}

			/**
			 * @return
			 */
			public String getDealerName() {
				return dealerName;
			}

			

			/**
			 * @param string
			 */
			public void setDealerid(String string) {
				dealerid = string;
			}

			/**
			 * @param string
			 */
			public void setDealerName(String string) {
				dealerName = string;
			}

						/**
			 * @return
			 */
			public List getContactDetails() {
				return contactDetails;
			}

			/**
			 * @param list
			 */
			public void setContactDetails(List list) {
				contactDetails = list;
			}
			
			

			/**
			 * @return Returns the primaryEmail.
			 */
			public String getPrimaryEmail() {
				return primaryEmail;
			}
			/**
			 * @param primaryEmail The primaryEmail to set.
			 */
			public void setPrimaryEmail(String primaryEmail) {
				this.primaryEmail = primaryEmail;
			}
			/**
			 * @return Returns the primaryName.
			 */
			public String getPrimaryName() {
				return primaryName;
			}
			/**
			 * @param primaryName The primaryName to set.
			 */
			public void setPrimaryName(String primaryName) {
				this.primaryName = primaryName;
			}
			/**
			 * @return Returns the primaryPhone.
			 */
			public String getPrimaryPhone() {
				return primaryPhone;
			}
			/**
			 * @param primaryPhone The primaryPhone to set.
			 */
			public void setPrimaryPhone(String primaryPhone) {
				this.primaryPhone = primaryPhone;
			}
			/**
			 * @return Returns the secondaryEmail.
			 */
			public String getSecondaryEmail() {
				return secondaryEmail;
			}
			/**
			 * @param secondaryEmail The secondaryEmail to set.
			 */
			public void setSecondaryEmail(String secondaryEmail) {
				this.secondaryEmail = secondaryEmail;
			}
			/**
			 * @return Returns the secondaryName.
			 */
			public String getSecondaryName() {
				return secondaryName;
			}
			/**
			 * @param secondaryName The secondaryName to set.
			 */
			public void setSecondaryName(String secondaryName) {
				this.secondaryName = secondaryName;
			}
			/**
			 * @return Returns the secondaryPhone.
			 */
			public String getSecondaryPhone() {
				return secondaryPhone;
			}
			/**
			 * @param secondaryPhone The secondaryPhone to set.
			 */
			public void setSecondaryPhone(String secondaryPhone) {
				this.secondaryPhone = secondaryPhone;
			}
}
