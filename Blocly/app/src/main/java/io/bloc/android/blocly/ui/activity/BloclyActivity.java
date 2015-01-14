package io.bloc.android.blocly.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import io.bloc.android.blocly.BloclyApplication;
import io.bloc.android.blocly.R;

public class BloclyActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blocly);

        TextView tv = (TextView) findViewById(R.id.hello);
        tv.setText("Hello, universe!");

        Toast.makeText(this,
                BloclyApplication.getSharedDataSource().getFeeds().get(0).getTitle(),
                Toast.LENGTH_LONG).show();
    }
}
