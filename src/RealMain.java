import java.awt.EventQueue;

import presentation.CtrlPresentation;
import presentation.MainView;

public class RealMain {
	public static void main(String[] args) throws Exception {

		/**javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				CtrlPresentation mainWindow = CtrlPresentation.getInstance();
				mainWindow.show();
			}
		});**/
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CtrlPresentation mainWindow = CtrlPresentation.getInstance();
					mainWindow.initialize();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}
}
