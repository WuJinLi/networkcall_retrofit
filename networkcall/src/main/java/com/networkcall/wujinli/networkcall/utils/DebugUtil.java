package com.networkcall.wujinli.networkcall.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * 
 * ClassName: DebugUtil
 * Function: TODO 打印日志.
 * Reason: TODO ADD REASON(可选).
 * date: 2014-4-17 上午11:14:13
 *
 * @author
 * @since JDK 1.6
 */
public class DebugUtil {
	 public static final boolean DEBUG = true;
     
	    public static void toast(Context context, String content){
	        Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
	    }
	    
	    public static void v(String tag, String msg){
	        if(DEBUG){
	        	Log.v(tag, msg);
	        }
	    }
	     
	    public static void d(String tag, String msg){
	        if(DEBUG){
	        	Log.d(tag, msg);
	        }
	    }
	    
	    public static void i(String tag, String msg){
	        if(DEBUG){
	        	Log.i(tag, msg);
	        }
	    }
	    
	    public static void w(String tag, String msg){
	        if(DEBUG){
	        	Log.w(tag, msg);
	        }
	    }
	    
	    public static void e(String tag, String msg){
	        if(DEBUG){
	        	Log.e(tag, msg);
	        }
	    }   
}
