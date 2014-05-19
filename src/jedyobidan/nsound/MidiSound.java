package jedyobidan.nsound;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.midi.*;

public class MidiSound extends Sound {
	private Sequencer sequencer;
	private Synthesizer synth;
	private Receiver receiver;

	public MidiSound(InputStream in) throws MidiUnavailableException,
			IOException, InvalidMidiDataException {
		sequencer = MidiSystem.getSequencer();
		synth = MidiSystem.getSynthesizer();
		receiver = MidiSystem.getReceiver();

		sequencer.open();
		synth.open();
		sequencer.getTransmitter().setReceiver(receiver);
		sequencer.setSequence(in);
	}

	@Override
	public void play(int times) {
		if (times == 0) {
			sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
		} else {
			sequencer.setLoopCount(times - 1);
		}
		sequencer.start();
	}

	@Override
	public void pause() {
		sequencer.stop();
	}
	

	@Override
	public boolean isPlaying() {
		return sequencer.isRunning();
	}

	@Override
	public void setPositionInMicroseconds(long position) {
		sequencer.setMicrosecondPosition(position);
	}

	@Override
	public long getPositionInMicroseconds() {
		return sequencer.getMicrosecondPosition();
	}

	@Override
	public long getLengthInMicroseconds() {
		return sequencer.getMicrosecondLength();
	}

	@Override
	public void setTempoFactor(float factor)
			throws UnsupportedOperationException {
		sequencer.setTempoFactor(factor);
	}

	@Override
	public void setVolumeFactor(double factor)
			throws UnsupportedOperationException {
		int midiVolume = (int) (factor * 127.0);
		if (synth.getDefaultSoundbank() == null) {
			ShortMessage volMessage = new ShortMessage();
			for (int i = 0; i < 16; i++) {
				try {
					volMessage.setMessage(ShortMessage.CONTROL_CHANGE, i, 7,
							midiVolume);
				} catch (InvalidMidiDataException e) {
				}
				receiver.send(volMessage, -1);
			}
		} else {
			MidiChannel[] channels = synth.getChannels();

			for (int c = 0; c < channels.length; c++) {
				if (channels[c] != null)
					channels[c].controlChange(7, midiVolume);
			}
		}
	}

	@Override
	public void dispose() {
		sequencer.close();
		synth.close();
	}

	@Override
	public void onPlaybackEnd(final ActionListener a) {
		sequencer.addMetaEventListener(new MetaEventListener(){
			@Override
			public void meta(MetaMessage e) {
				if(e.getType() == 47){
					a.actionPerformed(new ActionEvent(MidiSound.this, ActionEvent.ACTION_PERFORMED, "Playback Ended"));
					sequencer.removeMetaEventListener(this);
				}
			}
			
		});
	}

}
