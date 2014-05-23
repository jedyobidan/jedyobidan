package jedyobidan.nsound;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SampledSound extends Sound {
	private Clip clip;

	public SampledSound(InputStream in) throws LineUnavailableException,
			IOException, UnsupportedAudioFileException {
		clip = AudioSystem.getClip();
		clip.open(AudioSystem.getAudioInputStream(in));
	}

	public void play(int numOfTimes) {
		if (numOfTimes == 0) {
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		} else {
			clip.loop(numOfTimes - 1);
		}
	}

	@Override
	public void pause() {
		clip.stop();
	}

	@Override
	public boolean isPlaying() {
		return clip.isRunning();
	}

	@Override
	public void setPositionInMicroseconds(long position) {
		clip.setMicrosecondPosition(position);
	}

	@Override
	public long getPositionInMicroseconds() {
		return clip.getMicrosecondPosition();
	}

	@Override
	public long getLengthInMicroseconds() {
		return clip.getMicrosecondLength();
	}

	@Override
	public void setTempoFactor(float factor)
			throws UnsupportedOperationException {
		throw new UnsupportedOperationException(
				"Cannot Change Tempo of sampled sound");
	}

	@Override
	public void setVolumeFactor(double factor)
			throws UnsupportedOperationException {
		FloatControl gainControl = (FloatControl) clip
				.getControl(FloatControl.Type.MASTER_GAIN);
		float dB = (float) (Math.log(factor) / Math.log(10.0) * 20.0);
		gainControl.setValue(dB);
	}

	@Override
	public void dispose() {
		clip.close();
	}

	@Override
	public void onPlaybackEnd(final ActionListener a) {
		clip.addLineListener(new LineListener() {

			@Override
			public void update(LineEvent e) {
				if (e.getType().equals(LineEvent.Type.STOP)) {
					a.actionPerformed(new ActionEvent(SampledSound.this,
							ActionEvent.ACTION_PERFORMED, "Playback Ended"));
					clip.removeLineListener(this);
				}
			}

		});
	}
}
