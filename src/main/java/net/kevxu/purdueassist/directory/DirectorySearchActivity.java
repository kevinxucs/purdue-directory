package net.kevxu.purdueassist.directory;

import net.kevxu.purdueassistant.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class DirectorySearchActivity extends Activity {

	private final String[] rangeList = new String[] { PurdueDirectoryList.RANGE_ALL,
			PurdueDirectoryList.RANEE_STUDENT, PurdueDirectoryList.RANGE_STAFF };

	private Spinner rangeSpinner;
	private EditText nameInput;
	private Button searchButton;

	private String selectedRange;
	private String inputName;

	private final Context mContext = this;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.directory_search_activity);

		// init View
		rangeSpinner = (Spinner) findViewById(R.id.directory_search_activity_range_select);
		nameInput = (EditText) findViewById(R.id.directory_search_activity_name_input);
		searchButton = (Button) findViewById(R.id.directory_search_activity_name_input);

		// init spinner
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext,
				android.R.layout.simple_spinner_item, rangeList);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		rangeSpinner.setAdapter(adapter);
		rangeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				selectedRange = rangeList[position];

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});

		// init search button
		searchButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				inputName = nameInput.getText().toString();
				if (inputName.length() == 0) {
					Toast.makeText(mContext,
							mContext.getString(R.string.directory_helper_search_dialog_name_error),
							Toast.LENGTH_SHORT).show();
				} else if (inputName.length() > 0) {
					Intent intent = new Intent(mContext, PurdueDirectoryList.class);
					intent.putExtra(PurdueDirectoryList.NAME, inputName);
					intent.putExtra(PurdueDirectoryList.RANGE, selectedRange);
					mContext.startActivity(intent);
				}

			}
		});

	}

}
