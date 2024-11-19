package medleySimulation;

import medleySimulation.Swimmer.SwimStroke;
import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;


public class SwimTeam extends Thread {

    public static StadiumGrid stadium; // shared
    private Swimmer[] swimmers;
    private int teamNo; // team number
    CountDownLatch latch = new CountDownLatch(1);
    CountDownLatch firstlatch = new CountDownLatch(1);
    CountDownLatch secondlatch = new CountDownLatch(1);
        

    public static final int sizeOfTeam = 4;

    SwimTeam(int ID, FinishCounter finish, PeopleLocation[] locArr) {
        this.teamNo = ID;

        swimmers = new Swimmer[sizeOfTeam];
        SwimStroke[] strokes = SwimStroke.values(); // Get all enum constants
        stadium.returnStartingBlock(ID);

        
        for (int i = teamNo * sizeOfTeam, s = 0; i < ((teamNo + 1) * sizeOfTeam); i++, s++) { // initialize swimmers in team
            locArr[i] = new PeopleLocation(i, strokes[s].getColour());
            int speed = (int) (Math.random() * (3) + 30); // range of speeds
            swimmers[s] = new Swimmer(i, teamNo, locArr[i], finish, speed, strokes[s],this); // pass the swimTeam to the swimmer
        }

    }

    public void run() {
        try {	
			for(int s=0;s<sizeOfTeam; s++) { //start swimmer threads
				swimmers[s].start();				
			}
         
         for(int s=0;s<sizeOfTeam; s++) swimmers[s].join();		

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
