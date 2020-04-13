package com.song.test.poi;

import java.io.FileInputStream;
import java.io.InputStream;

import com.song.test.model.Config;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @ClassName:  Test   
 * @Description:TODO   
 * @author: wangyanzhao 
 * @date:   2018年9月24日 下午2:58:25   
 * @Copyright: 2018 www.youceedu.com All rights reserved. 
 * 注意：本内容仅限于优测教育内部传阅，禁止外泄以及用于其他的商业目
 */
public class ExcelUtil {

	/**
	 * 初始化
	 */
	private static String fileName = Config.EXCEL_NAME;
	

	/**
	 * @Title: getWb   
	 * @Description: TODO
	 * @param: @return      
	 * @return: Workbook      
	 * @throws
	 */
	public static Workbook getWb(){
		
		//初始化返回值
		Workbook wb = null;
		
		try{
			InputStream inputStream = new FileInputStream(fileName);
			
			//依据文件后缀判断实例化类型
			if(fileName.endsWith(".xlsx")){
				wb = new XSSFWorkbook(inputStream);
			}else{
				wb =new HSSFWorkbook(inputStream);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return wb;
	}
	
	/**
	 * @Title: getSheet   
	 * @Description: TODO
	 * @param: @param index
	 * @param: @return      
	 * @return: Sheet      
	 * @throws
	 */
	public static Sheet getSheet(int index){
		//初始化返回值
		Sheet sheet = null;
		
		try{
			Workbook wb = getWb();
			//依据sheet下标得到sheet对象
			sheet = wb.getSheetAt(index);
		}catch(Exception e){
			e.printStackTrace();
		}

		return sheet;
	}
	
	/**
	 * @Title: getCellValue   
	 * @Description: TODO
	 * @param: @param index
	 * @param: @param rownum
	 * @param: @param cellnum
	 * @param: @return      
	 * @return: Object      
	 * @throws
	 */
	public Object getCellValue(int index,int rownum,int cellnum){
		
		//初始化返回值
		Object result = null;
		
		try{
			//依据sheet,行,列下标,得到单元格值
			Row row = getSheet(index).getRow(rownum);
			Cell cell = row.getCell(cellnum);
			result = fromCellTypeGetCellValue(cell);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * @Title: fromCellTypeGetCellValue   
	 * @Description: TODO
	 * @param: @param cell
	 * @param: @return      
	 * @return: Object      
	 * @throws
	 */
	public static Object fromCellTypeGetCellValue(Cell cell){
		
		//初始化返回值
		Object value = null;
		
		try{
			//整个代码块依据单元格类型取对应的值
			if(cell.getCellType()== CellType.BLANK){
		    	   value = "";
		    	   
			}else if(cell.getCellType()==CellType.NUMERIC){
				value = cell.getNumericCellValue();
		    	   
			}else if(cell.getCellType()==CellType.STRING){
				value = cell.getStringCellValue().trim();

			}else if(cell.getCellType()==CellType.FORMULA){
				value = cell.getCellFormula();
				
			}else if(cell.getCellType()==CellType.BOOLEAN){
				value = cell.getBooleanCellValue();
	 	   
			} else if(cell.getCellType()==CellType.ERROR){
		    	   value = "";
			} else {
				value = cell.getDateCellValue();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return value;
	}
	
	/**
	 * @Title: getArrayCellValue   
	 * @Description: TODO
	 * @param: @param sheetIndex
	 * @param: @return      
	 * @return: Object[][]      
	 * @throws
	 */
	public static Object[][] getArrayCellValue(int sheetIndex){
		
		//初始化返回值
		Object[][] testCaseData = null;
		
		try{
			//用例总行数
			int totalRowIndex = getSheet(sheetIndex).getLastRowNum();

			testCaseData  = new Object[totalRowIndex][10];

	        for(int rowIndex = 0; rowIndex < totalRowIndex; rowIndex++){
	        	//通过sheet指定到rowIndex那行
	            Row row = getSheet(sheetIndex).getRow(rowIndex+1);
	            
	            //空行跳过
	            if(row == null){
	                continue;
	            }
	            
	            //指定到行后,遍历每列值
		        for(int cellIndex = 0;cellIndex < row.getLastCellNum();cellIndex++){
		        	
		            Cell cell = row.getCell(cellIndex);
		            if(cell == null){
		            	testCaseData[rowIndex][cellIndex]="";
		            }else{
		            	testCaseData[rowIndex][cellIndex] = fromCellTypeGetCellValue(cell);
		            }
		        }
	        }
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return testCaseData;
	}

}
