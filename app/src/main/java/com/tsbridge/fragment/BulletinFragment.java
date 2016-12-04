package com.tsbridge.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tsbridge.R;
import com.tsbridge.adapter.BulletinAdapter;
import com.tsbridge.utils.Utils;
import com.tsbridge.view.RecyclerViewDivider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BulletinFragment extends Fragment {
	private Context mContext = null;
	private String mClassName = null;

	private List<Map<String, String>> mBulletins = null;
	private BulletinAdapter mBulletinAdapter = null;

	private View mRootView = null;
	private RecyclerView mRecyclerView = null;

	private final String[] mImageUrls= {"www.baidu.com", "www.baidu.com", "www.baidu.com",
			"www.baidu.com", "www.baidu.com"};
	private final String[] mNames= {"名字1", "名字2", "名字3", "名字4", "名字5"};
	private final String[] mTimes= {"时间1", "时间2", "时间3", "时间4", "时间5"};
	private final String[] mContents= {"内容1", "内容2", "内容3", "内容4", "内容5"};

    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
            final Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.bulletin_fragment, container, false);

        return mRootView;
    }
    
    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

		initParams();

		initViews();
    }

	private void initParams() {
		mContext = getActivity();

		mClassName = "";
		mBulletins = new ArrayList<>();

		mBulletinAdapter = new BulletinAdapter(mContext, mBulletins);
	}

	private void initViews() {
		mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.bulletin_recyclerview);
		LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(mContext);
		mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		mRecyclerView.setLayoutManager(mLinearLayoutManager);
		RecyclerViewDivider dividerItemDecoration = new RecyclerViewDivider(mContext,
				RecyclerViewDivider.VERTICAL_LIST);
		mRecyclerView.addItemDecoration(dividerItemDecoration);
		mRecyclerView.setAdapter(mBulletinAdapter);

		new QueryBulletinTask().execute(mClassName);
	}

	private class QueryBulletinTask extends AsyncTask<String, Void, List<Map<String, String>>> {
		@Override
		protected List<Map<String, String>> doInBackground(String... params) {
			String className = params[0];
			int length = mImageUrls.length;

			Map<String, String> bulletin = null;
			for (int i=0; i<length; ++i) {
				bulletin = new HashMap<>();
				bulletin.put(Utils.BULLETIN_IMAGE_KEY, mImageUrls[i]);
				bulletin.put(Utils.BULLETIN_NAME_KEY, mNames[i]);
				bulletin.put(Utils.BULLETIN_TIME_KEY, mTimes[i]);
				bulletin.put(Utils.BULLETIN_CONTENT_KEY, mContents[i]);
				mBulletins.add(bulletin);
			}

			return mBulletins;
		}

		@Override
		protected void onPostExecute(List<Map<String, String>> result) {
			if(result.size() > 0) {
				mBulletinAdapter.notifyDataSetChanged();
			}
		}
	}

    @Override
	public void onDestroyView() {
    	super.onDestroyView();

		if(mBulletins != null) {
			mBulletins.clear();
			mBulletins = null;
		}
    }
}