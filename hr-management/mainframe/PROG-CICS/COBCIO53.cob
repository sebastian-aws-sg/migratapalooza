      *-----------------------------------------------------------------
      *    COBCIO53 - SAMPLE CICS PROGRAM
      *-----------------------------------------------------------------
      *                                                                 
      *--------------------PART OF MYTELCO HR APPLICATION-----------
      *                                                                 
      *-----------------------------------------------------------------
       IDENTIFICATION DIVISION.                                         
       PROGRAM-ID.   COBCIO53.                                          
       AUTHOR.       CAST SOFTWARE                                      
       DATE-WRITTEN. MARCH 2006.                                       
                                                                        
       EJECT                                                            
       ENVIRONMENT DIVISION.                                            
       DATA DIVISION.                                                   
                                                                        
       WORKING-STORAGE SECTION.                                         
                                                                        
           EXEC SQL 
              INCLUDE ACCOUNT                             
           END-EXEC.                                               

           EXEC SQL 
               INCLUDE SQLCA  
           END-EXEC.                       

       01 CMD-CODE  PIC 99.
       01 RESP-CODE PIC 99.
       01 DATA-IN   PIC X(512).
       01 DATA-OUT  PIC X(2048)
		   
       LINKAGE SECTION.
       01 DFHCOMMAREA  PIC X(2048)
      *-----------------------------------------------------------------
      * PROCEDURE DIVISION.
      *-----------------------------------------------------------------
       PROCEDURE DIVISION USING DFHCOMMAREA.               
      * MAIN PROCESS
       MAIN.
           PERFORM CMD-01
           GOBACK.           
       CMD-01.
           EXEC CICS 
               RECEIVE MAP("MAP") MAPSET("ACCOUNT")
               INTO(W-ACCOUNT) 
           END-EXEC.        
		   
           MOVE DATA-IN TO DCL-ACCOUNT.

           MOVE 1          TO CMD-CODE.
           MOVE 0          TO RESP-CODE.
           MOVE W-ACCOUNT  TO DATA-IN.
           
      *     CALL "APIACC" USING CMD-CODE RESP-CODE DATA-IN DATA-OUT.
       COPY CPAPIACC.
           
           MOVE DATA-OUT TO W-ACCOUNT.
           
      *     EXEC SQL                                                
      *            SELECT                                                
      *              ACC_TOTAL                                            
      *            INTO
      *              :W-ACC-TOTAL
      *            FROM ACCOUNT                                           
      *            WHERE CUST_NO = :W-CUST-NO AND ACC_NO = :W-ACC-NO                        
      *            FETCH FIRST ROW ONLY
      *     END-EXEC.                                               
      *                                                                  
      *     EVALUATE SQLCODE                                        
      *         WHEN 0                                              
      *             MOVE DCL-ACCOUNT             TO DATA-OUT                                       
      *             MOVE 0                       TO RESP-CODE
      *         WHEN OTHER                
      *             MOVE SPACES                  TO DATA-OUT                                        
      *             MOVE 1                       TO RESP-CODE
      *     END-EVALUATE.                                           

           EXEC CICS 
               SEND MAP("MAP") MAPSET("ACCOUNT")
               FROM(DCL-ACCOUNT)                     
               CURSOR FREEKB 
           END-EXEC.                        

