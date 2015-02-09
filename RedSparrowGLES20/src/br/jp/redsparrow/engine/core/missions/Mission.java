package br.jp.redsparrow.engine.core.missions;

import br.jp.redsparrow.engine.core.Game;
import br.jp.redsparrow.engine.core.Vector2f;
import br.jp.redsparrow.engine.core.physics.BCircle;

public abstract class Mission {

	protected BCircle mBounds; 
	
	protected boolean mComplete;
	protected boolean mTriggered;
	
	public Mission( String name, String description, float x, float y, float range) {
		
		mBounds = new BCircle( new Vector2f(x, y), range );
		
		mComplete = false;
		mTriggered = false;
		
	}
	
	public abstract void update(Game game);
	
	public boolean isComplete() {
		return mComplete;
	}
	
	public void complete() {
		mComplete = true;
	}
	
	public boolean wasTriggered() {
		return mTriggered;
	}
	
	public void trigger() {
		mTriggered = true;
	}
	
	public void reset() {
		mComplete = false;
		mTriggered = false;
	}

}
