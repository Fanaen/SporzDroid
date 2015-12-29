package fr.fanaen.sporzdroid;

import android.content.Intent;
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

import fr.fanaen.sporzdroid.fragment.GameFragment;
import fr.fanaen.sporzdroid.fragment.PersonFragment;
import fr.fanaen.sporzdroid.model.Game;
import fr.fanaen.sporzdroid.model.Person;

public class MainActivity extends AppCompatActivity implements
        GameFragment.OnListFragmentInteractionListener,
        PersonFragment.OnListFragmentInteractionListener {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private GameFragment gameFragment;
    private PersonFragment personFragment;

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

        gameFragment = new GameFragment();
        personFragment = new PersonFragment();

        adapter.addFragment(gameFragment, this.getResources().getString(R.string.title_tab_game));
        adapter.addFragment(personFragment, this.getResources().getString(R.string.title_tab_person));

        viewPager.setAdapter(adapter);
    }

    @Override
    public void onGameListFragmentInteraction(Game game) {
        System.out.println("Game " + game.getId());

        Person newPerson = new Person();
        newPerson.save();
        System.out.println("Person saved");

        List<Person> persons = Person.listAll(Person.class);
        String result = "ListAll Result: ";

        for(Person item : persons) {
            result += item.getId() + " ";
        }
        System.out.println(result);
        personFragment.adapter.populate();
        personFragment.adapter.notifyDataSetChanged();

    }

    @Override
    public void onPersonListFragmentInteraction(Person item) {
        // Redirect to editing activity --
        Intent intent = new Intent(this, PersonActivity.class);
        intent.putExtra(PersonActivity.PERSON_ID, item.getId());
        startActivity(intent);

        /*Game newGame = new Game();
        newGame.save();
        System.out.println("Game saved");

        List<Game> games = Game.listAll(Game.class);
        String result = "ListAll Result: ";

        for(Game game : games) {
            result += game.getId() + " ";
        }
        System.out.println(result);
        gameFragment.adapter.populate();
        gameFragment.adapter.notifyDataSetChanged();*/
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



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id == R.id.action_add_person) {
            Intent intent = new Intent(this, PersonActivity.class);
            intent.putExtra(PersonActivity.PERSON_ID, -1);
            startActivity(intent);
        }

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

}
