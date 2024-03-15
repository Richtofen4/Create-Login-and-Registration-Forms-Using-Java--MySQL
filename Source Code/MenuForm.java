import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuForm extends JDialog {
    private JPanel Menuwybor;
    private JButton REButton;
    private JButton LOGOButton;
    public MenuForm(JFrame parent){
        setTitle("Menu");
        setContentPane(Menuwybor);
        setMinimumSize(new Dimension(500,550));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        REButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                dispose();
                RegistrationForm registrationForm = new RegistrationForm(null);
            }
        });
        LOGOButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                dispose();
                LoginForm loginForm = new LoginForm(null);
            }
        });
        setVisible(true);
    }
    public User user;
    public static void main(String[] args){
        MenuForm menuForm = new MenuForm(null);
    }
}
