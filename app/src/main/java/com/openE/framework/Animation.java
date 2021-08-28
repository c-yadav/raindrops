package com.openE.framework;

import java.util.ArrayList;
import com.openE.framework.Image;

public class Animation {
	private ArrayList<AnimFrame> frames; // will contain AnimFrame objects
	private int currentFrame; // integer value index of the current frame in the
								// ArrayList
	private long animTime; // long takes up more memory than int but can hold
							// more accurate numbers.
							// how much time has elapsed since the current image
							// was displayed
	private long totalDuration; // amount of time that each frame (image) will
								// be displayed for

	public Animation() {
		frames = new ArrayList<AnimFrame>();
		totalDuration = 0;

		synchronized (this) {
			animTime = 0;
			currentFrame = 0;
		}
	}

	private class AnimFrame { // nested class

		Image image;
		long endTime;

		public AnimFrame(Image image, long endTime) {
			this.image = image;
			this.endTime = endTime;
		}
	}

	public synchronized void addFrame(Image image, long duration) {
		totalDuration += duration;
		frames.add(new AnimFrame(image, totalDuration));
	}

	public synchronized void update(long elapsedTime) {
		if (frames.size() > 1) {
			animTime += elapsedTime;
			if (animTime >= totalDuration) {
				animTime = animTime % totalDuration;
				currentFrame = 0;

			}

			while (animTime > getFrame(currentFrame).endTime) {
				currentFrame++;

			}
		}
	}

	private AnimFrame getFrame(int i) {
		return (AnimFrame) frames.get(i);
	}

	public synchronized Image getImage() {
		if (frames.size() == 0) {
			return null;
		} else {
			return getFrame(currentFrame).image;
		}
	}

}
