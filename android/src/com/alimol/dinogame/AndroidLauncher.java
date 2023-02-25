package com.alimol.dinogame;

import android.os.Bundle;

import com.alimol.catgame.GameMain;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useGyroscope = false;
		config.useAccelerometer = false;
		config.useCompass = false;
		config.useRotationVectorSensor = false;
		config.useImmersiveMode = true;
		initialize(new GameMain(), config);
	}

}
