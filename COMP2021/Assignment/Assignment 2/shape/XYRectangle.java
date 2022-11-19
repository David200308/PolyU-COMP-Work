package hk.edu.polyu.comp.comp2021.assignment2.shape;

public class XYRectangle {
    private Point topLeft;
    private Point bottomRight;

    public Point getTopLeft() {
        return topLeft;
    }

    public Point getBottomRight() {
        return bottomRight;
    }

    public XYRectangle(Point p1, Point p2){
        topLeft = p1;
        bottomRight = p2;

    }

    public String toString(){
        return "<" + topLeft.toString() + "," + bottomRight.toString() + ">";
    }

    public int area(){
        int h = topLeft.getY() - bottomRight.getY();
        int w = bottomRight.getX() - topLeft.getX();
        return h * w;
    }

    public void rotateClockwise(){
        int deltaX = topLeft.getY() - bottomRight.getY();
        int deltaY = bottomRight.getX() - topLeft.getX();
        int temp_rotateClockwise = this.topLeft.getX();
        this.topLeft.set(topLeft.getX() - deltaX, topLeft.getY());
        this.bottomRight.set(temp_rotateClockwise, topLeft.getY() - deltaY);
    }

    public void move(int deltaX, int deltaY){
        this.topLeft.set(this.topLeft.getX() + deltaX, this.topLeft.getY() + deltaY);
        this.bottomRight.set(this.bottomRight.getX() + deltaX, this.bottomRight.getY() + deltaY);
    }

    public boolean contains(Point p){
        if (p.getX() >= topLeft.getX() && p.getX() <= bottomRight.getX() && p.getY() <= topLeft.getY() && p.getY() >= bottomRight.getY()) {
            return true;
        }
        return false;
    }

    public boolean contains(XYRectangle r){
        if (r.topLeft.getX() >= this.topLeft.getX() && r.topLeft.getY() <= this.topLeft.getY() && r.bottomRight.getX() <= this.bottomRight.getX() && r.bottomRight.getY() >= this.bottomRight.getY()) {
            return true;
        }
        return false;
    }

    public boolean overlapsWith(XYRectangle r){
        if (r.topLeft.getX() > this.bottomRight.getX() && r.topLeft.getY() < this.bottomRight.getY() || r.bottomRight.getX() < this.topLeft.getX() || r.topLeft.getY() < this.bottomRight.getY()) {
            return false;
        }
        return true;
    }
}

class Point{
    private int x;
    private int y;

    public Point(int x, int y) {
        set(x, y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void set(int x, int y){
        this.x = x;
        this.y = y;
    }

    public String toString(){
        return "(" + getX() + "," + getY() + ")";
    }
}

