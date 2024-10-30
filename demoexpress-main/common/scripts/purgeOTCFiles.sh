#!/usr/bin/ksh 
# ------------------------------------------------------------------------
#
# Program:      /opt/netscape/dsv/scripts/purgeOTCFiles.sh
#
#
# Description:  This script will remove files from a specified directory.
#		You have to specify the directory name and the days old to 
#		remove.  You have to modify the code at the botton of the
#		script to add/remove functionality.  Here is a line example 
#		for a particular directory being purged:
#		
#		Function name   Directory                  					Number of days old
#		-------------   ---------                  					------------------
#		purge_old_files /opt/netscape/dsv/uploads 					5
#			
#               ie. syntax
#               $ /opt/netscape/dsv/scripts/purgeOTCFiles.sh >> /opt/netscape/logs/dsv/purgeOTCFiles.log
#
#		NOTE: 
#		Status information gets directed to standard out.  You 
#		might want to consider redirecting output to a log file.
#		Please refer to the syntax example above.
#
#		Return Codes:
#		0	Successful
#		1	Error
#		2	Warnings
#
# Edit History: 11/11/2002 S. Casazza
#               Inception of this script.
#
#				10/02/2003 	Rev 1.0		S. Velez
#				Updated Comments
#
#				01/27/2004	Rev 1.1		S. Velez
#				Added purge for otcp uploads
#
#				02/12/2004 	Rev 1.2		S. Velez
#				Added purge for otcd log files
#
#				10/05/2004	Rev 1.0		S. Velez
#				Moved from otcd project to DerivSERV/common
#
#				11/19/2004	Rev 1.1		S. Velez
#				Added comments, and added purging of DPR logs files.
#
# ------------------------------------------------------------------------

export RC=0
#
# output a formatted message.
# Usage:
#       Arg1:   INFO | WARNING | ERROR
#       Argn:   "The message"
#
# Examples:
#       log_msg INFO "The program ran successfuly.
#       log_msg WARNING "People from New Jersey are strange."
#       log_msg ERROR "You hired a jerk for a Director."
#
log_msg() {
  export ARG1="$1"
  shift
  echo "${ARG1}...(`date '+%Y%m%d %H%M%S'` ${LOGNAME}@`uname -n` $*"
}


#
# Function: purge_old_files() - This function is passwd two parameters
# that specify the directory that we'll remove from from and the second
# parameter specifies the number of days old to remove files.
# 
# Examples:
#	purge_old_files /opt/netscape/ipsx/export/backup 2
#	purge_old_files /export/home/scasazz/logs 10
#
purge_old_files () {
  # Check to see if the directory exists.
  #
  if [ ! -d ${1} ]
  then
     log_msg ERROR "Directory \"$1\" doesn't exist!" 
     exit 1
  fi

  # Check if function parameters are passed correctly.
  #
  if [ -z "$1" ]
  then
     log_msg ERROR "Problem with the first parameter of the function call defintion." 
     exit 1
  fi
  if [ -z "$2" ]
  then
     log_msg ERROR "Problem with the second parameter of the function call defintion." 
     exit 1
  fi

  find $1 -type f -mtime +$2 -print 2>&1 | while read line
  do
    if [[ -z $(fuser $file_name 2>/dev/null) ]]
    then
######LINE IS FOR TESTING PURPOSES######      ls -l $line 
      rm "$line"
      if [ "$?" -eq 0 ]
      then
        log_msg INFO "Removed file \"${line}\" ."
      else
        log_msg ERROR "Problem removing file \"${line}\" ."
        RC=1
      fi
    else
        log_msg WARNING "File \"${line}\" might be currently opened.  Please investigate."
        RC=2
    fi 
  done
}

#
# main() - This is the main section of the script.
#
log_msg INFO "Starting script `basename $0`."

# Purge for DSV
purge_old_files /opt/netscape/dsv/uploads 5
purge_old_files /opt/netscape/logs/dsv 5

# Purge for DPR
purge_old_files /opt/netscape/dpr/uploads 2
purge_old_files /opt/netscape/logs/dpr 5

log_msg INFO "Finished script `basename $0`."
exit $RC
