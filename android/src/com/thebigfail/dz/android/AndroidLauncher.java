package com.thebigfail.dz.android;
//to do : explore other config options
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.thebigfail.dz.Dz;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useAccelerometer=false;  //saves battery
        config.useCompass=false;    //saves battery
        config.useWakelock=true;    //keep screen awake when game is running.
		initialize(new Dz(), config);
	}
}
