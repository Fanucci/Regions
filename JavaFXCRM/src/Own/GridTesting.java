package Own;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

/**
 * 
 * @author ggrec
 *
 */
public class GridTesting extends Application
{

    // ==================== 1. Static Fields ========================

    private final static double GOLDEN_RATIO = 1.618;

    private final static double MIN_TILE_SIZE = 5;
    private final static double MAX_TILE_SIZE = Double.MAX_VALUE;


    // ====================== 2. Instance Fields =============================

    private DoubleProperty prefTileSize = new SimpleDoubleProperty(MIN_TILE_SIZE);

    private double nColumns;
    private double nRows;

    private GridPane gridPane;


    // ==================== 3. Static Methods ====================

    public static void main(final String[] args)
    {
        Application.launch(args);
    }


    // ==================== 4. Constructors ====================

    @Override
    public void start(final Stage primaryStage) throws Exception
    {
        gridPane = new GridPane();

        gridPane.setPadding(new Insets(20));
        gridPane.setHgap(0);
        gridPane.setVgap(0);

        nColumns = Math.floor(Math.sqrt(dummyButtons().length) * 2 / GOLDEN_RATIO);
        nRows    = Math.ceil(dummyButtons().length / nColumns);

        createContents();

     

        primaryStage.setScene(new Scene(gridPane));
        primaryStage.show();
    }


    // ==================== 5. Creators ====================

    private void createContents()
    {
        int i = 0;

        for (final Button button : dummyButtons()) {
            GridPane.setRowIndex(button, i / (int) nColumns);
            GridPane.setColumnIndex(button, i % (int) nColumns);

            button.setMinSize(MIN_TILE_SIZE, MIN_TILE_SIZE);
            button.setMaxSize(MAX_TILE_SIZE, MAX_TILE_SIZE);

            gridPane.getChildren().add(button);

            i++;
        }

        for (int j = 0; j < nColumns; j++) {
            ColumnConstraints cc = new ColumnConstraints();
            cc.setHgrow(Priority.ALWAYS);
            gridPane.getColumnConstraints().add(cc);
        }

        for (int j = 0; j < nRows; j++) {
            RowConstraints rc = new RowConstraints();
            rc.setVgrow(Priority.ALWAYS);
            gridPane.getRowConstraints().add(rc);
        }
    }



    // ==================== 15. Other ====================

    private static final Button[] dummyButtons()
    {
        final Button[] buttons = new Button[5];

        for(int i = 0; i < buttons.length; i++)
        {
            buttons[i] = new Button(String.valueOf(i));
        }

        return buttons;
    }

}