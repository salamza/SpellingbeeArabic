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

import java.io.IOException;

import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;

public class Skeleton extends JFrame {

    public Skeleton() {
        try {
			add(new SpellingBoard());
		} catch (UnsupportedAudioFileException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        setTitle("Spelling Game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1280, 720);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }
    
    public static void main(String[] args) {
        new Skeleton();
 
    }
}