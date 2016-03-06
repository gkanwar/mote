package io.github.gkanwar.mote;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;

import java.util.*;

import org.scilab.forge.jlatexmath.*;

import io.github.gkanwar.mote.expr.*;

public class TestMain extends JFrame {
  JLabel iconLabel1;
  JLabel iconLabel2;
  Context context;
  RenderMap rm;
  Formula f;
  java.util.List<Expr> exprs;

  private static final float ptSize = 50.0f;
  
  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
        public void run() {
          TestMain tm = new TestMain();
          tm.setVisible(true);
        }
      });
  }

  public BufferedImage formulaToImage(TeXFormula formula) {
    TeXIcon icon = formula.new TeXIconBuilder().setStyle(TeXConstants.STYLE_DISPLAY).setSize(ptSize).build();
    icon.setInsets(new Insets(5, 5, 5, 5));
    BufferedImage pic = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2 = pic.createGraphics();
    // g2.setColor(Color.white);
    // g2.fillRect(0,0,icon.getIconWidth(),icon.getIconHeight());
    iconLabel1 = new JLabel();
    iconLabel1.setForeground(new Color(0, 0, 0));
    icon.paintIcon(iconLabel1, g2, 0, 0);
    return pic;
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
      // float x = b.lastX;
      // float y = b.lastY;
      ClearButton but = new ClearButton();
      but.setLayout(null); // Absolute coordinates
      but.setBounds((int)(x*ptSize), (int)(y*ptSize),
                    (int)(w*ptSize), (int)(h*ptSize));

      System.out.println("button: " + (int)(x*ptSize) + "," + (int)(y*ptSize) + ","
                         + (int)(w*ptSize) + "," + (int)(h*ptSize));

      but.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent event) {
            System.out.println("Clicked: " + e);
          }
        });

      add(but);
    }
  }

  public TestMain() {
    exprs = new ArrayList<Expr>();
    context = new Context();
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
    
    // Build tagged atom tree for \int a + b = c
    // Map<String, org.scilab.forge.jlatexmath.Box> tags =
    //     new HashMap<String, org.scilab.forge.jlatexmath.Box>();

    // Atom a = new CharAtom('a', null /* default text style */);
    // Atom b = new CharAtom('b', null);
    // Atom c = new CharAtom('c', null);
    // Atom integral = SymbolAtom.get("int");
    // Atom plus = SymbolAtom.get("plus");
    // Atom equals = SymbolAtom.get("equals");
    // TeXFormula tf = new TeXFormula();
    // tf.add(new TaggedAtom(integral, "int", tags));
    // tf.add(new TaggedAtom(a, "a", tags));
    // tf.add(new TaggedAtom(plus, "plus", tags));
    // tf.add(new TaggedAtom(b, "b", tags));
    // tf.add(new TaggedAtom(equals, "equals", tags));
    // tf.add(new TaggedAtom(c, "c", tags));


    // Set up swing GUI
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setTitle("mote");
    
    // iconLabel1.setVerticalTextPosition(JLabel.BOTTOM);
    // iconLabel1.setHorizontalTextPosition(JLabel.CENTER);
    // iconLabel1.setHorizontalAlignment(JLabel.CENTER);

    // String latex = "\\int a + b = c";
    // String latex = "\\begin{array}{lr}\\mbox{\\textcolor{Blue}{Russian}}&\\mbox{\\textcolor{Melon}{Greek}}\\\\";
    // latex += "\\mbox{" + "привет мир".toUpperCase() + "}&\\mbox{" + "γειά κόσμο".toUpperCase() + "}\\\\";
    // latex += "\\mbox{привет мир}&\\mbox{γειά κόσμο}\\\\";
    // latex += "\\mathbf{\\mbox{привет мир}}&\\mathbf{\\mbox{γειά κόσμο}}\\\\";
    // latex += "\\mathit{\\mbox{привет мир}}&\\mathit{\\mbox{γειά κόσμο}}\\\\";
    // latex += "\\mathsf{\\mbox{привет мир}}&\\mathsf{\\mbox{γειά κόσμο}}\\\\";
    // latex += "&\\\\";
    // latex += "\\mbox{\\textcolor{Salmon}{Bulgarian}}&\\mbox{\\textcolor{Tan}{Serbian}}\\\\";
    // latex += "\\mbox{здравей свят}&\\mbox{Хелло уорлд}\\\\";
    // latex += "&\\\\";
    // latex += "\\mbox{\\textcolor{Turquoise}{Bielorussian}}&\\mbox{\\textcolor{LimeGreen}{Ukrainian}}\\\\";
    // latex += "\\mbox{прывітаньне Свет}&\\mbox{привіт світ}\\\\";
    // latex += "\\end{array}";

    ImageIcon ii = new ImageIcon(formulaToImage(f.tf));
    System.out.println("size: " + ii.getIconWidth() + "," + ii.getIconHeight());
    iconLabel1 = new JLabel(ii);
    // iconLabel2 = new JLabel(new ImageIcon(latexToImage(latex)));
    // iconLabel2 = new JLabel();
    // iconLabel2.setHorizontalAlignment(JLabel.LEFT);
    // iconLabel2.setText("Testing");

    // org.scilab.forge.jlatexmath.Box tb = rm.get(mul);
    // System.out.println("Root box: " + tb.getWidth()
    //                    + "," + tb.getHeight() + " at ");
                       // + tb.lastX + "," + tb.lastY);

    // Add all objects
    add(iconLabel1);
    Dimension size = iconLabel1.getPreferredSize();
    iconLabel1.setBounds(0, 0, size.width, size.height);
    // add(iconLabel2);

    addButtons();

    setSize(400, 300);
    setLocationRelativeTo(null); // Center the frame
    setLayout(null);

    System.out.println(context.toString());
  }
}
