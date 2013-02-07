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
import java.util.ArrayList;
import java.awt.Image;

public class Letter {
	
	private int letterNum;
	private Image  letterImage;
	
	public Letter(){
		letterNum=0;
		letterImage=null;
	}
	
	public int getLetterNum() {
		return letterNum;
	}
	public void setLetterNum(int letterNum) {
		this.letterNum = letterNum;
	}
	public Image getLetterImage() {
		return letterImage;
	}
	public void setLetterImage(Image letterImage) {
		this.letterImage = letterImage;
	}	
}