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
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class QImagesStructure {
	
	ArrayList<QuizImage> Quizimages;

	public QImagesStructure(){
		QuizImage img1;
		BufferedImage tempImg = null;
		File directory = new File("quiz PNGs/");
		File[] listed=directory.listFiles();
		String temp = null;
		String imgname=null;
		Quizimages= new ArrayList<>(listed.length);
		System.out.println("number of files in Quiz images"+listed.length);
		for (int i=0; i < listed.length; i++) {
			try {
			temp = "quiz PNGs/";
			temp= temp + listed[i].getName();
			
				tempImg = ImageIO.read (new File(temp));
				System.out.println("read of " + listed[i].getName()+" Success");
			} catch (IOException e) {
				System.out.println("Couldn't read " + listed[i].getName());
			}
			img1=new QuizImage();
			img1.setQuizImage(tempImg);
			imgname = listed[i].getName();
			imgname = imgname.substring(0, imgname.length()-4);
			System.out.println("here the name : "+ imgname);
			img1.setImageName(imgname);
			Quizimages.add(img1);
		}
	}

	
}
