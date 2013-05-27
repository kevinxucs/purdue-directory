package net.kevxu.purdue.directory;

import org.apache.directory.api.ldap.model.cursor.CursorException;
import org.apache.directory.api.ldap.model.cursor.EntryCursor;
import org.apache.directory.api.ldap.model.cursor.SearchCursor;
import org.apache.directory.api.ldap.model.entry.Entry;
import org.apache.directory.api.ldap.model.exception.LdapException;
import org.apache.directory.api.ldap.model.message.Response;
import org.apache.directory.api.ldap.model.message.SearchScope;
import org.apache.directory.ldap.client.api.LdapConnection;
import org.apache.directory.ldap.client.api.LdapNetworkConnection;

import java.io.IOException;

public class Directory {

    private static final String ADDRESS = "ped.purdue.edu";
    private static final String[] ADDRESS_FALLBACK = {"128.210.5.138", "128.210.5.137"};

    public static void main(String[] args) {
        LdapConnection connection = new LdapNetworkConnection(ADDRESS, 636, true);

        try {
            connection.connect();
            EntryCursor cursor = connection.search("sn=yili", "(objectclass=*)", SearchScope.ONELEVEL);

            while (cursor.next()) {
                Entry entry = cursor.get();

                System.out.println(entry);
            }
        } catch (LdapException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (CursorException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            try {
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }

        System.out.println("Exiting...");
    }

}
