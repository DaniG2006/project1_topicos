import javax.swing.*; 
import javax.swing.table.DefaultTableModel;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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
        private final JLabel lblLoginError = new JLabel("Usuario o contraseña incorrectos", SwingConstants.CENTER);
        private JPanel loginErrorContainer;
        private javax.swing.Timer loginErrorShakeTimer;
        private int loginErrorOffset;

        public LoginFrame() {
            setTitle("HolmWood - Registro de acceso");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setResizable(true);
                getContentPane().setBackground(new Color(250, 247, 242));

            JPanel root = new JPanel(new BorderLayout());
                root.setBackground(new Color(250, 247, 242));

            JPanel left = new JPanel();
                left.setBackground(new Color(250, 247, 242));
                left.setBorder(BorderFactory.createEmptyBorder());
            left.setPreferredSize(new Dimension(500, 0));
            left.setLayout(new BorderLayout());

            BufferedImage loginImage = loadLoginImage();
            JPanel leftCard = new JPanel(new BorderLayout()) {
                @Override
                protected void paintComponent(Graphics graphics) {
                    super.paintComponent(graphics);
                    Graphics2D g = (Graphics2D) graphics.create();
                    if (loginImage != null) {
                        double scale = Math.max(
                                (double) getWidth() / loginImage.getWidth(),
                                (double) getHeight() / loginImage.getHeight()
                        );
                        int imageWidth = (int) (loginImage.getWidth() * scale);
                        int imageHeight = (int) (loginImage.getHeight() * scale);
                        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                        g.drawImage(loginImage,
                                (getWidth() - imageWidth) / 2,
                                (getHeight() - imageHeight) / 2,
                                imageWidth, imageHeight, this);
                    } else {
                        g.setColor(new Color(112, 91, 70));
                        g.fillRect(0, 0, getWidth(), getHeight());
                    }
                    g.dispose();
                }
            };
            leftCard.setOpaque(false);
            leftCard.setBorder(BorderFactory.createEmptyBorder(18, 20, 18, 20));

            JLabel icon = new JLabel(createFurnitureLogo());
            icon.setPreferredSize(new Dimension(190, 155));
            icon.setMaximumSize(new Dimension(190, 155));
            icon.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel title = new JLabel("HOLMWOOD");
            title.setFont(new Font("Arial", Font.BOLD, 46));
            title.setHorizontalAlignment(SwingConstants.CENTER);
            title.setForeground(new Color(62, 35, 20));
            title.setAlignmentX(Component.CENTER_ALIGNMENT);
            title.setPreferredSize(new Dimension(0, 54));
            title.setMaximumSize(new Dimension(Integer.MAX_VALUE, 54));

            JLabel subtitle = new JLabel("----------- M U E B L E R Í A ------------");
            subtitle.setFont(new Font("Arial", Font.BOLD, 16));
            subtitle.setHorizontalAlignment(SwingConstants.CENTER);
            subtitle.setForeground(new Color(62, 35, 20));
            subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
            subtitle.setPreferredSize(new Dimension(0, 28));
            subtitle.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));

            JLabel brandNote = new JLabel("El arte de amueblar con estilo", SwingConstants.CENTER);
            brandNote.setFont(new Font("Arial", Font.PLAIN, 16));
            brandNote.setForeground(new Color(46, 35, 27));
            brandNote.setAlignmentX(Component.CENTER_ALIGNMENT);
            brandNote.setPreferredSize(new Dimension(0, 28));
            brandNote.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));

            JPanel brandGroup = new JPanel();
            brandGroup.setOpaque(false);
            brandGroup.setLayout(new BoxLayout(brandGroup, BoxLayout.Y_AXIS));
            brandGroup.setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 16));
            brandGroup.add(icon);
            brandGroup.add(Box.createVerticalStrut(6));
            brandGroup.add(title);
            brandGroup.add(Box.createVerticalStrut(3));
            brandGroup.add(subtitle);
            brandGroup.add(Box.createVerticalStrut(6));
            brandGroup.add(brandNote);

            JPanel brandPlate = new JPanel(new BorderLayout()) {
                @Override
                protected void paintComponent(Graphics graphics) {
                    Graphics2D g = (Graphics2D) graphics.create();
                    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g.setColor(new Color(255, 250, 240, 224));
                    g.fillRoundRect(0, 0, getWidth(), getHeight(), 18, 18);
                    g.setColor(new Color(255, 255, 255, 190));
                    g.setStroke(new BasicStroke(1.2f));
                    g.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 18, 18);
                    g.dispose();
                }
            };
            brandPlate.setOpaque(false);
            brandPlate.setPreferredSize(new Dimension(420, 300));
            brandPlate.add(brandGroup, BorderLayout.CENTER);

            JPanel brandHost = new JPanel(new GridBagLayout());
            brandHost.setOpaque(false);
            brandHost.add(brandPlate);
            leftCard.add(brandHost, BorderLayout.CENTER);
            left.add(leftCard, BorderLayout.CENTER);

            JPanel formPanel = new JPanel(new BorderLayout());
            formPanel.setBackground(new Color(250, 247, 242));

            JPanel formContent = new JPanel(new GridBagLayout());
            formContent.setBackground(new Color(250, 247, 242));
            formContent.setBorder(BorderFactory.createEmptyBorder(20, 32, 34, 18));

            JPanel labelPanel = new JPanel();
            labelPanel.setOpaque(false);
            labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));

            JLabel lblLogin = new JLabel("Iniciar sesión");
            lblLogin.setFont(new Font("Arial", Font.BOLD, 32));
            lblLogin.setForeground(new Color(34, 31, 28));
            lblLogin.setHorizontalAlignment(SwingConstants.CENTER);
            lblLogin.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel lblSubtitle = new JLabel("Accede a tu cuenta para continuar");
            lblSubtitle.setForeground(new Color(94, 91, 87));
            lblSubtitle.setFont(new Font("Arial", Font.PLAIN, 16));
            lblSubtitle.setHorizontalAlignment(SwingConstants.CENTER);
            lblSubtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
            lblLogin.setPreferredSize(new Dimension(0, 44));
            lblLogin.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
            lblSubtitle.setPreferredSize(new Dimension(0, 28));
            lblSubtitle.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));

            labelPanel.add(lblLogin);
            labelPanel.add(Box.createVerticalStrut(2));
            labelPanel.add(lblSubtitle);
            labelPanel.setPreferredSize(new Dimension(396, 74));
            labelPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 74));

            JPanel fields = new JPanel();
            fields.setOpaque(false);
            fields.setLayout(new BoxLayout(fields, BoxLayout.Y_AXIS));
            fields.setAlignmentX(Component.CENTER_ALIGNMENT);

            Color inputTextColor = new Color(76, 46, 29);
            txtUsuario.setFont(new Font("Arial", Font.PLAIN, 17));
            txtUsuario.setBorder(BorderFactory.createEmptyBorder());
            txtUsuario.setText("Usuario");
            txtUsuario.setForeground(inputTextColor);
            txtUsuario.addFocusListener(new java.awt.event.FocusAdapter() {
                @Override
                public void focusGained(java.awt.event.FocusEvent e) {
                    if (txtUsuario.getText().equals("Usuario")) {
                        txtUsuario.setText("");
                        txtUsuario.setForeground(inputTextColor);
                    }
                }

                @Override
                public void focusLost(java.awt.event.FocusEvent e) {
                    if (txtUsuario.getText().trim().isEmpty()) {
                        txtUsuario.setText("Usuario");
                        txtUsuario.setForeground(inputTextColor);
                    }
                }
            });

            txtPassword.setFont(new Font("Arial", Font.PLAIN, 17));
            txtPassword.setBorder(BorderFactory.createEmptyBorder());
            txtPassword.setText("Contraseña");
            txtPassword.setEchoChar((char) 0);
            txtPassword.setForeground(inputTextColor);
            txtPassword.addFocusListener(new java.awt.event.FocusAdapter() {
                @Override
                public void focusGained(java.awt.event.FocusEvent e) {
                    if (new String(txtPassword.getPassword()).equals("Contraseña")) {
                        txtPassword.setText("");
                        txtPassword.setEchoChar('\u2022');
                        txtPassword.setForeground(inputTextColor);
                    }
                }

                @Override
                public void focusLost(java.awt.event.FocusEvent e) {
                    if (txtPassword.getPassword().length == 0) {
                        txtPassword.setText("Contraseña");
                        txtPassword.setEchoChar((char) 0);
                        txtPassword.setForeground(inputTextColor);
                    }
                }
            });

            JPanel userField = createIconField(txtUsuario, createFieldIcon(false));
            JPanel passwordField = createIconField(txtPassword, createFieldIcon(true));
            userField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
            passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

            lblLoginError.setFont(new Font("Arial", Font.BOLD, 13));
            lblLoginError.setForeground(new Color(177, 48, 40));
            lblLoginError.setVisible(false);
            loginErrorContainer = new JPanel(null) {
                @Override
                public void doLayout() {
                    int labelWidth = lblLoginError.getPreferredSize().width;
                    int labelHeight = getHeight();
                    int x = (getWidth() - labelWidth) / 2 + loginErrorOffset;
                    lblLoginError.setBounds(x, 0, labelWidth, labelHeight);
                }
            };
            loginErrorContainer.setOpaque(false);
            loginErrorContainer.setPreferredSize(new Dimension(396, 22));
            loginErrorContainer.setMaximumSize(new Dimension(Integer.MAX_VALUE, 22));
            loginErrorContainer.setAlignmentX(Component.CENTER_ALIGNMENT);
            loginErrorContainer.add(lblLoginError);

            JButton btnIngresar = new JButton("Ingresar") {
                protected void paintComponent(Graphics graphics) {
                    Graphics2D g = (Graphics2D) graphics.create();
                    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    Color fill = getModel().isPressed()
                            ? new Color(45, 25, 14)
                            : getModel().isRollover() ? new Color(83, 47, 27) : getBackground();
                    g.setColor(fill);
                    g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 24, 24);
                    g.dispose();
                    super.paintComponent(graphics);
                }
                protected void paintBorder(Graphics graphics) {
                    Graphics2D g = (Graphics2D) graphics.create();
                    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g.setColor(new Color(103, 68, 47));
                    g.setStroke(new BasicStroke(1.2f));
                    g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 24, 24);
                    g.dispose();
                }
            };
            btnIngresar.setFont(new Font("Arial", Font.BOLD, 17));
            btnIngresar.setPreferredSize(new Dimension(396, 60));
            btnIngresar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
            btnIngresar.setAlignmentX(Component.CENTER_ALIGNMENT);
            styleButton(btnIngresar, new Color(61, 32, 17), Color.WHITE);
            btnIngresar.setContentAreaFilled(false);
            btnIngresar.setOpaque(false);
            btnIngresar.addActionListener(e -> ingresar());

            JButton btnForgot = new JButton("¿Olvidaste tu contraseña?");
            btnForgot.setHorizontalAlignment(SwingConstants.CENTER);
            btnForgot.setForeground(new Color(76, 46, 29));
            btnForgot.setFont(new Font("Arial", Font.PLAIN, 15));
            btnForgot.setBorderPainted(false);
            btnForgot.setContentAreaFilled(false);
            btnForgot.setFocusPainted(false);
            btnForgot.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            btnForgot.setAlignmentX(Component.CENTER_ALIGNMENT);
            btnForgot.addActionListener(e -> mostrarAyudaContrasena());

            fields.add(userField);
            fields.add(Box.createVerticalStrut(16));
            fields.add(passwordField);
            fields.add(Box.createVerticalStrut(4));
            fields.add(loginErrorContainer);
            fields.setMaximumSize(new Dimension(Integer.MAX_VALUE, 184));

            JPanel loginGroup = new JPanel();
            loginGroup.setOpaque(false);
            loginGroup.setLayout(new BoxLayout(loginGroup, BoxLayout.Y_AXIS));
            loginGroup.add(labelPanel);
            loginGroup.add(Box.createVerticalStrut(32));
            loginGroup.add(fields);
            loginGroup.add(Box.createVerticalStrut(28));
            loginGroup.add(btnIngresar);
            loginGroup.add(Box.createVerticalStrut(16));
            loginGroup.add(btnForgot);

            GridBagConstraints formConstraints = new GridBagConstraints();
            formConstraints.gridx = 0;
            formConstraints.gridy = 0;
            formConstraints.weightx = 1;
            formConstraints.weighty = 1;
            formConstraints.fill = GridBagConstraints.HORIZONTAL;
            formContent.add(loginGroup, formConstraints);

            formPanel.add(formContent, BorderLayout.CENTER);

            root.add(left, BorderLayout.WEST);
            root.add(formPanel, BorderLayout.CENTER);
            root.setPreferredSize(new Dimension(960, 620));
            add(root);
            pack();
            setLocationRelativeTo(null);
        }

        private void showLoginError() {
            if (loginErrorShakeTimer != null && loginErrorShakeTimer.isRunning()) {
                loginErrorShakeTimer.stop();
            }

            lblLoginError.setVisible(true);
            int[] offsets = {0, -9, 9, -7, 7, -4, 4, 0};
            int[] step = {0};
            loginErrorShakeTimer = new javax.swing.Timer(35, e -> {
                if (step[0] >= offsets.length) {
                    loginErrorOffset = 0;
                    loginErrorContainer.doLayout();
                    loginErrorContainer.repaint();
                    ((javax.swing.Timer) e.getSource()).stop();
                    return;
                }
                loginErrorOffset = offsets[step[0]++];
                loginErrorContainer.doLayout();
                loginErrorContainer.repaint();
            });
            loginErrorShakeTimer.start();
        }

        private BufferedImage loadLoginImage() {
            File imageFile = new File("assets/living-room.jpg");
            if (!imageFile.isFile()) {
                return null;
            }
            try {
                return ImageIO.read(imageFile);
            } catch (IOException e) {
                return null;
            }
        }

        private Icon createFurnitureLogo() {
            return new Icon() {
                @Override
                public void paintIcon(Component component, Graphics graphics, int x, int y) {
                    Graphics2D g = (Graphics2D) graphics.create();
                    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g.translate(x, y);
                    g.scale(1.1, 0.85);
                    g.setColor(new Color(62, 35, 20));
                    g.setStroke(new BasicStroke(5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                    g.drawLine(19, 106, 89, 28);
                    g.drawLine(89, 28, 160, 106);
                    g.drawLine(34, 90, 34, 179);
                    g.drawLine(145, 90, 145, 179);
                    g.drawLine(28, 179, 153, 179);
                    g.fillRect(124, 43, 10, 39);
                    g.setStroke(new BasicStroke(3.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                    g.drawLine(42, 100, 42, 84);
                    g.drawLine(42, 84, 57, 84);
                    g.drawLine(57, 84, 57, 100);
                    g.drawLine(47, 100, 47, 121);
                    g.drawLine(51, 100, 51, 121);
                    g.fillRoundRect(63, 104, 53, 46, 8, 8);
                    g.fillRoundRect(57, 117, 13, 33, 6, 6);
                    g.fillRoundRect(109, 117, 13, 33, 6, 6);
                    g.setColor(new Color(255, 246, 226));
                    g.drawLine(89, 109, 89, 143);
                    g.setColor(new Color(62, 35, 20));
                    g.drawLine(68, 150, 66, 166);
                    g.drawLine(111, 150, 113, 166);
                    g.drawRect(127, 126, 21, 41);
                    g.drawLine(127, 140, 148, 140);
                    g.drawLine(137, 126, 137, 167);
                    g.drawLine(24, 127, 42, 127);
                    g.drawLine(33, 127, 33, 166);
                    g.drawLine(22, 127, 44, 127);
                    g.drawLine(20, 115, 46, 115);
                    g.dispose();
                }

                @Override
                public int getIconWidth() {
                    return 190;
                }

                @Override
                public int getIconHeight() {
                    return 155;
                }
            };
        }

        private JPanel createIconField(JTextField field, Icon icon) {
            JPanel panel = new JPanel(new BorderLayout(10, 0));
            panel.setBackground(Color.WHITE);
            panel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(211, 214, 204)),
                    BorderFactory.createEmptyBorder(0, 12, 0, 12)
            ));
            JLabel iconLabel = new JLabel(icon);
            iconLabel.setToolTipText(field == txtUsuario ? "Usuario" : "Contraseña");
            panel.add(iconLabel, BorderLayout.WEST);
            panel.add(field, BorderLayout.CENTER);
            panel.setPreferredSize(new Dimension(396, 60));
            return panel;
        }

        private Icon createFieldIcon(boolean lock) {
            return new Icon() {
                @Override
                public void paintIcon(Component component, Graphics graphics, int x, int y) {
                    Graphics2D g = (Graphics2D) graphics.create();
                    g.setColor(new Color(112, 121, 103));
                    g.setStroke(new BasicStroke(1.7f));
                    if (lock) {
                        g.drawArc(x + 5, y + 1, 9, 10, 0, 180);
                        g.drawRoundRect(x + 3, y + 7, 13, 10, 2, 2);
                    } else {
                        g.fillOval(x + 6, y + 1, 7, 7);
                        g.drawArc(x + 3, y + 8, 13, 10, 0, 180);
                    }
                    g.dispose();
                }

                @Override
                public int getIconWidth() {
                    return 20;
                }

                @Override
                public int getIconHeight() {
                    return 20;
                }
            };
        }

        private void mostrarAyudaContrasena() {
            JOptionPane.showMessageDialog(this,
                    "Esta versión local usa una cuenta de demostración:\n"
                            + "Usuario: Dani Admin\nContraseña: DaniG2006\n\n"
                            + "Para cuentas reales, configura un método de recuperación seguro.",
                    "Ayuda para iniciar sesión",
                    JOptionPane.INFORMATION_MESSAGE);
        }

        private void ingresar() {
            String usuario = txtUsuario.getText();
            String password = new String(txtPassword.getPassword());
            lblLoginError.setVisible(false);
            loginErrorOffset = 0;
            loginErrorContainer.doLayout();
            if (usuario.equals("Usuario")) {
                usuario = "";
            }
            if (password.equals("Contraseña")) {
                password = "";
            }

            if (usuario.equalsIgnoreCase("Dani Admin") && password.equals("DaniG2006")) {
                new MenuFrame().setVisible(true);
                dispose();
            } else {
                showLoginError();
                txtPassword.requestFocusInWindow();
            }
        }
    }

    static class MenuFrame extends JFrame {
        private final Color darkBrown = new Color(58, 34, 21);
        private final Color softBg = new Color(248, 246, 242);
        private final Color sidebarBrown = new Color(75, 46, 30);
        private final Color selectedBrown = new Color(113, 72, 46);
        private final Color mutedCream = new Color(226, 211, 195);
        private final CardLayout pageLayout = new CardLayout();
        private final JPanel pageContainer = new JPanel(pageLayout);
        private final Map<String, JPanel> modulePages = new HashMap<>();
        private final Map<String, JButton> navButtons = new HashMap<>();
        private String activePage = "Inicio";

        public MenuFrame() {
            setTitle("HOGARWOOD - Menú principal");
            setSize(1000, 650);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setResizable(true);
            getContentPane().setBackground(softBg);

            JPanel root = new JPanel(new BorderLayout());
            root.setBackground(softBg);

            JPanel brandText = new JPanel();
            brandText.setOpaque(false);
            brandText.setLayout(new BoxLayout(brandText, BoxLayout.Y_AXIS));
            JLabel logo = new JLabel("HOLMWOOD");
            logo.setForeground(new Color(255, 250, 242));
            logo.setFont(new Font("Arial", Font.BOLD, 21));
            JLabel logoSubtitle = new JLabel("M U E B L E R Í A");
            logoSubtitle.setForeground(mutedCream);
            logoSubtitle.setFont(new Font("Arial", Font.BOLD, 10));
            brandText.add(logo);
            brandText.add(logoSubtitle);

            JLabel logoIcon = new JLabel(createMenuIcon("Inicio", new Color(255, 250, 242), 38));
            JPanel brand = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
            brand.setOpaque(false);
            brand.add(logoIcon);
            brand.add(brandText);

            JPanel userPanel = new JPanel();
            userPanel.setOpaque(false);
            userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.Y_AXIS));
            JLabel iconUser = new JLabel(createMenuIcon("Usuario", mutedCream, 28));
            JLabel userText = new JLabel("Usuario: Dani Admin");
            userText.setForeground(new Color(255, 250, 242));
            userText.setFont(new Font("Arial", Font.BOLD, 13));
            userText.setAlignmentX(Component.LEFT_ALIGNMENT);
            Color logoutColor = new Color(91, 56, 36);
            JButton btnLogout = new JButton("Cerrar sesión");
            btnLogout.setBackground(logoutColor);
            btnLogout.setForeground(new Color(255, 250, 242));
            btnLogout.setBorderPainted(false);
            btnLogout.setFocusPainted(false);
            btnLogout.setFont(new Font("Arial", Font.BOLD, 12));
            btnLogout.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            btnLogout.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
            btnLogout.setAlignmentX(Component.LEFT_ALIGNMENT);
            btnLogout.addActionListener(e -> {
                new LoginFrame().setVisible(true);
                dispose();
            });
            btnLogout.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mousePressed(java.awt.event.MouseEvent e) {
                    btnLogout.setBackground(logoutColor.darker());
                }

                @Override
                public void mouseReleased(java.awt.event.MouseEvent e) {
                    btnLogout.setBackground(logoutColor);
                    btnLogout.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
                }
            });
            iconUser.setAlignmentX(Component.LEFT_ALIGNMENT);
            userPanel.add(iconUser);
            userPanel.add(Box.createVerticalStrut(6));
            userPanel.add(userText);
            userPanel.add(Box.createVerticalStrut(10));
            userPanel.add(btnLogout);

            JPanel leftMenu = new JPanel();
            leftMenu.setPreferredSize(new Dimension(225, 0));
            leftMenu.setBackground(sidebarBrown);
            leftMenu.setBorder(BorderFactory.createEmptyBorder(16, 8, 4, 8));
            leftMenu.setLayout(new BoxLayout(leftMenu, BoxLayout.Y_AXIS));
            brand.setAlignmentX(Component.LEFT_ALIGNMENT);
            brand.setBorder(BorderFactory.createEmptyBorder(0, 8, 20, 0));
            leftMenu.add(brand);

            JLabel navHeading = new JLabel("MENÚ PRINCIPAL");
            navHeading.setForeground(mutedCream);
            navHeading.setFont(new Font("Arial", Font.BOLD, 11));
            navHeading.setBorder(BorderFactory.createEmptyBorder(4, 12, 12, 0));
            navHeading.setAlignmentX(Component.LEFT_ALIGNMENT);
            leftMenu.add(navHeading);

            String[] items = {"Inicio", "Muebles", "Ventas", "Gastos", "Apartados", "Impuestos", "Inventario", "Reportes"};
            for (String item : items) {
                JButton btn = new JButton(item);
                boolean selected = item.equals("Inicio");
                btn.setIcon(createMenuIcon(item, selected ? Color.WHITE : mutedCream, 25));
                btn.setHorizontalAlignment(SwingConstants.LEFT);
                btn.setIconTextGap(13);
                btn.setAlignmentX(Component.LEFT_ALIGNMENT);
                btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
                btn.setPreferredSize(new Dimension(181, 44));
                btn.setBackground(selected ? selectedBrown : sidebarBrown);
                btn.setForeground(new Color(255, 250, 242));
                btn.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 8));
                btn.setFocusPainted(false);
                btn.setFont(new Font("Arial", selected ? Font.BOLD : Font.PLAIN, 14));
                btn.setOpaque(true);
                btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                btn.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 8));
                btn.addActionListener(e -> openModule(item));
                btn.addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mousePressed(java.awt.event.MouseEvent e) {
                        Color baseColor = item.equals(activePage) ? selectedBrown : sidebarBrown;
                        btn.setBackground(baseColor.darker());
                    }

                    @Override
                    public void mouseReleased(java.awt.event.MouseEvent e) {
                        updateSelectedNavigation(activePage);
                    }
                });
                leftMenu.add(Box.createVerticalStrut(5));
                leftMenu.add(btn);
                navButtons.put(item, btn);
            }

            leftMenu.add(Box.createVerticalGlue());
            JPanel accountSection = new JPanel(new BorderLayout(0, 0));
            accountSection.setOpaque(false);
            accountSection.setAlignmentX(Component.LEFT_ALIGNMENT);
                accountSection.setBorder(BorderFactory.createEmptyBorder(2, 4, 0, 4));
            accountSection.add(userPanel, BorderLayout.CENTER);
            leftMenu.add(accountSection);

            JPanel content = new JPanel(new BorderLayout());
            content.setBackground(new Color(255, 255, 255));
            content.setBorder(BorderFactory.createEmptyBorder(28, 30, 28, 30));

            JLabel bienvenida = new JLabel("Bienvenido, Dani Admin");
            bienvenida.setFont(new Font("Arial", Font.BOLD, 26));
            bienvenida.setForeground(new Color(42, 34, 29));
            bienvenida.setBorder(BorderFactory.createEmptyBorder(0, 0, 4, 0));

            JLabel bienvenidaSubtitle = new JLabel("Selecciona una opción para comenzar.");
            bienvenidaSubtitle.setFont(new Font("Arial", Font.PLAIN, 14));
            bienvenidaSubtitle.setForeground(new Color(100, 93, 86));

            JPanel welcomeBlock = new JPanel();
            welcomeBlock.setOpaque(false);
            welcomeBlock.setLayout(new BoxLayout(welcomeBlock, BoxLayout.Y_AXIS));
            welcomeBlock.add(bienvenida);
            welcomeBlock.add(bienvenidaSubtitle);
            welcomeBlock.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

            JPanel cards = new JPanel(new GridLayout(2, 3, 18, 18));
            cards.setOpaque(false);

            Object[][] modules = {
                    {"Muebles", "Tipos y catálogo"},
                    {"Ventas", "Registrar y consultar"},
                    {"Gastos", "Control de gastos"},
                    {"Apartados", "Gestionar apartados"},
                    {"Impuestos", "Cálculo de impuestos"},
                    {"Inventario", "Productos en stock"}
            };

            for (Object[] module : modules) {
                String moduleName = String.valueOf(module[0]);
                JPanel card = new JPanel(new BorderLayout());
                card.setBackground(new Color(247, 243, 238));
                card.setBorder(BorderFactory.createLineBorder(new Color(231, 224, 216), 1));
                card.setPreferredSize(new Dimension(220, 150));

                JLabel icon = new JLabel(createMenuIcon(moduleName, darkBrown, 48));
                icon.setHorizontalAlignment(SwingConstants.CENTER);
                icon.setBorder(BorderFactory.createEmptyBorder(4, 0, 0, 0));

                JLabel title = new JLabel(moduleName, SwingConstants.CENTER);
                title.setFont(new Font("Arial", Font.BOLD, 16));
                title.setForeground(new Color(45, 34, 27));

                JLabel description = new JLabel(String.valueOf(module[1]), SwingConstants.CENTER);
                description.setFont(new Font("Arial", Font.PLAIN, 12));
                description.setForeground(new Color(102, 94, 86));

                JPanel cardText = new JPanel();
                cardText.setOpaque(false);
                cardText.setLayout(new BoxLayout(cardText, BoxLayout.Y_AXIS));
                cardText.add(Box.createVerticalStrut(4));
                cardText.add(title);
                cardText.add(Box.createVerticalStrut(4));
                cardText.add(description);

                card.add(icon, BorderLayout.NORTH);
                card.add(cardText, BorderLayout.CENTER);
                card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                card.addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent e) {
                        openModule(moduleName);
                    }

                    @Override
                    public void mousePressed(java.awt.event.MouseEvent e) {
                        card.setBackground(new Color(247, 243, 238).darker());
                    }

                    @Override
                    public void mouseReleased(java.awt.event.MouseEvent e) {
                        card.setBackground(card.contains(e.getPoint())
                                ? new Color(241, 234, 226)
                                : new Color(247, 243, 238));
                    }

                    @Override
                    public void mouseEntered(java.awt.event.MouseEvent e) {
                        card.setBackground(new Color(241, 234, 226));
                    }

                    @Override
                    public void mouseExited(java.awt.event.MouseEvent e) {
                        card.setBackground(new Color(247, 243, 238));
                    }
                });
                cards.add(card);
            }

            content.add(welcomeBlock, BorderLayout.NORTH);
            content.add(cards, BorderLayout.CENTER);

            pageContainer.setBackground(Color.WHITE);
            pageContainer.add(content, "Inicio");
            root.add(leftMenu, BorderLayout.WEST);
            root.add(pageContainer, BorderLayout.CENTER);
            add(root);
        }

        private Icon createMenuIcon(String name, Color color, int size) {
            return new Icon() {
                public void paintIcon(Component component, Graphics graphics, int x, int y) {
                    Graphics2D g = (Graphics2D) graphics.create();
                    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g.translate(x, y);
                    g.scale(size / 48.0, size / 48.0);
                    g.setColor(color);
                    g.setStroke(new BasicStroke(2.8f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                    switch (name) {
                        case "Inicio":
                            g.drawLine(7, 23, 24, 8);
                            g.drawLine(24, 8, 41, 23);
                            g.drawRoundRect(12, 21, 24, 20, 2, 2);
                            g.drawRect(21, 29, 7, 12);
                            break;
                        case "Usuario":
                            g.drawOval(17, 7, 14, 14);
                            g.drawArc(9, 23, 30, 23, 0, 180);
                            break;
                        case "Muebles":
                            g.drawRoundRect(9, 20, 30, 15, 4, 4);
                            g.drawRoundRect(13, 14, 22, 12, 4, 4);
                            g.drawLine(9, 27, 5, 27);
                            g.drawLine(39, 27, 43, 27);
                            g.drawLine(13, 35, 12, 41);
                            g.drawLine(35, 35, 36, 41);
                            break;
                        case "Ventas":
                            g.drawLine(7, 10, 13, 10);
                            g.drawLine(13, 10, 18, 32);
                            g.drawLine(18, 32, 38, 32);
                            g.drawLine(17, 18, 41, 18);
                            g.drawLine(41, 18, 36, 28);
                            g.drawLine(19, 22, 39, 22);
                            g.drawOval(19, 36, 5, 5);
                            g.drawOval(33, 36, 5, 5);
                            break;
                        case "Gastos":
                            g.drawOval(8, 20, 25, 20);
                            g.drawOval(14, 11, 25, 20);
                            g.drawOval(21, 6, 20, 16);
                            g.drawLine(26, 10, 26, 18);
                            g.drawLine(23, 14, 29, 14);
                            break;
                        case "Apartados":
                            g.drawRoundRect(9, 11, 30, 30, 3, 3);
                            g.drawLine(9, 20, 39, 20);
                            g.drawLine(17, 7, 17, 15);
                            g.drawLine(31, 7, 31, 15);
                            g.drawLine(16, 27, 20, 27);
                            g.drawLine(27, 27, 31, 27);
                            g.drawLine(16, 34, 20, 34);
                            g.drawLine(27, 34, 31, 34);
                            break;
                        case "Impuestos":
                            g.drawLine(13, 6, 29, 6);
                            g.drawLine(29, 6, 37, 14);
                            g.drawLine(37, 14, 37, 42);
                            g.drawLine(13, 6, 13, 42);
                            g.drawLine(13, 42, 37, 42);
                            g.drawLine(29, 6, 29, 15);
                            g.drawLine(29, 15, 37, 15);
                            g.drawLine(19, 24, 31, 24);
                            g.drawLine(19, 30, 31, 30);
                            g.drawLine(19, 36, 28, 36);
                            break;
                        case "Inventario":
                            g.drawLine(8, 17, 24, 8);
                            g.drawLine(24, 8, 40, 17);
                            g.drawLine(8, 17, 24, 26);
                            g.drawLine(40, 17, 24, 26);
                            g.drawLine(8, 17, 8, 34);
                            g.drawLine(40, 17, 40, 34);
                            g.drawLine(8, 34, 24, 43);
                            g.drawLine(40, 34, 24, 43);
                            g.drawLine(24, 26, 24, 43);
                            break;
                        case "Reportes":
                            g.drawLine(8, 41, 8, 9);
                            g.drawLine(8, 41, 41, 41);
                            g.drawRoundRect(14, 27, 6, 14, 2, 2);
                            g.drawRoundRect(24, 18, 6, 23, 2, 2);
                            g.drawRoundRect(34, 10, 6, 31, 2, 2);
                            break;
                        default:
                            g.drawOval(10, 10, 28, 28);
                            break;
                    }
                    g.dispose();
                }

                @Override
                public int getIconWidth() {
                    return size;
                }

                @Override
                public int getIconHeight() {
                    return size;
                }
            };
        }

        private void openModule(String name) {
            if (name.equals("Inicio")) {
                pageLayout.show(pageContainer, name);
                updateSelectedNavigation(name);
                return;
            }

            JPanel module = modulePages.get(name);
            if (module == null) {
                switch (name) {
                    case "Muebles":
                        module = new MueblesPanel();
                        break;
                    case "Ventas":
                        module = new VentasPanel();
                        break;
                    case "Gastos":
                        module = new GastosPanel();
                        break;
                    case "Apartados":
                        module = new ApartadosPanel();
                        break;
                    case "Impuestos":
                        module = new ImpuestosPanel();
                        break;
                    case "Inventario":
                        module = new InventarioPanel();
                        break;
                    case "Reportes":
                        module = createReportsPanel();
                        break;
                    default:
                        return;
                }
                modulePages.put(name, module);
                pageContainer.add(module, name);
            }
            pageLayout.show(pageContainer, name);
            updateSelectedNavigation(name);
        }

        private void updateSelectedNavigation(String selectedName) {
            activePage = selectedName;
            for (Map.Entry<String, JButton> entry : navButtons.entrySet()) {
                boolean selected = entry.getKey().equals(selectedName);
                JButton button = entry.getValue();
                button.setBackground(selected ? selectedBrown : sidebarBrown);
                button.setFont(new Font("Arial", selected ? Font.BOLD : Font.PLAIN, 14));
                button.setIcon(createMenuIcon(entry.getKey(), selected ? Color.WHITE : mutedCream, 25));
                button.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 8));
            }
        }

        private JPanel createReportsPanel() {
            JPanel panel = new JPanel(new BorderLayout(0, 8));
            panel.setBackground(Color.WHITE);
            panel.setBorder(BorderFactory.createEmptyBorder(30, 32, 30, 32));
            JLabel title = new JLabel("Reportes");
            title.setFont(new Font("Arial", Font.BOLD, 26));
            title.setForeground(new Color(42, 34, 29));
            JLabel message = new JLabel("Módulo de reportes en construcción");
            message.setFont(new Font("Arial", Font.PLAIN, 15));
            message.setForeground(new Color(100, 93, 86));
            panel.add(title, BorderLayout.NORTH);
            panel.add(message, BorderLayout.CENTER);
            return panel;
        }
    }

    static class MueblesPanel extends JPanel {
        private final DefaultTableModel model = new DefaultTableModel(
                new Object[]{"ID", "Tipo", "Descripción", "Precio"}, 0
        );
        private final JTable table = new JTable(model);
        private final JComboBox<String> cmbTipo = new JComboBox<>(new String[]{
                "Sala", "Comedor", "Recámara", "Oficina", "Infantil"
        });
        private final JTextField txtDescripcion = new JTextField();
        private final JTextField txtPrecio = new JTextField();

        public MueblesPanel() {
            setLayout(new BorderLayout(15, 15));
            setBackground(new Color(247, 242, 238));

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

    static class VentasPanel extends JPanel {
        public VentasPanel() {
            setLayout(new BorderLayout(15, 15));
            setBackground(new Color(248, 247, 244));

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

    static class GastosPanel extends JPanel {
        public GastosPanel() {
            setLayout(new BorderLayout(15, 15));
            setBackground(new Color(248, 247, 244));

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

    static class ApartadosPanel extends JPanel {
        public ApartadosPanel() {
            setLayout(new BorderLayout(15, 15));
            setBackground(new Color(248, 247, 244));

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
            JTextField txtCliente = new JTextField("Ruben");
            c.gridx = 1; form.add(txtCliente, c);

            c.gridx = 0; c.gridy = 2; form.add(new JLabel("Monto:"), c);
            JTextField txtMonto = new JTextField("$3,000");
            c.gridx = 1; form.add(txtMonto, c);

            c.gridx = 0; c.gridy = 3; form.add(new JLabel("Fecha:"), c);
            JTextField txtFecha = new JTextField("2026-09-30");
            c.gridx = 1; form.add(txtFecha, c);

            JButton btnAgregar = new JButton("Nuevo apartado");
            styleButton(btnAgregar, new Color(95, 65, 43), Color.WHITE);
            c.gridx = 0; c.gridy = 4; c.gridwidth = 2; form.add(btnAgregar, c);

            DefaultTableModel model = new DefaultTableModel(new Object[]{"ID", "Cliente", "Monto", "Fecha"}, 0);
            model.addRow(new Object[]{1, "Ruben", "$3,000", "2026-09-30"});
            model.addRow(new Object[]{2, "Bruno", "$5,500", "2026-09-28"});
            model.addRow(new Object[]{3, "Roberto", "$4,000", "2026-09-28"});
            JTable table = new JTable(model);

            add(form, BorderLayout.WEST);
            add(new JScrollPane(table), BorderLayout.CENTER);

            btnAgregar.addActionListener(e -> model.addRow(new Object[]{model.getRowCount() + 1, txtCliente.getText(), txtMonto.getText(), txtFecha.getText()}));
        }
    }

    static class ImpuestosPanel extends JPanel {
        public ImpuestosPanel() {
            setLayout(new BorderLayout(15, 15));
            setBackground(new Color(248, 247, 244));

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

    static class InventarioPanel extends JPanel {
        public InventarioPanel() {
            setLayout(new BorderLayout(15, 15));
            setBackground(new Color(248, 247, 244));

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