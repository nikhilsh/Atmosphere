package io.atmosphr.droid.atmosphr;

import org.xwalk.core.XWalkView;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.widget.LinearLayout;

public class MainActivity extends Activity {
    private LinearLayout commentsLayout;
    private XWalkView xWalkWebView;
    private ExtensionEcho mExtension;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getActionBar();
        actionBar.hide();
        Log.d("W", "init intent");
        NdefMessage[] msgs= null;

        commentsLayout=(LinearLayout)findViewById(R.id.principal);
        xWalkWebView = new XWalkView(this.getApplicationContext(), this);
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
            Parcelable[] rawMsgs = getIntent().getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            if (rawMsgs != null) {
                msgs =  new NdefMessage[rawMsgs.length];
                for (int i = 0; i < rawMsgs.length; i++) {
                    msgs[i] = (NdefMessage) rawMsgs[i];
                }
            }
            Log.d("W", msgs[0].getRecords()[0].getPayload().toString());
            xWalkWebView.load("file:///android_asset/www/panorama.html?img=minecraft4.jpg", null);
            commentsLayout.addView(xWalkWebView);
        } else {
            xWalkWebView.load("file:///android_asset/www/index.html", null);
            commentsLayout.addView(xWalkWebView);
        }
//        ActionBar actionBar = getActionBar();
//        actionBar.hide();

//        commentsLayout=(LinearLayout)findViewById(R.id.principal);
//        xWalkWebView = new XWalkView(this.getApplicationContext(), this);
//        xWalkWebView.load("file:///android_asset/www/index.html", null);
//        commentsLayout.addView(xWalkWebView);
        //mExtension = new ExtensionEcho();
    }

}