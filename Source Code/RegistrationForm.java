import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class RegistrationForm extends JDialog {
    private JTextField timie;
    private JTextField tnazwisko;
    private JTextField tnazwa;
    private JTextField temail;
    private JPasswordField phaslo;
    private JPasswordField ppotwierdzhaslo;
    private JButton zareSięButton;
    private JButton aButton;
    private JPanel rejestracja;

    public RegistrationForm(JFrame parent){
        super(parent);
        setTitle("Załóż nowe konto");
        setContentPane(rejestracja);
        setMinimumSize(new Dimension(500,550));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        zareSięButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rejestracja_uzytkownika();
            }
        });
        aButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                anuluj();
            }
        });

        setVisible(true);
    }

    private void anuluj() {
        setVisible(false);
        dispose();
    }

    private void rejestracja_uzytkownika() {
        String imie = timie.getText();
        String nazwisko = tnazwisko.getText();
        String nazwa = tnazwa.getText();
        String email = temail.getText();
        String haslo = String.valueOf(phaslo.getPassword());
        String potwierdzhaslo = String.valueOf(ppotwierdzhaslo.getPassword());

        if(imie.isEmpty() || nazwisko.isEmpty() || email.isEmpty() || nazwa.isEmpty() || haslo.isEmpty() || potwierdzhaslo.isEmpty()){
            JOptionPane.showMessageDialog(this, "Proszę wypełnić wszystkie pola!", "Spróbuj ponownie", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if(!haslo.equals(potwierdzhaslo)){
            JOptionPane.showMessageDialog(this, "Hasła nie są takie same!", "Spróbuj ponownie", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if(nazwa.length()<5){
            JOptionPane.showMessageDialog(this, "Nazwa użytkownika powinna zawierać conajmniej 5 znaków", "Spróbuj ponownie", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int dl = 0;
        int ml = 0;
        int cy = 0;
        int malpa = 0;
        boolean duza = false;
        boolean mala = false;
        boolean cyfra = false;
        boolean malpaznak = false;
        char[] tabhaslo = haslo.toCharArray();

        for(char znak : tabhaslo){
            if(Character.isUpperCase(znak)){
                dl++;
            } else if (Character.isLowerCase(znak)){
                ml++;
            } else if (Character.isDigit(znak)) {
                cy++;
            }
        }
        char[] tabemail = email.toCharArray();

        for(char znak1 : tabemail){
            if(znak1 == '@'){
                malpa++;
            }
        }
        if (dl>0) duza = true;
        if(ml>0) mala =true;
        if(cy>0) cyfra = true;
        if(malpa == 1) malpaznak = true;

        if(haslo.length()<8 || cyfra == false || mala == false || duza == false){
            JOptionPane.showMessageDialog(this, "Hasło powinno zawierać conajmniej 8 znaków, małe i duże litery oraz cyfry", "Spróbuj ponownie", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(malpaznak == false){
            JOptionPane.showMessageDialog(this, "Nieprawidłowy adres email!", "Spróbuj ponownie", JOptionPane.ERROR_MESSAGE);
            return;
        }
        user = addUserToDatabase(imie,nazwisko,nazwa,email,haslo);
        if(user != null){
            dispose();
        }
        else{
            JOptionPane.showMessageDialog(this,"NIe udało się zarejestrować nowego użtkownika!", "Spróbuj ponownie", JOptionPane.ERROR_MESSAGE);
        }
    }
    public User user;
    private User addUserToDatabase(String imie, String nazwisko, String nazwa, String email, String haslo){
        User user = null;
        final String DB_URL = "jdbc:mysql://localhost/rejest?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "";
        try{
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO users (imie, nazwisko, email, nazwa,haslo)"+ "VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, imie);
            preparedStatement.setString(2,nazwisko);
            preparedStatement.setString(3,email);
            preparedStatement.setString(4,nazwa);
            preparedStatement.setString(5, haslo);

            int addedRows = preparedStatement.executeUpdate();
            if(addedRows>0){
                user = new User();
                user.imie = imie;
                user.nazwisko = nazwisko;
                user.nazwa = nazwa;
                user.email = email;
                user.haslo = haslo;
            }

            stmt.close();
            conn.close();

        }catch(Exception e){
            e.printStackTrace();
        }

        return user;
    }

    public static void main(String[] args){
        RegistrationForm myForm = new RegistrationForm(null);

    }
}