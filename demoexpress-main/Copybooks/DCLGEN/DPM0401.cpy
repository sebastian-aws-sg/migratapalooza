      ******************************************************************
      * DCLGEN TABLE(NSCC.VDPM04_ATTRB_DTL)                            *
      *        LIBRARY(DB2T.DCLGEN.TEST.NSCC.COBCOPY(DPM0401))         *
      *        LANGUAGE(COBOL)                                         *
      *        NAMES(D004-)                                            *
      *        QUOTE                                                   *
      *        COLSUFFIX(YES)                                          *
      *        INDVAR(YES)                                             *
      * ... IS THE DCLGEN COMMAND THAT MADE THE FOLLOWING STATEMENTS   *
      ******************************************************************
           EXEC SQL DECLARE NSCC.VDPM04_ATTRB_DTL TABLE
           ( ATTRB_ID                       CHAR(8) NOT NULL,
             ATTRB_TYPE_ID                  CHAR(8) NOT NULL,
             ATTRB_VALUE_DS                 CHAR(150) NOT NULL,
             ROW_UPDT_TS                    TIMESTAMP NOT NULL,
             ROW_UPDT_USER_ID               CHAR(10) NOT NULL
           ) END-EXEC.
      ******************************************************************
      * COBOL DECLARATION FOR TABLE NSCC.VDPM04_ATTRB_DTL              *
      ******************************************************************
       01  DCLVDPM04-ATTRB-DTL.
      *                       ATTRB_ID
           10 D004-ATTRB-ID        PIC X(8).
      *                       ATTRB_TYPE_ID
           10 D004-ATTRB-TYPE-ID   PIC X(8).
      *                       ATTRB_VALUE_DS
           10 D004-ATTRB-VALUE-DS  PIC X(150).
      *                       ROW_UPDT_TS
           10 D004-ROW-UPDT-TS     PIC X(26).
      *                       ROW_UPDT_USER_ID
           10 D004-ROW-UPDT-USER-ID
              PIC X(10).
      ******************************************************************
      * INDICATOR VARIABLE STRUCTURE                                   *
      ******************************************************************
       01  IVDPM04-ATTRB-DTL.
           10 INDSTRUC           PIC S9(4) USAGE COMP OCCURS 5 TIMES.
      ******************************************************************
      * THE NUMBER OF COLUMNS DESCRIBED BY THIS DECLARATION IS 5       *
      ******************************************************************
