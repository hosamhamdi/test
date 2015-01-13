package com.example.nfr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class NewsCategoryFragment extends ListFragment implements
		OnItemSelectedListener, OnItemClickListener {

	private String url_GET_Category_AR;// = "http://fateh.innocastle.com/api/get_category_posts/?lang=ar&slug=";
	private ArrayList<HashMap<String, String>> categories;// show categories in spinner
	private ArrayList<HashMap<String, String>> hashmapList;
	final String TAG_Key = "id"; // spinner id
	final String TAG_Value = "value";// spinner value

	private ListView lv;
	private JSONParser jsonParser;
	private JSONArray jsonArray;
	String lang;
	
	private static final String TAG_SUCCESS = "status";// ok
	private static final String TAG_id = "id";
	private static final String TAG_type = "type";
	private static final String TAG_slug = "slug";
	private static final String TAG_url = "url";
	private static final String TAG_thumbnail = "thumbnail";
	private static final String TAG_title_plain = "title_plain";
	private static final String TAG_content = "content";
	private static final String TAG_excerpt = "excerpt";
	private static final String TAG_date = "date";
	private static final String TAG_modified = "modified";
	private static final String TAG_posts = "posts";

	private String key;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.news_category_fragment,
				container, false);
		Spinner spinner = (Spinner) rootView.findViewById(R.id.spNewsCategory);

		categories = initializeSpinner(spinner);

		hashmapList = new ArrayList<HashMap<String, String>>();
		jsonParser = new JSONParser();
		return rootView;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		lv = getListView();
		
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// getting values from selected ListItem
				 Intent intent = new Intent(getActivity(), NewsDetailsActivity.class);
				 intent.putExtra("id", hashmapList.get(position).get(TAG_slug));
				 
		            ((MainActivity) getActivity()).startActivity(intent);

				

			}
		});


		
		
		new loadData().execute();
		
		//key="";
		
		
		//hashmapList.clear();
		
		//lv.refreshDrawableState();
		String key = categories.get(position).get(TAG_Key).toString();
		Resources res = getResources();
		//
				Locale current = getResources().getConfiguration().locale;
				Log.d("current", current.toString());
		lang = current.getLanguage();
		this.key = key;
		if (lang.contains("en")) {
			String url_GET_Category_AR = "http://fateh.innocastle.com/api/get_category_posts/?lang=en&slug=";
			this.url_GET_Category_AR=url_GET_Category_AR+key;
//			Toast.makeText(parent.getContext(), "Selected: " + key,
//					Toast.LENGTH_LONG).show();
			Log.d("keyid", key);
		}else
		{
			String url_GET_Category_AR = "http://fateh.innocastle.com/api/get_category_posts/?lang=ar&slug=";
			this.url_GET_Category_AR=url_GET_Category_AR+key;
//			Toast.makeText(parent.getContext(), "Selected: " + key,
//					Toast.LENGTH_LONG).show();
			Log.d("keyid", key);
		}
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}

	public ArrayList<HashMap<String, String>> initializeSpinner(Spinner spinner) {
		Resources res = getResources();
		//
				Locale current = getResources().getConfiguration().locale;
				Log.d("current", current.toString());
			lang = current.getLanguage();
		String[] keyAr = { "newspaper-ar", "football-news-ar",
				"basketball-news-ar", "swimming-news-ar", "bicycles-news-ar",
				"tennis-news-ar", "taekwondo-news-ar", "other-news-ar" };
		String[] valueAr = { "الفتح في الصحافة", "اأخبار كرة القدم",
				"أخبار كرة السلة", "أخبار السباحة", "أخبار الدراجات",
				"أخبار التنس", "أخبار التايكوندو", "أخبار آخري" };
		String[] keyen = { "newspaper", "football-news",
				"basketball-news", "swimming-news", "bicycles-news",
				"tennis-news", "taekwondo-news", "other-news" };
		String[] valueen = { "Fateh in Newspaper", "Football News",
				"Basketball News", "Swimming News", "Bicycles News",
				"Tennis News", "Taekwondo News", "Other News" };
		spinner.setOnItemSelectedListener(this);

		ArrayList<HashMap<String, String>> categories = new ArrayList<HashMap<String, String>>();

		for (int i = 0; i < 8; i++) {
			HashMap<String, String> map = new HashMap<String, String>();
			if (lang.contains("en")) {
				map.put(TAG_Key, keyen[i]);
				map.put(TAG_Value, valueen[i]);	
			}else
			{
				map.put(TAG_Key, keyAr[i]);
				map.put(TAG_Value, valueAr[i]);				
			}

			categories.add(map);

		}
		ListAdapter dataAdapter = new SimpleAdapter(getActivity()
				.getApplicationContext(), categories,
				android.R.layout.simple_spinner_dropdown_item, new String[] {
						TAG_Value, TAG_Key }, new int[] { android.R.id.text1,
						android.R.id.text2 });
		spinner.setAdapter((SpinnerAdapter) dataAdapter);
		((SimpleAdapter) dataAdapter)
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter((SpinnerAdapter) dataAdapter);
		return categories;

		// ArrayAdapter<String> dataAdapter = new
		// ArrayAdapter<String>(getActivity().getApplicationContext(),
		// android.R.layout.simple_spinner_item, cat);

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);

		

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

			getActivity().runOnUiThread(new Runnable() {
				public void run() {
					/**
					 * Updating parsed JSON data into ListView
					 * */
					lv.refreshDrawableState();
					
					customArray adapter = new customArray();

					setListAdapter(adapter);
					//hashmapList.clear();

				}
			});

		}

	}

	class customArray extends BaseAdapter {

		@SuppressWarnings("deprecation")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			// getSystemService() inside Context Class

			View v = fngetViewListAdapter(position, parent);

			return v;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return hashmapList.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

	}

	public View fngetViewListAdapter(int position, ViewGroup parent) {
		LayoutInflater li = (LayoutInflater) getActivity()
				.getApplicationContext().getSystemService(
						Context.LAYOUT_INFLATER_SERVICE);
		View v = li.inflate(R.layout.adapter_news, parent, false);
		ImageView iv = (ImageView) v.findViewById(R.id.ivNewsImage);
		TextView tvNewsDate = (TextView) v.findViewById(R.id.tvNewsDate);
		TextView tvNewsdetails = (TextView) v.findViewById(R.id.tvNewsdetails);
		TextView tvNewstitle = (TextView) v.findViewById(R.id.tvNewstitle);

		Picasso.with(getActivity().getApplicationContext())
				.load(hashmapList.get(position).get(TAG_thumbnail)).into(iv);

		tvNewsDate.setText(hashmapList.get(position).get(TAG_date));
		tvNewsdetails.setText(hashmapList.get(position).get(TAG_content));
		tvNewstitle.setText(hashmapList.get(position).get(TAG_title_plain));

		return v;
	}

	public void fnProgressDialog(String msg) {

	}

	public void fnLoadData() {
			//List<NameValuePair> params = new ArrayList<NameValuePair>();
		
			//params.add(new BasicNameValuePair("COID", coid));

		hashmapList.clear();
			List<NameValuePair> params = new ArrayList<NameValuePair>();	 
		 
		
			JSONObject json = jsonParser.makeHttpRequest(url_GET_Category_AR,
					"GET",params);
			Log.d("keyid", url_GET_Category_AR);
			
//			params.add(new BasicNameValuePair(TAG_Key,
//					key));
			try {
				// Checking for SUCCESS TAG
				String success = json.getString(TAG_SUCCESS);
				
				if (success.equals("ok")  ) {
					// products found
					// Getting Array of Products
					
					jsonArray = json.getJSONArray(TAG_posts);

					// looping through All Products
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject c = jsonArray.getJSONObject(i);

						// Storing each json item in variable
						String id = c.getString(TAG_id);
						String date = c.getString(TAG_date);
						String content = c.getString(TAG_content);
						String excerpt = c.getString(TAG_excerpt);
						String modified = c.getString(TAG_modified);
						String slug = c.getString(TAG_slug);
						String title_plain = c.getString(TAG_title_plain);
						String type = c.getString(TAG_type);
						String url= c.getString(TAG_url);
						String thumbnail= c.getString(TAG_thumbnail);
						//String Attachment = c.getString(Tag_msan_Attachment);

						// creating new HashMap
						HashMap<String, String> map = new HashMap<String, String>();

						// adding each child node to HashMap key => value
						map.put(TAG_id, id);
						map.put(TAG_date, date);
						map.put(TAG_content, content);
						map.put(TAG_excerpt, excerpt);
						map.put(TAG_modified, modified);
						map.put(TAG_slug, slug);
						map.put(TAG_title_plain, title_plain);
						map.put(TAG_type, type);
						map.put(TAG_url, url);
						map.put(TAG_thumbnail, thumbnail);
						
						//map.put(Tag_msan_Attachment, Attachment);

						// adding HashList to ArrayList
						hashmapList.add(map);
					}
				} else {
					
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

}
