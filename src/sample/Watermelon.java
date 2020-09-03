package sample;

import javafx.animation.PathTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Watermelon extends Fruit {
    @Override
    public boolean slice(ImageView ımageView, Bomb bomb) {
        return false;
    }

    public Watermelon(Image image, ImageView ımageView) {
        super(image, ımageView);
    }
}
