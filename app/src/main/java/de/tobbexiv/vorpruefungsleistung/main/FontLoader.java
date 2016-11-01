package de.tobbexiv.vorpruefungsleistung.main;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Enum to load font files easily.
 *
 * Fonts should be stored in the assets subfolder fonts. File name: [name].ttf
 */
enum FontLoader {

    DEFAULT("default", "Default"),
    ARIAL("arial", "Arial"),
    TIMES("times", "Times New Roman"),
    VERDANA("verdana", "Verdana"),
    TREBUCHET("trebuchet", "Trebuchet"),
    CALIBRI("calibri", "Calibri"),
    PAPYRUS("papyrus", "Papyrus"),
    COURIER("courier", "Courier New"),
    TAHOMA("tahoma", "Tahoma"),
    LUCIDA("lucida", "Lucida Console");

    /**
     * The name id of the font. Equals the filename without the file type and must be the
     * same as the enum item name in lower case.
     */
    private String   name;

    /**
     * The readable name of the font.
     */
    private String   longName;

    /**
     * The typeface representing the font.
     */
    private Typeface typeFace;

    /**
     * All font name ids to avoid iterating all enum items if a selection by name is done.
     */
    private static String[] fontNames     = null;

    /**
     * All long names of the fonts to avoid iterating all enum items if a selection by longname is done.
     */
    private static String[] fontLongNames = null;

    /**
     * Creates a new font representation.
     *
     * @param name
     *   The name id of the font. Should equal the filename without the file type
     *   and should be the same as the enum item name but in lower case.
     * @param longName
     *   The readable name of the font. Can be translated.
     */
    FontLoader(String name, String longName) {
        this.name     = name;
        this.longName = longName;

        this.typeFace = null;
    }

    /**
     * Get the name id of the font.
     *
     * @return
     *   The name id of the font.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get the long name of the font.
     *
     * @return
     *   The long name of that font.
     */
    public String getLongName() {
        return this.longName;
    }

    /**
     * Returns the corresponding long name to a given name id.
     *
     * @param name
     *   The name id to convert.
     * @return
     *   The long name.
     */
    public static String getLongNameByName(String name) {
        try {
            return FontLoader.valueOf(name.toUpperCase()).getLongName();
        } catch (Exception e) {
            return FontLoader.DEFAULT.getLongName();
        }
    }

    /**
     * Returns the corresponding name id to a given long name.
     *
     * @param longName
     *   The long name to convert.
     * @return
     *   The name id.
     */
    public static String getNameByLongName(String longName) {
        for(FontLoader item : FontLoader.values()) {
            if(longName.equalsIgnoreCase(item.longName)) {
                return item.getName();
            }
        }

        return FontLoader.DEFAULT.getName();
    }

    /**
     * Get a typeface by the font name id. Null if the default font is requested.
     *
     * @param context
     *   The context in which the font should be loaded. Where to find the assets folder.
     * @param name
     *   The name id of the font. Must be the same as the corresponding enum name.
     * @return
     *   The typeface corresponding to this font.
     */
    @Nullable
    public static Typeface getTypeFaceByName(Context context, String name){
        if(name == FontLoader.DEFAULT.name) {
            return null;
        }

        try {
            FontLoader item = FontLoader.valueOf(name.toUpperCase());

            if(item.typeFace == null){
                item.typeFace = Typeface.createFromAsset(context.getAssets(), "fonts/" + item.name + ".ttf");
            }

            return item.typeFace;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Get a typeface by the font name id. Null if the default font is requested.
     *
     * @param context
     *   The context in which the font should be loaded. Where to find the assets folder.
     * @param longName
     *   The name long name of the font.
     * @return
     *   The typeface corresponding to this font.
     */
    @Nullable
    public static Typeface getTypeFaceByLongName(Context context, String longName){
        for(FontLoader item : FontLoader.values()) {
            if(longName.equalsIgnoreCase(item.longName)) {
                return FontLoader.getTypeFaceByName(context, item.name);
            }
        }

        return null;
    }

    /**
     * Get all possible font name ids as array.
     *
     * @return
     *   All possible font name ids.
     */
    public static String[] getFontNames() {
        if(FontLoader.fontNames == null) {
            List<String> names = new ArrayList<String>();

            for(FontLoader item : FontLoader.values()) {
                names.add(item.name);
            }

            FontLoader.fontNames = new String[names.size()];
            FontLoader.fontNames = names.toArray(FontLoader.fontNames);
        }

        return FontLoader.fontNames;
    }

    /**
     * Get all possible font long names as array.
     *
     * @return
     *   All possible font long names.
     */
    public static String[] getFontLongNames() {
        if(FontLoader.fontLongNames == null) {
            List<String> longNames = new ArrayList<String>();

            for(FontLoader item : FontLoader.values()) {
                longNames.add(item.longName);
            }

            FontLoader.fontLongNames = new String[longNames.size()];
            FontLoader.fontLongNames = longNames.toArray(FontLoader.fontLongNames);
        }

        return FontLoader.fontLongNames;
    }
}
