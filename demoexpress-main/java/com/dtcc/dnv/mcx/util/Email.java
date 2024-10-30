package com.dtcc.dnv.mcx.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/*
 * Copyright © 2003 The Depository Trust & Clearing Company. All rights reserved.
 *
 * Depository Trust & Clearing Corporation (DTCC)
 * 55, Water Street,
 * New York, NY 10048
 * U.S.A
 * All Rights Reserved.
 *
 * This software may contain (in part or full) confidential/proprietary information of DTCC.
 * ("Confidential Information"). Disclosure of such Confidential
 * Information is prohibited and should be used only for its intended purpose
 * in accordance with rules and regulations of DTCC.
 * 
 * @author Kevin Lake
 * @version 1.0
 * Date: September 05, 2007
 */
public class Email {
	
	// Mail store prototcol
	private static final String PROTOCOL = "mail.store.protocol";
	
	// Transport protocol
	private static final String TRANSPORT_PROTOCOL = "mail.transport.protocol";
	
	// Mail host
	private static final String HOST = "mail.smtp.host";
	
	// Logger
	private final static MessageLogger log = MessageLogger.getMessageLogger(Email.class.getName());
	
	/**
	 * Send Mail Message
	 * @param subject
	 * @param message
	 * @param fromAddr
	 * @param m_to
	 * @param m_cc
	 * @param m_bcc
	 */
	public static void sendMail (String subjKey, String msgKey, 
			                     String[] m_to, String[] m_cc, String[] m_bcc,
								 String msgType)	{
		
		// Set properties
		Properties props = System.getProperties();
		props.put(PROTOCOL, MessageResources.getMessage("mail.protocol"));
		props.put(TRANSPORT_PROTOCOL, MessageResources.getMessage("mail.protocol"));
		props.put(HOST, MessageResources.getMessage("mail.host"));
		
		// Create Mail Session
		Session session = Session.getDefaultInstance(props);
		
		try	{
			
			// Create MIME Message
			MimeMessage m_msg = new MimeMessage(session);
			
			//Get message from Message file
			String message =  EmailUtil.getEmailMessage(msgKey);
			
			// Set message and message type
			// m_msg.setContent(MessageResources.getMessage(msgKey), msgType);
			m_msg.setContent(message, msgType);
			
			// Set Subject
			m_msg.setSubject(MessageResources.getMessage(subjKey));
		    
			// From Address
			m_msg.setFrom(new InternetAddress(MessageResources.getMessage(MCXConstants.MCX_FROM_ADDRESS_KEY)));
			
			// To Address(es)
			if(m_to != null && m_to.length > 0)
			  m_msg.setRecipients (Message.RecipientType.TO, getInternetAddresses(m_to));
			else
			  return;
			
			// CC Address(es)
			if(m_cc != null && m_cc.length > 0)
			  m_msg.setRecipients (Message.RecipientType.CC, getInternetAddresses(m_cc));
			
			// BCC Address(es)
			if (m_bcc != null && m_bcc.length > 0)
				m_msg.setRecipients (Message.RecipientType.BCC, getInternetAddresses(m_bcc));
			
			// Sent Date
			m_msg.setSentDate(new java.util.Date());
			
			// Set Mail Header
			m_msg.setHeader("X-Mailer", MessageResources.getMessage("mail.xmailer"));
						
			// Send Message
			Transport.send(m_msg);			
			
		} catch (MessagingException me)	{
		  log.error(me);			
		} catch (Exception e)	{
		  log.error(e);			
		}		
	}
	
	/**
	 * @param addresses
	 * @return InternetAddress[]
	 * @throws Exception
	 */
	private static InternetAddress[] getInternetAddresses(String[] addresses) 
	  throws Exception {
		
		if (addresses == null || addresses.length == 0)
			return null;
		
		InternetAddress[] ias = new InternetAddress[addresses.length];
		for (int i = 0; i < addresses.length; i++) {
			ias[i] = new InternetAddress(addresses[i]);			
		}
		
		return ias;
	}

}
