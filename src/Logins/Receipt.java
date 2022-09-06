package Logins;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class Receipt extends javax.swing.JFrame {

   
    public Receipt() {
        initComponents();
        displayReceipt();
    }
    public static Connection connection;//handles dbase connection;declares static bec it will be accessed at static main()
    public static Statement statement;
    public ResultSet rs;
    
    public void displayReceipt(){
         connection connect = new connection();
         
        connection = connect.con;
        statement = connect.s;
        dishRec.setText(Resto.dishQty.getText());
        drinksRec.setText(Resto.drinksQty.getText());
        dessRec.setText(Resto.dessQty.getText());
        pastaRec.setText(Resto.pastaQty.getText());
        changeRec.setText(Resto.txtChange.getText());
         try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/db_proj", "root", "");
            String query = "SELECT * FROM customer_table ORDER BY customer_id DESC LIMIT 1;";
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next()) {
                txtName.setText(rs.getString(2));
                txtDish.setText(rs.getString(3));
                txtDrinks.setText(rs.getString(4));
                txtDess.setText(rs.getString(5));
                txtPasta.setText(rs.getString(6));
                txtFee.setText(Double.toString(rs.getDouble(7)));
                txtTotal.setText(Double.toString(rs.getDouble(8)));
            }
        } catch (Exception e) {
        System.err.println("Error");
        }
    
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel21 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        txtDrinks = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtDish = new javax.swing.JLabel();
        txtDess = new javax.swing.JLabel();
        txtPasta = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        dishRec = new javax.swing.JLabel();
        drinksRec = new javax.swing.JLabel();
        dessRec = new javax.swing.JLabel();
        pastaRec = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txtFee = new javax.swing.JLabel();
        txtTotal = new javax.swing.JLabel();
        changeRec = new javax.swing.JLabel();
        txtNames = new javax.swing.JLabel();
        txtName = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("LIST OF ORDER");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel3.setBackground(new java.awt.Color(102, 102, 102));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(204, 204, 204), null));

        txtDrinks.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtDrinks.setForeground(new java.awt.Color(255, 255, 255));
        txtDrinks.setText("ORDER");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("CUSTOMER RECEIPT");

        txtDish.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtDish.setForeground(new java.awt.Color(255, 255, 255));
        txtDish.setText("ORDER");

        txtDess.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtDess.setForeground(new java.awt.Color(255, 255, 255));
        txtDess.setText("ORDER");

        txtPasta.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtPasta.setForeground(new java.awt.Color(255, 255, 255));
        txtPasta.setText("ORDER");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("•••••••••••••");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("•••••••••••••");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("•••••••••••••");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("•••••••••••••");

        dishRec.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        dishRec.setForeground(new java.awt.Color(255, 255, 255));
        dishRec.setText("qty");

        drinksRec.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        drinksRec.setForeground(new java.awt.Color(255, 255, 255));
        drinksRec.setText("qty");

        dessRec.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        dessRec.setForeground(new java.awt.Color(255, 255, 255));
        dessRec.setText("qty");

        pastaRec.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        pastaRec.setForeground(new java.awt.Color(255, 255, 255));
        pastaRec.setText("qty");

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("FEE");

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("CHANGE");

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("TOTAL");

        txtFee.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtFee.setForeground(new java.awt.Color(255, 255, 255));
        txtFee.setText("00");

        txtTotal.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtTotal.setForeground(new java.awt.Color(255, 255, 255));
        txtTotal.setText("00");

        changeRec.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        changeRec.setForeground(new java.awt.Color(255, 255, 255));
        changeRec.setText("00");

        txtNames.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtNames.setForeground(new java.awt.Color(255, 255, 255));
        txtNames.setText("Customer name: ");

        txtName.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtName.setForeground(new java.awt.Color(255, 255, 255));
        txtName.setText("Customer name: ");

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("                     ******************************************************");

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Logins/img1.png"))); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(txtNames)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDrinks)
                            .addComponent(txtDess)
                            .addComponent(txtDish)
                            .addComponent(txtPasta))
                        .addGap(65, 65, 65)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel8)
                            .addComponent(jLabel7)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(drinksRec)
                            .addComponent(dishRec)
                            .addComponent(pastaRec)
                            .addComponent(dessRec))
                        .addGap(21, 21, 21))))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(jLabel15))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jLabel17))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel16)))
                .addGap(185, 185, 185)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtTotal)
                    .addComponent(changeRec)
                    .addComponent(txtFee))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 393, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(86, 86, 86))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(175, 175, 175)
                        .addComponent(txtName))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(167, 167, 167)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNames)
                    .addComponent(txtName))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(dishRec)
                            .addComponent(jLabel8))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(drinksRec)
                            .addComponent(jLabel7))
                        .addGap(28, 28, 28)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(dessRec)
                            .addComponent(jLabel10))
                        .addGap(30, 30, 30)
                        .addComponent(pastaRec)
                        .addGap(61, 61, 61))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(txtDish)
                                .addGap(26, 26, 26)
                                .addComponent(txtDrinks)
                                .addGap(28, 28, 28)
                                .addComponent(txtDess)
                                .addGap(30, 30, 30)
                                .addComponent(txtPasta))
                            .addComponent(jLabel9))
                        .addGap(34, 34, 34)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(txtFee))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(txtTotal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(changeRec))
                .addGap(22, 22, 22))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    
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
            java.util.logging.Logger.getLogger(Receipt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Receipt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Receipt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Receipt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Receipt().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel changeRec;
    private javax.swing.JLabel dessRec;
    public static javax.swing.JLabel dishRec;
    private javax.swing.JLabel drinksRec;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel pastaRec;
    private javax.swing.JLabel txtDess;
    private javax.swing.JLabel txtDish;
    private javax.swing.JLabel txtDrinks;
    private javax.swing.JLabel txtFee;
    private javax.swing.JLabel txtName;
    private javax.swing.JLabel txtNames;
    private javax.swing.JLabel txtPasta;
    private javax.swing.JLabel txtTotal;
    // End of variables declaration//GEN-END:variables
}
