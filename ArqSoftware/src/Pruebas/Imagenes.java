package Pruebas;

import java.awt.BorderLayout;
import java.awt.Image;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Imagenes {
	
	public static void main (String [] args){
		String u = "";
		prueba();
	}

	public static void prueba(){
		Image image = null;
		URL url = null;
		try {
			url = new URL(
			    "http://www.zuliapordentro.com/wp-content/uploads/2016/02/new-google-logo.jpg");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    try {
			image = ImageIO.read(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Use a label to display the image
		JFrame frame = new JFrame();

		JLabel lblimage = new JLabel(new ImageIcon(image));
		frame.getContentPane().add(lblimage, BorderLayout.CENTER);
		frame.setSize(300, 400);
		frame.setVisible(true);


		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(lblimage);
		// add more components here
		frame.add(mainPanel);
		frame.setVisible(true);
	}
}
