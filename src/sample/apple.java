package sample;

import javafx.animation.PathTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

public class apple extends Fruit {

    @Override
    public boolean slice(ImageView ımageView, Bomb bomb) {
        return false;
    }

    public apple(Image image, ImageView ımageView) {
        super(image, ımageView);
    }
}
