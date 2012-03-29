/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.ug.equation.buttons;

import basic.math.ug.path.UGVariables;
import eu.mihosoft.vrl.types.VCanvas3D;
import eu.mihosoft.vrl.v3d.IndexedGeometryList;
import eu.mihosoft.vrl.v3d.Triangle;
import eu.mihosoft.vrl.v3d.VTriangleArray;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.j3d.Shape3D;
import javax.vecmath.Point2f;

/**
 * The SkriptButton is used to write all coordinates of the selected areas
 * into a script file with the additional information how they were marked.
 *
 * @author night
 */
public class SkriptButton extends ToolButton {

    private static String scriptFile = UGVariables.APPL2D + "/SelectedTriangles.scr";
    private CustomCanvas canvas;
    private IndexedGeometryList indexedGeometryList;
    private VTriangleArray triangleArray;

    public SkriptButton(VCanvas3D canvas) {
        super(canvas);

        if (canvas instanceof CustomCanvas) {
            this.canvas = (CustomCanvas) canvas;
        }
    }

    /**
     *  Draws the characteristic symbol for the SkriptButton
     * @param g2 the area where the symbol is drawn
     */
    @Override
    protected void drawIcon(Graphics2D g2) {

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

//        Color strokeColor = Color.white;
//
//        if (style != null) {
//            strokeColor = style.getMessageBoxIconColor();
//        }
//        g2.setColor(strokeColor);

        float w = buffer.getWidth();
        float h = buffer.getHeight();

        Point2f p1 = new Point2f(w / 4.f, h / 8.f);
        Point2f p2 = new Point2f(w * 3.f / 4.f, h / 8.f);
        Point2f p3 = new Point2f(w * 3.f / 4.f, h * 7.f / 8.f);
        Point2f p4 = new Point2f(w / 4.f, h * 7.f / 8.f);

        Polygon part1 = new Polygon();
//        part1.addPoint((int) (p1.getX()), (int) (p1.getY()));
//        part1.addPoint((int) (p2.getX()), (int) (p2.getY()));
//        part1.addPoint((int) (p3.getX()), (int) (p3.getY()));
//        part1.addPoint((int) (p4.getX()), (int) (p4.getY()));
        
        
        part1.addPoint((int) (p1.x), (int) (p1.y) );
        part1.addPoint((int) (p2.x), (int) (p2.y) );
        part1.addPoint((int) (p3.x), (int) (p3.y) );
        part1.addPoint((int) (p4.x), (int) (p4.y) );

        g2.setColor(Color.LIGHT_GRAY);
        g2.fill(part1);

        Point2f p5 = new Point2f(w * 5.f / 16.f, h / 4.f);
        Point2f p6 = new Point2f(w / 2.f, h / 4.f);
        Point2f p7 = new Point2f(w * 9.f / 16.f, h / 4.f);
        Point2f p8 = new Point2f(w * 11.f / 16.f, h / 4.f);

        Point2f p9 = new Point2f(w * 5.f / 16.f, h * 3 / 8.f);
        Point2f p10 = new Point2f(w * 3.f / 8.f, h * 3 / 8.f);
        Point2f p11 = new Point2f(w * 7.f / 16.f, h * 3 / 8.f);
        Point2f p12 = new Point2f(w * 5.f / 8.f, h * 3 / 8.f);

        Point2f p13 = new Point2f(w * 5.f / 16.f, h / 2.f);
        Point2f p14 = new Point2f(w * 7.f / 16.f, h / 2.f);
        Point2f p15 = new Point2f(w / 2.f, h / 2.f);
        Point2f p16 = new Point2f(w * 11.f / 16.f, h / 2.f);

        Point2f p17 = new Point2f(w * 5.f / 16.f, h * 5.f / 8.f);
        Point2f p18 = new Point2f(w * 9.f / 16.f, h * 5.f / 8.f);

        Point2f p19 = new Point2f(w * 5.f / 16.f, h * 3 / 4.f);
        Point2f p20 = new Point2f(w * 7.f / 16.f, h * 3 / 4.f);
        Point2f p21 = new Point2f(w / 2.f, h * 3 / 4.f);
        Point2f p22 = new Point2f(w * 5.f / 8.f, h * 3 / 4.f);


        g2.setColor(Color.BLACK);
//        g2.drawLine((int) (p5.getX()), (int) (p5.getY()), (int) (p6.getX()), (int) (p6.getY()));
//        g2.drawLine((int) (p7.getX()), (int) (p7.getY()), (int) (p8.getX()), (int) (p8.getY()));
//        g2.drawLine((int) (p9.getX()), (int) (p9.getY()), (int) (p10.getX()), (int) (p10.getY()));
//        g2.drawLine((int) (p11.getX()), (int) (p11.getY()), (int) (p12.getX()), (int) (p12.getY()));
//        g2.drawLine((int) (p13.getX()), (int) (p13.getY()), (int) (p14.getX()), (int) (p14.getY()));
//        g2.drawLine((int) (p15.getX()), (int) (p15.getY()), (int) (p16.getX()), (int) (p16.getY()));
//        g2.drawLine((int) (p17.getX()), (int) (p17.getY()), (int) (p18.getX()), (int) (p18.getY()));
//        g2.drawLine((int) (p19.getX()), (int) (p19.getY()), (int) (p20.getX()), (int) (p20.getY()));
//        g2.drawLine((int) (p21.getX()), (int) (p21.getY()), (int) (p22.getX()), (int) (p22.getY()));
        
        g2.drawLine((int) (p5.x),  (int) (p5.y),  (int) (p6.x),  (int) (p6.y) );
        g2.drawLine((int) (p7.x),  (int) (p7.y),  (int) (p8.x),  (int) (p8.y) );
        g2.drawLine((int) (p9.x),  (int) (p9.y),  (int) (p10.x), (int) (p10.y) );
        g2.drawLine((int) (p11.x), (int) (p11.y), (int) (p12.x), (int) (p12.y) );
        g2.drawLine((int) (p13.x), (int) (p13.y), (int) (p14.x), (int) (p14.y) );
        g2.drawLine((int) (p15.x), (int) (p15.y), (int) (p16.x), (int) (p16.y) );
        g2.drawLine((int) (p17.x), (int) (p17.y), (int) (p18.x), (int) (p18.y) );
        g2.drawLine((int) (p19.x), (int) (p19.y), (int) (p20.x), (int) (p20.y) );
        g2.drawLine((int) (p21.x), (int) (p21.y), (int) (p22.x), (int) (p22.y) );

    }

    /**
     * Creates the skript file with informations about the selected triangles.
     *
     * @param e MouseEvent unused
     */
    @Override
    public void mouseClicked(MouseEvent e) {

        System.out.println("generate skript :-)");

        try {
            FileWriter out = new FileWriter(new File(scriptFile));
            BufferedWriter writer = new BufferedWriter(out);


            writer.write("Triangles Informations\n");
            writer.write("piont1 x, y, z, piont2 x, y, z, piont3 x, y, z, numeric method, value\n");
            writer.newLine();
            writer.newLine();

            HashSet<Shape3D> shape3DSet = canvas.getUniverse().getKeyMouseListener().getShape3DSet();
           
            for (Shape3D s : shape3DSet) {
                /*get the index of the selected triangle*/
                Integer index = indexedGeometryList.getIndex(s.getGeometry());
                Triangle t = triangleArray.getByIndex(index);

//                writer.write(t.getNodeOne().toString() +",   "+
//                        t.getNodeTwo().toString() +",   "+
//                        t.getNodeThree().toString() +",   "+
//                        s.getName()+",   "+                        
//                        "0.0");
                
                writer.write(t.getNodeOne().toString() +",   "+
                        t.getNodeTwo().toString() +",   "+
                        t.getNodeThree().toString() +",   "+
//                        s.getName()+",   "+  // changed because of Shape3D seems not to have a name variable
                        (String) s.getUserData()+",   "+
                        "0.0");
                
                writer.newLine();
            }
            writer.close();

        } catch (IOException ex) {
            Logger.getLogger(SkriptButton.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

    /**
     * Calls select().
     *
     * @param e MouseEvent unused
     */
    @Override
    public void mousePressed(MouseEvent e) {
        select();
    }

    /**
     * Calls unselect().
     *
     * @param e MouseEvent unused
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        unselect();
    }

    /**
     * @return the indexedGeometryList
     */
    public IndexedGeometryList getIndexedGeometryList() {
        return indexedGeometryList;
    }

    /**
     * @param indexedGeometryList the indexedGeometryList to set
     */
    public void setIndexedGeometryList(IndexedGeometryList indexedGeometryList) {
        this.indexedGeometryList = indexedGeometryList;
    }

    /**
     * @return the triangleArray
     */
    public VTriangleArray getTriangleArray() {
        return triangleArray;
    }

    /**
     * @param triangleArray the triangleArray to set
     */
    public void setTriangleArray(VTriangleArray triangleArray) {
        this.triangleArray = triangleArray;
    }
}
