      *-----------------------------------------------------------------
      *  APITPO17 - TECHNICAL PROGRAM TO ACCESS DATA NEEDED BY COBCIO17.
      *-----------------------------------------------------------------
      *                                                                 
      *--------------------PART OF MYTELCO HR APPLICATION-----------
      *                                                                 
      *-----------------------------------------------------------------
       IDENTIFICATION DIVISION.                                         
       PROGRAM-ID.   APITP017.                                          
       AUTHOR.       CAST SOFTWARE                                      
       DATE-WRITTEN. FEBRUARY 2012.                                       
                                                                        
       EJECT                                                            
       ENVIRONMENT DIVISION.                                            
       DATA DIVISION.                                                   
                                                                        
       WORKING-STORAGE SECTION.                                         
                                                                        
           COPY DFHBMSCA.                                               
                                                                        
           COPY DFHAID.  
           
           COPY HARDWRM.

           EXEC SQL
               INCLUDE HARDWARE
           END-EXEC.

           EXEC SQL 
               INCLUDE SQLCA  
           END-EXEC.                       
                                                                        
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

       LINKAGE SECTION.
       01 CMD-CODE  PIC 99.
       01 RESP-CODE PIC 99.
       01 DATA-IN   PIC X(512).
       01 DATA-OUT  PIC X(2048)
      *-----------------------------------------------------------------
      * PROCEDURE DIVISION.
      *-----------------------------------------------------------------
       PROCEDURE DIVISION USING CMD-CODE RESP-CODE DATA-IN DATA-OUT.               
      * MAIN PROCESS
       MAIN.
           EVALUATE CMD-CODE
               WHEN 1
                   PERFORM CMD-01
               WHEN OTHER
                   MOVE 99 TO RESP-CODE
           END-EVALUATE.
           GOBACK.           
       CMD-01.
           MOVE DATA-IN TO HARDWARE-MASTER-RECORD.
           
           EXEC SQL                                                
                  SELECT                                                
                      CPU_INS_VALUE                              
                  INTO          
                      :WS-CPU-INS-VALUE
                  FROM 
                       HARDWARE
                  WHERE 
                       CPU_ID       = :WS-CPU-ID
                   AND HARDWARE_ID  = :WS-HARDWARE-ID
           END-EXEC.                                               
                                                                        
           EVALUATE SQLCODE                                        
               WHEN 0                                              
                   MOVE HARDWARE-MASTER-RECORD  TO DATA-OUT                                       
                   MOVE 0                       TO RESP-CODE
               WHEN OTHER                
                   MOVE SPACES                  TO DATA-OUT                                        
                   MOVE 1                       TO RESP-CODE
           END-EVALUATE.                                           
