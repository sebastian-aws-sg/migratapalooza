package com.dtcc.dnv.otc.common.security.model;

import java.util.Hashtable;
import java.util.Vector;

import org.apache.xerces.xni.parser.XMLConfigurationException;

import com.dtcc.dnv.otc.common.exception.UserException;
import com.dtcc.dnv.otc.common.util.converters.DSConverterFactory;
import com.dtcc.dnv.otc.common.util.converters.IConverter;
import com.dtcc.dnv.otc.legacy.CounterpartyIdName;
import com.dtcc.dnv.otc.legacy.Originator;
/**
 * Copyright (c) 2006 Depository Trust and Clearing Corporation
 * 55 Water Street, New York, New York, 10041
 * All Rights Reserved
 * 
 * @author Steve Velez
 * @date Jul 19, 2006
 * @version 1.0
 * 
 * Abstract DerivSERV Common User object.
 * 
 * Rev	1.1			September 26, 2006		sv
 * Updated getAuditInfo() create the AuditInfo object with the
 * parameters in the correct order.
 * 
 * @version	1.3		Aptil 6, 2007			sv
 * Updated class to extend Exuser, updated comments and added toString().
 * 
 * @version	1.6		July 12, 2007			sv
 * Added getParticipantId() and getOriginatorCode() IUser interface methods
 * 
 * @version	1.7		July 21, 2007			sv
 * Made major to implementation and layout.  Restructured class to separate
 * methods in the following sections:
 * 		1.  Methods that will remain the user framework.
 * 		2.	Methods where the implementation still need to be completed.
 * 		3.	Methods that will be removed. (deprecated)
 * 		4.  Methods that will be removed, but must be done later to support backward compatibility. (Deprecated)
 * 		5.	Mehtods that should probably be moved to DSV.
 * Updated the class to mimic ParticipantUser.
 * 
 * @version	1.8		July 26, 2007			sv
 * Added originatorCode property and setter.  updated getter method to use 
 * instance variable.
 * 
 * @version	1.9		July 27, 2007			sv
 * Corrected bug in getOriginatorCode().
 * 
 * @version	1.10	July 31, 2007			sv
 * Updated getFamilyListAsLabelValueArray() to use correct converter implementation of 
 * DS_PARTICPANT_ID_LIST.
 * 
 * @version	1.11	August 7, 2007			sv
 * Added new protected constructor to take in CUOUser.  Moved all the protected methods 
 * to be together and after the public methods.  Implemented validateSystemAccess(String)
 * 
 * @version	1.12	August 30, 2007			sv
 * Added HasInquiry and hasUpdate boolean properties, setters, and getters.
 * 
 * @version	1.13	September 23, 2007		sv
 * Reverted participantId and particiapntName from deprecation.
 * 
 * @version	1.14	September 24, 2007		sv
 * Added DTCCPartyId property, setter, and getter.
 * 
 * @version	1.15	September 25, 2007		sv
 * Updated ROLE_SETTLEMENT_MEMBER constant to be in synch with current version.
 * 
 * @version	1.16	November 20, 2007		sv
 * Updated this revision to be in synch with ParticipantUser revision 1.11
 * 
 * @vesion	1.17	November 26, 2007		sv
 * Updated this revision to be in synch with ParticipantUser revision 1.12.  Updated 
 * counterPartyMap to be a Hashtable.
 */
public abstract class AbstractDSCUOUser extends ExUser implements IUser {

	// Instance members
	private IEntitlement entitlements = null;	
	private CounterpartyIdName[] counterPartyList;
	private String sessionId = "";
	private CounterpartyIdName [] familyList = null;
	private String currentSystem = "";
	private String testProdIndicator = TEST_FLAG;
	private Hashtable counterPartyMap = null;
	private Vector products;
	private String participantType = "";
	private Vector moduleList;
	private String originatorCode = "";
	private String participantId = "";
	private String participantName = "";
	private String DTCCPartyId = "";
	private boolean isSuperUser = false;
	private boolean hasInquiry = false;
	private boolean hasUpdate = false;
	
	// Static constants
	private static final String TEST_FLAG = "T";
	private static final String PROD_FLAG = "P";
	
	private static final String ROLE_FAMILY = "F";
	private static final String ROLE_INDIVIDUAL = "I";
	
	private static final String ROLE_SERVICE_BUREAU = "S";
	private static final String ROLE_BACKLOAD = "B";
	private static final String ROLE_EVENT_PROCESS = "E";	
	private static final String ROLE_SETTLEMENT_MEMBER = "M";
	private static final String ROLE_CUSTODIAN = "C";
	private static final String ROLE_SUB_FAMILY = "A"; 	//'A' for alternate famliy role
	
	private static final String BLOCK_INDICATOR_FLAG = "Y";
	
	/**
	 * @param user
	 */
	protected AbstractDSCUOUser(ExUser user){
		super(user);
	}
		
	/**
	 * @see com.dtcc.dnv.otc.common.security.model.IUser#getCurrentSystem()
	 */
	public String getCurrentSystem(){
		return this.currentSystem;
	}
	
	/**
	 * @see com.dtcc.dnv.otc.common.security.model.IUser#isBranchUser()
	 */
	public boolean isBranchUser() throws UserException {
		return this.isSuperUser();
	}

	/**
	 * @see com.dtcc.dnv.otc.common.security.model.IUser#getCounterPartyList()
	 */
	public CounterpartyIdName[] getCounterPartyList() {
		return this.counterPartyList;
	}
	
	/**
	 * @see com.dtcc.dnv.otc.common.security.model.IUser#getFamilyList()
	 */
	public CounterpartyIdName[] getFamilyList() {
		return this.familyList;
	}

	/**
	 * @see com.dtcc.dnv.otc.common.security.model.IUser#isProdIndicator()
	 */
	public boolean isProdIndicator() {
		return this.getProdIndicator().equalsIgnoreCase(PROD_FLAG);
	}

	/**
	 * @see com.dtcc.dnv.otc.common.security.model.IUser#getAuditInfo()
	 */
	public AuditInfo getAuditInfo() throws UserException {
		String username = this.getUserFirstName() + " " + this.getUserLastName();
		return new AuditInfo(this.getUserId(), this.getSessionId(), username, this.getEmailAddress());
	}

	/**
	 * @see com.dtcc.dnv.otc.common.security.model.IUser#getProdIndicator()
	 */
	public String getProdIndicator() {
		return this.testProdIndicator;
	}
	
	/**
	 * Method isFamily
	 * @param nothing
	 * @return boolean
	 * @throws nothing
     * @see com.dtcc.dnv.otc.common.security.model.IUser#isFamily()
	 * 
	 * Returns true if the user has more than one participant (member)
     * in the family.  Returns false if the user has only one participant
     * (member).
	 */
	public boolean isFamily(){
		CounterpartyIdName[] cpIdName = getFamilyList();
		if(cpIdName.length > 1) {
			return true;
		}
		else {
			return false;
		}
	}
    /**
     * Method to evaluate whether a given participant is a member of the family
     * @param String participantId
     * @return boolean - whether the participantId is a member of the family
     */
    public boolean isFamilyMember(String participantId){
    	CounterpartyIdName[] family = getFamilyList();
    	
    	for(int i = 0; i  < family.length; i++){
    		if (family[i].getCounterpartyId().equals(participantId))
    			return true;
    	}
    	//if we made it this far, we didn't find it.  it is not in the family
    	return false;
    }

	/**
     * Keyed name lookup to get the counterparty name for an id
     * @param String participantId
     * @return String - counterParty Name
     */
    public String getCounterPartyName (String participantId) {    	
    	return (String) this.counterPartyMap.get(participantId);

    }

	/**
	 * @see com.dtcc.dnv.otc.common.security.model.IUser#getProducts()
	 */
	public Vector getProducts() {
		return this.products;
	}
	
	/**
	 * @see com.dtcc.dnv.otc.common.security.model.IUser#getParticipantType()
	 */
	public String getParticipantType() {
		return this.participantType;
	}
	
	/**
	 * Method isPartyTypeServiceBureau
	 * @param nothing
	 * @return boolean
	 * @throws nothing
	 * Returns true if the user has logged in as a Service Bereau
	 */
	public boolean isPartyTypeServiceBureau(){
		if(participantType.equalsIgnoreCase(ROLE_SERVICE_BUREAU)) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Method isPartyTypeFamily
	 * @param nothing
	 * @return boolean
	 * @throws nothing
	 * Returns true if the user has logged in as a Family member
	 */
	public boolean isPartyTypeFamily(){
		if(participantType.equalsIgnoreCase(ROLE_FAMILY)) {
			return true;
		}
		else if (participantType.equalsIgnoreCase(ROLE_SUB_FAMILY)) {
			return true;
		}
		else
			return false;
	}

	/**
	 * Method isPartyTypeIndividual
	 * @param nothing
	 * @return boolean
	 * @throws nothing
	 * Returns true if the user has logged in as a Individual
	 */
	public boolean isPartyTypeIndividual(){
		if(participantType.equalsIgnoreCase(ROLE_INDIVIDUAL)) {
			return true;
		}
		else {
			return false;
		}
	}	

    /**
     * added 06/14/2005
     * @param String participantId
     * @return Method to return 8 character DTCC Account ID for a given participant
     */
    public String getDTCCpartyId(String partyId){
    	CounterpartyIdName[] counterParty = getCounterPartyList();
    	
    	for(int i = 0; i  < counterParty.length; i++){
    		if (counterParty[i].getCounterpartyId().trim().equals(partyId.trim()))
    			return counterParty[i].getDTCCpartyId();
    	}
    	//if we made it this far, we didn't find it.  it is not in the family
    	return null;
    }

    /**
     * added 06/14/2005
     * Method to evaluate whether Block Account Exists from the list of family
     * @param nothing
     * @return boolean - If Account with a block indicator exists
     */
    public boolean isPartyIdaBlockId(String partyId){
    	CounterpartyIdName[] counterParty = getCounterPartyList();
    	
    	for(int i = 0; i  < counterParty.length; i++){
    		if ( counterParty[i].getCounterpartyId().trim().equalsIgnoreCase(partyId.trim())
    			&& counterParty[i].getBlockIndicator().equals(BLOCK_INDICATOR_FLAG))
    			return true;
    	}
    	//if we made it this far, we didn't find it.  it is not in the family
    	return false;
    }

    /**
     * added 06/14/2005
     * Method to return whether for a given participant is a Block Account
     * @param String participantId
     * @return boolean - whether the participantId is a member of the family
     */
    public String getBlockIndicator(String partyId){
    	CounterpartyIdName[] counterParty = getCounterPartyList();
    	
    	for(int i = 0; i  < counterParty.length; i++){
    		if (counterParty[i].getCounterpartyId().trim().equals(partyId.trim()))
    			return counterParty[i].getBlockIndicator();
    	}
    	//if we made it this far, we didn't find it.  it is not in the family
    	return null;
    }

    /**
     * added 06/14/2005
     * Method to return whether for a given participant is a Block Account
     * @param String participantId
     * @return boolean - whether the participantId is a member of the family
     */
    public String getParticipantId(String dtccpartyId){
    	CounterpartyIdName[] counterParty = getCounterPartyList();
    	
    	for(int i = 0; i  < counterParty.length; i++){
    		if (counterParty[i].getDTCCpartyId().trim().equals(dtccpartyId.trim()))
    			return counterParty[i].getCounterpartyId();
    	}
    	//if we made it this far, we didn't find it.  it is not in the family
    	return null;
    }
	
	/**
	 * @see com.dtcc.dnv.otc.common.security.model.IUser#getOriginatorCode()
	 */
	public String getOriginatorCode() {
		return this.originatorCode;
	}
	
	/**
	 * @return Returns the sessionId.
	 */
	public String getSessionId() {
		return sessionId;
	}
	/**
	 * @param sessionId The sessionId to set.
	 */
	protected void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
	/**
	 * Returns true if the user has logged in as Custodian
	 * @return boolean
	 */
	public boolean isPartyTypeCustodian(){
		if(participantType.equalsIgnoreCase(ROLE_CUSTODIAN)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * @see com.dtcc.dnv.otc.common.security.model.IUser#getProductListAsStringArray()
	 */
	public Vector getModules() {
		return this.moduleList;
	}
	
	/**
	 * @return Returns the isSuperUser.
	 */
	public boolean isSuperUser() {
		return isSuperUser;
	}
	
	/**
	 * @return Returns the entitlements.
	 */
	IEntitlement getEntitlements() {
		return entitlements;
	}
	/**
	 * @param entitlements The entitlements to set.
	 */
	void setEntitlements(IEntitlement entitlements) {
		this.entitlements = entitlements;
	}
	
    /**
     * @see java.lang.Object#toString()
     */
    public String toString(){
    	StringBuffer sb = new StringBuffer();
    	sb.append(super.toString());
    	sb.append("family List: [");
    	
    	CounterpartyIdName[] familyList = this.getFamilyList();
    	if (familyList != null){
	    	for (int i = 0; i < familyList.length; i++){
	    		CounterpartyIdName party = familyList[i];
	    		if (i > 0){
	    			sb.append(", ");
	    		}
	    		sb.append(party.getCounterpartyId());
	    	}
    	}
    	sb.append("] ");
    	
    	return sb.toString();
    }
    
    /**
     * Protected instance methods
     */
	/**
	 * @see com.dtcc.dnv.otc.common.security.model.IUser#getCurrentSystem()
	 */
	protected void setCurrentSystem(String currentSystem){
		this.currentSystem = currentSystem;
	}
	
	/**
	 * @param counterPartyList
	 */
	protected void setCounterPartyList(CounterpartyIdName[] counterPartyList){
		this.counterPartyList = counterPartyList;
	}
	
	/**
	 * @param counterPartyMap
	 */
	protected void setCounterPartyMap(Hashtable counterPartyMap){
		this.counterPartyMap = counterPartyMap;
	}
	
	/**
	 * @param familyList
	 */
	protected void setFamilyList(CounterpartyIdName [] familyList){
		this.familyList = familyList;
	}
	
	/**
	 * @param testProdIndicator
	 */
	protected void setProdIndicator(String testProdIndicator) {
		this.testProdIndicator = testProdIndicator;
	}
	
	/**
	 * @param products
	 */
	protected void setProducts(Vector products) {
		this.products = products;
	}
    
	/**
	 * @param participantType
	 */
	protected void setParticipantType(String participantType) {
		this.participantType = participantType;
	}
	
	/**
	 * @param participantId
	 */
	protected void setParticipantId(String participantId) {
		this.participantId = participantId;
	}

	/**
	 * @param participantId
	 */
	protected void setDTCCPartyId(String DTCCPartyId) {
		this.DTCCPartyId = DTCCPartyId;
	}
	
	/**
	 * @param participantName
	 */
	protected void setParticipantName(String participantName) {
		this.participantName = participantName;
	}
	
	/**
	 * @param originatorCode The originatorCode to set.
	 */
	protected void setOriginatorCode(String originatorCode) {
		this.originatorCode = originatorCode;
	}
	
	/**
	 * @param moduleList
	 */
	protected void setModules(Vector moduleList) {
		this.moduleList = moduleList;
	}
	
	/**
	 * @param isSuperUser The isSuperUser to set.
	 */
	protected void setSuperUser(boolean isSuperUser) {
		this.isSuperUser = isSuperUser;
	}
	
	/**
	 * @param hasInquiry The hasInquiry to set.
	 */
	protected void setHasInquiry(boolean hasInquiry) {
		this.hasInquiry = hasInquiry;
	}
	
	/**
	 * @param hasUpdate The hasUpdate to set.
	 */
	protected void setHasUpdate(boolean hasUpdate) {
		this.hasUpdate = hasUpdate;
	}
	
	/**
	 * Method hasSystemAccess
	 * @param String
	 * @return boolean
	 * 
	 * Returns true if the user has access to the system (i.e. DSV, DPR)
	 */
	protected boolean hasSystemAccess(String systemName, DSCUOUser dsUser) throws UserException {
		try {
			return dsUser.hasSystemAccess(systemName);
		} catch (NullPointerException npe) {
			throw UserException.createFatal(UserException.SEC_ERROR_CODE_ENTITLEMENT,"No EntitlementDefinitions exist for operator " + dsUser.getUserId() );
		}
	}
	
	/**
     * Method to populate counterPartyMap for keyed search to get the party name
     * This method is called from lookupFamily() after we get back a counterparty
     * list.  counterPartyList is not checked for null or empty
     */
    protected void populateCounterPartyMap() {     	
    	int size = counterPartyList.length;

		counterPartyMap = new Hashtable(size);    	
    	for (int idx = 0; idx < size; idx++) {
    		counterPartyMap.put(this.counterPartyList[idx].getCounterpartyId(),
    							this.counterPartyList[idx].getCounterpartyName());
    	}
    	this.setCounterPartyMap(counterPartyMap);
    }
    
	/**
	 * Method hasSystemAccess
	 * @param String
	 * @return boolean
	 * 
	 * Returns true if the user has access to the system (i.e. DSV, DPR)
	 */
	public boolean hasSystemAccess(String systemName) throws UserException {
		try {
			return this.getEntitlements().hasSystemAccess(systemName);
		} catch (NullPointerException npe) {
			throw UserException.createFatal(UserException.SEC_ERROR_CODE_ENTITLEMENT,"No EntitlementDefinitions exist for operator "+this.getUserId());
		}
	}

    /**
     * ########### SV TODO IMPLEMENTATION ###############
     * These implementations for these methods must be completed.
     */ 
	/**
	 * @see com.dtcc.dnv.otc.common.security.model.IUser#hasModuleAccess(java.lang.String)
	 */
	public boolean hasModuleAccess(String key) {
		try {
			// Does the user have module ACL access
			if ( this.entitlements.isAclContains(key) )
				return true;
			else
				return false;
		} catch (XMLConfigurationException ue) {
			return false;
		}
	}

	/**
	 * Method validateSystemAccess
	 * @param String
	 * @return nothing
	 * @throws UserException
	 * 
	 * Used to validate the user's access to the system (i.e. DSV, DPR)
	 */
	public void validateSystemAccess(String systemName) throws UserException {
		try {
			if (!this.hasSystemAccess(systemName))
			throw UserException.createInfo( UserException.SEC_ERROR_CODE_ENTITLEMENT, "User does not have access to " + systemName );
		} catch (NullPointerException npe) {
			throw UserException.createFatal( UserException.SEC_ERROR_CODE_ENTITLEMENT, "entitlements is null for operator " + this.getUserId() );

		}
	}
	
	/**
	 * @see com.dtcc.dnv.otc.common.security.model.IUser#isInquiryOnly()
	 */
	public boolean isInquiryOnly(){
		return this.hasInquiry();
	}
	
	/**
	 * @see com.dtcc.dnv.otc.common.security.model.IUser#isUpdateAllowed()
	 */
	public boolean isUpdateAllowed(){
		return this.hasUpdate();
	}
	
	/**
	 * @return Returns the hasInquiry.
	 */
	public boolean hasInquiry() {
		return this.hasInquiry;
	}
	/**
	 * @return Returns the hasUpdate.
	 */
	public boolean hasUpdate() {
		return this.hasUpdate;
	}
  
    /**
     * ########### SV TODO DEPRECATED TO REMAIN ###############
     * These methods should not longer be used as they are deprecated.  However, they remain to support backward compatibilities.
     */ 
	/**
	 * @see com.dtcc.dnv.otc.common.security.model.IUser#getCounterPartyListAsString()
	 */
	public String getCounterPartyListAsString() {
		// Initialize value
		String value = "";
		// get appropriate converter.
		IConverter converter = DSConverterFactory.getInstance(DSConverterFactory.DS_PARTICPANT_LIST);
		// Convert if converter is not null;
		if (converter != null){
			// set value returned by converter.
			value = converter.convertToString(getCounterPartyList());
		}
		return value;
	}
	
	/**
	 * @see com.dtcc.dnv.otc.common.security.model.IUser#getFamilyListAsString()
	 */
	public String getFamilyListAsString() {
		// Initialize value
		String value = "";
		// get appropriate converter.
		IConverter converter = DSConverterFactory.getInstance(DSConverterFactory.DS_PARTICPANT_LIST);
		// Convert if converter is not null;
		if (converter != null){
			// set value returned by converter.
			value = converter.convertToString(getFamilyList());
		}
		return value;
	}
	
	/**
	 * @see com.dtcc.dnv.otc.common.security.model.IUser#getFamilyListAsIdString()
	 */
	public String getFamilyListAsIdString() {
		// Initialize value
		String value = "";
		// get appropriate converter.
		IConverter converter = DSConverterFactory.getInstance(DSConverterFactory.DS_PARTICPANT_LIST_ID);
		// Convert if converter is not null;
		if (converter != null){
			// set value returned by converter.
			value = converter.convertToString(getFamilyList());
		}
		return value;
	}
	
	/**
	 * @see com.dtcc.dnv.otc.common.security.model.IUser#getFamilyListAsLabelValueArray()
	 */
	public Vector getFamilyListAsLabelValueArray() {
		// Initialize value
		Vector value = null;
		// get appropriate converter.
		IConverter converter = DSConverterFactory.getInstance(DSConverterFactory.DS_PARTICPANT_LIST);
		// Convert if converter is not null;
		if (converter != null){
			// set value returned by converter.
			value = converter.convertToLabelValueVector(getFamilyList());
		}
		return value;
	}
	
	/**
	 * @see com.dtcc.dnv.otc.common.security.model.IUser#getProductsAsString()
	 */
	public String getProductsAsString() {
		// Initialize value
		String value = "";
		// get appropriate converter.
		IConverter converter = DSConverterFactory.getInstance(DSConverterFactory.DS_PRODUCT);
		// Convert if converter is not null;
		if (converter != null){
			// set value returned by converter.
			value = converter.convertToString(getProducts());
		}
		return value;
	}
	
	/**
	 * @see com.dtcc.dnv.otc.common.security.model.IUser#getProductListAsStringArray()
	 */
	public String[] getProductListAsStringArray() {
		// Initialize value
		String [] value = null;
		// get appropriate converter.
		IConverter converter = DSConverterFactory.getInstance(DSConverterFactory.DS_PRODUCT);
		// Convert if converter is not null;
		if (converter != null){
			// set value returned by converter.
			value = converter.convertToStringArray(getProducts());
		}
		return value;
	}
	
	/**
	 * @see com.dtcc.dnv.otc.common.security.model.IUser#getProductListAsStringArray()
	 */
	public String[] getModulesListAsStringArray() {
		// Initialize value
		String [] value = null;
		// get appropriate converter.
		IConverter converter = DSConverterFactory.getInstance(DSConverterFactory.DS_MODULES);
		// Convert if converter is not null;
		if (converter != null){
			// set value returned by converter.
			value = converter.convertToStringArray(getModules());
		}
		return value;
	}
	
	/**
	 * @see com.dtcc.dnv.otc.common.security.model.IUser#getProductListAsStringArray()
	 */
	public String getModulesAsString() {
		// Initialize value
		String value = "";
		// get appropriate converter.
		IConverter converter = DSConverterFactory.getInstance(DSConverterFactory.DS_MODULES);
		// Convert if converter is not null;
		if (converter != null){
			// set value returned by converter.
			value = converter.convertToString(getModules());
		}
		return value;
	}
	
	/**
	 * @see com.dtcc.dnv.otc.common.security.model.IUser#getParticipantName()
	 */
	public String getParticipantName() {
		return this.participantName;
	}
	
    
	/**
	 * @see com.dtcc.dnv.otc.common.security.model.IUser#getParticipantId(java.lang.String)
	 */
	public String getParticipantId() {
		return this.participantId;
	}
	
	/**
	 * @see com.dtcc.dnv.otc.common.security.model.IUser#getDTCCPartyId()
	 */
	public String getDTCCPartyId() {
		return this.DTCCPartyId;
	}
   
    /**
     * ########### SV TODO BAD IMPLEMENTATION ###############
     * These methods should not longer be used as they are deprecated.  This is left here to leave the interface to be backwards compatible.
     * Once the interface can be updated to remove the deprecated methods, then these methods should be deleted.  This is intentionally coded
     * throw a runtime exception to prevent applications from implementing it.
     */ 
	
	/**
	 * @see com.dtcc.dnv.otc.common.security.model.IUser#isValid()
	 */
	public boolean isValid() {
		throw new RuntimeException(UserException.SEC_ERROR_CODE_BAD_OPERATION + ": User method [isValid()] no longer supported");
	}
	
	/**
	 * @see com.dtcc.dnv.otc.common.security.model.IUser#hasDefaultParticipantId()
	 * @deprecated
	 */
	public boolean hasDefaultParticipantId() {
		throw new RuntimeException(UserException.SEC_ERROR_CODE_BAD_OPERATION + ": User method [hasDefaultParticipantId()] no longer supported");
	}
	
	/**
	 * @see com.dtcc.dnv.otc.common.security.model.IUser#getOriginatorList()
	 */
	public Originator[] getOriginatorList() {
		throw new RuntimeException(UserException.SEC_ERROR_CODE_BAD_OPERATION + ": User method [getOriginatorList()] no longer supported");
	}
	
	/**
	 * @see com.dtcc.dnv.otc.common.security.model.IUser#getOriginatorListAsString()
	 */
	public String getOriginatorListAsString() {
		throw new RuntimeException(UserException.SEC_ERROR_CODE_BAD_OPERATION + ": User method [getOriginatorListAsString()] no longer supported");
	}
	
	/**
	 * @see com.dtcc.dnv.otc.common.security.model.IUser#getOriginatorListAsLabelValueArray()
	 */
	public Vector getOriginatorListAsLabelValueArray() {
		throw new RuntimeException(UserException.SEC_ERROR_CODE_BAD_OPERATION + ": User method [getOriginatorListAsLabelValueArray()] no longer supported");
	}
	
	/**
	 * @see com.dtcc.dnv.otc.common.security.model.IUser#displayString()
	 */
	public String displayString() {
		throw new RuntimeException(UserException.SEC_ERROR_CODE_BAD_OPERATION + ": User method [displayString()] no longer supported");
	}
	
	/**
	 * @see com.dtcc.dnv.otc.common.security.model.IUser#isMultipleOriginators()
	 */
	public boolean isMultipleOriginators() {
		throw new RuntimeException(UserException.SEC_ERROR_CODE_BAD_OPERATION + ": User method [isMultipleOriginators()] no longer supported");
	}
	
	/**
	 * @see com.dtcc.dnv.otc.common.security.model.IUser#getProdTestIndicator(java.lang.String)
	 */
	public String getProdTestIndicator(String sOriginatorCode) {
		throw new RuntimeException(UserException.SEC_ERROR_CODE_BAD_OPERATION + ": User method [getProdTestIndicator(String)] no longer supported");
	}

    /**
     * ########### SV TODO TO MOVE TO DSV ###############
     * These methods probably belong in DSV
     */ 
	
	/**
	 * Returns true if the user has logged in as Backload
	 * @return boolean
	 */
	public boolean isPartyTypeBackload(){
		if(participantType.equalsIgnoreCase(ROLE_BACKLOAD)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Returns true if the user has logged in as Event Process
	 * @return boolean
	 */
	public boolean isPartyTypeEventProcess(){
		if(participantType.equalsIgnoreCase(ROLE_EVENT_PROCESS)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Method isPartyTypeSettlementMember
	 * @param nothing
	 * @return boolean
	 * @throws nothing
	 * Returns true if the user has logged in as a Settlement Member
	 */
	public boolean isPartyTypeSettlementMember(){
		if(participantType.equalsIgnoreCase(ROLE_SETTLEMENT_MEMBER)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public String getWarehousePushTypcd(String partyId) {
		return null;
	}

	public String getWarehouseGateInd(String partyId) {
		return null;
	}
}