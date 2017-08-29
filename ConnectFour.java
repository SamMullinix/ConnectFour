import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Created by Sam on 10/25/16.
 *
 *   Sam Mullinix
 *   Professor Kilinc
 *   CS1302 Section 02
 *   29 November 2016
 *
 *   Project 2 & 3
 */

public class ConnectFour extends Application{

    private Players player1 = new Players(0);
    private Players player2 = new Players(0);
    private int lane1 = 4;
    private int lane2 = 4;
    private int lane3 = 4;
    private int lane4 = 4;
    private int lane5 = 4;
    private int alternateColor = 0;
    private int count1 = 0;
    private int count2 = 0;
    private int count3 = 0;
    private int count4 = 0;   // Variables, objects, and GUI nodes
    private int count5 = 0;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Circle[][] array = new Circle[5][5];
    private Text winText = new Text();
    private Text player1Score = new Text();
    private Text player2Score = new Text();

    public void start(Stage primaryStage)
    {
        BorderPane borderPane = new BorderPane(); // Border Pane for main pane to hold items.
        GridPane gridPane = new GridPane();       // Grid Pane to act as the Board
        HBox hBox = new HBox();                   // Two Hboxes above and below for resetting/exiting and displaying score
        HBox hBox2 = new HBox();
        
        // Set alignment and padding for all the items
        gridPane.setAlignment(Pos.CENTER);
        borderPane.setPadding(new Insets(80));
        borderPane.setCenter(gridPane);
        borderPane.setBottom(hBox);
        borderPane.setTop(hBox2);
        hBox.setAlignment(Pos.BOTTOM_CENTER);
        hBox.setSpacing(50);
        hBox.setPadding(new Insets(30));
        hBox2.setAlignment(Pos.TOP_CENTER);
        hBox2.setSpacing(75);
        hBox2.setPadding(new Insets(30));

        // Create a 5x5 Gridpane with restraints for the connect four board.
        for (int i = 0; i < 5; i++) {
            ColumnConstraints columns = new ColumnConstraints(75);
            gridPane.getColumnConstraints().add(columns);
        }

        for (int i = 0; i < 5; i++) {
            RowConstraints rows = new RowConstraints(75);
            gridPane.getRowConstraints().add(rows);
        }
        
        
        // Initialize all the buttons texts and text labels.
        winText.setText("No Winner Yet!");
        player1Score.setText("Player 1 Score: " + Integer.toString(player1.getScore()));
        player2Score.setText("Player 2 Score: " + Integer.toString(player2.getScore()));
        player1Score.setStyle("-fx-stroke: black");
        player1Score.setStyle("-fx-stroke-width: 5px");
        player1Score.setFill(Color.RED);
        player2Score.setFill(Color.GOLDENROD);

        Button reset;
        Button exit;  // Local Button variables.

        button1 = new Button("1");
        button2 = new Button("2");
        button3 = new Button("3");
        button4 = new Button("4");
        button5 = new Button("5");
        reset = new Button("New Game");
        exit = new Button("Exit");
        
        // Align the buttons and add them and the hboxs to the panes.
        gridPane.setHalignment(button1, HPos.CENTER);
        gridPane.setHalignment(button2, HPos.CENTER);
        gridPane.setHalignment(button3, HPos.CENTER);
        gridPane.setHalignment(button4, HPos.CENTER);
        gridPane.setHalignment(button5, HPos.CENTER);
        gridPane.setHalignment(winText, HPos.CENTER);
        gridPane.setHalignment(reset, HPos.RIGHT);
        gridPane.setHalignment(exit,HPos.LEFT);

        gridPane.add(button1,0, 6);
        gridPane.add(button2,1, 6);
        gridPane.add(button3,2, 6);
        gridPane.add(button4,3, 6);
        gridPane.add(button5,4, 6);
        hBox.getChildren().add(exit);
        hBox.getChildren().add(winText);
        hBox.getChildren().add(reset);
        hBox2.getChildren().add(player1Score);
        hBox2.getChildren().add(player2Score);

        // Fill the grid with white circles for background.
        for(int i = 0; i < 5; i++)
        {
            for(int j = 0; j < 5; j++)
            {
                Circle circle = new Circle(35, Color.WHITE);
                array[i][j] = circle;
                gridPane.add(circle,i,j);
                gridPane.setHalignment(circle, HPos.CENTER);
            }
        }
        
        // Exit Program if exit button is pressed.
        exit.setOnAction(event -> {
            System.exit(0);
        });

        // Reset all the variables and reset gridpane to blank circles if reset button pressed.
        reset.setOnAction(event -> {
            for(int i = 0; i < 5; i++)
            {
                for(int j = 0; j < 5; j++)
                {
                    Circle circle = new Circle(35, Color.WHITE);
                    array[i][j] = circle;
                    gridPane.add(circle,i,j);
                    gridPane.setHalignment(circle, HPos.CENTER);
                }
            }
            button1.setDisable(false);
            button2.setDisable(false);
            button3.setDisable(false);
            button4.setDisable(false);
            button5.setDisable(false);
            lane1 = 4;
            lane2 = 4;
            lane3 = 4;
            lane4 = 4;
            lane5 = 4;
            alternateColor = 0;
            count1 = 0;
            count2 = 0;
            count3 = 0;
            count4 = 0;
            count5 = 0;
            winText.setFill(Color.BLACK);
            winText.setText("No Winner Yet!");
        });
        
        button1.setOnAction(event -> {
            count1++;
            if (count1 > 5) // After 5 presses disable button.
            {
                button1.setDisable(true);
            }
            else {
                Circle circle = new Circle(35, Color.RED);
                if (alternateColor == 0) {
                    circle.setFill(Color.YELLOW);   // Alternate between red and yellow colored circles.
                    alternateColor = 1;
                } else {
                    alternateColor = 0;
                }
                array[lane1][0] = circle;   // Add the circles to the array and to the gridpane.
                gridPane.add(circle, 0, lane1);
                gridPane.setHalignment(circle, HPos.CENTER);
                lane1--;
                isRedWin(array);    // Check for red wins.
                isYellowWin(array); // Check for yellow wins.
            }}
        );
        
        // The above comments can be applied to the 5 following buttons, they all do the same thing
        // Except in a different lane.
        button2.setOnAction(event -> {
            count2++;
            if (count2 > 5)
            {
                button2.setDisable(true);
            }
            else {
                Circle circle = new Circle(35, Color.RED);
                if (alternateColor == 0) {
                    circle.setFill(Color.YELLOW);
                    alternateColor = 3;
                } else {
                    alternateColor = 0;
                }
                array[lane2][1] = circle;
                gridPane.add(circle, 1, lane2);
                gridPane.setHalignment(circle, HPos.CENTER);
                lane2--;
                isRedWin(array);
                isYellowWin(array);
            }
        });
        button3.setOnAction(event -> {
            count3++;
            if (count3 > 5)
            {
                button3.setDisable(true);
            }
            else {
                Circle circle = new Circle(35, Color.RED);
                if (alternateColor == 0) {
                    circle.setFill(Color.YELLOW);
                    alternateColor = 1;
                } else {
                    alternateColor = 0;
                }
                array[lane3][2] = circle;
                gridPane.add(circle, 2, lane3);
                gridPane.setHalignment(circle, HPos.CENTER);
                lane3--;
                isRedWin(array);
                isYellowWin(array);
            }
        });
        button4.setOnAction(event -> {
            count4++;
            if (count4 > 5)
            {
                button4.setDisable(true);
            }
            else {
                Circle circle = new Circle(35, Color.RED);
                if (alternateColor == 0) {
                    circle.setFill(Color.YELLOW);
                    alternateColor = 1;
                } else {
                    alternateColor = 0;
                }
                array[lane4][3] = circle;
                gridPane.add(circle, 3, lane4);
                gridPane.setHalignment(circle, HPos.CENTER);
                lane4--;
                isRedWin(array);
                isYellowWin(array);
            }
        });
        button5.setOnAction(event -> {
            count5++;
            if (count5 > 5)
            {
                button5.setDisable(true);
            }
            else {
                Circle circle = new Circle(35, Color.RED);
                if (alternateColor == 0) {
                    circle.setFill(Color.YELLOW);
                    alternateColor = 1;
                } else {
                    alternateColor = 0;
                }
                array[lane5][4] = circle;
                gridPane.add(circle, 4, lane5);
                gridPane.setHalignment(circle, HPos.CENTER);
                lane5--;
                isRedWin(array);
                isYellowWin(array);
            }

        });

        // Set colors, add panes to scene and scene to stage, and show the stage.
        gridPane.setStyle("-fx-background-color: darkblue");
        Scene scene = new Scene(borderPane);
        primaryStage.setTitle("Connect Four Game");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private void isRedWin(Circle[][] array)
    {
        int count;

        //Check vertical for red win
        for(int i = 0; i < 5; i++)
        {
            count = 0;
            Circle circle;
            for(int j = 0; j < 5; j++)
            {
                circle = array[j][i];
                if(circle.getFill() == Color.RED)
                {
                    count++;
                }
                else if(count < 4)
                {
                    count = 0;
                }
            }
            if(count >= 4) {
                redWinActions();
            }
        }
        // Check for horizontal red win
        for(int i = 0; i < 5; i++)
        {
            count = 0;
            Circle circle;
            for(int j = 0; j < 5; j++)
            {
                circle = array[i][j];
                if(circle.getFill() == Color.RED)
                {
                    count++;
                }
                else if(count < 4)
                {
                    count = 0;
                }
            }
            if(count >= 4) {
                redWinActions();
            }
        }

        for(int i = 0; i < 2; i++) // Check diagonal top left to bottom right
        {
            Circle circle;
            Circle circle2;
            Circle circle3;
            Circle circle4;

            for(int j = 0; j < 2; j++)
            {
                circle = array[i][j];
                circle2 = array[i+ 1][j + 1];
                circle3 = array[i+ 2][j + 2];
                circle4 = array[i+ 3][j + 3];

                if(circle.getFill() == Color.RED
                        && circle2.getFill() == Color.RED
                        && circle3.getFill() == Color.RED
                        && circle4.getFill() == Color.RED)
                {
                    redWinActions();
                }
            }
        }

        for(int i = 0; i < 2; i++) // Check diagonal top right to bottom left
    {
        Circle circle;
        Circle circle2;
        Circle circle3;
        Circle circle4;

        for(int j = 4; j > 2; j--)
        {
            circle = array[i][j];
            circle2 = array[i+1][j - 1];
            circle3 = array[i+ 2][j - 2];
            circle4 = array[i+ 3][j - 3];

            if(circle.getFill() == Color.RED
                    && circle2.getFill() == Color.RED
                    && circle3.getFill() == Color.RED
                    && circle4.getFill() == Color.RED)
            {
                redWinActions();
            }
        }
    }
    }


    private void isYellowWin(Circle[][] array)
    {
        int count;

        //Check vertical for yellow win

        for(int i = 0; i < 5; i++)
        {
            count = 0;
            Circle circle;
            for(int j = 0; j < 5; j++)
            {
                circle = array[j][i];
                if(circle.getFill() == Color.YELLOW)
                {
                    count++;
                }
                else if(count < 4)
                {
                    count = 0;
                }
            }
            if(count >= 4) {
                yellowWinActions();
            }
        }
        // Check for horizontal yellow win
        for(int i = 0; i < 5; i++)
        {
            count = 0;
            Circle circle;
            for(int j = 0; j < 5; j++)
            {
                circle = array[i][j];
                if(circle.getFill() == Color.YELLOW)
                {
                    count++;
                }
                else if(count < 4)
                {
                    count = 0;
                }

            }
            if(count >= 4) {
                yellowWinActions();
            }
        }
        for(int i = 0; i < 2; i++) // Check diagonal top left to bottom right
        {
            Circle circle;
            Circle circle2;
            Circle circle3;
            Circle circle4;

            for(int j = 0; j < 2; j++)
            {
                circle = array[i][j];
                circle2 = array[i+ 1][j + 1];
                circle3 = array[i+ 2][j + 2];
                circle4 = array[i+ 3][j + 3];

                if(circle.getFill() == Color.YELLOW
                        && circle2.getFill() == Color.YELLOW
                        && circle3.getFill() == Color.YELLOW
                        && circle4.getFill() == Color.YELLOW)
                {
                    yellowWinActions();
                }
            }
        }
        for(int i = 0; i < 2; i++) // Check diagonal top right to bottom left
        {
            Circle circle;
            Circle circle2;
            Circle circle3;
            Circle circle4;

            for(int j = 4; j > 2; j--)
            {
                circle = array[i][j];
                circle2 = array[i+1][j - 1];
                circle3 = array[i+ 2][j - 2];
                circle4 = array[i+ 3][j - 3];

                if(circle.getFill() == Color.YELLOW
                        && circle2.getFill() == Color.YELLOW
                        && circle3.getFill() == Color.YELLOW
                        && circle4.getFill() == Color.YELLOW)
                {
                    yellowWinActions();
                }
            }
        }

    }

    private void redWinActions()
    {
        button1.setDisable(true);
        button2.setDisable(true);
        button3.setDisable(true);
        button4.setDisable(true);
        button5.setDisable(true);
        winText.setText("     Red Wins!   ");
        winText.setFill(Color.RED);
        player1.setScore(player1.getScore() + 1);
        player1Score.setText("Player 1 Score: " + Integer.toString(player1.getScore()));
    }

    private void yellowWinActions()
    {
        button1.setDisable(true);
        button2.setDisable(true);
        button3.setDisable(true);
        button4.setDisable(true);
        button5.setDisable(true);
        winText.setText("  Yellow Wins!  ");
        winText.setFill(Color.GOLDENROD);
        player2.setScore(player2.getScore() + 1);
        player2Score.setText("Player 2 Score: " + Integer.toString(player2.getScore()));
    }

    public static void main(String[] args)
    {
        Application.launch(args);
    }
}
