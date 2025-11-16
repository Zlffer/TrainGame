
package actions;

import assets.Train;
import ui.GameMenu;
import ui.TrainBuilderMenu;
import java.util.Scanner;

public class MMCreateTrainAction implements Action {

    private Scanner scanner;

    public MMCreateTrainAction(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public void execute() {

        TrainBuilderMenu builder = new TrainBuilderMenu(this.scanner);

        Train newTrain = builder.buildTrain();

        if (newTrain != null) {
            GameMenu gameMenu = new GameMenu(this.scanner, newTrain);
            gameMenu.run();
        }
    }

}
