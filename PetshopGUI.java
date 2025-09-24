import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Optional;

public class PetshopGUI {

    private GerenciadorPetshop petshopService;
    private JFrame janela;
    private JTextArea areaResultados;

    // Campos de texto criados uma única vez
    private JTextField campoNome, campoEspecie, campoRaca, campoIdade;
    private JTextField campoNomeDono, campoTelefoneDono;
    private JTextField campoDescricaoServico, campoValorServico;
    private JTextField campoPesquisaRemocao;
    private JTextField campoNomeInternamento, campoMotivoInternamento, campoDataInternamento, campoObservacoesInternamento;
    private JTextField campoNomePlanoAnimal, campoNomePlano, campoContratoPlano;

    public PetshopGUI() {
        this.petshopService = new PetshopService();
        try {
            petshopService.carregarDados();
        } catch (IOException | ClassNotFoundException ex) {
            System.err.println("Erro ao carregar dados. Iniciando com lista vazia.");
        }

        // Inicialização dos campos de texto (criados uma única vez)
        campoNome = new JTextField(15);
        campoEspecie = new JTextField(15);
        campoRaca = new JTextField(15);
        campoIdade = new JTextField(15);
        campoNomeDono = new JTextField(15);
        campoTelefoneDono = new JTextField(15);
        campoDescricaoServico = new JTextField(15);
        campoValorServico = new JTextField(15);
        campoPesquisaRemocao = new JTextField(20);
        campoNomeInternamento = new JTextField(15);
        campoMotivoInternamento = new JTextField(15);
        campoDataInternamento = new JTextField(15);
        campoObservacoesInternamento = new JTextField(15);
        campoNomePlanoAnimal = new JTextField(15);
        campoNomePlano = new JTextField(15);
        campoContratoPlano = new JTextField(15);

        janela = new JFrame("Pet's - Gerenciador de Animais");
        janela.setSize(800, 600);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setLocationRelativeTo(null);

        criarLayout();

        janela.setVisible(true);
    }

    private void criarLayout() {
        JPanel painelPrincipal = new JPanel(new BorderLayout());
        painelPrincipal.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel titulo = new JLabel("Bem-vindo ao Pet's", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(new Color(0, 102, 204));
        painelPrincipal.add(titulo, BorderLayout.NORTH);

        JTabbedPane abas = new JTabbedPane();

        abas.addTab("Cadastro", criarPainelCadastro());
        abas.addTab("Pesquisa/Remoção", criarPainelPesquisa());
        abas.addTab("Internamento", criarPainelInternamento());
        abas.addTab("Plano de Saúde", criarPainelPlanoSaude());
        abas.addTab("Listar Todos", criarPainelListar());
        abas.addTab("Suporte", criarPainelSuporte());

        painelPrincipal.add(abas, BorderLayout.CENTER);

        janela.add(painelPrincipal);
    }

    private JPanel criarPainelCadastro() {
        JPanel painel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Criação e organização dos campos de entrada
        String[] labels = {"Nome:", "Espécie:", "Raça:", "Idade:", "Nome do Dono:", "Telefone do Dono:", "Serviço:", "Valor do Serviço:"};
        JTextField[] campos = {campoNome, campoEspecie, campoRaca, campoIdade, campoNomeDono, campoTelefoneDono, campoDescricaoServico, campoValorServico};

        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0; gbc.gridy = i;
            painel.add(new JLabel(labels[i]), gbc);
            gbc.gridx = 1;
            painel.add(campos[i], gbc);
        }

        JButton btnCadastrar = new JButton("Cadastrar Animal");
        btnCadastrar.addActionListener(this::cadastrarAnimal);
        gbc.gridx = 0; gbc.gridy = labels.length;
        gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        painel.add(btnCadastrar, gbc);

        return painel;
    }

    private JPanel criarPainelPesquisa() {
        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel painelBusca = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        JButton btnPesquisar = new JButton("Pesquisar");
        JButton btnRemover = new JButton("Remover");

        btnPesquisar.addActionListener(this::pesquisarAnimal);
        btnRemover.addActionListener(this::removerAnimal);

        painelBusca.add(new JLabel("Nome do Animal:"));
        painelBusca.add(campoPesquisaRemocao);
        painelBusca.add(btnPesquisar);
        painelBusca.add(btnRemover);

        areaResultados = new JTextArea();
        areaResultados.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(areaResultados);

        painel.add(painelBusca, BorderLayout.NORTH);
        painel.add(scrollPane, BorderLayout.CENTER);

        return painel;
    }

    private JPanel criarPainelInternamento() {
        JPanel painel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0; painel.add(new JLabel("Nome do Animal:"), gbc);
        gbc.gridx = 1; painel.add(campoNomeInternamento, gbc);
        gbc.gridx = 0; gbc.gridy = 1; painel.add(new JLabel("Motivo:"), gbc);
        gbc.gridx = 1; painel.add(campoMotivoInternamento, gbc);
        gbc.gridx = 0; gbc.gridy = 2; painel.add(new JLabel("Data (AAAA-MM-DD):"), gbc);
        gbc.gridx = 1; painel.add(campoDataInternamento, gbc);
        gbc.gridx = 0; gbc.gridy = 3; painel.add(new JLabel("Observações:"), gbc);
        gbc.gridx = 1; painel.add(campoObservacoesInternamento, gbc);

        JButton btnRegistrarInternamento = new JButton("Registrar Internamento");
        btnRegistrarInternamento.addActionListener(this::registrarInternamento);
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        painel.add(btnRegistrarInternamento, gbc);

        JButton btnVerHistorico = new JButton("Ver Histórico de Internamento");
        btnVerHistorico.addActionListener(this::verHistoricoInternamento);
        gbc.gridy = 5;
        painel.add(btnVerHistorico, gbc);

        return painel;
    }

    private JPanel criarPainelPlanoSaude() {
        JPanel painel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0; painel.add(new JLabel("Nome do Animal:"), gbc);
        gbc.gridx = 1; painel.add(campoNomePlanoAnimal, gbc);
        gbc.gridx = 0; gbc.gridy = 1; painel.add(new JLabel("Nome do Plano:"), gbc);
        gbc.gridx = 1; painel.add(campoNomePlano, gbc);
        gbc.gridx = 0; gbc.gridy = 2; painel.add(new JLabel("Nº do Contrato:"), gbc);
        gbc.gridx = 1; painel.add(campoContratoPlano, gbc);

        JCheckBox checkBoxAtivo = new JCheckBox("Plano Ativo");
        checkBoxAtivo.setSelected(true);
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2; painel.add(checkBoxAtivo, gbc);

        JButton btnRegistrarPlano = new JButton("Registrar Plano de Saúde");
        btnRegistrarPlano.addActionListener(e -> registrarPlanoSaude(checkBoxAtivo.isSelected()));
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        painel.add(btnRegistrarPlano, gbc);

        return painel;
    }

    private JPanel criarPainelListar() {
        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBorder(new EmptyBorder(10, 10, 10, 10));

        areaResultados = new JTextArea();
        areaResultados.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(areaResultados);

        JButton btnListar = new JButton("Listar Todos os Animais");
        btnListar.addActionListener(this::listarAnimais);

        painel.add(btnListar, BorderLayout.NORTH);
        painel.add(scrollPane, BorderLayout.CENTER);

        JButton btnSalvar = new JButton("Salvar Dados");
        btnSalvar.addActionListener(this::salvarDados);
        painel.add(btnSalvar, BorderLayout.SOUTH);

        return painel;
    }

    private JPanel criarPainelSuporte() {
        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel infoSuporte = new JLabel("<html><body>"
            + "<h3>Central de Suporte</h3>"
            + "<p>Para obter ajuda, por favor, utilize os contatos abaixo ou envie uma mensagem.</p>"
            + "<p>Email: suporte@pets.com</p>"
            + "<p>Telefone: (11) 99999-9999</p>"
            + "</body></html>");

        painel.add(infoSuporte, BorderLayout.NORTH);

        JPanel painelMensagem = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        JTextField campoNomeSuporte = new JTextField(20);
        JTextArea areaMensagem = new JTextArea(5, 20);

        gbc.gridx = 0; gbc.gridy = 0; painelMensagem.add(new JLabel("Seu Nome:"), gbc);
        gbc.gridx = 1; painelMensagem.add(campoNomeSuporte, gbc);
        gbc.gridx = 0; gbc.gridy = 1; painelMensagem.add(new JLabel("Mensagem:"), gbc);
        gbc.gridx = 1; painelMensagem.add(new JScrollPane(areaMensagem), gbc);

        JButton btnEnviar = new JButton("Enviar Mensagem");
        btnEnviar.addActionListener(e -> JOptionPane.showMessageDialog(janela, "Mensagem enviada com sucesso para o suporte!", "Suporte", JOptionPane.INFORMATION_MESSAGE));
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        painelMensagem.add(btnEnviar, gbc);

        painel.add(painelMensagem, BorderLayout.CENTER);

        return painel;
    }

    private void cadastrarAnimal(ActionEvent e) {
        try {
            String nome = campoNome.getText();
            String especie = campoEspecie.getText();
            String raca = campoRaca.getText();
            int idade = Integer.parseInt(campoIdade.getText());
            String nomeDono = campoNomeDono.getText();
            String telefoneDono = campoTelefoneDono.getText();
            String descricaoServico = campoDescricaoServico.getText();
            double valorServico = Double.parseDouble(campoValorServico.getText());

            Dono dono = new Dono(nomeDono, telefoneDono);
            Servico servico = new Servico(descricaoServico, valorServico);
            Animal novoAnimal = new Animal(nome, especie, raca, idade, dono, servico);

            petshopService.cadastrarAnimal(novoAnimal);
            JOptionPane.showMessageDialog(janela, "Animal " + nome + " cadastrado com sucesso!");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(janela, "Por favor, insira valores válidos para Idade e Valor.", "Erro de Cadastro", JOptionPane.ERROR_MESSAGE);
        } catch (AnimalJaCadastradoException ex) {
            JOptionPane.showMessageDialog(janela, ex.getMessage(), "Erro de Cadastro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void pesquisarAnimal(ActionEvent e) {
        String nome = campoPesquisaRemocao.getText();
        if (nome != null && !nome.isEmpty()) {
            try {
                Animal animalEncontrado = petshopService.pesquisarAnimal(nome);
                areaResultados.setText("Animal encontrado:\n" + animalEncontrado.toString());
            } catch (AnimalNaoEncontradoException ex) {
                JOptionPane.showMessageDialog(janela, ex.getMessage(), "Animal Não Encontrado", JOptionPane.WARNING_MESSAGE);
                areaResultados.setText("");
            }
        }
    }

    private void removerAnimal(ActionEvent e) {
        String nome = campoPesquisaRemocao.getText();
        if (nome != null && !nome.isEmpty()) {
            try {
                petshopService.removerAnimal(nome);
                JOptionPane.showMessageDialog(janela, "Animal " + nome + " removido com sucesso!");
            } catch (AnimalNaoEncontradoException ex) {
                JOptionPane.showMessageDialog(janela, ex.getMessage(), "Animal Não Encontrado", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private void registrarInternamento(ActionEvent e) {
        try {
            String nomeAnimal = campoNomeInternamento.getText();
            String motivo = campoMotivoInternamento.getText();
            String dataStr = campoDataInternamento.getText();
            String observacoes = campoObservacoesInternamento.getText();

            LocalDate data = LocalDate.parse(dataStr);
            Internamento internamento = new Internamento(motivo, data, observacoes);

            petshopService.registrarInternamento(nomeAnimal, internamento);
            JOptionPane.showMessageDialog(janela, "Internamento registrado com sucesso!");
        } catch (AnimalNaoEncontradoException | DateTimeParseException ex) {
            JOptionPane.showMessageDialog(janela, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void verHistoricoInternamento(ActionEvent e) {
        String nomeAnimal = campoNomeInternamento.getText();
        try {
            String historico = petshopService.getHistoricoInternamentos(nomeAnimal);
            JOptionPane.showMessageDialog(janela, historico, "Histórico de Internamento", JOptionPane.INFORMATION_MESSAGE);
        } catch (AnimalNaoEncontradoException ex) {
            JOptionPane.showMessageDialog(janela, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void registrarPlanoSaude(boolean ativo) {
        try {
            String nomeAnimal = campoNomePlanoAnimal.getText();
            String nomePlano = campoNomePlano.getText();
            String numeroContrato = campoContratoPlano.getText();

            PlanoDeSaude plano = new PlanoDeSaude(nomePlano, numeroContrato, ativo);
            petshopService.registrarPlanoDeSaude(nomeAnimal, plano);
            JOptionPane.showMessageDialog(janela, "Plano de saúde registrado com sucesso!");
        } catch (AnimalNaoEncontradoException ex) {
            JOptionPane.showMessageDialog(janela, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void listarAnimais(ActionEvent e) {
        if (petshopService.listarAnimais().isEmpty()) {
            areaResultados.setText("Não há animais cadastrados.");
        } else {
            StringBuilder sb = new StringBuilder("--- LISTA DE ANIMAIS ---\n\n");
            petshopService.listarAnimais().forEach(animal -> {
                sb.append(animal).append("\n");
                Optional.ofNullable(animal.getPlanoSaude()).ifPresent(plano -> sb.append("   Plano de Saúde: ").append(plano).append("\n"));
                animal.getHistoricoInternamentos().forEach(internamento -> sb.append("   Internamento: ").append(internamento).append("\n"));
                sb.append("\n");
            });
            areaResultados.setText(sb.toString());
        }
    }

    private void salvarDados(ActionEvent e) {
        try {
            petshopService.salvarDados();
            JOptionPane.showMessageDialog(janela, "Dados salvos com sucesso!");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(janela, "Erro ao salvar os dados.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PetshopGUI::new);
    }
}