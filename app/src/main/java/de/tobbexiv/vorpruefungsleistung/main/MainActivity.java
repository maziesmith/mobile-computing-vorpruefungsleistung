package de.tobbexiv.vorpruefungsleistung.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import de.tobbexiv.vorpruefungsleistung.R;

/**
 * The main activity of the app. Displays a graphical number interface
 * to enter the hex code of a unicode character. This character is then
 * displayed by its byte representation and as the unicode character.
 * The display views can be changed by font size / type / color / style
 * and background color which is also handled by this activity.
 */
public class MainActivity extends AppCompatActivity {
    /**
     * Identify that the onActivityResult was called from the MoreActivity.
     */
    public static int MORE_ACTIVITY = 4711;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        new MainActivityButtonClickListener(this);
        new LayoutOptionsListener(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == MORE_ACTIVITY) {
            if(resultCode == RESULT_OK) {
                if(data.getBooleanExtra("EXIT", false)) {
                    killApp();
                }
            }
        }
    }

    /**
     * Remove the app from the recent apps list and kill it afterwards.
     */
    private void killApp() {
        this.finishAndRemoveTask();

        // Ugly, but really ending the app is one point required in the tasks.
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
}
