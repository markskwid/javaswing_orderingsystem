package Logins;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;


public class Resto extends javax.swing.JFrame {

     public static Connection connection;//handles dbase connection;declares static bec it will be accessed at static main()
    public static Statement statement;//handles sql statement;declares static bec it will be accessed at static main()
  //handles resultsets or data/contents of rows in your dbase/table
    ResultSet rs;
  
    
  
    public Resto() {
        initComponents();
        update_tbl();
    }
    
    public void clearInput(){
        dishQty.setText(null);
        drinksQty.setText(null);
        pastaQty.setText(null);
        dessQty.setText(null);
        totalDishes.setText(null);
        totalDessert.setText(null);
        totalPrice.setText(null);
        totalDrinks.setText(null);
        totalPasta.setText(null);
        dishesCombo.setSelectedIndex(0);
        drinksCombo.setSelectedIndex(0);
        dessCombo.setSelectedIndex(0);
        pastaCombo.setSelectedIndex(0);
        customerFee.setText(null);
        txtChange.setText(null);
        customerName.setText(null);
      }
    
     public void update_tbl() {
        idTxt.setEditable(false);
      
        connection connect = new connection();
         
        connection = connect.con;
        statement = connect.s;
        DefaultTableModel tModel = (DefaultTableModel)jTable1.getModel();
        try {
           
             int row = jTable1.getRowCount();
            while (row > 0) {
                row--;
                tModel.removeRow(row);
            }
            
            Object[] data = new Object[8];
             rs = statement.executeQuery("SELECT * FROM customer_table");
                
            while (rs.next()) {
                data[0] = rs.getInt("customer_id");
                data[1] = rs.getString("customer_name");
                data[2] = rs.getString("customer_dish");
                data[3] = rs.getString("customer_drinks");
                data[4] = rs.getString("customer_dessert");
                data[5] = rs.getString("customer_pasta");
                data[6] = rs.getDouble("customer_fee"); 
                data[7] = rs.getDouble("customer_total");  
                tModel.addRow(data);
            }
            
      
           
        } catch (SQLException ex) {
            Logger.getLogger(Resto.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }  
    }
     
     
     public void addRow(String name, String dish, String drinks, String dessert, String pasta, double fee, double total_price){
         
        try{
        connection = DriverManager.getConnection("jdbc:mysql://localhost/db_proj", "root","");
        PreparedStatement ps = connection.prepareStatement("INSERT INTO customer_table(customer_name,  customer_dish,  customer_drinks,  customer_dessert, customer_pasta, customer_fee, customer_total) VALUES(?, ?, ?, ?, ?, ?, ?)");
     
          ps.setString(1, name);
          ps.setString(2, dish);
          ps.setString(3, drinks);
          ps.setString(4, dessert);
          ps.setString(5, pasta);
           ps.setDouble(6, fee);
          ps.setDouble(7, total_price);
         
          int row = ps.executeUpdate();
          
          if(row>0){
                JOptionPane.showMessageDialog(this, "Record has been added");
                update_tbl();
          }else{
                JOptionPane.showMessageDialog(this, "Failed!");
          }
             
        }catch(Exception e){
             Logger.getLogger(Resto.class.getName()).log(Level.SEVERE, null, e);
        }
             
     }
     
      public void editInfo(int id, String newName, String newDish, String newDrinks, String newDessert,  String newPasta,  double fee, double newTotal){
        try{
            String sqlSearch= "SELECT * FROM customer_table WHERE customer_id ='"+id+"'";
            rs = statement.executeQuery(sqlSearch);
            
            if(rs.next()){
                
                  int x = JOptionPane.showConfirmDialog(null, "Are you sure you want to update "+ rs.getString("customer_name")+"?", "Update", 2);
                  if(x==0){
                       statement.executeUpdate("UPDATE customer_table SET customer_name='" + newName + "',customer_dish='" + newDish + "',customer_drinks='" + newDrinks + "',customer_dessert='"+newDessert+ "', customer_pasta='"+newPasta+"', customer_fee='"+fee+"',customer_total='"+newTotal+"' where customer_id='" + id + "'");
                       JOptionPane.showMessageDialog(null, "Record  saved.", "Search", 1);
                       update_tbl();
                  }else{
                      JOptionPane.showMessageDialog(null, "Record not save.", "Search", 1);
                      return;
                  }
            }
            
        }catch(Exception e){
            Logger.getLogger(Resto.class.getName()).log(Level.SEVERE, null, e);
        } 
    }
     
     public void deleteInfo(int id){
      
           try{
                String sqlSearch = "SELECT * FROM customer_table WHERE customer_id ='"+id+"'";
                rs = statement.executeQuery(sqlSearch);
                
                if(rs.next()){
                    String nameDel = rs.getString("customer_name");
                    int x = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete "+ nameDel+"?", "Delete", 2);
                    if(x==0){
                        statement.executeUpdate("delete from customer_table where customer_id = '" + id + "'");
                        JOptionPane.showMessageDialog(null, nameDel+ " is successfully deleted", "DELETED", 1);
                        update_tbl();
                        clearInput();
                        
                    }else{
                         JOptionPane.showMessageDialog(null, "Error deleting data", "DELETED", 1);
                    }
                }
           }catch(Exception e){
                Logger.getLogger(Resto.class.getName()).log(Level.SEVERE, null, e);
           }
               
               
    }
     
      public double totalPrice(){
          double total = 0;
          
          total = addDish() + addDrinks() + addDessert() + addPasta();
          
          
          return total;
      }
      
      
      
     
     public boolean testInput(){
         boolean flag = false;
         String regex = "^[\\p{L} .'-]+$";
         String regex_qty = "\\d+";
         double fee = 0; 
         
         if(customerName.getText().length() > 8 && customerName.getText().matches(regex)){
             if(dishesCombo.getSelectedIndex() !=0){
                 if(drinksCombo.getSelectedIndex() !=0){
                     if(dessCombo.getSelectedIndex() !=0){
                         if(pastaCombo.getSelectedIndex() !=0){
                             if(dishQty.getText().matches(regex_qty)){
                                 if(drinksQty.getText().matches(regex_qty)){
                                     if(dessQty.getText().matches(regex_qty)){
                                         if(pastaQty.getText().matches(regex_qty)){
                                           flag = true;
                                         }else{
                                            JOptionPane.showMessageDialog(this, "Please enter proper quantity for pasta"); 
                                         }
                                     }else{
                                        JOptionPane.showMessageDialog(this, "Please enter proper quantity for dessert"); 
                                     }
                                 }else{
                                    JOptionPane.showMessageDialog(this, "Please enter proper quantity for drinks");
                                 }
                             }else{
                                 JOptionPane.showMessageDialog(this, "Please enter proper quantity for dish");
                             }
                         }else{
                             JOptionPane.showMessageDialog(this, "Please select your pasta");
                         }
                     }else{
                         JOptionPane.showMessageDialog(this, "Please select your dessert");
                     }
                 }else{
                     JOptionPane.showMessageDialog(this, "Please select your drink");
                 }
             }else{
                JOptionPane.showMessageDialog(this, "Please select your main dish");
             }
         }else{
            JOptionPane.showMessageDialog(this, "Enter proper name");
         }
         
               
         return flag;
         
     }
     
     
    public boolean testAdd(){
        boolean flag = false;
        String regex_qty = "\\d+";
       
      
        if(testInput()){
          if(customerFee.getText().length() > 1){
            if(customerFee.getText().matches(regex_qty)){
                 double fee = Double.parseDouble(customerFee.getText());
                if(Double.parseDouble(customerFee.getText()) > totalPrice()){
                    fee = fee - totalPrice();
                    if(fee<0){
                        JOptionPane.showMessageDialog(this, "Something's wrong"); 
                    }else{
                        flag = true;
                        txtChange.setText(Double.toString(fee));
                    }
                }else{
                   JOptionPane.showMessageDialog(this, "Your money isn't enough to buy your order");
                }
            }else{
               JOptionPane.showMessageDialog(this, "Please enter proper fee"); 
            }
        }else{
           JOptionPane.showMessageDialog(this, "Please enter your fee"); 
        }
            
       }else{
             JOptionPane.showMessageDialog(this, "Please check your order"); 
        }
       
        
        
        
        
        return flag;
    }
    
    
    public double addDish(){
        double dishPrice = 0;
      
        if(dishesCombo.getSelectedItem().equals("Fillet-O-Fish")){
            dishPrice = 100 * Integer.parseInt(dishQty.getText());
        }else if(dishesCombo.getSelectedItem().equals("Menudo")){
            dishPrice = 80 * Integer.parseInt(dishQty.getText());
        }else if(dishesCombo.getSelectedItem().equals("Chicken Curry")){
            dishPrice = 90 * Integer.parseInt(dishQty.getText());
        }else if(dishesCombo.getSelectedItem().equals("Steak")){
            dishPrice = 100 * Integer.parseInt(dishQty.getText());
        }else if(dishesCombo.getSelectedItem().equals("Rice")){
            dishPrice = 40 * Integer.parseInt(dishQty.getText());
        }
        
        return dishPrice;
    }
    
    
    
     public double addDrinks(){
        double drinksPrice = 0;
        
        if(drinksCombo.getSelectedItem().equals("Coke")){
            drinksPrice = 60 * Integer.parseInt(drinksQty.getText());
        }else if(drinksCombo.getSelectedItem().equals("Mango Shake")){
            drinksPrice = 80 * Integer.parseInt(drinksQty.getText());
        }else if(drinksCombo.getSelectedItem().equals("Buko Juice")){
            drinksPrice = 50 * Integer.parseInt(drinksQty.getText());
        }else if(drinksCombo.getSelectedItem().equals("Hot Chocolate")){
            drinksPrice = 100 * Integer.parseInt(drinksQty.getText());
        }else if(drinksCombo.getSelectedItem().equals("Apple Juice")){
            drinksPrice = 40 * Integer.parseInt(drinksQty.getText());
        }
        
        return drinksPrice;
    }
     
      public double addDessert(){
        double dessPrice = 0;
        
        if(dessCombo.getSelectedItem().equals("Salad")){
            dessPrice = 80 * Integer.parseInt(dessQty.getText());
        }else if(dessCombo.getSelectedItem().equals("Leche Plan")){
            dessPrice = 80 * Integer.parseInt(dessQty.getText());
        }else if(dessCombo.getSelectedItem().equals("Ube")){
            dessPrice = 50 * Integer.parseInt(dessQty.getText());
        }else if(dessCombo.getSelectedItem().equals("Halo-Halo")){
            dessPrice = 100 * Integer.parseInt(dessQty.getText());
        }else if(dessCombo.getSelectedItem().equals("Macarons")){
            dessPrice = 50 * Integer.parseInt(dessQty.getText());
        }
        
        return dessPrice;
    }
      
      public double addPasta(){
        double pastaPrice = 0;
        
        
       
        if(pastaCombo.getSelectedItem().equals("Carbonara")){
            pastaPrice = 100 * Integer.parseInt(pastaQty.getText());
        }else if(pastaCombo.getSelectedItem().equals("Spaghetti")){
            pastaPrice = 70 * Integer.parseInt(pastaQty.getText());
        }else if(pastaCombo.getSelectedItem().equals("Baked Manicotti")){
            pastaPrice = 120 * Integer.parseInt(pastaQty.getText());
        }else if(pastaCombo.getSelectedItem().equals("Lasagna")){
           pastaPrice = 120 * Integer.parseInt(pastaQty.getText());
        }
        
        return pastaPrice;
    }
      
     
     
      
      

    /**
     * This method i
          
          s called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        dishesCombo = new javax.swing.JComboBox<>();
        drinksCombo = new javax.swing.JComboBox<>();
        pastaCombo = new javax.swing.JComboBox<>();
        dessCombo = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        drinksQty = new javax.swing.JTextField();
        dishQty = new javax.swing.JTextField();
        pastaQty = new javax.swing.JTextField();
        dessQty = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        customerName = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        nameTbl = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        dishTbl = new javax.swing.JComboBox<>();
        drinksTbl = new javax.swing.JComboBox<>();
        dessTbl = new javax.swing.JComboBox<>();
        pastaTbl = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        feeTbl = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        priceTbl = new javax.swing.JTextField();
        idTxt = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        totalDishes = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        totalDrinks = new javax.swing.JTextField();
        totalDessert = new javax.swing.JTextField();
        totalPrice = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        totalPasta = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        totalBtn = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        customerFee = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtChange = new javax.swing.JTextField();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 12));

        jLabel1.setFont(new java.awt.Font("Sylfaen", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(204, 204, 204));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("RESTAURANT MANAGEMEMT SYSTEM");
        jLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 10));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 869, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel2.setBackground(new java.awt.Color(0, 0, 0));
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Logins/plate-1.png"))); // NOI18N

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Logins/plate-1.png"))); // NOI18N

        jPanel2.setBackground(new java.awt.Color(102, 102, 102));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 5));

        dishesCombo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        dishesCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Dishes", "Fillet-O-Fish", "Menudo", "Chicken Curry", "Steak", "Rice" }));
        dishesCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dishesComboActionPerformed(evt);
            }
        });

        drinksCombo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        drinksCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Drinks", "Coke", "Mango Shake", "Buko Juice", "Hot Chocolate", "Apple Juice" }));
        drinksCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                drinksComboActionPerformed(evt);
            }
        });

        pastaCombo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        pastaCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Pasta", "Carbonara", "Spaghetti", "Baked Manicotti", "Lasagna" }));
        pastaCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pastaComboActionPerformed(evt);
            }
        });

        dessCombo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        dessCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Dessert", "Salad", "Leche Plan", "Ube", "Halo-Halo", "Macarons" }));
        dessCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dessComboActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setText("Quantity");

        drinksQty.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        drinksQty.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 3));

        dishQty.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        dishQty.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 3));

        pastaQty.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        pastaQty.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 3));

        dessQty.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        dessQty.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 3));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setText("NAME OF CUSTOMER");

        customerName.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        customerName.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 3));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(dishesCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(pastaCombo, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(drinksCombo, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(dessCombo, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(31, 31, 31)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(drinksQty, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(dishQty, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(dessQty, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(pastaQty, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                        .addGap(20, 20, 20)
                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(customerName)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(customerName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dishesCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dishQty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(drinksQty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(drinksCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dessCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dessQty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pastaCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pastaQty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(102, 102, 102));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 5));

        jTable1.setBackground(new java.awt.Color(204, 204, 204));
        jTable1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 0));
        jTable1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Name", "Dish", "Drinks", "Dessert", "Pasta", "Fee", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.getTableHeader().setReorderingAllowed(false);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(2).setResizable(false);
            jTable1.getColumnModel().getColumn(3).setResizable(false);
            jTable1.getColumnModel().getColumn(4).setResizable(false);
            jTable1.getColumnModel().getColumn(5).setResizable(false);
            jTable1.getColumnModel().getColumn(6).setResizable(false);
            jTable1.getColumnModel().getColumn(7).setResizable(false);
        }

        jPanel6.setBackground(new java.awt.Color(204, 204, 204));
        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(204, 204, 204), null));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("CUSTOMER FEE: ");

        nameTbl.setFont(new java.awt.Font("Ebrima", 0, 12)); // NOI18N
        nameTbl.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 3));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("CUSTOMER ID: ");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setText("DISH");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setText("DRINKS");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setText("PASTA");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel12.setText("DESSERT");

        dishTbl.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        dishTbl.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Dishes", "Fillet-O-Fish", "Menudo", "Chicken Curry", "Steak", "Rice" }));
        dishTbl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dishTblActionPerformed(evt);
            }
        });

        drinksTbl.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        drinksTbl.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Drinks", "Coke", "Mango Shake", "Buko Juice", "Hot Chocolate", "Apple Juice" }));
        drinksTbl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                drinksTblActionPerformed(evt);
            }
        });

        dessTbl.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        dessTbl.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Dessert", "Salad", "Leche Plan", "Ube", "Halo-Halo", "Macarons" }));
        dessTbl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dessTblActionPerformed(evt);
            }
        });

        pastaTbl.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        pastaTbl.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Pasta", "Carbonara", "Spaghetti", "Baked Manicotti", "Lasagna" }));
        pastaTbl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pastaTblActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel14.setText("NAME OF CUSTOMER");

        feeTbl.setFont(new java.awt.Font("Ebrima", 0, 12)); // NOI18N
        feeTbl.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 3));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setText("TOTAL PRICE: ");

        priceTbl.setFont(new java.awt.Font("Ebrima", 0, 12)); // NOI18N
        priceTbl.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 3));

        idTxt.setFont(new java.awt.Font("Ebrima", 1, 12)); // NOI18N
        idTxt.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(51, 51, 51), null));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(36, 36, 36)
                        .addComponent(idTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(jLabel8))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(priceTbl, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
                            .addComponent(feeTbl)
                            .addComponent(nameTbl))))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addGap(34, 34, 34)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pastaTbl, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(dessTbl, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(drinksTbl, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(dishTbl, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(31, 31, 31))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(idTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nameTbl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addGap(13, 13, 13)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(feeTbl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(priceTbl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(dishTbl, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(drinksTbl, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(dessTbl, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(pastaTbl, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11)))))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel3.setBackground(new java.awt.Color(102, 102, 102));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 5));

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel18.setText("Cost of Dishes:");

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel19.setText("Cost of Drinks:");

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel20.setText("Cost of Pasta:");

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel22.setText("Cost of Dessert:");

        totalPrice.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel23.setText("TOTAL :");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel22)
                    .addComponent(jLabel19)
                    .addComponent(jLabel18)
                    .addComponent(jLabel20)
                    .addComponent(jLabel23))
                .addGap(39, 39, 39)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(totalDrinks, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(totalDishes, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(totalDessert, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(totalPasta, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(totalPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 21, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel18)
                    .addComponent(totalDishes, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(totalDrinks, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19))
                .addGap(13, 13, 13)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(totalDessert, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(totalPasta, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(totalPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.setBackground(new java.awt.Color(102, 102, 102));
        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 5));

        totalBtn.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        totalBtn.setText("TOTAL");
        totalBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                totalBtnActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton2.setText("RECEIPT");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton3.setText("RESET");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton4.setText("EXIT");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton6.setText("ADD");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("CUSTOMER FEE:");

        customerFee.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        customerFee.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 3));

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("CHANGE:");

        txtChange.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtChange.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 3));

        jButton7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton7.setText("EDIT");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton8.setText("DELETE");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(totalBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(customerFee, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50)
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtChange, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(72, 72, 72)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(63, 63, 63)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton4)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(customerFee)
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtChange, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(totalBtn)
                    .addComponent(jButton2)
                    .addComponent(jButton7))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton6)
                    .addComponent(jButton3)
                    .addComponent(jButton8))
                .addGap(18, 18, 18)
                .addComponent(jButton4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void dishesComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dishesComboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dishesComboActionPerformed

    private void drinksComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_drinksComboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_drinksComboActionPerformed

    private void pastaComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pastaComboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pastaComboActionPerformed

    private void dessComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dessComboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dessComboActionPerformed

    private void totalBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_totalBtnActionPerformed
       customerFee.setText(null);
       txtChange.setText(null);
        if(testInput() == true){
       totalDishes.setText(Double.toString(addDish()));
       totalDrinks.setText(Double.toString(addDrinks()));
       totalDessert.setText(Double.toString(addDessert()));
       totalPasta.setText(Double.toString(addPasta()));
       totalPrice.setText(Double.toString(totalPrice()));
       }
      
      
    }//GEN-LAST:event_totalBtnActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        dishQty.setText(null);
        drinksQty.setText(null);
        pastaQty.setText(null);
        dessQty.setText(null);
        totalDishes.setText(null);
        totalDessert.setText(null);
        totalPrice.setText(null);
        totalDrinks.setText(null);
        totalPasta.setText(null);
        dishesCombo.setSelectedIndex(0);
        drinksCombo.setSelectedIndex(0);
        dessCombo.setSelectedIndex(0);
        pastaCombo.setSelectedIndex(0);
        customerFee.setText(null);
        txtChange.setText(null);
        customerName.setText(null);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
       
        if(testAdd()){
        String name = customerName.getText();
        String dish = dishesCombo.getItemAt(dishesCombo.getSelectedIndex());
        String drinks = drinksCombo.getItemAt(drinksCombo.getSelectedIndex());
        String dessert = dessCombo.getItemAt(dessCombo.getSelectedIndex());
        String pasta = pastaCombo.getItemAt(pastaCombo.getSelectedIndex());
        double total = Double.parseDouble(totalPrice.getText());
        double fee = Double.parseDouble(customerFee.getText());
         addRow(name, dish, drinks, dessert, pasta, fee, total);
          new Receipt().setVisible(true); 
        }
       
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
       int i = jTable1.getSelectedRow();
       TableModel model = jTable1.getModel();
       idTxt.setText(model.getValueAt(i, 0).toString());
      
       nameTbl.setText(model.getValueAt(i, 1).toString());
       customerName.setText(model.getValueAt(i, 1).toString());
       String dish = model.getValueAt(i, 2).toString();
       
       //for setting value to null
       dishQty.setText(null);
       drinksQty.setText(null);
       dessQty.setText(null);
       pastaQty.setText(null);
       totalDishes.setText(null);
       totalDrinks.setText(null);
       totalDessert.setText(null);
       totalPasta.setText(null);
       
        switch(dish){
           case "Fillet-O-Fish":
               dishTbl.setSelectedIndex(1);
               dishesCombo.setSelectedIndex(1);
               break;
           case "Menudo":
               dishTbl.setSelectedIndex(2);
               dishesCombo.setSelectedIndex(2);
               break;
           case "Chicken Curry":
               dishTbl.setSelectedIndex(3);
               dishesCombo.setSelectedIndex(3);
               break;
           case "Steak":
              dishTbl.setSelectedIndex(4);
              dishesCombo.setSelectedIndex(4);
               break;
           case "Rice":
               dishTbl.setSelectedIndex(5);
               dishesCombo.setSelectedIndex(5);
               break;
         }
     
       
       String drinks = model.getValueAt(i, 3).toString();
       switch(drinks){
           case "Coke":
               drinksTbl.setSelectedIndex(1);
               drinksCombo.setSelectedIndex(1);
               break;
           case "Mango Shake":
               drinksTbl.setSelectedIndex(2);
               drinksCombo.setSelectedIndex(2);
               break;
           case "Buko Juice":
              drinksTbl.setSelectedIndex(3);
              drinksCombo.setSelectedIndex(3);
               break;
           case "Hot Chocolate":
               drinksTbl.setSelectedIndex(4);
               drinksCombo.setSelectedIndex(5);
               break;
           case "Apple Juice":
              drinksTbl.setSelectedIndex(5);
              drinksCombo.setSelectedIndex(5);
               break;
         }
       
       String dessert = model.getValueAt(i, 4).toString();
        switch(dessert){
           case "Salad":
               dessTbl.setSelectedIndex(1);
               dessCombo.setSelectedIndex(1);
               break;
           case "Leche Plan":
                dessTbl.setSelectedIndex(2);
                 dessCombo.setSelectedIndex(2);
               break;
           case "Ube":
                dessTbl.setSelectedIndex(3);
                 dessCombo.setSelectedIndex(3);
               break;
           case "Halo-Halo":
                dessTbl.setSelectedIndex(4);
                 dessCombo.setSelectedIndex(4);
               break;
           case "Macarons":
                dessTbl.setSelectedIndex(5);
                 dessCombo.setSelectedIndex(5);
               break;
         }
        
         String pasta = model.getValueAt(i, 5).toString();
        switch(pasta){
           case "Carbonara":
               pastaTbl.setSelectedIndex(1);
               pastaCombo.setSelectedIndex(1);
               break;
           case "Spaghetti":
                pastaTbl.setSelectedIndex(2);
                pastaCombo.setSelectedIndex(2);
               break;
           case "Baked Manicotti":
                pastaTbl.setSelectedIndex(3);
                pastaCombo.setSelectedIndex(3);
               break;
           case "Lasagna":
               pastaTbl.setSelectedIndex(4);
               pastaCombo.setSelectedIndex(4);
               break;
         }
        feeTbl.setEditable(false);
        feeTbl.setText(model.getValueAt(i, 6).toString());
        customerFee.setText(model.getValueAt(i, 6).toString());
        
        priceTbl.setEditable(false);
        priceTbl.setText(model.getValueAt(i, 7).toString());
        totalPrice.setText(model.getValueAt(i, 7).toString());
        
        double c_fee = Double.parseDouble(customerFee.getText());
        double c_total = Double.parseDouble(totalPrice.getText());
        
        double change = c_fee - c_total;
        
        txtChange.setText(Double.toString(change));
        
        
   
                   
    }//GEN-LAST:event_jTable1MouseClicked

    private void pastaTblActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pastaTblActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pastaTblActionPerformed

    private void dessTblActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dessTblActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dessTblActionPerformed

    private void drinksTblActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_drinksTblActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_drinksTblActionPerformed

    private void dishTblActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dishTblActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dishTblActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if(testAdd()){
        new Receipt().setVisible(true); 
        }

      
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        if(testAdd()){
            int id = Integer.parseInt(idTxt.getText());
            String Newname = customerName.getText();
            String Newdish = dishesCombo.getItemAt(dishesCombo.getSelectedIndex());
            String Newdrinks = drinksCombo.getItemAt(drinksCombo.getSelectedIndex());
            String Newdessert = dessCombo.getItemAt(dessCombo.getSelectedIndex());
            String Newpasta = pastaCombo.getItemAt(pastaCombo.getSelectedIndex());
            double Newtotal = Double.parseDouble(totalPrice.getText());
            double Newfee = Double.parseDouble(customerFee.getText());

            editInfo(id, Newname, Newdish, Newdrinks, Newdessert, Newpasta,  Newfee, Newtotal);
           
        }
        
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        deleteInfo(Integer.parseInt(idTxt.getText()));
    }//GEN-LAST:event_jButton8ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Resto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Resto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Resto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Resto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Resto().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField customerFee;
    private javax.swing.JTextField customerName;
    private javax.swing.JComboBox<String> dessCombo;
    public static javax.swing.JTextField dessQty;
    private javax.swing.JComboBox<String> dessTbl;
    public static javax.swing.JTextField dishQty;
    private javax.swing.JComboBox<String> dishTbl;
    private javax.swing.JComboBox<String> dishesCombo;
    private javax.swing.JComboBox<String> drinksCombo;
    public static javax.swing.JTextField drinksQty;
    private javax.swing.JComboBox<String> drinksTbl;
    private javax.swing.JTextField feeTbl;
    private javax.swing.JTextField idTxt;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField nameTbl;
    private javax.swing.JComboBox<String> pastaCombo;
    public static javax.swing.JTextField pastaQty;
    private javax.swing.JComboBox<String> pastaTbl;
    private javax.swing.JTextField priceTbl;
    private javax.swing.JButton totalBtn;
    private javax.swing.JTextField totalDessert;
    private javax.swing.JTextField totalDishes;
    private javax.swing.JTextField totalDrinks;
    private javax.swing.JTextField totalPasta;
    private javax.swing.JTextField totalPrice;
    public static javax.swing.JTextField txtChange;
    // End of variables declaration//GEN-END:variables
}
