package de.tobbexiv.vorpruefungsleistung.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.tobbexiv.vorpruefungsleistung.R;

/**
 * Listen to changes on the display options, adjusts the output style accordingly and
 * handles saving / loading of the selected options.
 */
public class LayoutOptionsListener implements AdapterView.OnItemSelectedListener {
    /**
     * The activity which created the listener.
     */
    private AppCompatActivity creator;

    /**
     * A shared preferences object to store / load the options.
     */
    private SharedPreferences settings;

    /**
     * The font size which is selected by the user.
     */
    private int    selectedFontsize;

    /**
     * The font type which is selected by the user.
     */
    private String selectedFonttype;

    /**
     * The font style which is selcted by the user.
     */
    private int    selectedFontstyle;

    /**
     * The color for the font which is selected by the user.
     */
    private int    selectedFontcolor;

    /**
     * The color for the output area background which is selcted by the user.
     */
    private int    selectedBackgroundcolor;

    /**
     * The text view which displays the bit-code output.
     */
    private TextView displayBitcode;

    /**
     * The text view which dispays the unicode character.
     */
    private TextView displayUnicode;

    /**
     * Create a new layout option listener instance, fill the spinners,
     * attach the change listener and default the select options / load
     * the last stored user settings.
     *
     * @param creator
     *   The activity which creates this instance.
     */
    public LayoutOptionsListener(AppCompatActivity creator) {
        this.creator = creator;

        this.settings = this.creator.getSharedPreferences("LayoutOptions", Context.MODE_PRIVATE);

        this.selectedFontsize        = this.settings.getInt("fontsize", 14);
        this.selectedFonttype        = this.settings.getString("fonttype", FontLoader.DEFAULT.getName());
        this.selectedFontstyle       = this.settings.getInt("fontstyle", Typeface.NORMAL);
        this.selectedFontcolor       = this.settings.getInt("fontcolor", this.creator.getResources().getColor(R.color.black));
        this.selectedBackgroundcolor = this.settings.getInt("backgroundcolor", this.creator.getResources().getColor(R.color.transparent));

        getdisplayTextViews();
        attachListenersAndFillSpinners();

        updateCodeDisplayOptions();
    }

    /**
     * Get the instances for the text views for output.
     */
    private void getdisplayTextViews() {
        this.displayBitcode = (TextView) this.creator.findViewById(R.id.text_displayBitcode);
        this.displayUnicode = (TextView) this.creator.findViewById(R.id.text_displayUnicode);
    }

    /**
     * Attach all listener to the spinners and prefill them and select the last selected item.
     */
    private void attachListenersAndFillSpinners() {
        Spinner spinner;

        spinner = (Spinner) this.creator.findViewById(R.id.spinner_fontsize);
        spinner.setOnItemSelectedListener(this);
        fillSizeSpinner(spinner, this.selectedFontsize);

        spinner = (Spinner) this.creator.findViewById(R.id.spinner_fonttype);
        spinner.setOnItemSelectedListener(this);
        fillTypeSpinner(spinner, this.selectedFonttype);

        spinner = (Spinner) this.creator.findViewById(R.id.spinner_fontstyle);
        spinner.setOnItemSelectedListener(this);
        fillStyleSpinner(spinner, this.selectedFontstyle);

        spinner = (Spinner) this.creator.findViewById(R.id.spinner_fontcolor);
        spinner.setOnItemSelectedListener(this);
        fillColorSpinner(spinner, this.selectedFontcolor);

        spinner = (Spinner) this.creator.findViewById(R.id.spinner_backgroundcolor);
        spinner.setOnItemSelectedListener(this);
        fillColorSpinner(spinner, this.selectedBackgroundcolor);
    }

    /**
     * Fill a spinner to select the output font size.
     *
     * @param toFill
     *   The spinner to fill.
     * @param selected
     *   The font size which is selected at the moment.
     */
    private void fillSizeSpinner(Spinner toFill, int selected) {
        List<String> values = new ArrayList<String>();
        for(int i = 10; i <= 20; i = i + 2) {
            values.add(i + "");
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this.creator, android.R.layout.simple_spinner_item, values);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        toFill.setAdapter(dataAdapter);

        int spinnerPosition = dataAdapter.getPosition(selected + "");
        toFill.setSelection(spinnerPosition);
    }

    /**
     * Fill a spinner to select the font type.
     *
     * @param toFill
     *   The spinner to fill.
     * @param selected
     *   The font type name id of the font type which is selected at the moment.
     */
    private void fillTypeSpinner(Spinner toFill, String selected) {
        String[] values = FontLoader.getFontLongNames();

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this.creator, android.R.layout.simple_spinner_item, values);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        toFill.setAdapter(dataAdapter);

        int spinnerPosition = dataAdapter.getPosition(FontLoader.getLongNameByName(selected));
        toFill.setSelection(spinnerPosition);
    }

    /**
     * Fill the spinner to select the font style.
     *
     * @param toFill
     *   The spinner to fill.
     * @param selected
     *   The font style which is selected at the moment.
     */
    private void fillStyleSpinner(Spinner toFill, int selected) {
        List<String> values = new ArrayList<String>();
        values.add(this.creator.getString(R.string.normal));
        values.add(this.creator.getString(R.string.bold));
        values.add(this.creator.getString(R.string.italic));
        values.add(this.creator.getString(R.string.boldItalic));

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this.creator, android.R.layout.simple_spinner_item, values);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        toFill.setAdapter(dataAdapter);

        int spinnerPosition = dataAdapter.getPosition(resolveFontstyleToName(selected));
        toFill.setSelection(spinnerPosition);
    }

    /**
     * Fill a spinner to select a color.
     *
     * @param toFill
     *   The spinner to fill.
     * @param selected
     *   The color which is selected at the moment.
     */
    private void fillColorSpinner(Spinner toFill, int selected) {
        String[] values = this.creator.getResources().getStringArray(R.array.colorNames);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this.creator, android.R.layout.simple_spinner_item, values);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        toFill.setAdapter(dataAdapter);

        int spinnerPosition = dataAdapter.getPosition(resolveColorToName(selected));
        toFill.setSelection(spinnerPosition);
    }

    /**
     * Resolve a given font style to the corresponding name.
     *
     * @param fontstyle
     *   The font style to resolve.
     * @return
     *   The font style name.
     */
    @NonNull
    private String resolveFontstyleToName(int fontstyle) {
        if(fontstyle == Typeface.BOLD) {
            return this.creator.getString(R.string.bold);
        }

        if(fontstyle == Typeface.ITALIC) {
            return this.creator.getString(R.string.italic);
        }

        if(fontstyle == Typeface.BOLD_ITALIC) {
            return this.creator.getString(R.string.boldItalic);
        }

        return this.creator.getString(R.string.normal);
    }

    /**
     * Resolve a given color to the corresponding name.
     *
     * @param color
     *   The color to resolve.
     * @return
     *   The color name.
     */
    private String resolveColorToName(int color) {
        int[]    colors  = this.creator.getResources().getIntArray(R.array.colors);
        String[] names   = this.creator.getResources().getStringArray(R.array.colorNames);

        for(int i = 0; i < colors.length; i++) {
            if(colors[i] == color) {
                return names[i];
            }
        }

        return "";
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner changed   = (Spinner) parent;
        String  selection = changed.getSelectedItem().toString();

        switch(changed.getId()) {
            case R.id.spinner_fontsize:
                this.selectedFontsize = Integer.parseInt(selection);
                break;

            case R.id.spinner_fonttype:
                this.selectedFonttype = FontLoader.getNameByLongName(selection);
                break;

            case R.id.spinner_fontstyle:
                this.selectedFontstyle = resolveNameToFontstyle(selection);
                break;

            case R.id.spinner_fontcolor:
                this.selectedFontcolor = resolvePositionToColor(position);
                break;

            case R.id.spinner_backgroundcolor:
                this.selectedBackgroundcolor = resolvePositionToColor(position);
                break;

            default:
                break;
        }

        updateCodeDisplayOptions();
        storeSettings();
    }

    /**
     * Resolve a fontstyle by its name.
     *
     * @param name
     *   The name to resolve.
     * @return
     *   The corresponding font style.
     */
    private int resolveNameToFontstyle(String name) {
        if (name.equals(this.creator.getString(R.string.bold))) {
            return Typeface.BOLD;
        }

        if (name.equals(this.creator.getString(R.string.italic))) {
            return Typeface.ITALIC;
        }

        if (name.equals(this.creator.getString(R.string.boldItalic))) {
            return Typeface.BOLD_ITALIC;
        }

        return Typeface.NORMAL;
    }

    /**
     * Resolve a color by its position.
     *
     * @param position
     *   The position to resolve.
     * @return
     *   The corresponding color.
     */
    private int resolvePositionToColor(int position) {
        TypedArray colors = this.creator.getResources().obtainTypedArray(R.array.colors);
        return colors.getColor(position, 0);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        updateCodeDisplayOptions();
    }

    /**
     * Update the options of the code display text views.
     */
    private void updateCodeDisplayOptions() {
        updateCodeDisplayOptionsSingleTextView(this.displayBitcode);
        updateCodeDisplayOptionsSingleTextView(this.displayUnicode);
    }

    /**
     * Update the options of a single code display text view.
     *
     * @param toChange
     *   The text view which should be updated.
     */
    private void updateCodeDisplayOptionsSingleTextView(TextView toChange) {
        // Fontsize
        toChange.setTextSize(this.selectedFontsize);

        // Fonttype
        toChange.setTypeface(FontLoader.getTypeFaceByName(this.creator, this.selectedFonttype));

        // Fontstyle
        toChange.setTypeface(Typeface.create(toChange.getTypeface(), Typeface.NORMAL), Typeface.NORMAL); // Delete all possibly applied styles.
        toChange.setTypeface(Typeface.create(toChange.getTypeface(), this.selectedFontstyle), this.selectedFontstyle);

        // Fontcolor
        toChange.setTextColor(this.selectedFontcolor);

        // Backgroundcolor
        toChange.setBackgroundColor(this.selectedBackgroundcolor);
    }

    /**
     * Store the settings which are selected by the user.
     */
    private void storeSettings() {
        SharedPreferences.Editor editor = this.settings.edit();

        editor.putInt("fontsize", this.selectedFontsize);
        editor.putString("fonttype", this.selectedFonttype);
        editor.putInt("fontstyle", this.selectedFontstyle);
        editor.putInt("fontcolor", this.selectedFontcolor);
        editor.putInt("backgroundcolor", this.selectedBackgroundcolor);

        editor.commit();
    }
}