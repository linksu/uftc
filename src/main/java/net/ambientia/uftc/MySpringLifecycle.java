package net.ambientia.uftc;

import org.springframework.context.SmartLifecycle;

public class MySpringLifecycle implements SmartLifecycle {

	private volatile boolean isRunning = false;
	private BootStrap bootStrap;

	@Override
	public boolean isRunning() {
		// TODO Auto-generated method stub
		return isRunning;

	}

	@Override
	public void start() {
		System.out.println("STARTED!!!");
		isRunning = true;
		bootStrap.runBootStrap();

	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

	@Override
	public int getPhase() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isAutoStartup() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void stop(Runnable arg0) {
		// TODO Auto-generated method stub

	}

	public BootStrap getBootStrap() {
		return bootStrap;
	}

	public void setBootStrap(BootStrap bootStrap) {
		this.bootStrap = bootStrap;
	}

}
