      ******************************************************************
      * DCLGEN TABLE(NSCC.VDPM03_CMPNY_USER)                           *
      *        LIBRARY(DB2T.DCLGEN.TEST.NSCC.COBCOPY(DPM0301))         *
      *        ACTION(REPLACE)                                         *
      *        LANGUAGE(COBOL)                                         *
      *        NAMES(D003-)                                            *
      *        QUOTE                                                   *
      *        COLSUFFIX(YES)                                          *
      *        INDVAR(YES)                                             *
      * ... IS THE DCLGEN COMMAND THAT MADE THE FOLLOWING STATEMENTS   *
      ******************************************************************
           EXEC SQL DECLARE NSCC.VDPM03_CMPNY_USER TABLE
           ( CMPNY_ID                       CHAR(8) NOT NULL,
             CMPNY_USER_ID                  CHAR(10) NOT NULL,
             CMPNY_USER_NM                  CHAR(200) NOT NULL,
             CMPNY_USER_EMAIL_ID            CHAR(100) NOT NULL,
             ROW_UPDT_TS                    TIMESTAMP NOT NULL,
             UMG_USER_ID                    CHAR(50) NOT NULL,
             CMPNY_USER_PHONE_NB            CHAR(20) NOT NULL
           ) END-EXEC.
      ******************************************************************
      * COBOL DECLARATION FOR TABLE NSCC.VDPM03_CMPNY_USER             *
      ******************************************************************
       01  DCLVDPM03-CMPNY-USER.
      *                       CMPNY_ID
           10 D003-CMPNY-ID        PIC X(8).
      *                       CMPNY_USER_ID
           10 D003-CMPNY-USER-ID   PIC X(10).
      *                       CMPNY_USER_NM
           10 D003-CMPNY-USER-NM   PIC X(200).
      *                       CMPNY_USER_EMAIL_ID
           10 D003-CMPNY-USER-EMAIL-ID
              PIC X(100).
      *                       ROW_UPDT_TS
           10 D003-ROW-UPDT-TS     PIC X(26).
      *                       UMG_USER_ID
           10 D003-UMG-USER-ID     PIC X(50).
      *                       CMPNY_USER_PHONE_NB
           10 D003-CMPNY-USER-PHONE-NB
              PIC X(20).
      ******************************************************************
      * INDICATOR VARIABLE STRUCTURE                                   *
      ******************************************************************
       01  IVDPM03-CMPNY-USER.
           10 INDSTRUC           PIC S9(4) USAGE COMP OCCURS 7 TIMES.
      ******************************************************************
      * THE NUMBER OF COLUMNS DESCRIBED BY THIS DECLARATION IS 7       *
      ******************************************************************
