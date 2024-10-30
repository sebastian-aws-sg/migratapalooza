/*
 * Created on Oct 4, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtcc.dnv.mcx.db.mca;

import java.sql.SQLException;
import java.sql.Types;
import java.util.Arrays;
import java.util.TreeMap;

import com.dtcc.dnv.mcx.beans.ProductBean;
import com.dtcc.dnv.mcx.beans.SubProductBean;
import com.dtcc.dnv.mcx.db.MCXCommonDB;
import com.dtcc.dnv.mcx.dbhelper.home.AppContextDbRequest;
import com.dtcc.dnv.mcx.dbhelper.home.AppContextDbResponse;
import com.dtcc.dnv.mcx.util.InputReturnCodeMapping;
import com.dtcc.dnv.otc.common.exception.DBException;
import com.dtcc.dnv.otc.common.layers.IDbRequest;
import com.dtcc.dnv.otc.common.layers.IDbResponse;
import com.dtcc.dnv.otc.legacy.SQLCA;

/**
 * @author VVaradac
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DPMXMPSPDAO extends MCXCommonDB {		

	/* (non-Javadoc)
	 * @see com.dtcc.dnv.otc.legacy.CommonDB#execute(com.dtcc.dnv.otc.common.layers.IDbRequest, com.dtcc.dnv.otc.common.layers.IDbResponse)
	 */
	public void execute(IDbRequest request, IDbResponse response)
			throws DBException, SQLException {
		
		SPName = "DPMXMPSP";
		AppContextDbRequest dbRequest = (AppContextDbRequest) request;
		AppContextDbResponse dbResponse = (AppContextDbResponse) response;
		
		cstmt = con.prepareCall("{ call " + QUAL + "DPMXMPSP (?,?,?) }");

		cstmt.registerOutParameter(1, Types.CHAR);
		cstmt.registerOutParameter(2, Types.CHAR);
		cstmt.registerOutParameter(3, Types.CHAR);

		rs = cstmt.executeQuery();

		sqlca = new SQLCA(cstmt.getString(1));
		sSpErrArea = (String) cstmt.getObject(2);
		dbResponse.setSpReturnCode(cstmt.getString(3));
		dbResponse.setSpResponseMessage(sSpErrArea);
		
        if (sqlca.getSqlCode() == 0 && dbResponse.getSpReturnCode().equalsIgnoreCase(InputReturnCodeMapping.SP00))
		{
			TreeMap prods = new TreeMap();
			TreeMap subProds = new TreeMap();
			ProductBean prod = null;
			SubProductBean subProd = null;
			String prevProdCd = null;
			String prevSubProdCd = null;
			while(rs != null && rs.next())
			{
				if(prevProdCd == null || !prevProdCd.equalsIgnoreCase(rs.getString(1)))
				{
					prod = new ProductBean();
					prod.setProdId(rs.getString(1));
					prod.setProdNm(rs.getString(2));
				}
				if(prevSubProdCd == null || !prevSubProdCd.equalsIgnoreCase(rs.getString(4)))
				{
					subProd = new SubProductBean();
					subProd.setSubProdCd(rs.getString(4));
					subProd.setSubProdNm(rs.getString(5));
					if(rs.getInt(7) != 0)
					{
						subProd.setTmpltPresent(true);
					}
				}
				prevSubProdCd = subProd.getSubProdCd();
				subProds.put(subProd.getSubProdCd(), subProd);
				
				prod.setSubProds(Arrays.asList(subProds.values().toArray()));
				prevProdCd = prod.getProdId();
				prods.put(prod.getProdId(), prod);
			}
			dbResponse.setContent(Arrays.asList(prods.values().toArray()));
		}

	}
}
