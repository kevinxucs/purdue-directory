/*
 * Author: Kaiwen Xu
 */

package net.kevxu.purdueassist.directory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.C1Dev.Iry.Connector.HttpHelper;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public class DirectoryConnector {

	public static final String itapDirectoryUrl = "http://www.itap.purdue.edu/directory/";

	public static final String ALL = "all";
	public static final String STUDENT = "student";
	public static final String STAFF = "staff";

	private HttpClient httpClient;
	private HttpPost httpPost;
	private String responseString;

	private String searchType = "all";
	private String name = "";

	public DirectoryConnector(String name, String searchType) {
		this.name = name;
		this.searchType = searchType;

		httpClient = new DefaultHttpClient();
		httpPost = new HttpPost(itapDirectoryUrl);
	}

	public DirectoryConnector(String name) {
		this.name = name;

		httpClient = new DefaultHttpClient();
		httpPost = new HttpPost(itapDirectoryUrl);
	}

	private String sendHttpRequest() throws IOException {
		try {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("search", name));
			nameValuePairs.add(new BasicNameValuePair("searchType", searchType));

			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpClient.execute(httpPost);
			responseString = HttpHelper.request(response);

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		}
		return responseString;

	}

	public String query() throws IOException {
		String responseString = sendHttpRequest();

		return responseString;
	}

	public String queryDetail(String url) throws IOException {
		String temp = "";
		try {
			HttpGet httpGet = new HttpGet(url);
			HttpResponse response = httpClient.execute(httpGet);
			temp = HttpHelper.request(response);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		}
		return temp;
	}

}
