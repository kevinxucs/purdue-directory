package net.kevxu.purdue.directory;

import com.unboundid.ldap.sdk.LDAPConnection;
import com.unboundid.ldap.sdk.LDAPException;

public class Directory {

    private static final String ADDRESS = "ped.purdue.edu";
    private static final String[] ADDRESS_FALLBACK = {"128.210.5.138", "128.210.5.137"};

    public static void main(String[] args) {
        try {
            LDAPConnection connection = new LDAPConnection(ADDRESS, 389);
        } catch (LDAPException e) {
            e.printStackTrace();
        }
    }

}
