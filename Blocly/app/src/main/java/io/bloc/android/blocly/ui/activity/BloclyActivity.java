package io.bloc.android.blocly.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

import io.bloc.android.blocly.R;
import io.bloc.android.blocly.ui.adapter.ItemAdapter;

public class BloclyActivity extends Activity {

    private ItemAdapter itemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blocly);

     /* TextView tv = (TextView) findViewById(R.id.hello);
      * tv.setText("Hello, universe!");
      */
     /*Toast.makeText(this,
      *    BloclyApplication.getSharedDataSource().getFeeds().get(0).getTitle(),
      *    Toast.LENGTH_LONG).show();
      */
        itemAdapter = new ItemAdapter();


        ImageView iv = (ImageView) findViewById(R.id.iv_background);
        ImageLoader.getInstance().displayImage("https://www.petfinder.com/wp-content/uploads/2012/11/122163343-conditioning-dog-loud-noises-632x475.jpg", iv);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_activity_blocly);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(itemAdapter);


    }
}
