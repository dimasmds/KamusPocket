package com.riotfallen.kamuspocket.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.riotfallen.kamuspocket.R;
import com.riotfallen.kamuspocket.fragment.EngIndFragment;
import com.riotfallen.kamuspocket.fragment.IndEngFragment;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.mainActivityToolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.english_indonesia);

        drawerLayout = findViewById(R.id.mainActivityDrawerLayout);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.mainActivityNavigationView);
        navigationView.setNavigationItemSelectedListener(this);

        loadFragment(true);
    }

    private void loadFragment(boolean isEnglish) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (isEnglish) {
            EngIndFragment engIndFragment = new EngIndFragment();
            transaction.replace(R.id.mainActivityFrameLayout, engIndFragment);
            transaction.commit();
        } else {
            IndEngFragment indEngFragment = new IndEngFragment();
            transaction.replace(R.id.mainActivityFrameLayout, indEngFragment);
            transaction.commit();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        drawerLayout.closeDrawer(GravityCompat.START);
        switch (menuItem.getItemId()) {
            case R.id.navigationMenuEngInd:
                loadFragment(true);
                break;
            case R.id.navigationMenuIndEng:
                loadFragment(false);
                break;
        }
        return true;
    }
}
