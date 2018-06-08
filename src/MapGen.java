import java.awt.*;

public class MapGen {
    private int map[][];
    private int brickWidth;
    private int brickHeight;

    public int[][] getMap(){
        return this.map;
    }

    public int getBrickWidth(){
        return this.brickWidth;
    }

    public int getBrickHeight(){
        return this.brickHeight;
    }

    public MapGen(int r, int c){
        this.map = new int [r][c];
        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[0].length; j++){
                map[i][j] = 1;      // this shows that there is a brick, could do true or false
            }
        }
        this.brickWidth = 540 / c;
        this.brickHeight = 150/r;
    }

    public void draw(Graphics2D g){
        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[0].length; j++){
               if(map[i][j] > 0){
                   g.setColor(Color.RED);
                   g.fillRect(j*brickWidth + 80, i*brickHeight+50, brickWidth, brickHeight);
                   g.setStroke(new BasicStroke(3));
                   g.setColor(Color.BLACK);
                   g.drawRect(j*brickWidth + 80, i*brickHeight+50, brickWidth, brickHeight);
               }
            }
        }

    }

    public void setBrickValue(int val, int row, int col){
        map[row][col] = val;
    }
}
