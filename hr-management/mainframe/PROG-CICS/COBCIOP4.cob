      *-----------------------------------------------------------------
      *    COBCIOP4 - SAMPLE CICS PROGRAM TO PRINT EMPLOYEE PROJECT
      *               ACTIVITY DETAILS. THIS PROGRAM DIRECTLY CALLS
      *               DB2 DATABASE FOR GETTING EMPLOYEE ACTIVITY DETAILS   
      *               BELONGING TO A PROJECT. USER CAN ENTER PROJECT NUMBER                
      *               FROM CICS SCREEN AND GETS ACTIVITY DETAILS ON THE 
      *               SCREEN.
      *-----------------------------------------------------------------
      *                                                                 
      *--------------------PART OF MYTELCO HR APPLICATION-----------
      *                                                                 
      *-----------------------------------------------------------------
       IDENTIFICATION DIVISION.                                         
       PROGRAM-ID.   COBCIOP4.                                          
       AUTHOR.       CAST SOFTWARE                                      
       DATE-WRITTEN. AUGUST 2005.                                       
                                                                        
       EJECT                                                            
       ENVIRONMENT DIVISION.                                            
       DATA DIVISION.                                                   
                                                                        
       WORKING-STORAGE SECTION.                                         
                                                                        
       01  PROJECT-MASTER-RECORD.                                      
      *        ** key field                                             
           03  EMP-NO                      PIC X(6).                    
      *    03  PROJ-NO                     PIC X(6) VALUE SPACES.                    
      *    03  ACT-NO                      PIC S9(2).                    
      *    03  EMPTIME                     PIC S9(5).                   
      *        ** format (yymmdd)                                       
           03  ACT-DATE-STARTED            PIC 9(6) VALUE 0.                    
      *        ** format (yymmdd)                                        
           03  ACT-DATE-ENDED              PIC 9(6) VALUE 0.                    
                                                                        
           03  PROJ-NAME                   PIC X(24) VALUE SPACES.
           03  DEPTNO                      PIC X(3) VALUE SPACES.
           03  PROJ-EMP                    PIC X(6) VALUE SPACES.
           03  PROJ-STARTDATE              PIC 9(6) VALUE 0.
           03  PROJ-ENDDATE                PIC 9(6) VALUE 0.

       01  DEPT-MASTER-RECORD.                                          
      *        ** key field                                             
           03  DEPT-CODE                   PIC X(4).                    
           03  DEPT-DESCRIPTION            PIC X(30).                   
      *        ** format (yyddd) packed                                 
           03  DEPT-DATE-MAINTAINED        PIC 9(5) COMP-3.             
           03  FILLER                      PIC X(43).                   
                                                                        
       01  SWITCHES.                                                    
           03  ERROR-SWITCH                PIC X VALUE SPACE.           
               88  ERRORS                        VALUE "Y".             
                                                                        
       01  SWITCH-OFF                      PIC X VALUE "N".             
      *    ** report headings & detail line                             
                                                                        
                                                                        
      *    ** message table                                             
                                                                        
       01  MESSAGE-TABLE.                                               
           03  FILLER  PIC X(30) VALUE "I-RECORD ADDED              ".  
           03  FILLER  PIC X(30) VALUE "I-RECORD CHANGED            ".  
           03  FILLER  PIC X(30) VALUE "P-DEPARTMENT CODE INVALID   ".  
           03  FILLER  PIC X(30) VALUE "PROJ-CODE NOT NUMERIC       ".  
           03  FILLER  PIC X(30) VALUE "E-INVALID DATE              ".  
           03  FILLER  PIC X(30) VALUE "I-ENTER EMPLOYEE NUMBER     ".  
           03  FILLER  PIC X(30) VALUE "I-ENTER EMPLOYEE DETAILS    ".  
           03  FILLER  PIC X(30) VALUE "I-ENTER CHANGE DETAILS      ".  
           03  FILLER  PIC X(30) VALUE "I-SCR VALID PF10 TO UPDATE  ".  
           03  FILLER  PIC X(30) VALUE "E-NAME MISSING              ".  
           03  FILLER  PIC X(30) VALUE "E-ADDRESS LINE 1 MISSING    ".  
           03  FILLER  PIC X(30) VALUE "E-JOINED > TERMINATED DATE  ".  
           03  FILLER  PIC X(30) VALUE "E-DATE MISSING              ".  
           03  FILLER  PIC X(30) VALUE "E-EMPLOYEE NO NOT NUMERIC   ".  
                                                                        
       01  MSG-TABLE-RED REDEFINES MESSAGE-TABLE.                       
           03  MSG OCCURS 14 TIMES.                                     
               05  FILLER                  PIC X(30).                   
       01  ERROR-MESSAGE.                                               
               02  ERROR-LEN   PIC S9(4)  COMP VALUE +960.              
               02  ERROR-TEXT  PIC X(80) OCCURS 12 TIMES                
                                    INDEXED BY ERROR-INDEX.       
       77  ERROR-TEXT-LEN      PIC S9(9)  COMP VALUE +80.               
       01 ERROR-INDEX PIC 99.

       01  EIBDATE     PIC S9(07) COMP-3.
                                                                        
      *    ** work variables                                            
                                                                        
       01  WORK-VARS.                                                   
           03  WS-PROJ-NO                  PIC X(6).    
           03  WS-EMPNO                    PIC X(6).    
           03  WORK-TODAYS-MMDDYY          PIC 9(8).                    
           03  WORK-MSG-CODE               PIC 99.                      
      *    TARGET OF EIBDATE ASSIGNMENT MUST BE PIC 9(7) COMP-3.        
           03  WORK-EIB-DATE               PIC 9(7) COMP-3.             
           03  WORK-EIB-DATE-CHAR.                                      
               05  FILLER                  PIC X(1).                    
               05  WORK-EIB-CENTURY        PIC X(1).                    
               05  WORK-EIB-YYDDD          PIC X(5).                    
           03  WORK-JOINED-YYDDD           PIC 9(5) VALUE 0.                    
           03  WORK-JOINED-MMDDYY.                                      
               05  WORK-JOINED-MMDD        PIC X(6) VALUE SPACES.                    
               05  WORK-JOINED-YY          PIC 99 VALUE 0.                      
           03  WORK-SEC-EXP                PIC 9(5).                    
           03  WORK-TERMINATED-YYDDD       PIC 9(5).                    
                                                                        
       01  PROJ-LENGTH                     PIC S9(4) COMP VALUE +200.   
       01  DEP-LENGTH                      PIC S9(4) COMP VALUE +80.    
       01  RESPONSE                        PIC S9(8) COMP VALUE +0.     
       01  LAST-EMP-NO                     PIC X(6)  VALUE SPACES.      
       01  DFHRESP OCCURS 10               PIC S9(8) COMP VALUE +0.
       01  NOTFND                          PIC 9 VALUE 1.
                                                                        
       01  COBDATE-PARAMETERS.                                          
           03  COBDATE-DATE                PIC X(8).                    
           03  COBDATE-DATE-RED1 REDEFINES COBDATE-DATE.                
               05  COBDATE-DATE-YYDDD      PIC 9(5).                    
               05  FILLER                  PIC X(3).                    
           03  COBDATE-DATE-RED2 REDEFINES COBDATE-DATE.                
               05  COBDATE-DATE-YYMMDD     PIC 9(6).                    
               05  FILLER                  PIC X(2).                    
           03  COBDATE-INPUT-FORMAT        PIC X(8).                    
           03  COBDATE-OUTPUT-FORMAT       PIC X(8).                    
           03  COBDATE-MESSAGE             PIC X(30).                   
                                                                        
       01  PSQLCODE                        PIC S9(9) COMP.              
       01  PSQLSTATE                       PIC X(5).                    
       01  PSQLERRMC.                                                   
           49  PSQLERRMC-LEN               PIC S9(4) COMP.              
           49  PSQLERRMC-TEXT              PIC X(250).                  

           COPY DFHBMSCA.                                               
                                                                        
           COPY DFHAID.  
           
           COPY PRJACTM.

      ******************************************************************
      * SQLCA AND DCLGENS FOR TABLES                                    
      ******************************************************************
      *    EXEC SQL
      *       INCLUDE PACTIVITY
      *    END-EXEC.

      *    EXEC SQL
      *       INCLUDE EMPRJACT
      *    END-EXEC.
      *    
      *    EXEC SQL 
      *        INCLUDE SQLCA  
      *    END-EXEC.                       
                                                                        
                                                                       
           EJECT                                                        
      *-----------------------------------------------------------------                                                                  
      * PROCEDURE DIVISION.
      *-----------------------------------------------------------------                                                                  
       PROCEDURE DIVISION.                                              
                                                                        
      *    handle conditions                                            
                                                                        
           EXEC CICS 
               IGNORE CONDITION 
               LENGERR 
           END-EXEC.                 

           EXEC CICS 
               IGNORE CONDITION 
               MAPFAIL 
           END-EXEC.                 

           EXEC CICS 
               HANDLE AID 
               PF3(910-EXIT) 
           END-EXEC.                 
           
           EXEC CICS 
               HANDLE CONDITION 
               ERROR(900-ERRORS) 
           END-EXEC.       
                                                                        
      *    **  retrieve todays date                                     
                                                                        
           MOVE 0              T0  EIBDATE.
           MOVE 0              TO  WORK-EIB-DATE.                       
           MOVE WORK-EIB-DATE  TO  WORK-EIB-DATE-CHAR.                  
           MOVE WORK-EIB-YYDDD TO  COBDATE-DATE.                        
           MOVE "YYDDD"        TO  COBDATE-INPUT-FORMAT.                
           MOVE "MM/DD/YY"     TO  COBDATE-OUTPUT-FORMAT.               
           
           CALL "COBDTE3"      USING 
                                   COBDATE-DATE                            
                                   COBDATE-INPUT-FORMAT                    
                                   COBDATE-OUTPUT-FORMAT                   
                                   COBDATE-MESSAGE.                        
           
           MOVE COBDATE-DATE   TO  WORK-TODAYS-MMDDYY.                  

           MOVE SPACES         TO  PRJACTMO.                 
           MOVE 6              TO  WORK-MSG-CODE.                       
           MOVE -1             TO  PRJACTMPRJI.                           
           
           GO TO 300-SEND-ACTIVITYMAP.                                          
                                                                        
      *-----------------------------------------------------------------                                                                  
      * RECEIVE MAP RECEIVES THE CICS MAP.                                                                                                   
      *-----------------------------------------------------------------                                                                  
       200-RECEIVE-ACTIVITYMAP.                                                 

           EXEC CICS 
               RECEIVE MAP("MAP") MAPSET("PACTMAP")
               INTO(PRJACTMI) 
           END-EXEC.        
                                                                        
      *    **  validate employee number                                 
                                                                        
           IF  PRJACTMPRJI = ZERO THEN                                    
               MOVE -1                TO  PRJACTMPRJI                        
               MOVE 6                 TO  WORK-MSG-CODE                    

               GO TO 300-SEND-ACTIVITYMAP                                       
           ELSE                                                         
               IF PRJACTMEMPI IS NOT NUMERIC THEN                         
                  MOVE -1             TO  PRJACTMPRJI                     
                  MOVE 14             TO  WORK-MSG-CODE                 

                  GO TO 300-SEND-ACTIVITYMAP                                    
               END-IF                                                   
           END-IF.                                                      
                                                                        
      *    **  employee number changed                                  
                                                                        
           IF  PRJACTMEMPI  NOT = LAST-EMP-NO THEN                         
               MOVE PRJACTMEMPI TO LAST-EMP-NO                            
               PERFORM 400-READ-EMP-PROJ-ACTIVITY-DETAILS
               PERFORM 401-READ-EMP-PROJ-ACTIVITY-DETAILS
               MOVE -1          TO PRJACTMDEPL                              
            ELSE                                                        
               PERFORM 500-VALIDATE-SCREEN                              
               IF  NOT ERRORS THEN                                      
                   IF  EIBAID = DFHPF10 THEN                            
                       PERFORM 600-UPDATE-EMP-PROJ-ACTIVITY-DETAILS                    
                   ELSE                                                 
                       MOVE 9  TO WORK-MSG-CODE                         
                   END-IF                                               
               END-IF                                                   
           END-IF.                                                      
                                                                        
           MOVE DFHBMFSE        TO  PRJACTMEMPA                               
                                    PRJACTMDEPA                               
                                    PRJACTMNAMEA                              
                                    PRJACTMADDR1A                             
                                    PRJACTMADDR2A                             
                                    PRJACTMADDR3A                             
                                    PRJACTMZIPA                               
                                    PRJACTMJDATEA                             
                                    PRJACTMBDATEA                             
                                    PRJACTMTDATEA                             
                                    PRJACTMSDATEA.                            
                                                                        
      *-----------------------------------------------------------------                                                                  
      * THIS PARA SEND THE MAP TO CICS SCREEN USING THE OUTPUT COMM AREA                                                                 
      *-----------------------------------------------------------------                                                                  
       300-SEND-ACTIVITYMAP.                                                   
           MOVE MSG(WORK-MSG-CODE) TO PRJACTMMSGO.                        

           EXEC CICS 
               SEND MAP("MAP") MAPSET("PRJACTMP")
               FROM(PRJACTMO)                     
               CURSOR FREEKB 
           END-EXEC.                        

           GO TO 200-RECEIVE-ACTIVITYMAP.                                       
                                                                        
                       
      *-----------------------------------------------------------------                                                                  
      * THIS PARA MAKES A CALL TO COBOL PROGRAM TO GET EMPLOYEE RECORDS.                                                                    
      *-----------------------------------------------------------------                                                                  
       400-READ-EMP-PROJ-ACTIVITY-DETAILS.                                             

           MOVE PRJACTMPRJI TO  WS-PROJ-NO.    
           MOVE PRJACTMEMPI TO  WS-EMPNO.
      *-----------------------------------------------------------------
      *    SQL QUERY FOR GETTING EMPLOYEE PROJECT ACTIVITY DETAILS.
      *    DETAILS ARE OBTAINED ON THE BASIS OF EMPLOYEE CODE AND 
      *    PROJECT CODE ENTERED BY THE USER.
      *-----------------------------------------------------------------
           MOVE 1 TO CMD-CODE.
           MOVE PHOTO-MASTER-RECORD TO DATA-IN.
           CALL "APITPP4" USING CMD-CODE RESP-CODE DATA-IN DATA-OUT.
           IF RESP-CODE <> 0 
               MOVE DATA-OUT TO PHOTO-MASTER-RECORD
           ELSE
               PERFORM 950-DBERROR THRU 950-EXIT
           END-IF
      *         EXEC SQL                                                
      *           SELECT                                                
      *               ACT_NO                                        
      *             , EMPTIME                                         
      *             , ESTARTDATE                                        
      *             , EENDDATE                                        
      *           INTO          
      *               :ACT-NO                                        
      *             , :EMPTIME                                    
      *             , :ESTARTDATE                                   
      *             , :EENDDATE                                   
      *           FROM 
      *                EMP_PROJ_ACT                                              
      *           WHERE 
      *                PROJ_NO = :WS-PROJ-NO
      *            AND EMPNO   = :WS-EMPNO
      *         END-EXEC.                                               
      *                                                                 
      *         EVALUATE SQLCODE                                        
      *             WHEN 0                                              
      *                  CONTINUE                                       
      *             WHEN OTHER                
      *                  MOVE SPACES     TO ACT-NO                                        
      *                                     EMPTIME                                     
      *                                     ESTARTDATE                                        
      *                                     EENDDATE                                    
      *                                     
      *                  PERFORM 950-DBERROR THRU 950-EXIT                           
      *         END-EVALUATE.                                           
                                                                        
           MOVE PROJ-NAME           TO PRJACTMNAMEO.                            
           MOVE DEPTNO              TO PRJACTMDEPO.                             
           MOVE PROJ-EMP            TO PRJACTMEMPO.                                
           MOVE PROJ-STARTDATE      TO PRJACTMSDATEO.                          
           MOVE PROJ-ENDDATE        TO PRJACTMEDATEO.                        

                                                                        
           MOVE SPACES         TO  PRJACTMO.                              
           MOVE PROJ-NO        TO  PRJACTMPRJO.                           
                                                                        
           IF  RESPONSE = DFHRESP(NOTFND) THEN                          
               MOVE 7                    TO WORK-MSG-CODE               
               MOVE SPACES               TO PRJACTMDEPO                   
                                            PRJACTMNAMEO                  
                                            PRJACTMADDR1O                 
                                            PRJACTMJDATEO                 
                                            PRJACTMBDATEO                 
                                            PRJACTMEDATEO                 
                                            PRJACTMSDATEO                 
           ELSE                                                         
               MOVE PROJ-NO              TO PRJACTMPRJO                   
               MOVE ACT-NO               TO PRJACTMNAMEO                  
               MOVE EMPTIME              TO PRJACTMADDR1O                 
      *                                                                 
               MOVE ACT-DATE-STARTED     TO COBDATE-DATE                
               MOVE "YYDDD"              TO COBDATE-INPUT-FORMAT        
               MOVE "MM/DD/YY"           TO COBDATE-OUTPUT-FORMAT       
               CALL "COBDTE3"         USING 
                                            COBDATE-DATE                        
                                            COBDATE-INPUT-FORMAT                
                                            COBDATE-OUTPUT-FORMAT               
                                            COBDATE-MESSAGE                     
               MOVE COBDATE-DATE         TO PRJACTMJDATEO                 
      *                                                                 
               MOVE PRJACTMBDATEO        TO COBDATE-DATE                
               MOVE "YYDDD"              TO COBDATE-INPUT-FORMAT        
               MOVE "MM/DD/YY"           TO COBDATE-OUTPUT-FORMAT       
               CALL "COBDTE3"         USING 
                                            COBDATE-DATE                        
                                            COBDATE-INPUT-FORMAT                
                                            COBDATE-OUTPUT-FORMAT               
                                            COBDATE-MESSAGE                     
               MOVE COBDATE-DATE         TO PRJACTMBDATEO                 
      *                                                                 
               IF  ACT-DATE-ENDED > ZEROS THEN                     
                   MOVE ACT-DATE-ENDED   TO COBDATE-DATE            
                   MOVE "YYMMDD"         TO COBDATE-INPUT-FORMAT    
                   MOVE "MM/DD/YY"       TO COBDATE-OUTPUT-FORMAT   
                   CALL "COBDTE3"     USING 
                                            COBDATE-DATE                    
                                            COBDATE-INPUT-FORMAT            
                                            COBDATE-OUTPUT-FORMAT           
                                            COBDATE-MESSAGE                 
                   MOVE COBDATE-DATE     TO PRJACTMEDATEO             
               ELSE                                                     
                   MOVE SPACES           TO PRJACTMEDATEO             
               END-IF                                                   
      *                                                                 
               MOVE PRJACTMSDATEO        TO COBDATE-DATE                
               MOVE "YYDDD"              TO COBDATE-INPUT-FORMAT        
               MOVE "MM/DD/YY"           TO COBDATE-OUTPUT-FORMAT       
               CALL "COBDTE3"         USING 
                                            COBDATE-DATE                        
                                            COBDATE-INPUT-FORMAT                
                                            COBDATE-OUTPUT-FORMAT               
                                            COBDATE-MESSAGE                     
               MOVE COBDATE-DATE         TO PRJACTMSDATEO                 
      *                                                                 
               MOVE 8                    TO WORK-MSG-CODE               
           END-IF.                                                      
                                                                        
       401-READ-EMP-PROJ-ACTIVITY-DETAILS.                                             

           MOVE PRJACTMPRJI TO  WS-PROJ-NO.    
           MOVE PRJACTMEMPI TO  WS-EMPNO.
      *-----------------------------------------------------------------
      *    SQL QUERY FOR GETTING EMPLOYEE PROJECT ACTIVITY DETAILS.
      *    DETAILS ARE OBTAINED ON THE BASIS OF EMPLOYEE CODE AND 
      *    PROJECT CODE ENTERED BY THE USER.
      *-----------------------------------------------------------------
           MOVE 1 TO CMD-CODE.
           MOVE PHOTO-MASTER-RECORD TO DATA-IN.
           CALL "APITPP5" USING CMD-CODE RESP-CODE DATA-IN DATA-OUT.
           IF RESP-CODE <> 0 
               MOVE DATA-OUT TO PHOTO-MASTER-RECORD
           ELSE
               PERFORM 950-DBERROR THRU 950-EXIT
           END-IF
                                                                        
           MOVE PROJ-NAME           TO PRJACTMNAMEO.                            
           MOVE DEPTNO              TO PRJACTMDEPO.                             
           MOVE PROJ-EMP            TO PRJACTMEMPO.                                
           MOVE PROJ-STARTDATE      TO PRJACTMSDATEO.                          
           MOVE PROJ-ENDDATE        TO PRJACTMEDATEO.                        

                                                                        
           MOVE SPACES         TO  PRJACTMO.                              
           MOVE PROJ-NO        TO  PRJACTMPRJO.                           
                                                                        
           IF  RESPONSE = DFHRESP(NOTFND) THEN                          
               MOVE 7                    TO WORK-MSG-CODE               
               MOVE SPACES               TO PRJACTMDEPO                   
                                            PRJACTMNAMEO                  
                                            PRJACTMADDR1O                 
                                            PRJACTMJDATEO                 
                                            PRJACTMBDATEO                 
                                            PRJACTMEDATEO                 
                                            PRJACTMSDATEO                 
           ELSE                                                         
               MOVE PROJ-NO              TO PRJACTMPRJO                   
               MOVE ACT-NO               TO PRJACTMNAMEO                  
               MOVE EMPTIME              TO PRJACTMADDR1O                 
      *                                                                 
               MOVE ACT-DATE-STARTED     TO COBDATE-DATE                
               MOVE "YYDDD"              TO COBDATE-INPUT-FORMAT        
               MOVE "MM/DD/YY"           TO COBDATE-OUTPUT-FORMAT       
               CALL "COBDTE3"         USING 
                                            COBDATE-DATE                        
                                            COBDATE-INPUT-FORMAT                
                                            COBDATE-OUTPUT-FORMAT               
                                            COBDATE-MESSAGE                     
               MOVE COBDATE-DATE         TO PRJACTMJDATEO                 
      *                                                                 
               MOVE PRJACTMBDATEO        TO COBDATE-DATE                
               MOVE "YYDDD"              TO COBDATE-INPUT-FORMAT        
               MOVE "MM/DD/YY"           TO COBDATE-OUTPUT-FORMAT       
               CALL "COBDTE3"         USING 
                                            COBDATE-DATE                        
                                            COBDATE-INPUT-FORMAT                
                                            COBDATE-OUTPUT-FORMAT               
                                            COBDATE-MESSAGE                     
               MOVE COBDATE-DATE         TO PRJACTMBDATEO                 
      *                                                                 
               IF  ACT-DATE-ENDED > ZEROS THEN                     
                   MOVE ACT-DATE-ENDED   TO COBDATE-DATE            
                   MOVE "YYMMDD"         TO COBDATE-INPUT-FORMAT    
                   MOVE "MM/DD/YY"       TO COBDATE-OUTPUT-FORMAT   
                   CALL "COBDTE3"     USING 
                                            COBDATE-DATE                    
                                            COBDATE-INPUT-FORMAT            
                                            COBDATE-OUTPUT-FORMAT           
                                            COBDATE-MESSAGE                 
                   MOVE COBDATE-DATE     TO PRJACTMEDATEO             
               ELSE                                                     
                   MOVE SPACES           TO PRJACTMEDATEO             
               END-IF                                                   
      *                                                                 
               MOVE PRJACTMSDATEO        TO COBDATE-DATE                
               MOVE "YYDDD"              TO COBDATE-INPUT-FORMAT        
               MOVE "MM/DD/YY"           TO COBDATE-OUTPUT-FORMAT       
               CALL "COBDTE3"         USING 
                                            COBDATE-DATE                        
                                            COBDATE-INPUT-FORMAT                
                                            COBDATE-OUTPUT-FORMAT               
                                            COBDATE-MESSAGE                     
               MOVE COBDATE-DATE         TO PRJACTMSDATEO                 
      *                                                                 
               MOVE 8                    TO WORK-MSG-CODE               
           END-IF.                                                      
                                                                        
      *-----------------------------------------------------------------                                                                  
      * THIS PARA VALIDATES WHETHER THE USER HAS GIVEN PROPER INPUT.                                                                        
      *-----------------------------------------------------------------                                                                  
       500-VALIDATE-SCREEN.                                             
                                                                        
           MOVE SWITCH-OFF    TO  ERROR-SWITCH.                           
                                                                        
           IF  PRJACTMNAMEI IS NOT > SPACES   THEN                        
               MOVE DFHBMASB  TO  PRJACTMNAMEA                            
               IF  NOT ERRORS THEN                                      
                   SET ERRORS TO TRUE                                   
                   MOVE -1    TO PRJACTMNAMEL                             
                   MOVE 10    TO WORK-MSG-CODE                          
               END-IF                                                   
           END-IF                                                       
                                                                        
           IF  PRJACTMADDR1I IS NOT > SPACES   THEN                       
               MOVE DFHBMASB  TO  PRJACTMADDR1A                           
               IF  NOT ERRORS THEN                                      
                   SET ERRORS TO TRUE                                   
                   MOVE -1    TO PRJACTMADDR1L                            
                   MOVE 11    TO WORK-MSG-CODE                          
               END-IF                                                   
           END-IF                                                       
                                                                        
           IF  PRJACTMZIPI IS NOT NUMERIC THEN                            
               MOVE DFHBMASB  TO  PRJACTMZIPA                             
               IF  NOT ERRORS THEN                                      
                   SET ERRORS TO TRUE                                   
                   MOVE -1    TO PRJACTMZIPL                              
                   MOVE 4     TO WORK-MSG-CODE                          
               END-IF                                                   
           END-IF                                                       
                                                                        
           IF  PRJACTMJDATEI > SPACES     THEN                            
               MOVE PRJACTMJDATEI   TO COBDATE-DATE                       
               MOVE "MM/DD/YY"      TO COBDATE-INPUT-FORMAT             
               MOVE "YYDDD"         TO COBDATE-OUTPUT-FORMAT            
               CALL "COBDTE3"    USING 
                                       COBDATE-DATE                        
                                       COBDATE-INPUT-FORMAT                
                                       COBDATE-OUTPUT-FORMAT               
                                       COBDATE-MESSAGE                     

               IF  COBDATE-MESSAGE NOT = SPACES THEN                    
                   MOVE DFHBMASB    TO  PRJACTMJDATEA                       

                   IF  NOT ERRORS THEN                                  
                       SET ERRORS   TO TRUE                               
                       MOVE -1      TO  PRJACTMJDATEL                       
                       MOVE 5       TO WORK-MSG-CODE                      
                   END-IF                                               

               ELSE                                                     

                   MOVE COBDATE-DATE-YYDDD TO WORK-JOINED-YYDDD         
                   MOVE PRJACTMJDATEI      TO WORK-JOINED-MMDDYY          

               END-IF                                                   

           ELSE                                                         

               MOVE WORK-TODAYS-MMDDYY TO PRJACTMJDATEO                   
                                          WORK-JOINED-MMDDYY            
               MOVE WORK-EIB-YYDDD     TO WORK-JOINED-YYDDD             

           END-IF                                                       
                                                                        
           IF  PRJACTMBDATEI > SPACES     THEN                            
               MOVE PRJACTMBDATEI   TO COBDATE-DATE                       
               MOVE "MM/DD/YY"      TO COBDATE-INPUT-FORMAT             
               MOVE "YYDDD"         TO COBDATE-OUTPUT-FORMAT            
               CALL "COBDTE3"    USING 
                                       COBDATE-DATE                        
                                       COBDATE-INPUT-FORMAT                
                                       COBDATE-OUTPUT-FORMAT               
                                       COBDATE-MESSAGE                     

               IF  COBDATE-MESSAGE NOT = SPACES THEN                    
                   MOVE DFHBMASB    TO  PRJACTMBDATEA                       

                   IF  NOT ERRORS THEN                                  
                       SET ERRORS TO TRUE                               
                       MOVE -1    TO  PRJACTMBDATEL                       
                       MOVE 5     TO WORK-MSG-CODE                      
                   END-IF                                               

               END-IF                                                   

           ELSE                                                         
               MOVE DFHBMASB  TO  PRJACTMBDATEA                           
               IF  NOT ERRORS THEN                                      
                   SET ERRORS TO TRUE                                   
                   MOVE -1    TO  PRJACTMBDATEL                           
                   MOVE 13    TO WORK-MSG-CODE                          
               END-IF                                                   
           END-IF                                                       
                                                                        
           IF  PRJACTMTDATEI > SPACES THEN                                
               MOVE PRJACTMTDATEI   TO COBDATE-DATE                       
               MOVE "MM/DD/YY"      TO COBDATE-INPUT-FORMAT             
               MOVE "YYDDD"         TO COBDATE-OUTPUT-FORMAT            
               CALL "COBDTE3" USING COBDATE-DATE                        
                                    COBDATE-INPUT-FORMAT                
                                    COBDATE-OUTPUT-FORMAT               
                                    COBDATE-MESSAGE                     
               IF  COBDATE-MESSAGE NOT = SPACES THEN                    
                   MOVE DFHBMASB  TO  PRJACTMTDATEA                       
                   IF  NOT ERRORS THEN                                  
                       SET ERRORS TO TRUE                               
                       MOVE -1    TO  PRJACTMTDATEL                       
                       MOVE 5     TO WORK-MSG-CODE                      
                   END-IF                                               
               ELSE                                                     
                   MOVE PRJACTMEDATEO      TO PRJACTMSDATEO                 
                   MOVE COBDATE-DATE-YYDDD TO WORK-TERMINATED-YYDDD     
                   IF  WORK-TERMINATED-YYDDD < WORK-JOINED-YYDDD        
                       MOVE DFHBMASB  TO  PRJACTMJDATEA                   
                                          PRJACTMTDATEA                   
                       IF  NOT ERRORS THEN                              
                           SET ERRORS TO TRUE                           
                           MOVE -1    TO  PRJACTMJDATEL                   
                           MOVE 12    TO WORK-MSG-CODE                  
                       END-IF                                           
                   END-IF                                               
               END-IF                                                   
           END-IF.                                                      
                                                                        
           IF  PRJACTMSDATEI > SPACES     THEN                            
               MOVE PRJACTMSDATEI   TO COBDATE-DATE                       
               MOVE "MM/DD/YY"      TO COBDATE-INPUT-FORMAT             
               MOVE "YYDDD"         TO COBDATE-OUTPUT-FORMAT            
               CALL "COBDTE3" USING COBDATE-DATE                        
                                    COBDATE-INPUT-FORMAT                
                                    COBDATE-OUTPUT-FORMAT               
                                    COBDATE-MESSAGE                     
               IF  COBDATE-MESSAGE NOT = SPACES THEN                    
                   MOVE DFHBMASB  TO  PRJACTMSDATEA                       
                   IF  NOT ERRORS THEN                                  
                       SET ERRORS TO TRUE                               
                       MOVE -1    TO  PRJACTMSDATEL                       
                       MOVE 5     TO WORK-MSG-CODE                      
                   END-IF                                               
               END-IF                                                   
           ELSE                                                         
               COMPUTE WORK-JOINED-YY = WORK-JOINED-YY + 1              
               IF  WORK-JOINED-MMDD = "02/29/" THEN                     
                   MOVE "02/28/"  TO WORK-JOINED-MMDD                   
               END-IF                                                   
               MOVE WORK-JOINED-MMDDYY TO PRJACTMSDATEO                   
           END-IF.                                                      
                                                                        
      *-----------------------------------------------------------------                                                                  
      * THIS PARA MAKES A CALL TO COBOL PROGRAM TO UPDATE EMPLOYEE DATA.                                                                    
      *-----------------------------------------------------------------                                                                  
       600-UPDATE-EMP-PROJ-ACTIVITY-DETAILS.                                           
                                                                        
           MOVE SPACES             TO  PROJECT-MASTER-RECORD.          
           MOVE PRJACTMEMPI        TO  EMP-NO.                            
           MOVE PRJACTMDEPI        TO  PROJ-NO.                     
           MOVE PRJACTMNAMEI       TO  ACT-NO.                          
           MOVE PRJACTMADDR1I      TO  EMPTIME.                        
           MOVE PRJACTMJDATEI      TO  COBDATE-DATE                       
           MOVE "MM/DD/YY"         TO  COBDATE-INPUT-FORMAT             
           MOVE "YYDDD"            TO  COBDATE-OUTPUT-FORMAT            
           CALL "COBDTE3" USING        COBDATE-DATE                     
                                       COBDATE-INPUT-FORMAT             
                                       COBDATE-OUTPUT-FORMAT            
                                       COBDATE-MESSAGE.                 
           MOVE COBDATE-DATE-YYDDD TO  ACT-DATE-STARTED.                 
           MOVE PRJACTMBDATEI      TO  COBDATE-DATE                       
           MOVE "MM/DD/YY"         TO  COBDATE-INPUT-FORMAT             
           MOVE "YYDDD"            TO  COBDATE-OUTPUT-FORMAT            
           CALL "COBDTE3" USING        COBDATE-DATE                     
                                       COBDATE-INPUT-FORMAT             
                                       COBDATE-OUTPUT-FORMAT            
                                       COBDATE-MESSAGE.                 
           MOVE COBDATE-DATE-YYDDD TO  ACT-DATE-STARTED.                  
                                                                        
           IF  PRJACTMTDATEI > SPACES THEN                                
               MOVE PRJACTMTDATEI   TO COBDATE-DATE                       
               MOVE "MM/DD/YY"      TO COBDATE-INPUT-FORMAT             
               MOVE "YYMMDD"        TO COBDATE-OUTPUT-FORMAT            
               CALL "COBDTE3" USING    COBDATE-DATE                     
                                       COBDATE-INPUT-FORMAT             
                                       COBDATE-OUTPUT-FORMAT            
                                       COBDATE-MESSAGE                  
               MOVE COBDATE-DATE-YYMMDD TO  ACT-DATE-ENDED         
           ELSE                                                         
               MOVE ZEROS           TO  ACT-DATE-ENDED             
           END-IF.                                                      
           MOVE PRJACTMSDATEI      TO  COBDATE-DATE                       
           MOVE "MM/DD/YY"         TO  COBDATE-INPUT-FORMAT             
           MOVE "YYDDD"            TO  COBDATE-OUTPUT-FORMAT            
           CALL "COBDTE3" USING        COBDATE-DATE                     
                                       COBDATE-INPUT-FORMAT             
                                       COBDATE-OUTPUT-FORMAT            
                                       COBDATE-MESSAGE.                 
           MOVE COBDATE-DATE-YYDDD TO  PRJACTMSDATEI.                
                                                                        
      *-----------------------------------------------------------------
      *    SQL QUERY FOR UPDATING EMPLOYEE PROJECT ACTIVITY DETAILS.
      *    DETAILS OBJTAINED FROM CICS SCREEN ARE BEING UPDATED IN THE 
      *    DATABASE.
      *-----------------------------------------------------------------
           MOVE 2 TO CMD-CODE.
           MOVE PHOTO-MASTER-RECORD TO DATA-IN.
           CALL "APITPP4" USING CMD-CODE RESP-CODE DATA-IN DATA-OUT.
           IF RESP-CODE <> 0 
               MOVE DATA-OUT TO PHOTO-MASTER-RECORD
           ELSE
               PERFORM 950-DBERROR THRU 950-EXIT
           END-IF
      *    EXEC SQL                                                 
      *         UPDATE                                           
      *             EMP_PROJ_ACT                                 
      *         SET 
      *             ACT_NO         = :ACT-NO       
      *           , EMPTIME        = :EMPTIME     
      *           , ESTARTDATE     = :ESTARTDATE       
      *           , EENDDATE       = :EENDDATE      
      *         WHERE
      *         (
      *               EMP_PROJ_ACT.EMPNO   = :WS-EMPNO
      *           AND EMP_PROJ_ACT.PROJ_NO = :WS-PROJ-NO
      *         )
      *    END-EXEC                                                 
      *
      *    EVALUATE SQLCODE                                         
      *    WHEN 0                                               
      *      CONTINUE                                        
      *    WHEN OTHER                                           
      *      PERFORM 950-DBERROR THRU 950-EXIT             
      *    END-EVALUATE                                            
                                                                        
           MOVE 1    TO  WORK-MSG-CODE                              
                                                                        
       900-ERRORS.                                                      
                                                                        
           MOVE "TRANSACTION ABNORMALLY TERMINATED" TO PRJACTMMSGO.       
           GO TO 999-EXIT.                                              
                                                                        
       910-EXIT.                                                        
                                                                        
           MOVE "PROCESSING COMPLETED"              TO PRJACTMMSGO.       
           GO TO 999-EXIT.                                              
                                                                        
      *-----------------------------------------------------------------
      * 950-DBERROR - GET ERROR MESSAGE                                
      *-----------------------------------------------------------------
       950-DBERROR.                                                    
      *         CALL 'DSNTIAR' USING SQLCA ERROR-MESSAGE ERROR-TEXT-LEN.
                MOVE "PROCESSING COMPLETED WITH ERRORS"  TO PRJACTMMSGO.       
                GO TO 999-EXIT.                                              
                
       950-EXIT.                                                       
                EXIT.
                

       999-EXIT.                                                        
           MOVE DFHBMASB                 TO PRJACTMMSGA.                  

           EXEC CICS 
               SEND TEXT 
               FROM(PRJACTMMSGO) 
               LENGTH(57)               
               ERASE 
           END-EXEC.                          

           EXEC CICS 
               RETURN 
           END-EXEC.                                   
                                                                        
      *END PROGRAM CAST.                                                