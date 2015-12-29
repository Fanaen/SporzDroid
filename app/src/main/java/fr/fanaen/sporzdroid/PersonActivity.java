package fr.fanaen.sporzdroid;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import fr.fanaen.sporzdroid.model.Person;

public class PersonActivity extends AppCompatActivity {

    public static final String PERSON_ID = "id";

    @Bind(R.id.toolbar)             Toolbar toolbar;
    @Bind(R.id.input_person_name)   EditText editName;

    private boolean createMode;
    private long id;
    private Person person;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        ButterKnife.bind(this);

        // Display the bar with back button --
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Receive the id (-1 for creation) --
        Intent intent = getIntent();
        id = intent.getLongExtra(PERSON_ID, -1);
        createMode = id == -1;

        // Prepare fields --
        if(editName.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }

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
            onSaveButton();
        }
        else if(id == R.id.action_remove_person && !createMode) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.dialog_delete_person)
                    .setPositiveButton(R.string.button_remove, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            onDeleteButton();
                        }
                    })
                    .setNegativeButton(R.string.button_cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });
            // Create the AlertDialog object and return it
            AlertDialog dialog = builder.create();
            dialog.show();
        }

        return super.onOptionsItemSelected(item);
    }

    // Custom events --

    private void onSaveButton() {
        person.setName(editName.getText().toString());
        person.save();

        if(createMode) {
            this.id = person.getId();
            createMode = false;
        }
    }

    private void onDeleteButton() {
        person.delete();
        finish();
    }
}
