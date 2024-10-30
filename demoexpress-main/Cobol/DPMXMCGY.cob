       IDENTIFICATION DIVISION.
       PROGRAM-ID.   DPMXMCGY.
       AUTHOR.       COGNIZANT.
       DATE-WRITTEN. SEPTEMBER 2007.
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
      *    ENTRY DPMXMCGY
      *    NAME  DPMXMCGY(R)
      *
      ******************************************************************
      **         P R O G R A M   D O C U M E N T A T I O N            **
      ******************************************************************
      *                                                                *
      * SYSTEM:    MCA XPRESS APPLICATION                              *
      * PROGRAM:   DPMXMCGY                                            *
      *                                                                *
      * This stored procedure is used to fetch list of all catagories  *
      * for the selected Template ID                                   *
      *                                                                *
      ******************************************************************
      *                                                                *
      * TABLES:                                                        *
      * -------                                                        *
      *                                                                *
      * D0006     - MCA TEMPLATE TABLE                      *
      * VDPM07_MCA_CTGRY     - MCA CATAGORY TABLE                      *
      * VDPM04_ATTRB_DTL     - MCA ATTRIBUTE DETAIL TABLE              *
      * VDTM54_DEBUG_CNTRL   - DEBUG CONTROL TABLE                     *
      *                                                                *
      *----------------------------------------------------------------*
      *                                                                *
      * INCLUDES:                                                      *
      * ---------                                                      *
      *                                                                *
      * SQLCA                - DB2 COMMAREA                            *
      * DTM1401              - MCA TEMPLATE TABLE                      *
      * DTM0701              - MCA CATAGORY TABLE                      *
      * DTM0401              - MCA ATTRIBUTE DETAIL TABLE              *
      * DPM5401              - DEBUG CONTROL TABLE                     *
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
      * 09/07/2007        000       COGNIZANT                          *
      *                             INITIAL IMPLEMENTATION FOR         *
      *                             MCA XPRESS.                        *
      *                                                                *
      ******************************************************************
      *                                                                *
       ENVIRONMENT DIVISION.
       DATA DIVISION.
       WORKING-STORAGE SECTION.
      *
       01  WS-SQLCODE                       PIC -ZZZ9.
       01  WS-PROGRAM                       PIC X(08) VALUE 'DPMXMCGY'.
       01  WS-TEMPLATE-ID                   PIC S9(9) USAGE COMP.
       01  WS-CONVERT-TEMPLATE-ID           PIC 9(18).
       01  WS-INVLD-TEMPID                  PIC X(50) VALUE
           'Invalid Template ID passed'.
       01  WS-DASHES                        PIC X(40) VALUE ALL '='.
       01  WS-TS                            PIC X(26).
       01  WS-DISPLAY-SWITCH                PIC X(01)  VALUE 'N'.
           88 DISPLAY-PARAMETERS                       VALUE 'Y'.
           88 HIDE-PARAMETERS                          VALUE 'N'.
       01  WS-ERROR-AREA.
           05  WS-PARAGRAPH-NAME            PIC X(40).
           05  WS-TABLE-NAME                PIC X(40).
      *
      **SQL COMMUNICATIONS AREA PASSED BACK IN OUTSQLCA
      *
           EXEC SQL
              INCLUDE SQLCA
           END-EXEC
      *
           EXEC SQL
              INCLUDE DTM1401
           END-EXEC
      *
           EXEC SQL
              INCLUDE DTM0701
           END-EXEC
      *
           EXEC SQL
              INCLUDE DTM0401
           END-EXEC
      *
      *                                                                 00024910
           EXEC SQL                                                     00024920
                INCLUDE DTM5401                                         00024930
           END-EXEC.                                                    00024940
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
      *
       PROCEDURE DIVISION USING  OUTSQLCA,
                                 LS-SP-ERROR-AREA,
                                 LS-SP-RC,
                                 LS-TEMPLATE-ID.

      *----------*
       0000-MAIN.
      *----------*

           PERFORM 1000-INITIALIZE
           PERFORM 2000-VALIDATE-INPUT
           PERFORM 3000-LIST-CATAGORY
           IF DISPLAY-PARAMETERS
              PERFORM 9100-DISPLAY-DATA
           END-IF
           PERFORM 9990-GOBACK
           .

      *---------------*
       1000-INITIALIZE.
      *---------------*

           MOVE '1000-INITIALIZE'           TO WS-PARAGRAPH-NAME
           MOVE SPACES                      TO OUTSQLCA
                                               LS-SP-ERROR-AREA
           MOVE 'SP00'                      TO LS-SP-RC
           MOVE LS-TEMPLATE-ID              TO WS-CONVERT-TEMPLATE-ID
           MOVE WS-CONVERT-TEMPLATE-ID      TO WS-TEMPLATE-ID

           EXEC SQL                                                     00051800
                SELECT ACTVT_DSPLY_IN                                   00051900
                  INTO :D054-ACTVT-DSPLY-IN                             00052010
                FROM VDTM54_DEBUG_CNTRL                                 00052040
                WHERE PRGM_ID = :WS-PROGRAM                             00052050
                WITH UR
           END-EXEC                                                     00052060
                                                                        00052070
           MOVE SQLCODE                     TO WS-SQLCODE               00052080
                                                                        00052090
           EVALUATE SQLCODE                                             00052091
              WHEN 0                                                    00052092
                  IF D054-ACTVT-DSPLY-IN = 'Y'                          00052094
                     SET DISPLAY-PARAMETERS TO TRUE                     00052095
                  END-IF                                                00052099
              WHEN 100                                                  00052092
                  INITIALIZE SQLCODE

              WHEN OTHER                                                00052092
                  PERFORM 9000-SQL-ERROR
           END-EVALUATE                                                 00052102

           IF DISPLAY-PARAMETERS
              EXEC SQL
                  SET :WS-TS = CURRENT TIMESTAMP
              END-EXEC
              DISPLAY WS-DASHES
              DISPLAY 'DPMXMCGY STARTED AT      :' WS-TS
              DISPLAY WS-DASHES
           END-IF

               .

      *-------------------*
       2000-VALIDATE-INPUT.
      *-------------------*

           MOVE '2000-VALIDATE-INPUT'       TO WS-PARAGRAPH-NAME

           EXEC SQL
              SELECT  MCA_TMPLT_ID
                INTO :WS-TEMPLATE-ID
                FROM  D0006
               WHERE  MCA_TMPLT_ID = :WS-TEMPLATE-ID
                WITH  UR
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 CONTINUE
              WHEN 100
                 PERFORM 2100-CHECK-TMPLT-WORK
              WHEN OTHER
                 MOVE 'D0006'    TO WS-TABLE-NAME
                 PERFORM 9000-SQL-ERROR
           END-EVALUATE
           .

      *---------------------*
       2100-CHECK-TMPLT-WORK.
      *---------------------*

           MOVE '2100-CHECK-TMPLT-WORK'     TO WS-PARAGRAPH-NAME

           EXEC SQL
              SELECT  MCA_TMPLT_ID
                INTO :WS-TEMPLATE-ID
                FROM  VDPM15_TMPLT_WORK
               WHERE  MCA_TMPLT_ID = :WS-TEMPLATE-ID
                WITH  UR
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 CONTINUE
              WHEN 100
                 MOVE WS-INVLD-TEMPID       TO LS-SP-ERROR-AREA
                 MOVE 'SP50'                TO LS-SP-RC
                 PERFORM 9100-DISPLAY-DATA
                 PERFORM 9990-GOBACK
              WHEN OTHER
                 MOVE 'VDPM15_TMPLT_WORK'   TO WS-TABLE-NAME
                 PERFORM 9000-SQL-ERROR
           END-EVALUATE
           .
      *------------------*
       3000-LIST-CATAGORY.
      *------------------*

           MOVE '3000-LIST-CATAGORY'        TO WS-PARAGRAPH-NAME
           IF DISPLAY-PARAMETERS
              DISPLAY 'DPMXMCGY_CSR CURSOR'
           END-IF

           EXEC SQL
              DECLARE DPMXMCGY_CSR CURSOR WITH HOLD WITH RETURN FOR
   1             SELECT DPM07.ATTRB_CTGRY_ID AS CATAGORY_CODE
   2                   ,DPM04.ATTRB_VALUE_DS AS CATAGORY_NAME
   3                   ,DPM07.CTGRY_SQ       AS CATAGORY_SEQ
   4                   ,DPM07.CTGRY_STAT_CD  AS CTGRY_STATUS
                   FROM VDPM07_MCA_CTGRY DPM07
                       ,VDPM04_ATTRB_DTL DPM04
                   WHERE DPM07.MCA_TMPLT_ID   = :WS-TEMPLATE-ID
                     AND DPM07.ATTRB_CTGRY_ID = DPM04.ATTRB_ID
                     AND DPM04.ATTRB_TYPE_ID = 'C'
                     ORDER BY DPM07.CTGRY_SQ ASC
                     WITH UR
           END-EXEC
      *
           EXEC SQL
              OPEN DPMXMCGY_CSR
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC

              WHEN OTHER
                 MOVE 'DPMXMCGY_CSR'        TO WS-TABLE-NAME
                 PERFORM 9000-SQL-ERROR
           END-EVALUATE
           .

      *--------------*
       9000-SQL-ERROR.
      *--------------*

           MOVE 'Database error has occurred. Please contact DTCC.'
                                            TO LS-SP-ERROR-AREA
           MOVE 'SP99'                      TO LS-SP-RC
           PERFORM 9100-DISPLAY-DATA
           MOVE SQLCODE                     TO WS-SQLCODE
           DISPLAY 'SQLCODE                  :' WS-SQLCODE
           PERFORM 9999-FORMAT-SQLCA
           PERFORM 9990-GOBACK
           .

      *-----------------*
       9100-DISPLAY-DATA.
      *-----------------*

           IF DISPLAY-PARAMETERS
              DISPLAY WS-DASHES
              DISPLAY 'PROGRAM-NAME             :' WS-PROGRAM
              DISPLAY 'PARAGRAPH-NAME           :' WS-PARAGRAPH-NAME
              DISPLAY 'TABLE/CURSOR NAME        :' WS-TABLE-NAME
              DISPLAY 'SP-ERROR-AREA            :' LS-SP-ERROR-AREA
              DISPLAY 'SP-RC                    :' LS-SP-RC
              DISPLAY 'TEMPLATE ID            :' WS-CONVERT-TEMPLATE-ID D
              DISPLAY WS-DASHES
           END-IF

           .

      *-----------*
       9990-GOBACK.
      *-----------*

           PERFORM 9999-FORMAT-SQLCA
           IF DISPLAY-PARAMETERS
              EXEC SQL
                  SET :WS-TS = CURRENT TIMESTAMP
              END-EXEC
              DISPLAY WS-DASHES
              DISPLAY 'OUTSQLCA FOR DPMXMCGY    :' OUTSQLCA
              DISPLAY WS-DASHES
              DISPLAY 'DPMXMCGY ENDED AT        :' WS-TS
              DISPLAY WS-DASHES
           END-IF
           GOBACK
           .

      *-----------------*
       9999-FORMAT-SQLCA.
      *-----------------*

           PERFORM DB2000I-FORMAT-SQLCA
              THRU DB2000I-FORMAT-SQLCA-EXIT
           .
      *
      **MOVE STATEMENTS TO FORMAT THE OUTSQLCA USING DB2000IA & DB2000IB
      *
        COPY DB2000IC.
