package net.kevxu.purdue.directory;

import com.unboundid.ldap.sdk.*;
import com.unboundid.ldap.sdk.schema.AttributeTypeDefinition;
import com.unboundid.ldap.sdk.schema.Schema;

import java.util.ArrayList;
import java.util.List;

public class Directory {

    private static final String LDAP_SERVER = "ped.purdue.edu";
    private static final int LDAP_PORT = 389;
    private static final String BASE_DN = "dc=purdue,dc=edu";

    private final LDAPConnection connection;

    public Directory() throws LDAPException {
        connection = new LDAPConnection(LDAP_SERVER, LDAP_PORT);
    }

    public List<ResultEntry> search(Search search) throws LDAPSearchException {
        List<ResultEntry> results = new ArrayList<ResultEntry>();

        Filter filter = search.createAndFilter();
        SearchRequest request = new SearchRequest(BASE_DN, SearchScope.SUB, filter, Attributes.ALL);
        SearchResult result = connection.search(request);

        for (SearchResultEntry entry : result.getSearchEntries()) {
            results.add(new ResultEntry(entry));
        }

        return results;
    }

    public void close() {
        connection.close();
    }

}
