package com.example.nfr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class NewsDetailsActivity extends Activity {

	ListView lv;
	ProgressDialog pDialog;
	JSONParser jsonParser;
	JSONArray jsonArray;
	ArrayList<HashMap<String, String>> hashmapList;

	String url_GET_News_AR = "http://fateh.innocastle.com/api/get_post/?";// id=145
																			// or
																			// slug=اختبار-5;
	String IDorSlug;
	String title;
	String content;
	String thumb; 
	String lang;
	private static final String TAG_id = "id";
	private static final String TAG_posts = "post";// json array
	private static final String TAG_type = "type";
	private static final String TAG_slug = "slug";
	private static final String TAG_url = "url";
	private static final String TAG_thumbnail = "thumbnail";
	private static final String TAG_title_plain = "title_plain";
	private static final String TAG_title = "title";
	private static final String TAG_content = "content";
	private static final String TAG_excerpt = "excerpt";
	private static final String TAG_date = "date";
	private static final String TAG_modified = "modified";
	private static final String TAG_SUCCESS = "status";// ok

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.news_details_activity);
		hashmapList = new ArrayList<HashMap<String, String>>();
		jsonParser = new JSONParser();
		
		
		
		IDorSlug = "id="+getIntent().getStringExtra("id");
		Log.d("IDORSLUG", IDorSlug);
		url_GET_News_AR = url_GET_News_AR + IDorSlug;
		new loadData().execute();
	}

	class loadData extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// fnProgressDialog("Loading Data....");
		}

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub

			fnLoadData();

			return null;
		}

		protected void onPostExecute(String file_url) {
			// dismiss the dialog after getting all products
			// pDialog.dismiss();

			/**
			 * Updating parsed JSON data into ListView
			 * */

			runOnUiThread(new Runnable() {
				public void run() {
					/**
					 * Updating parsed JSON data into ListView
					 * */

					ImageView iv = (ImageView) findViewById(R.id.ivNewsDetails);
					TextView tvNewsDetailsTitle = (TextView) findViewById(R.id.tvNewsDetailsTitle);
					TextView tvNewsDetailsContent = (TextView) findViewById(R.id.tvNewsDetailsContent);

					Picasso.with(NewsDetailsActivity.this)
							.load(thumb)
							.into(iv);


					tvNewsDetailsTitle.setText(title);
					
					tvNewsDetailsContent.setText(content);
				}
			});

		}

	}

	public void fnProgressDialog(String msg) {
		pDialog = new ProgressDialog(NewsDetailsActivity.this);
		pDialog.setMessage(msg + " Please wait...");
		pDialog.setIndeterminate(false);
		pDialog.setCancelable(false);
		pDialog.show();
	}

	public void fnLoadData() {
		// List<NameValuePair> params = new ArrayList<NameValuePair>();

		// params.add(new BasicNameValuePair("COID", coid));

		List<NameValuePair> params = new ArrayList<NameValuePair>();

		// Log.d("url",url_GET_News_AR);

		JSONObject json = jsonParser.makeHttpRequest(url_GET_News_AR, "GET",
				params);
		try {
			parseJson(json);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void parseJson(JSONObject data) throws JSONException {
		HashMap<String, String> map = new HashMap<String, String>();
		if (data != null) {
			Iterator<String> it = data.keys();
			while (it.hasNext()) {
				String key = it.next();

				try {
					if (data.get(key) instanceof JSONArray) {
						JSONArray arry = data.getJSONArray(key);
						int size = arry.length();
						for (int i = 0; i < size; i++) {
							parseJson(arry.getJSONObject(i));
						}
					} else if (data.get(key) instanceof JSONObject) {
						parseJson(data.getJSONObject(key));
					} else {
						Log.d("key", key + " : " + data.optString(key));

						if (key.equals("title")) {
							title = data.optString(key);
							map.put(TAG_title, title);
							Log.d("title",title);
						} else if (key.equals("title_plain")) {
							content = data.optString(key);
							map.put(TAG_title_plain, content);
							Log.d("content",content);
						} 
						else if (key.equals("thumbnail")) {
							thumb = data.optString(key);
							map.put(TAG_thumbnail, thumb);
							Log.d("thumb", thumb);
						}
						hashmapList.add(map);
					}
				} catch (Throwable e) {
					Log.d("Key : ", key + " : " + data.optString(key));
					e.printStackTrace();

				}
			}
		}

		Log.d("hashsize",""+hashmapList.size());
	}

}
