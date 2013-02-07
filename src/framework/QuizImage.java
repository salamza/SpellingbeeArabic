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

public class QuizImage {
	private String ImageName;
	private Image  QuizImage;
	boolean difffculty;
	
	public QuizImage(){
		ImageName=null;
		QuizImage=null;
	}
	public String getImageName() {
		return ImageName;
	}
	public void setImageName(String imageName) {
		ImageName = imageName;
	}
	public Image getQuizImage() {
		return QuizImage;
	}
	public void setQuizImage(Image quizImage) {
		QuizImage = quizImage;
	}
}
