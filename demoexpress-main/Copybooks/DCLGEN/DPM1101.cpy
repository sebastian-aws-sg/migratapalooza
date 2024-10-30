      ******************************************************************
      * DCLGEN TABLE(NSCC.VDPM11_MCA_CMNT)                             *
      *        LIBRARY(DB2T.DCLGEN.TEST.NSCC.COBCOPY(DPM1101))         *
      *        ACTION(REPLACE)                                         *
      *        LANGUAGE(COBOL)                                         *
      *        NAMES(D011-)                                            *
      *        QUOTE                                                   *
      *        COLSUFFIX(YES)                                          *
      *        INDVAR(YES)                                             *
      * ... IS THE DCLGEN COMMAND THAT MADE THE FOLLOWING STATEMENTS   *
      ******************************************************************
           EXEC SQL DECLARE NSCC.VDPM11_MCA_CMNT TABLE
           ( MCA_VALUE_ID                   DECIMAL(18, 0) NOT NULL,
             DELR_CMPNY_ID                  CHAR(8) NOT NULL,
             CLNT_CMPNY_ID                  CHAR(8) NOT NULL,
             ROW_UPDT_TS                    TIMESTAMP NOT NULL,
             ROW_UPDT_USER_ID               CHAR(10) NOT NULL,
             CMNT_TX                        VARCHAR(32000) NOT NULL
           ) END-EXEC.
      ******************************************************************
      * COBOL DECLARATION FOR TABLE NSCC.VDPM11_MCA_CMNT               *
      ******************************************************************
       01  DCLVDPM11-MCA-CMNT.
      *                       MCA_VALUE_ID
           10 D011-MCA-VALUE-ID    PIC S9(18)V USAGE COMP-3.
      *                       DELR_CMPNY_ID
           10 D011-DELR-CMPNY-ID   PIC X(8).
      *                       CLNT_CMPNY_ID
           10 D011-CLNT-CMPNY-ID   PIC X(8).
      *                       ROW_UPDT_TS
           10 D011-ROW-UPDT-TS     PIC X(26).
      *                       ROW_UPDT_USER_ID
           10 D011-ROW-UPDT-USER-ID
              PIC X(10).
           10 D011-CMNT-TX.
      *                       CMNT_TX LENGTH
              49 D011-CMNT-TX-LEN
                 PIC S9(4) USAGE COMP.
      *                       CMNT_TX
              49 D011-CMNT-TX-TEXT
                 PIC X(32000).
      ******************************************************************
      * INDICATOR VARIABLE STRUCTURE                                   *
      ******************************************************************
       01  IVDPM11-MCA-CMNT.
           10 INDSTRUC           PIC S9(4) USAGE COMP OCCURS 6 TIMES.
      ******************************************************************
      * THE NUMBER OF COLUMNS DESCRIBED BY THIS DECLARATION IS 6       *
      ******************************************************************
