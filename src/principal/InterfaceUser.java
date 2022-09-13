package principal;

import modelo.*;

import java.util.ArrayList;
import java.util.regex.*;


import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class InterfaceUser {
	private JFrame frame;
	public  JTextField textField_amount;
	public  JComboBox<String>  comboBox_in ;
	public  JComboBox<String>  comboBox_out ;
	public  JLabel     label_result;
	public  Conversor operacion;
	private JTable table;

	 // Launch the application.
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfaceUser window = new InterfaceUser();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	//constructor
	public InterfaceUser() {
		operacion=new Conversor();
		comboBox_in = new JComboBox<String>();
		comboBox_out = new JComboBox<String>();
		cargarMonedaEntrada();
		comboBox_in.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedItem= (String) comboBox_in.getSelectedItem();
				ArrayList<String> arrayAux= operacion.obetenerArraylistUpdate(selectedItem);
				comboBox_out.removeAllItems();
				arrayAux.forEach((n) ->  comboBox_out.addItem(n));
			}
		}
		);
		
		frame = new JFrame();
		frame.setBounds(100, 100, 621, 336);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(25, 5, 265, 35);
		frame.getContentPane().add(panel);
		
		JLabel lblNewLabel = new JLabel("Convertidor de Monedas");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panel.add(lblNewLabel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 50, 150, 50);
		frame.getContentPane().add(panel_1);
		
		panel_1.add(comboBox_in);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(155, 50, 150, 50);
		frame.getContentPane().add(panel_2);
		
		
		panel_2.add(comboBox_out);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBounds(0, 124, 171, 30);
		frame.getContentPane().add(panel_4);
		
		JLabel lblNewLabel_2 = new JLabel("Amount:");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.LEFT);
		panel_4.add(lblNewLabel_2);
		
		textField_amount = new JTextField();
		panel_4.add(textField_amount);
		textField_amount.setColumns(10);
		

		
		JButton btnNewButton_1 = new JButton("Exit");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
			}
		});
		
		btnNewButton_1.setBounds(506, 263, 89, 23);
		frame.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Convert");
		
		btnNewButton_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				calcularConversion();
			}
		});
		btnNewButton_2.setBounds(10, 186, 89, 23);
		frame.getContentPane().add(btnNewButton_2);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBounds(408, 5, 106, 30);
		frame.getContentPane().add(panel_3);
		
		JLabel lblNewLabel_1 = new JLabel("Referencias");
		panel_3.add(lblNewLabel_1);
		
	
		label_result = new JLabel("New label");
		label_result.setBounds(10, 251, 341, 35);
		frame.getContentPane().add(label_result);
		label_result.setText("");
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{"Simbolos", "Nombre"},
				{"USD", "DOLAR AMERICANO"},
				{"EUR", "EURO"},
				{"GBP", "LIBRA ESTERLINA"},
				{"JPY", "YEN JAPONES"},
				{"KRW", "WON SURCOREANO"},
				{"ARS", "PESO ARGENTINO"},
			},
			new String[] {
				"New column", "New column"
			}
		));
		table.setBounds(315, 46, 300, 112);
		frame.getContentPane().add(table);
	}
	
	
	public void calcularConversion() {
		 String textFieldAmount = textField_amount.getText();
		 if(textFieldAmount.isEmpty()) {
			 JOptionPane.showMessageDialog(null, "Ingrese una cantidad", "Alert", JOptionPane.ERROR_MESSAGE); 
		 }
		 else 
		 {
		     if(validarAmount(textFieldAmount)) {
			    Double amount=Double.parseDouble(textFieldAmount);
			    String selectedItemIn= (String) comboBox_in.getSelectedItem();
			    String selectedItemOut= (String) comboBox_out.getSelectedItem();
			    Double ratio= Double.parseDouble(operacion.getConvertForex(selectedItemIn, selectedItemOut));
			    Double result=amount*ratio;
		        BigDecimal output= new BigDecimal(result).setScale(2, RoundingMode.HALF_EVEN);
				label_result.setText("");
				String frase=amount+" "+selectedItemIn+" son "+output.toString()+" "+selectedItemOut ;
				label_result.setText(frase);
	          }
			  else {
				 JOptionPane.showMessageDialog(null, "Ingrese un valor correcto: 1 /  1.5  / 150.55", "Error", JOptionPane.ERROR_MESSAGE); 
				 textField_amount.setText("");
			  }
		  }
    }
	
	
	public static boolean validarAmount(String amount){
		   Pattern pat = Pattern.compile("(\\d+(?:\\.\\d+)?)");
		   Matcher mat = pat.matcher(amount);  
		   if (mat.matches()) {
		         return true;
		     } else {
		         return false;
		     }
	}

	
	public void cargarMonedaEntrada() {
		 String[] arrayFiat=operacion.mostrarFiat();
		 for(int i =0; i<arrayFiat.length;i++) {
			 comboBox_in.addItem(arrayFiat[i]);
		 }

		 String noElement=(String) comboBox_in.getSelectedItem();
		 for(int i =0; i<arrayFiat.length;i++) {
				if(arrayFiat[i]!=noElement) {
					 comboBox_out.addItem(arrayFiat[i]);
				}
		} 	 
    }
}
