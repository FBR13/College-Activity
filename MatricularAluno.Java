import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AplicacaoSwing {

    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel mainPanel;

    private static final String DB_URL = "jdbc:sqlite:alunos.db";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AplicacaoSwing().iniciarAplicacao());
    }

    private void iniciarAplicacao() {
        inicializarBanco();
        frame = new JFrame("Aplicação Java Swing");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(criarMenuPanel(), "Menu");
        mainPanel.add(criarCadastroAlunoPanel(), "CadastroAluno");
        mainPanel.add(criarVisualizarPanel(), "Visualizar");

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private void inicializarBanco() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            String sqlAlunos = "CREATE TABLE IF NOT EXISTS Alunos (" +
                               "matricula TEXT PRIMARY KEY," +
                               "nome TEXT NOT NULL," +
                               "data_nascimento TEXT NOT NULL," +
                               "cpf TEXT NOT NULL)";
            stmt.execute(sqlAlunos);

            String sqlMaterias = "CREATE TABLE IF NOT EXISTS Materias (" +
                                 "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                 "matricula_aluno TEXT NOT NULL," +
                                 "materia TEXT NOT NULL," +
                                 "FOREIGN KEY (matricula_aluno) REFERENCES Alunos (matricula))";
            stmt.execute(sqlMaterias);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao inicializar o banco de dados: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JPanel criarMenuPanel() {
        JPanel menuPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        JLabel menuLabel = new JLabel("Menu Principal", JLabel.CENTER);

        JButton btnCadastroAluno = new JButton("Cadastro de Aluno");
        JButton btnVisualizarAlunos = new JButton("Visualizar Alunos");

        btnCadastroAluno.addActionListener(e -> trocarTela("CadastroAluno"));
        btnVisualizarAlunos.addActionListener(e -> trocarTela("Visualizar"));

        menuPanel.add(menuLabel);
        menuPanel.add(btnCadastroAluno);
        menuPanel.add(btnVisualizarAlunos);

        return menuPanel;
    }

    private JPanel criarCadastroAlunoPanel() {
        JPanel cadastroAlunoPanel = new JPanel(new BorderLayout());

        JPanel camposPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        JLabel lblMatricula = new JLabel("Matrícula:");
        JTextField txtMatricula = new JTextField();

        JLabel lblNome = new JLabel("Nome:");
        JTextField txtNome = new JTextField();

        JLabel lblDataNascimento = new JLabel("Data de Nascimento:");
        JTextField txtDataNascimento = new JTextField();

        JLabel lblCpf = new JLabel("CPF:");
        JTextField txtCpf = new JTextField();

        camposPanel.add(lblMatricula);
        camposPanel.add(txtMatricula);
        camposPanel.add(lblNome);
        camposPanel.add(txtNome);
        camposPanel.add(lblDataNascimento);
        camposPanel.add(txtDataNascimento);
        camposPanel.add(lblCpf);
        camposPanel.add(txtCpf);

        JPanel materiasPanel = new JPanel(new GridLayout(5, 2));
        JLabel lblMaterias = new JLabel("Selecione até 5 matérias:");
        JCheckBox[] checkboxes = {
                new JCheckBox("Português"), new JCheckBox("Matemática"),
                new JCheckBox("Inglês"), new JCheckBox("História"),
                new JCheckBox("Geografia"), new JCheckBox("Física"),
                new JCheckBox("Química"), new JCheckBox("Biologia"),
                new JCheckBox("Artes"), new JCheckBox("Educação Física")
        };

        for (JCheckBox checkbox : checkboxes) {
            materiasPanel.add(checkbox);
        }

        JButton btnSalvarAluno = new JButton("Salvar");
        JButton btnVoltarAluno = new JButton("Voltar");

        btnSalvarAluno.addActionListener(e -> salvarAluno(txtMatricula, txtNome, txtDataNascimento, txtCpf, checkboxes));
        btnVoltarAluno.addActionListener(e -> trocarTela("Menu"));

        cadastroAlunoPanel.add(camposPanel, BorderLayout.NORTH);
        cadastroAlunoPanel.add(lblMaterias, BorderLayout.CENTER);
        cadastroAlunoPanel.add(materiasPanel, BorderLayout.CENTER);
        JPanel botoesPanel = new JPanel();
        botoesPanel.add(btnSalvarAluno);
        botoesPanel.add(btnVoltarAluno);
        cadastroAlunoPanel.add(botoesPanel, BorderLayout.SOUTH);

        return cadastroAlunoPanel;
    }

    private JPanel criarVisualizarPanel() {
        JPanel visualizarPanel = new JPanel(new BorderLayout());

        JTextArea txtVisualizar = new JTextArea();
        txtVisualizar.setEditable(false);

        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> trocarTela("Menu"));

        visualizarPanel.add(new JScrollPane(txtVisualizar), BorderLayout.CENTER);
        visualizarPanel.add(btnVoltar, BorderLayout.SOUTH);

        visualizarPanel.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent event) {
                txtVisualizar.setText(buscarAlunos());
            }

            public void ancestorRemoved(javax.swing.event.AncestorEvent event) {}

            public void ancestorMoved(javax.swing.event.AncestorEvent event) {}
        });

        return visualizarPanel;
    }

    private void salvarAluno(JTextField txtMatricula, JTextField txtNome, JTextField txtDataNascimento, JTextField txtCpf, JCheckBox[] checkboxes) {
        String matricula = txtMatricula.getText();
        String nome = txtNome.getText();
        String dataNascimento = txtDataNascimento.getText();
        String cpf = txtCpf.getText();

        List<String> materiasSelecionadas = new ArrayList<>();
        for (JCheckBox checkbox : checkboxes) {
            if (checkbox.isSelected()) {
                materiasSelecionadas.add(checkbox.getText());
            }
        }

        if (matricula.isEmpty() || nome.isEmpty() || dataNascimento.isEmpty() || cpf.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Por favor, preencha todos os campos.", "Erro", JOptionPane.ERROR_MESSAGE);
        } else if (materiasSelecionadas.size() > 5) {
            JOptionPane.showMessageDialog(frame, "Selecione no máximo 5 matérias.", "Erro", JOptionPane.ERROR_MESSAGE);
        } else {
            try (Connection conn = DriverManager.getConnection(DB_URL)) {
                conn.setAutoCommit(false);

                try (PreparedStatement pstmtAluno = conn.prepareStatement("INSERT INTO Alunos VALUES (?, ?, ?, ?)")) {
                    pstmtAluno.setString(1, matricula);
                    pstmtAluno.setString(2, nome);
                    pstmtAluno.setString(3, dataNascimento);
                    pstmtAluno.setString(4, cpf);
                    pstmtAluno.executeUpdate();
                }

                try (PreparedStatement pstmtMateria = conn.prepareStatement("INSERT INTO Materias (matricula_aluno, materia) VALUES (?, ?)")) {
                    for (String materia : materiasSelecionadas) {
                        pstmtMateria.setString(1, matricula);
                        pstmtMateria.setString(2, materia);
                        pstmtMateria.executeUpdate();
                    }
                }

                conn.commit();
                JOptionPane.showMessageDialog(frame, "Aluno salvo com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

                txtMatricula.setText("");
                txtNome.setText("");
                txtDataNascimento.setText("");
                txtCpf.setText("");
                for (JCheckBox checkbox : checkboxes) {
                    checkbox.setSelected(false);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Erro ao salvar o aluno: " + e.getMessage(),
                        "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private String buscarAlunos() {
        StringBuilder resultado = new StringBuilder();
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            ResultSet rsAlunos = stmt.executeQuery("SELECT * FROM Alunos");

            while (rsAlunos.next()) {
                String matricula = rsAlunos.getString("matricula");
                String nome = rsAlunos.getString("nome");
                String dataNascimento = rsAlunos.getString("data_nascimento");
                String cpf = rsAlunos.getString("cpf");

                resultado.append("Matrícula: ").append(matricula)
                        .append("\nNome: ").append(nome)
                        .append("\nData de Nascimento: ").append(dataNascimento)
                        .append("\nCPF: ").append(cpf)
                        .append("\nMatérias: ");

                ResultSet rsMaterias = stmt.executeQuery("SELECT materia FROM Materias WHERE matricula_aluno = '" + matricula + "'");
                while (rsMaterias.next()) {
                    resultado.append(rsMaterias.getString("materia")).append(", ");
                }
                resultado.append("\n\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Erro ao buscar alunos: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
        return resultado.toString();
    }

    private void trocarTela(String tela) {
        cardLayout.show(mainPanel, tela);
    }
}
