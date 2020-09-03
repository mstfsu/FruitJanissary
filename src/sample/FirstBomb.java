package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class FirstBomb extends Bomb {
    public FirstBomb(Image image, ImageView ımageView) {
        super(image, ımageView);
    }

    @Override
    public boolean slice(ImageView ımageView, Fruit fruit) {
        return false;
    }
}
