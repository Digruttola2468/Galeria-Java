import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

public class Principales extends JFrame implements ActionListener{

    private JFileChooser fileChooser;
    private JPanel jPanel;
    private JLabel lb_label;
    private JButton jbNext,jbBack;


    private ArrayList<File> ficheros;
    private int contador = 0;

    public Principales() {
        setLayout(new BorderLayout());
        setSize(200,200);
        setLocationRelativeTo(null);
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Galeria");
        setIconImage(new ImageIcon(getClass().getResource("./img/imagelogo.png")).getImage());

        JButton button = new JButton("File Choose");
        button.setActionCommand("command");
        button.addActionListener(this);
        add(button, BorderLayout.PAGE_START);

        jbNext = new JButton(">>>");
        jbNext.setActionCommand("next");
        jbNext.addActionListener(this);

        jbBack = new JButton("<<<");
        jbBack.setActionCommand("back");
        jbBack.addActionListener(this);

        fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        panel.add(jbBack);
        panel.add(jbNext);

        add(panel,BorderLayout.PAGE_END);

        lb_label = new JLabel();

        jPanel = new JPanel();
        jPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        jPanel.add( lb_label );
        add(jPanel, BorderLayout.CENTER);

        enableButtons(false);
    }

    @Override
	public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("command")){
            int valresult = fileChooser.showOpenDialog(this);
            if(valresult == JFileChooser.APPROVE_OPTION){
                File file = fileChooser.getSelectedFile();

                Stream<File> archivoF = Stream.of(file.listFiles());
                Stream<File> archivosFilter = archivoF.filter(File::isFile);

                ficheros = new ArrayList<>();
                archivosFilter.forEach(ficheros::add);
                loadImage( ficheros );

                this.setVisible(false);
                this.setVisible(true);

                enableButtons(true);

                this.setSize(500,500);
            }   
        }
        if(e.getActionCommand().equals("back")){
            contador--;
            if(contador < 0)
                contador = ficheros.size() - 1;
            loadImage(ficheros);
        }
        if(e.getActionCommand().equals("next")){
            contador++;
            if(contador == ficheros.size())
                contador = 0;
            loadImage(ficheros);
        }
	}
    private void loadImage(ArrayList<File> rutas){
        Icon imageIcon = new ImageIcon(rutas.get(contador).getAbsolutePath());
        lb_label.setIcon(imageIcon);
        this.repaint();
    }

    private void enableButtons(boolean accept){
        jbNext.setEnabled(accept);
        jbBack.setEnabled(accept);
    }


    public static void main(String[] args) {
        new Principales().setVisible(true);
    }

} 