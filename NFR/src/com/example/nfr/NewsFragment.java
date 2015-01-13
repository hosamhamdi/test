package com.example.nfr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
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
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class NewsFragment extends ListFragment{
	
	private ListView lv ;
	private ProgressDialog pDialog;
	
	private JSONParser jsonParser ;
	private JSONArray jsonArray;
	private static String url_GET_News_AR = "http://fateh.innocastle.com/api/get_posts/?lang=ar";
	private static String url_GET_News_EN = "http://fateh.innocastle.com/api/get_posts/?lang=en/";
	String lang;
	private static final String TAG_SUCCESS = "status";//ok
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
	
	private ArrayList<HashMap<String, String>> hashmapList;
	
	
	
	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	 
	        View rootView = inflater.inflate(R.layout.newsfragment, container, false);
			 hashmapList=new ArrayList<HashMap<String, String>>();
			 jsonParser = new JSONParser();

				Resources res = getResources();
				//
						Locale current = getResources().getConfiguration().locale;
						Log.d("current", current.toString());
					lang = current.getLanguage();
	        return rootView;
	        
	        
	    }
	 
	 
	 
	 
	 @Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);

		lv = getListView();
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// getting values from selected ListItem
				 Intent intent = new Intent(getActivity(), NewsDetailsActivity.class);
				 intent.putExtra("id", hashmapList.get(position).get(TAG_id));
				 
		            ((MainActivity) getActivity()).startActivity(intent);

				

			}
		});


		new loadData().execute();
		
	}




	class loadData extends AsyncTask<String, String, String>
		{
		 
		 /**
			 * Before starting background thread Show Progress Dialog
			 * */
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				//fnProgressDialog("Loading Data....");
			}
			

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			
			fnLoadData();
			
			return null;
		}
		
		protected void onPostExecute(String file_url) {
			// dismiss the dialog after getting all products
			//pDialog.dismiss();

			
				
					/**
					 * Updating parsed JSON data into ListView
					 * */

				
					
			getActivity().runOnUiThread(new Runnable() {
				public void run() {
					/**
					 * Updating parsed JSON data into ListView
					 * */

					
					
				customArray adapter = new customArray();
					
					setListAdapter(adapter);
					
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
	LayoutInflater li = (LayoutInflater) getActivity().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	View v = li.inflate(R.layout.adapter_news, parent, false);
	ImageView iv = (ImageView) v.findViewById(R.id.ivNewsImage);
	TextView tvNewsDate = (TextView) v.findViewById(R.id.tvNewsDate);
	TextView tvNewsdetails= (TextView) v.findViewById(R.id.tvNewsdetails);
	TextView tvNewstitle= (TextView) v.findViewById(R.id.tvNewstitle);
	
	Picasso.with(getActivity().getApplicationContext()).load(hashmapList.get(position).get(TAG_thumbnail)).into(iv);
	
	tvNewsDate.setText(hashmapList.get(position).get(TAG_date));
	tvNewsdetails.setText(hashmapList.get(position).get(TAG_content));
	tvNewstitle.setText(hashmapList.get(position).get(TAG_title_plain));
	
	
	
	return v;
}
		
	 
	 public void fnProgressDialog(String msg) {
			pDialog = new ProgressDialog(getActivity().getApplicationContext());
			pDialog.setMessage(msg+" Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}
	 
	 public void fnLoadData() {
			//List<NameValuePair> params = new ArrayList<NameValuePair>();
		
			//params.add(new BasicNameValuePair("COID", coid));

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			JSONObject json ;
			if (lang.contains("en")) {
				 json = jsonParser.makeHttpRequest(url_GET_News_EN,
						"GET",params);
			}else
			{
				json = jsonParser.makeHttpRequest(url_GET_News_AR,
						"GET",params);	
			}
		 
			
	
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
						HashMap<String, String> map = new HashMap<String, String>();
						Resources res = getResources();
						JSONArray jsonArrayThumbnail;
						if (lang.contains("en")) {
							if (json != null) {
//								Iterator<String> it = json.keys();
//								while (it.hasNext()) {
//									String key = it.next();
//
//									try {
//										if (json.get(key) instanceof JSONArray) {
//											JSONArray arry = json.getJSONArray(key);
//											int size = arry.length();
//											for (int j = 0; j < size; j++) {
//												parseJson(arry.getJSONObject(i));
//											}
//										} else if (json.get(key) instanceof JSONObject) {
//											parseJson(json.getJSONObject(key));
//										} else {
//											Log.d("Key : ", key + " : " + json.optString(key));
//											if (key.equals("url")) {
//												String thumbnail = json.getString("url");
//												Log.d("thumb", thumbnail.toString());
//												map.put(TAG_thumbnail, thumbnail);												
//											}
//										}
//									} catch (Throwable e) {
//										Log.d("Key : ", key + " : " + json.optString(key));
//										e.printStackTrace();
//
//									}
//								}
//							}
//							
							

						}
						else
						{
							String thumbnail= c.getString(TAG_thumbnail);
							map.put(TAG_thumbnail, thumbnail);
						}
						
						
						
			//			
						
						//String Attachment = c.getString(Tag_msan_Attachment);

						// creating new HashMap
						

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
						
						
						//map.put(Tag_msan_Attachment, Attachment);

						// adding HashList to ArrayList
						hashmapList.add(map);
					}
				}} else {
					
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		private void parseJson(JSONObject data) {

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
							Log.d("Key : ", key + " : " + data.optString(key));
						}
					} catch (Throwable e) {
						Log.d("Key : ", key + " : " + data.optString(key));
						e.printStackTrace();

					}
				}
			}

}}
