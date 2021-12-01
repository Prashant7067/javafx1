package com.example.assignment;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.sql.Connection;
import java.sql.DriverManager;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;


import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.lang.*;

public class HelloController implements Initializable {
    @FXML
    private TextField Name;
    @FXML
    private TextField Marks;
    @FXML
    private TextField Rollno;
    @FXML
    private TableView<books> tvBooks;
    @FXML
    private TableColumn<books, String> colName;
    @FXML
    private TableColumn<books, Integer> colMarks;
    @FXML
    private TableColumn<books, Integer> colRollno;
    @FXML
    private Button btnSelect;
    @FXML
    private Button btnInsert;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    @FXML TextField tfName;
    @FXML TextField tfMarks;
    @FXML TextField tfRollno;

    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnInsert){
            insertRecord();
        }else if(event.getSource()== btnUpdate){
            insertUpdate();
        }else if(event.getSource() == btnDelete){
            deleteButton();
        }else if(event.getSource() == btnSelect){
            selectButton();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showBooks();

    }
    public Connection getConnection(){
        Connection conn;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/books","root","prashant12345");
            System.out.println("connection sup");
            return conn;
        }catch (Exception ex){
            System.out.println("Error" +ex.getMessage());
            return null;
        }

    }

    public ObservableList<books> getBooksList(){
        ObservableList<books> bookslist= FXCollections.observableArrayList();
        Connection conn = getConnection();
        String Query="SELECT * FROM books";
        Statement st;
        ResultSet rs;

        try{
            st= conn.createStatement();
            rs= st.executeQuery(Query);
            books Books;
            while(rs.next()){
                Books= new books(rs.getString("Name"), rs.getInt("Marks"),rs.getInt("Rollno") );
                bookslist.add(Books);
            }

        }catch (Exception ex){
            ex.printStackTrace();

        }
        return bookslist;
    }
    public void showBooks(){
        ObservableList<books> list=getBooksList();
        colName.setCellValueFactory(new PropertyValueFactory<books,  String> ("Name"));
        colMarks.setCellValueFactory(new PropertyValueFactory<books,  Integer> ("Marks"));
        colRollno.setCellValueFactory(new PropertyValueFactory<books,  Integer> ("Rollno"));

        tvBooks.setItems(list);
    }
    private void insertRecord(){
        String Query= "INSERT INTO BOOKS VALUES ("+ "'"+tfName.getText() + "','" + tfMarks.getText()+ "'," + Integer.parseInt(tfRollno.getText())+ ")";
        executeQuery(Query);
        showBooks();
    }
    private void insertUpdate() {
        String Query = "UPDATE BOOKS SET Name= '" + tfName.getText() + "', Marks= '" + tfMarks.getText() + "'" + "WHERE Rollno = " + Integer.parseInt(tfRollno.getText())+"";
        executeQuery(Query);
        showBooks();
    }
    private void deleteButton() {
        String Query = "DELETE FROM BOOKS WHERE Rollno= "  + tfRollno.getText()+"";
        executeQuery(Query);
        showBooks();
    }
    private void selectButton() {
        String Query = "SELECT * FROM BOOKS WHERE Rollno= " + tfRollno.getText() + "";
        executeQuery(Query);
        showBooks();
    }
    private void executeQuery(String query){
        Connection conn =getConnection();
        Statement st;
        try{
            st=conn.createStatement();
            st.executeUpdate(query);

        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}