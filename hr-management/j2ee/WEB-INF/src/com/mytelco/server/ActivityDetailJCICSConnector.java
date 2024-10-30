package com.mytelco.server;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ibm.ctg.client.ECIRequest;
import com.ibm.ctg.client.JavaGateway;
import com.mytelco.common.ActivityData;

;

public class ActivityDetailJCICSConnector {

    public ActivityDetailJCICSConnector() {
    }

    public ActivityData GetRecord(Float activityNo) { // return activity data
        // make activity data object.
        ActivityData activityData = null; // object
        try {
            String commAreaString;
            commAreaString = activityNo.toString();
            byte commarea[] = new byte[72];
            // IBM037 used here EBCDIC code page used to
            // covert the java unicode byte array into EBCDIC byte format.
            commarea = (commAreaString).getBytes("IBM037");
            /*
             * GTMYTELCOJCICS is the java gateway name running on port 2004
             * CSTCICS1 is the CICS server name. user name and password for the
             * cics server is "telcotmp". transation id for the cics transation
             * to get activity details is CEMP"
             */

            JavaGateway jg = new JavaGateway("tcp://GTMYTELCOJCICS", 2004);
            ECIRequest req = new ECIRequest(ECIRequest.ECI_SYNC, // sync or
                                                                    // async
                    "CSTCICS1", // CICS server name
                    "telcotmp", "telcotmp", // userid & password
                    "COBCIO12", // program name
                    "TRNCACT", // transaction ID
                    commarea, commarea.length, // commarea data & length
                    ECIRequest.ECI_NO_EXTEND, // extended mode
                    ECIRequest.ECI_LUW_NEW); // LUW token

            // send the request to call cics transation using gatway object.
            jg.flow(req);

            // returned result from cics environment is converted back to java
            // string
            commAreaString = new String(req.Commarea, "IBM037");
            jg.close();

            Date currentServerDateTime = null;
            String actDataStatusFlag = new String();
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yy");
            SimpleDateFormat dateTimeFormat = new SimpleDateFormat("MM-dd-yy:hh:mm:ss");

            /*
             * Below is the datastructure of the activity record string as
             * returned by CICS program.
             * 
             */

            actDataStatusFlag = commAreaString.substring(0, 1);
            // If results are proper, status flag will be "Y" else it will be
            // "N"
            if (actDataStatusFlag == "Y") {
                // make and populate empData object
            	activityData = new ActivityData();
                // activity id
            	activityData.setactNo(((Float)(Object)commAreaString.substring(1, 7)));
                // activity description
            	activityData.setactDesc(commAreaString.substring(8, 14));
                // activity description
            	activityData.setactKeyword(commAreaString.substring(9, 24));
                // currentServerDateTime
                currentServerDateTime = dateTimeFormat.parse(commAreaString.substring(57, 74));
            }

            // : return this activity Data object.

        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return activityData;
    }

}