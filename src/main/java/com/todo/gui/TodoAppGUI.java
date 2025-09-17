package com.todo.gui;
import com.todo.dao.TodoAppDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TodoAppGUI extends JFrame {
    private TodoAppDAO tododao;
    private DefaultTableModel tableModel;
    private JTable todoTable;
    private JTextField titleField;
    private JTextArea descriptionArea;
    private JCheckBox completedCheckBox;
    private JButton addButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JButton refreshButton;
    private JComboBox<String> fillterComboBox;

    public TodoAppGUI() {
        this.tododao= new TodoAppDAO();
        initializeComponents();
        setupLayout();
    }

    private void initializeComponents() {
        setTitle("Todo Apllication");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        String[] columnNames={"ID","Title","Description","Completed","Created At","Updated At"};
        tableModel=new DefaultTableModel(columnNames,0){
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        todoTable =new JTable(tableModel);
        //
        todoTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        todoTable.getSelectionModel().addListSelectionListener(
                (e) -> {
                    if(!e.getValueIsAdjusting()){
//                  loadSelectedtodo();
                    }
                }
        );

        titleField =new JTextField(20);
        descriptionArea= new JTextArea(3,20);
        descriptionArea.setLineWrap(true);
//      would wrap to the next line instead of going beyond the box.
        descriptionArea.setWrapStyleWord(true);

        completedCheckBox=new JCheckBox("completed");
        addButton= new JButton("Add Todo");
        updateButton=new JButton ("Update Todo");
        deleteButton= new JButton("Delte Todo");
        refreshButton= new JButton("Refresh Todo");

        String[] fillterOptions={"All","Completed","Pending"};
        fillterComboBox =new JComboBox<>(fillterOptions);
        fillterComboBox.addActionListener(
                (e)->{

                }
        );

    }

    private void setupLayout(){
        setLayout(new BorderLayout());
        JPanel inputPanel =new JPanel(new GridBagLayout());
//        GridBagLayout that follows a table layout that follows row and col
        GridBagConstraints gbc = new GridBagConstraints(); // we are getting this from the awt liberies (it tell about how element going to present in the Jpanel)
        gbc.insets = new Insets(5,5,5,5);// this gbc.insets requiring an Insets class object ( so this was a reference variable of the Insets class object) |(this will tell the how much distance should be present between each element )
        gbc.gridx = 0; // this will act as a x coordinates (col)
        gbc.gridy = 0; // this will act as a y coordinates (x)
        gbc.anchor= GridBagConstraints.WEST;
        inputPanel.add(new JLabel("Title"),gbc); // mention the titleField->TextField we are placing inside a JPanel->inputPanel
        gbc.gridx=1;
        gbc.fill=GridBagConstraints.HORIZONTAL; //Stretch horizontally to fill the cell’s width.
        inputPanel.add(titleField,gbc);
        gbc.gridx=0;
        gbc.gridy=1;
        inputPanel.add(new JLabel("Description"),gbc);
        gbc.gridx=1;
        gbc.fill=GridBagConstraints.HORIZONTAL; //Stretch horizontally to fill the cell’s width.
        inputPanel.add(descriptionArea,gbc);
        gbc.gridx=1;
        gbc.gridy=2;
        gbc.fill=GridBagConstraints.HORIZONTAL; //Stretch horizontally to fill the cell’s width.
        inputPanel.add(completedCheckBox,gbc);
        add(inputPanel,BorderLayout.NORTH);
    }
}
