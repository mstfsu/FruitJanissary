package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Pomegranate extends Fruit {
    public Pomegranate(Image image, ImageView ımageView) {
        super(image, ımageView);
    }

    @Override
    public boolean slice(ImageView ımageView, Bomb bomb) {
        return false;
    }
}
