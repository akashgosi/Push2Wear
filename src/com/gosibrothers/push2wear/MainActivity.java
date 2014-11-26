package com.gosibrothers.push2wear;


import android.app.Activity;
import android.app.Fragment;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


public class MainActivity extends Activity {
	
	//To store the push message
	public final static String PUSH_MESSAGE = "com.gosibrothers.push2wear.MESSAGE";
	
	//Group to stack the push messages
	final static String GROUP_KEY_PUSHES = "group_key_pushes";
    
	//To keep track of the notifications
	static Integer notificationId = 000;;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }
    
    public void sendMessage(View view){
    	
    	
    	String eventTitle = "Text Push";
    	
    	// Build intent for notification content
    	Intent viewIntent = new Intent(this, DisplayMessageActivity.class);
    	EditText editText = (EditText) findViewById(R.id.edit_message);
    	String message = editText.getText().toString();
    	viewIntent.putExtra(PUSH_MESSAGE, message);
    	PendingIntent viewPendingIntent =  PendingIntent.getActivity(this, 0, viewIntent, 0);
    	
    	//Create new notification
    	Notification notif = new NotificationCompat.Builder(getBaseContext())
    	//.setAutoCancel(true)
    	.setContentIntent(viewPendingIntent)
    	.setContentTitle(eventTitle.concat(notificationId.toString()))
        .setContentText(message)
        .setSmallIcon(R.drawable.ic_launcher)
        .setGroup(GROUP_KEY_PUSHES)
        .setGroupSummary(true)
        .build();

    	// Get an instance of the NotificationManager service
    	NotificationManagerCompat notificationManager =
    	        NotificationManagerCompat.from(this);
    	
    	// Build the notification and issues it with notification manager.
    	
    	notificationManager.notify(notificationId++, notif);
    	
    	startActivity(viewIntent);
    }
}
