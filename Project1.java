import javax.swing.*; 
import javax.swing.table.DefaultTableModel;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.prefs.Preferences;

public class Project1 {

    public static void main(String[] args) {
        aplicarFuenteArial();
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
        private final JCheckBox chkRecordarme = new JCheckBox("Recordarme");
        private final Preferences loginPreferences = Preferences.userNodeForPackage(Project1.class).node("login");
        private final JLabel lblLoginError = new JLabel("Usuario y/o contraseña incorrecto", SwingConstants.CENTER);
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
                public void focusGained(java.awt.event.FocusEvent e) {
                    if (txtUsuario.getText().equals("Usuario")) {
                        txtUsuario.setText("");
                        txtUsuario.setForeground(inputTextColor);
                    }
                }

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
                public void focusGained(java.awt.event.FocusEvent e) {
                    if (new String(txtPassword.getPassword()).equals("Contraseña")) {
                        txtPassword.setText("");
                        txtPassword.setEchoChar('\u2022');
                        txtPassword.setForeground(inputTextColor);
                    }
                }
                public void focusLost(java.awt.event.FocusEvent e) {
                    if (txtPassword.getPassword().length == 0) {
                        txtPassword.setText("Contraseña");
                        txtPassword.setEchoChar((char) 0);
                        txtPassword.setForeground(inputTextColor);
                    }
                }
            });

            chkRecordarme.setOpaque(false);
            chkRecordarme.setForeground(new Color(76, 46, 29));
            chkRecordarme.setFont(new Font("Arial", Font.PLAIN, 14));
            chkRecordarme.setAlignmentX(Component.LEFT_ALIGNMENT);
            String rememberedUser = loginPreferences.get("username", "");
            String rememberedPassword = loginPreferences.get("password", "");
            if (!rememberedUser.isEmpty() && !rememberedPassword.isEmpty()) {
                txtUsuario.setText(rememberedUser);
                txtPassword.setText(rememberedPassword);
                txtPassword.setEchoChar('\u2022');
                chkRecordarme.setSelected(true);
            }

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
            loginErrorContainer.setPreferredSize(new Dimension(270, 22));
            loginErrorContainer.setMaximumSize(new Dimension(Integer.MAX_VALUE, 22));
            loginErrorContainer.add(lblLoginError);

            JPanel loginFeedbackRow = new JPanel(new BorderLayout(8, 0));
            loginFeedbackRow.setOpaque(false);
            loginFeedbackRow.setPreferredSize(new Dimension(396, 28));
            loginFeedbackRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
            loginFeedbackRow.setAlignmentX(Component.CENTER_ALIGNMENT);
            loginFeedbackRow.add(chkRecordarme, BorderLayout.WEST);
            loginFeedbackRow.add(loginErrorContainer, BorderLayout.CENTER);

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
            fields.add(Box.createVerticalStrut(6));
            fields.add(loginFeedbackRow);
            fields.setMaximumSize(new Dimension(Integer.MAX_VALUE, 170));

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
                if (chkRecordarme.isSelected()) {
                    loginPreferences.put("username", usuario);
                    loginPreferences.put("password", password);
                } else {
                    loginPreferences.remove("username");
                    loginPreferences.remove("password");
                }
                new MenuFrame(usuario).setVisible(true);
                dispose();
            } else {
                showLoginError();
                txtPassword.requestFocusInWindow();
            }
        }
    }

    static class DatePickerField extends JPanel {
        private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE;
        private static final String[] MONTH_NAMES = {
            "enero", "febrero", "marzo", "abril", "mayo", "junio",
            "julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre"
        };

        private final JTextField dateInput;
        private final JButton calendarButton;
        private final JPopupMenu calendarPopup = new JPopupMenu();
        private YearMonth displayedMonth;
        private JPanel daysPanel;
        private JSpinner monthSelector;
        private JSpinner yearSelector;

        DatePickerField(String initialDate) {
            super(new BorderLayout(6, 0));
            setOpaque(false);

            dateInput = new JTextField(initialDate, 12);
            dateInput.setFont(new Font("Arial", Font.PLAIN, 14));
            dateInput.setPreferredSize(new Dimension(130, 30));
            dateInput.setBackground(new Color(252, 250, 246));
            dateInput.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(211, 198, 184)),
                    BorderFactory.createEmptyBorder(4, 8, 4, 8)));

            calendarButton = new JButton(createCalendarIcon());
            calendarButton.setToolTipText("Seleccionar fecha");
            calendarButton.getAccessibleContext().setAccessibleName("Abrir calendario");
            calendarButton.setFocusable(false);
            calendarButton.setPreferredSize(new Dimension(34, 30));
            calendarButton.setBackground(new Color(250, 247, 242));
            calendarButton.setBorder(BorderFactory.createLineBorder(new Color(211, 198, 184)));
            calendarButton.addActionListener(e -> {
                try {
                    displayedMonth = YearMonth.from(LocalDate.parse(dateInput.getText().trim(), DATE_FORMAT));
                } catch (RuntimeException ex) {
                    displayedMonth = YearMonth.now();
                }
                showCalendar();
            });

            add(dateInput, BorderLayout.CENTER);
            add(calendarButton, BorderLayout.EAST);
        }

        String getDate() {
            return dateInput.getText().trim();
        }

        private void showCalendar() {
            calendarPopup.setVisible(false);
            calendarPopup.removeAll();

            JPanel calendar = new JPanel(new BorderLayout(4, 6));
            calendar.setBackground(new Color(255, 252, 247));
            calendar.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(211, 198, 184)),
                    BorderFactory.createEmptyBorder(8, 8, 8, 8)
            ));

            JPanel monthHeader = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
            monthHeader.setOpaque(false);

            monthSelector = new JSpinner(new SpinnerListModel(Arrays.asList(MONTH_NAMES)));
            monthSelector.setValue(MONTH_NAMES[displayedMonth.getMonthValue() - 1]);
            monthSelector.setFont(new Font("Arial", Font.BOLD, 13));
            monthSelector.setPreferredSize(new Dimension(120, 30));
            JSpinner.ListEditor monthEditor = new JSpinner.ListEditor(monthSelector);
            monthSelector.setEditor(monthEditor);
            monthEditor.getTextField().setEditable(false);
            monthEditor.getTextField().setHorizontalAlignment(SwingConstants.CENTER);
            monthEditor.getTextField().setForeground(new Color(76, 46, 29));

            yearSelector = new JSpinner(new SpinnerNumberModel(displayedMonth.getYear(), 1900, 2100, 1));
            yearSelector.setFont(new Font("Arial", Font.BOLD, 13));
            JSpinner.NumberEditor yearEditor = new JSpinner.NumberEditor(yearSelector, "####");
            yearSelector.setEditor(yearEditor);
            yearEditor.getTextField().setForeground(new Color(76, 46, 29));
            yearEditor.getTextField().addFocusListener(new java.awt.event.FocusAdapter() {
                @Override
                public void focusLost(java.awt.event.FocusEvent e) {
                    try {
                        yearSelector.commitEdit();
                    } catch (java.text.ParseException ignored) {
                    }
                }
            });

            monthSelector.addChangeListener(e -> aplicarSeleccion());
            yearSelector.addChangeListener(e -> aplicarSeleccion());

            monthHeader.add(monthSelector);
            monthHeader.add(yearSelector);

            daysPanel = new JPanel(new GridLayout(7, 7, 3, 3));
            daysPanel.setOpaque(false);
            refrescarDias();

            calendar.add(monthHeader, BorderLayout.NORTH);
            calendar.add(daysPanel, BorderLayout.CENTER);
            calendarPopup.add(calendar);
            calendarPopup.show(calendarButton, 0, calendarButton.getHeight());
        }
        private void aplicarSeleccion() {
            if (daysPanel == null) {
                return;
            }
            int mes = Arrays.asList(MONTH_NAMES).indexOf(String.valueOf(monthSelector.getValue())) + 1;
            if (mes < 1 || mes > 12) {
                return;
            }
            try {
                displayedMonth = YearMonth.of((Integer) yearSelector.getValue(), mes);
            } catch (RuntimeException ex) {
                return;
            }
            refrescarDias();
        }
        private void refrescarDias() {
            daysPanel.removeAll();

            String[] weekdays = {"Lu", "Ma", "Mi", "Ju", "Vi", "Sá", "Do"};
            for (String weekday : weekdays) {
                JLabel label = new JLabel(weekday, SwingConstants.CENTER);
                label.setFont(new Font("Arial", Font.BOLD, 11));
                label.setForeground(new Color(112, 91, 70));
                daysPanel.add(label);
            }

            LocalDate firstDay = displayedMonth.atDay(1);
            int leadingDays = firstDay.getDayOfWeek().getValue() - DayOfWeek.MONDAY.getValue();
            for (int i = 0; i < leadingDays; i++) {
                daysPanel.add(new JLabel(""));
            }

            LocalDate selectedDate = null;
            try {
                selectedDate = LocalDate.parse(dateInput.getText().trim(), DATE_FORMAT);
            } catch (RuntimeException ignored) {
            }

            for (int day = 1; day <= displayedMonth.lengthOfMonth(); day++) {
                LocalDate date = displayedMonth.atDay(day);
                JButton dayButton = new JButton(Integer.toString(day));
                dayButton.setFont(new Font("Arial", Font.PLAIN, 12));
                dayButton.setForeground(new Color(62, 45, 33));
                dayButton.setBackground(date.equals(selectedDate)
                        ? new Color(226, 211, 195) : Color.WHITE);
                dayButton.setFocusPainted(false);
                dayButton.setBorder(BorderFactory.createLineBorder(new Color(236, 229, 220)));
                dayButton.setPreferredSize(new Dimension(30, 28));
                dayButton.addActionListener(e -> {
                    dateInput.setText(date.format(DATE_FORMAT));
                    calendarPopup.setVisible(false);
                });
                daysPanel.add(dayButton);
            }

            int trailingDays = 42 - leadingDays - displayedMonth.lengthOfMonth();
            for (int i = 0; i < trailingDays; i++) {
                daysPanel.add(new JLabel(""));
            }
            daysPanel.revalidate();
            daysPanel.repaint();
        }

        private Icon createCalendarIcon() {
            return new Icon() {
                @Override
                public void paintIcon(Component component, Graphics graphics, int x, int y) {
                    Graphics2D g = (Graphics2D) graphics.create();
                    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g.setColor(new Color(91, 68, 52));
                    g.setStroke(new BasicStroke(1.6f));
                    g.drawRoundRect(x + 2, y + 3, 16, 15, 3, 3);
                    g.drawLine(x + 2, y + 8, x + 18, y + 8);
                    g.drawLine(x + 6, y + 1, x + 6, y + 5);
                    g.drawLine(x + 14, y + 1, x + 14, y + 5);
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
    }

    static class MenuFrame extends JFrame {
        private static final Color SIDEBAR_BG = new Color(74, 43, 27);
        private static final Color SIDEBAR_HOVER = new Color(95, 59, 39);
        private static final Color SIDEBAR_SELECTED = new Color(124, 87, 66);
        private static final Color CREAM_TEXT = new Color(246, 238, 229);
        private static final Color CONTENT_BG = new Color(250, 247, 241);
        private static final Color CARD_BG = new Color(239, 230, 218);
        private static final Color CARD_HOVER = new Color(230, 217, 201);
        private static final Color INK = new Color(46, 31, 20);
        private static final Color MUTED_TEXT = new Color(107, 96, 88);
        private static final Color MUTED_CREAM = new Color(208, 182, 164);

        private final CardLayout pageLayout = new CardLayout();
        private final JPanel pageContainer = new JPanel(pageLayout);
        private final Map<String, JPanel> modulePages = new HashMap<>();
        private final Map<String, NavButton> navButtons = new HashMap<>();
        private final String username;

        public MenuFrame(String username) {
            this.username = username;
            setTitle("HOLMWOOD - Menú principal");
            setSize(1060, 680);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setResizable(true);
            getContentPane().setBackground(CONTENT_BG);

            JPanel root = new JPanel(new BorderLayout());
            root.setBackground(CONTENT_BG);
            root.add(buildHeader(), BorderLayout.NORTH);
            root.add(buildSidebar(), BorderLayout.WEST);

            pageContainer.setBackground(CONTENT_BG);
            JPanel welcome = createWelcomePage();
            modulePages.put("Inicio", welcome);
            pageContainer.add(welcome, "Inicio");
            root.add(pageContainer, BorderLayout.CENTER);

            add(root);
            updateSelectedNavigation("Inicio");
        }

        private JPanel buildHeader() {
            JPanel header = new JPanel(new BorderLayout());
            header.setBackground(SIDEBAR_BG);
            header.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
            header.setPreferredSize(new Dimension(0, 84));

            JPanel brandText = new JPanel();
            brandText.setOpaque(false);
            brandText.setLayout(new BoxLayout(brandText, BoxLayout.Y_AXIS));
            JLabel brandName = new JLabel("HOLMWOOD");
            brandName.setFont(new Font("Arial", Font.BOLD, 20));
            brandName.setForeground(CREAM_TEXT);
            brandName.setAlignmentX(Component.LEFT_ALIGNMENT);
            JLabel brandSubtitle = new JLabel("----- M U E B L E R Í A -----");
            brandSubtitle.setFont(new Font("Arial", Font.BOLD, 8));
            brandSubtitle.setForeground(MUTED_CREAM);
            brandSubtitle.setAlignmentX(Component.LEFT_ALIGNMENT);
            brandText.add(Box.createVerticalGlue());
            brandText.add(brandName);
            brandText.add(Box.createVerticalStrut(3));
            brandText.add(brandSubtitle);
            brandText.add(Box.createVerticalGlue());

            JPanel brand = new JPanel(new BorderLayout(12, 0));
            brand.setOpaque(false);
            brand.add(new JLabel(createHouseLogo(CREAM_TEXT, 46)), BorderLayout.WEST);
            brand.add(brandText, BorderLayout.CENTER);

            header.add(brand, BorderLayout.WEST);
            return header;
        }

        private JPanel buildSidebar() {
            JPanel sidebar = new JPanel();
            sidebar.setPreferredSize(new Dimension(206, 0));
            sidebar.setBackground(SIDEBAR_BG);
            sidebar.setBorder(BorderFactory.createEmptyBorder(8, 8, 14, 8));
            sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

            String[] items = {"Inicio", "Muebles", "Ventas", "Gastos",
                    "Apartados", "Impuestos", "Inventario", "Reportes"};
            for (String item : items) {
                NavButton button = new NavButton(item, createMenuIcon(item, CREAM_TEXT, 22));
                button.setAlignmentX(Component.LEFT_ALIGNMENT);
                button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
                button.setPreferredSize(new Dimension(190, 44));
                button.addActionListener(e -> openModule(item));
                sidebar.add(button);
                sidebar.add(Box.createVerticalStrut(3));
                navButtons.put(item, button);
            }
            sidebar.add(Box.createVerticalGlue());
            sidebar.add(buildAccountSection());
            return sidebar;
        }

        private JPanel buildAccountSection() {
            JPanel section = new JPanel(new BorderLayout(10, 0));
            section.setOpaque(false);
            section.setAlignmentX(Component.LEFT_ALIGNMENT);
            section.setMaximumSize(new Dimension(Integer.MAX_VALUE, 78));
            section.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(112, 76, 55)),
                    BorderFactory.createEmptyBorder(14, 8, 4, 8)));

            JPanel texts = new JPanel();
            texts.setOpaque(false);
            texts.setLayout(new BoxLayout(texts, BoxLayout.Y_AXIS));
            JLabel userText = new JLabel("Usuario: " + username);
            userText.setForeground(CREAM_TEXT);
            userText.setFont(new Font("Arial", Font.BOLD, 12));
            userText.setAlignmentX(Component.LEFT_ALIGNMENT);
            JButton btnLogout = new JButton("Cerrar sesión", createMenuIcon("Salir", MUTED_CREAM, 15));
            btnLogout.setForeground(MUTED_CREAM);
            btnLogout.setFont(new Font("Arial", Font.PLAIN, 12));
            btnLogout.setContentAreaFilled(false);
            btnLogout.setBorderPainted(false);
            btnLogout.setFocusPainted(false);
            btnLogout.setIconTextGap(6);
            btnLogout.setMargin(new Insets(0, 0, 0, 0));
            btnLogout.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            btnLogout.setAlignmentX(Component.LEFT_ALIGNMENT);
            btnLogout.addActionListener(e -> {
                new LoginFrame().setVisible(true);
                dispose();
            });
            texts.add(userText);
            texts.add(Box.createVerticalStrut(3));
            texts.add(btnLogout);

            section.add(new JLabel(createMenuIcon("Usuario", CREAM_TEXT, 26)), BorderLayout.WEST);
            section.add(texts, BorderLayout.CENTER);
            return section;
        }

        private JPanel createWelcomePage() {
            JPanel page = new JPanel(new BorderLayout(0, 22));
            page.setBackground(CONTENT_BG);
            page.setBorder(BorderFactory.createEmptyBorder(26, 30, 26, 30));

            JPanel heading = new JPanel();
            heading.setOpaque(false);
            heading.setLayout(new BoxLayout(heading, BoxLayout.Y_AXIS));
            JLabel title = new JLabel("Bienvenido, " + username);
            title.setFont(new Font("Arial", Font.BOLD, 27));
            title.setForeground(INK);
            title.setAlignmentX(Component.LEFT_ALIGNMENT);
            JLabel subtitle = new JLabel("Selecciona una opción del menú para comenzar.");
            subtitle.setFont(new Font("Arial", Font.PLAIN, 15));
            subtitle.setForeground(MUTED_TEXT);
            subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);
            heading.add(title);
            heading.add(Box.createVerticalStrut(6));
            heading.add(subtitle);

            Object[][] modules = {
                    {"Muebles", "Tipos, productos y precios"},
                    {"Ventas", "Registrar y consultar"},
                    {"Gastos", "Control de gastos"},
                    {"Apartados", "Gestionar apartados"},
                    {"Impuestos", "Cálculo de impuestos"},
                    {"Inventario", "Productos en stock"}
            };
            JPanel cards = new JPanel(new GridLayout(2, 3, 20, 20));
            cards.setOpaque(false);
            for (Object[] module : modules) {
                String name = String.valueOf(module[0]);
                cards.add(new ModuleCard(name, String.valueOf(module[1]),
                        createMenuIcon(name, INK, 44), () -> openModule(name)));
            }

            page.add(heading, BorderLayout.NORTH);
            page.add(cards, BorderLayout.CENTER);
            return page;
        }

        private void openModule(String name) {
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
            if (module instanceof ModuloActualizable actualizable) {
                actualizable.refrescar();
            }
            updateSelectedNavigation(name);
        }

        private void updateSelectedNavigation(String selectedName) {
            for (Map.Entry<String, NavButton> entry : navButtons.entrySet()) {
                entry.getValue().setSelectedState(entry.getKey().equals(selectedName));
            }
        }

        private JPanel createReportsPanel() {
            JPanel panel = new JPanel(new BorderLayout(0, 8));
            panel.setBackground(CONTENT_BG);
            panel.setBorder(BorderFactory.createEmptyBorder(30, 32, 30, 32));
            JLabel title = new JLabel("Reportes");
            title.setFont(new Font("Arial", Font.BOLD, 26));
            title.setForeground(INK);
            JLabel message = new JLabel("Módulo de reportes en construcción");
            message.setFont(new Font("Arial", Font.PLAIN, 15));
            message.setForeground(MUTED_TEXT);
            panel.add(title, BorderLayout.NORTH);
            panel.add(message, BorderLayout.CENTER);
            return panel;
        }

        private static Icon createHouseLogo(Color color, int size) {
            return new Icon() {
                @Override
                public void paintIcon(Component component, Graphics graphics, int x, int y) {
                    Graphics2D g = (Graphics2D) graphics.create();
                    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g.translate(x, y);
                    g.scale(size / 48.0, size / 48.0);
                    g.setColor(color);
                    g.setStroke(new BasicStroke(2.4f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                    g.drawLine(4, 21, 24, 4);
                    g.drawLine(24, 4, 44, 21);
                    g.drawLine(9, 18, 9, 42);
                    g.drawLine(39, 18, 39, 42);
                    g.drawLine(9, 42, 39, 42);
                    g.fillRoundRect(17, 26, 14, 8, 4, 4);
                    g.drawRoundRect(14, 32, 20, 8, 3, 3);
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

        private static Icon createMenuIcon(String name, Color color, int size) {
            return new Icon() {
                @Override
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
                        case "Salir":
                            g.drawRoundRect(8, 8, 19, 32, 3, 3);
                            g.drawLine(24, 24, 41, 24);
                            g.drawLine(35, 18, 41, 24);
                            g.drawLine(35, 30, 41, 24);
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

        private static class NavButton extends JButton {
            private boolean selected;
            private boolean hover;

            NavButton(String text, Icon icon) {
                super(text, icon);
                setOpaque(false);
                setContentAreaFilled(false);
                setBorderPainted(false);
                setFocusPainted(false);
                setHorizontalAlignment(SwingConstants.LEFT);
                setIconTextGap(14);
                setForeground(CREAM_TEXT);
                setFont(new Font("Arial", Font.PLAIN, 15));
                setBorder(BorderFactory.createEmptyBorder(0, 14, 0, 8));
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseEntered(java.awt.event.MouseEvent e) {
                        hover = true;
                        repaint();
                    }

                    @Override
                    public void mouseExited(java.awt.event.MouseEvent e) {
                        hover = false;
                        repaint();
                    }
                });
            }

            void setSelectedState(boolean selected) {
                this.selected = selected;
                setFont(new Font("Arial", selected ? Font.BOLD : Font.PLAIN, 15));
                repaint();
            }

            @Override
            protected void paintComponent(Graphics graphics) {
                Graphics2D g = (Graphics2D) graphics.create();
                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g.setColor(selected ? SIDEBAR_SELECTED : hover ? SIDEBAR_HOVER : SIDEBAR_BG);
                g.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g.dispose();
                super.paintComponent(graphics);
            }
        }

        private static class ModuleCard extends JPanel {
            private boolean hover;

            ModuleCard(String name, String description, Icon icon, Runnable onClick) {
                setOpaque(false);
                setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
                setBorder(BorderFactory.createEmptyBorder(16, 10, 16, 10));
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

                JLabel iconLabel = new JLabel(icon);
                iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
                iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                JLabel title = new JLabel(name);
                title.setHorizontalAlignment(SwingConstants.CENTER);
                title.setFont(new Font("Arial", Font.BOLD, 17));
                title.setForeground(INK);
                title.setAlignmentX(Component.CENTER_ALIGNMENT);
                JLabel descriptionLabel = new JLabel(description);
                descriptionLabel.setHorizontalAlignment(SwingConstants.CENTER);
                descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 13));
                descriptionLabel.setForeground(MUTED_TEXT);
                descriptionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

                add(Box.createVerticalGlue());
                add(iconLabel);
                add(Box.createVerticalStrut(12));
                add(title);
                add(Box.createVerticalStrut(5));
                add(descriptionLabel);
                add(Box.createVerticalGlue());

                addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent e) {
                        onClick.run();
                    }

                    @Override
                    public void mouseEntered(java.awt.event.MouseEvent e) {
                        hover = true;
                        repaint();
                    }

                    @Override
                    public void mouseExited(java.awt.event.MouseEvent e) {
                        hover = false;
                        repaint();
                    }
                });
            }

            @Override
            protected void paintComponent(Graphics graphics) {
                Graphics2D g = (Graphics2D) graphics.create();
                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g.setColor(hover ? CARD_HOVER : CARD_BG);
                g.fillRoundRect(0, 0, getWidth(), getHeight(), 14, 14);
                g.dispose();
                super.paintComponent(graphics);
            }
        }
    }

    // ==================================================================
    //  Paleta y utilidades compartidas por los módulos
    // ==================================================================
    private static final Color PANEL_BG = new Color(250, 247, 241);
    private static final Color CARD_BG = new Color(255, 253, 249);
    private static final Color CARD_BORDER = new Color(233, 226, 216);
    private static final Color TEXT_INK = new Color(46, 31, 20);
    private static final Color TEXT_MUTED = new Color(122, 112, 102);
    private static final Color BTN_PRIMARY = new Color(75, 46, 30);
    private static final Color FIELD_BG = new Color(252, 250, 246);
    private static void aplicarFuenteArial() {
        Font base = new Font("Arial", Font.PLAIN, 13);
        String[] claves = {
                "Label.font", "Button.font", "CheckBox.font", "RadioButton.font",
                "TextField.font", "PasswordField.font", "TextArea.font", "ComboBox.font",
                "List.font", "Table.font", "TableHeader.font", "Spinner.font",
                "TitledBorder.font", "OptionPane.messageFont", "OptionPane.buttonFont",
                "Menu.font", "MenuItem.font", "ToolTip.font"
        };
        for (String clave : claves) {
            UIManager.put(clave, base);
        }
    }

    /** Contenedor tipo tarjeta: fondo claro, esquinas redondeadas y borde fino. */
    private static JPanel card(int radius) {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics graphics) {
                Graphics2D g = (Graphics2D) graphics.create();
                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g.setColor(CARD_BG);
                g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
                g.setColor(CARD_BORDER);
                g.setStroke(new BasicStroke(1f));
                g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
                g.dispose();
                super.paintComponent(graphics);
            }
        };
        panel.setOpaque(false);
        panel.setLayout(new BorderLayout(0, 14));
        panel.setBorder(BorderFactory.createEmptyBorder(18, 20, 20, 20));
        return panel;
    }

    private static JLabel cardTitle(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 22));
        label.setForeground(TEXT_INK);
        return label;
    }

    private static JButton primaryButton(String text) {
        JButton button = new JButton(text);
        styleButton(button, BTN_PRIMARY, Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 13));
        button.setBorder(BorderFactory.createEmptyBorder(9, 18, 9, 18));
        return button;
    }

    private static JButton secondaryButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(242, 236, 229));
        button.setForeground(TEXT_INK);
        button.setFont(new Font("Arial", Font.PLAIN, 13));
        button.setOpaque(true);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(9, 18, 9, 18));
        return button;
    }

    private static JTextField field() {
        JTextField field = new JTextField(12);
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setBackground(FIELD_BG);
        field.setPreferredSize(new Dimension(140, 32));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(CARD_BORDER),
                BorderFactory.createEmptyBorder(4, 8, 4, 8)));
        return field;
    }

    private static void styleTable(JTable table) {
        javax.swing.table.DefaultTableCellRenderer centrado = new javax.swing.table.DefaultTableCellRenderer();
        centrado.setHorizontalAlignment(SwingConstants.CENTER);
        table.setDefaultRenderer(Object.class, centrado);
        table.setRowHeight(30);
        table.setFillsViewportHeight(true);
        table.setGridColor(CARD_BORDER);
        table.setShowVerticalLines(false);
        table.setSelectionBackground(new Color(238, 228, 216));
        table.setSelectionForeground(TEXT_INK);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(245, 240, 233));
    }

    private static JScrollPane scroll(JTable table) {
        JScrollPane pane = new JScrollPane(table);
        pane.setBorder(BorderFactory.createLineBorder(CARD_BORDER));
        pane.getViewport().setBackground(Color.WHITE);
        return pane;
    }
    private static JPanel encabezado(String titulo, JComponent... acciones) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.add(cardTitle(titulo), BorderLayout.WEST);
        if (acciones.length > 0) {
            JPanel derecha = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
            derecha.setOpaque(false);
            for (JComponent accion : acciones) {
                derecha.add(accion);
            }
            panel.add(derecha, BorderLayout.EAST);
        }
        return panel;
    }
    private static JPanel accionesDeTabla(JTable tabla, Runnable editar, Runnable eliminar) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        panel.setOpaque(false);
        if (editar != null) {
            JButton boton = secondaryButton("Editar");
            boton.addActionListener(e -> conSeleccion(tabla, editar));
            panel.add(boton);
        }
        if (eliminar != null) {
            JButton boton = secondaryButton("Eliminar");
            boton.setForeground(new Color(150, 44, 36));
            boton.addActionListener(e -> conSeleccion(tabla, eliminar));
            panel.add(boton);
        }
        return panel;
    }
    private static void conSeleccion(JTable tabla, Runnable accion) {
        if (tabla.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(tabla, "Selecciona una fila de la tabla primero.",
                    "Sin selección", JOptionPane.WARNING_MESSAGE);
            return;
        }
        accion.run();
    }
    private static boolean dialogoGuardar(Component padre, JComponent contenido, String titulo) {
        Object[] opciones = {"Cancelar", "Guardar"};
        int opcion = JOptionPane.showOptionDialog(padre, contenido, titulo,
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, opciones, opciones[1]);
        return opcion == 1;
    }
    private static boolean dialogoEliminar(Component padre, String mensaje) {
        Object[] opciones = {"Cancelar", "Eliminar"};
        int opcion = JOptionPane.showOptionDialog(padre, mensaje, "Confirmar",
                JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, opciones, opciones[0]);
        return opcion == 1;
    }
    private static final class BadgeRenderer extends JLabel implements javax.swing.table.TableCellRenderer {
        private Color badgeBackground = Color.WHITE;

        BadgeRenderer() {
            setOpaque(false);
            setHorizontalAlignment(SwingConstants.CENTER);
            setFont(new Font("Arial", Font.BOLD, 12));
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int column) {
            setText(value == null ? "" : value.toString());
            boolean disponible = "Disponible".equals(value);
            badgeBackground = disponible ? new Color(211, 234, 210) : new Color(245, 212, 208);
            setForeground(disponible ? new Color(45, 95, 48) : new Color(152, 44, 36));
            return this;
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            Graphics2D g = (Graphics2D) graphics.create();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            FontMetrics fm = g.getFontMetrics();
            int width = fm.stringWidth(getText()) + 22;
            int height = fm.getHeight();
            int x = (getWidth() - width) / 2;
            int y = (getHeight() - height) / 2;
            g.setColor(badgeBackground);
            g.fillRoundRect(x, y, width, height, height, height);
            g.dispose();
            super.paintComponent(graphics);
        }
    }

    // ==================================================================
    //  Datos compartidos por todos los módulos
    // ==================================================================
    static final class Datos {
        /** Catálogo de muebles: {tipo, producto, descripción, precio} */
        static final List<Object[]> MUEBLES = new ArrayList<>();
        /** Existencias por producto: producto -> stock */
        static final Map<String, Integer> STOCK = new LinkedHashMap<>();
        /** Ventas registradas: {cliente, fecha, total, líneas {producto, cantidad}} */
        static final List<Object[]> VENTAS = new ArrayList<>();
        /** Gastos registrados: {fecha, concepto, monto, descripción} */
        static final List<Object[]> GASTOS = new ArrayList<>();
        /** Apartados: {id, cliente, monto, fecha} */
        static final List<Object[]> APARTADOS = new ArrayList<>();

        private Datos() {
        }

        static {
            MUEBLES.add(new Object[]{"Sala", "Sala Verona", "Sala de 3 piezas", 8500.0});
            MUEBLES.add(new Object[]{"Sala", "Sala Oslo", "Sala modular de 3 piezas", 9800.0});
            MUEBLES.add(new Object[]{"Comedor", "Comedor Roma", "Mesa con 6 sillas y buffet", 6100.0});
            MUEBLES.add(new Object[]{"Recámara", "Recámara Kyoto", "Cama king con 2 burós", 5300.0});
            MUEBLES.add(new Object[]{"Oficina", "Escritorio Nordic", "Escritorio con cajonera", 3700.0});
            MUEBLES.add(new Object[]{"Infantil", "Cuna Luna", "Cuna convertible para bebé", 2900.0});

            STOCK.put("Sala Verona", 5);
            STOCK.put("Sala Oslo", 4);
            STOCK.put("Comedor Roma", 5);
            STOCK.put("Recámara Kyoto", 0);
            STOCK.put("Escritorio Nordic", 8);
            STOCK.put("Cuna Luna", 3);

            APARTADOS.add(new Object[]{1, "Ruben", "$3,000.00", "2025-05-20"});
            APARTADOS.add(new Object[]{2, "Roberto", "$5,500.00", "2025-05-22"});
            APARTADOS.add(new Object[]{3, "Carolina", "$4,000.00", "2025-05-25"});
        }

        /** Devuelve el registro {tipo, producto, descripción, precio} o null si no existe. */
        static Object[] buscarProducto(String producto) {
            for (Object[] mueble : MUEBLES) {
                if (mueble[1].equals(producto)) {
                    return mueble;
                }
            }
            return null;
        }

        /** Indica si el producto ya está dado de alta en el catálogo. */
        static boolean existeProducto(String producto) {
            return buscarProducto(producto) != null;
        }

        /** Tipo (categoría) al que pertenece el producto ("" si no existe). */
        static String tipoDe(String producto) {
            Object[] mueble = buscarProducto(producto);
            return mueble == null ? "" : String.valueOf(mueble[0]);
        }

        /** Precio de catálogo del producto indicado (0 si no existe). */
        static double precioDe(String producto) {
            Object[] mueble = buscarProducto(producto);
            return mueble == null ? 0 : (Double) mueble[3];
        }

        /** Existencias actuales del producto (0 si no existe). */
        static int stockDe(String producto) {
            return STOCK.getOrDefault(producto, 0);
        }

        /** Da de alta el producto en el inventario si aún no existe. */
        static void asegurarProducto(String producto, int stockInicial) {
            if (producto != null && !producto.isBlank()) {
                STOCK.putIfAbsent(producto, stockInicial);
            }
        }

        /** Renombra un producto conservando sus existencias. */
        static void renombrarProducto(String anterior, String nuevo) {
            if (anterior == null || nuevo == null || anterior.equals(nuevo)) {
                return;
            }
            Integer stock = STOCK.remove(anterior);
            if (stock != null) {
                STOCK.put(nuevo, stock);
            } else {
                STOCK.putIfAbsent(nuevo, 0);
            }
        }

        /** Elimina el producto del catálogo y del inventario. */
        static void eliminarProducto(String producto) {
            if (producto == null) {
                return;
            }
            MUEBLES.removeIf(mueble -> mueble[1].equals(producto));
            STOCK.remove(producto);
        }

        static int siguienteIdApartado() {
            int maximo = 0;
            for (Object[] apartado : APARTADOS) {
                maximo = Math.max(maximo, (Integer) apartado[0]);
            }
            return maximo + 1;
        }

        static double totalVentas() {
            double total = 0;
            for (Object[] venta : VENTAS) {
                total += (Double) venta[2];
            }
            return total;
        }
    }

    /** Formatea un valor como importe en pesos. */
    static String dinero(double valor) {
        return "$" + new DecimalFormat("#,##0.00").format(valor);
    }

    /** Módulo capaz de refrescar su contenido cuando vuelve a mostrarse. */
    interface ModuloActualizable {
        void refrescar();
    }
    static class MueblesPanel extends JPanel implements ModuloActualizable {
        private final DefaultTableModel model = new DefaultTableModel(
                new Object[]{"ID", "Tipo", "Producto", "Descripción", "Precio", "Stock"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        private final JTable table = new JTable(model);
        private final JTextField txtTipo = field();
        private final JTextField txtProducto = field();
        private final JTextField txtDescripcion = field();
        private final JTextField txtPrecio = field();
        private final JTextField txtStock = field();

        MueblesPanel() {
            setLayout(new BorderLayout());
            setBackground(PANEL_BG);
            setBorder(BorderFactory.createEmptyBorder(20, 22, 20, 22));

            JPanel card = card(14);
            card.add(encabezado("Catálogo de muebles",
                    accionesDeTabla(table, this::editarMueble, this::eliminarMueble)), BorderLayout.NORTH);
            styleTable(table);
            card.add(scroll(table), BorderLayout.CENTER);
            card.add(construirFormulario(), BorderLayout.SOUTH);

            add(card, BorderLayout.CENTER);
            refrescar();
        }

        private JPanel construirFormulario() {
            JPanel contenedor = new JPanel(new BorderLayout(14, 0));
            contenedor.setOpaque(false);

            JPanel form = new JPanel(new GridBagLayout());
            form.setOpaque(false);
            GridBagConstraints gc = new GridBagConstraints();
            gc.insets = new Insets(4, 0, 4, 10);
            gc.fill = GridBagConstraints.HORIZONTAL;

            gc.gridy = 0;
            gc.gridx = 0; gc.weightx = 0; form.add(new JLabel("Tipo"), gc);
            gc.gridx = 1; gc.weightx = 1; form.add(txtTipo, gc);
            gc.gridx = 2; gc.weightx = 0; form.add(new JLabel("Producto"), gc);
            gc.gridx = 3; gc.weightx = 1; form.add(txtProducto, gc);

            gc.gridy = 1;
            gc.gridx = 0; gc.weightx = 0; form.add(new JLabel("Descripción"), gc);
            gc.gridx = 1; gc.weightx = 2; gc.gridwidth = 3; form.add(txtDescripcion, gc);
            gc.gridwidth = 1;

            gc.gridy = 2;
            gc.gridx = 0; gc.weightx = 0; form.add(new JLabel("Precio"), gc);
            gc.gridx = 1; gc.weightx = 1; form.add(txtPrecio, gc);
            gc.gridx = 2; gc.weightx = 0; form.add(new JLabel("Stock"), gc);
            gc.gridx = 3; gc.weightx = 1; form.add(txtStock, gc);

            JButton btnAgregar = primaryButton("+ Agregar producto");
            btnAgregar.addActionListener(e -> agregarMueble());
            JPanel boton = new JPanel(new BorderLayout());
            boton.setOpaque(false);
            boton.add(btnAgregar, BorderLayout.NORTH);

            contenedor.add(form, BorderLayout.CENTER);
            contenedor.add(boton, BorderLayout.EAST);
            return contenedor;
        }

        private void agregarMueble() {
            String tipo = txtTipo.getText().trim();
            String producto = txtProducto.getText().trim();
            String descripcion = txtDescripcion.getText().trim();
            String textoPrecio = txtPrecio.getText().trim().replace("$", "").replace(",", "");
            String textoStock = txtStock.getText().trim();
            if (tipo.isEmpty() || producto.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Escribe el tipo y el nombre del producto.",
                        "Datos incompletos", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (Datos.existeProducto(producto)) {
                JOptionPane.showMessageDialog(this, "Ya existe un producto llamado \"" + producto + "\".",
                        "Producto duplicado", JOptionPane.WARNING_MESSAGE);
                return;
            }
            double precio;
            try {
                precio = Double.parseDouble(textoPrecio);
                if (precio < 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Ingresa un precio válido.",
                        "Datos inválidos", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int stock = 0;
            if (!textoStock.isEmpty()) {
                try {
                    stock = Integer.parseInt(textoStock);
                    if (stock < 0) {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Ingresa un stock válido (0 o más).",
                            "Datos inválidos", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            Datos.MUEBLES.add(new Object[]{tipo, producto, descripcion, precio});
            Datos.STOCK.put(producto, stock);
            txtTipo.setText("");
            txtProducto.setText("");
            txtDescripcion.setText("");
            txtPrecio.setText("");
            txtStock.setText("");
            refrescar();
        }

        private void editarMueble() {
            int fila = table.getSelectedRow();
            Object[] mueble = Datos.MUEBLES.get(fila);
            String productoAnterior = String.valueOf(mueble[1]);

            JTextField tipo = field();
            JTextField producto = field();
            JTextField descripcion = field();
            JTextField precio = field();
            JTextField stock = field();
            tipo.setText(String.valueOf(mueble[0]));
            producto.setText(productoAnterior);
            descripcion.setText(String.valueOf(mueble[2]));
            precio.setText(String.valueOf(mueble[3]));
            stock.setText(String.valueOf(Datos.stockDe(productoAnterior)));

            JPanel formulario = new JPanel(new GridLayout(5, 2, 10, 10));
            formulario.add(new JLabel("Tipo"));
            formulario.add(tipo);
            formulario.add(new JLabel("Producto"));
            formulario.add(producto);
            formulario.add(new JLabel("Descripción"));
            formulario.add(descripcion);
            formulario.add(new JLabel("Precio"));
            formulario.add(precio);
            formulario.add(new JLabel("Stock"));
            formulario.add(stock);
            if (!dialogoGuardar(this, formulario, "Editar producto")) {
                return;
            }

            String nuevoTipo = tipo.getText().trim();
            String nuevoProducto = producto.getText().trim();
            if (nuevoTipo.isEmpty() || nuevoProducto.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El tipo y el producto no pueden quedar vacíos.",
                        "Datos incompletos", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (!productoAnterior.equals(nuevoProducto) && Datos.existeProducto(nuevoProducto)) {
                JOptionPane.showMessageDialog(this, "Ya existe un producto llamado \"" + nuevoProducto + "\".",
                        "Producto duplicado", JOptionPane.WARNING_MESSAGE);
                return;
            }
            double nuevoPrecio;
            try {
                nuevoPrecio = Double.parseDouble(precio.getText().trim().replace("$", "").replace(",", ""));
                if (nuevoPrecio < 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Ingresa un precio válido.",
                        "Datos inválidos", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int nuevoStock;
            try {
                nuevoStock = Integer.parseInt(stock.getText().trim());
                if (nuevoStock < 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Ingresa un stock válido (0 o más).",
                        "Datos inválidos", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Datos.renombrarProducto(productoAnterior, nuevoProducto);
            Datos.STOCK.put(nuevoProducto, nuevoStock);
            Datos.MUEBLES.set(fila, new Object[]{nuevoTipo, nuevoProducto,
                    descripcion.getText().trim(), nuevoPrecio});
            refrescar();
        }

        private void eliminarMueble() {
            int fila = table.getSelectedRow();
            Object[] mueble = Datos.MUEBLES.get(fila);
            String producto = String.valueOf(mueble[1]);
            if (!dialogoEliminar(this, "¿Eliminar \"" + producto + "\" del catálogo y del inventario?")) {
                return;
            }
            Datos.eliminarProducto(producto);
            refrescar();
        }

        @Override
        public void refrescar() {
            model.setRowCount(0);
            int id = 1;
            for (Object[] mueble : Datos.MUEBLES) {
                String producto = String.valueOf(mueble[1]);
                model.addRow(new Object[]{id++, mueble[0], producto, mueble[2],
                        dinero((Double) mueble[3]), Datos.stockDe(producto)});
            }
        }
    }

    static class VentasPanel extends JPanel implements ModuloActualizable {
        private final DatePickerField campoFecha = new DatePickerField(LocalDate.now().toString());
        private final JComboBox<String> cmbCliente = new JComboBox<>(
                new String[]{"Ruben", "Roberto", "Carolina", "Público general"});
        private final JComboBox<String> cmbProducto = new JComboBox<>();
        private final JTextField txtCantidad = field();
        private final JLabel lblTotal = new JLabel("$0.00");
        private final JLabel lblStockDisponible = new JLabel(" ");
        private final DefaultTableModel modeloDetalle = new DefaultTableModel(
                new Object[]{"Producto", "Cantidad", "Precio", "Subtotal"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        private final JTable tablaDetalle = new JTable(modeloDetalle);
        private final DefaultTableModel modeloHistorial = new DefaultTableModel(
                new Object[]{"Cliente", "Fecha", "Productos", "Total"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        private final JTable tablaHistorial = new JTable(modeloHistorial);

        VentasPanel() {
            setLayout(new BorderLayout(0, 16));
            setBackground(PANEL_BG);
            setBorder(BorderFactory.createEmptyBorder(20, 22, 20, 22));

            JPanel entrada = card(14);
            entrada.add(cardTitle("Registro de ventas"), BorderLayout.NORTH);
            entrada.add(construirEntrada(), BorderLayout.CENTER);
            entrada.add(construirAcciones(), BorderLayout.SOUTH);

            JPanel historial = card(14);
            historial.add(encabezado("Ventas registradas",
                    accionesDeTabla(tablaHistorial, null, this::eliminarVenta)), BorderLayout.NORTH);
            styleTable(tablaHistorial);
            historial.add(scroll(tablaHistorial), BorderLayout.CENTER);
            historial.setPreferredSize(new Dimension(0, 180));

            add(entrada, BorderLayout.CENTER);
            add(historial, BorderLayout.SOUTH);
            refrescar();
        }

        private JPanel construirEntrada() {
            JPanel contenedor = new JPanel();
            contenedor.setOpaque(false);
            contenedor.setLayout(new BoxLayout(contenedor, BoxLayout.Y_AXIS));

            JPanel formulario = new JPanel(new GridBagLayout());
            formulario.setOpaque(false);
            GridBagConstraints gc = new GridBagConstraints();
            gc.insets = new Insets(0, 0, 10, 12);
            gc.fill = GridBagConstraints.HORIZONTAL;
            gc.gridy = 0;
            gc.gridx = 0; gc.weightx = 0; formulario.add(new JLabel("Fecha"), gc);
            gc.gridx = 1; gc.weightx = 1; formulario.add(campoFecha, gc);
            gc.gridx = 2; gc.weightx = 0; formulario.add(new JLabel("Cliente"), gc);
            gc.gridx = 3; gc.weightx = 1; formulario.add(cmbCliente, gc);
            gc.gridx = 4; gc.weightx = 0; formulario.add(new JLabel("Total"), gc);
            lblTotal.setFont(new Font("Arial", Font.BOLD, 18));
            lblTotal.setForeground(TEXT_INK);
            gc.gridx = 5; gc.weightx = 1; formulario.add(lblTotal, gc);

            JPanel agregar = new JPanel(new GridBagLayout());
            agregar.setOpaque(false);
            GridBagConstraints ga = new GridBagConstraints();
            ga.insets = new Insets(0, 0, 10, 12);
            ga.fill = GridBagConstraints.HORIZONTAL;
            ga.gridy = 0;
            ga.gridx = 0; ga.weightx = 0; agregar.add(new JLabel("Producto"), ga);
            ga.gridx = 1; ga.weightx = 2; agregar.add(cmbProducto, ga);
            lblStockDisponible.setFont(new Font("Arial", Font.BOLD, 13));
            lblStockDisponible.setForeground(TEXT_MUTED);
            ga.gridx = 2; ga.weightx = 0; agregar.add(lblStockDisponible, ga);
            ga.gridx = 3; ga.weightx = 0; agregar.add(new JLabel("Cantidad"), ga);
            txtCantidad.setText("1");
            txtCantidad.setPreferredSize(new Dimension(80, 32));
            ga.gridx = 4; ga.weightx = 1; agregar.add(txtCantidad, ga);
            JButton btnAnadir = secondaryButton("+ Añadir");
            btnAnadir.addActionListener(e -> anadirDetalle());
            ga.gridx = 5; ga.weightx = 0; ga.insets = new Insets(0, 0, 10, 0);
            agregar.add(btnAnadir, ga);
            cmbProducto.addActionListener(e -> actualizarStockDisponible());

            styleTable(tablaDetalle);
            contenedor.add(formulario);
            contenedor.add(agregar);
            contenedor.add(scroll(tablaDetalle));
            contenedor.add(accionesDeTabla(tablaDetalle, null, this::eliminarLinea));
            return contenedor;
        }

        private JPanel construirAcciones() {
            JPanel acciones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
            acciones.setOpaque(false);
            JButton btnCancelar = secondaryButton("Cancelar");
            btnCancelar.addActionListener(e -> limpiarDetalle());
            JButton btnGuardar = primaryButton("Guardar");
            btnGuardar.addActionListener(e -> guardar());
            acciones.add(btnCancelar);
            acciones.add(btnGuardar);
            return acciones;
        }

        private void anadirDetalle() {
            String producto = (String) cmbProducto.getSelectedItem();
            if (producto == null) {
                return;
            }
            int cantidad;
            try {
                cantidad = Integer.parseInt(txtCantidad.getText().trim());
                if (cantidad <= 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Ingresa una cantidad válida.",
                        "Datos inválidos", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int disponible = Datos.stockDe(producto) - cantidadEnDetalle(producto);
            if (cantidad > disponible) {
                JOptionPane.showMessageDialog(this,
                        "Stock insuficiente de \"" + producto + "\" (disponible: " + disponible + ").",
                        "Sin existencias", JOptionPane.ERROR_MESSAGE);
                return;
            }
            double precio = Datos.precioDe(producto);
            modeloDetalle.addRow(new Object[]{producto, cantidad, dinero(precio), dinero(precio * cantidad)});
            txtCantidad.setText("1");
            actualizarTotal();
        }

        /** Cantidad del producto que ya está agregada al detalle de la venta. */
        private int cantidadEnDetalle(String producto) {
            int total = 0;
            for (int fila = 0; fila < modeloDetalle.getRowCount(); fila++) {
                if (String.valueOf(modeloDetalle.getValueAt(fila, 0)).equals(producto)) {
                    total += (Integer) modeloDetalle.getValueAt(fila, 1);
                }
            }
            return total;
        }

        /** Refleja en pantalla las existencias del producto seleccionado. */
        private void actualizarStockDisponible() {
            String producto = (String) cmbProducto.getSelectedItem();
            lblStockDisponible.setText(producto == null
                    ? " " : "Stock disponible: " + Datos.stockDe(producto));
        }

        private double totalDetalle() {
            double total = 0;
            for (int fila = 0; fila < modeloDetalle.getRowCount(); fila++) {
                total += Datos.precioDe(String.valueOf(modeloDetalle.getValueAt(fila, 0)))
                        * (Integer) modeloDetalle.getValueAt(fila, 1);
            }
            return total;
        }

        private void actualizarTotal() {
            lblTotal.setText(dinero(totalDetalle()));
        }

        private void guardar() {
            if (modeloDetalle.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "Añade al menos un producto.",
                        "Venta vacía", JOptionPane.WARNING_MESSAGE);
                return;
            }
            for (int fila = 0; fila < modeloDetalle.getRowCount(); fila++) {
                String producto = String.valueOf(modeloDetalle.getValueAt(fila, 0));
                int cantidad = (Integer) modeloDetalle.getValueAt(fila, 1);
                int disponible = Datos.stockDe(producto);
                if (cantidad > disponible) {
                    JOptionPane.showMessageDialog(this,
                            "Stock insuficiente de \"" + producto + "\" (disponible: " + disponible + ").",
                            "Sin existencias", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            double total = 0;
            List<Object[]> lineas = new ArrayList<>();
            for (int fila = 0; fila < modeloDetalle.getRowCount(); fila++) {
                String producto = String.valueOf(modeloDetalle.getValueAt(fila, 0));
                int cantidad = (Integer) modeloDetalle.getValueAt(fila, 1);
                total += Datos.precioDe(producto) * cantidad;
                Datos.STOCK.put(producto, Datos.stockDe(producto) - cantidad);
                lineas.add(new Object[]{producto, cantidad});
            }
            Datos.VENTAS.add(new Object[]{cmbCliente.getSelectedItem(), campoFecha.getDate(), total, lineas});
            JOptionPane.showMessageDialog(this, "Venta registrada: " + dinero(total));
            limpiarDetalle();
            refrescar();
        }

        private void limpiarDetalle() {
            modeloDetalle.setRowCount(0);
            txtCantidad.setText("1");
            actualizarTotal();
        }

        private void eliminarLinea() {
            modeloDetalle.removeRow(tablaDetalle.getSelectedRow());
            actualizarTotal();
        }

        @SuppressWarnings("unchecked")
        private void eliminarVenta() {
            int fila = tablaHistorial.getSelectedRow();
            Object[] venta = Datos.VENTAS.get(fila);
            if (!dialogoEliminar(this, "¿Eliminar la venta de \"" + venta[0] + "\" por "
                    + dinero((Double) venta[2]) + "? El stock se devolverá al inventario.")) {
                return;
            }
            for (Object[] linea : (List<Object[]>) venta[3]) {
                String producto = String.valueOf(linea[0]);
                int cantidad = (Integer) linea[1];
                Datos.STOCK.put(producto, Datos.stockDe(producto) + cantidad);
            }
            Datos.VENTAS.remove(fila);
            refrescar();
        }

        @Override
        public void refrescar() {
            Object seleccionado = cmbProducto.getSelectedItem();
            cmbProducto.removeAllItems();
            for (Object[] mueble : Datos.MUEBLES) {
                cmbProducto.addItem(String.valueOf(mueble[1]));
            }
            if (seleccionado != null) {
                cmbProducto.setSelectedItem(seleccionado);
            }
            actualizarStockDisponible();
            modeloHistorial.setRowCount(0);
            for (Object[] venta : Datos.VENTAS) {
                modeloHistorial.addRow(new Object[]{venta[0], venta[1], resumenProductos(venta),
                        dinero((Double) venta[2])});
            }
        }

        /** Detalle legible de los productos incluidos en una venta. */
        @SuppressWarnings("unchecked")
        private String resumenProductos(Object[] venta) {
            StringBuilder resumen = new StringBuilder();
            for (Object[] linea : (List<Object[]>) venta[3]) {
                if (resumen.length() > 0) {
                    resumen.append(", ");
                }
                resumen.append(linea[0]).append(" x").append(linea[1]);
            }
            return resumen.toString();
        }
    }

    static class GastosPanel extends JPanel implements ModuloActualizable {
        private final DatePickerField campoFecha = new DatePickerField(LocalDate.now().toString());
        private final JComboBox<String> cmbConcepto = new JComboBox<>(
                new String[]{"Luz", "Agua", "Internet", "Transporte", "Nómina", "Otro"});
        private final JTextField txtMonto = field();
        private final JTextArea txtDescripcion = new JTextArea(3, 20);
        private final DefaultTableModel modelo = new DefaultTableModel(
                new Object[]{"Fecha", "Concepto", "Monto", "Descripción"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        private final JTable tabla = new JTable(modelo);

        GastosPanel() {
            setLayout(new BorderLayout(0, 16));
            setBackground(PANEL_BG);
            setBorder(BorderFactory.createEmptyBorder(20, 22, 20, 22));

            JPanel entrada = card(14);
            entrada.add(cardTitle("Registro de gastos"), BorderLayout.NORTH);
            entrada.add(construirFormulario(), BorderLayout.CENTER);
            entrada.add(construirAcciones(), BorderLayout.SOUTH);

            JPanel historial = card(14);
            historial.add(encabezado("Gastos registrados",
                    accionesDeTabla(tabla, this::editarGasto, this::eliminarGasto)), BorderLayout.NORTH);
            styleTable(tabla);
            historial.add(scroll(tabla), BorderLayout.CENTER);
            historial.setPreferredSize(new Dimension(0, 180));

            add(entrada, BorderLayout.CENTER);
            add(historial, BorderLayout.SOUTH);
            refrescar();
        }

        private JPanel construirFormulario() {
            JPanel form = new JPanel(new GridBagLayout());
            form.setOpaque(false);
            GridBagConstraints gc = new GridBagConstraints();
            gc.insets = new Insets(0, 0, 10, 12);
            gc.fill = GridBagConstraints.HORIZONTAL;
            gc.gridy = 0;
            gc.gridx = 0; gc.weightx = 0; form.add(new JLabel("Fecha"), gc);
            gc.gridx = 1; gc.weightx = 1; form.add(campoFecha, gc);
            gc.gridx = 2; gc.weightx = 0; form.add(new JLabel("Concepto"), gc);
            gc.gridx = 3; gc.weightx = 1; form.add(cmbConcepto, gc);
            gc.gridx = 4; gc.weightx = 0; form.add(new JLabel("Monto"), gc);
            gc.gridx = 5; gc.weightx = 1; form.add(txtMonto, gc);

            gc.gridx = 0; gc.gridy = 1; gc.weightx = 0; gc.insets = new Insets(0, 0, 0, 12);
            form.add(new JLabel("Descripción"), gc);
            txtDescripcion.setFont(new Font("Arial", Font.PLAIN, 14));
            txtDescripcion.setBackground(FIELD_BG);
            txtDescripcion.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(CARD_BORDER),
                    BorderFactory.createEmptyBorder(6, 8, 6, 8)));
            gc.gridx = 1; gc.gridwidth = 5; gc.weightx = 1;
            form.add(new JScrollPane(txtDescripcion), gc);
            return form;
        }

        private JPanel construirAcciones() {
            JPanel acciones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
            acciones.setOpaque(false);
            JButton btnCancelar = secondaryButton("Cancelar");
            btnCancelar.addActionListener(e -> limpiar());
            JButton btnGuardar = primaryButton("Guardar");
            btnGuardar.addActionListener(e -> guardar());
            acciones.add(btnCancelar);
            acciones.add(btnGuardar);
            return acciones;
        }

        private void guardar() {
            String textoMonto = txtMonto.getText().trim().replace("$", "").replace(",", "");
            double monto;
            try {
                monto = Double.parseDouble(textoMonto);
                if (monto <= 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Ingresa un monto válido.",
                        "Datos inválidos", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Datos.GASTOS.add(new Object[]{campoFecha.getDate(), cmbConcepto.getSelectedItem(),
                    monto, txtDescripcion.getText().trim()});
            JOptionPane.showMessageDialog(this, "Gasto registrado: " + dinero(monto));
            limpiar();
            refrescar();
        }

        private void limpiar() {
            txtMonto.setText("");
            txtDescripcion.setText("");
        }

        private void editarGasto() {
            int fila = tabla.getSelectedRow();
            Object[] gasto = Datos.GASTOS.get(fila);
            DatePickerField fecha = new DatePickerField(String.valueOf(gasto[0]));
            JComboBox<String> concepto = new JComboBox<>(
                    new String[]{"Luz", "Agua", "Internet", "Transporte", "Nómina", "Otro"});
            concepto.setSelectedItem(String.valueOf(gasto[1]));
            JTextField monto = field();
            monto.setText(String.valueOf(gasto[2]));
            JTextArea descripcion = new JTextArea(String.valueOf(gasto[3]), 3, 18);

            JPanel formulario = new JPanel(new GridBagLayout());
            GridBagConstraints gc = new GridBagConstraints();
            gc.insets = new Insets(0, 0, 8, 10);
            gc.fill = GridBagConstraints.HORIZONTAL;
            gc.gridx = 0; gc.gridy = 0; formulario.add(new JLabel("Fecha"), gc);
            gc.gridx = 1; formulario.add(fecha, gc);
            gc.gridx = 0; gc.gridy = 1; formulario.add(new JLabel("Concepto"), gc);
            gc.gridx = 1; formulario.add(concepto, gc);
            gc.gridx = 0; gc.gridy = 2; formulario.add(new JLabel("Monto"), gc);
            gc.gridx = 1; formulario.add(monto, gc);
            gc.gridx = 0; gc.gridy = 3; formulario.add(new JLabel("Descripción"), gc);
            gc.gridx = 1; formulario.add(new JScrollPane(descripcion), gc);
            if (!dialogoGuardar(this, formulario, "Editar gasto")) {
                return;
            }

            double valor;
            try {
                valor = Double.parseDouble(monto.getText().trim().replace("$", "").replace(",", ""));
                if (valor <= 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Ingresa un monto válido.",
                        "Datos inválidos", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Datos.GASTOS.set(fila, new Object[]{fecha.getDate(), concepto.getSelectedItem(), valor,
                    descripcion.getText().trim()});
            refrescar();
        }

        private void eliminarGasto() {
            int fila = tabla.getSelectedRow();
            Object[] gasto = Datos.GASTOS.get(fila);
            if (!dialogoEliminar(this, "¿Eliminar el gasto de " + dinero((Double) gasto[2]) + "?")) {
                return;
            }
            Datos.GASTOS.remove(fila);
            refrescar();
        }

        @Override
        public void refrescar() {
            modelo.setRowCount(0);
            for (Object[] gasto : Datos.GASTOS) {
                modelo.addRow(new Object[]{gasto[0], gasto[1], dinero((Double) gasto[2]), gasto[3]});
            }
        }
    }

    static class ApartadosPanel extends JPanel implements ModuloActualizable {
        private final DefaultTableModel modelo = new DefaultTableModel(
                new Object[]{"ID", "Cliente", "Monto", "Fecha"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        private final JTable tabla = new JTable(modelo);

        ApartadosPanel() {
            setLayout(new BorderLayout());
            setBackground(PANEL_BG);
            setBorder(BorderFactory.createEmptyBorder(20, 22, 20, 22));

            JPanel card = card(14);
            card.add(encabezado("Apartados de clientes",
                    accionesDeTabla(tabla, this::editarApartado, this::eliminarApartado)), BorderLayout.NORTH);
            styleTable(tabla);
            card.add(scroll(tabla), BorderLayout.CENTER);

            JPanel acciones = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
            acciones.setOpaque(false);
            JButton btnNuevo = primaryButton("+ Nuevo apartado");
            btnNuevo.addActionListener(e -> nuevoApartado());
            acciones.add(btnNuevo);
            card.add(acciones, BorderLayout.SOUTH);

            add(card, BorderLayout.CENTER);
            refrescar();
        }

        private void nuevoApartado() {
            JTextField cliente = field();
            JTextField monto = field();
            DatePickerField fecha = new DatePickerField(LocalDate.now().toString());
            JPanel formulario = new JPanel(new GridLayout(3, 2, 10, 10));
            formulario.add(new JLabel("Cliente"));
            formulario.add(cliente);
            formulario.add(new JLabel("Monto"));
            formulario.add(monto);
            formulario.add(new JLabel("Fecha"));
            formulario.add(fecha);
            if (!dialogoGuardar(this, formulario, "Nuevo apartado")) {
                return;
            }
            String nombre = cliente.getText().trim();
            String textoMonto = monto.getText().trim().replace("$", "").replace(",", "");
            if (nombre.isEmpty() || textoMonto.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Completa cliente y monto.",
                        "Datos incompletos", JOptionPane.WARNING_MESSAGE);
                return;
            }
            double valor;
            try {
                valor = Double.parseDouble(textoMonto);
                if (valor < 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Ingresa un monto válido.",
                        "Datos inválidos", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Datos.APARTADOS.add(new Object[]{Datos.siguienteIdApartado(), nombre, dinero(valor), fecha.getDate()});
            refrescar();
        }

        private void editarApartado() {
            int fila = tabla.getSelectedRow();
            Object[] apartado = Datos.APARTADOS.get(fila);
            JTextField cliente = field();
            cliente.setText(String.valueOf(apartado[1]));
            JTextField monto = field();
            monto.setText(String.valueOf(apartado[2]));
            DatePickerField fecha = new DatePickerField(String.valueOf(apartado[3]));

            JPanel formulario = new JPanel(new GridLayout(3, 2, 10, 10));
            formulario.add(new JLabel("Cliente"));
            formulario.add(cliente);
            formulario.add(new JLabel("Monto"));
            formulario.add(monto);
            formulario.add(new JLabel("Fecha"));
            formulario.add(fecha);
            if (!dialogoGuardar(this, formulario, "Editar apartado")) {
                return;
            }

            String nombre = cliente.getText().trim();
            double valor;
            try {
                valor = Double.parseDouble(monto.getText().trim().replace("$", "").replace(",", ""));
                if (valor < 0 || nombre.isEmpty()) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Revisa el cliente y el monto.",
                        "Datos inválidos", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Datos.APARTADOS.set(fila, new Object[]{apartado[0], nombre, dinero(valor), fecha.getDate()});
            refrescar();
        }

        private void eliminarApartado() {
            int fila = tabla.getSelectedRow();
            Object[] apartado = Datos.APARTADOS.get(fila);
            if (!dialogoEliminar(this, "¿Eliminar el apartado de \"" + apartado[1] + "\"?")) {
                return;
            }
            Datos.APARTADOS.remove(fila);
            refrescar();
        }

        @Override
        public void refrescar() {
            modelo.setRowCount(0);
            for (Object[] apartado : Datos.APARTADOS) {
                modelo.addRow(apartado);
            }
        }
    }

    static class ImpuestosPanel extends JPanel implements ModuloActualizable {
        private final JTextField txtIva = field();
        private final JLabel lblSubtotal = new JLabel("$0.00");
        private final JLabel lblIva = new JLabel("$0.00");
        private final JLabel lblTotal = new JLabel("$0.00");

        ImpuestosPanel() {
            setLayout(new BorderLayout());
            setBackground(PANEL_BG);
            setBorder(BorderFactory.createEmptyBorder(20, 22, 20, 22));

            JPanel card = card(14);
            card.add(cardTitle("Cálculo de impuestos"), BorderLayout.NORTH);
            card.add(construirContenido(), BorderLayout.CENTER);

            add(card, BorderLayout.CENTER);
            refrescar();
        }

        private JPanel construirContenido() {
            JPanel contenido = new JPanel();
            contenido.setOpaque(false);
            contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));

            JPanel iva = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
            iva.setOpaque(false);
            iva.add(new JLabel("IVA (%)"));
            txtIva.setText("16");
            txtIva.setPreferredSize(new Dimension(80, 32));
            iva.add(txtIva);

            JPanel filas = new JPanel(new GridLayout(3, 2, 8, 8));
            filas.setOpaque(false);
            filas.add(new JLabel("Subtotal (ventas)"));
            filas.add(lblSubtotal);
            filas.add(new JLabel("IVA"));
            filas.add(lblIva);
            filas.add(new JLabel("Total"));
            filas.add(lblTotal);
            for (JLabel etiqueta : new JLabel[]{lblSubtotal, lblIva, lblTotal}) {
                etiqueta.setFont(new Font("Arial", Font.BOLD, 18));
                etiqueta.setForeground(TEXT_INK);
            }

            JButton btnCalcular = primaryButton("Calcular");
            btnCalcular.addActionListener(e -> calcular());
            JPanel acciones = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
            acciones.setOpaque(false);
            acciones.add(btnCalcular);

            JLabel nota = new JLabel("*El cálculo se realiza automáticamente a partir de las ventas registradas.");
            nota.setFont(new Font("Arial", Font.ITALIC, 12));
            nota.setForeground(TEXT_MUTED);

            contenido.add(iva);
            contenido.add(Box.createVerticalStrut(12));
            contenido.add(filas);
            contenido.add(Box.createVerticalStrut(14));
            contenido.add(acciones);
            contenido.add(Box.createVerticalStrut(14));
            contenido.add(nota);
            return contenido;
        }

        private void calcular() {
            double porcentaje;
            try {
                porcentaje = Double.parseDouble(txtIva.getText().trim()) / 100.0;
                if (porcentaje < 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Ingresa un IVA válido.",
                        "Datos inválidos", JOptionPane.ERROR_MESSAGE);
                return;
            }
            double subtotal = Datos.totalVentas();
            lblSubtotal.setText(dinero(subtotal));
            lblIva.setText(dinero(subtotal * porcentaje));
            lblTotal.setText(dinero(subtotal * (1 + porcentaje)));
        }

        @Override
        public void refrescar() {
            calcular();
        }
    }

    static class InventarioPanel extends JPanel implements ModuloActualizable {
        private final DefaultTableModel modelo = new DefaultTableModel(
                new Object[]{"Tipo", "Producto", "Stock", "Estado"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        private final JTable tabla = new JTable(modelo);
        private final JComboBox<String> cmbProducto = new JComboBox<>();
        private final JTextField txtStock = field();

        InventarioPanel() {
            setLayout(new BorderLayout());
            setBackground(PANEL_BG);
            setBorder(BorderFactory.createEmptyBorder(20, 22, 20, 22));

            JPanel card = card(14);
            card.add(encabezado("Inventario de productos",
                    accionesDeTabla(tabla, null, this::eliminarProducto)), BorderLayout.NORTH);
            styleTable(tabla);
            tabla.getColumnModel().getColumn(3).setCellRenderer(new BadgeRenderer());
            card.add(scroll(tabla), BorderLayout.CENTER);
            card.add(construirAcciones(), BorderLayout.SOUTH);

            add(card, BorderLayout.CENTER);
            refrescar();
        }

        private JPanel construirAcciones() {
            JPanel acciones = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
            acciones.setOpaque(false);
            acciones.add(new JLabel("Producto"));
            cmbProducto.setPreferredSize(new Dimension(150, 32));
            acciones.add(cmbProducto);
            acciones.add(new JLabel("Stock"));
            txtStock.setPreferredSize(new Dimension(80, 32));
            acciones.add(txtStock);
            JButton btnActualizar = primaryButton("Actualizar");
            btnActualizar.addActionListener(e -> actualizar());
            acciones.add(btnActualizar);
            return acciones;
        }

        private void actualizar() {
            String producto = (String) cmbProducto.getSelectedItem();
            if (producto == null) {
                return;
            }
            int stock;
            try {
                stock = Integer.parseInt(txtStock.getText().trim());
                if (stock < 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Ingresa un stock válido.",
                        "Datos inválidos", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Datos.STOCK.put(producto, stock);
            txtStock.setText("");
            refrescar();
        }

        private void eliminarProducto() {
            int fila = tabla.getSelectedRow();
            String producto = String.valueOf(modelo.getValueAt(fila, 1));
            if (!dialogoEliminar(this, "¿Eliminar \"" + producto + "\" del inventario y del catálogo?\n"
                    + "Las ventas ya registradas no se modifican.")) {
                return;
            }
            Datos.eliminarProducto(producto);
            refrescar();
        }

        @Override
        public void refrescar() {
            Object seleccionado = cmbProducto.getSelectedItem();
            cmbProducto.removeAllItems();
            modelo.setRowCount(0);
            for (Object[] mueble : Datos.MUEBLES) {
                String producto = String.valueOf(mueble[1]);
                int stock = Datos.stockDe(producto);
                cmbProducto.addItem(producto);
                modelo.addRow(new Object[]{mueble[0], producto, stock,
                        stock > 0 ? "Disponible" : "Agotado"});
            }
            if (seleccionado != null) {
                cmbProducto.setSelectedItem(seleccionado);
            }
        }
    }
}
