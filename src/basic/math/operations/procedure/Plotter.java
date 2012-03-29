/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.operations.procedure;

import basic.math.elements.visual.VisualVector;
import eu.mihosoft.vrl.annotation.ComponentInfo;
import eu.mihosoft.vrl.annotation.MethodInfo;
import eu.mihosoft.vrl.annotation.ObjectInfo;
import eu.mihosoft.vrl.annotation.ParamInfo;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * This Class contains some methods which rresults are used to visualize
 * one or more trajectories.
 *
 * @author night
 */
@ObjectInfo(name = "Plotter")
@ComponentInfo(name = "Plotter", category = "BasicMath")
public class Plotter implements Serializable {

    private static final long serialVersionUID = 2854798159077944571L;
    transient private BufferedImage image;
    transient private ArrayList<BufferedImage> imageList = null;

    /**
     * @param t1 the trajectory tto visualize
     * @return a buffered image containing the course of the trajectorie
     */
    public BufferedImage plot(
            @ParamInfo(name = "Input Trajectory") Trajectory t1) {

        System.out.println(">> Plotter.run()");

        image = new BufferedImage(640, 480, BufferedImage.TYPE_INT_ARGB);


        // init paint device
        Graphics2D g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        //g2.setColor(Color.BLACK);

        //g2.fillRoundRect(0, 0, image.getWidth(),
        //        image.getHeight(), 1, 1);

        g2.setColor(new Color(154, 158, 166));

        Composite original = g2.getComposite();

        AlphaComposite ac1 = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                0.65f);
        g2.setComposite(ac1);

        g2.setStroke(new BasicStroke(4.0f));

        //g2.drawLine(0, image.getHeight()/2 -1, image.getWidth() -1, image.getHeight()/2 -1);
        //g2.drawLine( image.getWidth()/2 -1, 0, image.getWidth()/2 -1, image.getHeight() -1);

        g2.setComposite(original);

        // trajectory 1
        g2.setColor(Color.GREEN);

        Double minX = Double.MAX_VALUE;
        Double minY = Double.MAX_VALUE;

        Double maxX = Double.MIN_VALUE;
        Double maxY = Double.MIN_VALUE;

        // find min / max values
        for (VisualVector v1 : t1) {
            Double xValue = (Double) v1.getEntry(0);
            Double yValue = (Double) v1.getEntry(1);

            if (minX > xValue) {
                minX = xValue;
            }
            if (minY > yValue) {
                minY = yValue;
            }

            if (maxX < xValue) {
                maxX = xValue;
            }
            if (maxY < yValue) {
                maxY = yValue;
            }

        }

//        System.out.println( minX  + " " + maxX + " " + minY + " " + maxY + " ");

        Double dx = Math.abs(maxX - minX);
        Double dy = Math.abs(maxY - minY);

        Double xScale = image.getWidth() / dx;
        Double yScale = image.getHeight() / dy;

        Double xOffset = (maxX + minX) / 2;
        Double yOffset = (maxY + minY) / 2;

        int xImageOffset = image.getWidth() / 2 - (int) (xOffset * xScale);
        int yImageOffset = image.getHeight() / 2 - (int) (yOffset * yScale);


        int x = (int) ((Double) t1.get(0).getEntry(0) * xScale) + xImageOffset;
        int y = (int) ((Double) t1.get(0).getEntry(1) * yScale) + yImageOffset;
        int xTmp = (int) ((Double) t1.get(0).getEntry(0) * xScale) + xImageOffset;
        int yTmp = (int) ((Double) t1.get(0).getEntry(1) * yScale) + yImageOffset;

        // plot the trajectory
        for (VisualVector v1 : t1) {
            x = (int) ((Double) v1.getEntry(0) * xScale) + xImageOffset;
            y = (int) ((Double) v1.getEntry(1) * yScale) + yImageOffset;

            g2.drawLine(xTmp, yTmp, x, y);

//            System.out.println( xTmp + " " + yTmp + " " + x + " " + y + " ");

            xTmp = x;
            yTmp = y;
        }

        g2.dispose();

        return image;
    }

    /**
     * @param t1 the trajectory tto visualize
     * @param color the color which should be used for the trajectory
     * @return an array of buffered images containing the course of the trajectorie
     */
    @MethodInfo(hide = true, valueStyle = "array")
    public BufferedImage[] plotColor(
            @ParamInfo(name = "Input Trajectory") Trajectory t1, Color color) {

        System.out.println(">> Plotter.run()");

        image = new BufferedImage(640, 480, BufferedImage.TYPE_INT_ARGB);

        if (imageList == null) {
            imageList = new ArrayList<BufferedImage>();
        }


        // init paint device
        Graphics2D g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        Composite original = g2.getComposite();

        AlphaComposite ac1 = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                0.65f);
        g2.setComposite(ac1);

        g2.setStroke(new BasicStroke(4.0f));

        g2.setComposite(original);

        // trajectory 1
        g2.setColor(color);

        Double minX = Double.MAX_VALUE;
        Double minY = Double.MAX_VALUE;

        Double maxX = Double.MIN_VALUE;
        Double maxY = Double.MIN_VALUE;


        // find min / max values
        for (VisualVector v1 : t1) {
            Double xValue = (Double) v1.getEntry(0);
            Double yValue = (Double) v1.getEntry(1);

            if (minX > xValue) {
                minX = xValue;
            }
            if (minY > yValue) {
                minY = yValue;
            }

            if (maxX < xValue) {
                maxX = xValue;
            }
            if (maxY < yValue) {
                maxY = yValue;
            }

        }

//        System.out.println( minX  + " " + maxX + " " + minY + " " + maxY + " ");

        Double dx = Math.abs(maxX - minX);
        Double dy = Math.abs(maxY - minY);

        Double xScale = image.getWidth() / dx;
        Double yScale = image.getHeight() / dy;

        Double xOffset = (maxX + minX) / 2;
        Double yOffset = (maxY + minY) / 2;

        int xImageOffset = image.getWidth() / 2 - (int) (xOffset * xScale);
        int yImageOffset = image.getHeight() / 2 - (int) (yOffset * yScale);

//        System.out.println( minX  + " " + maxX + " " + minY + " " + maxY + " ");

        int x = (int) ((Double) t1.get(0).getEntry(0) * xScale) + xImageOffset;
        int y = (int) ((Double) t1.get(0).getEntry(1) * yScale) + yImageOffset;
        int xTmp = (int) ((Double) t1.get(0).getEntry(0) * xScale) + xImageOffset;
        int yTmp = (int) ((Double) t1.get(0).getEntry(1) * yScale) + yImageOffset;

        // plot the trajectory
        for (VisualVector v1 : t1) {
            x = (int) ((Double) v1.getEntry(0) * xScale) + xImageOffset;
            y = (int) ((Double) v1.getEntry(1) * yScale) + yImageOffset;

            g2.drawLine(xTmp, yTmp, x, y);

//            System.out.println( xTmp + " " + yTmp + " " + x + " " + y + " ");

            xTmp = x;
            yTmp = y;
        }

        g2.dispose();

        imageList.add(image);

        BufferedImage[] imageArray = new BufferedImage[imageList.size()];

        return imageList.toArray(imageArray);
    }

    /**
     * Clears the ArrayList containg the buffered images.
     * These ArrayList is used in plotTrajectories().
     */
    @MethodInfo(hide = true)
    public void clear() {
        imageList = new ArrayList<BufferedImage>();
    }

    /**
     *
     * @param tList is an ArrayList of Trajectories
     * @return a BufferedImage containg the trajectories drawn in their linked color
     */
    @MethodInfo(hide = true, valueStyle = "array")
    public BufferedImage plotTrajectories(
            @ParamInfo(name = "Input Trajectory", style="array") Trajectory[] tList) {

        System.out.println(">> Plotter.run()");

        image = new BufferedImage(640, 480, BufferedImage.TYPE_INT_ARGB);

        if (imageList == null) {
            imageList = new ArrayList<BufferedImage>();
        }


        // init paint device
        Graphics2D g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

//        g2.setColor(color);
//
//        //g2.fillRoundRect(0, 0, image.getWidth(),
//        //        image.getHeight(), 1, 1);
//
//        g2.setColor(new Color(154, 158, 166));

        Composite original = g2.getComposite();

        AlphaComposite ac1 = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                0.65f);
        g2.setComposite(ac1);

        g2.setStroke(new BasicStroke(4.0f));

        //g2.drawLine(0, image.getHeight()/2 -1, image.getWidth() -1, image.getHeight()/2 -1);
        //g2.drawLine( image.getWidth()/2 -1, 0, image.getWidth()/2 -1, image.getHeight() -1);

        g2.setComposite(original);



        // trajectory 1
//        g2.setColor(Color.GREEN);

        for (int i = 0; i < tList.length; i++) {
            Trajectory t1 = tList[i];

            g2.setColor(t1.getColor());
//            g2.setColor(Color.GREEN);


            Double minX = Double.MAX_VALUE;
            Double minY = Double.MAX_VALUE;

            Double maxX = Double.MIN_VALUE;
            Double maxY = Double.MIN_VALUE;


            // find min / max values
            for (VisualVector v1 : t1) {
                Double xValue = (Double) v1.getEntry(0);
                Double yValue = (Double) v1.getEntry(1);

                if (minX > xValue) {
                    minX = xValue;
                }
                if (minY > yValue) {
                    minY = yValue;
                }

                if (maxX < xValue) {
                    maxX = xValue;
                }
                if (maxY < yValue) {
                    maxY = yValue;
                }

            }

//        System.out.println( minX  + " " + maxX + " " + minY + " " + maxY + " ");

            Double dx = Math.abs(maxX - minX);
            Double dy = Math.abs(maxY - minY);

            Double xScale = image.getWidth() / dx;
            Double yScale = image.getHeight() / dy;

            Double xOffset = (maxX + minX) / 2;
            Double yOffset = (maxY + minY) / 2;

            int xImageOffset = image.getWidth() / 2 - (int) (xOffset * xScale);
            int yImageOffset = image.getHeight() / 2 - (int) (yOffset * yScale);


//        System.out.println( minX  + " " + maxX + " " + minY + " " + maxY + " ");

            int x = (int) ((Double) t1.get(0).getEntry(0) * xScale) + xImageOffset;
            int y = (int) ((Double) t1.get(0).getEntry(1) * yScale) + yImageOffset;
            int xTmp = (int) ((Double) t1.get(0).getEntry(0) * xScale) + xImageOffset;
            int yTmp = (int) ((Double) t1.get(0).getEntry(1) * yScale) + yImageOffset;

            // plot the trajectory
            for (VisualVector v1 : t1) {
                x = (int) ((Double) v1.getEntry(0) * xScale) + xImageOffset;
                y = (int) ((Double) v1.getEntry(1) * yScale) + yImageOffset;

                g2.drawLine(xTmp, yTmp, x, y);

//            System.out.println( xTmp + " " + yTmp + " " + x + " " + y + " ");

                xTmp = x;
                yTmp = y;
            }
        }

        g2.dispose();

        return image;
    }
}

