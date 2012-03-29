package basic.math.menu;

import eu.mihosoft.vrl.io.vrlx.SessionInitializer;
import eu.mihosoft.vrl.reflection.VisualCanvas;
//import eu.mihosoft.vrl.reflection.SessionInitializer;
import eu.mihosoft.vrl.visual.Canvas;
import eu.mihosoft.vrl.visual.MessageType;

/**
 *
 * This class is a session initializer.
 * It loads the BasicMathMenu into VRL-Studio.
 *
 * @see eu.mihosoft.vrl.reflection.SessionInitializer
 * @see BasicMathMenu
 *
 * @author night
 */
public class BasicMathSessionInitializer implements SessionInitializer {

    private static final long serialVersionUID = 1L;

    /**
     * {@inheritDoc}
     */
    @Override
//    public void preInit(VisualCanvas canvas) throws Exception {
    public void preInit(Canvas canvas) throws Exception {

        if( ! System.getProperty("os.name").contains("Mac OS X")){

            canvas.getMessageBox().addMessage("Information",
                    "Please notice that Sessions that need UG will NOT work correctly " +
                    "because the UG binaries are currently only available for Mac OS X.",
                    MessageType.INFO);
        }

        BasicMathMenu creator = new BasicMathMenu();
        
        if(canvas instanceof VisualCanvas){
            
            VisualCanvas vcanvas = (VisualCanvas) canvas;
            creator.addMenuEntry(vcanvas);
        }
        else{
            System.out.println(BasicMathSessionInitializer.class.getName() + 
                    "The Methode preInit() needs intern a VisualCanvas !!");
        }
        
        
    }

    /**
     * {@inheritDoc}
     */
    @Override
//    public void postInit(final VisualCanvas canvas) throws Exception {
public void postInit(final Canvas canvas) throws Exception {
//        Timer t = new Timer();
//
//        TimerTask task = new TimerTask() {
//
//            @Override
//            public void run() {
//                for (CallOptionEvaluationTask e : canvas.getCallOptionEvaluationTasks()) {
//                    e.evaluate();
//                }
//            }
//        };
//
//        t.schedule(task, 10000);

    }

    @Override
    public void codesLoaded(Canvas canvas) throws Exception {
//        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void dispose(Canvas canvas) throws Exception {
//        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void preSave(Canvas canvas) throws Exception {
//        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void postSave(Canvas canvas) throws Exception {
//        throw new UnsupportedOperationException("Not supported yet.");
    }
}
