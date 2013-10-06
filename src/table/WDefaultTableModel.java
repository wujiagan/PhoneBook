package table;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class WDefaultTableModel extends DefaultTableModel {
	public WDefaultTableModel(){
	}
	
	public WDefaultTableModel(Vector data, Vector columnNames) {
		setDataVector(data, columnNames);
	}
	
	/**
	 * 以data数据来构建特定的列
	 * @param data
	 * @param columnNames
	 */
	public WDefaultTableModel(Object[][] data,Object[] columnNames){
		super(data,columnNames);
	}
	
	/**
	 * 返回column列的类
	 */
	@SuppressWarnings("unchecked")
	public Class getColumnClass(int column){
		return getValueAt(0, column).getClass();
	}
	
	/**
	 * 该方法返回true则该单元格可编辑
	 */
	public boolean isCellEditable(int row, int column){
		if(column == 5)
			return true;
		return false;
	}
	
}
