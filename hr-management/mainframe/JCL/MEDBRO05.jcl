//AEDBIN01 JOB SISTEMAS,'OPERATOR',CLASS=A,MSGLEVEL=(1,1),
//        MSGCLASS=X,NOTIFY=&SYSUID,USER=SQLOPE
//*
//*
//*
//***************************************************
//*  CREATED BY :  SYS0GBT
//*  TIMESTAMP  :  01/21/2010.16.28.02
//*  ENVIRONMENT:  ISPF 5.7MVS     BATCH
//*  RELEASE    :  V09.01.01 12/21/2007
//*  DB2 VERSION:  815
//***************************************************
//*--------------------------------------------------------------------
//*        BMC CHANGE MANAGER EXECUTION
//*--------------------------------------------------------------------
//STEP1 EXEC PGM=AEXEMAIN,REGION=0M,PARM='ACMDOPD1',
//             COND=(4,LT)
//STEPLIB DD DISP=SHR,DSN=ALEX.LBDB2.SDSNEXIT
//        DD DISP=SHR,DSN=PREX.BMCDB2.ADM.BMCLINK
//        DD DISP=SHR,DSN=PRD0.DBS.SDSNLOAD.ALEX
//ABNLIGNR DD DUMMY
//SYSUDUMP DD SYSOUT=*
//SYSTERM  DD SYSOUT=*
//UTPRINT  DD SYSOUT=*
//*--------------------------------------------------------------------
//*  STATISTICS WORK DD STATEMENTS
//*--------------------------------------------------------------------
//STPRIN01 DD SYSOUT=*
//RNPRIN01  DD SYSOUT=*
//SYSOUT   DD SYSOUT=*
//AEXIN    DD *
  ACM
  SSID ALEX WORKID E503460.MIGTABEB
  DYNWORKUNIT SYSDA
  CATDOPT ACTDOPD1
  STOPWAIT 3
  LOCK EXCLUSIVE
//SYSIN    DD SPACE=(CYL,(15,15)),UNIT=SYSDA,DISP=(NEW,DELETE),
//            DSORG=PS,LRECL=80,BLKSIZE=3200,RECFM=FB
//SYSTSIN  DD SPACE=(TRK,(1,1)),UNIT=SYSDA,DISP=(NEW,DELETE),
//            DSORG=PS,LRECL=80,BLKSIZE=3200,RECFM=FB
//SYSPRINT DD SPACE=(CYL,(15,15)),UNIT=SYSDA,
//            DISP=(NEW,DELETE)
//SYSTSPRT DD SPACE=(CYL,(5,5)),UNIT=SYSDA,DISP=(NEW,DELETE),
//            DSORG=PS,LRECL=137,BLKSIZE=3155,RECFM=VBA
//ALUIN    DD SPACE=(TRK,(1,1)),UNIT=SYSDA,DISP=(NEW,DELETE),
//            DSORG=PS,LRECL=80,BLKSIZE=3200,RECFM=FB
//ALUPRINT DD SPACE=(CYL,(5,5)),UNIT=SYSDA,DISP=(NEW,DELETE)
//AJXPRINT DD SPACE=(CYL,(5,5)),UNIT=SYSDA,DISP=(NEW,DELETE)
//*--------------------------------------------------------------------
//*  STATISTICS WORK DD STATEMENTS
//*--------------------------------------------------------------------
//STPRIN01 DD SYSOUT=*
//ACT$MSGS DD DISP=SHR,DSN=PRSI.BMCDB2.D0801.BMCMLIB
//ISPMLIB  DD DISP=SHR,DSN=PRSI.BMCDB2.D0801.BMCMLIB
//         DD DISP=SHR,DSN=ISP.SISPMENU
//ISPSLIB  DD DISP=SHR,DSN=PRSI.BMCDB2.D0801.BMCSLIB
//ISPTLIB  DD DISP=SHR,DSN=PRSI.BMCDB2.D0801.BMCTLIB
//ISPPLIB  DD DISP=SHR,DSN=PRSI.BMCDB2.D0801.BMCPLIB
//AEXPRINT DD SYSOUT=*
//WORKLIST DD DISP=SHR,
//         DSN=SYS0GBT.JOBS.CNTLALEX(WKLEBEX)
//*--------------------------------------------------------------------
//* END OF JOBSTEP
//*--------------------------------------------------------------------
//*--------------------------------------------------------------------
//* END OF JOB
//*--------------------------------------------------------------------
