package game;
import java.util.ArrayList;

public interface Movable {
    /**
     * @version 1.0
     * @author Wesley, Michael, Shacor
     */

    /**
     * Update the movable object
     * @param objects The list of drawable objects
     */
    public void update(ArrayList<Drawable> objects);
}
