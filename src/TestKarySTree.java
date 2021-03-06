import java.util.Random;


public class TestKarySTree {
	static KArySearchTree tree;
	private static int THREAD_COUNT = 8;
	private static int K = 4;
	private static Random integerGenerator = new Random();
	private static int INSERT_FRACTION = 10;
	private static int REMOVE_FRACTION = 10;
	private static int CONTAINS_FRACTION = 80;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		tree = new KArySearchTree(K);
		
		THREAD_COUNT = (args.length >= 1 ? Integer.parseInt(args[0]) : THREAD_COUNT);
		K = (args.length >= 2 ? Integer.parseInt(args[1]) : K );
		
		if(K <= 1){
			System.out.println("Wrong value of K");
			return;
		}
		INSERT_FRACTION = (args.length >= 3 ? Integer.parseInt(args[2]) : INSERT_FRACTION );
		REMOVE_FRACTION = (args.length >= 4 ? Integer.parseInt(args[3]) : REMOVE_FRACTION );
		CONTAINS_FRACTION = (args.length >= 5 ? Integer.parseInt(args[4]) : CONTAINS_FRACTION );
		
		Thread[] threads = new TestThread[THREAD_COUNT];
		for (int i = 0; i < THREAD_COUNT; i++) {
			threads[i] = new TestThread();
		    }
		
		for (int i = 0; i < THREAD_COUNT; i ++) {
			threads[i].start();
		    }
		double startTime = System.currentTimeMillis();
		double timeElapsed = 0;
		while( timeElapsed < 2000){
			timeElapsed = System.currentTimeMillis()-startTime;
		}
		for (int i = 0; i < THREAD_COUNT; i ++) {
			threads[i].interrupt();
		    }
		long totalOperations = 0;
		for (int i = 0; i < THREAD_COUNT; i++) {
			totalOperations += ((TestThread) threads[i]).numberOfOperations;
		    }
		System.out.println("Total Operations Executed for thread count ="+THREAD_COUNT+" is "+totalOperations);
		while(timeElapsed < 3000){
			timeElapsed = System.currentTimeMillis()-startTime;
		}
		System.exit(0);
	}
	
	static class TestThread extends Thread {
	    public long elapsedTime;
	    public long pushes;
	    public long pops;
	    public boolean running = true;
	    private static int THREAD_ID = 0;
	    private int threadId;
	    public int numberOfOperations = 0;
	    TestThread() {
	    	//System.out.println("Test Thread Created");
	    	this.elapsedTime = 0;
	    	this.pushes = 0;
	    	this.pops = 0;
	    	this.threadId = THREAD_ID++;
	    }
	    
	    public void run() {	 
	    	while(!Thread.interrupted()){
	    		//int operationType = integerGenerator.nextInt(2);
	    		
	    		int addOperations = (int) ((INSERT_FRACTION*1.0/100)*20);
	    		int removeOperations = (int) ((REMOVE_FRACTION*1.0/100)*20);
	    		int containOperations = (int) ((CONTAINS_FRACTION*1.0/100)*20);
	    		
	    		int key;
		    	for(int i = 0 ; i < addOperations; ++i){
		    		key = integerGenerator.nextInt(1000);
		    		tree.insert(key);
		    	}
		    	for(int i = 0 ; i < containOperations; ++i){
		    		key = integerGenerator.nextInt(1000);
		    		tree.containsKey(key);
		    		}
		        for(int i = 0 ; i < removeOperations; ++i){
		    		key = integerGenerator.nextInt(1000);
		    		tree.remove(key);	    		
		    	}
		        numberOfOperations += 20;	
	    	}
	    	//System.out.println("Thread with ID ="+threadId+" Executed Successfully");
	    }
	}
}
