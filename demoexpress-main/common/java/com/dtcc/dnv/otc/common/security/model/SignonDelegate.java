package com.dtcc.dnv.otc.common.security.model;

import java.util.Vector;
import java.util.Iterator;

import com.dtcc.dnv.otc.common.exception.BusinessException;
import com.dtcc.dnv.otc.common.exception.DBException;

import com.dtcc.dnv.otc.common.layers.AbstractBusinessDelegate;
import com.dtcc.dnv.otc.common.layers.IServiceRequest;
import com.dtcc.dnv.otc.common.layers.IServiceResponse;
import com.dtcc.dnv.otc.legacy.CounterpartyIdName;
import com.dtcc.dnv.otc.legacy.Originator;


/**
 * @author tkawanis
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 * 
 * @version	1.2				August 30, 2007			sv
 * Made the class public.
 */
public final class SignonDelegate extends AbstractBusinessDelegate {

	/**
	 * @see com.dtcc.dnv.otc.common.layers.IBusinessDelegate#processRequest(IServiceRequest)
	 */
	public IServiceResponse processRequest(IServiceRequest req) throws BusinessException {

		SignonRequest request = null;
		SignonResponse response = new SignonResponse();
		SignonDbProxy dbProxy = new SignonDbProxy();
		SignonDbRequest dbRequest = null;
		SignonDbResponse dbResponse;

		try {
			request = (SignonRequest) req;

			// The ServiceRequest may be for one of the following three:
			if ( request.isForBranchFamily() ) {
				// Construct a DB request for Branch Family List
				dbRequest = new SignonDbRequest( "DERS71C0", "FAMILY", request.getAuditInfo(), request );
			} else if ( request.isForNonBranchFamily() ) {
				// Construct a DB request for a NonBranch Family List
				dbRequest = new SignonDbRequest( "DERS66C0", "FAMILY", request.getAuditInfo(), request );
			} else if ( request.isForCounterParty() ) {
				// Construct a DB request for the Counter Party List
				dbRequest = new SignonDbRequest( "DERS64C0", "COUNTER_PARTY", request.getAuditInfo(), request );
			}

			// Make the DB Request
			dbResponse = (SignonDbResponse) dbProxy.processRequest(dbRequest);


			//list.get(0);
			
			// We always get back a list of CounterpartyIdName objects
			// Make and array of it
			
			
			if ( request.isForCounterParty())
			{

				Vector list = (Vector) dbResponse.getContent();
				Iterator iter = list.iterator();

				CounterpartyIdName[] cpList = new CounterpartyIdName[ list.size() ];
				int count = 0;
				while ( iter.hasNext() )
					cpList[count++] = (CounterpartyIdName) iter.next();
	
					// Now pass it back in the response
					response.setList( cpList );
				
			}
			else if ( request.isForBranchFamily() ) {			


				/**
				 * added for retreving list of Originator Code and Product Type
				 * 
				 */
				Vector originatorlist = (Vector) dbResponse.getContent();
			
				Iterator itrOriginator = originatorlist.iterator();
				Originator[] ocList = new Originator[ originatorlist.size() ];
				int iCount = 0;
				while ( itrOriginator.hasNext() )
					ocList[iCount++] = (Originator) itrOriginator.next();

	
				// Now pass it back in the response
				response.setOriginatorList( ocList );
					
			}
			else if ( request.isForNonBranchFamily() )
			{
				
				/**
				 * added for retreving list of Family members and Product Type
				 * 
				 */
				Vector list = (Vector) dbResponse.getContent();
				//Iterator iter = list.iterator();
			
				Vector counterPartylist = (Vector) list.get(0);			
				Iterator itrCounterParty = counterPartylist.iterator();
				CounterpartyIdName[] cpList = new CounterpartyIdName[ counterPartylist.size() ];
				int count = 0;
				while ( itrCounterParty.hasNext() )
					cpList[count++] = (CounterpartyIdName) itrCounterParty.next();
	
				// Now pass it back in the response
				response.setList( cpList );
				//available Products
				response.setProductTypeList((Vector) list.get(1));	
				//application Modules
				response.setModuleList((Vector) list.get(2));
			}			
			else {
				
			}
			

		} catch (DBException dbe) {
			throw new BusinessException("SignOnRequest", dbe.getMessage());
		} 
		catch (Throwable te) {
			throw new BusinessException("SignOnRequest", te.getMessage());
		} 
		
		return response;
	}

	
}
