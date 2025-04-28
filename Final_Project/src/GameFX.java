import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GameFX extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Text Adventure Game");

        Label storyLabel = new Label("Welcome to the Adventure Game!");
        TextField inputField = new TextField();
        Button submitButton = new Button("Submit");

        VBox layout = new VBox(10);
        layout.getChildren().addAll(storyLabel, inputField, submitButton);

        Scene scene = new Scene(layout, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Example: When the user clicks the button
        submitButton.setOnAction(e -> {
            String userInput = inputField.getText();
            storyLabel.setText("You entered: " + userInput);
            inputField.clear();
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
