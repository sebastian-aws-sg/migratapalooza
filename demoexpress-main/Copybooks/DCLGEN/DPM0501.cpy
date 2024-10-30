      ******************************************************************
      * DCLGEN TABLE(NSCC.VDPM05_ALERT_INFO)                           *
      *        LIBRARY(DB2T.DCLGEN.TEST.NSCC.COBCOPY(DPM0501))         *
      *        LANGUAGE(COBOL)                                         *
      *        NAMES(D005-)                                            *
      *        QUOTE                                                   *
      *        COLSUFFIX(YES)                                          *
      *        INDVAR(YES)                                             *
      * ... IS THE DCLGEN COMMAND THAT MADE THE FOLLOWING STATEMENTS   *
      ******************************************************************
           EXEC SQL DECLARE NSCC.VDPM05_ALERT_INFO TABLE
           ( MCA_ALERT_ID                   DECIMAL(18, 0) NOT NULL,
             ALERT_INFO_SUB_DS              CHAR(150) NOT NULL,
             ROW_UPDT_TS                    TIMESTAMP NOT NULL,
             ROW_UPDT_USER_ID               CHAR(10) NOT NULL
           ) END-EXEC.
      ******************************************************************
      * COBOL DECLARATION FOR TABLE NSCC.VDPM05_ALERT_INFO             *
      ******************************************************************
       01  DCLVDPM05-ALERT-INFO.
      *                       MCA_ALERT_ID
           10 D005-MCA-ALERT-ID    PIC S9(18)V USAGE COMP-3.
      *                       ALERT_INFO_SUB_DS
           10 D005-ALERT-INFO-SUB-DS
              PIC X(150).
      *                       ROW_UPDT_TS
           10 D005-ROW-UPDT-TS     PIC X(26).
      *                       ROW_UPDT_USER_ID
           10 D005-ROW-UPDT-USER-ID
              PIC X(10).
      ******************************************************************
      * INDICATOR VARIABLE STRUCTURE                                   *
      ******************************************************************
       01  IVDPM05-ALERT-INFO.
           10 INDSTRUC           PIC S9(4) USAGE COMP OCCURS 4 TIMES.
      ******************************************************************
      * THE NUMBER OF COLUMNS DESCRIBED BY THIS DECLARATION IS 4       *
      ******************************************************************
