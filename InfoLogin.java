import javax.swing.*;
import java.awt.*;

public class InfoLogin  extends JDialog{
    private JPanel informacja;

    public InfoLogin (JFrame parent) {
        setTitle("Info");
        setContentPane(informacja);
        setMinimumSize(new Dimension(550,550));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args){
        InfoLogin infologin = new InfoLogin(null);
    }

}
