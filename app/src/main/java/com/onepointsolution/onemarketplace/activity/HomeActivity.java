package com.onepointsolution.onemarketplace.activity;

import android.net.Uri;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.onepointsolution.onemarketplace.R;
import com.onepointsolution.onemarketplace.fragment.HomeFragment;
import com.onepointsolution.onemarketplace.fragment.NewsFragment;
import com.onepointsolution.onemarketplace.fragment.NotificationsFragment;
import com.onepointsolution.onemarketplace.fragment.RestaurantsFragment;
import com.onepointsolution.onemarketplace.fragment.SettingsFragment;
import com.onepointsolution.onemarketplace.fragment.ShopFragment;
import com.onepointsolution.onemarketplace.fragment.TravelFragment;
import com.onepointsolution.onemarketplace.utils.CircleTransform;

public class HomeActivity extends AppCompatActivity implements HomeFragment.OnFragmentInteractionListener,
        RestaurantsFragment.OnFragmentInteractionListener, NewsFragment.OnFragmentInteractionListener,
        ShopFragment.OnFragmentInteractionListener, TravelFragment.OnFragmentInteractionListener,
        SettingsFragment.OnFragmentInteractionListener, NotificationsFragment.OnFragmentInteractionListener{

    private static final String TAG = HomeActivity.class.getSimpleName();


    // Adding navigation drawable functionality
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private ImageView imgNavHeaderBg, imgProfile;
    private TextView txtName, txtWebsite;
    private Toolbar toolbar;

    // urls to load navigation header background image
    // and profile image
    private static final String urlNavHeaderBg = "https://api.androidhive.info/images/nav-menu-header-bg.jpg";
    private static final String urlProfileImg = "https://media.licdn.com/dms/image/C4E03AQHEeTThCpevXg/profile-displayphoto-shrink_200_200/0?e=1556150400&v=beta&t=bcdvR-jOnQcVk7W6461Or8BQsGefdIzhO080eiMgc68";
    // index to identify current nav menu item
    public static int navItemIndex = 0;

    // tags used to attach the fragments
    private static final String TAG_HOME = "home";
    public static final String TAG_FOOD = "food";
    public static final String TAG_NEWS = "news";
    public static final String TAG_SHOP = "shop";
    public static final String TAG_TRAVEL = "travel";
    private static final String TAG_NOTIFICATIONS = "notifications";
    private static final String TAG_SETTINGS = "settings";
    public static String CURRENT_TAG = TAG_HOME;

    // toolbar titles respected to selected nav menu item
    private String[] activityTitles;

    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mHandler = new Handler();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        navHeader = navigationView.getHeaderView(0);
        txtName = (TextView) navHeader.findViewById(R.id.name);
        txtWebsite = (TextView) navHeader.findViewById(R.id.website);
        imgNavHeaderBg = (ImageView) navHeader.findViewById(R.id.img_header_bg);
        imgProfile = (ImageView) navHeader.findViewById(R.id.img_profile);

        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);

        loadNavHeader();
        setUpNavigationView();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();
        }
    }

    private void loadNavHeader() {
        txtName.setText("Hemanth Gadi");
        txtWebsite.setText("www.1pointsolution.com");

        Glide.with(this).load(urlNavHeaderBg)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgNavHeaderBg);

        Glide.with(this).load(urlProfileImg)
                .crossFade()
                .thumbnail(0.5f)
                .bitmapTransform(new CircleTransform(this))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgProfile);

        navigationView.getMenu().getItem(5).setActionView(R.layout.menu_dot);
    }

    private void loadHomeFragment() {
        selectNavMenu();
        setToolbarTitle();

        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();
            return;
        }

        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

        drawer.closeDrawers();
        invalidateOptionsMenu();
    }

    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                // home fragment
                HomeFragment homeFragment = new HomeFragment();
                return homeFragment;
            case 1:
                // food fragment
                RestaurantsFragment restaurantsFragment = new RestaurantsFragment();
                return restaurantsFragment;
            case 2:
                // news fragment
                NewsFragment newsFragment = new NewsFragment();
                return newsFragment;
            case 3:
                // shop fragment
                ShopFragment shopFragment = new ShopFragment();
                return shopFragment;
            case 4:
                // travel fragment
                TravelFragment travelFragment = new TravelFragment();
                return travelFragment;

            case 5:
                // notifications fragment
                NotificationsFragment notificationsFragment = new NotificationsFragment();
                return notificationsFragment;

            case 6:
                // settings fragment
                SettingsFragment settingsFragment = new SettingsFragment();
                return settingsFragment;
            default:
                return new HomeFragment();
        }
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void setUpNavigationView() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.nav_home:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_HOME;
                        break;
                    case R.id.nav_food:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_FOOD;
                        break;
                    case R.id.nav_news:
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_NEWS;
                        break;
                    case R.id.nav_shop:
                        navItemIndex = 3;
                        CURRENT_TAG = TAG_SHOP;
                        break;
                    case R.id.nav_travel:
                        navItemIndex = 4;
                        CURRENT_TAG = TAG_TRAVEL;
                        break;
                    case R.id.nav_notifications:
                        navItemIndex = 5;
                        CURRENT_TAG = TAG_NOTIFICATIONS;
                        break;
                    case R.id.nav_settings:
                        navItemIndex = 6;
                        CURRENT_TAG = TAG_SETTINGS;
                        break;
                    case R.id.nav_about_us:
                        // launch new intent instead of loading fragment
                       // startActivity(new Intent(HomeActivity.this, AboutUsActivity.class));
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_privacy_policy:
                        // launch new intent instead of loading fragment
                        //startActivity(new Intent(HomeActivity.this, PrivacyPolicyActivity.class));
                        drawer.closeDrawers();
                        return true;
                    default:
                        navItemIndex = 0;
                }

                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                loadHomeFragment();

                return true;
            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawer.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }

        if (shouldLoadHomeFragOnBackPress) {
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;
                loadHomeFragment();
                return;
            }
        }

        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (navItemIndex == 0) {
            getMenuInflater().inflate(R.menu.main, menu);
        }

        if (navItemIndex == 5) {
            getMenuInflater().inflate(R.menu.notifications, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            Toast.makeText(getApplicationContext(), "Logout user!", Toast.LENGTH_LONG).show();
            return true;
        }

        if (id == R.id.action_mark_all_read) {
            Toast.makeText(getApplicationContext(), "All notifications marked as read!", Toast.LENGTH_LONG).show();
        }

        if (id == R.id.action_clear_notifications) {
            Toast.makeText(getApplicationContext(), "Clear all notifications!", Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(String tag) {
        switch (tag) {
            case TAG_HOME:
                navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;
                break;
            case TAG_FOOD:
                navItemIndex = 1;
                CURRENT_TAG = TAG_FOOD;
                break;
            case TAG_NEWS:
                navItemIndex = 2;
                CURRENT_TAG = TAG_NEWS;
                break;
            case TAG_SHOP:
                navItemIndex = 3;
                CURRENT_TAG = TAG_SHOP;
                break;
            case TAG_TRAVEL:
                navItemIndex = 4;
                CURRENT_TAG = TAG_TRAVEL;
                break;
            default:
                navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;
        }
        loadHomeFragment();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
