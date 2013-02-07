package framework;
/*
author:Sameh Salama.
Date: 7/1/2013
Email:eng.sameh11@gmail.com

This file is part a of SpellingBee Application
SpellingBee Application is a free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

SpellingBee Application is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with SpellingBee Application. If not, see <http://www.gnu.org/licenses/>.
*/
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;

public class LettersStructure {

	ArrayList<Letter> arabicLetters ;
	
	public LettersStructure() {
		String tmp;
		int temp2;
		Letter L;
		BufferedImage tempImg = null;
		File directory = new File("letters PNGs/");
		File[] listed=directory.listFiles();
		Arrays.sort(listed);
		String temp = null;
		String tmp1=null;
		arabicLetters = new ArrayList<Letter>(listed.length);
		System.out.println("number of files"+listed.length);
		for (int i=0; i < listed.length; i++) {
			temp2 =i+1;
			tmp1 = Integer.toString(temp2) + ".png";
			try {
			temp = "letters PNGs/";
			temp= temp + tmp1;
				tempImg = ImageIO.read (new File(temp));
				System.out.println("Read of " + listed[i].getName()+ " Success");
			} catch (IOException e) {
				System.out.println("Couldn't read " + listed[i].getName());
			}
			L = new Letter();
			L.setLetterImage(tempImg);
			tmp=listed[i].getName();
			tmp = tmp.substring(0, tmp.length()-4);
			L.setLetterNum(Integer.parseInt(tmp));
			arabicLetters.add(L);
		}
	}
}