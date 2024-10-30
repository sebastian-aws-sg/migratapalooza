package com.dtcc.dnv.mcx.decorator;

import java.util.Date;

import org.displaytag.decorator.TableDecorator;
import org.displaytag.model.Row;
import org.displaytag.model.RowIterator;
import org.displaytag.model.TableModel;
import org.displaytag.render.TableWriterTemplate;
import org.displaytag.util.TagConstants;

import com.dtcc.dnv.mcx.beans.TemplateDocsBean;
import com.dtcc.dnv.mcx.util.MCXConstants;

/**
 * 
 * The decarator used for the display tag used for grouping and displaying the
 * links.
 * 
 * RTM Reference : 3.3.16.16 The “MCA name” should not have a link if it is just
 * a placeholder and there is no document uploaded for a pre-existing MCA RTM
 * Reference : 3.3.16.12 The document library tab under the “search documents”
 * should display the file name if the documents uploaded is anything other than
 * pre-executed MCA RTM Reference : 3.3.16.13 The document library tab under the
 * “search documents” should display the MCA name if the documents uploaded is a
 * pre-executed MCA
 * 
 * 
 * Copyright © 2003 The Depository Trust & Clearing Company. All rights
 * reserved.
 * 
 * Depository Trust & Clearing Corporation (DTCC) 55, Water Street, New York, NY
 * 10048, U.S.A All Rights Reserved.
 * 
 * This software may contain (in part or full) confidential/proprietary
 * information of DTCC. ("Confidential Information"). Disclosure of such
 * Confidential Information is prohibited and should be used only for its
 * intended purpose in accordance with rules and regulations of DTCC.
 * 
 * @author
 * @date Sep 7, 2007
 * @version 1.0
 * 
 *  
 */
public class MCXTableDecorator extends TableDecorator
{

    //  Boolean used to decide whether the row has to be displayed or hidden.
    private static boolean isDisplay;

    //Boolean used to indicate the start of group to display the "+" symbol.
    private static boolean isGroup;

    //Boolean used for setting the rowid.
    private boolean onlyEndGrp = false;

    //Boolean used for setting the rowid.
    private boolean onlyStrtGrp = false;

    //Style classes used
    private final String EVENROWCLASS = "evenRow";
    private final String ODDROWCLASS = "oddRow";
    private final String HIDDENCLASS = "hide";
    
    //Boolean used to set the Style class for hidden rows.
    private String rowClass = EVENROWCLASS;

    private int rowIndex = 1;
    private final String HIDDEN = "<input type=\"hidden\" value=\"";
    private final String CLOSETAG = "\"/>";
    private final String SPACE = "&nbsp;";
    private final String COUNTERPARTYNAMELINK = "<a class=\"linkDealer\" href=\"javascript:popUpWindow('";
    private final String MCANAMELINK = "<a class=\"linkDealer\" style=\"padding: 0px 0px 0px 24px\" href=\"javascript:downloadMCADoc('";
    private final String MCXPARAMETER = "','/mcx')\">";
    private final String AENDTAG = "</a>";
    private final String GROUPSYMBOL = "<img id='dealer' src='/mcx/static/images/tree/ftv2plastnode_new.gif' style='cursor:hand' onclick='showhide(this)' index= '";
    private final String GROUPSTYLE = "' style='float:left'/>";
    private final String LOCKIMAGE = "<img border=\"0\" align=\"absmiddle\" id='lock3' src='/mcx/static/images/lock.gif' alt='This document is locked by ";
    private final String UNLOCKIMAGE = "<img border=\"0\" align=\"absmiddle\" id='lock4' src='/mcx/static/images/unlock.gif' alt='This document is locked by ";
    private final String ADMINLOCKIMAGE = "<img border=\"0\" align=\"absmiddle\" id='lock3' src='/mcx/static/images/lock.gif' alt='This template is locked by ";
    private final String ADMINUNLOCKIMAGE = "<img border=\"0\" align=\"absmiddle\" id='lock4' src='/mcx/static/images/unlock.gif' alt='This template is locked by ";
    private final String IMAGECLOSETAG = "'/>";
    private final String ACTIONINPUT = "<input class='approve' type= 'button' value='APPROVE' onclick=\"javascript:openWindow('";
    private final String SEPARATOR = "','";
    private final String approve = "APPROVE";
    private final String deny = "DENY";
    private final String denyActionInput = "<input class='deny' type= 'button' value='DENY' onclick=\"javascript:openWindow('";
    private final String statusMSG = "<a class=\"linkDealer\" href=\"/mcx/action/displayDealer?Dealer=";
    private final String ADMINSTATUS = "<a class=\"linkDealer\" href=\"/mcx/action/ViewAdminISDATemplate?tmpltId=";
    private final String TEMPLATEFIRSTSTRING = "<a class=\"linkDealer\" style=\"padding: 0px 0px 0px 24px\" href=\"/mcx/action/ViewMCA?tmpltId=";
    private final String TEMPLATESECONDSTRING = "&frmScr=NEG&catgyId=&sltInd=TE&viewInd=F\">";
    private final String USERDETAILSPOPUPLINK = "<a class=\"linkDealer\" href=\"javascript:userDetailsPopUp('";
    private final String USERDETAILSPOPUPCLOSE = "')\">";
    private final String MCASETUPFIRSTSTRING = "<a class=\"linkDealer\" href=\"/mcx/action/ViewMCA?tmpltId=";
    private final String MCASETUPCPID = "&cltCd=";
    private final String MCASETUPCPNAME = "&cltNm=";
    private final String MCASETUPSECONDSTRING = "&frmScr=MW1&sltInd=EA&viewInd=F&catgyId=\">";
    private final String MCATEMPLATEALIGNED = "<a class=\"linkMCA\" style=\"padding: 0px 0px 0px 19px;text-decoration:none\" title=\"";
    private final String MCATMPLTEALIGN = "<a class=\"linkMCA\" style=\"padding: 0px 0px 0px 22px;text-decoration:none\" title=\"";
    private final String MCATMPLTALIGNEND = "\">";
    private final String TEMPLATEGROUPFIRSTSTRING = "<a class=\"linkDealer\" style=\"padding: 0px 0px 0px 24px\" href=\"/mcx/action/ViewMCA?tmpltId=";
    private final String MCAGROUPNAMELINK = "<a  class=\"linkDealer\" style=\"padding: 0px 0px 0px 24px;\" href=\"javascript:downloadMCADoc('";
    private final String PREEXISTINGGROUPFILENAME = "<a class=\"linkMCA\" style=\"padding: 0px 0px 0px 24px;text-decoration:none\" title=\"";
    private final String PREEXISTINGFILENAME = "<a class=\"linkMCA\" style=\"padding: 0px 0px 0px 24px;text-decoration:none\" title=\"";
    private final String TEMPLATEFIRSTSTRINGNOPADDING = "<a class=\"linkDealer\"  href=\"/mcx/action/ViewMCA?tmpltId=";
    private final String MANAGEDOCSPREEXISTINGGROUPFILENAME = "<a class=\"linkMCA\" style=\"padding: 0px 0px 0px 22px;text-decoration:none;cursor:default\" href=\"#\" />";
    private final String MANAGEDOCSPREEXISTINGFILENAME = "<a class=\"linkMCA\" style=\"padding: 0px 0px 0px 24px;text-decoration:none;cursor:default\" title=\"";
    private final String MANAGEDOCSMCANAMELINK = "<a class=\"linkDealer\" style=\"padding: 0px 0px 0px 24px\" href=\"javascript:downloadMCADoc('";
    

    /**
     * Constructor
     */
    public MCXTableDecorator()
    {
        super();
        isDisplay = true;

    }

   /*
    * This function is used for displaying the counterparty name as a link
    * 
    */

    public String getOrgDlrNm()
    {

        String dealerName = ((TemplateDocsBean) this.getCurrentRowObject()).getOrgDlrNm();
        String dealerCode = ((TemplateDocsBean) this.getCurrentRowObject()).getOrgDlrCd();
        String docType = ((TemplateDocsBean) this.getCurrentRowObject()).getTmpltTyp();

        String ahref = HIDDEN + dealerName + CLOSETAG;

        String PREEXISTING = "Pre-Existing";
        String Y = "Y";

        String mcxRegisteredIndicator = ((TemplateDocsBean) this.getCurrentRowObject()).getMcaRegisteredIndicator();

        dealerName = dealerName.replaceAll(" ", SPACE);
        
        
        /*
         * This loop is for generating link only for MCXRegistered users.
         * For NonMCXRegistered users the counterparty name will not appear as a link.
         */

        if (PREEXISTING.equalsIgnoreCase(docType) || MCXConstants.PREEXISTINGDISP.equals(docType) || MCXConstants.OTHERS.equals(docType))
        {

            if (Y.equalsIgnoreCase(mcxRegisteredIndicator) || MCXConstants.MCAFIRM.equals(mcxRegisteredIndicator))
            {

                ahref = ahref + COUNTERPARTYNAMELINK + dealerCode + MCXPARAMETER + dealerName + AENDTAG;
            } else
            {
                
                ahref = ahref + dealerName+MCXConstants.MCAINDICATOR;

            }
        } else
        {
            ahref = ahref + COUNTERPARTYNAMELINK + dealerCode + MCXPARAMETER + dealerName + AENDTAG;
        }

        return ahref;
    }

    /*
     * This function is used for grouping of template name based on counterparty name.
     * This function is used for Pending MCAs and Approved firms screen.
     * 
     */

    public String getTmpltNm()
    {
        String PREEXISTING = "PreExisting";
        String TRUE = "true";

        /*
         * "this.getCurrentRowObject()" gives the current row object. TypeCasted
         * to the Bean to get the required property value.
         */

        String tempName = ((TemplateDocsBean) this.getCurrentRowObject()).getTmpltNm();
        String tempLongName = ((TemplateDocsBean) this.getCurrentRowObject()).getTmpltLngNm();
        tempLongName = tempLongName.replaceAll("\"","&quot;");

        String ahref = HIDDEN + tempName + CLOSETAG;

        String firmName = ((TemplateDocsBean) this.getCurrentRowObject()).getOrgDlrNm();
        String firmId = ((TemplateDocsBean) this.getCurrentRowObject()).getOrgDlrCd();

        /*
         * Table model contains the list which is displayed on the UI. Used to
         * get the previous and next row column values .
         */
        
        /*
         * This function will be called only when we sort on the first column
         * i.e. getSortedColumnNumber = 0
         */
        if (this.tableModel.getSortedColumnNumber() == 0)
        {

            TableModel model = this.tableModel;

            /*
             * RowIterator gets the list displayed on the UI. By iterating
             * through the iterator we can access each row object.
             *  
             */
            RowIterator rowIterator = this.tableModel.getRowIterator(false);

            int rowListSize = model.getRowListFull().size();
            //Row objects.
            Row previousRow = null;

            Row nextRow = null;

            String nextRowName = null;
            String nextRowId = null;

            String previousRowName = null;
            String previousRowId = null;

            //"this.getListIndex()" gives the sorted list index.
            int index = this.getListIndex();

            // Iterating till the previous row to get previous row values.
            for (int i = 0; i < index - 1; i++)
            {
                rowIterator.next();
            }
            if (index > 0)
            {
                //Getting the previous row.
                previousRow = rowIterator.next();
                previousRowName = ((TemplateDocsBean) previousRow.getObject()).getOrgDlrNm();
                previousRowId = ((TemplateDocsBean) previousRow.getObject()).getOrgDlrCd();

            }

            // to get the next row.
            rowIterator.next();

            if (rowIterator.hasNext())
            {
                // getting the next row.
                nextRow = rowIterator.next();
                nextRowName = ((TemplateDocsBean) nextRow.getObject()).getOrgDlrNm();
                nextRowId = ((TemplateDocsBean) nextRow.getObject()).getOrgDlrCd();

            }

            // To come to the current row again as the object goes to next row

            rowIterator = this.tableModel.getRowIterator(false);
            index = this.getListIndex();

            if (rowIndex < rowListSize)
            {
                for (int i = 0; i < index - 1; i++)
                {
                    rowIterator.next();
                }
                if (index > 0)
                {
                    //Getting the previous row.
                    previousRow = rowIterator.next();
                }
            }
            rowIndex++;
            /*
             * Setting the boolean value to true if next dealername is same and
             * previous dealer name is different to place the "+" symbol.
             */
            if (firmId.equals(nextRowId))
            {
                if (!firmId.equals(previousRowId))
                {
                    isGroup = true;
                } else
                {
                    isGroup = false;
                }

            } else
            {
                isGroup = false;
            }

            /*
             * Placing the symbol along with the necessary call to java script
             * to show the hidden rows when clicked on the link.
             */

            if (isGroup & this.tableModel.getSortedColumnNumber() == 0)
            {
               
                return GROUPSYMBOL + index + GROUPSTYLE + MCATEMPLATEALIGNED + tempLongName + MCATMPLTALIGNEND + tempName + AENDTAG;

            } else
            {
                return MCATMPLTEALIGN + tempLongName + MCATMPLTALIGNEND + tempName + AENDTAG;
            }
        } else
        {
            return MCATMPLTEALIGN + tempLongName + MCATMPLTALIGNEND + tempName + AENDTAG;
        }
    }


    /*
     * Wrapping the getMcaTempName() of DealerClientList bean to include "+" at
     * start of group when sorted on dealer name.
     */

    public String getMcaTempName()
    {
        String documentType = "";
        final String SEPERATOR = "','";
        final String ACLOSE = "')\" title=\"";
        final String ACLOSEOTHERS = "')\">";
        /*
         * "this.getCurrentRowObject()" gives the current row object. TypeCasted
         * to the Bean to get the required property value.
         */
        String fileName = ((TemplateDocsBean) this.getCurrentRowObject()).getDocName();
        String tempName = ((TemplateDocsBean) this.getCurrentRowObject()).getTmpltShrtNm().trim();
        String tempLongName = ((TemplateDocsBean) this.getCurrentRowObject()).getTmpltNm().trim();
        tempLongName = tempLongName.replaceAll("\"","&quot;");
        String docType = ((TemplateDocsBean) this.getCurrentRowObject()).getTmpltTyp();
        if (MCXConstants.PREEXISTINGDISP.equals(docType))
        {
            fileName = tempName;
            documentType = MCXConstants.PREEXISTINGTAB;
        } else if (MCXConstants.OTHERS.equals(docType))
        {
            documentType = MCXConstants.OTHERTAB;
        }

        String firmId = ((TemplateDocsBean) this.getCurrentRowObject()).getOrgDlrCd();
        String docName = ((TemplateDocsBean) this.getCurrentRowObject()).getDocName();
        String documentId = ((TemplateDocsBean) this.getCurrentRowObject()).getDocId();

        String ahref = HIDDEN + fileName + CLOSETAG;

        /*if (docName.trim().length() != 0)
        {
            fileName = ahref + MCANAMELINK + documentId + SEPERATOR + documentType + ACLOSE + fileName + AENDTAG;
        } else
        {

            fileName = ahref + SPACE + fileName;
        }*/

        /*
         * Table model contains the list which is displayed on the UI. Used to
         * get the previous and next row column values .
         */
        
        

        if (this.tableModel.getSortedColumnNumber() == 0)
        {

            TableModel model = this.tableModel;

            /*
             * RowIterator gets the list displayed on the UI. By iterating
             * through the iterator we can access each row object.
             *  
             */
            RowIterator rowIterator = this.tableModel.getRowIterator(false);

            //Row objects.
            Row previousRow = null;

            Row nextRow = null;

            String nextRowId = null;

            String previousRowId = null;

            int rowListSize = model.getRowListFull().size();

            //"this.getListIndex()" gives the sorted list index.
            int index = this.getListIndex();

            // Iterating till the previous row to get previous row values.
            for (int i = 0; i < index - 1; i++)
            {
                rowIterator.next();
            }
            if (index > 0)
            {
                //Getting the previous row.
                previousRow = rowIterator.next();
                previousRowId = ((TemplateDocsBean) previousRow.getObject()).getOrgDlrCd();

            }

            // to get the next row.
            rowIterator.next();

            if (rowIterator.hasNext())
            {
                // getting the next row.
                nextRow = rowIterator.next();
                nextRowId = ((TemplateDocsBean) nextRow.getObject()).getOrgDlrCd();

            }

            //To come to the current row again as the object goes to next row
            rowIterator = this.tableModel.getRowIterator(false);
            index = this.getListIndex();

            if (rowIndex < rowListSize)
            {
                for (int i = 0; i < index - 1; i++)
                {
                    rowIterator.next();
                }
                if (index > 0)
                {
                    //Getting the previous row.
                    previousRow = rowIterator.next();
                }
            }
            rowIndex++;

            /*
             * Setting the boolean value to true if next dealername is same and
             * previous dealer name is different to place the "+" symbol.
             */

            if (firmId.equals(nextRowId))
            {
                if (!firmId.equals(previousRowId))
                {
                    isGroup = true;
                } else
                {
                    isGroup = false;
                }

            } else
            {
                isGroup = false;
            }

            /*
             * Placing the symbol along with the necessary call to java script
             * to show the hidden rows when clicked on the link.
             */

            if (isGroup & this.tableModel.getSortedColumnNumber() == 0)
            {
                if (MCXConstants.PREEXISTINGDISP.equalsIgnoreCase(docType))
                {
                    if (docName.trim().length() == 0)
                    {

                        //ahref = ahref + MANAGEDOCSPREEXISTINGGROUPFILENAME +
                        // fileName + AENDTAG;
                        ahref = ahref + PREEXISTINGGROUPFILENAME + tempLongName + MCATMPLTALIGNEND + fileName + AENDTAG;
                    } else
                    {
                        //ahref = ahref + MCAGROUPNAMELINK + documentId +
                        // SEPERATOR + documentType + ACLOSE + fileName +
                        // AENDTAG;
                        ahref = ahref + MCANAMELINK + documentId + SEPERATOR + documentType + SEPERATOR +firmId + ACLOSE + tempLongName + MCATMPLTALIGNEND + fileName + AENDTAG;
                    }
                } else
                {
                    ahref = ahref + MCAGROUPNAMELINK + documentId + SEPERATOR + documentType + SEPERATOR +firmId + ACLOSEOTHERS + fileName + AENDTAG;
                }

                return GROUPSYMBOL + index + GROUPSTYLE + ahref;

            } else
            {
                if (MCXConstants.PREEXISTINGDISP.equalsIgnoreCase(docType))
                {
                    if (docName.trim().length() == 0)
                    {
                        //ahref = ahref + MANAGEDOCSPREEXISTINGFILENAME + fileName + AENDTAG;
                        ahref = ahref + PREEXISTINGFILENAME + tempLongName + MCATMPLTALIGNEND + fileName + AENDTAG;
                        
                    } else
                    {
                        //ahref = ahref + MANAGEDOCSMCANAMELINK + documentId + SEPERATOR + documentType + ACLOSE +  fileName + AENDTAG;
                        ahref = ahref + MCANAMELINK + documentId + SEPERATOR + documentType + SEPERATOR +firmId + ACLOSE + tempLongName + MCATMPLTALIGNEND + fileName + AENDTAG;
                    }
                }else
                {
                    ahref = ahref + MANAGEDOCSMCANAMELINK + documentId + SEPERATOR + documentType + SEPERATOR +firmId + ACLOSEOTHERS +  fileName + AENDTAG;
                } 
                
               

                return ahref;
            }
        } else
        {
            if (MCXConstants.PREEXISTINGDISP.equalsIgnoreCase(docType))
            {
                if (docName.trim().length() == 0)
                {
                    //ahref = ahref + MANAGEDOCSPREEXISTINGFILENAME +
                    // tempLongName + MCATMPLTALIGNEND + fileName + AENDTAG;
                    ahref = ahref + PREEXISTINGFILENAME + tempLongName + MCATMPLTALIGNEND + fileName + AENDTAG;
                } else
                {
                    //ahref = ahref + MANAGEDOCSMCANAMELINK + documentId +
                    // SEPERATOR + documentType + ACLOSE + fileName +
                    // MCATMPLTALIGNEND + fileName + AENDTAG;
                    ahref = ahref + MCANAMELINK + documentId + SEPERATOR + documentType + SEPERATOR + firmId + ACLOSE + tempLongName + MCATMPLTALIGNEND + fileName + AENDTAG;
                }
            } else
            {
                ahref = ahref + MANAGEDOCSMCANAMELINK + documentId + SEPERATOR + documentType + SEPERATOR +firmId + ACLOSEOTHERS + fileName + AENDTAG;
            }

            return ahref;
        }
    }

    /*
     * Over riding the "org.displaytag.decorator.TableDecorator#
     * displayGroupedValue(java.lang.String, short, int)" function used to get
     * the value to be displayed when sorting is used.
     */

    public String displayGroupedValue(String cellValue, short groupingStatus, int columnNumber)
    {

        if ((groupingStatus == TableWriterTemplate.GROUP_END || groupingStatus == TableWriterTemplate.GROUP_NO_CHANGE) & this.tableModel.getSortedColumnNumber() == 0)
        {

            isDisplay = false;
            return TagConstants.EMPTY_STRING;
        } else
        {

            isDisplay = true;
            return cellValue;
        }
    }

    /*
     * This function is called when grouping is present and at start of group.
     * Overriding "org.displaytag.decorator.TableDecorator#
     * startOfGroup(java.lang.String, int)"
     */

    public void startOfGroup(String value, int group)
    {

        onlyStrtGrp = true;

    }

    /*
     * This function is called when grouping is present and at end of group.
     * OverRiding "org.displaytag.decorator.TableDecorator#
     * endOfGroup(java.lang.String, int)"
     */

    public void endOfGroup(String value, int groupThatHasEnded)
    {

        onlyEndGrp = true;

    }

    /*
     * To specify a style class Overriding
     * org.displaytag.decorator.TableDecorator#addRowClass()
     */

    public String addRowClass()
    {
        String className;
        if (!isDisplay)
        {
            className = HIDDENCLASS;
        } else
        {
            className = rowClass;
            /*if (ODDROWCLASS.equalsIgnoreCase(rowClass))
            {
                rowClass = EVENROWCLASS;
            } else
            {
                rowClass = ODDROWCLASS;
            }*/
            if (EVENROWCLASS.equalsIgnoreCase(rowClass))
            {
                rowClass = ODDROWCLASS;
            } else
            {
                rowClass = EVENROWCLASS;
            }

        }
        return className;
    }

    /*
     * To add a row id for each row. Overriding
     * org.displaytag.decorator.TableDecorator#addRowId()
     */

    public String addRowId()
    {

        if (onlyEndGrp && !onlyStrtGrp)
        {
            return "grpEnd";
        }

        onlyEndGrp = false;
        onlyStrtGrp = false;
        return "grp";
    }
    
    /*
     * This function is used for displaying the approve and deny buttons
     * in the action column of pending enrollment approval screen
     * 
     */
    public String getAction()
    {
        String dealerCode = ((TemplateDocsBean) this.getCurrentRowObject()).getOrgDlrCd();
        String userName = ((TemplateDocsBean) this.getCurrentRowObject()).getRowUpdtName();
        Date uploadedTime = ((TemplateDocsBean) this.getCurrentRowObject()).getModifiedTime();
        return ACTIONINPUT + dealerCode + SEPARATOR + userName + SEPARATOR + uploadedTime + SEPARATOR + approve + MCXPARAMETER + SPACE + denyActionInput + dealerCode + SEPARATOR + userName
                + SEPARATOR + uploadedTime + SEPARATOR + deny + MCXPARAMETER;
    }
    
    

    public String getMcaStatusMsg()
    {

        String YES = "Y";
        String IMAGEPATH = "";
        String lockIndicator = (((TemplateDocsBean) this.getCurrentRowObject()).getLockInd());
        String currentCompanyId = (((TemplateDocsBean) this.getCurrentRowObject()).getCurrentCompanyId());
        String currentUserId = (((TemplateDocsBean) this.getCurrentRowObject()).getCurrentUserId());
        String lockedUserId = (((TemplateDocsBean) this.getCurrentRowObject()).getLockByUsrId());
        String lockedcompanyId = (((TemplateDocsBean) this.getCurrentRowObject()).getLockCmpnyId());
        String lockedUser = (((TemplateDocsBean) this.getCurrentRowObject()).getLockByUsrNm());
        String ahref = "";
        String status = "MCA Set up required";
        String imagePathSubString = "";
        
        /*
         * The loop decides which lock is to be displayed based
         * on the logged in companyId, userId and the Locked companyId and userid
         * 
         */
        if (YES.equalsIgnoreCase(lockIndicator))
        {
            if (currentCompanyId.equals(lockedcompanyId) && currentUserId.equals(lockedUserId))
            {
                IMAGEPATH = LOCKIMAGE + lockedUser + IMAGECLOSETAG;
                imagePathSubString = IMAGEPATH.substring(1, IMAGEPATH.length() - 2);
            } else
            {
                IMAGEPATH = UNLOCKIMAGE + lockedUser + IMAGECLOSETAG;
                imagePathSubString = IMAGEPATH.substring(1, IMAGEPATH.length() - 2);
            }
        } else
        {
            IMAGEPATH = "";
        }
        String MCAStatus = ((TemplateDocsBean) this.getCurrentRowObject()).getMcaStatusCd();
        String mcaStatus = ((TemplateDocsBean) this.getCurrentRowObject()).getMcaStatusCd() + IMAGEPATH;
        String hidden = HIDDEN + MCAStatus + imagePathSubString + CLOSETAG;
        int mcaTemplateId = ((TemplateDocsBean) this.getCurrentRowObject()).getTmpltId();
        String cpID = ((TemplateDocsBean) this.getCurrentRowObject()).getOrgDlrCd();
        String cpNm = ((TemplateDocsBean) this.getCurrentRowObject()).getOrgDlrNm();
        if (status.equals(MCAStatus))
        {
            ahref = hidden + MCASETUPFIRSTSTRING + mcaTemplateId + MCASETUPCPID + cpID + MCASETUPCPNAME + cpNm + MCASETUPSECONDSTRING + mcaStatus + AENDTAG;
        } else
        {
            ahref = hidden + TEMPLATEFIRSTSTRINGNOPADDING + mcaTemplateId + TEMPLATESECONDSTRING + mcaStatus + AENDTAG;
        }
        return ahref;
    }
    
    
    /*
     * This function is used for the status column in the admin setup and posting page.
     * 
     */

    public String getMcaStatusCd()
    {
        String YES = "Y";
        String IMAGEPATH = "";
        String currentCompanyId = (((TemplateDocsBean) this.getCurrentRowObject()).getCurrentCompanyId());
        String currentUserId = (((TemplateDocsBean) this.getCurrentRowObject()).getCurrentUserId());
        String lockedCmpnyId = (((TemplateDocsBean) this.getCurrentRowObject()).getLockCmpnyId());
        String lockedUsrId = (((TemplateDocsBean) this.getCurrentRowObject()).getLockByUsrId());
        String lockIndicator = (((TemplateDocsBean) this.getCurrentRowObject()).getLockInd());
        String lockedUser = (((TemplateDocsBean) this.getCurrentRowObject()).getLockByUsrNm());
        String imagePathSubString = "";
        String printParam = "&sltInd= ";

        /*
         * This loop decides which lock is to be displayed based on the logged in companyid, userid
         * and locked companyId, userId.
         * 
         */

        if (YES.equalsIgnoreCase(lockIndicator))
        {
            if (currentCompanyId.equals(lockedCmpnyId) && currentUserId.equals(lockedUsrId))
            {
                IMAGEPATH = ADMINLOCKIMAGE + lockedUser + IMAGECLOSETAG;
                imagePathSubString = IMAGEPATH.substring(1, IMAGEPATH.length() - 2);
            } else
            {
                IMAGEPATH = ADMINUNLOCKIMAGE + lockedUser + IMAGECLOSETAG;
                imagePathSubString = IMAGEPATH.substring(1, IMAGEPATH.length() - 2);
            }
        } else
        {
            IMAGEPATH = "";
        }
        String MCAStatus = ((TemplateDocsBean) this.getCurrentRowObject()).getMcaStatusCd();
        String mcaStatus = ((TemplateDocsBean) this.getCurrentRowObject()).getMcaStatusCd() + IMAGEPATH;
        int mcaTemplateId = ((TemplateDocsBean) this.getCurrentRowObject()).getTmpltId();
        String hidden = HIDDEN + MCAStatus + imagePathSubString + CLOSETAG;
        String ahref = hidden + ADMINSTATUS + mcaTemplateId + printParam + CLOSETAG + mcaStatus + AENDTAG;

        return ahref;
    }
    
    /*
     * 
     * This function is used for the status column in the Pending MCAs screen. 
     * 
     */

    public String getPendingMcaStatusCd()
    {
        String YES = "Y";
        String IMAGEPATH = "";
        String lockIndicator = (((TemplateDocsBean) this.getCurrentRowObject()).getLockInd());
        String currentCompanyId = (((TemplateDocsBean) this.getCurrentRowObject()).getCurrentCompanyId());
        String currentUserId = (((TemplateDocsBean) this.getCurrentRowObject()).getCurrentUserId());
        String lockedUserId = (((TemplateDocsBean) this.getCurrentRowObject()).getLockByUsrId());
        String lockedcompanyId = (((TemplateDocsBean) this.getCurrentRowObject()).getLockCmpnyId());
        String lockedUser = (((TemplateDocsBean) this.getCurrentRowObject()).getLockByUsrNm());
        String imagePathSubString = "";

        /*
         * This loop decides which lock is to be displayed based on the logged in companyid, userid
         * and locked companyId, userId.
         * 
         */

        if (YES.equalsIgnoreCase(lockIndicator))
        {
            if (currentCompanyId.equals(lockedcompanyId) && currentUserId.equals(lockedUserId))
            {
                IMAGEPATH = LOCKIMAGE + lockedUser + IMAGECLOSETAG;
                imagePathSubString = IMAGEPATH.substring(1, IMAGEPATH.length() - 2);

            } else
            {
                IMAGEPATH = UNLOCKIMAGE + lockedUser + IMAGECLOSETAG;
                imagePathSubString = IMAGEPATH.substring(1, IMAGEPATH.length() - 2);
            }
        } else
        {
            IMAGEPATH = "";
        }
        String MCAStatus = ((TemplateDocsBean) this.getCurrentRowObject()).getMcaStatusCd();
        String mcaStatus = ((TemplateDocsBean) this.getCurrentRowObject()).getMcaStatusCd() + IMAGEPATH;
        int mcaTemplateId = ((TemplateDocsBean) this.getCurrentRowObject()).getTmpltId();
        String hidden = HIDDEN + MCAStatus + imagePathSubString + CLOSETAG;
        String ahref = hidden + TEMPLATEFIRSTSTRINGNOPADDING + mcaTemplateId + TEMPLATESECONDSTRING + mcaStatus + AENDTAG;
        return ahref;
    }
    
    /*
     * This function is for the MCA file name column in the Executed MCAs screen.
     * 
     */

    public String getExecutedMcaFileName()
    {
        /*
         * "this.getCurrentRowObject()" gives the current row object. TypeCasted
         * to the Bean to get the required property value.
         */
        String PREEXISTING = "Pre-Existing";
        String documentType = "P";
        final String SEPERATOR = "','";
        final String ACLOSE = "')\" title=\"";
        final String tempTitle = "&frmScr=NEG&sltInd=TE&viewInd=F&catgyId=\" title=\"";
        String fileName = ((TemplateDocsBean) this.getCurrentRowObject()).getExecutedMcaFileName();
        String tempLongName = ((TemplateDocsBean) this.getCurrentRowObject()).getTmpltLngNm();
        String firmName = ((TemplateDocsBean) this.getCurrentRowObject()).getOrgDlrNm();
        String firmId = ((TemplateDocsBean) this.getCurrentRowObject()).getOrgDlrCd();
        int mcaTemplateId = ((TemplateDocsBean) this.getCurrentRowObject()).getTmpltId();
        String docName = ((TemplateDocsBean) this.getCurrentRowObject()).getDocName();
        String docType = ((TemplateDocsBean) this.getCurrentRowObject()).getTmpltTyp();
        String docId = ((TemplateDocsBean) this.getCurrentRowObject()).getDocId();
        String ahref = HIDDEN + fileName + CLOSETAG;
        
        tempLongName = tempLongName.replaceAll("\"","&quot;");

        /*
         * Table model contains the list which is displayed on the UI. Used to
         * get the previous and next row column values .
         */

        if (this.tableModel.getSortedColumnNumber() == 0)
        {

            TableModel model = this.tableModel;

            /*
             * RowIterator gets the list displayed on the UI. By iterating
             * through the iterator we can access each row object.
             *  
             */
            RowIterator rowIterator = this.tableModel.getRowIterator(false);

            //Row objects.
            Row previousRow = null;

            Row nextRow = null;

            String nextRowName = null;

            String previousRowName = null;
            String nextFileName = null;
            String previousFileName = null;
            String previousRowId = null;
            String nextRowId = null;

            int rowListSize = model.getRowListFull().size();
            //"this.getListIndex()" gives the sorted list index.
            int index = this.getListIndex();

            // Iterating till the previous row to get previous row values.
            for (int i = 0; i < index - 1; i++)
            {
                rowIterator.next();
            }
            if (index > 0)
            {
                //Getting the previous row.
                previousRow = rowIterator.next();
                previousRowName = ((TemplateDocsBean) previousRow.getObject()).getOrgDlrNm();
                previousRowId = ((TemplateDocsBean) previousRow.getObject()).getOrgDlrCd();
            }

            // to get the next row.
            rowIterator.next();

            if (rowIterator.hasNext())
            {
                // getting the next row.
                nextRow = rowIterator.next();
                nextRowName = ((TemplateDocsBean) nextRow.getObject()).getOrgDlrNm();
                nextRowId = ((TemplateDocsBean) nextRow.getObject()).getOrgDlrCd();
            }

            rowIterator = this.tableModel.getRowIterator(false);
            index = this.getListIndex();

            if (rowIndex < rowListSize)
            {
                for (int i = 0; i < index - 1; i++)
                {
                    rowIterator.next();
                }
                if (index > 0)
                {
                    //Getting the previous row.
                    previousRow = rowIterator.next();
                }
            }
            rowIndex++;
            if (firmId.equals(nextRowId))
            {
                if (!firmId.equals(previousRowId))
                {
                    isGroup = true;
                } else
                {
                    isGroup = false;
                }

            } else
            {
                isGroup = false;
            }

            if (isGroup & this.tableModel.getSortedColumnNumber() == 0)
            {
                if (PREEXISTING.equalsIgnoreCase(docType))
                {
                    /*
                     * If it is a pre-existing mca with no document attached to it 
                     * then the template name will not appear as a link
                     * 
                     */
                    if (docName.trim().length() == 0)
                    {
                        ahref = ahref + PREEXISTINGGROUPFILENAME + tempLongName + MCATMPLTALIGNEND + fileName + AENDTAG;
                    } else
                    {
                        /*
                         * If it is a pre-existing mca with a document attached to it 
                         * then the we pass the document id and the doc type as 'pre-existing' to download the document
                         * 
                         */
                        ahref = ahref + MCAGROUPNAMELINK + docId + SEPERATOR + documentType + SEPERATOR +firmId + ACLOSE + tempLongName + MCATMPLTALIGNEND + fileName + AENDTAG;
                    }
                } else
                {
                    ahref = ahref + TEMPLATEGROUPFIRSTSTRING + mcaTemplateId + tempTitle + tempLongName + MCATMPLTALIGNEND + fileName + AENDTAG;
                }
                return GROUPSYMBOL + index + GROUPSTYLE + ahref;
            } else
            {
                
                if (PREEXISTING.equalsIgnoreCase(docType))
                {
                    if (docName.trim().length() == 0)
                    {
                        ahref = ahref + PREEXISTINGFILENAME + tempLongName + MCATMPLTALIGNEND + fileName + AENDTAG;
                    } else
                    {
                        ahref = ahref + MCANAMELINK + docId + SEPERATOR + documentType + SEPERATOR +firmId + ACLOSE + tempLongName + MCATMPLTALIGNEND + fileName + AENDTAG;
                    }
                } else
                {
                    ahref = ahref + TEMPLATEFIRSTSTRING + mcaTemplateId + tempTitle + tempLongName + MCATMPLTALIGNEND + fileName + AENDTAG;
                }

                return ahref;
            }
        } else
        {
            if (PREEXISTING.equalsIgnoreCase(docType))
            {
                if (docName.trim().length() == 0)
                {
                    ahref = ahref + PREEXISTINGFILENAME + tempLongName + MCATMPLTALIGNEND + fileName + AENDTAG;
                    
                } else
                {
                    ahref = ahref + MCANAMELINK + docId + SEPERATOR + documentType + SEPERATOR +firmId + ACLOSE + tempLongName + MCATMPLTALIGNEND + fileName + AENDTAG;
                }
            } else
            {
                ahref = ahref + TEMPLATEFIRSTSTRING + mcaTemplateId + tempTitle + tempLongName + MCATMPLTALIGNEND + fileName + AENDTAG;
            }
            return ahref;
        }
    }



    public String getRowUpdtName()
    {
        String userId = ((TemplateDocsBean) this.getCurrentRowObject()).getRowUpdtId().trim();
        String userName = ((TemplateDocsBean) this.getCurrentRowObject()).getRowUpdtName().trim();
        String hidden = HIDDEN + userName + CLOSETAG;
        String ahref = hidden + USERDETAILSPOPUPLINK + userId + USERDETAILSPOPUPCLOSE + userName + AENDTAG;
        return ahref;
    }

    public String getStatus()
    {
        String YES = "Y";
        String IMAGEPATH = "";
        String lockIndicator = (((TemplateDocsBean) this.getCurrentRowObject()).getLockInd());
        String currentCompanyId = (((TemplateDocsBean) this.getCurrentRowObject()).getCurrentCompanyId());
        String currentUserId = (((TemplateDocsBean) this.getCurrentRowObject()).getCurrentUserId());
        String lockedUserId = (((TemplateDocsBean) this.getCurrentRowObject()).getLockByUsrId());
        String lockedcompanyId = (((TemplateDocsBean) this.getCurrentRowObject()).getLockCmpnyId());
        String lockedUser = (((TemplateDocsBean) this.getCurrentRowObject()).getLockByUsrNm());

        //check for the actual lock indicator from the SP resultset.

        if (YES.equalsIgnoreCase(lockIndicator))
        {
            if (currentCompanyId.equals(lockedcompanyId) && currentUserId.equals(lockedUserId))
            {
                IMAGEPATH = LOCKIMAGE + lockedUser + IMAGECLOSETAG;
            } else
            {
                IMAGEPATH = UNLOCKIMAGE + lockedUser + IMAGECLOSETAG;
            }
        } else
        {
            IMAGEPATH = "";
        }
        String MCAStatus = ((TemplateDocsBean) this.getCurrentRowObject()).getMcaStatusCd();
        String mcaStatus = ((TemplateDocsBean) this.getCurrentRowObject()).getMcaStatusCd() + IMAGEPATH;
        String ahref = mcaStatus + AENDTAG;
        return ahref;

    }

    public String getTemplateName()
    {
        String tempName = ((TemplateDocsBean) this.getCurrentRowObject()).getTmpltNm();
        return tempName;
    }

}
