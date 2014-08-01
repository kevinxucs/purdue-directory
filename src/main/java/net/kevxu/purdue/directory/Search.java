package net.kevxu.purdue.directory;

import com.unboundid.ldap.sdk.Filter;

import java.util.ArrayList;
import java.util.List;

public class Search {

    private Filter nameFilter = null;
    private Filter emailFilter = null;
    private Filter loginFilter = null;
    private Filter officePhoneFilter = null;
    private Filter campusFilter = null;

    public static class Builder {

        private Search search;

        public Builder() {
            search = new Search();
        }

        public Builder name(String name) {
            search.setName(name);
            return this;
        }

        public Builder email(String email) {
            search.setEmail(email);
            return this;
        }

        public Builder login(String login) {
            search.setLogin(login);
            return this;
        }

        public Builder officePhone(String officePhone) {
            search.setOfficePhone(officePhone);
            return this;
        }

        public Builder campus(Campus campus) {
            search.setCampus(campus);
            return this;
        }

        public Search build() {
            return search;
        }

    }

    public void setName(String name) {
        nameFilter = createSubstringFilter(Attributes.name, name);
    }

    public void setEmail(String email) {
        emailFilter = createSubstringFilter(Attributes.email, email);
    }

    public void setLogin(String login) {
        loginFilter = createSubstringFilter(Attributes.login, login);
    }

    public void setOfficePhone(String officePhone) {
        officePhoneFilter = createSubstringFilter(Attributes.officePhone, officePhone);
    }

    public void setCampus(Campus campus) {
        campusFilter = createSubstringFilter(Attributes.campus, campus.getName());
    }

    private Filter createSubstringFilter(String attributeName, String value) {
        return Filter.createSubstringFilter(attributeName, null, new String[] { value }, null);
    }

    protected Filter allFilters() {
        List<Filter> filters = new ArrayList<Filter>();
        if (nameFilter != null) {
            filters.add(nameFilter);
        }
        if (emailFilter != null) {
            filters.add(emailFilter);
        }
        if (loginFilter != null) {
            filters.add(loginFilter);
        }
        if (officePhoneFilter != null) {
            filters.add(officePhoneFilter);
        }
        if (campusFilter != null) {
            filters.add(campusFilter);
        }

        return Filter.createANDFilter(filters);
    }

}
