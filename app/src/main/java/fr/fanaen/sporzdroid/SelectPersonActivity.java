package fr.fanaen.sporzdroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.Bind;
import butterknife.ButterKnife;
import fr.fanaen.sporzdroid.fragment.PersonFragment;
import fr.fanaen.sporzdroid.model.Person;

public class SelectPersonActivity extends AppCompatActivity implements PersonFragment.OnListFragmentInteractionListener {

    public static final String GAME_ID = "id";

    @Bind(R.id.toolbar)             Toolbar toolbar;

    private long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_person);
        ButterKnife.bind(this);

        // Display the bar with back button --
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Receive the id (-1 for creation) --
        Intent intent = getIntent();
        id = intent.getLongExtra(GAME_ID, -1);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_root, new PersonFragment()).commit();
    }

    @Override
    public void onPersonListFragmentInteraction(Person item) {
        System.err.println("Person " + item.getId());
    }
}
