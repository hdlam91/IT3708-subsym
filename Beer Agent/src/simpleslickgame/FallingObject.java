package simpleslickgame;

import java.util.Arrays;

public class FallingObject {
	private int width, velocity, posX, posY, boardWidth, boardHeight;
	private boolean finishedOnScreen;
	
	public FallingObject(int boardWidth,int startY,int boardHeight,int velocity){
		this.width = (int)(Math.random()*6)+1;
//		this.velocity = (Math.random()>0.5 ? 0:1)*(Math.random()>0.5?-1:1);
		this.velocity = velocity;
		this.posX = (int)(Math.random()*boardWidth);
		this.posY = startY;
		this.boardWidth = boardWidth;
		this.boardHeight = boardHeight;
		
		this.finishedOnScreen = false;
	}
	
	public void iter(){
		posY++;
		posX += 1*velocity;
		if(posY>=boardHeight){
			finishedOnScreen = true;
		}
	}
	
	public boolean contact(int[] agentPosisions){
		int[] objectPos = renderPosition();
		for (int i = 0; i < objectPos.length; i++) {
			for (int j = 0; j < agentPosisions.length; j++) {
				if(objectPos[i]==agentPosisions[j])
					return true;
			}
		}
		return false;
	}
	
	public boolean caught(int[] agentPosisions, int threshold){
		int[] objectPos = renderPosition();
		int count = 0;
		for (int i = 0; i < objectPos.length; i++) {
			for (int j = 0; j < agentPosisions.length; j++) {
				if(objectPos[i]==agentPosisions[j])
					count++;
			}
		}
		if(count>=threshold)
			return true;
		return false;
	}
	
	public int safeX(int x){
		return (x+boardWidth)%boardWidth;
	}
	
	public int[] renderPosition(){
		int [] pos =  new int[width];
		for (int i = 0; i < width; i++) {
			pos[i] = safeX(posX+i);
		}
//		System.out.println(Arrays.toString(pos));
		return (int[]) pos.clone();
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getPosY(){
		return posY;
	}
	
	public boolean getDoneOnScreen(){
		return finishedOnScreen;
	}
	
	public String toString(){
		StringBuffer buf = new StringBuffer();
		buf.append("w:" +width + " x:" + posX + " y:" +posY + " vel:" +velocity + " done:" + finishedOnScreen+"\n");
		return buf.toString();
	}
	
}
