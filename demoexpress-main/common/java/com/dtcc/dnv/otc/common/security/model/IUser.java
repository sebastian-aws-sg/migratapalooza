/*
 * Copyright (c) 2004 Depository Trust and Clearing Corporation
 * 55 Water Street, New York, New York, 10041
 * All Rights Reserved
 *
 */

package com.dtcc.dnv.otc.common.security.model;

import java.io.Serializable;
import java.util.Vector;

import com.dtcc.dnv.otc.common.exception.UserException;
import com.dtcc.dnv.otc.legacy.CounterpartyIdName;
import com.dtcc.dnv.otc.legacy.Originator;

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
 * @author Toshi Kawanishi
 * @version 1.0
 * 
 * IUser defines the interface that support the semantics of how application code
 * obtains information regarding the user
 * 
 * This is the only public access to a user object
 * 
 * Changes:
 *    5/4/2004 DLV added isAclContains(String entitlements):boolean method
 *                 to verify credential for a specific person
 * Rev 1.1	May 31, 2004	TK
 * Added getAuditInfo() method to return the AuditInfo object
 * 
 * rev 1.5 scb 7/8/2004
 * added isFamilyMember()
 * 
 * rev 1.6 TK 7/21/2004
 * added getCounterPartyName(String participantId)
 * 
 * rev 1.7 DLV 10/17/2004
 * added methods 
 * 		isAllProductsAllowed
 * 		isInterestRateAllowed
 * 		isEquityAllowed
 * 		isCreditSwapAllowed
 * 		getProductListAsLabelValueArray
 * 
 * rev 1.8 dlv 10/29/2004
 * 		added getProductListAsLabelValueArrayForSearch;
 * 
 * rev 1.10 
 * 03/25/2005 Shashi
 * added methods
 * 		isPartyTypeServiceBureau
 * 		isPartyTypeFamily
 * 		isPartyTypeIndividual
 * 		getParticipantType
 * 
 * ###### Project moved to different project location.  Revision sequence numbers reset.
 * 
 * Rev	1.5				July 21, 2007			sv
 * Updated with javadocs and denoted which methods are deprecated.  Added isSuperUser()
 * interface method.
 * 
 * @version	1.6			October 29, 2007		sv
 * Added hasRole() interface method.
 */

public interface IUser extends Serializable{


	/**
	 * Method isUpdateAllowed
	 * @param String key f.e.:"Trade"
	 * @return boolean
	 * @throws nothing
	 * 
	 * Returns true if the user's entitlements allow Updates
	 */
	public boolean hasModuleAccess(String key);

	/**
	 * Method validateSystemAccess
	 * @param String system name
	 * @return nothing
	 * @throws UserException
	 * 
	 * Check to see if the user has access to the application
	 */
	public void validateSystemAccess ( String systemName ) throws UserException;

	/**
	 * Method getCurrentSystem
	 * @param nothing
	 * @return nothing
	 * @throws UserException
	 * 
	 * Return the name of the application context under which this user object was
	 * instantiated.
	 */
	public String getCurrentSystem() throws UserException;

	/**
	 * Method displayString
	 * @param nothing
	 * @return String
	 * @throws nothing
	 * @deprecated
	 * 
	 * Dump the user information for debugging
	 */
	public String displayString();

	/**
	 * Method isBranchUser
	 * @param nothing
	 * @return nothing
	 * @throws UserException
	 * @deprecated
	 * 
	 * Return true if this is a BRANCH user
	 */
	public boolean isBranchUser () throws UserException;

	/**
	 * Method isValid
	 * @param nothing
	 * @return nothing
	 * @throws nothing
	 * @deprecated
	 * 
	 * Return true if the instantiation of the user is complete
	 */
	public boolean isValid();

	/**
	 * Method hasDefaultParticipantId
	 * @param nothing
	 * @return nothing
	 * @throws nothing
	 * @deprecated
	 * 
	 * Return true if the user has a default participant.
	 * This would be true in the case of a Branch user who selected a participant Id
	 * or a NonBranch user that has only one participant id in the family
	 */
	public boolean hasDefaultParticipantId();

	/**
	 * Method getOriginatorCode
	 * @param nothing
	 * @return nothing
	 * @throws nothing
	 * 
	 * Return the originator code retrieved from the database
	 */
	public String getOriginatorCode();

	/**
	 * Method getParticipantId
	 * @param nothing
	 * @return nothing
	 * @throws nothing
	 * @deprecated
	 * 
	 * Returns an 8 digit participant id if the user has a default.
	 * Otherwise return an empty string
	 * @see hasDefaultParticipantId
	 */
	public String getParticipantId();

	/**
	 * Method getParticipantName
	 * @param nothing
	 * @return nothing
	 * @throws nothing
	 * @deprecated
	 * 
	 * Return the name associated with the participant
	 */
	public String getParticipantName();

	/**
	 * Method getCounterPartyList
	 * @param nothing
	 * @return CounterpartyIdName[]
	 * @throws nothing
	 * 
	 * Returns an array of counter parties that the user can trade with
	 */
	public CounterpartyIdName[] getCounterPartyList();

	/**
	 * Method getFamilyList
	 * @param nothing
	 * @return CounterpartyIdName[]
	 * @throws nothing
	 * 
	 * Returns an array containg the participant's family
	 */
	public CounterpartyIdName[] getFamilyList();

	/**
	 * Method isProdIndicator
	 * @param nothing
	 * @return boolean
	 * @throws nothing
	 * 
	 * Returns
	 * 	true if the user is running under prod,
	 * 	false if the user is running under test.
	 */
	public boolean isProdIndicator();
	
	/**
	 * Method isInquiryOnly
	 * @param nothing
	 * @return boolean
	 * @throws nothing
	 * 
	 * Returns true if the user's entitlements allows only Reads
	 */
	public boolean isInquiryOnly();

	/**
	 * Method isUpdateAllowed
	 * @param nothing
	 * @return boolean
	 * @throws nothing
	 * 
	 * Returns true if the user's entitlements allow Updates
	 */
	public boolean isUpdateAllowed();


	/**
	 * Method getAuditInfo
	 * @param nothing
	 * @return AuditInfo
	 * @throws nothing
	 * 
	 * Returns the AuditInfo containing the userId and sessionId
	 */
	public AuditInfo getAuditInfo() throws UserException;


	/**
	 * Method getProdIndicator
	 * @author dlarin
	 * @param nothing
	 * @return String
	 * @throws nothing
	 * @version 1.0
	 * 
	 * Returns
	 * 	"P" if the user is running under prod,
	 * 	"T" if the user is running under test.
	 */
	public String getProdIndicator();	
	
	
	//dlv >> 6/5/2004
	/**
	 * @author dlarin
	 * Method getCounterPartyListAsString
	 * @param nothing
	 * @return String
	 * @throws nothing
	 * @deprecated
	 * 
	 * Returns an array of counter parties that the user can trade with
	 * as a delimeted string with "|" between counterpartyID & counterpartyName
	 * and ";" between different counterparties
	 */
	public String getCounterPartyListAsString();

	/**
	 * @author dlarin 
	 * Method getFamilyListAsString
	 * @param nothing
	 * @return String
	 * @throws nothing
	 * @deprecated
	 * 
	 * Returns an array containg the participant's family as a delimeted String
	 * with "|" to join ID & Name and ";" to join different counterparties
	 */
	public String getFamilyListAsString();

	/**
	 * @author dlarin 
	 * Method getFamilyListAsString
	 * @param nothing
	 * @return String
	 * @throws nothing
	 * @deprecated
	 * 
	 * Returns an array containg the participant's family as a delimeted String
	 * of participant ID(s) with "," to join ID(s). 
	 */
    public String getFamilyListAsIdString();
    
	/**
	 * @author dlarin 
	 * Method getFamilyListAsLabelValueArray
	 * @param nothing
	 * @return boolean
	 * @throws nothing
	 * 
	 * return true if family contains more the single
	 * participant. Otherwise returns false
	 */
	public boolean isFamily();

	/**
	 * @author dlarin 
	 * Method getFamilyListAsLabelValueArray
	 * @param nothing
	 * @return java.util.Vector of LabelValueBean
	 * @throws nothing
	 * @deprecated
	 * 
	 * Returns an array containg the participant's family 
	 * as a java.util.Vector LabelValueBean(s) for use on a presentation
	 * layer
	 */
	public Vector getFamilyListAsLabelValueArray();
    //<< dlv	
    
    /**
     * Method to evaluate whether a given participant is a member of the family
     * @param String participantId
     * @return boolean - whether the participantId is a member of the family
     */
    public boolean isFamilyMember(String participantId);
    
	/**
     * Keyed name lookup to get the counterparty name for an id
     * @param String participantId
     * @return String - counterParty Name
     */
    public String getCounterPartyName (String participantId);
    
    /**
     * @deprecated
     * @return
     */
	public Originator[] getOriginatorList();

    /**
     * @deprecated
     * @return
     */
	public String getOriginatorListAsString();

	
	/**
	 * @return
	 * @deprecated
	 */
	public boolean isMultipleOriginators();

	public Vector getOriginatorListAsLabelValueArray();
	
	/**
	 * @param sOriginatorCode
	 * @return
	 * @deprecated
	 */
	public String getProdTestIndicator (String sOriginatorCode);


	/**
     * @author shashi
     * Methods define to return Products that are available for the User
     * @return String - Product Type
     */
 	public String getProductsAsString();

	/**
     * @author shashi
     * Methods define to return Products that are available for the User
     * @return Vector - Product Type List
     */

   	public Vector getProducts();

	/**
	 * @author Shashi 
	 * Method getProductListAsStringArray
	 * @param nothing
	 * @return String[]
	 * @throws nothing
	 * Returns an string array containg the product Lists
	 */
	
	public String[] getProductListAsStringArray();      


	/**
	 * @author Shashi 
	 * Method getParticipantType
	 * @param nothing
	 * @return String
	 * @throws nothing
	 * Returns an string containing the User Role indicator 
	 * S - Service Bureau, F - Family, I - Individual
	 */
	public String getParticipantType();      

	/**
	 * @author Shashi 
	 * Method isPartyTypeServiceBureau, isPartyTypeFamily, isPartyTypeIndividual
	 * @param nothing
	 * @return boolean
	 * @throws nothing
	 * Returns an true containing the User Role indicator 
	 */
	public boolean isPartyTypeServiceBureau();
	public boolean isPartyTypeFamily();
	public boolean isPartyTypeIndividual();
	
	/**
	 * Returns true if the user has logged in as Backload
	 * @return boolean
	 * @author Klake
	 */
	public boolean isPartyTypeBackload();	

	//Changes made on 06/14/2005 - Shashi
	/**
	 * Method getDTCCPartyId
	 * @param nothing
	 * @return String
	 * @throws nothing
	 * @deprecated
	 * Returns an 8 char Particpant Id 
	 */
	public String getDTCCPartyId();
	
	/**
	 * @param nothing
	 * @return String
	 * @throws nothing
	 * Returns an 8 char Particpant Id 
	 */
	public String getDTCCpartyId(String partyId);
	
	/**
	 * Method isPartyIdaBlockId
	 * @param nothing
	 * @return boolean
	 * @throws nothing
	 * Returns an true is the  Account id a Block Account
	 */
    public boolean isPartyIdaBlockId(String partyId);	
    
	/**
	 * Method getBlockIndicator
	 * @param nothing
	 * @return String 
	 * @throws nothing
	 * Returns an Block Indicator "Y" or "N"
	 */
    public String getBlockIndicator(String partyId);

	/**
	 * Method getBlockIndicator
	 * @param nothing
	 * @return String 
	 * @throws nothing
	 * Returns an Block Indicator "Y" or "N"
	 */
    public String getParticipantId(String dtccpartyId);

	/**
	 * Method isPartyTypeCustodian
	 * @param nothing
	 * @return boolean
	 * @throws nothing
	 * Returns an true is the  Account id a Custodian.
	 */
    public boolean isPartyTypeCustodian();
    
    /**
     * Method getWarehousePushTypcd
     * @param partyId 
	 * @return String 
	 * Returns warehouse Push Type Cd for the family member.
	 */
    public String getWarehousePushTypcd(String partyId);

    /**
     * Method getWarehouseGateInd
     * @param partyId 
	 * @return String 
	 * Returns warehouse Gate for the family member.
	 */
	public String getWarehouseGateInd(String partyId);    
	
	/**
	 * Method isPartyTypeEventProcess
	 * @param nothing
	 * @return boolean
	 * @throws nothing
	 * Returns an true is the  Account id a Custodian.
	 */
    public boolean isPartyTypeEventProcess();
	/**
     * @author shashi
     * Methods define to return Products that are available for the User
     * @return String - Product Type
     */
 	public String getModulesAsString();

	/**
     * @author shashi
     * Methods define to return Products that are available for the User
     * @return Vector - Product Type List
     */

   	public Vector getModules();

	/**
	 * @author Shashi 
	 * Method getModuleListAsStringArray
	 * @param nothing
	 * @return String[]
	 * @throws nothing
	 * Returns an string array containg the product Lists
	 */
	
	public String[] getModulesListAsStringArray();      

	/**
	 * Method isPartyTypeEventProcess
	 * @param nothing
	 * @return boolean
	 * @throws nothing
	 * Returns an true is the  Account id a Custodian.
	 */
    public boolean isPartyTypeSettlementMember();
   
    /**
     * @return boolean value of whether user is a super user or not.
     */
    public boolean isSuperUser();
    
    /**
     * @return boolean value of true if the user has the role
     */
    public boolean hasRole(String roleName);
}
