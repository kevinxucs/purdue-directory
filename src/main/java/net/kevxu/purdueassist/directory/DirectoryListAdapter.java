/*
 * Author: Kaiwen Xu
 */

package net.kevxu.purdueassist.directory;

import java.util.ArrayList;

import net.kevxu.purdueassistant.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DirectoryListAdapter extends BaseAdapter {

	private ArrayList<People> list;
	private Context mContext;

	private TextView name;
	private TextView email;
	private TextView school;

	private String[] basicInfo;

	public DirectoryListAdapter(Context context, ArrayList<People> list) {
		this.mContext = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view;

		LayoutInflater mInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view = mInflater.inflate(R.layout.purdue_directory_list_row, parent, false);
		name = (TextView) view.findViewById(R.id.purdue_directory_list_row_name);
		email = (TextView) view.findViewById(R.id.purdue_directory_list_row_email);
		school = (TextView) view.findViewById(R.id.purdue_directory_list_row_school);

		basicInfo = list.get(position).getBasicInfo();

		name.setText(basicInfo[0]);
		email.setText("Email: " + ((basicInfo[1] == null) ? "" : basicInfo[1]));
		if (basicInfo[3] == null)
			school.setText("School: " + ((basicInfo[2] == null) ? "" : basicInfo[2]));
		else
			school.setText("Department: " + ((basicInfo[3] == null) ? "" : basicInfo[3]));

		return view;
	}

}
