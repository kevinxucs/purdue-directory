package net.kevxu.purdue.directory;

public interface Attributes {

    public static final String name = "cn";
    public static final String login = "uid";
    public static final String website = "labeledURI";
    public static final String lastName = "sn";
    public static final String title = "title";
    public static final String firstName = "givenName";
    public static final String email = "mail";
    public static final String pager = "pager";
    public static final String office = "roomNumber";
    public static final String displayName = "displayName";
    public static final String building = "purdueEduBuilding";
    public static final String campus = "purdueEduCampus";
    public static final String department = "purdueEduDepartment";
    public static final String officeHours = "purdueEduOfficeHours";
    public static final String officePhone = "purdueEduOfficePhone";
    public static final String pgp = "purdueEduPgp";
    public static final String project = "purdueEduProject";
    public static final String qualifiedName = "purdueEduQualifiedName";
    public static final String school = "purdueEduSchool";
    public static final String fax = "purdueEduFax";
    public static final String nickname = "purdueEduNickname";
    public static final String comment = "purdueEduComment";

    public static final String[] ALL = new String[] {
            name,
            login,
            website,
            lastName,
            title,
            firstName,
            email,
            pager,
            office,
            displayName,
            building,
            campus,
            department,
            officeHours,
            officePhone,
            pgp,
            project,
            qualifiedName,
            school,
            fax,
            nickname,
            comment
    };

}
