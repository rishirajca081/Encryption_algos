package com.example.demo1;



import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EncryptCont implements Initializable {
    @FXML
    private Button addButton;
    @FXML
    private TableView<Data> table;              // Make sure to give name as same as fxid

    @FXML
    private TableColumn<Data, Integer> sno;

    @FXML
    private  TableColumn<Data, String> path;
    //    @FXML
//    private TableColumn deleteButton = new TableColumn("deleteButton");
    @FXML
    private TextField KeyField;

    private int cnt = 1;

    private List<File> files;

    @FXML
            private ListView history;


    ObservableList<Data> list = FXCollections.observableArrayList();

    public void addElement(ActionEvent event){
        files = new FileChooser().showOpenMultipleDialog(null);
        if(files != null){
            for(var oneFile : files){
                int ID = cnt;
                String PATH = oneFile.getAbsolutePath();
                list.add(new Data(ID, PATH));
                cnt++;
                System.out.println(oneFile.getAbsolutePath());
            }
        }else{
            System.out.println("No File Taken");
        }
        event.consume();
    }

    @FXML
    public void handleDragOver(DragEvent event){
        if(event.getDragboard().hasFiles()){
            event.acceptTransferModes(TransferMode.ANY);
        }
        event.consume();
    }

    @FXML
    public void handleDragDropped(DragEvent event){
        files = event.getDragboard().getFiles();
        for(var myfile : files){
            int ID = cnt;
            String PATH = myfile.getAbsolutePath();
            list.add(new Data(ID, PATH));
            cnt++;
            //
            System.out.println("Dropped file: " + myfile.getAbsolutePath());
        }
        event.setDropCompleted(true);
        event.consume();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        table.getColumns().add(deleteButton);

        sno.setCellValueFactory(new PropertyValueFactory<Data,Integer>("sno"));
        path.setCellValueFactory(new PropertyValueFactory<Data, String>("path"));
//        deleteButton.setCellValueFactory(new PropertyValueFactory<Data,String>("deleteButton"));

        table.setItems(list);
    }

    public void deleteRowFromTable(ActionEvent event){
        table.getItems().removeAll(table.getSelectionModel().getSelectedItem());
    }

    public void getKey(ActionEvent event){

        //key we will get come public List<File>file
        System.out.println(KeyField.getText());
        des_ecb_pkcs5_padding des = new des_ecb_pkcs5_padding();
        for(var file : files){
            des.encryptFile(file.getAbsolutePath(), KeyField.getText());
        }

        for(var file : files){
            history.getItems().add(file.getAbsolutePath());
        }
    }
}