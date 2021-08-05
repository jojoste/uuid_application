import Control.UUIDController;
import View.ViewUUID;

import javax.swing.*;

public class MVCUUID {
    public static void main(String[] args) {


        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ViewUUID theView = new ViewUUID();


                UUIDController theControler = new UUIDController(theView);

            }
        });
    }

    //theView.setVisible(true);
}

