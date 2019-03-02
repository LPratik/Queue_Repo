package com.example.demo.util;

import java.util.Date;
import java.util.List;

import com.example.demo.entities.AverageTime;
import com.example.demo.repositories.AvgServiceTimeRepository;

public class TimeExtendThread implements Runnable{
 
    static TimeExtendThread target = null;
    static Thread bix = null;
    static int count =0;
    static String text;
    static AvgServiceTimeRepository avgTimeRepository;
    static Date startTime;
    static int avgServSecs;
    
    
    private TimeExtendThread() {
        if(bix == null)
        {
            // TODO Auto-generated constructor stub
            bix = new Thread(this);
            bix.setName("TimeExtendThread");
            bix.start();
        }
    }
 
    public static TimeExtendThread getTarget(AvgServiceTimeRepository val1, Date val2)
    {
        if(target==null)
        {
            createNewTarget(val1,val2);
        }else{
        	TimeExtendThread.startTime = val2;
        	TimeExtendThread.avgServSecs=((AverageTime) ((List)avgTimeRepository.findAll()).get(0)).getAverageTime();
        }
        return target;
 
    }
 
    private static void createNewTarget(AvgServiceTimeRepository val1, Date val2)
    {
        target = new TimeExtendThread();
        avgTimeRepository = val1;
        startTime=val2;
        //avgServSecs=((AverageTime) ((List)avgTimeRepository.findAll()).get(0)).getAverageTime();
    }
 
 
 
    public void run()
    {
    	avgServSecs=((AverageTime) ((List)avgTimeRepository.findAll()).get(0)).getAverageTime();
    	System.out.println("***** Average Service Time considered as " + avgServSecs +" *****");
        while(true)
        {
            /*System.out.println(text+"->"+count);
 
            try {
                bix.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            count++;*/
        	int elapsedSecs = (int)(new Date().getTime() - startTime.getTime())/1000;
        	if(elapsedSecs == avgServSecs){
        		avgServSecs+=300;
        		
        		//avgTimeRepository.deleteAll();
        		AverageTime avgTimeObj = avgTimeRepository.getAvgTimeById(1);
        		if(avgTimeObj!=null) {
        		avgTimeObj.setAverageTime(avgServSecs);
        			avgTimeObj.setUpdated(new Date());
        		avgTimeRepository.save(avgTimeObj);
        		}
        		//AverageTime avgTimeObj = new AverageTime();
        		//avgTimeObj.setAverageTime(avgServSecs);
        		//avgTimeRepository.save(avgTimeObj);
        		System.out.println("***** Average Service Time updated as " + avgServSecs +" *****" );
        	}
        	System.out.println("Elapsed seconds : " +elapsedSecs);
        	try {
                bix.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
