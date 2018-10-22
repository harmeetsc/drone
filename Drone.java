import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.TreeMap;

public class DroneDelivery {

	private static double NEUTRAL_THRESHOLD = 60.0 * 60.0 * 2;
	private static double DETRACTOR_THRESHOLD = 60.0 * 60.0 * 4;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		/*File file = new File("C:\\Users\\chawl\\Desktop\\test1.txt"); */
		File file = new File(args[0]);
		String fileName = "output.txt";
		
		Scanner sc;
		int ordTotal = 0, ordProm = 0, ordNeu = 0, ordDetrac = 0; 
		TreeMap<Double, LinkedHashMap<String, Date>> fastMap = new TreeMap<>();
		TreeMap<Double, LinkedHashMap<String, Date>> medMap = new TreeMap<>();
		TreeMap<Double, LinkedHashMap<String, Date>> slowMap = new TreeMap<>();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		try {
			Date d = sdf.parse("06:00:00");
			Date dEnd = sdf.parse("22:00:00");
			sc = new Scanner(file);
			Writer fileWriter = new FileWriter(fileName);
			while (sc.hasNextLine()) {
				
				ordTotal++;
				
				/*System.out.println(fastMap);
				System.out.println(medMap);
				System.out.println(slowMap);
				System.out.println("\n");
*/				
				String a = sc.nextLine().toString();
				String str[] = a.split("\\s");
				
				//converting coordinates to Distance
				String s[] = str[1].split("W|E");
				String orderNum = str[0];
				int x = Integer.parseInt(s[1]);
				int y = Integer.parseInt(s[0].substring(1));
				double distance = Math.sqrt(x*x + y*y);
				double timeSec = distance * 60;
				//timeSec = Math.round(timeSec * 100.0)/100.0;
				// TODO put all this in a class object
				
				//System.out.println(distance);		
				
				//time formatter				
			     Date d2 = sdf.parse(str[2]);
			   
				Order mOrder = new Order(orderNum, mAddress, d2);
			    
			    
			    //System.out.println(orderNum + " " + distance + " " + sdf.format(d2));
			    // TODO repeatable code, put it in a method
			    if(d2.compareTo(d) <= 0 ) {
			    	TreeMap<Double, LinkedHashMap<String, Date>> map;
			    	if(timeSec < NEUTRAL_THRESHOLD ) {
			    		map = fastMap;
			    	}else if(timeSec < DETRACTOR_THRESHOLD) {
			    		map = medMap;
			    	}else {
			    		map = slowMap;
			    	}
			    	if( map.get(timeSec) == null ) {
			    		LinkedHashMap<String, Date> map2 = new LinkedHashMap<>();
			    		map2.put(mOrder.getOrdId(), mOrder.getTimeOfOrder());
			    		map.put(timeSec, map2);
			    	}else {
			    		LinkedHashMap<String, Date> map2 = map.get(timeSec);
			    		map2.put(mOrder.getOrdId(), mOrder.getTimeOfOrder());
			    	}
			    	//long elapsed = (d2.getTime() - d1.getTime())/10000; 
			    	//System.out.println(elapsed);
			    
			    	/*Calendar cal = Calendar.getInstance();
			    	cal.setTime(d2);
			    	cal.add( Calendar.SECOND , 10);
			    	String newTime = sdf.format(cal.getTime());*/
			    }else {
			    	/*for(Double dist : map.keySet()) {
			    		System.out.println(dist);
			    	}*/
			    	while(d2.compareTo(d) > 0) {
			    		TreeMap<Double, LinkedHashMap<String, Date>> map;
			    		
			    		if(fastMap.size() != 0) {
			    			map = fastMap;
			    		}else if(medMap.size() != 0){
			    			map = medMap;
			    		} else if(slowMap.size() != 0) {
			    			map = slowMap;
			    		}else {
			    			break;
			    		}
			    
			    		double tTemp = map.firstKey();
			    		LinkedHashMap<String, Date> map2 = map.get(tTemp);
			    		String tempONum = map2.keySet().iterator().next();
			    		Date tempDate = map2.get(tempONum);
			    		if(d2.compareTo(d) > 0 ) {
			    			d = tempDate;
			    		}
			    		System.out.println(tempONum + " " + sdf.format(d) );
			    		fileWriter.write(tempONum + " " + sdf.format(d) );
			    		Calendar cal = Calendar.getInstance();
			    		cal.setTime(d);
			    		long diff = Math.abs(d.getTime() - tempDate.getTime());
			    		double time = diff/1000;
			    		//System.out.println(time);
			    		//System.out.println(tempONum + " " + sdf.format(cal.getTime()) + " " + dTemp);
			    		int add = (int)(2 * tTemp *10);
			    		if(add %10 != 0) {
			    			add = add/10;
			    			add++;
			    		}else {
			    			add = add/10;
			    		}
			    		time = time + add/2.0;
			    		if(time < NEUTRAL_THRESHOLD ) {
				    		ordProm++;
				    	}else if(timeSec < DETRACTOR_THRESHOLD) {
				    		ordNeu++;
				    	}else {
				    		ordDetrac++;
				    	}
			    		//System.out.println(add);
			    		cal.add( Calendar.SECOND , add);
			    		d = cal.getTime();
			    		
			    		
			    		//System.out.println(sdf.format(cal.getTime()));
			    		map2.remove(tempONum);
			    		if(map2.size() != 0) {
			    			map.replace(tTemp, map2);
			    		}else {
			    			map.remove(tTemp);
			    		}
			    		
			    		
			    	}
			    	
			    	TreeMap<Double, LinkedHashMap<String, Date>> map;
			    	if(timeSec < NEUTRAL_THRESHOLD ) {
			    		map = fastMap;
			    	}else if(timeSec < DETRACTOR_THRESHOLD) {
			    		map = medMap;
			    	}else {
			    		map = slowMap;
			    	}
			    	
			    	if( map.get(timeSec) == null ) {
			    		LinkedHashMap<String, Date> map2 = new LinkedHashMap<>();
			    		map2.put(orderNum, d2);
			    		map.put(timeSec, map2);
			    	}else {
			    		LinkedHashMap<String, Date> map2 = map.get(timeSec);
			    		map2.put(str[0], d2);
			    	}
			    	
			    }
			}
			
			while(dEnd.compareTo(d) > 0) {
				// TODO repeatable code, put it in a method
				TreeMap<Double, LinkedHashMap<String, Date>> map;
				if(fastMap.size() != 0) {
	    			map = fastMap;
	    		}else if(medMap.size() != 0){
	    			map = medMap;
	    		} else if(slowMap.size() != 0) {
	    			map = slowMap;
	    		}else {
	    			break;
	    		}
				
				double tTemp = map.firstKey();
	    		LinkedHashMap<String, Date> map2 = map.get(tTemp);
	    		String tempONum = map2.keySet().iterator().next();
	    		Date tempDate = map2.get(tempONum);
	    		System.out.println(tempONum + " " + sdf.format(d) );
	    		Calendar cal = Calendar.getInstance();
	    		cal.setTime(d);
	    		long diff = Math.abs(d.getTime() - tempDate.getTime());
	    		double time = diff/1000;
	    		//System.out.println(time);
	    		//System.out.println(tempONum + " " + sdf.format(cal.getTime()) + " " + dTemp);
	    		int add = (int)(2 * tTemp * 10);
	    		if(add %10 != 0) {
	    			add = add/10;
	    			add++;
	    		}else {
	    			add = add/10;
	    		}
	    		time = time + add/2;
	    		if(time < NEUTRAL_THRESHOLD ) {
		    		ordProm++;
		    	}else if(time < DETRACTOR_THRESHOLD) {
		    		ordNeu++;
		    	}else {
		    		ordDetrac++;
		    	}
	    		//add = add/100;
	    		//System.out.println(add);
	    		cal.add( Calendar.SECOND , add);
	    		d = cal.getTime();
	    		//System.out.println(sdf.format(cal.getTime()));
	    		map2.remove(tempONum);
	    		if(map2.size() != 0) {
	    			map.replace(tTemp, map2);
	    		}else {
	    			map.remove(tTemp);
	    		}
	    		
				
			}
			
			double nps = (ordProm *100/ordTotal) - (ordDetrac*100/ordTotal);
			System.out.println(nps);
			fileWriter.write("NPS " + nps);
			fileWriter.close();
			System.out.println(System.getProperty("user.dir") + "\\" + fileName);
			
		} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*System.out.println(ordProm);
		System.out.println(ordNeu);
		System.out.println(ordDetrac);*/
	}
}


