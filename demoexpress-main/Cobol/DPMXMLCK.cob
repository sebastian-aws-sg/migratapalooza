       IDENTIFICATION DIVISION.
       PROGRAM-ID.   DPMXMLCK.
       AUTHOR.       COGNIZANT.
       DATE-WRITTEN. OCTOBER 2007.
      *                                                                *
      ******************************************************************
      *                                                                *
      *   THIS IS AN UNPUBLISHED PROGRAM OWNED BY ISCC                 *
      *   IN WHICH A COPYRIGHT SUBSISTS AS OF OCTOBER 2003.            *
      *                                                                *
      ******************************************************************
      ******************************************************************
      *                                                                *
      *                   COMPILATION INSTRUCTION                      *
      *                  COMPILE DB2 VS COBOL 370                      *
      *                                                                *
      ******************************************************************
      *
      *    **LNKCTL**
      *    INCLUDE SYSLIB(DSNRLI)
      *    MODE AMODE(31) RMODE(ANY)
      *    ENTRY DPMXMLCK
      *    NAME  DPMXMLCK(R)
      *
      ******************************************************************
      **         P R O G R A M   D O C U M E N T A T I O N            **
      ******************************************************************
      *                                                                *
      * SYSTEM:    MCA XPRESS APPLICATION                              *
      * PROGRAM:   DPMXMLCK                                            *
      *                                                                *
      * This stored procedure is used to lock/unlock/view locking info *
      * of a template for a particular user                            *
      *                                                                *
      ******************************************************************
      *                                                                *
      * TABLES:                                                        *
      * -------                                                        *
      *                                                                *
      * D0006    - MCA TEMPLATE TABLE                       *
      * D0003   - MCA ORG USER TABLE                       *
      * VDPM10_MCA_LOCK     - MCA LOCK TABLE                           *
      * VDPM10AMCA_LOCK     - MCA LOCK HISTORY TABLE                   *
      * VDTM54_DEBUG_CNTRL    - DEBUG CONTROL TABLE                    *
      *                                                                *
      *----------------------------------------------------------------*
      *                                                                *
      * INCLUDES:                                                      *
      * ---------                                                      *
      *                                                                *
      * SQLCA               - DB2 COMMAREA                             *
      * DTM7301             - MCA TEMPLATE TABLE                       *
      * DTM6501             - MCA ORG USER TABLE                       *
      * DTM8701             - MCA LOCK TABLE                           *
      * DTM8801             - MCA LOCK HISTORY TABLE                   *
      *                                                                *
      *----------------------------------------------------------------*
      *                                                                *
      * COPYBOOK:                                                      *
      * ---------                                                      *
      *                                                                *
      * DB2000IA                                                       *
      * DB20001B                                                       *
      * DB20001C                                                       *
      *                                                                *
      *----------------------------------------------------------------*
      *                                                                *
      *              M A I N T E N A N C E   H I S T O R Y             *
      *                                                                *
      * DATE CHANGED    VERSION     PROGRAMMER                         *
      * ------------    -------     --------------------               *
      *                                                                *
      * 09/10/2007        000       COGNIZANT                          *
      *                             INITIAL IMPLEMENTATION FOR         *
      *                             MCA XPRESS.                        *
      *                                                                *
      ******************************************************************
      *                                                                *
       ENVIRONMENT DIVISION.
       DATA DIVISION.
       WORKING-STORAGE SECTION.
      *
       01  WS-OUT-SQLCODE                   PIC +(9)9.
       01  WS-PROGRAM                       PIC X(08) VALUE 'DPMXMLCK'.
       01  WS-CALL                          PIC X(08) VALUE 'DPMXMLOK'.
       01  WS-DASHES                        PIC X(40) VALUE ALL '='.
       01  WS-ERROR-AREA                    PIC X(80).
       01  WS-RC                            PIC X(04).
       01  WS-ERROR-AREA2.
           05  WS-PARAGRAPH-NAME            PIC X(40).
           05  WS-TABLE-NAME                PIC X(40).
       01  WS-TS                          PIC X(26).
       01  WS-DISPLAY-SWITCH              PIC X(01) VALUE 'N'.
           88 DISPLAY-PARAMETERS                    VALUE 'Y'.
           88 HIDE-PARAMETERS                       VALUE 'N'.
      *
      **SQL COMMUNICATIONS AREA PASSED BACK IN OUTSQLCA
      *
           EXEC SQL
              INCLUDE SQLCA
           END-EXEC
      *
           EXEC SQL
              INCLUDE DPM1401
           END-EXEC
      *
           EXEC SQL
              INCLUDE DPM0301
           END-EXEC
      *
           EXEC SQL
              INCLUDE DPM1001
           END-EXEC
      * INCLUDE FOR VDTM54_DEBUG_CNTRL                                  00024910
           EXEC SQL                                                     00024920
                INCLUDE DTM5401                                         00024930
           END-EXEC.                                                    00024940
      *
      **DB2 STANDARD COPYBOOK WITH FORMATTED DISPLAY SQLCA
      **THIS MUST REMAIN AS THE LAST ENTRY IN WORKING STORAGE
      *
       COPY  DB2000IA.
      *
       LINKAGE SECTION.
      *
      **PICTURE CLAUSE FOR OUTSQLCA - PIC X(179) - FOR LINKAGE SECTION
      *
       COPY  DB2000IB.
      *
       01  LS-SP-ERROR-AREA                 PIC X(80).
       01  LS-SP-RC                         PIC X(04).
       01  LS-TEMPLATE-ID                   PIC S9(9) USAGE COMP.
       01  LS-CMPNY-ID                      PIC X(8).
       01  LS-USER-ID                       PIC X(10).
       01  LS-ACTION-IND                    PIC X(01).
       01  LS-LCK-CMPNY-CD                  PIC X(8).
       01  LS-LCK-USER-ID                   PIC X(10).
       01  LS-LCK-USER-NM                   PIC X(200).
      *
       PROCEDURE DIVISION USING  OUTSQLCA,
                                 LS-SP-ERROR-AREA,
                                 LS-SP-RC,
                                 LS-TEMPLATE-ID,
                                 LS-CMPNY-ID,
                                 LS-USER-ID,
                                 LS-ACTION-IND,
                                 LS-LCK-CMPNY-CD,
                                 LS-LCK-USER-ID ,
                                 LS-LCK-USER-NM .

      *----------*
       0000-MAIN.
      *----------*

           PERFORM 1000-INITIALIZE
           PERFORM 2000-CALL-LOK
           IF DISPLAY-PARAMETERS
              PERFORM 9100-DISPLAY-DATA
           END-IF
           PERFORM 9990-GOBACK
           .

      *------------------------*
       1000-INITIALIZE.
      *------------------------*

           MOVE '1000-INITIALIZE'           TO WS-PARAGRAPH-NAME
           MOVE SPACES                      TO OUTSQLCA
                                               LS-SP-ERROR-AREA
           MOVE SPACES                      TO WS-RC
           MOVE 'SP00'                      TO LS-SP-RC
                                                                        00051700
           EXEC SQL                                                     00051800
                SELECT ACTVT_DSPLY_IN                                   00051900
                  INTO :D054-ACTVT-DSPLY-IN                             00052010
                FROM   VDTM54_DEBUG_CNTRL                               00052040
                WHERE PRGM_ID = :WS-PROGRAM                             00052050
           END-EXEC                                                     00052060
                                                                        00052070
                                                                        00052090
           EVALUATE SQLCODE                                             00052091
              WHEN 0                                                    00052092
                  IF D054-ACTVT-DSPLY-IN = 'Y'                          00052094
                     SET DISPLAY-PARAMETERS TO TRUE                     00052095
                  END-IF                                                00052099
              WHEN 100                                                  00052092
                  CONTINUE
              WHEN OTHER                                                00052092
                  PERFORM 9000-SQL-ERROR
           END-EVALUATE                                                 00052102

           IF DISPLAY-PARAMETERS
              EXEC SQL
                  SET :WS-TS = CURRENT TIMESTAMP
              END-EXEC
              DISPLAY WS-DASHES
              DISPLAY 'DPMXMLCK STARTED AT      :' WS-TS
              DISPLAY WS-DASHES
           END-IF
           .
      *----------------*
       2000-CALL-LOK.
      *----------------*
           MOVE '2000-CALL-LOK'             TO WS-PARAGRAPH-NAME

           CALL   WS-CALL  USING  LS-TEMPLATE-ID,
                                  LS-CMPNY-ID,
                                  LS-USER-ID,
                                  LS-ACTION-IND ,
                                  LS-LCK-CMPNY-CD
                                  LS-LCK-USER-ID,
                                  LS-LCK-USER-NM,
                                  WS-OUT-SQLCODE,
                                  WS-ERROR-AREA,
                                  WS-RC

           MOVE WS-RC                       TO LS-SP-RC
           MOVE WS-ERROR-AREA               TO LS-SP-ERROR-AREA
           MOVE WS-OUT-SQLCODE              TO SQLCODE

           EVALUATE SQLCODE
              WHEN 0
              WHEN 100
                  CONTINUE
              WHEN OTHER
                 PERFORM 9000-SQL-ERROR
           END-EVALUATE.
      *---------------------*
       9000-SQL-ERROR.
      *------------------------*

           MOVE 'DATABASE ERROR HAS OCCURRED. PLEASE CONTACT DTCC.'
                                            TO LS-SP-ERROR-AREA
           MOVE 'SP99'                      TO LS-SP-RC
           MOVE WS-OUT-SQLCODE              TO SQLCODE
           DISPLAY 'PARAGRAPH-NAME          :' WS-PARAGRAPH-NAME
           DISPLAY 'SQLCODE                :' WS-OUT-SQLCODE
           PERFORM 9100-DISPLAY-DATA
           PERFORM 9999-FORMAT-SQLCA
           PERFORM 9990-GOBACK
           .
      *------------------------*
       9100-DISPLAY-DATA.
      *------------------------*

           DISPLAY WS-DASHES
           DISPLAY 'PROGRAM-NAME             :' WS-PROGRAM
           DISPLAY 'PARAGRAPH-NAME           :' WS-PARAGRAPH-NAME
           DISPLAY 'SP-ERROR-AREA            :' LS-SP-ERROR-AREA
           DISPLAY 'SP-RC                    :' LS-SP-RC

           .
      *------------------------*
       9990-GOBACK.
      *------------------------*

            PERFORM 9999-FORMAT-SQLCA
            IF DISPLAY-PARAMETERS
               EXEC SQL
                   SET :WS-TS = CURRENT TIMESTAMP
               END-EXEC
               DISPLAY WS-DASHES
               DISPLAY 'OUTSQLCA FOR DPMXMLCK    :' OUTSQLCA
               DISPLAY WS-DASHES
               DISPLAY 'DPMXMLCK ENDED AT        :' WS-TS
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
      *
      **MOVE STATEMENTS TO FORMAT THE OUTSQLCA USING DB2000IA & DB2000IB
      *
        COPY DB2000IC.
