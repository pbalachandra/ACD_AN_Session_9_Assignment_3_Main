package com.acadgild.balu.acd_an_session_9_assignment_3_main;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    TextView    textView_title;
    EditText    editText_number;
    Button      button_showDialog, button_okay, button_cancel;
    int         mNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_showDialog = (Button) findViewById(R.id.button_showDialog);
        button_showDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                mNumber = 0;
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);

                View viewDialog = layoutInflater.inflate(R.layout.custom_dialog, null);
                alertDialog.setView(viewDialog);

                final AlertDialog dialog_display = alertDialog.create();
                dialog_display.show();

                textView_title = (TextView) viewDialog.findViewById(R.id.textView_title);
                editText_number = (EditText) viewDialog.findViewById(R.id.editText_number);
                button_okay = (Button) viewDialog.findViewById(R.id.button_okay);
                button_cancel = (Button) viewDialog.findViewById(R.id.button_cancel);

                button_okay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        if (TextUtils.isEmpty(editText_number.getText().toString()))
                        {
                            editText_number.setError(getResources().getString(R.string.title));
                        }
                        else
                        {
                            mNumber = Integer.parseInt(editText_number.getText().toString());
                            dialog_display.dismiss();
                            new Dialog_Task().execute(mNumber);
                        }
                    }
                });

                button_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        dialog_display.dismiss();
                    }
                });
            }
        });

    }

    class Dialog_Task extends AsyncTask<Integer, Integer, Void>
    {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(MainActivity.this, null,
                    String.format(getResources().getString(R.string.progress), mNumber));

        }

        @Override
        protected Void doInBackground(Integer... params)
        {
            int i = 0;

            try {
                for (i = 1; i < mNumber; i++)
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            publishProgress(i);
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
            final AlertDialog.Builder alertDialogResult = new AlertDialog.Builder(MainActivity.this);
            alertDialogResult.setTitle(getResources().getString(R.string.dialog_title));
            alertDialogResult.setMessage(getResources().getString(R.string.dialog_message));

            alertDialogResult.setPositiveButton(getResources().getString(R.string.ok),
                                                new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    dialog.dismiss();
                }
            });
            alertDialogResult.create();
            alertDialogResult.show();
        }
    }

}
