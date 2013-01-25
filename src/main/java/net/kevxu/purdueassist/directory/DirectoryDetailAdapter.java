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

public class DirectoryDetailAdapter extends BaseAdapter {

	private People people;
	private Context mContext;

	private TextView elementName;
	private TextView elementContent;
	private ArrayList<String[]> detail;

	public DirectoryDetailAdapter(People people, Context context) {
		this.people = people;
		this.mContext = context;
		detail = this.people.getDetailedInfo();

	}

	@Override
	public int getCount() {
		return detail.size();
	}

	@Override
	public Object getItem(int arg0) {
		return detail.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		String[] info = detail.get(arg0);

		LayoutInflater mInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = mInflater.inflate(R.layout.purdue_directory_detail_list_row, arg2, false);

		elementName = (TextView) view.findViewById(R.id.purdue_directory_list_detail_element_name);
		elementContent = (TextView) view
				.findViewById(R.id.purdue_directory_list_detail_element_content);

		elementName.setText(info[0]);
		elementContent.setText(info[1]);

		return view;
	}

}
