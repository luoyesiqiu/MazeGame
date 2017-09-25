package com.woc.dfsmaze;
import java.util.*;
import android.graphics.*;

/*
*迷宫类
*/
public  class Maze
{

	public static final int WALL = 0;//墙
	public static final int SPACE = 1;//空
	public static final int VISITED=2;//标记已访问过
	public static final int MOVE=3;//迷宫中可以动的物体
	public static final int EMPTY=4;
	private byte[][] data;//数据
	public int width;//迷宫宽
	public int height;//迷宫高
	private Random rand = new Random();
	public int mx,my;
	final int[] upx = { 1, -1, 0, 0 };//分别代表了左右上下4个方向
	final int[] upy = { 0, 0, 1, -1 };
	public Maze(int mx,int my,int width, int height)
	{
		this.width = width;
		this.height = height;
		data = new byte[width][];
		this.mx=mx;
		this.my=my;
	}

	/**
	 *打洞
	 *从入口打洞，随机选择一个方向，如果那个方向前面两格都是墙，则打通
	 *这里也是用了深度优先搜索算法
	 *
	 */
	private void carve(int x, int y)
	{
		int dir = rand.nextInt(4);//0~3
		int count = 0;
		while (count < 4)
		{
			final int x1 = x + upx[dir];
			final int y1 = y + upy[dir];
			final int x2 = x1 + upx[dir];//加强，与x1同个方向
			final int y2 = y1 + upy[dir];//加强，与y1同一个方向
			if (data[x1][y1] == WALL && data[x2][y2] == WALL)//如果为都为墙。表示打中空格
			{
				data[x1][y1] = SPACE;
				data[x2][y2] = SPACE;
				carve(x2, y2);//条件符合，开挖
			}
			else
			{
				dir = (dir + 1) % 4;//产生一个不同与dir的，又在0~3范围内的数(即产生下一个方向)
				count += 1;
			}
		}
	}

	/**
	 *生成
	 *没什么好说的
	 */
	public byte[][] generate()
	{
		//都填为墙
		for (int x = 0; x < width; x++)
		{
			data[x] = new byte[height];
			for (int y = 0; y < height; y++)
			{
				data[x][y] = WALL;
			}
		}
		//第一行和最后一行清空
		for (int x = 0; x < width; x++)
		{
			data[x][0] = EMPTY;
			data[x][height - 1] = EMPTY;
		}
		//第一列和最后列填充为空
		for (int y = 0; y < height; y++)
		{
			data[0][y] = EMPTY;
			data[width - 1][y] = EMPTY;
		}

		//入口下来那一格打空
		data[2][2] = SPACE;
		//开始打洞
		carve(2, 2);

		//开入口
		data[2][1] = SPACE;
		//开出口
		data[width - 3][height - 2] = SPACE;
		
		return data;
	}

	/**
	 *画迷宫
	 *Paint传进来就可以自定义方块颜色
	 */
	public void draw(Canvas canvas,Paint paint)
	{
		int x1=mx,y1=my;
		final int rectW=20,rectH=20;
		for (int y = 0; y < height; y++)
		{
			for (int x = 0; x < width; x++)
			{
				if (data[x][y] == WALL)
				{
					canvas.drawRect(x1,y1,x1+rectW,y1+rectH,paint);
					x1=x1+rectW;
				}
				else
				{
					//如果是空白，直接不画，右移一格就行
					x1=x1+rectW;
					
				}
			}
			y1=y1+rectH;//下一行
			x1=mx;//定位到一行开始
		}
	}


}

	
