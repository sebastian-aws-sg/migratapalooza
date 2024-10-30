       IDENTIFICATION DIVISION.
       PROGRAM-ID.   DPMXHUSD.
       AUTHOR.       COGNIZANT.
       DATE-WRITTEN. OCTOBER 2007.
      *
      *
      ****************************************************************
      *   THIS IS AN UNPUBLISHED PROGRAM OWNED BY ISCC               *
      *   IN WHICH A COPYRIGHT SUBSISTS AS OF JULY 2006.             *
      ****************************************************************
      ****************************************************************
      *                   COMPILATION INSTRUCTION                    *
      *                  COMPILE DB2 VS COBOL 370                    *
      ****************************************************************
      *    **LNKCTL**
      *    INCLUDE SYSLIB(DSNRLI)
      *    MODE AMODE(31) RMODE(ANY)
      *    ENTRY DPMXHUSD
      *    NAME  DPMXHUSD(R)
      *
      ******************************************************************
      **         P R O G R A M   D O C U M E N T A T I O N            **
      ******************************************************************
      * SYSTEM:    MCA XPRESS APPLICATION                              *
      * PROGRAM:   DPMXHUSD                                            *
      *                                                                *
      * THIS STORED PROCEDURE IS CALLED WHEN CLICKED AT USER LINK IN   *
      * XPRESS APPLICATION. IT QUERIES FOR THE USER INFO IN THE TABLE  *
      * D0003 AND SENDS THE USER DETAIL TO THE WEB.        *
      *                                                                *
      ******************************************************************
      * TABLES:                                                        *
      * -------                                                        *
      * VDPM01_MCA-CMPNY   - COMPANY TABLE FOR MCA
      * D0003  - COMPANY USER TABLE FOR MCA                *
      *----------------------------------------------------------------*
      * INCLUDES:                                                      *
      * ---------                                                      *
      * SQLCA              - DB2 COMMAREA                              *
      * DPM0101            - COMPANY TABLE FOR MCA
      * DPM0301            - COMPANY USER TABLE FOR MCA                *
      * DTM5401            - VDTM54_DEBUG_CNTRL                *
      *----------------------------------------------------------------*
      * COPYBOOK:                                                      *
      * ---------                                                      *
      * DB2000IA                                                       *
      * DB2000IB                                                       *
      * DB2000IC                                                       *
      *----------------------------------------------------------------*
      *              M A I N T E N A N C E   H I S T O R Y             *
      *                                                                *
      *                                                                *
      * DATE CHANGED    VERSION     PROGRAMMER                         *
      * ------------    -------     --------------------               *
      *                                                                *
      * 10/19/2007        001       COGNIZANT                          *
      *                             INITIAL IMPLEMENTATION FOR         *
      *                             MCA XPRESS.                        *
      ******************************************************************
       ENVIRONMENT DIVISION.
       DATA DIVISION.
       WORKING-STORAGE SECTION.
       01  WS-SQLCODE                     PIC -ZZ9.
       01  WS-PROGRAM                     PIC X(08) VALUE 'DPMXHUSD'.
       01  WS-DASHES                      PIC X(80) VALUE ALL '='.
       01  WS-EMPTY-USER-ID               PIC X(50) VALUE
           'User Id is Empty'.
       01  WS-INVLD-USER-ID               PIC X(50) VALUE
           'Invalid User id'.
       01  WS-ERROR-AREA.
           05  WS-PARAGRAPH-NAME          PIC X(40).
       01  WS-TS                          PIC X(26).
       01  WS-DISPLAY-SWITCH              PIC X(01) VALUE 'N'.
           88 DISPLAY-PARAMETERS                    VALUE 'Y'.
           88 HIDE-PARAMETERS                       VALUE 'N'.

      **SQL COMMUNICATIONS AREA PASSED BACK IN OUTSQLCA
           EXEC SQL
              INCLUDE SQLCA
           END-EXEC

           EXEC SQL
              INCLUDE DPM0101
           END-EXEC

           EXEC SQL
              INCLUDE DPM0301
           END-EXEC

      * INCLUDE FOR VDTM54_DEBUG_CNTRL                                  00024910
           EXEC SQL                                                     00024920
                INCLUDE DTM5401                                         00024930
           END-EXEC.                                                    00024940

      **DB2 STANDARD COPYBOOK WITH FORMATTED DISPLAY SQLCA
      **THIS MUST REMAIN AS THE LAST ENTRY IN WORKING STORAGE
       COPY  DB2000IA.

       LINKAGE SECTION.
      **PICTURE CLAUSE FOR OUTSQLCA - PIC X(179) - FOR LINKAGE SECTION
       COPY  DB2000IB.

       01  LS-SP-ERROR-AREA               PIC X(80).
       01  LS-SP-RC                       PIC X(04).
       01  LS-USER-ID                     PIC X(10).
       01  LS-CMPNY-NAME                  PIC X(255).
       01  LS-USER-NAME                   PIC X(200).
       01  LS-USER-EMAIL                  PIC X(100).
       01  LS-USER-PHONE-NB               PIC X(20).

       PROCEDURE DIVISION USING  OUTSQLCA,
                                 LS-SP-ERROR-AREA,
                                 LS-SP-RC,
                                 LS-USER-ID,
                                 LS-CMPNY-NAME,
                                 LS-USER-NAME,
                                 LS-USER-EMAIL,
                                 LS-USER-PHONE-NB.
      *----------*
       0000-MAIN.
      *----------*

           PERFORM 1000-INITIALIZE.

           PERFORM 2000-VALIDATE-INPUT.

           PERFORM 3000-GET-USER-INFO.

           PERFORM 9990-GOBACK

           .
      *------------------------*
       1000-INITIALIZE.
      *------------------------*

           MOVE '1000-INITIALIZE'          TO WS-PARAGRAPH-NAME
           MOVE SPACES                     TO OUTSQLCA, LS-SP-ERROR-AREA
           MOVE 'SP00'                     TO LS-SP-RC

                                                                        00051700
           EXEC SQL                                                     00051800
                SELECT ACTVT_DSPLY_IN                                   00051900
                  INTO :D054-ACTVT-DSPLY-IN                             00052010
                FROM   VDTM54_DEBUG_CNTRL                               00052040
                WHERE PRGM_ID = :WS-PROGRAM                             00052050
                WITH UR
           END-EXEC                                                     00052060
                                                                        00052070
           EVALUATE SQLCODE                                             00052091
              WHEN 0                                                    00052092
                  IF D054-ACTVT-DSPLY-IN = 'Y'                          00052094
                     SET DISPLAY-PARAMETERS TO TRUE                     00052095
                     EXEC SQL
                         SET :WS-TS = CURRENT TIMESTAMP
                     END-EXEC
                  END-IF                                                00052099
              WHEN 100                                                  00052092
                  CONTINUE
              WHEN OTHER                                                00052092
                  PERFORM 9000-SQL-ERROR
           END-EVALUATE                                                 00052102

           IF DISPLAY-PARAMETERS
              DISPLAY WS-DASHES
              DISPLAY 'DPMXHUSD STARTED AT      :' WS-TS
              DISPLAY WS-DASHES
           END-IF

           .
      *------------------------*
       2000-VALIDATE-INPUT.
      *------------------------*

           MOVE '2000-VALIDATE-INPUT'               TO WS-PARAGRAPH-NAME

           IF LS-USER-ID = SPACES
              MOVE WS-EMPTY-USER-ID                 TO LS-SP-ERROR-AREA
              MOVE 'SP50'                           TO LS-SP-RC
              PERFORM 9100-DISPLAY-DATA
              PERFORM 9990-GOBACK
           END-IF

           .
      *------------------------*
       3000-GET-USER-INFO.
      *------------------------*
      *   QUERY THE USER TABLE TO FIND THE USER INFO.

           MOVE '3000-GET-USER-INFO'              TO WS-PARAGRAPH-NAME

           EXEC SQL
              SELECT DPM01.CMPNY_NM
                    ,DPM03.CMPNY_USER_NM
                    ,DPM03.CMPNY_USER_EMAIL_ID
                    ,DPM03.CMPNY_USER_PHONE_NB
                INTO
                    :D001-CMPNY-NM
                   ,:D003-CMPNY-USER-NM
                   ,:D003-CMPNY-USER-EMAIL-ID
                   ,:D003-CMPNY-USER-PHONE-NB
                FROM D0003 DPM03
                    ,D0005  DPM01
                WHERE DPM03.CMPNY_USER_ID = :LS-USER-ID
                  AND DPM03.CMPNY_ID      = DPM01.CMPNY_ID
                WITH UR
           END-EXEC

           EVALUATE SQLCODE
              WHEN ZEROES
                 MOVE D001-CMPNY-NM                 TO LS-CMPNY-NAME
                 MOVE D003-CMPNY-USER-NM            TO LS-USER-NAME
                 MOVE D003-CMPNY-USER-EMAIL-ID      TO LS-USER-EMAIL
                 MOVE D003-CMPNY-USER-PHONE-NB      TO LS-USER-PHONE-NB
              WHEN +100
                 MOVE WS-INVLD-USER-ID              TO LS-SP-ERROR-AREA
                 MOVE 'SP50'                        TO LS-SP-RC
                 PERFORM 9100-DISPLAY-DATA
                 PERFORM 9990-GOBACK
              WHEN OTHER
                 PERFORM 9000-SQL-ERROR
           END-EVALUATE

           IF DISPLAY-PARAMETERS
              PERFORM 9100-DISPLAY-DATA
           END-IF

           .
      *------------------------*
       9000-SQL-ERROR.
      *------------------------*

           PERFORM 9100-DISPLAY-DATA
           MOVE 'Database error has occurred. Please contact DTCC.'
                                            TO LS-SP-ERROR-AREA
           MOVE SQLCODE                     TO WS-SQLCODE
           MOVE 'SP99'                      TO LS-SP-RC
           DISPLAY 'SQLCODE                 :' WS-SQLCODE
           DISPLAY 'PARAGRAPH-NAME          :' WS-PARAGRAPH-NAME
           PERFORM 9999-FORMAT-SQLCA
           PERFORM 9990-GOBACK
           .
      *------------------------*
       9100-DISPLAY-DATA.
      *------------------------*

           IF DISPLAY-PARAMETERS
              DISPLAY WS-DASHES
              DISPLAY 'PARAGRAPH-NAME           :' WS-PARAGRAPH-NAME
              DISPLAY 'SP-ERROR-AREA            :' LS-SP-ERROR-AREA
              DISPLAY 'SP-RC                    :' LS-SP-RC
              DISPLAY 'COMPANY ID               :' LS-CMPNY-NAME
              DISPLAY 'USER ID                  :' LS-USER-ID
              DISPLAY 'USER NAME                :' LS-USER-NAME
              DISPLAY 'USER EMAIL ID            :' LS-USER-EMAIL
              DISPLAY 'USER PHONE NUMBER        :' LS-USER-PHONE-NB
           END-IF

           .
      *------------------------*
       9990-GOBACK.
      *------------------------*

            PERFORM 9999-FORMAT-SQLCA
            IF DISPLAY-PARAMETERS
               DISPLAY WS-DASHES
               DISPLAY 'OUTSQLCA FOR DPMXHUSD    :' OUTSQLCA
               DISPLAY WS-DASHES
               EXEC SQL
                  SET :WS-TS = CURRENT TIMESTAMP
               END-EXEC
               DISPLAY 'DPMXHUSD ENDED AT        :' WS-TS
               DISPLAY WS-DASHES
            END-IF
            GOBACK
           .
      *------------------------*
       9999-FORMAT-SQLCA.
      *------------------------*
           PERFORM DB2000I-FORMAT-SQLCA
              THRU DB2000I-FORMAT-SQLCA-EXIT
              .
      **MOVE STATEMENTS TO FORMAT THE OUTSQLCA USING DB2000IA & DB2000IB
        COPY DB2000IC.
