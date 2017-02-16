package com.example.franck.candycrush_like;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class BaseActivity extends AppCompatActivity {

    @Override
    /**  https://developer.android.com/training/appbar/actions.html */
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.exit:
                confirm_exit();
                return true;
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /** Crée la boite de dialogue
     *     -   https://developer.android.com/guide/topics/ui/dialogs.html */
    protected void confirm_exit(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage(R.string.dialog_exit);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Oui",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //dialog.cancel();
                        exit();
                    }
                });

        builder1.setNegativeButton(
                R.string.cancel,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    /** Fermer l'application
     *  http://stackoverflow.com/questions/3226495/how-to-exit-from-the-application-and-show-the-home-screen */
    public void exit() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("Quit", true);
        startActivity(intent);
        finish();
    }
}


