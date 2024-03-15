import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LoginForm extends JDialog{
    private JTextField tEmail;
    private JPasswordField pPassword;
    private JButton blogin;
    private JButton banuluj;
    private JPanel loginPanel;

    public LoginForm(JFrame parent) {
        setTitle("Login");
        setContentPane(loginPanel);
        setMinimumSize(new Dimension(500, 550));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        blogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = tEmail.getText();
                String password = String.valueOf(pPassword.getPassword());
                user = getAuthenticatedUser(email, password);

                if (user != null) {
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(LoginForm.this, "Email lub hasło są niepoprawne!\nSpróbuj ponownie", "Sprubuj ponownie", JOptionPane.ERROR_MESSAGE);
                }
                if (user != null) {
                    setVisible(false);
                    dispose();
                    InfoLogin infoLogin = new InfoLogin(null);
                }
            }
        });
        banuluj.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                dispose();
            }
        });
        setVisible(true);
    }
    private User getAuthenticatedUser(String email, String haslo){
        User user = null;

        final String DB_URL = "jdbc:mysql://localhost/rejest?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "";
        try{
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM users WHERE email=? AND haslo=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, haslo);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                user = new User();
                user.imie = resultSet.getString("imie");
                user.nazwisko = resultSet.getString("nazwisko");
                user.nazwa = resultSet.getString("nazwa");
                user.email = resultSet.getString("email");
                user.haslo = resultSet.getString("haslo");
            }
            stmt.close();
            conn.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return user;
    }
    public User user;
    public static void main(String[] args){
        LoginForm loginForm = new LoginForm(null);
    }
}
