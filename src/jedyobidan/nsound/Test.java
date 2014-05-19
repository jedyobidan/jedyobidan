package jedyobidan.nsound;

import java.util.Scanner;

class Test {
	public static void main(String[] args){
		Sound s = Sound.getSound(Test.class, "game2_n.mid");
		Sound s2 = Sound.getSound(Test.class, "game1.mid");
		Scanner s3 = new Scanner(System.in);
		s.play();
		while(s3.nextLine() != "quit"){
			if(s.isPlaying()){
				s.pause();
				s2.play();
			} else {
				s2.pause();
				s.play();
			}
		}
		s3.close();
	}
}
