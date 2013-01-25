/*
 * Author: Kaiwen Xu
 */

package net.kevxu.purdueassist.directory;

import java.util.Scanner;

public class DirectoryDetailHelper {

	private String rawString;
	private People basicPeople;

	public DirectoryDetailHelper(String rawString, People basicInfoPeople) {
		this.rawString = rawString;
		this.basicPeople = basicInfoPeople;
	}

	private String tagRemover(String input) {
		int startIndex = input.indexOf("detailResults");
		int endIndex = input.indexOf("</div>", startIndex);
		String temp = input.substring(startIndex + 15, endIndex);

		temp = temp.replace("\t", "");
		temp = temp.replace("<br />", "|");
		temp = temp.replaceAll("\\<[^>]*>", "");
		temp = temp.replace("&lt; Back to search", "");
		temp = temp.replace("Details for ", "");

		return temp;
	}

	public People getDetail() throws DirectoryQueryException {
		People detailPeople = basicPeople;
		
		String tempString = tagRemover(rawString);
		Scanner scanner = new Scanner(tempString);

		String temp;
		String elementNameHolder = "";
		boolean enterPeople = false;
		boolean enterElement = false;

		while (scanner.hasNextLine()) {
			temp = scanner.nextLine();
			if (temp.equals(""))
				continue;
			if (temp.contains(":")) {
				if (enterPeople == false && enterElement == false) {
					if ((temp.toLowerCase()).contains((basicPeople.getName()).toLowerCase())) {
						enterPeople = true;
						continue;
					} else {
						throw new DirectoryQueryException("Server Internal Error.");
					}
				}
				if (enterPeople == true && enterElement == false) {
					elementNameHolder = temp.replace(":", "");
					enterElement = true;
				}
				continue;
			}
			if (enterPeople == true && enterElement == true) {
				detailPeople.set(elementNameHolder, temp);
				enterElement = false;
				elementNameHolder = "";
				continue;
			}

		}
		return detailPeople;
	}

}
