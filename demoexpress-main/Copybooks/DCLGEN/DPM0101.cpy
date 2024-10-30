      ******************************************************************
      * DCLGEN TABLE(NSCC.VDPM01_MCA_CMPNY)                            *
      *        LIBRARY(DB2T.DCLGEN.TEST.NSCC.COBCOPY(DPM0101))         *
      *        ACTION(REPLACE)                                         *
      *        LANGUAGE(COBOL)                                         *
      *        NAMES(D001-)                                            *
      *        QUOTE                                                   *
      *        COLSUFFIX(YES)                                          *
      *        INDVAR(YES)                                             *
      * ... IS THE DCLGEN COMMAND THAT MADE THE FOLLOWING STATEMENTS   *
      ******************************************************************
           EXEC SQL DECLARE NSCC.VDPM01_MCA_CMPNY TABLE
           ( CMPNY_ID                       CHAR(8) NOT NULL,
             CMPNY_TYPE_CD                  CHAR(1) NOT NULL,
             CMPNY_GROUP_CD                 CHAR(1) NOT NULL,
             CMPNY_NM                       CHAR(255) NOT NULL,
             CMPNY_STAT_IN                  CHAR(1) NOT NULL,
             CMPNY_PRMRY_CNTCT_NM           CHAR(200) NOT NULL,
             CMPNY_SCNDY_CNTCT_NM           CHAR(200) NOT NULL,
             CMPNY_PRMRY_PHONE_NB           CHAR(20) NOT NULL,
             CMPNY_SCNDY_PHONE_NB           CHAR(20) NOT NULL,
             CMPNY_PRMRY_EMAIL_ID           CHAR(100) NOT NULL,
             CMPNY_SCNDY_EMAIL_ID           CHAR(100) NOT NULL,
             EFFV_START_DT                  DATE NOT NULL,
             EFFV_END_DT                    DATE NOT NULL,
             ROW_UPDT_TS                    TIMESTAMP NOT NULL,
             ROW_UPDT_USER_ID               CHAR(10) NOT NULL
           ) END-EXEC.
      ******************************************************************
      * COBOL DECLARATION FOR TABLE NSCC.VDPM01_MCA_CMPNY              *
      ******************************************************************
       01  DCLVDPM01-MCA-CMPNY.
      *                       CMPNY_ID
           10 D001-CMPNY-ID        PIC X(8).
      *                       CMPNY_TYPE_CD
           10 D001-CMPNY-TYPE-CD   PIC X(1).
      *                       CMPNY_GROUP_CD
           10 D001-CMPNY-GROUP-CD  PIC X(1).
      *                       CMPNY_NM
           10 D001-CMPNY-NM        PIC X(255).
      *                       CMPNY_STAT_IN
           10 D001-CMPNY-STAT-IN   PIC X(1).
      *                       CMPNY_PRMRY_CNTCT_NM
           10 D001-CMPNY-PRMRY-CNTCT-NM
              PIC X(200).
      *                       CMPNY_SCNDY_CNTCT_NM
           10 D001-CMPNY-SCNDY-CNTCT-NM
              PIC X(200).
      *                       CMPNY_PRMRY_PHONE_NB
           10 D001-CMPNY-PRMRY-PHONE-NB
              PIC X(20).
      *                       CMPNY_SCNDY_PHONE_NB
           10 D001-CMPNY-SCNDY-PHONE-NB
              PIC X(20).
      *                       CMPNY_PRMRY_EMAIL_ID
           10 D001-CMPNY-PRMRY-EMAIL-ID
              PIC X(100).
      *                       CMPNY_SCNDY_EMAIL_ID
           10 D001-CMPNY-SCNDY-EMAIL-ID
              PIC X(100).
      *                       EFFV_START_DT
           10 D001-EFFV-START-DT   PIC X(10).
      *                       EFFV_END_DT
           10 D001-EFFV-END-DT     PIC X(10).
      *                       ROW_UPDT_TS
           10 D001-ROW-UPDT-TS     PIC X(26).
      *                       ROW_UPDT_USER_ID
           10 D001-ROW-UPDT-USER-ID
              PIC X(10).
      ******************************************************************
      * INDICATOR VARIABLE STRUCTURE                                   *
      ******************************************************************
       01  IVDPM01-MCA-CMPNY.
           10 INDSTRUC           PIC S9(4) USAGE COMP OCCURS 15 TIMES.
      ******************************************************************
      * THE NUMBER OF COLUMNS DESCRIBED BY THIS DECLARATION IS 15      *
      ******************************************************************
