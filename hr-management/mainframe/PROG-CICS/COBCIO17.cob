      *-----------------------------------------------------------------
      *    COBCIO17 - SAMPLE CICS PROGRAM TO GET INSURANCE DETAILS OF CPU
      *               THIS PROGRAM DIRECTLY CALLS DB2 DATABASE FOR GETTING
      *               INSURANCE DETAILS FROM HARDWARE TABLE.
      *               USER CAN ENTER HARDWARE ID AND CPU ID               
      *               FROM CICS SCREEN AND GETS INSURANCE DETAILS ON THE 
      *               SCREEN.
      *-----------------------------------------------------------------
      *                                                                 
      *--------------------PART OF MYTELCO HR APPLICATION-----------
      *                                                                 
      *-----------------------------------------------------------------
       IDENTIFICATION DIVISION.                                         
       PROGRAM-ID.   COBCIO17.                                          
       AUTHOR.       CAST SOFTWARE                                      
       DATE-WRITTEN. MARCH 2006.                                       
                                                                        
       EJECT                                                            
       ENVIRONMENT DIVISION.                                            
       DATA DIVISION.                                                   
                                                                        
       WORKING-STORAGE SECTION.                                         
                                                                        
       01  HARDWARE-MASTER-RECORD.                                      
           10 WS-HARDWARE-ID          PIC X(10).
           10 WS-MOUSE-ID             PIC X(10).
           10 WS-KEYBOARD-ID          PIC X(10).
           10 WS-CPU-ID               PIC X(10).
           10 WS-MONITOR-ID           PIC X(10).
           10 WS-MOUSE-INS-VALUE      PIC S9(7)V9(2) USAGE COMP-3.
           10 WS-KEYBOARD-INS-VALUE   PIC S9(7)V9(2) USAGE COMP-3. 
           10 WS-CPU-INS-VALUE        PIC S9(7)V9(2) USAGE COMP-3.            
           10 WS-MONITOR-INS-VALUE    PIC S9(7)V9(2) USAGE COMP-3.
         
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
           
           COPY HARDWRM.

      *    EXEC SQL
      *        INCLUDE HARDWARE
      *    END-EXEC.
      *
      *    EXEC SQL 
      *        INCLUDE SQLCA  
      *    END-EXEC.                       
                                                                        
                                                                       
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
                                                                        
           MOVE SPACES         TO  HARDWRMO.                 
           MOVE 6              TO  WORK-MSG-CODE.                       
           MOVE -1             TO  HARDWRMHARI.                           
           
           GO TO 300-SEND-HARDWRMAP.                                          
                                                                        
      *-----------------------------------------------------------------                                                                  
      * RECEIVE MAP RECEIVES THE CICS MAP.                                                                                                   
      *-----------------------------------------------------------------                                                                  
       200-RECEIVE-HARDWRMAP.                                                 

           EXEC CICS 
               RECEIVE MAP("MAP") MAPSET("HARDWMP")
               INTO(HARDWRMI) 
           END-EXEC.        
                                                                                                                                                
           IF  HARDWRMHARI = ZERO THEN                                    
               MOVE -1                TO  HARDWRMHARI                        
               MOVE 6                 TO  WORK-MSG-CODE                    
               GO TO 300-SEND-HARDWRMAP                                       
           ELSE                                                         
               IF HARDWRMHARI IS NOT NUMERIC THEN                         
                  MOVE -1             TO  HARDWRMHARI                     
                  MOVE 14             TO  WORK-MSG-CODE                 
                  GO TO 300-SEND-HARDWRMAP                                    
               END-IF                                                   
           END-IF.                                                      
                                                                        
           PERFORM 400-READ-HARDWARE-DETAILS
                                                                        
           MOVE DFHBMFSE        TO  HARDWRMHARA                               
                                    HARDWRMCPUA                               
                                    HARDWRMINSA.                              
                                                                        
      *-----------------------------------------------------------------                                                                  
      * THIS PARA SEND THE MAP TO CICS SCREEN USING THE OUTPUT COMM AREA                                                                 
      *-----------------------------------------------------------------                                                                  
       300-SEND-HARDWRMAP.                                                   
           MOVE MSG(WORK-MSG-CODE) TO HARDWRMMSGO.                        

           EXEC CICS 
               SEND MAP("MAP") MAPSET("HARDWMP")
               FROM(HARDWRMO)                     
               CURSOR FREEKB 
           END-EXEC.                        

           GO TO 200-RECEIVE-HARDWRMAP.                                       
                                                                        
      *-----------------------------------------------------------------                                                                  
      * THIS PARA FIRES THE SQL QUERY TO GET HARDWR DETAILS                                                                 
      *-----------------------------------------------------------------                                                                                         
       400-READ-HARDWARE-DETAILS.                                             

           MOVE HARDWRMCPUI TO  WS-CPU-ID.    
           MOVE HARDWRMHARI TO  WS-HARDWARE-ID.
      
           MOVE 1 TO CMD-CODE.
           MOVE HARDWARE-MASTER-RECORD TO DATA-IN.
           CALL "APITP017" USING CMD-CODE RESP-CODE DATA-IN DATA-OUT.
           IF RESP-CODE <> 0 
               MOVE DATA-OUT TO HARDWARE-MASTER-RECORD
           ELSE
               PERFORM 950-DBERROR THRU 950-EXIT
           END-IF
      *    EXEC SQL                                                
      *           SELECT                                                
      *               CPU_INS_VALUE                              
      *           INTO          
      *               :WS-CPU-INS-VALUE
      *           FROM 
      *                HARDWARE
      *           WHERE 
      *                CPU_ID       = :WS-CPU-ID
      *            AND HARDWARE_ID  = :WS-HARDWARE-ID
      *         END-EXEC.                                               
      *                                                                 
      *         EVALUATE SQLCODE                                        
      *             WHEN 0                                              
      *                  CONTINUE                                       
      *             WHEN OTHER                
      *                  MOVE SPACES     TO WS-CPU-INS-VALUE
      *                  PERFORM 950-DBERROR THRU 950-EXIT                           
      *         END-EVALUATE.                                           
                                                                        
           IF  RESPONSE = DFHRESP(NOTFND) THEN                          
               MOVE 7                    TO WORK-MSG-CODE               
               MOVE SPACES               TO HARDWRMINSO                   
           ELSE                                                         
               MOVE WS-CPU-INS-VALUE     TO HARDWRMINSO                  
               MOVE 8                    TO WORK-MSG-CODE               
           END-IF.                                                      
                                                                                                                                                                                                                        
       
       900-ERRORS.                                                      
                                                                        
           MOVE "TRANSACTION ABNORMALLY TERMINATED" TO HARDWRMMSGO.       
           GO TO 999-EXIT.                                              
                                                                        
       910-EXIT.                                                        
                                                                        
           MOVE "PROCESSING COMPLETED"              TO HARDWRMMSGO.       
           GO TO 999-EXIT.                                              
       
       
      *-----------------------------------------------------------------
      * 950-DBERROR - GET ERROR MESSAGE                                
      *-----------------------------------------------------------------
       950-DBERROR.                                                    
      *         CALL 'DSNTIAR' USING SQLCA ERROR-MESSAGE ERROR-TEXT-LEN.
                MOVE "PROCESSING COMPLETED WITH ERRORS"  TO HARDWRMMSGO.       
                GO TO 999-EXIT.                                              
                
       950-EXIT.                                                       
                EXIT.
                

       999-EXIT.                                                        
           MOVE DFHBMASB                 TO HARDWRMMSGA.                  

           EXEC CICS 
               SEND TEXT 
               FROM(HARDWRMMSGO) 
               LENGTH(57)               
               ERASE 
           END-EXEC.                          

           EXEC CICS 
               RETURN 
           END-EXEC.                                   
                                                                        
      *END PROGRAM CAST.                                                