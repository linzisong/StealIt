package com.example.service;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

public class MySprite extends ImageView {

	private Bitmap sp;
	private int spx;
	private int spy;
	private int spw;
	private int sph;
	private LayoutParams lp;
	
	public MySprite(Context context) {
		super(context);
	}
	
	public MySprite(Context context,Bitmap bit){
		super(context);
		this.sp = bit;
		super.setImageBitmap(sp);
		this.spw = bit.getWidth();
		this.sph = bit.getHeight();	
		setLp();
	}
	
	public void setPosition(int px,int py){
		this.spx = px;
		this.spy = py;	
		super.setPadding(spx, spy, 0, 0);
	}
	
	public void setLp(){
		super.setLayoutParams(new android.widget.LinearLayout.LayoutParams(
				android.widget.LinearLayout.LayoutParams.WRAP_CONTENT,
				android.widget.LinearLayout.LayoutParams.WRAP_CONTENT ));
	}
	
	public int[] getPosition(){
		int[] po = new int[2];
		po[0] = this.spx;
		po[1] = this.spy;
		return po;
	}
	
	public void move(int x,int y){		
		this.spx = x;
		this.spy = y;
		setLp();
		super.setPadding(spx, spy, 0, 0);
	}
}
