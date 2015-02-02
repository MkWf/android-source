package io.bloc.android.blocly.ui.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import io.bloc.android.blocly.BloclyApplication;
import io.bloc.android.blocly.R;
import io.bloc.android.blocly.api.DataSource;
import io.bloc.android.blocly.api.model.RssItem;

/**
 * Created by Mark on 2/1/2015.
 */
public class RssItemDetailFragment extends Fragment implements ImageLoadingListener {

    private static final String BUNDLE_EXTRA_RSS_ITEM = RssItemDetailFragment.class.getCanonicalName().concat(".EXTRA_RSS_ITEM");

    public static RssItemDetailFragment detailFragmentForRssItem(RssItem rssItem) {
        Bundle arguments = new Bundle();
        arguments.putLong(BUNDLE_EXTRA_RSS_ITEM, rssItem.getRowId());
        RssItemDetailFragment rssItemDetailFragment = new RssItemDetailFragment();
        rssItemDetailFragment.setArguments(arguments);
        return rssItemDetailFragment;
    }

    ImageView headerImage;
    TextView title;
    TextView content;
    ProgressBar progressBar;
    Toolbar toolbar;
    Menu menu;
    RssItem currentItem;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle arguments = getArguments();
        if (arguments != null) {
            long rssItemId = arguments.getLong(BUNDLE_EXTRA_RSS_ITEM);
            BloclyApplication.getSharedDataSource().fetchRSSItemWithId(rssItemId, new DataSource.Callback<RssItem>() {
                @Override
                public void onSuccess(RssItem rssItem) {
                    if (getActivity() == null) {
                        return;
                    }
                    title.setText(rssItem.getTitle());
                    content.setText(rssItem.getDescription());
                    ImageLoader.getInstance().loadImage(rssItem.getImageUrl(), RssItemDetailFragment.this);
                    currentItem = rssItem;
                }

                @Override
                public void onError(String errorMessage) {}
            });
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_rss_item_detail, container, false);
        headerImage = (ImageView) inflate.findViewById(R.id.iv_fragment_rss_item_detail_header);
        progressBar = (ProgressBar) inflate.findViewById(R.id.pb_fragment_rss_item_detail_header);
        title = (TextView) inflate.findViewById(R.id.tv_fragment_rss_item_detail_title);
        content = (TextView) inflate.findViewById(R.id.tv_fragment_rss_item_detail_content);
        toolbar = (Toolbar) inflate.findViewById(R.id.tb_fragment_rss_item_detail);

        ActionBarActivity activity = (ActionBarActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setHasOptionsMenu(true);

        return inflate;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_detail, menu);

        if(getResources().getConfiguration().orientation == getResources().getConfiguration().ORIENTATION_LANDSCAPE){
            MenuItem share = menu.findItem(R.id.action_detail_share);
            share.setVisible(false);
        }

        this.menu = menu;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_detail_share) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_TEXT,
                    String.format("%s (%s)", title.getText(), title.getUrls()));
            shareIntent.setType("text/plain");
            Intent chooser = Intent.createChooser(shareIntent, getString(R.string.share_chooser_title));
            startActivity(chooser);
        }
        if (item.getItemId() == R.id.action_detail_visit) {
            Intent viewIntent = new Intent(Intent.ACTION_VIEW);
            viewIntent.setData(Uri.parse(currentItem.getUrl()));
            Intent chooser = Intent.createChooser(viewIntent, getString(R.string.visit_chooser_title));
            startActivity(chooser);
        }
        return super.onOptionsItemSelected(item);
    }

    /*
      * ImageLoadingListener
      */

    @Override
    public void onLoadingStarted(String imageUri, View view) {
        progressBar.animate()
                .alpha(1f)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .setDuration(getActivity().getResources().getInteger(android.R.integer.config_shortAnimTime))
                .start();
        headerImage.animate()
                .alpha(0f)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .setDuration(getActivity().getResources().getInteger(android.R.integer.config_shortAnimTime))
                .start();
    }

    @Override
    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
        progressBar.animate()
                .alpha(0f)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .setDuration(getActivity().getResources().getInteger(android.R.integer.config_shortAnimTime))
                .start();
    }

    @Override
    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
        progressBar.animate()
                .alpha(0f)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .setDuration(getActivity().getResources().getInteger(android.R.integer.config_shortAnimTime))
                .start();
        headerImage.setImageBitmap(loadedImage);
        headerImage.animate()
                .alpha(1f)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .setDuration(getActivity().getResources().getInteger(android.R.integer.config_shortAnimTime))
                .start();
    }

    @Override
    public void onLoadingCancelled(String imageUri, View view) {}
}
