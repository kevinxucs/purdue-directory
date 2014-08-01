package net.kevxu.purdue.directory;

import com.unboundid.ldap.sdk.SearchResultEntry;

import java.util.HashMap;
import java.util.Map;

public class ResultEntry {

    private Map<String, String> fields;

    ResultEntry(SearchResultEntry entry) {
        fields = new HashMap<String, String>();

        for (String attributeName : Attributes.ALL) {
            fields.put(attributeName, entry.getAttributeValue(attributeName));
        }
    }

    public String get(String attributeName) {
        return fields.get(attributeName);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        for (String attributeName : Attributes.ALL) {
            stringBuilder.append(attributeName);
            stringBuilder.append(": ");
            stringBuilder.append(fields.get(attributeName));
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }

}
