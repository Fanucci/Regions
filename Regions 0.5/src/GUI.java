import java.awt.EventQueue;
import java.io.File;
import java.net.URISyntaxException;

import javax.swing.JFileChooser;
import javax.swing.UIManager;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class GUI {
	protected Shell shell;
    public static void main(String[] args) {
        Runnable r = new Runnable() {

            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(
                            UIManager.getSystemLookAndFeelClassName());
                } catch(Exception e) {
                    e.printStackTrace();
                }
                new GUI().createUI();
            }
        };

        EventQueue.invokeLater(r);
    }


    private void createUI() {
        Display display = new Display();
        Shell shell = new Shell(display);
		shell.setSize(185, 70);
        shell.setText("File Chooser");
		shell.setLayout(new GridLayout(3, false));


		Button saveBtn = new Button(shell, SWT.NONE);
		saveBtn.setText("Сохранить");
		Button openBtn = new Button(shell, SWT.NONE);
		openBtn.setText("Путь к кодам");
        
        saveBtn.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent arg0) {
                JFileChooser saveFile;
				try {
					saveFile = new JFileChooser(new File(GUI.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()));
	                int returnVal = saveFile.showSaveDialog(null);
	                if(returnVal==JFileChooser.APPROVE_OPTION){
	                File file = saveFile.getSelectedFile();
	                System.out.println("Saving:"+file+".xlsx");
	                job.writeExcel(file);
	                }
	                else System.out.println("Canceled");
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

            }
        });

        openBtn.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent arg0) {
                JFileChooser openFile = new JFileChooser(/*"C:/Personal Soft/eclipse/workspace/Scripts"*/);
                int returnVal = openFile.showOpenDialog(null);
                if(returnVal==JFileChooser.APPROVE_OPTION){
                File file = openFile.getSelectedFile();
                System.out.println("Opening:" + file);
                CSV.changeCodes(file);
                }
                else System.out.println("Canceled");
            }
        });
        shell.open();
        while (!shell.isDisposed()) {
          if (!display.readAndDispatch()) {
            display.sleep();
          }
        }
   /*     frame.getContentPane().add(new JLabel("File Chooser"), BorderLayout.NORTH);
        frame.getContentPane().add(saveBtn, BorderLayout.CENTER);
        frame.getContentPane().add(openBtn, BorderLayout.SOUTH);

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);*/
    }
}