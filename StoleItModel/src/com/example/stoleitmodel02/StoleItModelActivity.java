package com.example.stoleitmodel02;

import java.util.ArrayList;
import java.util.List;

import com.example.service.MySprite;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class StoleItModelActivity extends Activity {
	
	private LinearLayout lrope;
	private LinearLayout lsp;
	private MySprite msp;
	private Bitmap black;
	private Bitmap[] spframes=new Bitmap[8];
	private Bitmap rope;
	private int width;
	private int height;
	boolean ismove=false;
	private List<ImageView> views;
	private Handler handler;
	private Runnable runnable;
	private static final String TAG = "StoleItTag";
	
	ImageView v1,v2,v3;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        		WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setContentView(R.layout.main);
		
		views = new ArrayList<ImageView>();
		//lsp = (LinearLayout) findViewById(R.id.mylinear_sp);
		lrope = (LinearLayout) findViewById(R.id.mylinear_rope);
		
		rope = BitmapFactory.decodeResource(getResources(), R.drawable.rope);
		black = BitmapFactory.decodeResource(getResources(), R.drawable.black4);
		width = black.getWidth();
		height = black.getHeight();
		
		for(int i=0;i<8;i++){
			spframes[i] = Bitmap.createBitmap(black, 0+width*i/8, 0, width/8, height);
		}
		
		msp = new MySprite(getApplicationContext(), spframes[3]);
		msp.setPosition(50, 190);
		lrope.addView(msp);				
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.stole_it_model, menu);
		return true;
	}
	
	public boolean onTouchEvent(MotionEvent e){			
		int x = (int)e.getX();
		int y = (int)e.getY();
		int action = e.getAction();						
		switch(action){
		case MotionEvent.ACTION_DOWN:
			//msp.move((int)x-5,(int)y-165);
			//Toast.makeText(getApplicationContext(),x+" ", Toast.LENGTH_SHORT).show();
			int c=views.size();
			if(c>0){
				for(int i=0;i<c;i++)
					lrope.removeView(views.get(i));
				views = new ArrayList<ImageView>();
			}else{
				if(x<200&&y>150){
					msp.move((int)x-5,(int)y-40);
				}
			}
			break;
		case MotionEvent.ACTION_MOVE:
			ismove=true;
			//Toast.makeText(getApplicationContext(),e.getX()+" ", Toast.LENGTH_SHORT).show();
			break;
		case MotionEvent.ACTION_UP:
			if(ismove){
				//Toast.makeText(getApplicationContext(), "Throwing", Toast.LENGTH_SHORT).show();						
				double atan = Math.atan2((msp.getPosition()[1]-y),(x-msp.getPosition()[0]));				
				int sita = (int)(atan/Math.PI*180);
				
				throwRopes(0, msp.getPosition()[1]+15, sita);
				//double sita = Math.atan2((msp.getPosition()[1]-y+150),(x-msp.getPosition()[0]));
				//int de = (int)Math.toDegrees(sita);
				//int de = (int)(sita/Math.PI*180);
				//Toast.makeText(getApplicationContext(),de+"", Toast.LENGTH_SHORT).show();
			}
			ismove=false;
			break;
		}
		
		return true;
	}	
	
	public void throwRopes(int sx,int sy,int sa){
		List<myPictures> bits = new ArrayList<myPictures>();
		Matrix matrix = new Matrix();
		int wid = rope.getWidth();
		int sita = sa;
		int x=sx;
		int y=sy;
		
		while(sita>=0){
			//Log.i(TAG, y+"");
			//Log.i(TAG, sita+"");
			myPictures mp = new myPictures(
					x+wid*(int)Math.cos(sita),(int)(y-wid*Math.sin(sita*Math.PI/180)),sita);
			x=x+wid*(int)Math.cos(sita);
			y=(int)(y-wid*Math.sin(sita*Math.PI/180));
			//Log.i(TAG, Math.sin(sita*Math.PI/180)+"");
			bits.add(mp);
			sita -= 3;
		}
		myPictures mpt = new myPictures(
				x+wid*(int)Math.cos(0),(int)(y-wid*Math.sin(0)),0);
		x=x+wid*(int)Math.cos(0);
		y=(int)(y-wid*Math.sin(0));
		bits.add(mpt);
		
		while(sita>=-sa){
			myPictures mp = new myPictures(
					x+wid*(int)Math.cos(-sita),y+(int)(wid*Math.sin(-sita*Math.PI/180)),sita);
			x=x+wid*(int)Math.cos(-sita);
			y=y+(int)(wid*Math.sin(-sita*Math.PI/180));
			bits.add(mp);
			sita -= 3;
		}
				
		for(myPictures mp:bits){
			ImageView iv = new ImageView(this);
			iv.setLayoutParams(new android.widget.LinearLayout.LayoutParams(
				android.widget.LinearLayout.LayoutParams.WRAP_CONTENT,
				android.widget.LinearLayout.LayoutParams.WRAP_CONTENT ));
			matrix.setRotate(-mp.pangle);
			Bitmap temp = Bitmap.createBitmap(rope,0,0,rope.getWidth(),rope.getHeight(),matrix,true);			
			iv.setImageBitmap(temp);
			iv.setPadding(0, mp.py, 0, 0);
			views.add(iv);					
		}						
		for(int i=0;i<views.size();i++)
			lrope.addView(views.get(i));
				
	}
	
	private class myPictures{
		private int px;
		private int py;
		private int pangle;
		
		public myPictures(int x,int y,int angle){
			this.px = x;
			this.py = y;
			this.pangle = angle;
		}
	}
}
