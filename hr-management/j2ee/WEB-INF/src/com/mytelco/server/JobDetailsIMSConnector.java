package com.mytelco.server;

import java.io.DataOutputStream;
import java.net.Socket;

public class JobDetailsIMSConnector {

    private Socket socket = null;

    // connection information
    private String hostName;

    private int portNumber;

    // IMS information
    private String tranCode;

    private String datastoreID;

    private String ltermName;

    // RACF security information
    private String racfUserID;

    private String racfGroupName;

    private String password;

    // IMS Connect information
    private String clientID;

    private String exitID = "*JobDetailsIMSConnector*";

    private byte syncLevel;

    private byte commitMode;

    private int prefixLength = 80;

    public JobDetailsIMSConnector(String hostName, int portNumber, String datastoreID, String ltermName, String tranCode,
            String clientID, String racfUserID, String racfGroupName, String password, byte syncLevel, byte commitMode) {
        // set the corresponding transaction data, making all strings 8
        // characters long
        this.hostName = stringPad(hostName, ' ', 13);
        this.portNumber = portNumber;
        this.datastoreID = stringPad(datastoreID, ' ', 8);
        this.ltermName = stringPad(ltermName, ' ', 8);
        this.tranCode = stringPad(tranCode, ' ', 8);
        this.clientID = stringPad(clientID, ' ', 8);
        this.racfUserID = stringPad(racfUserID, ' ', 8);
        this.racfGroupName = stringPad(racfGroupName, ' ', 8);
        this.password = stringPad(password, ' ', 8);
        this.syncLevel = syncLevel;
        this.commitMode = commitMode;
    }

    /**
     * Connects to the host.
     */
    public void connect() {
        try {
            // open a socket for the transaction
            socket = new Socket(hostName, portNumber);
        } catch (Exception e) {
            System.err.println(e);
            System.exit(1);
        }
    }

    /**
     * Disconnects from the host.
     */
    public void disconnect() {
        // verify socket open before attempting to disconnect
        if (socket != null) {
            try {
                socket.close();
                socket = null;
            } catch (Exception e) {
                System.err.println(e);
                System.exit(1);
            }
        }
    }

    /**
     * Sends prefix and segment data.
     */
    public void send(String segment, char nad) {
        int totalLength;

        // +4 for first LL, ZZ and final LL, ZZ
        totalLength = 4 + prefixLength + 4;

        // add in segment length, if segment is defined
        if ((segment != null) && (segment.length() > 0)) {
            totalLength += segment.length() + 12; // +12 for LL, ZZ, tranCode
        }

        try {
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            out.writeInt(totalLength); // send total message length
            out.writeShort(prefixLength); // send LL
            out.writeShort((short) 0); // send ZZ
            out.writeBytes(exitID); // send identifier
            out.writeInt(0); // send extra full word 0
            out.writeInt(0); // send RESV
            out.writeBytes(clientID); // send client id
            out.writeByte(0); // send FLG 1
            out.write(commitMode); // send FLG 2 - commit mode
            out.write(syncLevel); // send FLG 3 - synclevel
            out.writeByte(nad); // send FLG 4 - ack/nack/decallocate
            out.writeBytes(tranCode); // send transaction code
            out.writeBytes(datastoreID); // send datastore id
            out.writeBytes(ltermName); // send lterm name
            out.writeBytes(racfUserID); // send RACF id
            out.writeBytes(racfGroupName); // send RACF group
            out.writeBytes(password); // send password

            // the 'if' is not supposed to be executed when you ACK, NACK,
            // DECALLOCATE, as data is irrelevant
            if ((segment != null) && (segment.length() > 0)) {
                // + 12 for LL and ZZ and trancode
                short recordLength = (short) (segment.length() + 12);
                out.writeShort(recordLength); // send LL
                out.writeShort((short) 0); // send ZZ
                out.writeBytes(tranCode); // send transaction code
                out.writeBytes(segment); // send segment
            }

            // send final LL ZZ to signal no more data to IMS Connect
            out.writeShort((short) 4); // send LL
            out.writeShort((short) 0); // send ZZ
        } catch (Exception e) {
            System.err.println(e);
            System.exit(1);
        }
    }

    /**
     * Pads or truncates the String to the specified length.
     */
    private static String stringPad(String string, char padChar, int padLength) {
        // construct a stringbuffer for padding efficiency
        StringBuffer stringBuffer = new StringBuffer(string);
        // pad the stringbuffer if string.length() is less than padLength
        for (int i = 0; i < (padLength - string.length()); i++) {
            stringBuffer.append(padChar);
        }
        // if truncation was necessary, substring will take care of that
        return stringBuffer.substring(0, padLength);
    }

    public static synchronized void  sendJobDetailsToIMS(String JobID, String subsidary, String DeptNo, String runDate, String runTime) {
        // set default values
        String hostName = "mytelcomach";
        int portNumber = 9999;
        String datastoreID = "JOBD";
        String ltermName = "";
        String tranCode = "COBIMSO1"; // this transaction code invokes
        // COBIMSO1 COBOL program
        String clientID = "TELCO";
        String racfUserID = "TELCO";
        String racfGroupName = "";
        String password = "telco";
        byte syncLevel = 0x00; // synclevel = none
        byte commitMode = 0x20; // send-commit

        // set input text (jobid, subsidiary, deptno, rundate, runtime)
        String segment = stringPad(JobID, ' ',20) + stringPad(subsidary , ' ', 20) + stringPad(DeptNo,' ',3) + runDate+ runTime;

        // create a new JobDetailsIMSConnector object
        JobDetailsIMSConnector SendJobDetails = new JobDetailsIMSConnector(hostName, portNumber, datastoreID, ltermName, tranCode,
                clientID, racfUserID, racfGroupName, password, syncLevel, commitMode);

        // connect to the host
        System.err.println("Connecting to the host...");
        SendJobDetails.connect();

        // send the segment data
        System.err.println("Sending input data...");
        SendJobDetails.send(segment, ' ');
        //      disconnect from the host

        System.err.println("Disconnecting from the host...");
        SendJobDetails.disconnect();
    }
    
    
//    public static void main(String[] args) {
//        // set default values
//        String hostName = "mytelcomach";
//        int portNumber = 9999;
//        String datastoreID = "JOBD";
//        String ltermName = "";
//        String tranCode = "JOBDET"; // this transaction code invokes
//        // COBIMSO1 program
//        String clientID = "TELCO";
//        String racfUserID = "TELCO";
//        String racfGroupName = "";
//        String password = "telco";
//        byte syncLevel = 0x00; // synclevel = none
//        byte commitMode = 0x20; // send-commit
//
//        // set input text (jobid, subsidiary, deptno, rundate, runtime)
//        String segment = "AN960C10";
//
//        // create a new JobDetailsIMSConnector object
//        JobDetailsIMSConnector JobDetailsIMSConnector = new JobDetailsIMSConnector(hostName, portNumber, datastoreID, ltermName, tranCode,
//                clientID, racfUserID, racfGroupName, password, syncLevel, commitMode);
//
//        // connect to the host
//        System.err.println("Connecting to the host...");
//        JobDetailsIMSConnector.connect();
//
//        // send the segment data
//        System.err.println("Sending input data...");
//        JobDetailsIMSConnector.send(segment, ' ');
//        //      disconnect from the host
//
//        System.err.println("Disconnecting from the host...");
//        JobDetailsIMSConnector.disconnect();
//    }

}

