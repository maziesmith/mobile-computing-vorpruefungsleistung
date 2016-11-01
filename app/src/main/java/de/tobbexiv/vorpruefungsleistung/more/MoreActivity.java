package de.tobbexiv.vorpruefungsleistung.more;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import de.tobbexiv.vorpruefungsleistung.R;

/**
 * Second layer. Provides option to show a modal "about" dialog and to end the app completely.
 */
public class MoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        new MoreActivityButtonClickListener(this);
    }
}
