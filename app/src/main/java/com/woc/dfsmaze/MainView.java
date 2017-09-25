package com.woc.dfsmaze;
import android.content.*;
import android.os.Handler;
import android.os.Message;
import android.view.*;
import android.graphics.*;


	/**
	*主视图类
	*落叶似秋编写
	*Q群：233582843
	*2016-3-11
	*/
public class MainView extends View implements Runnable
{

	final  int SLEEP=200;
	Paint wallPaint;//墙画笔
	Paint manPaint;//人画笔
	boolean state=false;//状态
	Maze maze;
	int circleX,circleY;//实时记录人的位置
	byte[][] data;//迷宫数据矩阵
	final int[][] direct={
		{-1,0},//左
		{0,1},//上
		{1,0},//右
		{0,-1}//下
	};//方向，相对于当前位置的偏移
	public MainView(Context context)
	{
		super(context);
		wallPaint =new Paint();
		wallPaint.setColor(Color.BLACK);
		wallPaint.setAntiAlias(true);

		manPaint =new Paint();
		manPaint.setColor(Color.RED);
		manPaint.setAntiAlias(true);

		maze=new Maze(120,300,21,21);//迷宫长和宽都必须为奇数。

		data=maze.generate();//生成迷宫,data不是副本，修改会影响原来的

	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		// TODO: Implement this method
		canvas.drawARGB(255,255,255,255);
		maze.draw(canvas, wallPaint);
		if(state==true)
		{
			drawMan(canvas,circleX,circleY);
		}
	}
	Handler handler=new Handler() {
		@Override
		public void handleMessage(Message msg) {
			invalidate();//刷新画布
		}
	};
	@Override
	public void run()
	{
		// TODO: Implement this method
		//从开始位置搜索
		dfs(2, 1);
	}


	/**
	 * 画人
	 * @param canvas
	 * @param x
	 * @param y
	 */
	private void drawMan(Canvas canvas,int x,int y)
	{
		canvas.drawCircle(x + 10, y + 10, 10, manPaint);
	}


	/**
	 * 深度优先搜索出口
	 * @param x
	 * @param y
	 */
	private void dfs(int x,int y)  {

		//标记为访问过
		data[x][y]=Maze.VISITED;
		//每走到一个点就遍历四个方向
		for(int i=0;i<4;i++)
		{
			int offsetX=direct[i][0];
			int offsetY=direct[i][1];

				if(data[x+offsetX][y+offsetY]==Maze.SPACE)
				{

					try {
						Thread.sleep(SLEEP);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					circleX=(x + offsetX)*20+maze.mx;
					circleY=(y + offsetY)*20+maze.my;
					//通知刷新画布
					handler.sendEmptyMessage(0);
					//深度优先搜索下一位置**************
					dfs(x + offsetX, y + offsetY);
					//回溯--------------------------
					try {
						Thread.sleep(SLEEP);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					circleX=(x + offsetX)*20+maze.mx;
					circleY=(y + offsetY)*20+maze.my;
					//通知刷新画布
					handler.sendEmptyMessage(0);
				}
		}
		//如果到达终点，结束搜索
		if(x==maze.width-3&&y==maze.height-2)
		{
			handler.sendEmptyMessage(0);
			state=false;
			return;
		}
	}

	/**
	 * 屏幕点击事件
	 * @param event
	 * @return
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(event.getAction()==MotionEvent.ACTION_DOWN)
		{

			new Thread(this).start();
			state=true;
		}
		return super.onTouchEvent(event);
	}
}
