import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.JSpinner;
import javax.swing.SwingUtilities;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Preview {
    private static Project1.MenuFrame frame;

    public static void main(String[] args) throws Exception {
        if (GraphicsEnvironment.isHeadless()) {
            System.out.println("headless");
            return;
        }
        SwingUtilities.invokeAndWait(() -> {
            try {
                frame = new Project1.MenuFrame("Dani Admin");
                frame.setSize(1200, 700);
                frame.setVisible(true);
                frame.validate();

                click("Ventas");
                frame.validate();
                capturar(frame.getContentPane(), ".tmp_c/ventas.png");

                AbstractButton calendario = findByTooltip(frame.getContentPane(), "Seleccionar fecha");
                calendario.doClick();
                frame.validate();

                Container root = frame.getRootPane();
                List<JSpinner> spinners = new ArrayList<>();
                recogerSpinners(root, spinners);
                System.out.println("spinners en el calendario = " + spinners.size());
                if (spinners.size() >= 2) {
                    JSpinner mes = spinners.get(0);
                    System.out.println("mes inicial = " + mes.getValue() + " / días visibles = " + contarDias(root));
                    mes.setValue("marzo");
                    System.out.println("marzo      -> días visibles = " + contarDias(root));
                    mes.setValue("febrero");
                    System.out.println("febrero    -> días visibles = " + contarDias(root));
                    mes.setValue("diciembre");
                    System.out.println("diciembre  -> días visibles = " + contarDias(root));
                }
                frame.validate();
                capturar(root, ".tmp_c/calendario.png");

                click("Muebles");
                frame.validate();
                capturar(frame.getContentPane(), ".tmp_c/muebles.png");
                click("Inventario");
                frame.validate();
                capturar(frame.getContentPane(), ".tmp_c/inventario.png");
                click("Apartados");
                frame.validate();
                capturar(frame.getContentPane(), ".tmp_c/apartados.png");
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        System.out.println("DONE");
        System.exit(0);
    }

    private static void recogerSpinners(Container root, List<JSpinner> out) {
        for (Component child : root.getComponents()) {
            if (child instanceof JSpinner spinner) {
                out.add(spinner);
            }
            if (child instanceof Container container) {
                recogerSpinners(container, out);
            }
        }
    }

    private static int contarDias(Container root) {
        int total = 0;
        for (Component child : root.getComponents()) {
            if (child instanceof AbstractButton button && button.getText() != null
                    && button.getText().matches("\\d+")) {
                total++;
            }
            if (child instanceof Container container) {
                total += contarDias(container);
            }
        }
        return total;
    }

    private static void click(String text) {
        AbstractButton button = find(frame.getContentPane(), text);
        if (button == null) {
            throw new IllegalStateException("no encontrado: " + text);
        }
        button.doClick();
    }

    private static void capturar(Container content, String path) throws Exception {
        BufferedImage image = new BufferedImage(
                content.getWidth(), content.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        content.printAll(g);
        g.dispose();
        ImageIO.write(image, "png", new File(path));
        System.out.println("saved " + path + " (" + content.getWidth() + "x" + content.getHeight() + ")");
    }

    private static AbstractButton findByTooltip(Container root, String tooltip) {
        for (Component child : root.getComponents()) {
            if (child instanceof AbstractButton button && tooltip.equals(button.getToolTipText())) {
                return button;
            }
            if (child instanceof Container container) {
                AbstractButton found = findByTooltip(container, tooltip);
                if (found != null) {
                    return found;
                }
            }
        }
        return null;
    }

    private static AbstractButton find(Container root, String text) {
        for (Component child : root.getComponents()) {
            if (child instanceof AbstractButton button && text.equals(button.getText())) {
                return button;
            }
            if (child instanceof Container container) {
                AbstractButton found = find(container, text);
                if (found != null) {
                    return found;
                }
            }
        }
        return null;
    }
}
