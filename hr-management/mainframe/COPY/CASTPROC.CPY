           MOVE SPACES     TO CASTMSALI.                                   
           MOVE CASTMSALI  TO CASTMSALVI.                                   
           EXEC CICS 
               SEND MAPSET("EMPMAP") MAP("MAP")
               FROM(EMPLOYEE-MASTER-RECORD)                     
               CURSOR 
               FREEKB 
           END-EXEC. 
