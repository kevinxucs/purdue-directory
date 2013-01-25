/*
 * Author: Kaiwen Xu
 */

package net.kevxu.purdueassist.directory;

import java.util.ArrayList;

import org.apache.commons.lang3.text.WordUtils;

public class People {

	public final static int OFFICE_PHONE = 2124;
	public final static int HOME_PHONE = 1623;

	private String alias = null;
	private String nickName = null;
	private String title = null;
	private String campus = null;
	private String name = null;
	private String school = null;
	private String department = null;
	private String email = null;
	private String office = null;
	private String officePhone = null;
	private String homePhone = null;
	private String homeAddress = null;

	private String url;

	public void set(String attr, String content) {
		if (attr.equals("Career Acct. Login"))
			this.alias = content;
		if (attr.equals("Nickname"))
			this.nickName = WordUtils.capitalize(content);
		if (attr.equals("Title"))
			this.title = WordUtils.capitalize(content);
		if (attr.equals("Campus"))
			this.campus = WordUtils.capitalize(content);
		if (attr.equals("Name"))
			this.name = WordUtils.capitalize(content);
		if (attr.equals("School"))
			this.school = WordUtils.capitalize(content);
		if (attr.equals("Department"))
			this.department = WordUtils.capitalize(content);
		if (attr.equals("Email")) {
			this.email = content;
		}
		if (attr.equals("Office"))
			this.office = WordUtils.capitalize(content);
		if (attr.equals("Office Phone"))
			this.officePhone = formatPhoneNumber(content);
		if (attr.equals("Home Phone"))
			this.homePhone = formatPhoneNumber(content);
		if (attr.equals("Home Postal Address")) {
			this.homeAddress = content.replace("|", "\n");
			this.homeAddress = WordUtils.capitalize(this.homeAddress);
		}

	}

	private String formatPhoneNumber(String content) {
		content = content.replace("-", "");
		content = content.replace(" ", "-");
		content = content.substring(0, 10) + "-" + content.substring(10, 14);
		return content;

	}

	public void setUrl(String partUrl) {
		url = DirectoryConnector.itapDirectoryUrl + partUrl.substring(0, partUrl.indexOf("&"));
		url = url.replace("\0", "");
	}

	public String getUrl() {
		return url;
	}

	public String[] getBasicInfo() {
		return new String[] { name, email, school, department };
	}

	public ArrayList<String[]> getDetailedInfo() {
		ArrayList<String[]> recorder = new ArrayList<String[]>();
		String[][] temp = new String[][] { new String[] { "Login:", alias },
				new String[] { "Name:", name }, new String[] { "Nickname:", nickName },
				new String[] { "Title:", title }, new String[] { "Campus:", campus },
				new String[] { "School:", school }, new String[] { "Department:", department },
				new String[] { "Email:", email }, new String[] { "Office:", office },
				new String[] { "Office Phone:", officePhone },
				new String[] { "Residence Phone:", homePhone },
				new String[] { "Address:", homeAddress }

		};

		for (int i = 0; i < temp.length; i++) {
			if (temp[i][1] != null)
				recorder.add(temp[i]);
		}

		return recorder;
	}

	public String getName() {
		return name;
	}

	public String getName(boolean includeInitial) {
		if (includeInitial) {
			return name;
		} else {
			String[] temp = name.split(" ");
			String result = "";
			for (int i = 0; i < temp.length; i++) {
				if (temp[i].length() > 1) {
					result = result + " " + temp[i];
				}
			}
			return result.trim();
		}
	}

	public String[][] getPhone() {
		if (homePhone == null && officePhone == null) {
			return null;
		} else if (homePhone == null) {
			return new String[][] { new String[] { String.valueOf(OFFICE_PHONE),
					officePhone.replace("+", "") } };
		} else if (officePhone == null) {
			return new String[][] { new String[] { String.valueOf(HOME_PHONE),
					homePhone.replace("+", "") } };
		} else {
			return new String[][] {
					new String[] { String.valueOf(HOME_PHONE), homePhone.replace("+", "") },
					new String[] { String.valueOf(OFFICE_PHONE), officePhone.replace("+", "") } };

		}
	}

	public String getEmail() {
		return email;
	}
	
	public String getAddress() {
		return homeAddress;
	}
}
