package SA;

import modal.Schedule;

public class SimulatedAnnealing {
	public static Schedule execute(Schedule initial) {
		double T = 10;
		double coolingRate = 0.9995;
		Schedule current = new Schedule(initial);
		System.out.println("the first Schedule H1: " + current.getH1());
		int i = 0;
		while ((current.getH1() != 0)) {
			if (current.getH1() == 0) {
				return current;
			} else {
			Schedule next = current.selectScheduleBest();
//			tinh deltaE
				int deltaE = next.getH1() - current.getH1();
				// next schedule better current
				if (deltaE < 0) {
					current = new Schedule(next);
					
				} else if (Math.exp(deltaE / T) > Math.random()) {
					current = new Schedule(next);
				}
				T = T * coolingRate;
				i++;
				
			}
				System.out.println("Iteration #" + i + " t: " + T);
		}
		System.out.println("T: " + T);
		return current;
	}
}
