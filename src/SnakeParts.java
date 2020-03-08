import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SnakeParts extends ImageView {

    public SnakeParts(int x, int y) {
        this.setLayoutX(x);
        this.setLayoutY(y);
        this.setImage(new Image("images/snakepart.png"));
        this.setFitHeight(13);
        this.setFitWidth(13);
    }

}
