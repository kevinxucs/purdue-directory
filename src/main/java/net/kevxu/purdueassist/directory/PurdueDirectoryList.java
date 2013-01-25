/*
 * Author: Kaiwen Xu
 * Modified by: Siyuan Gao
 */

package net.kevxu.purdueassist.directory;

import java.io.IOException;
import java.util.ArrayList;

import net.kevxu.purdueassistant.CourseMap;
import net.kevxu.purdueassistant.R;
import net.kevxu.purdueassistant.util.SimpleTask;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class PurdueDirectoryList extends ListActivity {

	public static final String NAME = "name";
	public static final String RANGE = "range";
	public static final String RANGE_ALL = DirectoryConnector.ALL;
	public static final String RANEE_STUDENT = DirectoryConnector.STUDENT;
	public static final String RANGE_STAFF = DirectoryConnector.STAFF;

	private DirectoryConnector connector;
	private DirectoryHelper helper;
	private DirectoryDetailHelper detailHelper;
	private ArrayList<People> peopleList;
	private DirectoryListAdapter listAdapter;
	private People detailPeople = null;

	private ListView listView;

	private String name;
	private String range;

	private final Context mContext = this;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.purdue_directory_list);

		listView = getListView();

		// set default range to ALL
		range = RANGE_ALL;

		Bundle extras = getIntent().getExtras();
		if (extras.getString(NAME) == null) {
			SimpleTask.errorMessage(this, R.string.purdue_directory_list_name_not_found, true);
		} else {
			name = extras.getString(NAME);
		}

		if (extras.getString(RANGE) != null) {
			range = extras.getString(RANGE);
		}

		// progress dialog
		final ProgressDialog progressDialog = new ProgressDialog(mContext);
		progressDialog.setMessage(getString(R.string.purdue_directory_list_loading_message));
		progressDialog.show();

		// displays the search result
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {

				Looper.prepare();

				try {
					connector = new DirectoryConnector(name, range);
					String rawString = connector.query();
					helper = new DirectoryHelper(mContext, rawString);

					peopleList = helper.getPeople();

				} catch (DirectoryQueryException e) {
					progressDialog.dismiss();
					SimpleTask.errorMessage(mContext, e.getMessage(), true);
					Looper.loop();

				} catch (IOException e) {
					progressDialog.dismiss();
					SimpleTask.errorMessage(mContext,
							R.string.purdue_directory_list_ioexception_message, true);
					Looper.loop();

				}

				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						progressDialog.dismiss();

						listAdapter = new DirectoryListAdapter(mContext, peopleList);
						listView.setAdapter(listAdapter);

						// set clicklistener for listview

						listView.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
									long arg3) {
								createOptionMenu(arg2);

							}
						});

					}

				});

			}

		});
		t.start();

	}

	private void createOptionMenu(int id) {
		final int position = id;

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.purdue_directory_detail_menu_title);
		builder.setItems(R.array.purdue_directory_detail_menu, new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, final int which) {

				final ProgressDialog progressDialog = new ProgressDialog(mContext);
				progressDialog
						.setMessage(getString(R.string.purdue_directory_list_loading_message));
				progressDialog.show();

				Thread t = new Thread(new Runnable() {

					@Override
					public void run() {
						Looper.prepare();

						People clickedPeople = peopleList.get(position);
						try {
							String url = clickedPeople.getUrl();
							String rawString = connector.queryDetail(url);
							detailHelper = new DirectoryDetailHelper(rawString, clickedPeople);
							detailPeople = detailHelper.getDetail();
						} catch (IOException e) {
							progressDialog.dismiss();
							SimpleTask.errorMessage(mContext,
									R.string.purdue_directory_list_ioexception_message, false);
							Looper.loop();
						} catch (DirectoryQueryException e) {
							progressDialog.dismiss();
							SimpleTask.errorMessage(mContext, e.getMessage(), false);
							Looper.loop();
						}

						runOnUiThread(new Runnable() {

							@Override
							public void run() {
								progressDialog.dismiss();

								switch (which) {
								case 0:
									createDetailInfoDialog(detailPeople);
									break;

								case 1:
									Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
											Uri.parse("https://m.facebook.com/search?query="
													+ detailPeople.getName(false)));
									startActivity(intent);
									break;

								case 2:
									startEmailSending(detailPeople);
									break;

								case 3:
									startCallPhone(detailPeople);
									break;

								case 4:
									startViewMap(detailPeople);
									break;

								default:
									break;
								}

							}

						});
					}

				});
				t.start();

			}

		});
		AlertDialog alert = builder.create();
		alert.show();

	}

	private void startViewMap(People detailPeople) {
		String address = detailPeople.getAddress();
		if (address != null) {
			Intent intent = new Intent(this, CourseMap.class);
			intent.putExtra(CourseMap.VIEW_TYPE, CourseMap.VIEW_BY_ADDRESS);
			intent.putExtra(CourseMap.ADDRESS_KEY, address);
			startActivity(intent);
		} else {
			SimpleTask.errorMessage(this,
					R.string.purdue_directory_list_menu_no_address_found_message, false);
		}

	}

	private void createDetailInfoDialog(People detailPeople) {

		LayoutInflater mInflater = (LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = mInflater.inflate(R.layout.purdue_directory_detail_list, null);
		ListView listview = (ListView) view.findViewById(R.id.purdue_directory_detail_listview);
		listview.setAdapter(new DirectoryDetailAdapter(detailPeople, this));

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.purdue_directory_list_detail_dialog_title);
		builder.setIcon(android.R.drawable.ic_dialog_info);
		builder.setView(view);
		builder.setNegativeButton(R.string.simple_task_errormessage_cancel_button,
				new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();

					}
				});
		AlertDialog alert = builder.create();
		alert.show();

	}

	private void startEmailSending(People detailPeople) {
		String emailAddress = detailPeople.getEmail();
		if (emailAddress == null) {
			SimpleTask.errorMessage(this,
					R.string.purdue_directory_list_send_email_address_not_found, false);
		} else {
			Intent intent = new Intent(android.content.Intent.ACTION_SEND);
			intent.setType("plain/text");
			intent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] { emailAddress });

			startActivity(Intent.createChooser(intent, "Send Email As..."));

		}
	}

	private void startCallPhone(People detailPeople) {
		final String[][] phones = detailPeople.getPhone();
		if (phones == null) {
			SimpleTask.errorMessage(this, R.string.purdue_directory_list_call_phone_no_phone_found,
					false);
		} else if (phones.length == 1) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			switch (Integer.valueOf(phones[0][0])) {
			case People.HOME_PHONE:
				builder.setTitle(R.string.purdue_directory_list_call_phone_title);
				builder.setItems(new String[] { "Call Resident Phone" }, new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(android.content.Intent.ACTION_CALL);
						intent.setData(Uri.parse("tel:" + phones[0][1]));
						startActivity(intent);

					}
				});
				builder.create().show();
				break;
			case People.OFFICE_PHONE:
				builder.setTitle(R.string.purdue_directory_list_call_phone_title);
				builder.setItems(new String[] { "Call Office Phone" }, new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(android.content.Intent.ACTION_CALL);
						intent.setData(Uri.parse("tel:" + phones[0][1]));
						startActivity(intent);

					}
				});
				builder.create().show();
				break;
			default:
				SimpleTask.errorMessage(this,
						R.string.purdue_directory_list_call_phone_unknown_error, false);
				break;
			}
		} else if (phones.length == 2) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(R.string.purdue_directory_list_call_phone_title);
			builder.setItems(new String[] {
					(Integer.valueOf(phones[0][0]) == People.OFFICE_PHONE ? "Call Office Phone"
							: "Call Home Phone"),
					(Integer.valueOf(phones[1][0]) == People.OFFICE_PHONE ? "Call Office Phone"
							: "Call Home Phone") }, new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					switch (which) {
					case 0:
						Intent intent = new Intent(android.content.Intent.ACTION_CALL);
						intent.setData(Uri.parse("tel:" + phones[0][1]));
						startActivity(intent);
						break;
					case 1:
						Intent intent2 = new Intent(android.content.Intent.ACTION_CALL);
						intent2.setData(Uri.parse("tel:" + phones[1][1]));
						startActivity(intent2);
						break;
					default:
						SimpleTask.errorMessage(getBaseContext(),
								R.string.purdue_directory_list_call_phone_unknown_error, false);
						break;
					}

				}
			});
			builder.create().show();
		} else {
			SimpleTask.errorMessage(this, R.string.purdue_directory_list_call_phone_unknown_error,
					false);
		}
	}
}