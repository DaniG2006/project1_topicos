import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class Project1 {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }

    private static void styleButton(JButton button, Color bg, Color fg) {
        button.setBackground(bg);
        button.setForeground(fg);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    static class LoginFrame extends JFrame {
        private final JTextField txtUsuario = new JTextField();
        private final JPasswordField txtPassword = new JPasswordField();

        public LoginFrame() {
            setTitle("HOGARWOOD - Registro de acceso");
            setSize(1100, 650);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setResizable(false);
            getContentPane().setBackground(new Color(244, 239, 233));

            JPanel root = new JPanel(new BorderLayout());
            root.setBackground(new Color(244, 239, 233));

            JPanel left = new JPanel();
            left.setBackground(new Color(255, 248, 241));
            left.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            left.setPreferredSize(new Dimension(500, 0));
            left.setLayout(new BorderLayout());

            JPanel leftCard = new JPanel();
            leftCard.setBackground(new Color(216, 212, 206));
            leftCard.setBorder(BorderFactory.createLineBorder(new Color(131, 96, 65), 2));
            leftCard.setLayout(new BorderLayout());

            JLabel icon = new JLabel("🏠", SwingConstants.CENTER);
            icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 70));
            icon.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

            JLabel title = new JLabel("HOGARWOOD");
            title.setFont(new Font("Arial", Font.BOLD, 36));
            title.setHorizontalAlignment(SwingConstants.CENTER);
            title.setForeground(new Color(78, 47, 28));

            JLabel subtitle = new JLabel("MUEBLERÍA");
            subtitle.setFont(new Font("Arial", Font.BOLD, 18));
            subtitle.setHorizontalAlignment(SwingConstants.CENTER);
            subtitle.setForeground(new Color(113, 86, 67));

            JPanel leftText = new JPanel();
            leftText.setOpaque(false);
            leftText.setLayout(new GridLayout(3, 1));
            leftText.add(title);
            leftText.add(subtitle);

            JLabel livingRoom = new JLabel("◧", SwingConstants.CENTER);
            livingRoom.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 150));
            livingRoom.setForeground(new Color(149, 101, 56));

            leftCard.add(icon, BorderLayout.NORTH);
            leftCard.add(leftText, BorderLayout.CENTER);
            leftCard.add(livingRoom, BorderLayout.SOUTH);
            left.add(leftCard, BorderLayout.CENTER);

            JPanel formPanel = new JPanel();
            formPanel.setBackground(new Color(250, 246, 241));
            formPanel.setLayout(new BorderLayout());
            formPanel.setBorder(BorderFactory.createEmptyBorder(40, 50, 30, 50));

            JPanel labelPanel = new JPanel(new GridLayout(2, 1, 0, 10));
            labelPanel.setOpaque(false);

            JLabel lblLogin = new JLabel("Iniciar sesión");
            lblLogin.setFont(new Font("Arial", Font.BOLD, 28));
            lblLogin.setForeground(new Color(61, 45, 33));

            JLabel lblSubtitle = new JLabel("Accede a tu cuenta para continuar");
            lblSubtitle.setForeground(new Color(118, 95, 74));
            lblSubtitle.setFont(new Font("Arial", Font.PLAIN, 14));

            labelPanel.add(lblLogin);
            labelPanel.add(lblSubtitle);

            JPanel fields = new JPanel();
            fields.setOpaque(false);
            fields.setLayout(new GridLayout(4, 1, 0, 18));

            JLabel lblUsuario = new JLabel("Usuario");
            lblUsuario.setFont(new Font("Arial", Font.BOLD, 12));
            txtUsuario.setFont(new Font("Arial", Font.PLAIN, 15));
            txtUsuario.setPreferredSize(new Dimension(0, 42));
            txtUsuario.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(178, 167, 157)),
                    BorderFactory.createEmptyBorder(5, 10, 5, 10)
            ));

            JLabel lblPassword = new JLabel("Contraseña");
            lblPassword.setFont(new Font("Arial", Font.BOLD, 12));
            txtPassword.setFont(new Font("Arial", Font.PLAIN, 15));
            txtPassword.setPreferredSize(new Dimension(0, 42));
            txtPassword.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(178, 167, 157)),
                    BorderFactory.createEmptyBorder(5, 10, 5, 10)
            ));

            JButton btnIngresar = new JButton("Ingresar");
            btnIngresar.setFont(new Font("Arial", Font.BOLD, 18));
            styleButton(btnIngresar, new Color(83, 52, 36), Color.WHITE);
            btnIngresar.addActionListener(e -> ingresar());

            JLabel lblForgot = new JLabel("¿Olvidaste tu contraseña?");
            lblForgot.setHorizontalAlignment(SwingConstants.CENTER);
            lblForgot.setForeground(new Color(86, 61, 44));
            lblForgot.setFont(new Font("Arial", Font.PLAIN, 13));

            fields.add(lblUsuario);
            fields.add(txtUsuario);
            fields.add(lblPassword);
            fields.add(txtPassword);

            formPanel.add(labelPanel, BorderLayout.NORTH);
            formPanel.add(fields, BorderLayout.CENTER);
            formPanel.add(btnIngresar, BorderLayout.SOUTH);

            JPanel footer = new JPanel(new BorderLayout());
            footer.setOpaque(false);
            footer.add(lblForgot, BorderLayout.CENTER);

            root.add(left, BorderLayout.WEST);
            root.add(formPanel, BorderLayout.CENTER);
            root.add(footer, BorderLayout.SOUTH);
            add(root);
        }

        private void ingresar() {
            String usuario = txtUsuario.getText();
            String password = new String(txtPassword.getPassword());

            if (usuario.equalsIgnoreCase("admin") && password.equals("1234")) {
                JOptionPane.showMessageDialog(this, "Acceso correcto");
                new MenuFrame().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Usuario o contraseña incorrectos",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    static class MenuFrame extends JFrame {
        private final Color darkBrown = new Color(74, 42, 27);
        private final Color softBg = new Color(244, 239, 233);

        public MenuFrame() {
            setTitle("HOGARWOOD - Menú principal");
            setSize(1200, 700);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setResizable(false);
            getContentPane().setBackground(softBg);

            JPanel root = new JPanel(new BorderLayout());
            root.setBackground(softBg);

            JPanel topBar = new JPanel(new BorderLayout());
            topBar.setBackground(darkBrown);
            topBar.setBorder(BorderFactory.createEmptyBorder(12, 18, 12, 18));

            JLabel logo = new JLabel("🏠 HOGARWOOD  —  MUEBLERÍA");
            logo.setForeground(Color.WHITE);
            logo.setFont(new Font("Arial", Font.BOLD, 22));

            JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
            userPanel.setOpaque(false);
            JLabel iconUser = new JLabel("👤");
            JLabel userText = new JLabel("Usuario: admin");
            userText.setForeground(Color.WHITE);
            userText.setFont(new Font("Arial", Font.PLAIN, 14));
            JButton btnLogout = new JButton("Cerrar sesión");
            btnLogout.setBackground(new Color(250, 250, 250, 40));
            btnLogout.setForeground(Color.WHITE);
            btnLogout.setBorderPainted(false);
            btnLogout.setFocusPainted(false);
            btnLogout.addActionListener(e -> {
                new LoginFrame().setVisible(true);
                dispose();
            });
            userPanel.add(iconUser);
            userPanel.add(userText);
            userPanel.add(btnLogout);

            topBar.add(logo, BorderLayout.WEST);
            topBar.add(userPanel, BorderLayout.EAST);

            JPanel leftMenu = new JPanel();
            leftMenu.setPreferredSize(new Dimension(220, 0));
            leftMenu.setBackground(new Color(244, 239, 233));
            leftMenu.setBorder(BorderFactory.createEmptyBorder(20, 16, 20, 16));
            leftMenu.setLayout(new BoxLayout(leftMenu, BoxLayout.Y_AXIS));

            String[] items = {"Inicio", "Muebles", "Ventas", "Gastos", "Apartados", "Impuestos", "Inventario", "Reportes"};
            for (String item : items) {
                JButton btn = new JButton(item);
                btn.setAlignmentX(Component.CENTER_ALIGNMENT);
                btn.setMaximumSize(new Dimension(200, 42));
                btn.setBackground(new Color(239, 234, 228));
                btn.setForeground(new Color(61, 45, 33));
                btn.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
                btn.setFocusPainted(false);
                btn.setFont(new Font("Arial", Font.BOLD, 14));
                btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                btn.addActionListener(e -> openModule(item));
                leftMenu.add(Box.createVerticalStrut(10));
                leftMenu.add(btn);
            }

            JPanel content = new JPanel(new BorderLayout());
            content.setBackground(new Color(255, 255, 255));
            content.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

            JLabel bienvenida = new JLabel("Bienvenido, admin");
            bienvenida.setFont(new Font("Arial", Font.BOLD, 28));
            bienvenida.setForeground(new Color(70, 47, 33));
            bienvenida.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

            JPanel cards = new JPanel(new GridLayout(2, 3, 20, 20));
            cards.setOpaque(false);

            Object[][] modules = {
                    {"Muebles", "🛋️", "Tipos y catálogo"},
                    {"Ventas", "🛒", "Registrar y consultar"},
                    {"Gastos", "💸", "Control de gastos"},
                    {"Apartados", "🧾", "Gestionar apartados"},
                    {"Impuestos", "💰", "Cálculo de impuestos"},
                    {"Inventario", "📦", "Productos en stock"}
            };

            for (Object[] m : modules) {
                JPanel card = new JPanel();
                card.setBackground(new Color(247, 242, 238));
                card.setBorder(BorderFactory.createLineBorder(new Color(220, 214, 208), 1));
                card.setLayout(new BorderLayout());
                card.setPreferredSize(new Dimension(180, 130));

                JLabel icon = new JLabel(String.valueOf(m[1]), SwingConstants.CENTER);
                icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 38));
                icon.setForeground(new Color(95, 67, 49));
                icon.setBorder(BorderFactory.createEmptyBorder(18, 0, 0, 0));

                JLabel title = new JLabel(String.valueOf(m[0]), SwingConstants.CENTER);
                title.setFont(new Font("Arial", Font.BOLD, 18));
                title.setForeground(new Color(54, 36, 29));

                JLabel desc = new JLabel(String.valueOf(m[2]), SwingConstants.CENTER);
                desc.setFont(new Font("Arial", Font.PLAIN, 12));
                desc.setForeground(new Color(106, 91, 81));

                JPanel inner = new JPanel(new GridLayout(3, 1));
                inner.setOpaque(false);
                inner.add(title);
                inner.add(desc);

                card.add(icon, BorderLayout.NORTH);
                card.add(inner, BorderLayout.CENTER);
                card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                String action = String.valueOf(m[0]);
                card.addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent e) {
                        openModule(action);
                    }
                });
                cards.add(card);
            }

            content.add(bienvenida, BorderLayout.NORTH);
            content.add(cards, BorderLayout.CENTER);

            root.add(topBar, BorderLayout.NORTH);
            root.add(leftMenu, BorderLayout.WEST);
            root.add(content, BorderLayout.CENTER);
            add(root);
        }

        private void openModule(String name) {
            switch (name) {
                case "Muebles":
                    new MueblesFrame().setVisible(true);
                    break;
                case "Ventas":
                    new VentasFrame().setVisible(true);
                    break;
                case "Gastos":
                    new GastosFrame().setVisible(true);
                    break;
                case "Apartados":
                    new ApartadosFrame().setVisible(true);
                    break;
                case "Impuestos":
                    new ImpuestosFrame().setVisible(true);
                    break;
                case "Inventario":
                    new InventarioFrame().setVisible(true);
                    break;
                case "Reportes":
                    JOptionPane.showMessageDialog(this, "Módulo de reportes en construcción");
                    break;
                default:
                    JOptionPane.showMessageDialog(this, "Sección inicial");
                    break;
            }
        }
    }

    static class MueblesFrame extends JFrame {
        private final DefaultTableModel model = new DefaultTableModel(
                new Object[]{"ID", "Tipo", "Descripción", "Precio"}, 0
        );
        private final JTable table = new JTable(model);
        private final JComboBox<String> cmbTipo = new JComboBox<>(new String[]{
                "Sala", "Comedor", "Recámara", "Oficina", "Infantil"
        });
        private final JTextField txtDescripcion = new JTextField();
        private final JTextField txtPrecio = new JTextField();

        public MueblesFrame() {
            setTitle("Tipos de muebles");
            setSize(900, 430);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLayout(new BorderLayout(15, 15));
            getContentPane().setBackground(new Color(247, 242, 238));

            JPanel form = new JPanel(new GridBagLayout());
            form.setBackground(new Color(255, 255, 255));
            form.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));
            GridBagConstraints gc = new GridBagConstraints();
            gc.insets = new Insets(8, 8, 8, 8);
            gc.fill = GridBagConstraints.HORIZONTAL;

            JLabel title = new JLabel("Tipos de muebles");
            title.setFont(new Font("Arial", Font.BOLD, 26));
            title.setForeground(new Color(71, 48, 35));

            gc.gridx = 0; gc.gridy = 0; gc.gridwidth = 2;
            form.add(title, gc);

            gc.gridwidth = 1;
            gc.gridx = 0; gc.gridy = 1; form.add(new JLabel("Tipo"), gc);
            gc.gridx = 1; gc.gridy = 1; form.add(cmbTipo, gc);

            gc.gridx = 0; gc.gridy = 2; form.add(new JLabel("Descripción"), gc);
            gc.gridx = 1; gc.gridy = 2; form.add(txtDescripcion, gc);

            gc.gridx = 0; gc.gridy = 3; form.add(new JLabel("Precio"), gc);
            gc.gridx = 1; gc.gridy = 3; form.add(txtPrecio, gc);

            JButton btnAgregar = new JButton("Agregar tipo");
            styleButton(btnAgregar, new Color(123, 83, 53), Color.WHITE);
            btnAgregar.addActionListener(e -> agregarMueble());

            gc.gridx = 0; gc.gridy = 4; gc.gridwidth = 2; form.add(btnAgregar, gc);

            JPanel right = new JPanel(new BorderLayout());
            right.setBackground(new Color(252, 250, 247));
            right.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

            table.setRowHeight(28);
            table.setFillsViewportHeight(true);
            JScrollPane scroll = new JScrollPane(table);
            right.add(scroll, BorderLayout.CENTER);

            add(form, BorderLayout.WEST);
            add(right, BorderLayout.CENTER);

            Object[][] initial = {
                    {1, "Sala", "Sofás, sillas, mesas", "$4,500"},
                    {2, "Comedor", "Mesas, sillas, vitrinas", "$6,100"},
                    {3, "Recámara", "Camas, burós, armarios", "$5,300"},
                    {4, "Oficina", "Escritorios, sillas", "$3,700"},
                    {5, "Infantil", "Muebles para niños", "$2,900"}
            };
            for (Object[] row : initial) {
                model.addRow(row);
            }
        }

        private void agregarMueble() {
            String tipo = String.valueOf(cmbTipo.getSelectedItem());
            String descripcion = txtDescripcion.getText().trim();
            String precio = txtPrecio.getText().trim();

            if (descripcion.isEmpty() || precio.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Completa descripción y precio");
                return;
            }

            try {
                Double.parseDouble(precio.replace("$", "").replace(",", ""));
                model.addRow(new Object[]{model.getRowCount() + 1, tipo, descripcion, "$" + precio});
                txtDescripcion.setText("");
                txtPrecio.setText("");
                JOptionPane.showMessageDialog(this, "Mueble agregado correctamente");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Ingrese un precio válido", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    static class VentasFrame extends JFrame {
        public VentasFrame() {
            setTitle("Ventas");
            setSize(950, 430);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLayout(new BorderLayout(15, 15));
            getContentPane().setBackground(new Color(248, 247, 244));

            JPanel form = new JPanel(new GridBagLayout());
            form.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
            GridBagConstraints c = new GridBagConstraints();
            c.insets = new Insets(8, 8, 8, 8);
            c.fill = GridBagConstraints.HORIZONTAL;

            JLabel title = new JLabel("Registro de ventas");
            title.setFont(new Font("Arial", Font.BOLD, 26));
            c.gridx = 0; c.gridy = 0; c.gridwidth = 2; form.add(title, c);

            c.gridwidth = 1;
            c.gridx = 0; c.gridy = 1; form.add(new JLabel("Fecha:"), c);
            JTextField txtFecha = new JTextField("2025-06-01");
            c.gridx = 1; form.add(txtFecha, c);

            c.gridx = 0; c.gridy = 2; form.add(new JLabel("Cliente:"), c);
            JComboBox<String> cmbCliente = new JComboBox<>(new String[]{"Seleccionar cliente", "Ana López", "Juan Pérez", "Maria García"});
            c.gridx = 1; form.add(cmbCliente, c);

            c.gridx = 0; c.gridy = 3; form.add(new JLabel("Producto:"), c);
            JComboBox<String> cmbProducto = new JComboBox<>(new String[]{"Sofá", "Mesa", "Silla", "Escritorio"});
            c.gridx = 1; form.add(cmbProducto, c);

            c.gridx = 0; c.gridy = 4; form.add(new JLabel("Cantidad:"), c);
            JTextField txtCantidad = new JTextField("1");
            c.gridx = 1; form.add(txtCantidad, c);

            c.gridx = 0; c.gridy = 5; form.add(new JLabel("Precio unitario:"), c);
            JTextField txtPrecio = new JTextField("$5,000");
            c.gridx = 1; form.add(txtPrecio, c);

            JButton btnGuardar = new JButton("Guardar");
            styleButton(btnGuardar, new Color(95, 65, 43), Color.WHITE);
            c.gridx = 0; c.gridy = 6; c.gridwidth = 2; form.add(btnGuardar, c);

            DefaultTableModel tableModel = new DefaultTableModel(new Object[]{"Cliente", "Fecha", "Total", "Estado"}, 0);
            tableModel.addRow(new Object[]{"Ana López", "2025-06-20", "$3,000", "Pagado"});
            tableModel.addRow(new Object[]{"Juan Pérez", "2025-06-22", "$5,500", "Pendiente"});
            JTable table = new JTable(tableModel);

            JPanel right = new JPanel(new BorderLayout());
            right.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
            right.add(new JScrollPane(table), BorderLayout.CENTER);

            add(form, BorderLayout.WEST);
            add(right, BorderLayout.CENTER);

            btnGuardar.addActionListener(e -> {
                try {
                    double cantidad = Double.parseDouble(txtCantidad.getText());
                    String unit = txtPrecio.getText().replace("$", "").replace(",", "");
                    double precioUnitario = Double.parseDouble(unit);
                    double total = cantidad * precioUnitario;
                    tableModel.addRow(new Object[]{cmbCliente.getSelectedItem(), txtFecha.getText(), "$" + new DecimalFormat("#,##0.00").format(total), "Registrado"});
                    JOptionPane.showMessageDialog(this, "Venta registrada correctamente");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Verifica los datos de la venta", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
        }
    }

    static class GastosFrame extends JFrame {
        public GastosFrame() {
            setTitle("Gastos");
            setSize(900, 420);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLayout(new BorderLayout(15, 15));

            JPanel form = new JPanel(new GridBagLayout());
            form.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));
            GridBagConstraints c = new GridBagConstraints();
            c.insets = new Insets(8, 8, 8, 8);
            c.fill = GridBagConstraints.HORIZONTAL;

            JLabel title = new JLabel("Registro de gastos");
            title.setFont(new Font("Arial", Font.BOLD, 26));
            c.gridx = 0; c.gridy = 0; c.gridwidth = 2; form.add(title, c);

            c.gridwidth = 1;
            c.gridx = 0; c.gridy = 1; form.add(new JLabel("Fecha:"), c);
            JTextField txtFecha = new JTextField("2025-06-01");
            c.gridx = 1; form.add(txtFecha, c);

            c.gridx = 0; c.gridy = 2; form.add(new JLabel("Concepto:"), c);
            JComboBox<String> cmbConcepto = new JComboBox<>(new String[]{"Seleccionar", "Luz", "Agua", "Internet", "Transporte", "Nomina"});
            c.gridx = 1; form.add(cmbConcepto, c);

            c.gridx = 0; c.gridy = 3; form.add(new JLabel("Monto:"), c);
            JTextField txtMonto = new JTextField("0.00");
            c.gridx = 1; form.add(txtMonto, c);

            c.gridx = 0; c.gridy = 4; form.add(new JLabel("Descripción:"), c);
            JTextArea txtDescripcion = new JTextArea(4, 18);
            c.gridx = 1; form.add(new JScrollPane(txtDescripcion), c);

            JButton btnGuardar = new JButton("Guardar");
            styleButton(btnGuardar, new Color(95, 65, 43), Color.WHITE);
            c.gridx = 0; c.gridy = 5; c.gridwidth = 2; form.add(btnGuardar, c);

            DefaultTableModel model = new DefaultTableModel(new Object[]{"Fecha", "Concepto", "Monto", "Descripción"}, 0);
            model.addRow(new Object[]{"2025-06-05", "Luz", "$520.00", "Consumo mensual"});
            JTable table = new JTable(model);

            add(form, BorderLayout.WEST);
            add(new JScrollPane(table), BorderLayout.CENTER);

            btnGuardar.addActionListener(e -> {
                try {
                    double monto = Double.parseDouble(txtMonto.getText());
                    model.addRow(new Object[]{txtFecha.getText(), cmbConcepto.getSelectedItem(), "$" + monto, txtDescripcion.getText()});
                    JOptionPane.showMessageDialog(this, "Gasto guardado");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Monto inválido", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
        }
    }

    static class ApartadosFrame extends JFrame {
        public ApartadosFrame() {
            setTitle("Apartados");
            setSize(1000, 430);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLayout(new BorderLayout(15, 15));

            JPanel form = new JPanel(new GridBagLayout());
            form.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));
            GridBagConstraints c = new GridBagConstraints();
            c.insets = new Insets(8, 8, 8, 8);
            c.fill = GridBagConstraints.HORIZONTAL;

            JLabel title = new JLabel("Apartados de clientes");
            title.setFont(new Font("Arial", Font.BOLD, 26));
            c.gridx = 0; c.gridy = 0; c.gridwidth = 2; form.add(title, c);

            c.gridwidth = 1;
            c.gridx = 0; c.gridy = 1; form.add(new JLabel("Cliente:"), c);
            JTextField txtCliente = new JTextField("Ana López");
            c.gridx = 1; form.add(txtCliente, c);

            c.gridx = 0; c.gridy = 2; form.add(new JLabel("Monto:"), c);
            JTextField txtMonto = new JTextField("$3,000");
            c.gridx = 1; form.add(txtMonto, c);

            c.gridx = 0; c.gridy = 3; form.add(new JLabel("Fecha:"), c);
            JTextField txtFecha = new JTextField("2025-06-20");
            c.gridx = 1; form.add(txtFecha, c);

            JButton btnAgregar = new JButton("Nuevo apartado");
            styleButton(btnAgregar, new Color(95, 65, 43), Color.WHITE);
            c.gridx = 0; c.gridy = 4; c.gridwidth = 2; form.add(btnAgregar, c);

            DefaultTableModel model = new DefaultTableModel(new Object[]{"ID", "Cliente", "Monto", "Fecha"}, 0);
            model.addRow(new Object[]{1, "Ana López", "$3,000", "2025-06-20"});
            model.addRow(new Object[]{2, "Juan Pérez", "$5,500", "2025-06-22"});
            model.addRow(new Object[]{3, "Maria García", "$4,000", "2025-06-25"});
            JTable table = new JTable(model);

            add(form, BorderLayout.WEST);
            add(new JScrollPane(table), BorderLayout.CENTER);

            btnAgregar.addActionListener(e -> model.addRow(new Object[]{model.getRowCount() + 1, txtCliente.getText(), txtMonto.getText(), txtFecha.getText()}));
        }
    }

    static class ImpuestosFrame extends JFrame {
        public ImpuestosFrame() {
            setTitle("Impuestos");
            setSize(700, 350);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLayout(new BorderLayout(15, 15));

            JPanel panel = new JPanel(new GridBagLayout());
            panel.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));
            GridBagConstraints c = new GridBagConstraints();
            c.insets = new Insets(8, 8, 8, 8);
            c.fill = GridBagConstraints.HORIZONTAL;

            JLabel title = new JLabel("Cálculo de impuestos");
            title.setFont(new Font("Arial", Font.BOLD, 26));
            c.gridx = 0; c.gridy = 0; c.gridwidth = 2; panel.add(title, c);

            c.gridwidth = 1;
            c.gridx = 0; c.gridy = 1; panel.add(new JLabel("Subtotal:"), c);
            JTextField txtSubtotal = new JTextField("10000");
            c.gridx = 1; panel.add(txtSubtotal, c);

            c.gridx = 0; c.gridy = 2; panel.add(new JLabel("IVA (%):"), c);
            JTextField txtIva = new JTextField("16");
            c.gridx = 1; panel.add(txtIva, c);

            JButton btnCalcular = new JButton("Calcular");
            styleButton(btnCalcular, new Color(95, 65, 43), Color.WHITE);
            c.gridx = 0; c.gridy = 3; c.gridwidth = 2; panel.add(btnCalcular, c);

            JLabel lblResultado = new JLabel("Total: $0.00");
            lblResultado.setFont(new Font("Arial", Font.BOLD, 20));
            lblResultado.setForeground(new Color(67, 45, 32));
            c.gridx = 0; c.gridy = 4; c.gridwidth = 2; panel.add(lblResultado, c);

            add(panel, BorderLayout.CENTER);

            btnCalcular.addActionListener(e -> {
                try {
                    double subtotal = Double.parseDouble(txtSubtotal.getText());
                    double iva = Double.parseDouble(txtIva.getText()) / 100;
                    double total = subtotal + (subtotal * iva);
                    lblResultado.setText("Total: $" + new DecimalFormat("#,##0.00").format(total));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Valores inválidos", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
        }
    }

    static class InventarioFrame extends JFrame {
        public InventarioFrame() {
            setTitle("Inventario");
            setSize(800, 400);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLayout(new BorderLayout(15, 15));

            JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
            top.setBorder(BorderFactory.createEmptyBorder(12, 12, 0, 12));
            JLabel title = new JLabel("Inventario");
            title.setFont(new Font("Arial", Font.BOLD, 26));
            top.add(title);

            DefaultTableModel model = new DefaultTableModel(new Object[]{"Producto", "Stock", "Estado"}, 0);
            model.addRow(new Object[]{"Sofá", 10, "Disponible"});
            model.addRow(new Object[]{"Mesa", 5, "Disponible"});
            model.addRow(new Object[]{"Silla", 0, "Agotado"});
            model.addRow(new Object[]{"Escritorio", 3, "Disponible"});
            model.addRow(new Object[]{"Cama", 8, "Disponible"});
            JTable table = new JTable(model);

            JButton btnActualizar = new JButton("Actualizar");
            styleButton(btnActualizar, new Color(95, 65, 43), Color.WHITE);
            btnActualizar.addActionListener(e -> {
                model.setValueAt("Disponible", 2, 2);
                JOptionPane.showMessageDialog(this, "Inventario actualizado");
            });
            top.add(btnActualizar);

            add(top, BorderLayout.NORTH);
            add(new JScrollPane(table), BorderLayout.CENTER);
        }
    }
}