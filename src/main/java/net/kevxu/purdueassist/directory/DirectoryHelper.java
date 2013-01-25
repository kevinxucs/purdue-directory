/*
 * Author: Kaiwen Xu
 */

package net.kevxu.purdueassist.directory;

import java.util.ArrayList;
import java.util.Scanner;

public class DirectoryHelper {

	private String rawString;
	private String tempStirng;
	private static String selectedRange = PurdueDirectoryList.RANGE_ALL;
	private static String inputName;

	public DirectoryHelper(String rawString) {
		this.rawString = rawString;
	}

	private String tagRemover(String input) throws DirectoryQueryException {
		int startIndex = input.indexOf("directoryResults");
		int endIndex = input.indexOf("</div>", startIndex);
		String temp = input.substring(startIndex + 18, endIndex);

		if (temp.contains("Your search has returned too many entries.")
				&& !temp.contains("detail.cfm")) {
			throw new DirectoryQueryException("Your search has returned too many results.");
		}

		if (temp.contains("Your search returned no results.") && !temp.contains("detail.cfm")) {
			throw new DirectoryQueryException(
					context.getString(R.string.directory_helper_query_exception_no_people_found));
		}

		if (temp.contains("Your search returned no results.")
				|| temp.contains("Your search has returned too many entries.")) {
			Toast.makeText(context,
					context.getString(R.string.directory_helper_not_all_the_people_listed),
					Toast.LENGTH_SHORT).show();
		}

		temp = temp.replace("\t", "");
		temp = temp.replace("<a href=", "");
		temp = temp.replaceAll("\\<[^>]*>", "");
		temp = temp.replace("&nbsp;", "");
		temp = temp.replace(">", "");
		temp = temp.replace('"', '\0');

		return temp;
	}

	public ArrayList<People> getPeople() throws DirectoryQueryException {
		tempStirng = tagRemover(rawString);
		Scanner scanner = new Scanner(tempStirng);
		ArrayList<People> list = new ArrayList<People>();
		People people = new People();

		String temp;
		String elementNameHolder = "";
		boolean enterPeople = false;
		boolean enterElement = false;
		boolean anticipatingName = false;

		while (scanner.hasNextLine()) {
			temp = scanner.nextLine();
			if (temp.equals(""))
				continue;
			if (temp.contains("detail.cfm") && enterPeople) {
				list.add(people);
				enterPeople = false;
				enterElement = false;
			}
			if (temp.contains(" detail.cfm") && !enterPeople) {
				people = new People();
				enterPeople = true;
				anticipatingName = true;
				people.setUrl(temp);
				continue;
			}
			if (anticipatingName) {
				people.set("Name", temp);
				anticipatingName = false;
				continue;
			}
			if (temp.contains(":")) {
				enterElement = true;
				elementNameHolder = temp;
				elementNameHolder = elementNameHolder.replace(":", "");
				continue;
			}
			if (enterElement) {
				people.set(elementNameHolder, temp);
				elementNameHolder = "";
				enterElement = false;
				continue;
			}

		}
		list.add(people);
		return list;
	}
}
