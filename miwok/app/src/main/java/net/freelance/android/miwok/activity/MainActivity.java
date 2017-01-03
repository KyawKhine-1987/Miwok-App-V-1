package net.freelance.android.miwok.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import net.freelance.android.miwok.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Find the View that shows the numbers category.
        TextView numbers = (TextView) findViewById(R.id.numbers);

        //Set a clickListener on that View.
        numbers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Create a new intent to open the {@link NumbersActivity}
                Intent numbersIntent = new Intent(MainActivity.this, NumbersActivity.class);
                //Start the new activity
                startActivity(numbersIntent);
            }
        });


        //Find the View that shows the family category.
        TextView family = (TextView) findViewById(R.id.family);

        //Set a clickListener on that View.
        family.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //Create a new intent to open the {@link FamilyActivity}
                Intent numbersIntent = new Intent(MainActivity.this, FamilyActivity.class);
                //Start the new activity
                startActivity(numbersIntent);
            }
        });


        //Find the View that shows the colors category.
        TextView colors = (TextView) findViewById(R.id.colors);

        //Set a clickListener on that View.
        colors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Create a new intent to open the {@link ColorsActivity}
                Intent colorsIntent = new Intent(MainActivity.this, ColorsActivity.class);
                //Start the new activity
                startActivity(colorsIntent);
            }
        });

        //Find the View that shows the phrases category.
        TextView phrases = (TextView) findViewById(R.id.phrases);

        //Set a clickListener on that View.
        phrases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Create a new intent to open the {@link PhrasessActivity}
                Intent colorsIntent = new Intent(MainActivity.this, PhrasesActivity.class);
                //Start the new activity
                startActivity(colorsIntent);
            }
        });

    }
}

/*//Old Method

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NumbersClickListener clickListener = new NumbersClickListener();

        //Find the View that shows the numbers category.
        TextView numbers = (TextView)findViewById(R.id.numbers);

        //Set a clickListener on that View.
        numbers.setOnClickListener(clickListener);
        numbers.setOnClickListener(new NumbersClickListener());
    }

    public void openNumbersList(View view) {
        Intent i = new Intent(this, NumbersActivity.class);
        startActivity(i);
}*/