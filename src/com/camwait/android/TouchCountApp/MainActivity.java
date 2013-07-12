package com.camwait.android.TouchCountApp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.TextView;

import com.camwait.android.TouchCountApp.utils.Util;

public class MainActivity extends Activity {

	private int mCount;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initialization();
	}
	
	public void initialization() {
		findViewById(R.id.btn_count_add).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mCount++;
				((TextView)findViewById(R.id.tv_count_number)).setText(Util.numberArab2CN(mCount));
			}
		});
		
//		findViewById(R.id.btn_count_add).setOnTouchListener(new OnTouchListener() {
//			
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				// TODO Auto-generated method stub
//				mCount++;
//				((TextView)findViewById(R.id.tv_count_number)).setText(Util.numberArab2CN(mCount));
//				return true;
//			}
//		});
		
//		findViewById(R.id.btn_count_add).setOnLongClickListener(new OnLongClickListener() {
//			
//			@Override
//			public boolean onLongClick(View v) {
//				// TODO Auto-generated method stub
//				mCount++;
//				((TextView)findViewById(R.id.tv_count_number)).setText(Util.numberArab2CN(mCount));
//				return false;
//			}
//		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
