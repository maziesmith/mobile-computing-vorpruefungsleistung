package de.tobbexiv.vorpruefungsleistung.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import de.tobbexiv.vorpruefungsleistung.R;
import de.tobbexiv.vorpruefungsleistung.more.MoreActivity;

/**
 * Listens to clicks on buttons on the display, handles the clicks accordingly
 * and handles saving / loading of the entered options.
 */
class MainActivityButtonClickListener implements View.OnClickListener {
    /**
     * The hex code entered by the user.
     */
    private String hexcode;

    /**
     * The actvity which created the listener.
     */
    private AppCompatActivity creator;

    /**
     * A shared preferences object to store / load the entered text.
     */
    private SharedPreferences settings;

    /**
     * The text view to display the user input as hex code.
     */
    private TextView displayHexcode;

    /**
     * The text view to display the user input as bit code.
     */
    private TextView displayBitcode;

    /**
     * The text view to display the user code as unicode character.
     */
    private TextView displayUnicode;

    /**
     * Constant to increment a value.
     */
    private static int INCREMENT = 1;

    /**
     * Constant to decrement a value.
     */
    private static int DECREMENT = -1;

    /**
     * Constant to indicate that the unicode page value should be changed.
     */
    private static int PAGEVALUE = 0;

    /**
     * Constant to indicate that the unicode code value should be changed.
     */
    private static int CODEVALUE = 1;

    /**
     * Create a new button click listener, attach the click listener,
     * default the string / load the last entered string.
     *
     * @param creator
     *   The activity which creates this instance.
     */
    public MainActivityButtonClickListener(AppCompatActivity creator) {
        this.creator = creator;

        this.settings = this.creator.getSharedPreferences("EnteredCode", Context.MODE_PRIVATE);

        this.hexcode = this.settings.getString("hexcode", "");

        getdisplayTextViews();
        attachListener();

        updateCodeDisplays();
    }

    /**
     * Get the instances for the text views for output.
     */
    private void getdisplayTextViews() {
        this.displayHexcode = (TextView) this.creator.findViewById(R.id.text_displayHexcode);
        this.displayBitcode = (TextView) this.creator.findViewById(R.id.text_displayBitcode);
        this.displayUnicode = (TextView) this.creator.findViewById(R.id.text_displayUnicode);
    }

    /**
     * Attach all listener to the buttons.
     */
    private void attachListener() {
        this.creator.findViewById(R.id.btn_more).setOnClickListener(this);

        this.creator.findViewById(R.id.btn_0).setOnClickListener(this);
        this.creator.findViewById(R.id.btn_1).setOnClickListener(this);
        this.creator.findViewById(R.id.btn_2).setOnClickListener(this);
        this.creator.findViewById(R.id.btn_3).setOnClickListener(this);
        this.creator.findViewById(R.id.btn_4).setOnClickListener(this);
        this.creator.findViewById(R.id.btn_5).setOnClickListener(this);
        this.creator.findViewById(R.id.btn_6).setOnClickListener(this);
        this.creator.findViewById(R.id.btn_7).setOnClickListener(this);
        this.creator.findViewById(R.id.btn_8).setOnClickListener(this);
        this.creator.findViewById(R.id.btn_9).setOnClickListener(this);
        this.creator.findViewById(R.id.btn_a).setOnClickListener(this);
        this.creator.findViewById(R.id.btn_b).setOnClickListener(this);
        this.creator.findViewById(R.id.btn_c).setOnClickListener(this);
        this.creator.findViewById(R.id.btn_d).setOnClickListener(this);
        this.creator.findViewById(R.id.btn_e).setOnClickListener(this);
        this.creator.findViewById(R.id.btn_f).setOnClickListener(this);

        this.creator.findViewById(R.id.btn_deleteLast).setOnClickListener(this);
        this.creator.findViewById(R.id.btn_delete).setOnClickListener(this);

        this.creator.findViewById(R.id.btn_pageDecrement).setOnClickListener(this);
        this.creator.findViewById(R.id.btn_pageIncrement).setOnClickListener(this);
        this.creator.findViewById(R.id.btn_codeDecrement).setOnClickListener(this);
        this.creator.findViewById(R.id.btn_codeIncrement).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Button clicked = (Button) v;

        switch(clicked.getId()) {
            case R.id.btn_more:
                startMoreActivity();
                break;

            case R.id.btn_0:
            case R.id.btn_1:
            case R.id.btn_2:
            case R.id.btn_3:
            case R.id.btn_4:
            case R.id.btn_5:
            case R.id.btn_6:
            case R.id.btn_7:
            case R.id.btn_8:
            case R.id.btn_9:
            case R.id.btn_a:
            case R.id.btn_b:
            case R.id.btn_c:
            case R.id.btn_d:
            case R.id.btn_e:
            case R.id.btn_f:
                addHexCharacter(clicked.getText().charAt(0));
                break;

            case R.id.btn_deleteLast:
                removeHexCharacter();
                break;

            case R.id.btn_delete:
                this.hexcode = "";
                break;

            case R.id.btn_pageDecrement:
                changeHexValue(PAGEVALUE, DECREMENT);
                break;

            case R.id.btn_pageIncrement:
                changeHexValue(PAGEVALUE, INCREMENT);
                break;

            case R.id.btn_codeDecrement:
                changeHexValue(CODEVALUE, DECREMENT);
                break;

            case R.id.btn_codeIncrement:
                changeHexValue(CODEVALUE, INCREMENT);
                break;

            default:
                break;
        }

        updateCodeDisplays();
        storeSettings();
    }

    /**
     * Starts the second layer activity.
     */
    private void startMoreActivity() {
        Intent intent = new Intent(this.creator, MoreActivity.class);
        this.creator.startActivityForResult(intent, MainActivity.MORE_ACTIVITY);
    }

    /**
     * Add a character to the hex representtion of the user input.
     *
     * @param toAdd
     *   The hex character to add.
     */
    private void addHexCharacter(char toAdd) {
        if(this.hexcode.charAt(0) != '0') {
            Toast.makeText(this.creator.getApplicationContext(), R.string.maximumNumberCharacters, Toast.LENGTH_SHORT).show();
        } else {
            this.hexcode += toAdd;
        }
    }

    /**
     * Remove the last character of the hex representation of the user input.
     */
    private void removeHexCharacter() {
        this.hexcode = this.hexcode.substring(0, this.hexcode.length() - 1);
    }

    /**
     * Update the code display text views.
     */
    private void updateCodeDisplays() {
        this.hexcode = fixedLength(this.hexcode, 4);
        this.hexcode = this.hexcode.toUpperCase();

        int     intVal  = this.hexcode.length() > 0 ? Integer.parseInt(this.hexcode, 16): 0;
        String  bitcode = "";
        char    unicode = (char) intVal;

        for(int i = 0; i < this.hexcode.length(); i++) {
            String toConvert = this.hexcode.substring(i, i + 1);
            String binary    = Integer.toBinaryString(Integer.parseInt(toConvert, 16));

            binary = fixedLength(binary, 4);

            bitcode += binary + " ";
        }

        this.displayHexcode.setText(this.hexcode);
        this.displayBitcode.setText(bitcode);
        this.displayUnicode.setText(Character.toString(unicode));
    }

    /**
     * Store the user entered hex code.
     */
    private void storeSettings() {
        SharedPreferences.Editor editor = this.settings.edit();

        editor.putString("hexcode", this.hexcode);

        editor.commit();
    }

    /**
     * Increment / decrement the hex representation of the page value or code value.
     * An overflow sets 00 to FF or FF to 00.
     *
     * @param part
     *   Whether the page or the code value should be changed. Use the constants for indicating.
     * @param operation
     *   If the string should be incremented / decremented. Use the constants for indicating.
     */
    private void changeHexValue(int part, int operation) {
        int startIndex = part == PAGEVALUE ? 0 : 2;
        int endIndex = startIndex + 2;

        String change = this.hexcode.substring(startIndex, endIndex);
        change = Integer.toHexString(Integer.parseInt(change, 16) + operation);
        change = fixedLength(change, 2);

        this.hexcode = this.hexcode.substring(0, startIndex) + change + this.hexcode.substring(endIndex);
    }

    /**
     * Padds a string from left with "0" to a fixed length
     * and cuts the leading characters if the string is
     * longer than the fixed length.
     *
     * @param toProcess
     *   The string which should be set to a fixed length.
     * @param resultLength
     *   The length the result string should have.
     * @return
     *   The fixed length string.
     */
    private String fixedLength(String toProcess, int resultLength) {
        String padding = "";

        int endIndex   = toProcess.length();
        int startIndex = endIndex - resultLength > 0 ? endIndex - resultLength : 0;

        toProcess = toProcess.substring(startIndex, endIndex);

        for(int i = 0; i < resultLength - toProcess.length(); i++) {
            padding += "0";
        }

        return padding + toProcess;
    }
}
