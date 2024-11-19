//M. M. Kuttel 2024 mkuttel@gmail.com
// Simple Thread class to update the display of a text field
package medleySimulation;

import java.awt.Color;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class CounterDisplay implements Runnable {

    private FinishCounter results;
    private JLabel win;
    private ImageIcon dancingGuyIcon;

    CounterDisplay(JLabel w, FinishCounter score) {
        this.win = w;
        this.results = score;
        
        // Load the dancing guy PNG from the resources or local folder
        ImageIcon originalIcon = new ImageIcon(getClass().getResource("dance.png"));
        
        // Resize the image to desired dimensions (e.g., 50x50)
        Image scaledImage = originalIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        this.dancingGuyIcon = new ImageIcon(scaledImage);
    }

    public void run() { // this thread just updates the display of a text field
        boolean showText = true; // Used to toggle between showing and hiding the text
        while (true) {
            if (results.isRaceWon()) {
                win.setIcon(dancingGuyIcon); // Set the resized dancing guy image
                if (showText) {
                    win.setForeground(Color.RED);
                    win.setText("Winning Team: " + results.getWinningTeam() + "!!");
                } else {
                    win.setText(""); // Hide the text for the flash effect
                }
                showText = !showText; // Toggle between showing and hiding
            } else {
                win.setForeground(Color.BLACK);
                win.setText("------");
                win.setIcon(null); // Remove the image when no team has won
            }
            try {
                // Pause to create the flashing effect
                Thread.sleep(500); // Adjust the sleep time for faster/slower flashing
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}