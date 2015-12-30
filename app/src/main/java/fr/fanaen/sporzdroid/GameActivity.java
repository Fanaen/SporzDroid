package fr.fanaen.sporzdroid;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;
import fr.fanaen.sporzdroid.fragment.GameStateFragment;
import fr.fanaen.sporzdroid.fragment.ParticipationFragment;
import fr.fanaen.sporzdroid.model.Game;
import fr.fanaen.sporzdroid.model.Participation;

public class GameActivity extends AppCompatActivity implements
        GameStateFragment.OnFragmentInteractionListener,
        ParticipationFragment.OnListFragmentInteractionListener {

    public static final String GAME_ID = "id";

    // Tab fields --
    @Bind(R.id.toolbar)     Toolbar toolbar;
    @Bind(R.id.tabs)        TabLayout tabLayout;
    @Bind(R.id.viewpager)   ViewPager viewPager;

    @BindString(R.string.title_tab_game_state)      String gameStateTitle;
    @BindString(R.string.title_tab_participation)   String participationTitle;

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
        ButterKnife.bind(this);

        // Display the bar with back button --
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

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        gameStateFragment = new GameStateFragment();
        participationFragment = new ParticipationFragment();

        adapter.addFragment(gameStateFragment, gameStateTitle);
        adapter.addFragment(participationFragment, participationTitle);

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

    // -- Menu methods --

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id == R.id.action_add_participation) {

        }

        return super.onOptionsItemSelected(item);
    }

}
