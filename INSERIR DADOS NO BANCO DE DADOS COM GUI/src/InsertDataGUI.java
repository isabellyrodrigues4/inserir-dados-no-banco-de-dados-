import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class InsertDataGUI {

    private JFrame frame;
    private JTextField nameField, emailField;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InsertDataGUI().createGUI());
    }

    public void createGUI() {
        frame = new JFrame("Inserir Dados no Banco");
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(3, 2));

        JLabel nameLabel = new JLabel("Nome:");
        nameField = new JTextField();

        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField();

        JButton insertButton = new JButton("Inserir");
        insertButton.addActionListener(e -> insertData());

        frame.add(nameLabel);
        frame.add(nameField);
        frame.add(emailLabel);
        frame.add(emailField);
        frame.add(insertButton);

        frame.setVisible(true);
    }

    public void insertData() {
        String name = nameField.getText();
        String email = emailField.getText();

        if (name.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(frame,
                    "Preencha todos os campos!",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 🔥 CONEXÃO CORRETA COM O SEU BANCO:
        String url = "jdbc:mysql://localhost:3306/inserir_gui?useSSL=false&serverTimezone=UTC";
        String user = "root";   // coloque seu usuário
        String password = "";    // se usar XAMPP, normalmente é vazio ""

        String sql = "INSERT INTO users (name, email) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setString(2, email);

            int result = pstmt.executeUpdate();

            if (result > 0) {
                JOptionPane.showMessageDialog(frame,
                        "Dados inseridos com sucesso!",
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame,
                    "Erro ao conectar ou inserir no banco:\n" + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}

