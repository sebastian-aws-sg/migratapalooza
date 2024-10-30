package com.dtcc.dnv.otc.legacy;

import java.io.Serializable;

/**
 * @author Slingaia
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class Originator implements Serializable {

   String originatorCode;
   String prodTestInd;


	/**
	 * Constructor for DerivServProduct.
	 */
	public Originator (String sOriginatorCode, String sProdTestInd) {
		super();
		this.originatorCode = sOriginatorCode;
		this.prodTestInd = sProdTestInd;		
	}

/**
 * Returns the productCode.
 * @return String
 */
public String getOriginatorCode() {
	return originatorCode;
}

/**
 * Returns the productDescription.
 * @return String
 */
public String getProdTestInd() {
	return prodTestInd;
}

}
