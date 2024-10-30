package com.mytelco.server;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ibm.ctg.client.ECIRequest;
import com.ibm.ctg.client.JavaGateway;
import com.mytelco.common.EmployeeData;

;

public class EmployeeDetailJCICSConnector {

    public EmployeeDetailJCICSConnector() {
    }

    public EmployeeData GetRecord(String employeeNo) { // return employee data
        // make employee data object.
        EmployeeData empData = null; // object
        try {
            String commAreaString;
            commAreaString = employeeNo.substring(0, 5);
            byte commarea[] = new byte[72];
            // IBM037 used here EBCDIC code page used to
            // covert the java unicode byte array into EBCDIC byte format.
            commarea = (commAreaString).getBytes("IBM037");
            /*
             * GTMYTELCOJCICS is the java gateway name running on port 2004
             * CSTCICS1 is the CICS server name. user name and password for the
             * cics server is "telcotmp". transation id for the cics transation
             * to get employee details is CEMP"
             */

            JavaGateway jg = new JavaGateway("tcp://GTMYTELCOJCICS", 2004);
            ECIRequest req = new ECIRequest(ECIRequest.ECI_SYNC, // sync or
                                                                    // async
                    "CSTCICS1", // CICS server name
                    "telcotmp", "telcotmp", // userid & password
                    "COBCIOP2", // program name
                    "TRNCEMP", // transaction ID
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
            String empDataStatusFlag = new String();
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yy");
            SimpleDateFormat dateTimeFormat = new SimpleDateFormat("MM-dd-yy:hh:mm:ss");

            /*
             * Below is the datastructure of the employee record string as
             * returned by CICS program.
             * 
             * 01 DFHCOMMAREA. 03 RETURN-FLAG PIC X(1). 03 FILLER-1 PIC X(72) 01
             * EMPLOYEE-INFO REDEFINES DFHCOMMAREA. 03 FILLER-2 PIC X(1). 03
             * EMPLOYEE-RECORD. 05 EMP-ID PIC X(6). 05 EMP-DEPT-CODE PIC X(3).
             * 05 EMP-NAME PIC X(30). 05 EMP-JOB PIC X(8). 05 EMP-BIRTHDATE PIC
             * X(8). 03 CURRENT-DATETIME. 05 DATE-AREA PIC X(8). 05 FILLER-3 PIC
             * X(1) VALUE ":". 05 TIME-AREA PIC X(8).
             */

            empDataStatusFlag = commAreaString.substring(0, 1);
            // If results are proper, status flag will be "Y" else it will be
            // "N"
            if (empDataStatusFlag == "Y") {
                // make and populate empData object
                empData = new EmployeeData();
                // employee id
                empData.setEmpNo(commAreaString.substring(1, 7));
                // department code
                empData.setWorkDept(commAreaString.substring(8, 14));
                // employee First Name
                empData.setFirstName(commAreaString.substring(9, 24));
                // employee Mid Name
                empData.setMidinit(commAreaString.substring(25, 25));
                // employee Last Name
                empData.setLastName(commAreaString.substring(26, 40));
                // employee Job
                empData.setJob(commAreaString.substring(41, 48));
                // employee Birth Date
                empData.setBirthDate(dateFormat.parse(commAreaString.substring(49, 56)));
                // currentServerDateTime
                currentServerDateTime = dateTimeFormat.parse(commAreaString.substring(57, 74));
            }

            // : return this empData object.

        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return empData;
    }

}