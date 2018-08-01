package com.stevenberdak.displaything;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class DisplayThingActivity extends AppCompatActivity {

    public static String DISPLAY_THING_STRING_EXTRA = "display_thing_string_extra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_thing);

        String message = getIntent().getStringExtra(DISPLAY_THING_STRING_EXTRA);

        if (message != null && message.length() > 0) {
            setResult(Activity.RESULT_OK);
            TextView textViewDisplayThing = findViewById(R.id.text_view_display_thing);
            textViewDisplayThing.setText(message);
        } else {
            setResult(Activity.RESULT_CANCELED);
            finish();
        }
    }
}
