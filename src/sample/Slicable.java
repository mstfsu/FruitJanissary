package sample;

import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public interface Slicable {

    public boolean slice(ImageView ımageView, Fruit fruit);
    public boolean slice(ImageView ımageView, Bomb bomb);
}
