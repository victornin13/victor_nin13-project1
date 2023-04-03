import java.util.Random;

public class ZombieCat extends Cat {

    protected int dir;
    protected int x;
    protected int y;
    protected int round;
    protected int area;
    protected Creature mice;
    protected Creature cats;

    public ZombieCat(int x, int y, City cty, Random rnd){
        super(x, y, cty, rnd);
        this.lab = LAB_RED;
        this.round=0;
        this.area=40;
        this.stepLen=3;
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
//Searches for both Cats and Mice
    public void search(){
        
        int distToMice = Integer.MAX_VALUE;
        int distToCats = Integer.MAX_VALUE;
        Mouse closestMice = null;
        Cat closestCats = null;
    
        for (Creature i : city.creatures) {
            if (i instanceof ZombieCat) {
                continue;
            }
    
            int dist = dist(i);
            if (i instanceof Mouse && dist < distToMice) {
                distToMice = dist;
                closestMice = (Mouse) i;
            } else if (i instanceof Cat && dist < distToCats) {
                distToCats = dist;
                closestCats = (Cat) i;
            }
        }
    
        if (closestMice != null || closestCats != null) {
            Creature target = closestMice != null ? closestMice : closestCats;
            int dx = target.getX() - getX();
            int dy = target.getY() - getY();
            if (Math.abs(dx) > Math.abs(dy)) {
                if (dx > 0) {
                    setDir(EAST);
                    if (dx <= 40) {
                        this.point.x += dx;
                    } else {
                        this.point.x += stepLen;
                    }
                } else {
                    setDir(WEST);
                    if (Math.abs(dx) <= 40) {
                        this.point.x -= Math.abs(dx);
                    } else {
                        this.point.x -= stepLen;
                    }
                }
            } else {
                if (dy > 0) {
                    setDir(SOUTH);
                    if (dy <= 40) {
                        this.point.y += dy;
                    } else {
                        this.point.y += stepLen;
                    }
                } else {
                    setDir(NORTH);
                    if (Math.abs(dy) <= 40) {
                        this.point.y -= Math.abs(dy);
                    } else {
                        this.point.y -= stepLen;
                    }
                }
            }
        }
    }

    //Looks to chase whats closer between a cat or mouse
    private void chase(Creature target) {
    if (mice != null && !mice.dead && dist(mice) <= area) {
        target = mice;
    } else if (cats != null && !cats.dead && dist(cats) <= area) {
        target = cats;
    }
    
    if (target != null) {
        int dx = target.getX() - getX();
        int dy = target.getY() - getY();
        
        if (Math.abs(dx) > Math.abs(dy)) {
            if (dx > 0) {
                setDir(EAST);
                if (dx <= 40) {
                    this.point.x += dx;
                } else {
                    this.point.x += stepLen;
                }
            } else {
                setDir(WEST);
                if (Math.abs(dx) <= 40) {
                    this.point.x -= Math.abs(dx);
                } else {
                    this.point.x -= stepLen;
                }
            }
        } else {
            if (dy > 0) {
                setDir(SOUTH);
                if (dy <= 40) {
                    this.point.y += dy;
                } else {
                    this.point.y += stepLen;
                }
            } else {
                setDir(NORTH);
                if (Math.abs(dy) <= 40) {
                    this.point.y -= Math.abs(dy);
                } else {
                    this.point.y -= stepLen;
                }
            }
        }
    } else {
        search();
    }
} 


  public void takeAction()
    {

        if (mice == null || cats == null) {
            for (Creature i : city.creatures) {
                if (mice == null && i instanceof Mouse && dist(i) <= area) {
                    lab = LAB_BLACK;
                    mice = (Mouse) i;
                } else if (cats == null && i instanceof Cat && dist(i) <= area) {
                    lab = LAB_BLACK;
                    cats = (Cat) i;
                }
            }
        } else {
            //checks which creature is closer and chases it
            if (!mice.dead && !cats.dead) {
                if (dist(mice) <= area && dist(mice) < dist(cats)) {
                    chase(mice);
                } else if (dist(cats) <= area && dist(cats) < dist(mice)) {
                    chase(cats);
                } else {
                    // If neither is close enough, search for them
                    search();
                }
            } else {
                // If either mice or cats is dead, reset their values and search for new ones
                mice = null;
                cats = null;
                lab = LAB_RED;
                search();
            }
        }
        
        round++;
        // If the zombie cat has been alive for 100 rounds, it dies
        if (round == 100) {
            dead = true;
        }
    }

       
}