package de.tobbexiv.vorpruefungsleistung.more;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import de.tobbexiv.vorpruefungsleistung.R;
import de.tobbexiv.vorpruefungsleistung.main.MainActivity;

/**
 * Listens to clicks on buttons on the display, handles the clicks accordingly.
 */
class MoreActivityButtonClickListener implements View.OnClickListener {
    /**
     * The actvity which created the listener.
     */
    private AppCompatActivity creator;

    private AlertDialog authorModal;

    /**
     * Create a new button click listener, attach the click listener.
     *
     * @param creator
     *   The activity which creates this instance.
     */
    public MoreActivityButtonClickListener(AppCompatActivity creator) {
        this.creator = creator;

        attachListener();
    }

    /**
     * Attach all listener to the buttons.
     */
    private void attachListener() {
        this.creator.findViewById(R.id.btn_showAuthorModal).setOnClickListener(this);
        this.creator.findViewById(R.id.btn_killApp).setOnClickListener(this);
        this.creator.findViewById(R.id.btn_back).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Button clicked = (Button) v;

        switch(clicked.getId()) {
            case R.id.btn_showAuthorModal:
                showAuthorModal();
                break;

            case R.id.btn_killApp:
                killApp();
                break;

            case R.id.btn_back:
                goBack();
                break;

            default:
                break;
        }
    }

    /**
     * Show a modal with information about the app developer.
     */
    private void showAuthorModal() {
        if(this.authorModal == null) {
            this.authorModal = new AlertDialog.Builder(this.creator)
                    .setTitle(R.string.authorModalTitle)
                    .setView(R.layout.author_modal)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            authorModal.dismiss();
                        }
                    })
                    .create();
        }

        this.authorModal.show();
    }

    /**
     * Ends the app completely.
     */
    private void killApp() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("EXIT", true);
        this.creator.setResult(Activity.RESULT_OK, resultIntent);
        this.creator.finish();
    }

    /**
     * Go back to the main screen.
     */
    private void goBack() {
        this.creator.finish();
    }
}
