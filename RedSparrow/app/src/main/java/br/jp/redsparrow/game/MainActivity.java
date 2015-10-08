package br.jp.redsparrow.game;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import br.jp.redsparrow.engine.App;
import br.jp.redsparrow.engine.World;
import br.jp.redsparrow.engine.input.InputManager;
import br.jp.redsparrow.engine.input.SensorInput;
import br.jp.redsparrow.engine.input.TouchInput;
import br.jp.redsparrow.engine.math.Vec2;
import br.jp.redsparrow.engine.math.Vec3;
import br.jp.redsparrow.engine.rendering.Renderer;

public class MainActivity extends Activity implements View.OnTouchListener, SensorEventListener
{
	private GLSurfaceView glSurfaceView;


	@Override
	protected void onCreate (Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
		                                                 | View.SYSTEM_UI_FLAG_FULLSCREEN
		                                                 | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

		glSurfaceView = new GLSurfaceView(this);
		glSurfaceView.setEGLContextClientVersion(2);
		glSurfaceView.setRenderer(new Renderer());
		glSurfaceView.setOnTouchListener(this);
		App.registerAccelerometerListener(this);
		setContentView(glSurfaceView);

		InputManager.set(new InputManager(true, false) {
			@Override
			protected boolean onTouch_0 (TouchInput touchInput)
			{
//				Vec3 dir = World.player.loc.sub(new Vec3(touchInput.x, touchInput.y, 0));
//				dir.normalize();
//				dir = dir.mult(0.001f);
//				World.player.acl = dir;
				return true;
			}

			@Override
			protected boolean onTouch_1 (TouchInput touchInput)
			{
				return false;
			}

			@Override
			protected boolean onTouch_2 (TouchInput touchInput)
			{
				return false;
			}

			@Override
			protected boolean onTouch_3 (TouchInput touchInput)
			{
				return false;
			}

			@Override
			protected boolean onTouch_4 (TouchInput touchInput)
			{
				return false;
			}

			@Override
			protected boolean onSensorChanged (SensorInput sensorInput)
			{
				Vec2 dir = new Vec2(sensorInput.dir.x, sensorInput.dir.y);
				dir.normalize();
				Log.d("Player", dir.toString());
//				World.player.acl = new Vec3(dir.y, dir.x, 0.0f);
				return true;
			}
		});
	}

	@Override
	public boolean onTouch (View v, MotionEvent event)
	{
		if(InputManager.useTouch)
		{
			int pointerID = 0;
			int pointerIndex = 0;
			switch (event.getActionMasked())
			{
				case MotionEvent.ACTION_POINTER_DOWN:
					pointerIndex = event.getActionIndex();
					pointerID = event.getPointerId(pointerIndex);
				case MotionEvent.ACTION_DOWN:
					InputManager.setLastTouchInput(pointerID, new TouchInput(TouchInput.TYPE_DOWN, event.getX(pointerIndex), event.getY(pointerIndex)));
					break;
				case MotionEvent.ACTION_MOVE:
					for (int i = 0; i < event.getPointerCount(); i++)
						InputManager.setLastTouchInput(event.getPointerId(i), new TouchInput(TouchInput.TYPE_MOVE, event.getX(i), event.getY(i)));
					break;
				case MotionEvent.ACTION_POINTER_UP:
					pointerIndex = event.getActionIndex();
					pointerID = event.getPointerId(pointerIndex);
				case MotionEvent.ACTION_UP:
					InputManager.setLastTouchInput(pointerID, new TouchInput(TouchInput.TYPE_UP, event.getX(pointerIndex), event.getY(pointerIndex)));
					break;
			}

			return true;
		}

		return false;
	}

	@Override
	public void onSensorChanged (SensorEvent event)
	{
		if(InputManager.useSensor)
			InputManager.setLastSensorInput(new SensorInput(event.values[0], event.values[1], event.values[2]));
	}

	@Override
	public void onAccuracyChanged (Sensor sensor, int accuracy) {}

	@Override
	protected void onPause ()
	{
		super.onPause();
		App.unregisterAccelerometerListener(this);
	}

	@Override
	protected void onResume ()
	{
		super.onResume();
		App.registerAccelerometerListener(this);
	}

	@Override
	protected void onStop ()
	{
		super.onStop();
		App.unregisterAccelerometerListener(this);
	}
}
