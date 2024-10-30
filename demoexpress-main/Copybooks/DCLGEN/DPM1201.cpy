      ******************************************************************
      * DCLGEN TABLE(NSCC.VDPM12_MCA_DOC)                              *
      *        LIBRARY(DB2T.DCLGEN.TEST.NSCC.COBCOPY(DPM1201))         *
      *        ACTION(REPLACE)                                         *
      *        LANGUAGE(COBOL)                                         *
      *        NAMES(D012-)                                            *
      *        QUOTE                                                   *
      *        COLSUFFIX(YES)                                          *
      *        INDVAR(YES)                                             *
      * ... IS THE DCLGEN COMMAND THAT MADE THE FOLLOWING STATEMENTS   *
      ******************************************************************
           EXEC SQL DECLARE NSCC.VDPM12_MCA_DOC TABLE
           ( MCA_VALUE_ID                   DECIMAL(18, 0) NOT NULL,
             MCA_TMPLT_ID                   INTEGER NOT NULL,
             MCA_DOC_DS                     CHAR(216) NOT NULL,
             MCA_DOC_TYPE_CD                CHAR(1) NOT NULL,
             CMPNY_ID                       CHAR(8) NOT NULL,
             DOC_DEL_CD                     CHAR(1) NOT NULL,
             ROW_UPDT_TS                    TIMESTAMP NOT NULL,
             ROW_UPDT_USER_ID               CHAR(10) NOT NULL,
             MCA_ROW_ID                      ROWID NOT NULL,
             MCA_DOC_OBJ_TX                 BLOB(2097152) NOT NULL
           ) END-EXEC.
      ******************************************************************
      * COBOL DECLARATION FOR TABLE NSCC.VDPM12_MCA_DOC                *
      ******************************************************************
       01  DCLVDPM12-MCA-DOC.
      *                       MCA_VALUE_ID
           10 D012-MCA-VALUE-ID    PIC S9(18)V USAGE COMP-3.
      *                       MCA_TMPLT_ID
           10 D012-MCA-TMPLT-ID    PIC S9(9) USAGE COMP.
      *                       MCA_DOC_DS
           10 D012-MCA-DOC-DS      PIC X(216).
      *                       MCA_DOC_TYPE_CD
           10 D012-MCA-DOC-TYPE-CD
              PIC X(1).
      *                       CMPNY_ID
           10 D012-CMPNY-ID        PIC X(8).
      *                       DOC_DEL_CD
           10 D012-DOC-DEL-CD      PIC X(1).
      *                       ROW_UPDT_TS
           10 D012-ROW-UPDT-TS     PIC X(26).
      *                       ROW_UPDT_USER_ID
           10 D012-ROW-UPDT-USER-ID
              PIC X(10).
      *                       MCA_ROW_ID
           10 D012-MCA-ROW-ID      USAGE SQL TYPE IS ROWID.
      *                       MCA_DOC_OBJ_TX
           10 D012-MCA-DOC-OBJ-TX  USAGE SQL TYPE IS BLOB-LOCATOR.
      ******************************************************************
      * INDICATOR VARIABLE STRUCTURE                                   *
      ******************************************************************
       01  IVDPM12-MCA-DOC.
           10 INDSTRUC           PIC S9(4) USAGE COMP OCCURS 10 TIMES.
      ******************************************************************
      * THE NUMBER OF COLUMNS DESCRIBED BY THIS DECLARATION IS 10      *
      ******************************************************************
