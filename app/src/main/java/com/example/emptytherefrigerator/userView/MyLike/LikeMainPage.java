package com.example.emptytherefrigerator.userView.MyLike;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.emptytherefrigerator.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class LikeMainPage extends AppCompatActivity
{
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private FragLikeInView likeIn = new FragLikeInView();
    private FragLikeOutView likeOut = new FragLikeOutView();
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_my_like_list_main);

        setFragMainPage();          //메인 페이지(프라그먼트) 생성
    }

    public void setFragMainPage()
    {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.likeFrame, likeIn).commitAllowingStateLoss();

        BottomNavigationView bottomNavigationView = findViewById(R.id.likeBottomNav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new ItemSelectedListener());

        toolbar = findViewById(R.id.myLikeToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);    //기본 제목을 없애줍니다
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case android.R.id.home:     //뒤로가기 버튼이 눌렸을 때
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    class ItemSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener {       //bottom nav 선택에 따른 화면 출력 설정
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            switch (menuItem.getItemId()) {
                case R.id.in_recipe_like:
                    transaction.replace(R.id.likeFrame, likeIn).commitAllowingStateLoss();
                    break;
                case R.id.out_site_like:
                    transaction.replace(R.id.likeFrame, likeOut).commitAllowingStateLoss();
                    break;
            }
            return true;
        }
    }
}
