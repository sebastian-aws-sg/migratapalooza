      *-----------------------------------------------------------------
      *    COBCIO14 - SAMPLE CICS PROGRAM TO PRINT EMPLOYEE LEAVE DETAILS
      *               THIS PROGRAM DIRECTLY CALLS DB2 DATABASE FOR GETTING
      *               EMPLOYEE LEAVE DETAILS.   
      *               USER CAN ENTER EMPLOYEE NUMBER AND YEAR MONTH               
      *               FROM CICS SCREEN AND GETS LEAVE DETAILS ON THE 
      *               SCREEN.
      *-----------------------------------------------------------------
      *                                                                 
      *--------------------PART OF MYTELCO HR APPLICATION-----------
      *                                                                 
      *-----------------------------------------------------------------
       IDENTIFICATION DIVISION.                                         
       PROGRAM-ID.   COBCIO14.                                          
       AUTHOR.       CAST SOFTWARE                                      
       DATE-WRITTEN. MARCH 2006.                                       
                                                                        
       EJECT                                                            
       ENVIRONMENT DIVISION.                                            
       DATA DIVISION.                                                   
                                                                        
       WORKING-STORAGE SECTION.                                         
                                                                        
       01  LEAVE-MASTER-RECORD.                                      
           10 WS-EMP-NO               PIC X(6).
           10 WS-LYEARMONTH           PIC X(6).
           10 WS-LAVAILABLE           PIC S9(4) USAGE COMP.
           10 WS-LTAKEN               PIC S9(4) USAGE COMP. 
           10 WS-LBALANCE             PIC S9(4) USAGE COMP.            
           10 WS-WORKINGDAYS          PIC S9(4) USAGE COMP.
         
       01  SWITCHES.                                                    
           03  ERROR-SWITCH                PIC X VALUE SPACE.           
               88  ERRORS                        VALUE "Y".             
                                                                        
       01  SWITCH-OFF                      PIC X VALUE "N".             
                                                                        
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

       01  EIBDATE     PIC S9(07) COMP-3.
       
       01  WORK-MSG-CODE          PIC 99.
       01  ERROR-MESSAGE.                                               
               02  ERROR-LEN   PIC S9(4)  COMP VALUE +960.              
               02  ERROR-TEXT  PIC X(80) OCCURS 12 TIMES                
                                    INDEXED BY ERROR-INDEX.       
       77  ERROR-TEXT-LEN      PIC S9(9)  COMP VALUE +80.               
       01 ERROR-INDEX PIC 99.
                                                                        
                                                                        
       01  PROJ-LENGTH                     PIC S9(4) COMP VALUE +200.   
       01  DEP-LENGTH                      PIC S9(4) COMP VALUE +80.    
       01  RESPONSE                        PIC S9(8) COMP VALUE +0.     
       01  DFHRESP OCCURS 10               PIC S9(8) COMP VALUE +0.
       01  NOTFND                          PIC 9 VALUE 1.
                                                                                                                                               
       01  PSQLCODE                        PIC S9(9) COMP.              
       01  PSQLSTATE                       PIC X(5).                    
       01  PSQLERRMC.                                                   
           49  PSQLERRMC-LEN               PIC S9(4) COMP.              
           49  PSQLERRMC-TEXT              PIC X(250).                  


           COPY DFHBMSCA.                                               
                                                                        
           COPY DFHAID.  
           
           COPY LEAVEM.

      *    EXEC SQL
      *      INCLUDE LEAVE
      *    END-EXEC.
      *
      *    EXEC SQL 
      *        INCLUDE SQLCA  
      *    END-EXEC.                       
                                                                        
                                                                       
       LINKAGE SECTION.
       01 CMD-CODE  PIC 99.
       01 RESP-CODE PIC 99.
       01 DATA-IN   PIC X(512).
       01 DATA-OUT  PIC X(2048)

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
                                                                        
           MOVE SPACES         TO  LEAVEMO.                 
           MOVE 6              TO  WORK-MSG-CODE.                       
           MOVE -1             TO  LEAVEMEMPI.                           
           
           GO TO 300-SEND-LEAVEMAP.                                          
                                                                        
      *-----------------------------------------------------------------                                                                  
      * RECEIVE MAP RECEIVES THE CICS MAP.                                                                                                   
      *-----------------------------------------------------------------                                                                  
       200-RECEIVE-LEAVEMAP.                                                 

           EXEC CICS 
               RECEIVE MAP("MAP") MAPSET("LEAVMP")
               INTO(LEAVEMI) 
           END-EXEC.        
                                                                                                                                                
           IF  LEAVEMEMPI = ZERO THEN                                    
               MOVE -1                TO  LEAVEMEMPI                        
               MOVE 6                 TO  WORK-MSG-CODE                    
               GO TO 300-SEND-LEAVEMAP                                       
           ELSE                                                         
               IF LEAVEMEMPI IS NOT NUMERIC THEN                         
                  MOVE -1             TO  LEAVEMEMPI                     
                  MOVE 14             TO  WORK-MSG-CODE                 
                  GO TO 300-SEND-LEAVEMAP                                    
               END-IF                                                   
           END-IF.                                                      
                                                                        
           PERFORM 400-READ-EMP-LEAVE-DETAILS
                                                                        
           MOVE DFHBMFSE        TO  LEAVEMEMPA                               
                                    LEAVEMYRMMA                               
                                    LEAVEMAVAA.                              
                                                                        
      *-----------------------------------------------------------------                                                                  
      * THIS PARA SEND THE MAP TO CICS SCREEN USING THE OUTPUT COMM AREA                                                                 
      *-----------------------------------------------------------------                                                                  
       300-SEND-LEAVEMAP.                                                   
           MOVE MSG(WORK-MSG-CODE) TO LEAVEMMSGO.                        

           EXEC CICS 
               SEND MAP("MAP") MAPSET("LEAVMP")
               FROM(LEAVEMO)                     
               CURSOR FREEKB 
           END-EXEC.                        

           GO TO 200-RECEIVE-LEAVEMAP.                                       
                                                                        
      *-----------------------------------------------------------------                                                                  
      * THIS PARA FIRES THE SQL QUERY TO GET LEAVE DETAILS                                                                 
      *-----------------------------------------------------------------                                                                                         
       400-READ-EMP-LEAVE-DETAILS.                                             

           MOVE LEAVEMYRMMI TO  WS-LYEARMONTH.    
           MOVE LEAVEMEMPI  TO  WS-EMP-NO.
           CALL "COBSV004" USING LEAVEMMSGO.

           MOVE 1 TO CMD-CODE.
           MOVE LEAVE-MASTER-RECORD TO DATA-IN.
           CALL "APITP014" USING CMD-CODE RESP-CODE DATA-IN DATA-OUT.
           IF RESP-CODE <> 0 
               MOVE DATA-OUT TO LEAVE-MASTER-RECORD
           ELSE
               PERFORM 950-DBERROR THRU 950-EXIT
           END-IF
      *    EXEC SQL                                                
      *        SELECT                                                
      *           LAVAILABLE INTO :WS-LAVAILABLE
      *        FROM 
      *           LEAVE                                              
      *        WHERE 
      *           LYEARMONTH = :WS-LYEARMONTH AND
      *           EMPNO      = :WS-EMP-NO
      *    END-EXEC.                                               
      *                                                                 
      *    EVALUATE SQLCODE                                        
      *        WHEN 0                                              
      *            CONTINUE                                       
      *        WHEN OTHER                
      *            MOVE SPACES     TO WS-LAVAILABLE                                        
      *            PERFORM 950-DBERROR THRU 950-EXIT                           
      *    END-EVALUATE.                                           
                                                                        
           IF  RESPONSE = DFHRESP(NOTFND) THEN                          
               MOVE 7                    TO WORK-MSG-CODE               
               MOVE SPACES               TO LEAVEMAVAO                   
           ELSE                                                         
               MOVE WS-LAVAILABLE        TO LEAVEMAVAO                  
               MOVE 8                    TO WORK-MSG-CODE               
           END-IF.                                                      
                                                                                                                                                                                                                        
       
       900-ERRORS.                                                      
                                                                        
           MOVE "TRANSACTION ABNORMALLY TERMINATED" TO LEAVEMMSGO.       
           GO TO 999-EXIT.                                              
                                                                        
       910-EXIT.                                                        
                                                                        
           MOVE "PROCESSING COMPLETED"              TO LEAVEMMSGO.       
           GO TO 999-EXIT.                                              
       
       
      *-----------------------------------------------------------------
      * 950-DBERROR - GET ERROR MESSAGE                                
      *-----------------------------------------------------------------
       950-DBERROR.                                                    
      *         CALL 'DSNTIAR' USING SQLCA ERROR-MESSAGE ERROR-TEXT-LEN.
                MOVE "PROCESSING COMPLETED WITH ERRORS"  TO LEAVEMMSGO.       
                GO TO 999-EXIT.                                              
                
       950-EXIT.                                                       
                EXIT.
                

       999-EXIT.                                                        
           MOVE DFHBMASB                 TO LEAVEMMSGA.                  

           EXEC CICS 
               SEND TEXT 
               FROM(LEAVEMMSGO) 
               LENGTH(57)               
               ERASE 
           END-EXEC.                          

           EXEC CICS 
               RETURN 
           END-EXEC.                                   
                                                                        
      *END PROGRAM CAST.                                                