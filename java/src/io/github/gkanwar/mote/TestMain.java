package io.github.gkanwar.mote;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;

import java.util.*;

import org.scilab.forge.jlatexmath.*;

import io.github.gkanwar.mote.expr.*;

public class TestMain extends JFrame {
  /**
   * Label component with rendering of current formula.
   */
  private JLabel fLabel;
  /**
   * Formula context, containing expression root.
   */
  private Context context;
  /**
   * Mapping of Expr to rendered TrackingBoxes.
   */
  private RenderMap rm;
  /**
   * Container of Formula structure with laid-out components.
   */
  private Formula f;

  /**
   * List of all Expr objects which should be touchable.
   */
  java.util.List<Expr> exprs;


  /**
   * Equation rendering point size.
   */
  private static final float ptSize = 50.0f;

  /**
   * Entry point.
   */
  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
        public void run() {
          TestMain tm = new TestMain();
          tm.setVisible(true);
        }
      });
  }

  /**
   * Helper function to render a TeXFormula object into a BufferedImage.
   */
  public BufferedImage formulaToImage(TeXFormula formula) {
    TeXIcon icon = formula.new TeXIconBuilder().setStyle(TeXConstants.STYLE_DISPLAY).setSize(ptSize).build();
    icon.setInsets(new Insets(5, 5, 5, 5));
    BufferedImage pic = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2 = pic.createGraphics();
    icon.paintIcon(null, g2, 0, 0);
    return pic;
  }

  /**
   * Render Formula `f` to label `fLabel`.
   */
  public void renderFormulaToLabel() {
    ImageIcon ii = new ImageIcon(formulaToImage(f.tf));
    fLabel.setIcon(ii);
    // Maybe repaint?
    Dimension size = fLabel.getPreferredSize();
    fLabel.setBounds(0, 0, size.width, size.height);
  }

  /**
   * Use current RenderMap to add buttons.
   */
  public void addButtons() {
    for (Expr e : exprs) {
      org.scilab.forge.jlatexmath.TrackingBox b = rm.get(e);
      float w = b.lastW;
      float h = b.lastH;
      float x = b.lastX;
      float y = b.lastY-b.lastH;
      ClearButton but = new ClearButton();
      but.setLayout(null); // Absolute coordinates
      but.setBounds((int)(x*ptSize), (int)(y*ptSize),
                    (int)(w*ptSize), (int)(h*ptSize));
      but.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent event) {
            System.out.println("Clicked: " + e);
            e.toggleSelected();
          }
        });

      add(but);
    }
  }

  public TestMain() {
    // Init objects
    fLabel = new JLabel();
    exprs = new ArrayList<Expr>();
    context = new Context();
    
    // Set up swing GUI
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setTitle("mote");

    // Build a test expression
    Expr a = new VarExpr(context.makeGlobal("a"));
    Expr b = new VarExpr(context.makeGlobal("b"));
    Expr c = new VarExpr(context.makeGlobal("c"));
    Expr add = new AddExpr(b, c);
    Expr mul = new MulExpr(a, add);
    exprs.add(a);
    exprs.add(b);
    exprs.add(c);
    exprs.add(add);
    exprs.add(mul);
    context.setRoot(mul);

    // Render context to formula
    f = new Formula(new TeXFormula());
    rm = new RenderMap();
    context.buildFormula(f, rm);
    // Render formula into fLabel
    renderFormulaToLabel();

    // Add all objects
    add(fLabel);
    addButtons();

    // Overall window shape
    setSize(400, 300);
    setLocationRelativeTo(null); // Center the frame

    // Use absolute layout in the frame
    setLayout(null);

    // DEBUG: Print current expression
    System.out.println(context.toString());
  }
}
