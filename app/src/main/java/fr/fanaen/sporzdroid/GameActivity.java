package fr.fanaen.sporzdroid;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import fr.fanaen.sporzdroid.fragment.GameFragment;
import fr.fanaen.sporzdroid.fragment.GameStateFragment;
import fr.fanaen.sporzdroid.fragment.ParticipationFragment;
import fr.fanaen.sporzdroid.fragment.PersonFragment;
import fr.fanaen.sporzdroid.model.Game;
import fr.fanaen.sporzdroid.model.Participation;

public class GameActivity extends AppCompatActivity implements
        GameStateFragment.OnFragmentInteractionListener,
        ParticipationFragment.OnListFragmentInteractionListener {

    public static final String GAME_ID = "id";

    // Tab fields --
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private GameStateFragment gameStateFragment;
    private ParticipationFragment participationFragment;

    // Editing fields --
    private boolean createMode;
    private long id;
    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Display the bar with back button --
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Receive the id (-1 for creation) --
        Intent intent = getIntent();
        id = intent.getLongExtra(GAME_ID, -1);
        createMode = id == -1;

        // Prepare the person object --
        if(createMode) {
            game = new Game();
        }
        else {
            game = Game.findById(Game.class, id);
        }

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        gameStateFragment = new GameStateFragment();
        participationFragment = new ParticipationFragment();

        adapter.addFragment(gameStateFragment, this.getResources().getString(R.string.title_tab_game_state));
        adapter.addFragment(participationFragment, this.getResources().getString(R.string.title_tab_participation));

        viewPager.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(participationFragment != null  && gameStateFragment != null) {
            participationFragment.update();
            gameStateFragment.update();
        }
    }

    @Override
    public void onParticipationListFragmentInteraction(Participation item) {
        // Redirect to editing activity --
        //Intent intent = new Intent(this, PersonActivity.class);
        //intent.putExtra(PersonActivity.PERSON_ID, item.getId());
        //startActivity(intent);
    }

    @Override
    public void onGameStateFragmentInteraction(Uri uri) {

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
