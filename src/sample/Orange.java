package sample;

import javafx.animation.PathTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Orange extends Fruit{

    public Orange(Image image, ImageView ımageView) {
        super(image, ımageView);
    }

    @Override
    public boolean slice(ImageView ımageView, Bomb bomb) {
        return false;
    }
}
