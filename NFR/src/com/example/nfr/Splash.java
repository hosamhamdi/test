package com.example.nfr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;


public class Splash extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		  Thread logoTimer=new Thread(){
	        	public void run() {
					try{
						sleep(1300);
						Intent intent = new Intent(Splash.this,MainActivity.class);
						startActivity(intent);
						
					} 
					catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					finally{
						finish();
					}
				}
	        };
	        
	        logoTimer.start();
	}

	
	
}
