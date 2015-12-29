package fr.fanaen.sporzdroid;

import android.os.Bundle;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import fr.fanaen.sporzdroid.fragment.GameFragment;
import fr.fanaen.sporzdroid.fragment.PersonFragment;
import fr.fanaen.sporzdroid.fragment.dummy.DummyContent;
import fr.fanaen.sporzdroid.model.Game;

public class MainActivity extends AppCompatActivity implements
        GameFragment.OnListFragmentInteractionListener,
        PersonFragment.OnListFragmentInteractionListener {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new GameFragment(), this.getResources().getString(R.string.title_tab_game));
        adapter.addFragment(new PersonFragment(), this.getResources().getString(R.string.title_tab_person));
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onGameListFragmentInteraction(DummyContent.DummyItem item) {
        System.out.println("Game " + item.id);

        Game newGame = new Game();
        newGame.save();
        System.out.println("Game saved");

        List<Game> games = Game.listAll(Game.class);
        String result = "ListAll Result: ";

        for(Game game : games) {
            result += game.getId() + " ";
        }
        System.out.println(result);
    }

    @Override
    public void onPersonListFragmentInteraction(DummyContent.DummyItem item) {
        System.out.println("Person " + item.id);

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
