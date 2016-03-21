package com.eo.videoremote.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eo.videoremote.MainActivity;
import com.eo.videoremote.R;
import com.eo.videoremote.interfaces.ThreadCallback;
import com.eo.videoremote.interfaces.VideoList;
import com.eo.videoremote.interfaces.VideoSelected;
import com.eo.videoremote.network.VideoListFactory;
import com.eo.videoremote.models.Video;
import com.eo.videoremote.utils.ItemClickSupport;
import com.eo.videoremote.utils.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tom on 18.3.2016.
 */
public class VideoListFragment extends Fragment implements ItemClickSupport.OnItemClickListener, ItemClickSupport.OnItemLongClickListener {
    private static final java.lang.String TAG = VideoListFragment.class.getSimpleName();
    private static final String KEY_BUNDLE_PATH = "videoListPath";
    private ProgressDialog mProgressDialog;
    private ViewGroup mContainer;
    private VideoSelected mActivityCallback;
    private String mPath;

    /**
     * @param path from where to show the list of files. If null will will default to pre set one.
     * @return
     */
    public static Fragment newInstance(String path) {
        Fragment fragment = new VideoListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_BUNDLE_PATH, path);
        fragment.setArguments(bundle);

        return fragment;
    }

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setTitle("Fetching...");
        mProgressDialog.setCancelable(false);
        mActivityCallback = (VideoSelected) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPath = getArguments().getString(KEY_BUNDLE_PATH);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(mContainer != null) return mContainer; // Is this legal?

        mContainer = (ViewGroup) inflater.inflate(R.layout.fragment_videolist, container, false);
        mRecyclerView = (RecyclerView) mContainer.findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new VideoListAdapter(this, mPath);
        mRecyclerView.setAdapter(mAdapter);

        ItemClickSupport.addTo(mRecyclerView).setOnItemClickListener(this);
//        ItemClickSupport.addTo(mRecyclerView).setOnItemLongClickListener(this);

        return mContainer;
    }

    @Override
    public void onResume() {
        super.onResume();
        mActivityCallback.setTitle(mPath);
    }

    @Override
    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
        if (recyclerView.getAdapter() instanceof VideoListAdapter) {
            VideoListAdapter adapter = (VideoListAdapter) recyclerView.getAdapter();
            Video video = adapter.getItem(position);
            mActivityCallback.onVideoSelected(video);

//            Logger.debug(TAG, "onItemClicked: " + position + " name: " + video);
        }
    }

    @Override
    public boolean onItemLongClicked(RecyclerView recyclerView, int position, View v) {
        Logger.debug(TAG, "onItemLongClicked: " + position);

        return true;
    }

    void showProgressDialog() {
        mProgressDialog.show();
    }

    void hideProgressDialog() {
        mProgressDialog.hide();
//        mProgressDialog = null;
    }

    void showErrorDialog(Exception exception) {
        DialogFragment fragment = (DialogFragment) ErrorDialogFragment.newInstance(exception.toString());
        fragment.show(getFragmentManager(), "errorDialog");
    }


    private static class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.ViewHolder> implements ThreadCallback<List<Video>> {
        private static final java.lang.String TAG = VideoListAdapter.class.getSimpleName();
        private final List<Video> mDataset = new ArrayList<>();
        private final VideoListFragment mFragment;
        private final VideoList mVideoListProvider;

        // Provide a suitable constructor (depends on the kind of dataset)
        public VideoListAdapter(VideoListFragment videoListFragment, String path) {
            mFragment = videoListFragment;

            mVideoListProvider = VideoListFactory.getInstance();
            mVideoListProvider.fetchVideoList(path, this);
        }

        // Create new views (invoked by the layout manager)
        @Override
        public VideoListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {
            // create a new view
            ViewGroup container = (ViewGroup) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_videolist, parent, false);            // set the view's size, margins, paddings and layout parameters
//            container.setOnClickListener(this);

            ViewHolder holder = new ViewHolder(container);

            return holder;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element

            Video video = mDataset.get(position);
            holder.mTextView.setText(video.getName());
            holder.mTextView.setTextColor(video.isDirectory() ? Color.BLUE : Color.GREEN);
        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mDataset.size();
        }

        public Video getItem(int position) {
            return mDataset.get(position);
        }

        @Override
        public void workerStart() {
            mFragment.showProgressDialog();
        }

        @Override
        public void workerEnd(List<Video> result, Exception exception) {
            mFragment.hideProgressDialog();

            if (exception == null) {
                mDataset.clear();
                mDataset.addAll(result);
                notifyDataSetChanged();
            } else {
                mFragment.showErrorDialog(exception);
                Logger.error(TAG, "", exception);
            }
        }

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public static class ViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            public TextView mTextView;

            public ViewHolder(ViewGroup itemView) {
                super(itemView);
                mTextView = (TextView) itemView.findViewById(R.id.cardview_textview);
            }
        }
    }
}
