package com.dtcc.dnv.otc.common.signon.config;


/**
 * @author vxsubram
 * @date Aug 10, 2007
 * @version 1.0 
 * 
 * This class serves as a Factory class to instantiate an signon property loader.
 */
class DSSignonConfigManager {
	
	DSSignonPropertyLoader getPropertyLoader(){
		return new DSSignonPropertyLoader();
	}

}
