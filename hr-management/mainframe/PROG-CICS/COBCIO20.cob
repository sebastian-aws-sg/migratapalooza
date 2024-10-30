      *-----------------------------------------------------------------
      *    COBCIO20 - SAMPLE CICS PROGRAM TO GET START AND END DATE OF 
      *               ANY ACTIVITY IN A PROJECT 
      *-----------------------------------------------------------------
      *                                                                 
      *--------------------PART OF MYTELCO HR APPLICATION-----------
      *                                                                 
      *-----------------------------------------------------------------
       IDENTIFICATION DIVISION.                                         
       PROGRAM-ID.   COBCIO20.                                          
       AUTHOR.       CAST SOFTWARE                                      
       DATE-WRITTEN. MARCH 2006.                                       
                                                                        
       EJECT                                                            
       ENVIRONMENT DIVISION.                                            
       DATA DIVISION.                                                   
                                                                        
       WORKING-STORAGE SECTION.                                         
                                                                        
       01  PACTV-MASTER-RECORD.                                      
           10 WS-PROJ-NO               PIC X(6).
           10 WS-ACT-NO                PIC S9(4) USAGE COMP.
           10 WS-ACT-STAFF             PIC S9(3)V9(2) USAGE COMP-3.
           10 WS-ACT-STARTDATE         PIC X(10).
           10 WS-ACT-ENDDATE           PIC X(10).
         
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
           
           COPY PACTVM.

      *    EXEC SQL
      *       INCLUDE PACTIVITY
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
                                                                        
           MOVE SPACES         TO  PACTVMO.                 
           MOVE 6              TO  WORK-MSG-CODE.                       
           MOVE -1             TO  PACTVMIDI.                           
           
           GO TO 300-SEND-PACTVMAP.                                          
                                                                        
      *-----------------------------------------------------------------                                                                  
      * RECEIVE MAP RECEIVES THE CICS MAP.                                                                                                   
      *-----------------------------------------------------------------                                                                  
       200-RECEIVE-PACTVMAP.                                                 

           EXEC CICS 
               RECEIVE MAP("MAP") MAPSET("PACTMAP")
               INTO(PACTVMI) 
           END-EXEC.        
                                                                                                                                                
           IF  PACTVMIDI = ZERO THEN                                    
               MOVE -1                TO  PACTVMIDI                        
               MOVE 6                 TO  WORK-MSG-CODE                    
               GO TO 300-SEND-PACTVMAP                                       
           ELSE                                                         
               IF PACTVMIDI IS NOT NUMERIC THEN                         
                  MOVE -1             TO  PACTVMIDI                     
                  MOVE 14             TO  WORK-MSG-CODE                 
                  GO TO 300-SEND-PACTVMAP                                    
               END-IF                                                   
           END-IF.                                                      
                                                                        
           PERFORM 400-READ-EMP-PACTV-DETAILS
                                                                        
           MOVE DFHBMFSE        TO  PACTVMIDA 
                                    PACTVMACTA
                                    PACTVMSDATA                               
                                    PACTVMEDATA.                              
                                                                        
      *-----------------------------------------------------------------                                                                  
      * THIS PARA SEND THE MAP TO CICS SCREEN USING THE OUTPUT COMM AREA                                                                 
      *-----------------------------------------------------------------                                                                  
       300-SEND-PACTVMAP.                                                   
           MOVE MSG(WORK-MSG-CODE) TO PACTVMMSGO.                        

           EXEC CICS 
               SEND MAP("MAP") MAPSET("PACTMAP")
               FROM(PACTVMO)                     
               CURSOR FREEKB 
           END-EXEC.                        

           GO TO 200-RECEIVE-PACTVMAP.                                       
                                                                        
      *-----------------------------------------------------------------                                                                  
      * THIS PARA FIRES THE SQL QUERY TO GET PACTV DETAILS                                                                 
      *-----------------------------------------------------------------                                                                                         
       400-READ-EMP-PACTV-DETAILS.                                             

           MOVE PACTVMIDI  TO  WS-PROJ-NO
           MOVE PACTVMACTI TO  WS-ACT-NO
      
           MOVE 1 TO CMD-CODE.
           MOVE PACTV-MASTER-RECORD TO DATA-IN.
           CALL "APITP020" USING CMD-CODE RESP-CODE DATA-IN DATA-OUT.
           IF RESP-CODE <> 0 
               MOVE DATA-OUT TO PACTV-MASTER-RECORD
           ELSE
               PERFORM 950-DBERROR THRU 950-EXIT
           END-IF
      *    EXEC SQL                                                
      *           SELECT                                                
      *             ACT_STARTDATE,
      *             ACT_ENDDATE
      *           INTO          
      *               :WS-ACT-STARTDATE,
      *               :WS-ACT-ENDDATE
      *           FROM 
      *                PACTIVITY
      *           WHERE 
      *                PROJ_NO = :WS-PROJ-NO
      *           AND  ACT_NO  = :WS-ACT-NO     
      *         END-EXEC.                                               
      *                                                                 
      *         EVALUATE SQLCODE                                        
      *             WHEN 0                                              
      *                  CONTINUE                                       
      *             WHEN OTHER                
      *                  MOVE SPACES     TO WS-ACT-STARTDATE
      *                  MOVE SPACES     TO WS-ACT-ENDDATE
      *                  PERFORM 950-DBERROR THRU 950-EXIT                           
      *         END-EVALUATE.                                           
                                                                        
           IF  RESPONSE = DFHRESP(NOTFND) THEN                          
               MOVE 7                    TO WORK-MSG-CODE               
               MOVE SPACES               TO WS-ACT-STARTDATE
               MOVE SPACES               TO WS-ACT-ENDDATE
           ELSE                                                         
               MOVE WS-ACT-STARTDATE     TO PACTVMSDATO                  
               MOVE WS-ACT-ENDDATE       TO PACTVMEDATO
               MOVE 8                    TO WORK-MSG-CODE               
           END-IF.                                                      
                                                                                                                                                                                                                        
       
       900-ERRORS.                                                      
                                                                        
           MOVE "TRANSACTION ABNORMALLY TERMINATED" TO PACTVMMSGO.       
           GO TO 999-EXIT.                                              
                                                                        
       910-EXIT.                                                        
                                                                        
           MOVE "PROCESSING COMPLETED"              TO PACTVMMSGO.       
           GO TO 999-EXIT.                                              
       
       
      *-----------------------------------------------------------------
      * 950-DBERROR - GET ERROR MESSAGE                                
      *-----------------------------------------------------------------
       950-DBERROR.                                                    
      *         CALL 'DSNTIAR' USING SQLCA ERROR-MESSAGE ERROR-TEXT-LEN.
                MOVE "PROCESSING COMPLETED WITH ERRORS"  TO PACTVMMSGO.       
                GO TO 999-EXIT.                                              
                
       950-EXIT.                                                       
                EXIT.
                

       999-EXIT.                                                        
           MOVE DFHBMASB                 TO PACTVMMSGA.                  

           EXEC CICS 
               SEND TEXT 
               FROM(PACTVMMSGO) 
               LENGTH(57)               
               ERASE 
           END-EXEC.                          

           EXEC CICS 
               RETURN 
           END-EXEC.                                   
                                                                        
      *END PROGRAM CAST.                                                