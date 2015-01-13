package com.example.nfr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;



public class RankFragment extends ListFragment{

	private ListView lv;
	private JSONParser jsonParser;
	private JSONArray jsonArray;
	private ArrayList<HashMap<String, String>> hashmapList;

	private static String url_get_ranks = "http://fateh.innocastle.com/language/en/teams-table-api/";

	private static final String TAG_SUCCESS = "teamTable";// ok
	
	private static final String TAG_Competition = "competition";
	private static final String TAG_League = "league";
	private static final String TAG_Start_date = "start";
	private static final String TAG_End_date = "end";
	
	private static final String TAG_Id = "Id";
	private static final String TAG_competition_id = "competition_id";
	private static final String TAG_competition_name = "competition_name";
	private static final String TAG_season_id = "season_id";
	private static final String TAG_last_updated = "last_updated";
	private static final String TAG_round_id = "round_id";
	private static final String TAG_round_name = "round_name";
	private static final String TAG_round_start_date = "round_start_date";
	private static final String TAG_round_end_date = "round_end_date";
	private static final String TAG_Rank = "rank";
	private static final String TAG_ranking_club_name = "ranking_club_name";
	private static final String TAG_ranking_matches_total = "ranking_matches_total";
	private static final String TAG_ranking_matches_won = "ranking_matches_won";
	private static final String TAG_ranking_matches_draw = "ranking_matches_draw";
	private static final String TAG_ranking_matches_lost = "ranking_matches_lost";
	private static final String TAG_ranking_goals_pro = "ranking_goals_pro";
	private static final String TAG_ranking_goals_against = "ranking_goals_against";
//	private static final String TAG_Gameweek = "Gameweek";
	private static final String TAG_ranking_points = "ranking_points";
	private static final String TAG_Created_date = "Created_date";
	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	 
	        View rootView = inflater.inflate(R.layout.rankfragment, container, false);
	        hashmapList = new ArrayList<HashMap<String, String>>();
			jsonParser = new JSONParser();         
	        return rootView;
	    }
	 
	 @Override
		public void onViewCreated(View view, Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onViewCreated(view, savedInstanceState);
			lv = getListView();
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

				getActivity().runOnUiThread(new Runnable() {
					public void run() {
						/**
						 * Updating parsed JSON data into ListView
						 * */
//						lv.refreshDrawableState();

						customArray adapter = new customArray();

						setListAdapter(adapter);
						// hashmapList.clear();

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
			int[] colors = new int[] { 0x30FF0000, 0x300000FF };
			LayoutInflater li = (LayoutInflater) getActivity()
					.getApplicationContext().getSystemService(
							Context.LAYOUT_INFLATER_SERVICE);
			View v = li.inflate(R.layout.adapter_ranks, parent, false);

			TextView tvRankValue = (TextView) v.findViewById(R.id.tvRankValue);
			TextView tvTeamName= (TextView) v.findViewById(R.id.tvTeamName);
			TextView tvNoOfMatchesValue= (TextView) v
					.findViewById(R.id.tvNoOfMatchesValue);
			TextView tvWinsValue= (TextView) v
					.findViewById(R.id.tvWinsValue);
			TextView tvDrawsValue= (TextView) v
					.findViewById(R.id.tvDrawsValue);
			TextView tvLosesValue= (TextView) v
					.findViewById(R.id.tvLosesValue);
			TextView tvProValue = (TextView) v
					.findViewById(R.id.tvProValue);
			TextView tvAgainstValue= (TextView) v
					.findViewById(R.id.tvAgainstValue);
			TextView tvPointsValue= (TextView) v
					.findViewById(R.id.tvPointsValue);
	Log.d("hashmap", hashmapList.size()+"");
	tvRankValue.setText(hashmapList.get(position).get(TAG_Rank));
	tvTeamName.setText(hashmapList.get(position).get(TAG_ranking_club_name));
	tvNoOfMatchesValue.setText(hashmapList.get(position).get(TAG_ranking_matches_total));
	tvWinsValue.setText(hashmapList.get(position).get(TAG_ranking_matches_won));
	tvDrawsValue.setText(hashmapList.get(position).get(TAG_ranking_matches_draw));
	tvLosesValue.setText(hashmapList.get(position).get(TAG_ranking_matches_lost));
	tvProValue.setText(hashmapList.get(position).get(TAG_ranking_goals_pro));
	tvAgainstValue.setText(hashmapList.get(position).get(TAG_ranking_goals_against));
	tvPointsValue.setText(hashmapList.get(position).get(TAG_ranking_points));

			int colorPos = position % colors.length;
			v.setBackgroundColor(colors[colorPos]);
			
			return v;
		}

		public void fnProgressDialog(String msg) {

		}

		public void fnLoadData() {
			// List<NameValuePair> params = new ArrayList<NameValuePair>();

			// params.add(new BasicNameValuePair("COID", coid));

			hashmapList.clear();
			List<NameValuePair> params = new ArrayList<NameValuePair>();

			JSONObject json = jsonParser.makeHttpRequest(url_get_ranks, "GET",
					params);

			// params.add(new BasicNameValuePair(TAG_Key,
			// key));
			try {
				// Checking for SUCCESS TAG
				String success = json.getString(TAG_SUCCESS);
				Log.d("success", success);
				//if (success.equals("Pro League")) {
					// products found
					// Getting Array of Products

					jsonArray = json.getJSONArray(TAG_SUCCESS);
	
					// looping through All Products
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject c = jsonArray.getJSONObject(i);

						// Storing each json item in variable

						String competition_id = c.getString(TAG_competition_id);
						String competition_name = c.getString(TAG_competition_name);
						String season_id = c.getString(TAG_season_id);
						String last_updated = c.getString(TAG_last_updated);
						String round_id = c.getString(TAG_round_id);
						String round_name = c.getString(TAG_round_name);
						String round_start_date = c.getString(TAG_round_start_date);
						String round_end_date = c.getString(TAG_round_end_date);
						String rank = c.getString(TAG_Rank);
						String ranking_club_name = c.getString(TAG_ranking_club_name);
						String ranking_matches_total = c.getString(TAG_ranking_matches_total);
						String ranking_matches_won = c.getString(TAG_ranking_matches_won);
						String ranking_matches_draw = c.getString(TAG_ranking_matches_draw);
						String ranking_matches_lost = c.getString(TAG_ranking_matches_lost);
						String ranking_goals_pro = c.getString(TAG_ranking_goals_pro);
						String ranking_goals_against = c.getString(TAG_ranking_goals_against);
//						String Gameweek = c.getString(TAG_Gameweek);
						String ranking_points = c.getString(TAG_ranking_points);
						String Created_date = c.getString(TAG_Created_date);

						// String Attachment = c.getString(Tag_msan_Attachment);

						// creating new HashMap
						HashMap<String, String> map = new HashMap<String, String>();

						// adding each child node to HashMap key => value

						map.put(TAG_competition_id, competition_id);
						map.put(TAG_competition_name, competition_name);
						map.put(TAG_Created_date, Created_date);
						//map.put(TAG_Gameweek, Gameweek);
						map.put(TAG_last_updated, last_updated);
						map.put(TAG_round_end_date, round_end_date);
						map.put(TAG_round_id, round_id);
						map.put(TAG_round_name, round_name);
						map.put(TAG_round_start_date, round_start_date);
						map.put(TAG_season_id, season_id);
						map.put(TAG_Rank,rank);
						map.put(TAG_ranking_club_name,ranking_club_name);
						map.put(TAG_ranking_matches_total,ranking_matches_total);
						map.put(TAG_ranking_matches_won,ranking_matches_won);
						map.put(TAG_ranking_matches_draw,ranking_matches_draw);
						map.put(TAG_ranking_matches_lost,ranking_matches_lost);
						map.put(TAG_ranking_goals_pro,ranking_goals_pro);
						map.put(TAG_ranking_goals_against,ranking_goals_against);
						//map.put(TAG_Gameweek,Gameweek);
						map.put(TAG_ranking_points,ranking_points);
						map.put(TAG_Created_date,Created_date);

						
						hashmapList.add(map);
						Log.d("hashmap", ""+hashmapList.size());
					}
				//} else {

				//}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}


	 
}
