package com.dtcc.dnv.otc.common.util;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


import org.apache.log4j.Logger;
import org.apache.struts.upload.FormFile;

/**
 * @author spasrich
 *
 * A utility class for FileUpload.  It backs up the file, parses it into 
 * individual lines and dtermines how each record in the file is terminated - 
 * either by a CRLF or LF.
 */
public class UploadFileUtility
{
	/**
	 * Write the file to the local file system as a backup.
	 * 
	 * @param ArrayList - An ArrayList of Strings, each of which is a record in the file.
	 * @param String - The path to which the backup is to be written.
	 * @param String - Originator
	 * @param Logger
	 * @return void
	 * @throws - IOException
	 * @throws - FileNotFoundException 
	 */
	public static void backUpFile(ArrayList alLines, String filePath, String fileName,
											  String originator, Logger log)
																			throws IOException, FileNotFoundException
	{
		String fileNameAndPath = "";
		BufferedWriter bw = null;
		
		//Get the operating system name
		String osName = System.getProperty("os.name");
				
		//Construct the timestamp for the file name
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss-SSS");
		String timeStamp = sdf.format( new Date() );
		
		//Generate the file name and the timestamp
		String fileNameNoExtension = fileName.substring( 0, fileName.lastIndexOf(".") ).replace(' ', '_');
		
		String fileNameWithTSAndExtension = fileNameNoExtension + "_" + timeStamp + ".csv";
		
		//If it is Windows, prefix it with a c:
		if( osName.indexOf("Windows") != -1 )
			fileNameAndPath = "c:" + filePath + fileNameWithTSAndExtension;
		else
			fileNameAndPath = filePath + fileNameWithTSAndExtension;			
			
		try
		{	
			//Create the file & write the lines
			bw = new BufferedWriter( new FileWriter( new File(fileNameAndPath) ) );
			for(int i = 0; i < alLines.size(); i++)
			{
				String line = (String) alLines.get(i);
				bw.write(line);	
			}
		
			//Flush what remains in the buffer & close the file
			bw.close();	
		}
		catch(FileNotFoundException fnfe)
		{
			log.error( "FileNotFoundException in UploadFileUtility - possible path error." + 
						  "  Backup path of " + filePath + " should exist on the server.  Originator: " + 
						  originator + " at: " + timeStamp + ".  File name and path: " + 
						  fileNameAndPath );
						  
			/**Re-throw the exception so the ActionClass can catch it and add an 
			 * ActionError to ActionErrors. 
			 */		 
			throw fnfe;
		}		
		catch(IOException ioe)
		{
			//Log the error 
			log.error( "IOException in UploadFileUtility.  Originator: " + originator + 
						  " at: " + timeStamp + "File name and path: " + fileNameAndPath );
						  
			/**Re-throw the exception so the ActionClass can catch it and add an 
			 * ActionError to ActionErrors. 
			 */		 
			throw new IOException("No Log");
		}
		finally{
			try {
				if(bw!=null){
					bw.close();
				}
			} catch (IOException e) {
				log.error("IOException in UploadFileUtility. while closing the Stream",e);
			}
		}
		
	}
	
	
	
	/**
	 * Determine whether the uploaded file terminates each record with a 0x0D0A or with a 
	 * 0x0A.  In the former case call parseCRLF and in the latter case, call parseLF to
	 * correctly split the file into records.
	 * 
	 * The records are returned as an ArrayList of Strings to the function processCSV.
	 * @param FormFile - The file to be uploaded.
	 * @return ArrayList of Strings.
	 * @throws - IOException.
	 */
	public static ArrayList readAndParseFile(FormFile file)
												throws IOException
	{
		ArrayList alLines = new ArrayList();


		//Get the size of the file & get its input stream
		int fileSize = file.getFileSize();
		InputStream iStream = file.getInputStream();
		
		//Allocate a buffer of bytes with the same size as the file & read the uploaded
		//file into same.
		byte[] buffer = new byte[fileSize];
		int bytesRead = iStream.read(buffer);

		//If you read in any data
		if(bytesRead != -1)
		{
			//Determine whether the lines break on a 0x0D0A (CRLF), on a 0x0D (CR) or 
			//on a 0x0A (LF).
			//Next call the appropriate Method to split them into individual
			//lines and return them as an ArrayList of Strings.
			String format = determineFormat(buffer);
			if( format.equalsIgnoreCase("CRLF") )
				parseCRLF(buffer, alLines);
			else if( format.equalsIgnoreCase("LF") )
				parseLF(buffer, alLines);
			else
				parseCR(buffer, alLines);	
		}
		if(iStream!=null){
			iStream.close();
		}
		
		
		return alLines;
	}			
	
	
	
	/**
	 * Determine whether the lines break with a CRLF (0x0D0A), with a LF (0x0A) or
	 * with a CR (0x0D).  The latter case occurs when a one line Excel file  is 
	 * saved without the user having pressed the enter key at the end of the line.
	 * 
	 * As you read the logic below, keep in mind that a CRLF terminated file may
	 * have LFs mixed in with it.  The true end of line (and file terminator), however,
	 * is the CRLF.
	 * 
	 * @param byte array
	 * @return String - Either CRLF or LF
	 */
	private static String determineFormat(byte[] buffer)
	{
		String format = "LF";
		
		
		//Loop through the byte array looking for a 0x0D0A pair.  If found, even though 
		//there may be 0x0As in the file, the file is to be regarded as a CRLF 
		//terminated file.  This is why we do not explicitly test for 0x0A (LF) and 
		//break out of the method when one is found. 
		//
		//If the file has a 0x0D (CR) WITHOUT a 0x0A (LF) after it (see the note about 
		//the one line Excel file above) then it is to be regarded as a CR terminated 
		//file.
		//
		//If the above two conditions do not apply, then it is a LF terminated file.
		for(int i = 0; i < buffer.length; i++)
		{
			//Is it a CR?
			if( buffer[i] == 0x0D )
			{
				//If we examine the next byte, will it be within the Array's bounds 
				//AND is that next byte a LF?
				if( (i + 1) < buffer.length && buffer[i + 1] == 0x0A )
				{
					//Yes it is within bounds AND the next byte is an LF
					format = "CRLF";
					break;
				}
				else
				{
					//No it won't be within bounds OR it will be within bounds, but
					//the next byte is not an LF
					format = "CR";
					break;					
				}	
			}
		}
		
		
		return format;
	}
	
	
	
	/**
	 * Parse a file whose lines break with a CRLF (0x0D0A) and return the individual 
	 * lines as an ArrayList of Strings.
	 * 
	 * @param byte array
	 * @param ArrayList
	 * @return void
	 */ 
	private static void parseCRLF(byte[] buffer, ArrayList alLines)
	{				
		int start = 0;
		int length = 0;
		int i = 0;
		int bytesRead = buffer.length;
		
		
		while(true)
		{
			if( buffer[i] == 0x0D && buffer[i + 1] == 0x0A )
			{
				//We are at a line break.  Calculate the length of the line.
				length = i - start + 1;
					
				//Allocate a buffer for the line and copy the bytes representing the 
				//line into same.  NOTE: We copy the CR, but ignore the LF.
				byte[] bLine = new byte[length];
				System.arraycopy(buffer, start, bLine, 0, length);
				
				//Convert the bytes to a String.				
				String sLine = new String(bLine);
				
				//Before you add it to the ArrayList, make sure that it isn't a 
				//blank line
				if( !(sLine.length() == 1 && bLine[0] == 0x0D) )
					alLines.add(sLine);
				
				//Bump past the end of the line & check to see if we have exceeded the
				//limits of the array.  If so, we have finished parsing.	
				i += 2;
				if( i > (bytesRead - 1) )
					break;
			
				//Since we have not come to the end of the array, reset our starting point
				//to point to the first byte after the 0x0D0A		
				start = i;
				length = 0;
			}
			else if( ++i > (bytesRead - 1) )
				break;					
		}		
	}
		
		

	/**
	 * Parse a file whose lines break with a CR (0x0D) and return the individual 
	 * lines as an ArrayList of Strings.
	 * 
	 * This situation can occur when an Excel file is saved, as comma delimited, but 
	 * with only one line in it and without pressing enter at the end of the line.  If 
	 * you should add another line, again without pressing Enter anywhere in the file, 
	 * Excel will correct itself and terminate both the existing line and the new line 
	 * with a CRLF.
	 * 
	 * @param byte array
	 * @param ArrayList
	 * @return void
	 */ 
	private static void parseCR(byte[] buffer, ArrayList alLines)
	{				
		int start = 0;
		int length = 0;
		int i = 0;
		int bytesRead = buffer.length;
		
		
		while(true)
		{
			if( buffer[i] == 0x0D )
			{
				//We are at a line break.  Calculate the length of the line.
				length = i - start + 1;
					
				//Allocate a buffer for the line and copy the bytes representing the 
				//line into same.
				byte[] bLine = new byte[length];
				System.arraycopy(buffer, start, bLine, 0, length);

				//Convert the bytes to a String.				
				String sLine = new String(bLine);
				
				//Before you add it to the ArrayList, make sure that it isn't a 
				//blank line
				if( !(sLine.length() == 1 && bLine[0] == 0x0D) )
					alLines.add(sLine);
				
				//Bump past the end of the line & check to see if we have exceeded the
				//limits of the array.  If so, we have finished parsing.	
				i += 1;
				if( i > (bytesRead - 1) )
					break;
			
				//Since we have not come to the end of the array, reset our starting point
				//to point to the first byte after the 0x0D		
				start = i;
				length = 0;
			}
			else if( ++i > (bytesRead - 1) )
				break;					
		}		
	}



	/**
	 * Parse a file whose lines break with a LF (0x0A) and return the individual 
	 * lines as an ArrayList of Strings.
	 * 
	 * @param byte array
	 * @param ArrayList
	 * @return void
	 */
	private static void parseLF(byte[] buffer, ArrayList alLines)
	{				
		int start = 0;
		int length = 0;
		int i = 0;
		int bytesRead = buffer.length;
		
		while(true)
		{
			if( buffer[i] == 0x0A )
			{
				//We are at a line break.  Calculate the length of the line.				
				length = i - start + 1;
					
				//Allocate a buffer for the line and copy the bytes representing the 
				//line into same.
				//Convert the bytes to a String.					
				byte[] bLine = new byte[length];
				System.arraycopy(buffer, start, bLine, 0, length);
				String sLine = new String(bLine);
				
				//Before you add it to the ArrayList, make sure that it isn't a 
				//blank line				
				if( !(sLine.length() == 1 && bLine[0] == 0x0A) )
					alLines.add(sLine);
					
				//Bump past the end of the line & check to see if we have exceeded the
				//limits of the array.  If so, we have finished parsing.						
				i++;
				if( i > (bytesRead - 1) )
					break;
					
				//Since we have not come to the end of the array, reset our starting point
				//to point to the first byte after the 0x0D0A
				start = i;
				length = 0;
			}
			else if( ++i > (bytesRead - 1) )
				break;					
		}		
	}
}
