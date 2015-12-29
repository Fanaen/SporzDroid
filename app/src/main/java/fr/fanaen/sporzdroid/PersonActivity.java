package fr.fanaen.sporzdroid;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import fr.fanaen.sporzdroid.model.Person;

public class PersonActivity extends AppCompatActivity {

    public static final String PERSON_ID = "id";

    private Toolbar toolbar;
    private boolean createMode;
    private long id;
    private Person person;

    private EditText editName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        // Display the bar with back button --
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Receive the id (-1 for creation) --
        Intent intent = getIntent();
        id = intent.getLongExtra(PERSON_ID, -1);
        createMode = id == -1;

        // Prepare fields --
        editName = (EditText) findViewById(R.id.input_person_name);

        // Prepare the person object --
        if(createMode) {
            person = new Person();
        }
        else {
            person = Person.findById(Person.class, id);
            editName.setText(person.getName());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_person, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id == R.id.action_save_person) {
            person.setName(editName.getText().toString());
            person.save();

            if(createMode) {
                this.id = person.getId();
                createMode = false;
            }
        }
        else if(id == R.id.action_remove_person && !createMode) {
            person.delete();
        }

        return super.onOptionsItemSelected(item);
    }
}
