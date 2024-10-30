package com.dtcc.dnv.otc.legacy;

import java.io.Serializable;

/**
 * <p>Title: OTC Derivatives</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: The Depository Trust Clearing Corporation</p>
 * @author Sarosh Pasricha
 * @version 1.0
 * 
 * 
 * rev 1.1 
 * 03/25/2005 Shashi
 * added variable
 * 		participantType
 * 
 * Rev	1.3					October 14, 2005		sv
 * Updated to add constructor to use firm id and firm name.  Commented out unused constrcutors,
 * Deprecated version without firm id and firm name.  This will be taken out later.
 * 
 * 07/12/2006 
 * Added Warehouse Pushflag at the end.
 * 
 * @version	1.3				September 20, 2007		sv
 * Updated class to implement Serializable.
 */

public class CounterpartyIdName implements Comparable, Serializable
{
   private String counterpartyId;
   private String counterpartyName;
   private String prodTestInd;
   private String participantType;
   private String DTCCpartyId;
   private String blockIndicator;
   private String firmId = "";
   private String firmNm = "";
   private String wrhsPushTypcd = "";
   private String wrhsGateInd = "";

   
	// TK
   private String originatorCode;

   public CounterpartyIdName()
   {
      super();
   }



//   public CounterpartyIdName(String counterpartyId, String counterpartyName,String prodTestInd)
//   {
//      this();
//
//      this.counterpartyId = counterpartyId;
//      this.counterpartyName = counterpartyName;
//      this.prodTestInd = prodTestInd;
//
//   }


//   public CounterpartyIdName(String counterpartyId, String counterpartyName,String prodTestInd, String originatorCode, String participantType)
//   {
//      this();
//
//	  this.originatorCode = originatorCode;
//      this.counterpartyId = counterpartyId;
//      this.counterpartyName = counterpartyName;
//      this.prodTestInd = prodTestInd;
//      this.participantType = participantType;
//
//   }
	/**
	 * Method CounterpartyIdName.
	 * @param counterpartyId
	 * @param counterpartyName
	 * @param prodTestInd
	 * @param originatorCode
	 * @param participantType
	 * @param dtccPartyId
	 * @param blockInd
	 * @param firmId
	 * @param firmNm
	 */
 public CounterpartyIdName(String counterpartyId, String counterpartyName,String prodTestInd, String originatorCode, String participantType, String dtccPartyId, String blockInd, String firmId, String firmNm)
 {
    this();

	this.originatorCode = originatorCode;
    this.counterpartyId = counterpartyId;
    this.counterpartyName = counterpartyName;
    this.prodTestInd = prodTestInd;
    this.participantType = participantType;
    this.DTCCpartyId = dtccPartyId;
    this.blockIndicator = blockInd;
    this.firmId = firmId;
    this.firmNm = firmNm;
 }

	/**
	 * Method CounterpartyIdName.
	 * @param counterpartyId
	 * @param counterpartyName
	 * @param prodTestInd
	 * @param originatorCode
	 * @param participantType
	 * @param dtccPartyId
	 * @param blockInd
	 * @param firmId
	 * @param firmNm
	 * @param wrhsPushTypcd
	 * @param wrhsGateInd
	 */
  public CounterpartyIdName(String counterpartyId, String counterpartyName,String prodTestInd, String originatorCode, String participantType, String dtccPartyId, String blockInd, String firmId, String firmNm, String wrhsPushTypcd, String wrhsGateInd)
  {
     this();

	  this.originatorCode = originatorCode;
     this.counterpartyId = counterpartyId;
     this.counterpartyName = counterpartyName;
     this.prodTestInd = prodTestInd;
     this.participantType = participantType;
     this.DTCCpartyId = dtccPartyId;
     this.blockIndicator = blockInd;
     this.firmId = firmId;
     this.firmNm = firmNm;
     this.wrhsPushTypcd = wrhsPushTypcd;
     this.wrhsGateInd = wrhsGateInd;
  }
   
   public String getCounterpartyId()
   {
      return counterpartyId;
   }


   public String getCounterpartyName()
   {
      return counterpartyName;
   }

   public String getProdTestInd()
   {
     return this.prodTestInd;
   }

	public String getOriginatorCode() {
		return this.originatorCode;
	}


	/**
	 * Returns the getParticipantType.
	 * @return String
	 */
	public String getParticipantType() {
		return this.participantType;
	}

	/**
	 * Returns the blockInd.
	 * @return String
	 */
	public String getBlockIndicator() {
		return blockIndicator;
	}
	
	/**
	 * Returns the dTCCpartyId.
	 * @return String
	 */
	public String getDTCCpartyId() {
		return DTCCpartyId;
	}

	/**
	 * Returns the firmId.
	 * @return String
	 */
	public String getFirmId() {
		return firmId;
	}
	
	/**
	 * Returns the firmNm.
	 * @return String
	 */
	public String getFirmNm() {
		return firmNm;
	}

/**
 * @return Returns the wrhsPushTypcd.
 */
	public String getWrhsPushTypcd() {
		return wrhsPushTypcd;
	}
	

	/**
	 * @return Returns the wrhsGateInd.
	 */
	public String getWrhsGateInd() {
		return wrhsGateInd;
	}

    /**
     * Compare this CounterpartyIdName instance against another Object
     * @param that
     * @return
     */
    public int compareTo(Object aThat) {
        final int EQUAL = 0;

        if(aThat == null)
            return -1;
        final CounterpartyIdName that = (CounterpartyIdName)aThat;

        if (this == that) return EQUAL;

        int comparison = this.counterpartyId.compareTo(that.counterpartyId);
        if (comparison != EQUAL) return comparison;

        return EQUAL;
    }
}