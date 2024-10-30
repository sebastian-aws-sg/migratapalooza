      *-----------------------------------------------------------------
      *    COBCIO50 - SAMPLE CICS PROGRAM
      *-----------------------------------------------------------------
      *                                                                 
      *--------------------PART OF MYTELCO HR APPLICATION-----------
      *                                                                 
      *-----------------------------------------------------------------
       IDENTIFICATION DIVISION.                                         
       PROGRAM-ID.   COBCIO50.                                          
       AUTHOR.       CAST SOFTWARE                                      
       DATE-WRITTEN. MARCH 2006.                                       
                                                                        
       EJECT                                                            
       ENVIRONMENT DIVISION.                                            
       DATA DIVISION.                                                   
                                                                        
       WORKING-STORAGE SECTION.                                         
                                                                        
           EXEC SQL 
              INCLUDE CUSTOMER                             
           END-EXEC.                                               

           EXEC SQL 
               INCLUDE SQLCA  
           END-EXEC.                       
                                                                        
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
               RECEIVE MAP("MAP") MAPSET("CUSTOMER")
               INTO(W-CUSTOMER) 
           END-EXEC.        
		   
           MOVE DATA-IN TO DCL-CUSTOMER.
           
           EXEC SQL                                                
                  SELECT                                                
                    CUST_NAME                                            
                  INTO
                    :W-CUST-NAME
                  FROM CUSTOMER                                           
                  WHERE CUST_NO = :W-CUST-NO                         
                  FETCH FIRST ROW ONLY
           END-EXEC.                                               
                                                                        
           EVALUATE SQLCODE                                        
               WHEN 0                                              
                   MOVE DCL-CUSTOMER            TO DATA-OUT                                       
                   MOVE 0                       TO RESP-CODE
               WHEN OTHER                
                   MOVE SPACES                  TO DATA-OUT                                        
                   MOVE 1                       TO RESP-CODE
           END-EVALUATE.    
		   
           EXEC CICS 
               SEND MAP("MAP") MAPSET("CUSTOMER")
               FROM(DCL-CUSTOMER)                     
               CURSOR FREEKB 
           END-EXEC.                        

