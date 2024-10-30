      ******************************************************************
      * DCLGEN TABLE(NSCC.VDTM54_DEBUG_CNTRL)                          *
      *        LIBRARY(DB2T.DCLGEN.TEST.NSCC.COBCOPY(DTM5401))         *
      *        LANGUAGE(COBOL)                                         *
      *        NAMES(D054-)                                            *
      *        QUOTE                                                   *
      *        COLSUFFIX(YES)                                          *
      *        INDVAR(YES)                                             *
      * ... IS THE DCLGEN COMMAND THAT MADE THE FOLLOWING STATEMENTS   *
      ******************************************************************
           EXEC SQL DECLARE NSCC.VDTM54_DEBUG_CNTRL TABLE
           ( PRGM_ID                        CHAR(8) NOT NULL,
             ACTVT_DSPLY_IN                 CHAR(1) NOT NULL,
             ACTVT_OPTMZ_IN                 CHAR(1) NOT NULL,
             CURSR_NM                       CHAR(18) NOT NULL,
             FNCTN_1_NM                     CHAR(8) NOT NULL,
             FNCTN_2_NM                     CHAR(8) NOT NULL,
             FNCTN_3_NM                     CHAR(8) NOT NULL,
             USER_ROW_UPDT_ID               CHAR(8) NOT NULL,
             ROW_UPDT_TS                    TIMESTAMP NOT NULL
           ) END-EXEC.
      ******************************************************************
      * COBOL DECLARATION FOR TABLE NSCC.VDTM54_DEBUG_CNTRL            *
      ******************************************************************
       01  DCLVDTM54-DEBUG-CNTRL.
      *                       PRGM_ID
           10 D054-PRGM-ID         PIC X(8).
      *                       ACTVT_DSPLY_IN
           10 D054-ACTVT-DSPLY-IN  PIC X(1).
      *                       ACTVT_OPTMZ_IN
           10 D054-ACTVT-OPTMZ-IN  PIC X(1).
      *                       CURSR_NM
           10 D054-CURSR-NM        PIC X(18).
      *                       FNCTN_1_NM
           10 D054-FNCTN-1-NM      PIC X(8).
      *                       FNCTN_2_NM
           10 D054-FNCTN-2-NM      PIC X(8).
      *                       FNCTN_3_NM
           10 D054-FNCTN-3-NM      PIC X(8).
      *                       USER_ROW_UPDT_ID
           10 D054-USER-ROW-UPDT-ID
              PIC X(8).
      *                       ROW_UPDT_TS
           10 D054-ROW-UPDT-TS     PIC X(26).
      ******************************************************************
      * INDICATOR VARIABLE STRUCTURE                                   *
      ******************************************************************
       01  IVDTM54-DEBUG-CNTRL.
           10 INDSTRUC           PIC S9(4) USAGE COMP OCCURS 9 TIMES.
      ******************************************************************
      * THE NUMBER OF COLUMNS DESCRIBED BY THIS DECLARATION IS 9       *
      ******************************************************************
