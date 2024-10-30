      *-----------------------------------------------------------------
      *    COBCIOP12 - DUMMY PROGRAM TO ACCESS ACTIVITY DETAILS AND STORE. 
      *               IN THE MESSAGE QUEUE WHICH CAN BE ACCESSED BY 
      *               THE JAVA PROGRAM FROM MESSAGE QUEUE USING JCICS 
      *               CONNECTOR AND JAVA TO CICS GATEWAY.
      *-----------------------------------------------------------------
      *                                                                 
      *--------------------PART OF MYTELCO HR APPLICATION-----------
      *                                                                 
      *-----------------------------------------------------------------
       IDENTIFICATION DIVISION.                                         
       PROGRAM-ID.   COBCIO12.                                          
       AUTHOR.       CAST SOFTWARE                                      
       DATE-WRITTEN. AUGUST 2005.                                       
                                                                        
       EJECT                                                            
       ENVIRONMENT DIVISION.                                            
       DATA DIVISION.                                                   
                                                                        
       WORKING-STORAGE SECTION.                                         
                                                                        
       01 DFHCOMMAREA.
           03 RETURN-FLAG                   PIC X(1).
           03 FILLER-1                      PIC X(72)
       01 EMPLOYEE-INFO REDEFINES DFHCOMMAREA.
           03 FILLER-2                      PIC X(1).
           03 EMPLOYEE-RECORD.                                             
             05 EMP-ID                      PIC X(6).                    
             05 EMP-DEPT-CODE               PIC X(3).                    
             05 EMP-NAME                    PIC X(30).                   
             05 EMP-JOB                     PIC X(8).                    
             05 EMP-BIRTHDATE               PIC X(8).                    
           03 CURRENT-DATETIME.
             05 DATE-AREA                   PIC X(8).
             05 FILLER-3                    PIC X(1) VALUE ":".
             05 TIME-AREA                   PIC X(8).

       01  ADD-REC-FLAG                    PIC X VALUE SPACES.          

           EJECT                                                        
      *-----------------------------------------------------------------                                                                  
      * PROCEDURE DIVISION.
      *-----------------------------------------------------------------                                                                  
       PROCEDURE DIVISION.                                              

      *-----------------------------------------------------------------                                                                  
      *    the instructions below handle cics error conditions.     
      *-----------------------------------------------------------------                                                                  
                                                                        
           EXEC CICS IGNORE CONDITION LENGERR END-EXEC.                 
           EXEC CICS HANDLE CONDITION ERROR(900-ERRORS) END-EXEC.       
           PERFORM 300-GET-CURRENT-DATETIME 
              THRU 300-EXIT.
           PERFORM 400-READ-EMP-RECORD 
              THRU 400-EXIT.
           STOP RUN.
                                                                        
      *-----------------------------------------------------------------                                                                  
      * THIS PARA GETS CURRENT DATE TIME FROM CICS ENVIRONMENT                                                                    
      * AND POPULATES DFHCOMMAREA WITH CURRENT DATETIME.                                                                          
      *-----------------------------------------------------------------                                                                  
       300-GET-CURRENT-DATETIME.                                             
      *    EXEC CICS ASKTIME ABSTIME(ABSTIME)
      *              END-EXEC.

      *    EXEC CICS FORMATTIME
      *              ABSTIME(ABSTIME)
      *              DDMMYY(DATE-AREA)
      *              DATESEP("/")
      *              TIME(TIME-AREA)
      *              TIMESEP(":")
      *              END-EXEC.
           ACCEPT DATE-AREA FROM DATE.
      *-----------------------------------------------------------------                                                                  
      * EXIT PARA.                                                                                                                          
      *-----------------------------------------------------------------                                                                  
       300-EXIT.                                             
           EXIT.

      *-----------------------------------------------------------------                                                                  
      * THIS PARA MAKES A CALL TO COBOL PROGRAM TO GET EMPLOYEE RECORDS.                                                                    
      *-----------------------------------------------------------------                                                                  
       400-READ-EMP-RECORD.                                             
           MOVE "N"        TO ADD-REC-FLAG                              
           CALL "COBEMPDP" USING EMPLOYEE-RECORD.                       
           MOVE "Y"        TO RETURN-FLAG.
           PERFORM 500-RETURN-EMP-INFO 
              THRU 500-EXIT.
      *-----------------------------------------------------------------                                                                  
      * EXIT PARA.                                                                                                                          
      *-----------------------------------------------------------------                                                                  
       400-EXIT.                                             
           EXIT.

      *-----------------------------------------------------------------                                                                  
      * THIS PARA POPULATES THE QUEUE WITH RETURN DATA.              .                                                                    
      *-----------------------------------------------------------------                                                                  
       500-RETURN-EMP-INFO.                                                        

           EXEC CICS WRITEQ TD
                     QUEUE("CSMT")
                     FROM(DFHCOMMAREA)
                     LENGTH(73)
                     RESP(RESP)
                     END-EXEC.

           EXEC CICS RETURN
                     END-EXEC.

      *-----------------------------------------------------------------                                                                  
      * EXIT PARA.                                                                                                                          
      *-----------------------------------------------------------------                                                                  
       500-EXIT.
           EXIT.
           
      *-----------------------------------------------------------------                                                                  
      * PARA FOR UNEXPECTED ERRORS.                                                                                                         
      *-----------------------------------------------------------------                                                                  
       900-ERRORS.                                                      
           MOVE "N"        TO RETURN-FLAG.
           MOVE "TRANSACTION ABNORMALLY TERMINATED" 
                           TO DFHCOMMAREA.       
           PERFORM 500-RETURN-EMP-INFO 
              THRU 500-EXIT.                             
      *-----------------------------------------------------------------                                                                  
      * EXIT PARA.                                                                                                                          
      *-----------------------------------------------------------------                                                                  
       900-EXIT.                                             
           EXIT.
                                                                        
                                                                        
      *END PROGRAM CAST.                                                                                                                        

