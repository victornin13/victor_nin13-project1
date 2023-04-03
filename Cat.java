import java.util.Random;

public class Cat extends Creature {
    
    protected int dir;
    protected int x;
    protected int y;
    protected int round;
    private Creature mice;
    

    public Cat(int x, int y, City cty, Random rnd){
        super(x, y, cty, rnd);
        this.mice = null;
        this.lab = LAB_YELLOW;
        this.round = 0;
        this.stepLen=2;
    }

    public void step()
    {

        if(rand.nextInt(100) <= 5){
            dir = DIRS[rand.nextInt(DIRS.length)];
        randomTurn();
        }

        int dc = stepLen;
        
        switch(dir){
            case EAST: //East
            this.point.x += dc;
            break;
            case SOUTH: //South
            this.point.y += dc;
            break;
            case WEST: //West
            this.point.x -= dc;
            break;
            case NORTH: //North
            this.point.y -= dc;
            break;
            default:
            break;
        }

    }
    
    

    public void randomChangeDirection(){
        if(rand.nextInt(100) <= 5);
        randomTurn();
    }

    //Searches for the mice
    public void search(){
        
        if (mice != null) {
            int dx = mice.getX() - getX();
            int dy = mice.getY() - getY();
            if (Math.abs(dx) > Math.abs(dy)) {
                if (dx > 0) {
                    setDir(EAST);
                    if (dx <= 20) {
                        this.point.x += dx;
                    } else {
                        this.point.x += stepLen;
                    }
                } else {
                    setDir(WEST);
                    if (Math.abs(dx) <= 20) {
                        this.point.x -= Math.abs(dx);
                    } else {
                        this.point.x -= stepLen;
                    }
                }
            } else {
                if (dy > 0) {
                    setDir(SOUTH);
                    if (dy <= 20) {
                        this.point.y += dy;
                    } else {
                        this.point.y += stepLen;
                    }
                } else {
                    setDir(NORTH);
                    if (Math.abs(dy) <= 20) {
                        this.point.y -= Math.abs(dy);
                    } else {
                        this.point.y -= stepLen;
                    }
                }
            }
        }
    }
    


    public void takeAction()
    {

        if ((lab == LAB_YELLOW)){
            randomChangeDirection();
        }
        if (mice != null){
            search();
        }

        if(round == 25){
            city.creaturesToAdd.add(new Cat(getX(), getY(), city, rand));
        }
        
        for (Creature i: city.creatures){
            //Searches for mice within 20 gridpoints
            if((mice == null) && (dist(i) <= 20)){
                if ( i instanceof Mouse){
                    lab = LAB_CYAN;
                    mice = i;
    
                }
            }
            if ((mice != null) && (dist(i) < dist(mice))){
                if ( i instanceof Mouse){
                    mice = i;
                }
            }
        }
        if ((mice != null)){
            if (((mice.dead == true) || (dist(mice) > 20))){
                lab = LAB_YELLOW;
                mice = null;
    
            }
        }
    
    
    if ((mice != null) && (dist(mice) == 0) ){
        mice.dead = true; 
        mice = null;
        lab = LAB_YELLOW;
        
    }
    
    if (round == 50){
        city.creaturesToAdd.add(new ZombieCat(getX(), getY(), city, rand));
        dead = true;
    }
    //step();
    round++;
}

}

