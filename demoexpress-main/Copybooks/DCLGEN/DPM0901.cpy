      ******************************************************************
      * DCLGEN TABLE(NSCC.VDPM09_DOC_USER)                             *
      *        LIBRARY(DB2T.DCLGEN.TEST.NSCC.COBCOPY(DPM0901))         *
      *        ACTION(REPLACE)                                         *
      *        LANGUAGE(COBOL)                                         *
      *        NAMES(D009-)                                            *
      *        QUOTE                                                   *
      *        COLSUFFIX(YES)                                          *
      *        INDVAR(YES)                                             *
      * ... IS THE DCLGEN COMMAND THAT MADE THE FOLLOWING STATEMENTS   *
      ******************************************************************
           EXEC SQL DECLARE NSCC.VDPM09_DOC_USER TABLE
           ( MCA_VALUE_ID                   DECIMAL(18, 0) NOT NULL,
             CMPNY_ID                       CHAR(8) NOT NULL,
             MCA_DOC_VIEW_IN                CHAR(1) NOT NULL,
             ROW_UPDT_TS                    TIMESTAMP NOT NULL,
             ROW_UPDT_USER_ID               CHAR(10) NOT NULL
           ) END-EXEC.
      ******************************************************************
      * COBOL DECLARATION FOR TABLE NSCC.VDPM09_DOC_USER               *
      ******************************************************************
       01  DCLVDPM09-DOC-USER.
      *                       MCA_VALUE_ID
           10 D009-MCA-VALUE-ID    PIC S9(18)V USAGE COMP-3.
      *                       CMPNY_ID
           10 D009-CMPNY-ID        PIC X(8).
      *                       MCA_DOC_VIEW_IN
           10 D009-MCA-DOC-VIEW-IN
              PIC X(1).
      *                       ROW_UPDT_TS
           10 D009-ROW-UPDT-TS     PIC X(26).
      *                       ROW_UPDT_USER_ID
           10 D009-ROW-UPDT-USER-ID
              PIC X(10).
      ******************************************************************
      * INDICATOR VARIABLE STRUCTURE                                   *
      ******************************************************************
       01  IVDPM09-DOC-USER.
           10 INDSTRUC           PIC S9(4) USAGE COMP OCCURS 5 TIMES.
      ******************************************************************
      * THE NUMBER OF COLUMNS DESCRIBED BY THIS DECLARATION IS 5       *
      ******************************************************************
