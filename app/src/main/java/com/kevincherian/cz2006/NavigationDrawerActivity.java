package com.kevincherian.cz2006;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.parse.ParseUser;

import java.util.ArrayList;

/**
 * Created by hp on 3/14/2015.
 */
public class NavigationDrawerActivity extends ActionBarActivity{
    TextView textView;

    //Navigation Drawer code
    class NavItem {
        String mTitle;
        String mSubtitle;
        int mIcon;

        public NavItem(String title, String subtitle, int icon) {
            mTitle = title;
            mSubtitle = subtitle;
            mIcon = icon;
        }
    }

    class DrawerListAdapter extends BaseAdapter {

        Context mContext;
        ArrayList<NavItem> DrawerItems;

        public DrawerListAdapter(Context context, ArrayList<NavItem> navItems)
        {
            mContext = context;
            DrawerItems = navItems;
        }

        @Override
        public int getCount() {
            return DrawerItems.size();
        }

        @Override
        public Object getItem(int position) {
            return DrawerItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;


            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.drawer_items, null);
            }
            else {
                view = convertView;
            }

            TextView titleView = (TextView) view.findViewById(R.id.title);
            TextView subtitleView = (TextView) view.findViewById(R.id.subtitle);
            ImageView iconView = (ImageView) view.findViewById(R.id.icon);

            titleView.setText( DrawerItems.get(position).mTitle );
            subtitleView.setText( DrawerItems.get(position).mSubtitle );
            iconView.setImageResource(DrawerItems.get(position).mIcon);

            return view;
        }
    }
    // end of Navigation Drawer code

    //drawer declaration
    ListView DrawerList;
    RelativeLayout DrawerPane;
    private android.support.v4.widget.DrawerLayout DrawerLayout;
    ArrayList<NavItem> DrawerItems = new ArrayList<NavItem>();
    private ActionBarDrawerToggle toggle;
    //end of drawer declaration


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);


        //set drawer view
        DrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        DrawerPane = (RelativeLayout) findViewById(R.id.drawerPane);
        DrawerList = (ListView) findViewById(R.id.navList);

        //action bar for drawer
        Toolbar tool_bar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(tool_bar);
        toggle = new ActionBarDrawerToggle(this, DrawerLayout, tool_bar, R.string.drawer_open, R.string.drawer_close);
        toggle.setDrawerIndicatorEnabled(true);
        DrawerLayout.setDrawerListener(toggle);
        //end of action bar for drawer


        //set userer name appearance
        UserInfoRetrievalInterface UserInfo = new ParseUserInfoRetrieval();
        TextView username=(TextView)findViewById(R.id.PatientName);
        username.setText("Welcome," + UserInfo.getFirstName()+ " " + UserInfo.getLastName());
        //end of set names
        setDrawerITem();




        DrawerListAdapter adapter = new DrawerListAdapter(this, DrawerItems);
        DrawerList.setAdapter(adapter);

        // Drawer Item click listeners
        DrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItemFromDrawer(position);
            }
        });
        //end of drawer
        populateListView();
        registerClickCallBack();

    }
    private void setDrawerITem()
    {
        DrawerItems.add(new NavItem("Profile", "view profile", R.drawable.drawer_profile));
        DrawerItems.add(new NavItem("Medic", "Handle your appointments", R.drawable.drawer_medic));
        DrawerItems.add(new NavItem("Medical Record", "My history", R.drawable.drawer_setting));
        DrawerItems.add(new NavItem("Logout", "Goodbye", R.drawable.drawer_logout));
    }
    private void selectItemFromDrawer(int position)
    {

        ListFragment newFragmentList;
        Fragment newFragment;
        FragmentTransaction transaction =getFragmentManager().beginTransaction();
        switch(position){
            case 0:
                newFragment= new UserProfileFragment();
                transaction.replace(R.id.mainContent,newFragment);
                break;
            case 1:
                newFragment= new UserProfileFragment();
                transaction.replace(R.id.mainContent,newFragment);
                break;
            case 2:
                newFragment= new MedicalRecordFragment();
                transaction.replace(R.id.mainContent,newFragment);
                break;


        }
        transaction.addToBackStack(null);
        transaction.commit();
        //onBackPressed();
        DrawerList.setItemChecked(position, true);
        setTitle(DrawerItems.get(position).mTitle);

        // Close the drawer
        DrawerLayout.closeDrawer(DrawerPane);
    }

    //burger
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }
    //end of burger


    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            this.finish();
        } else {
            getFragmentManager().popBackStack();
        }
    }    //end of drawer



    private void populateListView()
    {
        String[] myItems = {"New Appointment", "View Appointment"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(NavigationDrawerActivity.this, R.layout.listview, myItems);

        ListView list = (ListView) findViewById (R.id.listView1);
        list.setAdapter(adapter);
    }

    private void registerClickCallBack(){
        ListView list = (ListView) findViewById(R.id.listView1);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick (AdapterView<?> parent, View viewClicked, int position, long id){
                if (position == 1) {
                    Intent i = new Intent(viewClicked.getContext(), ViewAppointment.class);
                    startActivity(i);
                }
                /*else if (position == 0) {
                    Intent i = new Intent(viewClicked.getContext(), .class); //insert the name of the New appointment class
                    startActivity(i);
                }
                */
            }
        });
    }
}
