package tabsswipe.adapter;

import com.example.nfr.MatchesFragment;
import com.example.nfr.NewsCategoryFragment;
import com.example.nfr.NewsFragment;
import com.example.nfr.RankFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter {
	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:

			return new NewsFragment();
		case 1:
			return new NewsCategoryFragment();

		case 2:
			return new MatchesFragment();

		case 3:
			return new RankFragment();

		}

		return null;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 4;
	}

}
