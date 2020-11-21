package com.example.emptytherefrigerator.main;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.emptytherefrigerator.R;
import com.example.emptytherefrigerator.main.Notification.NotificationView;
import com.example.emptytherefrigerator.userView.UserView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

//MainSearchFrag 와 MainUserFrag 가 합쳐짐
public class MainPageView extends AppCompatActivity
{
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private MainSearchFragView mainSearchFrag = new MainSearchFragView();
    private UserView memberView = new UserView();
    private NotificationView notificationView = new NotificationView();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_home);

        setFragMainPage();          //메인 페이지(프라그먼트) 생성
    }

    public void setFragMainPage()
    {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameLayout, mainSearchFrag).commitAllowingStateLoss();

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new ItemSelectedListener());
    }

    class ItemSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            switch (menuItem.getItemId()) {
                case R.id.menu_home:
                    transaction.replace(R.id.frameLayout, mainSearchFrag).commitAllowingStateLoss();
                    break;
                case R.id.menu_user:
                    transaction.replace(R.id.frameLayout, memberView).commitAllowingStateLoss();
                    break;
                case R.id.menu_alarm:
                    transaction.replace(R.id.frameLayout, notificationView).commitAllowingStateLoss();
                    break;
            }
            return true;
        }
    }
}
