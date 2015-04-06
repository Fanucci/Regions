package Own;

import java.util.concurrent.atomic.AtomicInteger;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public final class DnDTableViewDemo extends Application {
     private final AtomicInteger id = new AtomicInteger(0);
     
     @Override
     public void start(final Stage stage) throws Exception {     
          final SplitPane pane = new SplitPane();
          final Scene scene = new Scene(pane, 600, 400, Color.LIGHTBLUE);
          stage.setScene(scene);
          
          final TableView<TableRow> table1 = createTableView();
          final TableView<TableRow> table2 = createTableView();
          
          for(int i = 0; i < 10; i++) {
               table1.getItems().addAll(new TableRow());
               table2.getItems().addAll(new TableRow());
          }
          
          pane.getItems().addAll(table1, table2);
          
          stage.show();
     }
     
     private TableView<TableRow> createTableView() {
          final TableView<TableRow> table = new TableView<TableRow>();
          table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
          final ObservableList<TableRow> tableData = FXCollections.observableArrayList();
          table.setItems(tableData);
          
          final TableColumn<TableRow, Integer> idCol = new TableColumn<TableRow, Integer>("ID");
          idCol.setCellValueFactory(new PropertyValueFactory<TableRow, Integer>("nodeId"));
          table.getColumns().add(idCol);
          
          // dnd stuff begin
          table.setOnDragDetected(new EventHandler<MouseEvent>() {
               @Override public void handle(final MouseEvent me) {
                    log("setOnDragDetected("+me+")");
                    final Dragboard db = table.startDragAndDrop(TransferMode.COPY);
                    final ClipboardContent content = new ClipboardContent();
                    content.putString("Drag Me!");
                    db.setContent(content);
                    me.consume();
               }
          });
          table.setOnDragEntered(new EventHandler<DragEvent>() {
               @Override public void handle(final DragEvent de) {
               }
          });
          
          table.setOnDragOver(new EventHandler<DragEvent>() {
               @Override public void handle(final DragEvent de) {
                    de.acceptTransferModes(TransferMode.COPY);

                    de.consume();
               }
          });
          
          // dnd stuff end
          
          return table;
     }
     
     public static void main(String ...args) throws Exception {
          launch(args);
     }
     
     private static void log(Object o) {
          System.out.println(""+o);
     }
}