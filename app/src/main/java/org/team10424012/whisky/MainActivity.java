package org.team10424012.whisky;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentTabHost tabhost = (FragmentTabHost) findViewById(android.R.id.tabhost);

        tabhost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);

        TabHost.TabSpec spec;
        View tabIndicator;

        spec = tabhost.newTabSpec("activity");
        tabIndicator = LayoutInflater.from(this).inflate(R.layout.tab_indicator, tabhost.getTabWidget(), false);
        ((TextView) tabIndicator.findViewById(R.id.title)).setText(getResources().getString(R.string.activity));
        ((ImageView) tabIndicator.findViewById(R.id.icon)).setImageResource(R.drawable.tab_activity);
        spec.setIndicator(tabIndicator);
        //spec.setContent(R.id.tab_activity);
        tabhost.addTab(spec, FragActivity.class, null);

        spec = tabhost.newTabSpec("post");
        tabIndicator = LayoutInflater.from(this).inflate(R.layout.tab_indicator, tabhost.getTabWidget(), false);
        ((TextView) tabIndicator.findViewById(R.id.title)).setText(getResources().getString(R.string.post));
        ((ImageView) tabIndicator.findViewById(R.id.icon)).setImageResource(R.drawable.tab_match);
        spec.setIndicator(tabIndicator);
        //spec.setContent(R.id.tab_post);
        tabhost.addTab(spec, FragPost.class, null);

        spec = tabhost.newTabSpec("message");
        tabIndicator = LayoutInflater.from(this).inflate(R.layout.tab_indicator, tabhost.getTabWidget(), false);
        ((TextView) tabIndicator.findViewById(R.id.title)).setText(getResources().getString(R.string.message));
        ((ImageView) tabIndicator.findViewById(R.id.icon)).setImageResource(R.drawable.tab_message);
        spec.setIndicator(tabIndicator);
        //spec.setContent(R.id.tab_message);
        tabhost.addTab(spec, FragMessage.class, null);

        spec = tabhost.newTabSpec("profile");
        tabIndicator = LayoutInflater.from(this).inflate(R.layout.tab_indicator, tabhost.getTabWidget(), false);
        ((TextView) tabIndicator.findViewById(R.id.title)).setText(getResources().getString(R.string.profile));
        ((ImageView) tabIndicator.findViewById(R.id.icon)).setImageResource(R.drawable.tab_personal);
        spec.setIndicator(tabIndicator);
        //spec.setContent(R.id.tab_profile);
        tabhost.addTab(spec, FragProfile.class, null);
    }

    public static class FragActivity extends Fragment {
        private Button filter;
        private ListView list;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View root =  inflater.inflate(R.layout.fragment_activity, container, false);
            filter = (Button)root.findViewById(R.id.filter);
            list = (ListView)root.findViewById(R.id.list);
            list.setAdapter(new BaseAdapter() {
                @Override
                public int getCount() {
                    return 3;
                }

                @Override
                public Object getItem(int position) {
                    return null;
                }

                @Override
                public long getItemId(int position) {
                    return 0;
                }

                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    return null;
                }
            });
            return root;
        }
    }

    public static class FragPost extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_post, container, false);
        }
    }

    public static class FragMessage extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_message, container, false);
        }
    }

    public static class FragProfile extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_profile, container, false);
        }
    }
}
