package com.anchex.contacts;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import com.google.gdata.client.Query;
import com.google.gdata.client.contacts.ContactsService;
import com.google.gdata.data.Link;
import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.data.contacts.ContactFeed;
import com.google.gdata.data.contacts.GroupMembershipInfo;
import com.google.gdata.data.extensions.Email;
import com.google.gdata.data.extensions.ExtendedProperty;
import com.google.gdata.data.extensions.Im;
import com.google.gdata.data.extensions.Name;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;

public class Test {
	
	public ContactsService service;
	
	public Test() throws AuthenticationException
	{
		service = new ContactsService("ProfileMapper");
		
		
		
	}
	
	public Test(String user, String pass) throws AuthenticationException
	{
		service = new ContactsService("ProfileMapper");
		
		service.setUserCredentials(user, pass);
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Scanner keyboard = new Scanner(System.in);
		
		System.out.println("Username:");
		String user = keyboard.nextLine();
		System.out.println("Password:");
		String pass = keyboard.nextLine();
		
		// TODO Auto-generated method stub
		try { 
			Test myTest = new Test(user,pass);
			
			myTest.testEntries();
		
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void printAllContacts(ContactsService myService)
    throws ServiceException, IOException {
  // Request the feed
  URL feedUrl = new URL("https://www.google.com/m8/feeds/contacts/default/full");
  ContactFeed resultFeed = myService.getFeed(feedUrl, ContactFeed.class);
  // Print the results
  System.out.println(resultFeed.getTitle().getPlainText());
  for (int i = 0; i < resultFeed.getEntries().size(); i++) {
    ContactEntry entry = resultFeed.getEntries().get(i);
    if (entry.hasName()) {
      Name name = entry.getName();
      if (name.hasFullName()) {
        String fullNameToDisplay = name.getFullName().getValue();
        if (name.getFullName().hasYomi()) {
          fullNameToDisplay += " (" + name.getFullName().getYomi() + ")";
        }
        System.out.println("\t\t" + fullNameToDisplay);
      } else {
        System.out.println("\t\t (no full name found)");
      }
      if (name.hasNamePrefix()) {
        System.out.println("\t\t" + name.getNamePrefix().getValue());
      } else {
        System.out.println("\t\t (no name prefix found)");
      }
      if (name.hasGivenName()) {
        String givenNameToDisplay = name.getGivenName().getValue();
        if (name.getGivenName().hasYomi()) {
          givenNameToDisplay += " (" + name.getGivenName().getYomi() + ")";
        }
        System.out.println("\t\t" + givenNameToDisplay);
      } else {
        System.out.println("\t\t (no given name found)");
      }
      if (name.hasAdditionalName()) {
        String additionalNameToDisplay = name.getAdditionalName().getValue();
        if (name.getAdditionalName().hasYomi()) {
          additionalNameToDisplay += " (" + name.getAdditionalName().getYomi() + ")";
        }
        System.out.println("\t\t" + additionalNameToDisplay);
      } else {
        System.out.println("\t\t (no additional name found)");
      }
      if (name.hasFamilyName()) {
        String familyNameToDisplay = name.getFamilyName().getValue();
        if (name.getFamilyName().hasYomi()) {
          familyNameToDisplay += " (" + name.getFamilyName().getYomi() + ")";
        }
        System.out.println("\t\t" + familyNameToDisplay);
      } else {
        System.out.println("\t\t (no family name found)");
      }
      if (name.hasNameSuffix()) {
        System.out.println("\t\t" + name.getNameSuffix().getValue());
      } else {
        System.out.println("\t\t (no name suffix found)");
      }
    } else {
      System.out.println("\t (no name found)");
    }

    System.out.println("Email addresses:");
    for (Email email : entry.getEmailAddresses()) {
      System.out.print(" " + email.getAddress());
      if (email.getRel() != null) {
        System.out.print(" rel:" + email.getRel());
      }
      if (email.getLabel() != null) {
        System.out.print(" label:" + email.getLabel());
      }
      if (email.getPrimary()) {
        System.out.print(" (primary) ");
      }
      System.out.print("\n");
    }

    System.out.println("IM addresses:");
    for (Im im : entry.getImAddresses()) {
      System.out.print(" " + im.getAddress());
      if (im.getLabel() != null) {
        System.out.print(" label:" + im.getLabel());
      }
      if (im.getRel() != null) {
        System.out.print(" rel:" + im.getRel());
      }
      if (im.getProtocol() != null) {
        System.out.print(" protocol:" + im.getProtocol());
      }
      if (im.getPrimary()) {
        System.out.print(" (primary) ");
      }
      System.out.print("\n");
    }

    System.out.println("Groups:");
    for (GroupMembershipInfo group : entry.getGroupMembershipInfos()) {
      String groupHref = group.getHref();
      System.out.println("  Id: " + groupHref);
    }

    System.out.println("Extended Properties:");
    for (ExtendedProperty property : entry.getExtendedProperties()) {
      if (property.getValue() != null) {
        System.out.println("  " + property.getName() + "(value) = " +
            property.getValue());
      } else if (property.getXmlBlob() != null) {
        System.out.println("  " + property.getName() + "(xmlBlob)= " +
            property.getXmlBlob().getBlob());
      }
    }

    Link photoLink = entry.getContactPhotoLink();
    String photoLinkHref = photoLink.getHref();
    System.out.println("Photo Link: " + photoLinkHref);

    if (photoLink.getEtag() != null) {
      System.out.println("Contact Photo's ETag: " + photoLink.getEtag());
    }

    System.out.println("Contact's ETag: " + entry.getEtag());
  }
}

	public void testEntries() throws Exception
	{
		URL feedUrl = new URL("https://www.google.com/m8/feeds/contacts/default/full");
		Query myQuery = new Query(feedUrl);
		myQuery.setMaxResults(100);
		
		ContactFeed feed = service.getFeed(myQuery, ContactFeed.class);
		
		List entries = feed.getEntries();
		Iterator<ContactEntry> it = entries.iterator();
		
		while (it.hasNext())
		{
			try
			{String name = it.next().getName().getFullName().getValue();
			System.out.println(name);
			}
			catch (Exception e)
			{
				System.out.println("NoName");
			}
		}
		
	}





}
