package sample;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

public abstract class Fruit  implements Slicable{

    private Image image;
    @Override
    public boolean slice(ImageView ımageView, Fruit fruit) {
        boolean Kontrol =false;
        if(fruit.getImageView().getBoundsInParent().intersects(ımageView.getBoundsInParent())){
            Kontrol=true;
        }
        return  Kontrol;
    }

    private ImageView ımageView;

    int maxHeight=(int)(Math.random()*300+20);
    int initialX=(int)(Math.random()*300+50);
    int throwingX=(int)(Math.random()*300+50);
    int lastX;
    int hız=(int)(Math.random()*5+2);
    PathTransition pathTransition;
    RotateTransition rt;

    public Fruit(Image image, ImageView ımageView) {
        this.image = image;
        this.ımageView = ımageView;
        if(initialX<throwingX){
            lastX=initialX+(2*(throwingX-initialX));
        }else{
            int fark=(initialX-throwingX)*2;
            lastX=initialX-fark;
        }
        pathTransition=new PathTransition();
        ımageView.setX(initialX);
        ımageView.setY(540);
        rt = new RotateTransition(Duration.millis(3000), ımageView);
        rt.setByAngle(360);
        rt.setCycleCount(Animation.INDEFINITE);
        rt.setInterpolator(Interpolator.LINEAR);
        rt.play();

        Path path = new Path();
        MoveTo moveTo = new MoveTo();
        moveTo.setX(initialX);
        moveTo.setY(540);
        CubicCurveTo cubicTo = new CubicCurveTo();
        cubicTo.setControlX1(throwingX);
        cubicTo.setControlY1(maxHeight);
        cubicTo.setControlX2(throwingX+50);
        cubicTo.setControlY2(maxHeight);
        cubicTo.setX(lastX);
        cubicTo.setY(540.0f);
        path.getElements().add(moveTo);
        path.getElements().add(cubicTo);

        pathTransition.setNode(this.getImageView());
        pathTransition.setPath(path);
        pathTransition.setDuration(Duration.seconds(hız));

        pathTransition.setCycleCount(1);
        pathTransition.setAutoReverse(false);
        pathTransition.play();

    }


    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public ImageView getImageView() {
        return ımageView;
    }

    public void setImageView(ImageView ımageView) {
        this.ımageView = ımageView;
    }
}
