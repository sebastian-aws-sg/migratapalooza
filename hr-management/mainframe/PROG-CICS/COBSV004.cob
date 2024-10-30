      *-----------------------------------------------------------------
      *    COBSV004 - SAMPLE COBOL-CICS PROGRAM TO DISPLAY ALL THE 
      *               EMPLOYEES HAVING SALARY EQUAL TO THE AMOUNT OR 
      *               IS IN THE RANGE OF ENTERED PERCENTAGE ENTERED BY 
      *               USER ON CICS SCREEN.
      *               (SAME AS COBCIOP8)
      *               ADDITIONAL - HANDLE ABEND & ALTER 
      *-----------------------------------------------------------------
      *                                                                 
      *--------------------PART OF MYTELCO HR APPLICATION-------------
      *                                                                 
      *-----------------------------------------------------------------
       IDENTIFICATION DIVISION.                                         
       PROGRAM-ID.   COBSV004.                                          
       AUTHOR.       CAST SOFTWARE                                      
       DATE-WRITTEN. OCTOBER 2005.                                       
                                                                        
       EJECT                                                            
       ENVIRONMENT DIVISION.                                            
       DATA DIVISION.                                                   
                                                                        
       WORKING-STORAGE SECTION.                                         
                                                                        
       01  EMPLOYEE-MASTER-RECORD.                                      
      *        ** key field                                             
           03  EMP-ID                      PIC X(6).                    
           03  EMP-DEPT-CODE               PIC X(4).                    
           03  EMP-NAME                    PIC X(30).                   
           03  EMP-ADDR-1                  PIC X(30).                   
           03  EMP-ADDR-2                  PIC X(30).                   
           03  EMP-ADDR-3                  PIC X(30).                   
           03  EMP-ZIP-CODE                PIC X(5).                    
      *        ** format (yyddd)                                        
           03  EMP-DATE-JOINED             PIC 9(5).                    
      *        ** format (yymmdd)                                       
           03  EMP-DATE-TERMINATED         PIC 9(6).                    
      *        ** format (yyddd)                                        
           03  EMP-DATE-MAINTAINED         PIC 9(5).                    
      *        ** format (yyddd)                                        
           03  EMP-BIRTH-DATE              PIC 9(5).                    
      *        ** format (yyddd)                                        
           03  EMP-SECURITY-EXP            PIC 9(5) COMP-3.             
           03  FILLER                      PIC X(41).                   
                                                                        
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
           03  FILLER  PIC X(30) VALUE "E-DEPARTMENT CODE INVALID   ".  
           03  FILLER  PIC X(30) VALUE "E-ZIP CODE NOT NUMERIC      ".  
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

       01  EIBDATE     PIC S9(07) COMP-3.
                                                                        
      *    ** work variables                                            
                                                                        
       01  WORK-VARS.                                                   
           03  WORK-TODAYS-MMDDYY          PIC 9(8).                    
           03  WORK-MSG-CODE               PIC 99.                      
      *    TARGET OF EIBDATE ASSIGNMENT MUST BE PIC 9(7) COMP-3.        
           03  WORK-EIB-DATE               PIC 9(7) COMP-3.             
           03  WORK-EIB-DATE-CHAR.                                      
               05  FILLER                  PIC X(1).                    
               05  WORK-EIB-CENTURY        PIC X(1).                    
               05  WORK-EIB-YYDDD          PIC X(5).                    
           03  WORK-JOINED-YYDDD           PIC 9(5).                    
           03  WORK-JOINED-MMDDYY.                                      
               05  WORK-JOINED-MMDD        PIC X(6).                    
               05  WORK-JOINED-YY          PIC 99.                      
           03  WORK-SEC-EXP                PIC 9(5).                    
           03  WORK-TERMINATED-YYDDD       PIC 9(5).                    
   
   
       01  COMMAREA                                                     
           03  CASTMSALI            PIC S9(7)V9(2) USAGE COMP-3.                                   
           03  CASTMSALVARI         PIC S9(3)      USAGE COMP-3.                                   
           03  CASTMEMPDETAILSO     PIC X(45) OCCURS WS-TOTAL-EMPLOYEE TIMES.                                  
                05  CASTMEMPO         PIC X(6)                                   
                05  CASTMNAMEO        PIC X(30)                                  
                05  CASTMSALO         PIC X(9)                                  

                                                                       
       01  EMP-LENGTH                      PIC S9(4) COMP VALUE +200.   
       01  DEP-LENGTH                      PIC S9(4) COMP VALUE +80.    
       01  RESPONSE                        PIC S9(8) COMP VALUE +0.     
       01  LAST-EMP-SALARY                 PIC S9(7)V99  VALUE +0.      
       01  DFHRESP OCCURS 10               PIC S9(8) COMP VALUE +0.
       01  NOTFND                          PIC 9 VALUE 1.
                                                                        
       01  WS-WORKING-VARS.                                                 
               02  WS-EMPNO                PIC X(06).                     
               02  WS-EMPNAME              PIC X(36).                                                                        
               02  WS-SALARY               PIC S9(7)V99.                                                                        
               02  WS-SALVAR               PIC S9(3) USAGE COMP-3.                                                                        
               02  WS-COUNTER              PIC S9(2).       
               02  WS-FETCH-COUNT          PIC S9(2).       
               02  WS-TOTAL-EMPLOYEE       PIC S9(9) VALUE 10.

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
                                                                        
           COPY DFHBMSCA.                                               
                                                                        
           COPY DFHAID.                                                 
                                                                        
           COPY EMPSALVR.                                                  
                                                                        
                                                                        
                                                                        
           EJECT                                                        
      *-----------------------------------------------------------------                                                                  
      * PROCEDURE DIVISION.
      *-----------------------------------------------------------------                                                                  
       PROCEDURE DIVISION.                                              
       100-MAIN-MODULE.                                                                 
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
               HANDLE ABEND RESET
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
           CALL "COBDTE3" USING COBDATE-DATE                            
                                COBDATE-INPUT-FORMAT                    
                                COBDATE-OUTPUT-FORMAT                   
                                COBDATE-MESSAGE.                        
           MOVE COBDATE-DATE   TO  WORK-TODAYS-MMDDYY.                  
                                                                        
           MOVE SPACES         TO  CASTMO.                              
           MOVE 6              TO  WORK-MSG-CODE.                       
           MOVE -1             TO  CASTMSALARYL.                           
           GO TO 300-SEND-MAP.                                          
                                                                        
      *-----------------------------------------------------------------                                                                  
      * RECEIVE MAP RECEIVES THE CICS MAP.                                                                                                   
      *-----------------------------------------------------------------                                                                  
       200-RECEIVE-MAP.                                                 
                                                                        
           EXEC CICS 
              RECEIVE MAP("MAP") MAPSET("EMPMAP")
              INTO(CASTMI) 
           END-EXEC.        
                                                                        
      *    **  validate employee salary                              
                                                                        
           IF  CASTMSALARYL IS SPACES THEN                                    
               MOVE -1             TO  CASTMSALARYL                        
               MOVE 6              TO  WORK-MSG-CODE                    
               GO TO 300-SEND-MAP                                       
           ELSE                                                         
               IF CASTMSALARYL IS NOT NUMERIC THEN                         
                  MOVE -1             TO  CASTMSALARYL                     
                  MOVE 14             TO  WORK-MSG-CODE                 
                  GO TO 300-SEND-MAP                                    
               END-IF                                                   
           END-IF.                                                      
                                                                        
      *    **  employee salary changed                                  
                                                                        
           IF  CASTMSALARYI NOT = LAST-EMP-SALARY THEN                         
               MOVE CASTMSALARYI TO LAST-EMP-SALARY                            
               PERFORM 400-READ-EMP-RECORD                              
               MOVE -1        TO CASTMSALARYL                              
            ELSE                                                        
      *        PERFORM 500-VALIDATE-SCREEN                              
               IF  NOT ERRORS THEN                                      
                   IF  EIBAID NOT = DFHPF10 THEN                            
                       MOVE 9  TO WORK-MSG-CODE                         
                   END-IF                                               
               END-IF                                                   
           END-IF.                                                      

      *    **  validate employee salary variation                            
                                                                        
           IF  CASTMSALVL IS SPACES THEN                                    
               MOVE -1             TO  CASTMSALVL                        
               MOVE 6              TO  WORK-MSG-CODE                    
               GO TO 300-SEND-MAP                                       
           ELSE                                                         
               IF CASTMSALVL IS NOT NUMERIC THEN                         
                  MOVE -1             TO  CASTMSALVL                     
                  MOVE 14             TO  WORK-MSG-CODE                 
                  GO TO 300-SEND-MAP                                    
               END-IF                                                   
           END-IF.                                                      
                                                                        
          MOVE DFHBMFSE     TO  CASTMSALARYA                               
          MOVE DFHBMFSE     TO  CASTMSALVA                               
                                                           
                                                                        
      *-----------------------------------------------------------------                                                                  
      * THIS PARA SEND THE MAP TO CICS SCREEN USING THE OUTPUT COMM AREA                                                                 
      *-----------------------------------------------------------------                                                                  
       300-SEND-MAP.                                                   
           MOVE MSG(WORK-MSG-CODE) TO CASTMMSGO.                        
           
           EXEC CICS 
               SEND MAP("MAP") MAPSET("EMPMAP")
               FROM(CASTMO)                     
               CURSOR 
               FREEKB 
           END-EXEC. 
           
           ALTER 100-MAIN-MODULE TO 200-RECEIVE-MAP.                                       
                                                                        
                       
      *-----------------------------------------------------------------                                                                  
      * THIS PARA MAKES A CALL TO COBOL PROGRAM TO GET EMPLOYEE RECORDS.                                                                    
      *-----------------------------------------------------------------                                                                  
       400-READ-EMP-RECORD.                                             
       COPY CASTPROC.
           MOVE CASTMSALARYI  TO CASTMSALI.                                   
           MOVE CASTMSALVARI  TO CASTMSALVI.                                   
           CALL "COBSALR2"    USING COMMAREA.                              
                                                                        

                                                                        
           IF  RESPONSE = DFHRESP(NOTFND) THEN                          
               MOVE 7                    TO WORK-MSG-CODE               
    
      *        MOVE SPACES TO OUTPUT MAP
      
   
               MOVE SPACES    TO CASTMEMP1O
               MOVE SPACES    TO CASTMNAME1O
               MOVE SPACES    TO CASTMSAL1O
               
               MOVE SPACES    TO CASTMEMP1O
               MOVE SPACES    TO CASTMNAME2O
               MOVE SPACES    TO CASTMSAL2O

               MOVE SPACES    TO CASTMEMP3O
               MOVE SPACES    TO CASTMNAME3O
               MOVE SPACES    TO CASTMSAL3O

               MOVE SPACES    TO CASTMEMP4O
               MOVE SPACES    TO CASTMNAME4O
               MOVE SPACES    TO CASTMSAL4O

               MOVE SPACES    TO CASTMEMP5O
               MOVE SPACES    TO CASTMNAME5O
               MOVE SPACES    TO CASTMSAL5O

               MOVE SPACES    TO CASTMEMP6O
               MOVE SPACES    TO CASTMNAME6O
               MOVE SPACES    TO CASTMSAL6O

               MOVE SPACES    TO CASTMEMP7O
               MOVE SPACES    TO CASTMNAME7O
               MOVE SPACES    TO CASTMSAL7O

               MOVE SPACES    TO CASTMEMP8O
               MOVE SPACES    TO CASTMNAME8O
               MOVE SPACES    TO CASTMSAL8O

               MOVE SPACES    TO CASTMEMP9O
               MOVE SPACES    TO CASTMNAME9O
               MOVE SPACES    TO CASTMSAL9O

               MOVE SPACES    TO CASTMEMP10O
               MOVE SPACES    TO CASTMNAME10O
               MOVE SPACES    TO CASTMSAL10O

               MOVE SPACES    TO CASTMEMP11O
               MOVE SPACES    TO CASTMNAME11O
               MOVE SPACES    TO CASTMSAL11O

               MOVE SPACES    TO CASTMEMP12O
               MOVE SPACES    TO CASTMNAME12O
               MOVE SPACES    TO CASTMSAL12O
    
           ELSE
           
      *        MOVE VALUES FROM THE TABLE OF RECORDS TO OUTPUT MAP
      
  
               MOVE CASTMEMPO(1)     TO CASTMEMP1O
               MOVE CASTMNAMEO(1)    TO CASTMNAME1O
               MOVE CASTMSALO(1)     TO CASTMSAL1O
               
               MOVE CASTMEMPO(2)     TO CASTMEMP2O
               MOVE CASTMNAMEO(2)    TO CASTMNAME2O
               MOVE CASTMSALO(2)     TO CASTMSAL2O

               MOVE CASTMEMPO(3)     TO CASTMEMP3O
               MOVE CASTMNAMEO(3)    TO CASTMNAME3O
               MOVE CASTMSALO(3)     TO CASTMSAL3O
               
               MOVE CASTMEMPO(4)     TO CASTMEMP4O
               MOVE CASTMNAMEO(4)    TO CASTMNAME4O
               MOVE CASTMSALO(4)     TO CASTMSAL4O

               MOVE CASTMEMPO(5)     TO CASTMEMP5O
               MOVE CASTMNAMEO(5)    TO CASTMNAME5O
               MOVE CASTMSALO(5)     TO CASTMSAL5O
               
               MOVE CASTMEMPO(6)     TO CASTMEMP6O
               MOVE CASTMNAMEO(6)    TO CASTMNAME6O
               MOVE CASTMSALO(6)     TO CASTMSAL6O

               MOVE CASTMEMPO(7)     TO CASTMEMP7O
               MOVE CASTMNAMEO(7)    TO CASTMNAME7O
               MOVE CASTMSALO(7)     TO CASTMSAL7O
               
               MOVE CASTMEMPO(8)     TO CASTMEMP8O
               MOVE CASTMNAMEO(8)    TO CASTMNAME8O
               MOVE CASTMSALO(8)     TO CASTMSAL8O

               MOVE CASTMEMPO(9)     TO CASTMEMP9O
               MOVE CASTMNAMEO(9)    TO CASTMNAME9O
               MOVE CASTMSALO(9)     TO CASTMSAL9O
               
               MOVE CASTMEMPO(10)    TO CASTMEMP10O
               MOVE CASTMNAMEO(10)   TO CASTMNAME10O
               MOVE CASTMSALO(10)    TO CASTMSAL10O

               MOVE CASTMEMPO(11)    TO CASTMEMP11O
               MOVE CASTMNAMEO(11)   TO CASTMNAME11O
               MOVE CASTMSALO(11)    TO CASTMSAL11O
               
               MOVE CASTMEMPO(12)    TO CASTMEMP12O
               MOVE CASTMNAMEO(12)   TO CASTMNAME12O
               MOVE CASTMSALO(12)    TO CASTMSAL12O

           END-IF.                                                      
                                                                        
      *-----------------------------------------------------------------                                                                  
      * THIS PARA VALIDATES WHETHER THE USER HAS GIVEN PROPER INPUT.                                                                        
      *-----------------------------------------------------------------                                                                  
       500-VALIDATE-SCREEN.                                             
                                                                        
           MOVE SWITCH-OFF  TO  ERROR-SWITCH.                           
                                                                        
      *-----------------------------------------------------------------                                                                  
      *    **  validate employee salary                              
      *-----------------------------------------------------------------                                                                  
                                                                        
           IF  CASTMSALARYL IS SPACES THEN                                    
               MOVE -1             TO  CASTMSALARYL                        
               MOVE 6              TO  WORK-MSG-CODE                    
               GO TO 300-SEND-MAP                                       
           ELSE                                                         
               IF CASTMSALARYL IS NOT NUMERIC THEN                         
                  MOVE -1             TO  CASTMSALARYL                     
                  MOVE 14             TO  WORK-MSG-CODE                 
                  GO TO 300-SEND-MAP                                    
               END-IF                                                   
           END-IF.                                                      
                                                                        
      *-----------------------------------------------------------------                                                                  
      *    **  employee salary changed                                  
      *-----------------------------------------------------------------                                                                  
                                                                        
           IF  CASTMSALARYI NOT = LAST-EMP-SALARY THEN                         
               MOVE CASTMSALARYI TO LAST-EMP-SALARY                            
               PERFORM 400-READ-EMP-RECORD                              
               MOVE -1        TO CASTMSALARYL                              
            ELSE                                                        
               PERFORM 500-VALIDATE-SCREEN                              
               IF  NOT ERRORS THEN                                      
                   IF  EIBAID NOT = DFHPF10 THEN                            
                       MOVE 9  TO WORK-MSG-CODE                         
                   END-IF                                               
               END-IF                                                   
           END-IF.                                                      
                    
                    
      *-----------------------------------------------------------------                                                                  
      * ERROR HANDLING PARA.                                                                        
      *-----------------------------------------------------------------                                                                  
       900-ERRORS.                                                      
                                                                        
           MOVE "TRANSACTION ABNORMALLY TERMINATED" TO CASTMMSGO.       
           GO TO 999-EXIT.                                              
                                                                        
      *-----------------------------------------------------------------                                                                  
      * EXIT PARA .                                                                        
      *-----------------------------------------------------------------                                                                  
       910-EXIT.                                                        
                                                                        
           MOVE "PROCESSING COMPLETED"              TO CASTMMSGO.       
           GO TO 999-EXIT.                                              
                                                                        
      *-----------------------------------------------------------------                                                                  
      * EXIT PARA                                                                        
      *-----------------------------------------------------------------                                                                  
       999-EXIT.                                                        
           MOVE DFHBMASB                 TO CASTMMSGA.                  
           EXEC CICS 
               SEND TEXT 
               FROM(CASTMMSGO) 
               LENGTH(57)               
               ERASE 
           END-EXEC.                          
           
           EXEC CICS 
               RETURN 
           END-EXEC.                                   
                                                                        
                                                                        
      *END PROGRAM CAST.                                                