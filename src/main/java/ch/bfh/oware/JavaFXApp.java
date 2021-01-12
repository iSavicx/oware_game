
package ch.bfh.oware;


import java.util.Random;

import ch.bfh.oware.model.MyBoardClass;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
 

public class JavaFXApp extends Application implements EventHandler<ActionEvent> {
	
	// needed for eventhandler
	private Stage stage;
	private GridPane sceneOneGrid;
	private GridPane sceneTwoGrid;
	private GridPane sceneThreeGrid;

	private Button submitScene1;
	private Button playAgain;
	private Button exit;

	private TextField returnSize;
	private TextField returnSeeds;
	private ChoiceBox<String> playerMode;

	private HBox boardA;
	private HBox boardB;

	private Label playerB;
	private Label winner;

	private Scene sceneOne;
	private Scene sceneTwo;
	private Scene sceneThree;

	private MyBoardClass fxGameBoard;
	private Button pitA;
	private Button pitB;
	private Button[] arrPitA;
	private Button[] arrPitB;

	private Text strScoreA;
	private Text strScoreB;

	private Boolean pvplayer = false;
	private Boolean pvbot = false;
	/**
	 * Start method called by the JavaFX framework upon calling launch().
	 *
	 * @param stage a (default) stage provided by the framework
	 */
	@Override
	public void start(Stage stage) throws Exception {
		// idee:
		// 3 Scenes
		// 1. Scene: Spiel grösse und anzahl seeds wählen so wie game mode (player vs. player, player vs bot, bot vs bot)
		// 2. Scene: Spiel anzeigen
		// 3. Scene: Sieger screen mit option neues spiel zu starten oder programm zu schliessen
		this.stage = stage;
		//Scene 1 implementation:
		sceneOneGrid = new GridPane();
		// Labels, Textfields, ChoiceBox and Buttons for Scene 1
		Label selectMode = new Label("Select game Mode");
		playerMode = new ChoiceBox<String>(FXCollections.observableArrayList("vs. Player", "vs. Bot"));
		playerMode.setValue("vs. Player");
		Label enterSize = new Label("Number of pits in each row:");
		returnSize = new TextField();
		Label enterSeeds = new Label("Number of seeds per pit:");
		returnSeeds = new TextField();
		submitScene1 = new Button("Ok");
		submitScene1.setOnAction(this::handle);
		


		//Style of the grid pane Scene 1
		final int widthSceneOne = 440;
		final int heightSceneOne = 280;
		sceneOneGrid.setHgap(5);
		sceneOneGrid.setVgap(5);
		sceneOneGrid.setAlignment(Pos.CENTER);
		sceneOneGrid.add(selectMode, 0, 0);
		sceneOneGrid.add(playerMode, 1, 0);
		sceneOneGrid.add(enterSize, 0, 1);
		sceneOneGrid.add(returnSize, 1, 1);
		sceneOneGrid.add(enterSeeds, 0, 2);
		sceneOneGrid.add(returnSeeds, 1, 2);
		sceneOneGrid.add(submitScene1, 1, 3);
		// First colum right alignment
		ColumnConstraints column1 = new ColumnConstraints();
		column1.setHalignment(HPos.RIGHT);
		sceneOneGrid.getColumnConstraints().add(column1);
		//Second column left alignment
		ColumnConstraints column2 = new ColumnConstraints();
		column2.setHalignment(HPos.LEFT);
		sceneOneGrid.getColumnConstraints().add(column2);
		// defining the first sceen
		sceneOne = new Scene(sceneOneGrid, widthSceneOne, heightSceneOne);


		//Scene 2 implementation: 6 Columns (playerA, ScoreA, BoardA, BoardB, ScoreB, playerB)
		sceneTwoGrid = new GridPane();
		sceneTwoGrid.setPadding(new Insets(25));
		sceneTwoGrid.setHgap(10);
		sceneTwoGrid.setVgap(10);
		sceneTwoGrid.setAlignment(Pos.CENTER);
		final int widthSceneTwo = 740;
		final int heightSceneTwo = 480;

		Label playerA = new Label("Player A");
		playerA.setFont(Font.font("Arial", 50));
		Text txtScoreA = new Text("Score:");
		Text txtScoreB = new Text("Score:");
		playerB = new Label("Player B");
		playerB.setFont(Font.font("Arial", 50));

		boardA = new HBox();
		boardB = new HBox();
		HBox scoreA = new HBox();
		HBox scoreB = new HBox();

		strScoreA = new Text("");
		strScoreB = new Text("");

		scoreA.getChildren().addAll(txtScoreA, strScoreA);
		scoreB.getChildren().addAll(txtScoreB, strScoreB);

		boardA.setSpacing(10.0);
		boardB.setSpacing(10.0);
		scoreA.setSpacing(10.0);
		scoreB.setSpacing(10.0);

		sceneTwoGrid.add(playerA, 0, 0);
		sceneTwoGrid.add(scoreA, 0, 1);
		sceneTwoGrid.add(boardA, 0, 2);
		sceneTwoGrid.add(boardB, 0, 3);
		sceneTwoGrid.add(scoreB, 0, 4);
		sceneTwoGrid.add(playerB, 0, 5);

		sceneTwo = new Scene(sceneTwoGrid, widthSceneTwo, heightSceneTwo);
		//Scene 3 implementation:
		sceneThreeGrid = new GridPane();
		sceneThreeGrid.setPadding(new Insets(25));
		sceneThreeGrid.setHgap(10);
		sceneThreeGrid.setVgap(10);
		sceneThreeGrid.setAlignment(Pos.CENTER);
		final int widthSceneThree = 1040;
		final int heightSceneThree = 380;

		winner = new Label("");
		playAgain = new Button("Play again");
		playAgain.addEventHandler(ActionEvent.ACTION, this);
		exit = new Button("Exit");
		exit.addEventHandler(ActionEvent.ACTION, this);

		sceneThreeGrid.add(winner, 0, 0);
		sceneThreeGrid.add(playAgain, 0, 1);
		sceneThreeGrid.add(exit, 0, 2);

		sceneThree = new Scene(sceneThreeGrid, widthSceneThree, heightSceneThree);


		// Starting the Scene
		stage.setTitle("Oware");
		stage.setScene(sceneOne);
		stage.show();
		

	}

	/**
	 * Main entry point of the application.
	 *
	 * @param args not used
	 */
	public static void main(String[] args) {
		launch();
	}


	@Override
	public void handle(ActionEvent event) {
		int seeds;
		int size;

		if (event.getSource().equals(exit)) {
			event.consume();
			stage.close();
		}

		if (event.getSource().equals(playAgain)) {
			clean();
			stage.setScene(sceneOne);
			event.consume();
		}


		if (event.getSource().equals(submitScene1)) {
			seeds = Integer.parseInt(returnSeeds.getText());
			size = Integer.parseInt(returnSize.getText());    
			fxGameBoard = new MyBoardClass(size, seeds);

			strScoreA.setText("" + fxGameBoard.getScore(1));
			strScoreB.setText("" + fxGameBoard.getScore(2));
			

			arrPitA = new Button[size];
			for (int i = 0; i < size; i++) {
				final int temp = i+1;
				pitA = new Button(returnSeeds.getText());

				pitA.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>(){
					public void handle(ActionEvent arg0) {
						fxGameBoard.play(1, temp);
					}
				});
				pitA.addEventHandler(ActionEvent.ACTION, this);
				
				arrPitA[i] = pitA;

				boardA.getChildren().add(pitA);
				
			} 
			arrPitB = new Button[size];
			for (int i = 0; i < size; i++) {
				final int temp = i+1;
				pitB = new Button(returnSeeds.getText());

				pitB.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>(){
					public void handle(ActionEvent arg0) {
						fxGameBoard.play(2, temp);
					}
				});
				pitB.addEventHandler(ActionEvent.ACTION, this);

				arrPitB[i] = pitB;
				
				boardB.getChildren().add(pitB);
			} 

			// Disable Player B from having first move:
			for (int j = 0; j < fxGameBoard.getSize(); j++) {
				arrPitB[j].setDisable(true);
			}
			if (playerMode.getValue().equals("vs. Bot")) {
				playerB.setText("Bot");
				pvbot = true;
			} 
			if (playerMode.getValue().equals("vs. Player")) {
				playerB.setText("Player B");
				pvplayer = true;
			}
			
			stage.setScene(sceneTwo);

			if (fxGameBoard.gameOver() == true) {
				stage.setScene(sceneThree);
				winner.setText("It's a draw with a final score of: " + fxGameBoard.getScore(1) + " to " + fxGameBoard.getScore(2));
				winner.setFont(Font.font("Arial", 36));
			}
			event.consume();
		}


		if (pvplayer == true) {
			for (int i = 0; i < fxGameBoard.getSize(); i++) {
				arrPitA[i].setText(Integer.toString(fxGameBoard.countSeeeds(1, i+1)));
				arrPitB[i].setText(Integer.toString(fxGameBoard.countSeeeds(2, i+1)));
			}

			strScoreA.setText("" + fxGameBoard.getScore(1));
			strScoreB.setText("" + fxGameBoard.getScore(2));

			if (fxGameBoard.getCurrentPlayer() == 1) {
				for (int i = 0; i < fxGameBoard.getSize(); i++) {
					arrPitA[i].setDisable(false);
					arrPitB[i].setDisable(true); 
					if (fxGameBoard.countSeeeds(1, i+1) == 0) {
						arrPitA[i].setDisable(true);
					}
				}
			}
			if (fxGameBoard.getCurrentPlayer() == 2) {
				for (int i = 0; i < fxGameBoard.getSize(); i++) {
					arrPitA[i].setDisable(true);
					arrPitB[i].setDisable(false);
					if (fxGameBoard.countSeeeds(2, i+1) == 0) {
						arrPitB[i].setDisable(true);
					}
				}
			}
			
			if (fxGameBoard.gameOver() == true) {
				stage.setScene(sceneThree);

				if (fxGameBoard.getScore(1) > fxGameBoard.getScore(2)) {
					winner.setText("Winner is Payer A with a final score of: " + fxGameBoard.getScore(1) + " to " + fxGameBoard.getScore(2));
					winner.setFont(Font.font("Arial", 36));
				}
				if (fxGameBoard.getScore(1) < fxGameBoard.getScore(2)) {
					winner.setText("Winner is Payer B with a final score of: " + fxGameBoard.getScore(2) + " to " + fxGameBoard.getScore(1));
					winner.setFont(Font.font("Arial", 36));
				}
				if (fxGameBoard.getScore(1) == fxGameBoard.getScore(2)) {
					winner.setText("It's a draw with a final score of: " + fxGameBoard.getScore(1) + " to " + fxGameBoard.getScore(2));
					winner.setFont(Font.font("Arial", 36));
				}
			}
			event.consume();		
		}
		if (pvbot == true) {
			
			while (fxGameBoard.getCurrentPlayer() == 2) { // Bot turn
				if (fxGameBoard.gameOver() == true) {
					break;
				}
				Random random = new Random();
				int rand = 0;
				while (true) {
					rand = random.nextInt(fxGameBoard.getSize() + 1);
					if (rand != 0 && fxGameBoard.countSeeeds(2, rand)  != 0) {
						fxGameBoard.play(2, rand);
						break;
					}
				}
			}
			for (int i = 0; i < fxGameBoard.getSize(); i++) {
				arrPitA[i].setText(Integer.toString(fxGameBoard.countSeeeds(1, i+1)));
				arrPitB[i].setText(Integer.toString(fxGameBoard.countSeeeds(2, i+1)));
				if (fxGameBoard.countSeeeds(1, i+1) == 0) {
					arrPitA[i].setDisable(true);
				} else {
					arrPitA[i].setDisable(false);
				}
			}
			strScoreA.setText("" + fxGameBoard.getScore(1));
			strScoreB.setText("" + fxGameBoard.getScore(2));
			
			if (fxGameBoard.gameOver() == true) {
				stage.setScene(sceneThree);

				if (fxGameBoard.getScore(1) > fxGameBoard.getScore(2)) {
					winner.setText("Winner is Payer A with a final score of: " + fxGameBoard.getScore(1) + " to " + fxGameBoard.getScore(2));
					winner.setFont(Font.font("Arial", 36));
				}
				if (fxGameBoard.getScore(1) < fxGameBoard.getScore(2)) {
					winner.setText("Winner is Bot with a final score of: " + fxGameBoard.getScore(2) + " to " + fxGameBoard.getScore(1));
					winner.setFont(Font.font("Arial", 36));
				}
				if (fxGameBoard.getScore(1) == fxGameBoard.getScore(2)) {
					winner.setText("It's a draw with a final score of: " + fxGameBoard.getScore(1) + " to " + fxGameBoard.getScore(2));
					winner.setFont(Font.font("Arial", 36));
				}
			}
			event.consume();
		}
	}
	
	private void clean() { //cleans the changed scenes to enable the play again function
		pvplayer = false;
		pvbot = false;
		fxGameBoard = null;

		boardA.getChildren().clear();
		boardB.getChildren().clear();


	}
}