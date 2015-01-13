package com.example.nfr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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

public class MatchesFragment extends ListFragment {

	private ListView lv;
	private JSONParser jsonParser;
	private JSONArray jsonArray;
	private ArrayList<HashMap<String, String>> hashmapList;

	private  String url_get_matches = "http://fateh.innocastle.com/language/en/matches-table-api/";

	private  final String TAG_SUCCESS = "teamTable";// ok
	private  final String TAG_Competition = "competition";
	private  final String TAG_League = "league";
	private  final String TAG_Start_date = "start";
	private  final String TAG_End_date = "end";

	private  final String TAG_Id = "Id";
	private  final String TAG_competition_id = "competition_id";
	private  final String TAG_competition_name = "competition_name";
	private  final String TAG_season_id = "season_id";
	private  final String TAG_last_updated = "last_updated";
	private  final String TAG_round_id = "round_id";
	private  final String TAG_round_name = "round_name";
	private  final String TAG_round_start_date = "round_start_date";
	private  final String TAG_round_end_date = "round_end_date";
	private  final String TAG_match_id = "match_id";
	private  final String TAG_ow_match_id = "ow_match_id";
	private  final String TAG_date_utc = "date_utc";
	private  final String TAG_time_utc = "time_utc";
	private  final String TAG_team_A_name = "team_A_name";
	private  final String TAG_team_B_name = "team_B_name";
	private  final String TAG_Winner = "winner";
	private  final String TAG_Status = "status";
	private  final String TAG_Gameweek = "gameweek";
	private  final String TAG_fs_A = "fs_A";
	private  final String TAG_fs_B = "fs_B";
	private  final String TAG_hts_A = "hts_A";
	private  final String TAG_hts_B = "hts_B";
	private  final String TAG_much_session = "much_session";
	private  final String TAG_Created_date = "Created_date";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.matchesfragment, container,
				false);

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

private 	class loadData extends AsyncTask<String, String, String> {

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
					// lv.refreshDrawableState();

					customArray adapter = new customArray();

					setListAdapter(adapter);
					// hashmapList.clear();

				}
			});

		}

	}

private 	class customArray extends BaseAdapter {

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

	private View fngetViewListAdapter(int position, ViewGroup parent) {
		int[] colors = new int[] { 0x30FF0000, 0x300000FF };
		LayoutInflater li = (LayoutInflater) getActivity()
				.getApplicationContext().getSystemService(
						Context.LAYOUT_INFLATER_SERVICE);
		View v = li.inflate(R.layout.adapter_matches, parent, false);

		TextView tvWeekNoValue = (TextView) v.findViewById(R.id.tvWeekNoValue);
		TextView tvDateValue = (TextView) v.findViewById(R.id.tvDateValue);
		TextView tvTeamNameAVale = (TextView) v
				.findViewById(R.id.tvTeamNameAVale);
		TextView tvTeamNameBVale = (TextView) v
				.findViewById(R.id.tvTeamNameBVale);
		TextView tvTeamAWinLoseValue = (TextView) v
				.findViewById(R.id.tvTeamAWinLoseValue);
		TextView tvTeamBWinLoseValue = (TextView) v
				.findViewById(R.id.tvTeamBWinLoseValue);
		TextView tvTeamAScoreValue = (TextView) v
				.findViewById(R.id.tvTeamAScoreValue);
		TextView tvTeamBScoreValue = (TextView) v
				.findViewById(R.id.tvTeamBScoreValue);
		Log.d("hashmap", hashmapList.size() + "");
		tvWeekNoValue.setText(hashmapList.get(position).get(TAG_Gameweek));
		tvDateValue.setText(hashmapList.get(position).get(TAG_Created_date));
		tvTeamNameAVale.setText(hashmapList.get(position).get(TAG_team_A_name));
		tvTeamNameBVale.setText(hashmapList.get(position).get(TAG_team_B_name));
		tvTeamAWinLoseValue.setText(hashmapList.get(position).get(TAG_Winner));
		tvTeamBWinLoseValue.setText(hashmapList.get(position).get(TAG_Winner));
		tvTeamAScoreValue.setText(hashmapList.get(position).get(TAG_fs_A));
		tvTeamBScoreValue.setText(hashmapList.get(position).get(TAG_fs_B));

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

		JSONObject json = jsonParser.makeHttpRequest(url_get_matches, "GET",
				params);
		Log.d("jsonObj",json.toString());
		// params.add(new BasicNameValuePair(TAG_Key,
		// key));
		try {
			// Checking for SUCCESS TAG
			String success = json.getString(TAG_SUCCESS);
			//Log.d("success", success);
			//if (success.equals("Pro League")) {
				// products found
				// Getting Array of Products

				jsonArray = json.getJSONArray(TAG_SUCCESS);
				Log.d("jsonArray",jsonArray.length()+"");
				// looping through All Products
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject c = jsonArray.getJSONObject(i);

					// Storing each json item in variable
//					String Competition = c.getString(TAG_Competition);
//					String League = c.getString(TAG_League);
//					String Start_date = c.getString(TAG_Start_date);
//					String End_date = c.getString(TAG_End_date);
//		String Id = c.getString(TAG_Id);
					String competition_id = c.getString(TAG_competition_id);
					String competition_name = c.getString(TAG_competition_name);
					String season_id = c.getString(TAG_season_id);
					String last_updated = c.getString(TAG_last_updated);
					String round_id = c.getString(TAG_round_id);
					String round_name = c.getString(TAG_round_name);
					String round_start_date = c.getString(TAG_round_start_date);
					String round_end_date = c.getString(TAG_round_end_date);
					String match_id = c.getString(TAG_match_id);
					String ow_match_id = c.getString(TAG_ow_match_id);
					String date_utc = c.getString(TAG_date_utc);
					String time_utc = c.getString(TAG_time_utc);
					String team_A_name = c.getString(TAG_team_A_name);
					String team_B_name = c.getString(TAG_team_B_name);
					String Winner = c.getString(TAG_Winner);
					String Status = c.getString(TAG_Status);
					String Gameweek = c.getString(TAG_Gameweek);
					String fs_A = c.getString(TAG_fs_A);
					String fs_B = c.getString(TAG_fs_B);
					String hts_A = c.getString(TAG_hts_A);
					String hts_B = c.getString(TAG_hts_B);
					String much_session = c.getString(TAG_much_session);
					String Created_date = c.getString(TAG_Created_date);

					// String Attachment = c.getString(Tag_msan_Attachment);

					// creating new HashMap
					HashMap<String, String> map = new HashMap<String, String>();

					// adding each child node to HashMap key => value
	//				map.put(TAG_Competition, Competition);
					map.put(TAG_competition_id, competition_id);
					map.put(TAG_competition_name, competition_name);
					map.put(TAG_Created_date, Created_date);
					map.put(TAG_date_utc, date_utc);
			//		map.put(TAG_End_date, End_date);
					map.put(TAG_fs_A, fs_A);
					map.put(TAG_fs_B, fs_B);
					map.put(TAG_Gameweek, Gameweek);
					map.put(TAG_hts_A, hts_A);
					map.put(TAG_hts_B, hts_B);
	//				map.put(TAG_Id, Id);
					map.put(TAG_last_updated, last_updated);
		//			map.put(TAG_League, League);
					map.put(TAG_match_id, match_id);
					map.put(TAG_much_session, much_session);
					map.put(TAG_ow_match_id, ow_match_id);
					map.put(TAG_round_end_date, round_end_date);
					map.put(TAG_round_id, round_id);
					map.put(TAG_round_name, round_name);
					map.put(TAG_round_start_date, round_start_date);
					map.put(TAG_season_id, season_id);
				//	map.put(TAG_Start_date, Start_date);
					map.put(TAG_Status, Status);
					map.put(TAG_team_A_name, team_A_name);
					map.put(TAG_team_B_name, team_B_name);
					map.put(TAG_time_utc, time_utc);
					if (Winner.equals("team_A")) {
						Winner=team_A_name;
					}
					else
					{
						Winner = team_B_name;
					}
					map.put(TAG_Winner, Winner);
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
