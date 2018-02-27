package com.viewscenes.timeTask;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.viewscenes.dao.XmlReader;
import com.viewscenes.dao.database.DbException;
import com.viewscenes.pub.GDSetException;
import com.viewscenes.util.LogTool;
import com.viewscenes.util.report.printtxt.Txt;





public class AlarmClock 
{

    private final Scheduler scheduler = new Scheduler();//调度器
    private final SimpleDateFormat dateFormat =
        new SimpleDateFormat("dd MMM yyyy HH24:mm:ss.SSS");
    private final int hourOfDay, minute, second;//每天触发的时间点 

    public AlarmClock(int hourOfDay, int minute, int second) 
    {
        this.hourOfDay = hourOfDay;
        this.minute = minute;
        this.second = second;
    }

    public void start() 
    {
        scheduler.schedule(new SchedulerTask() 
        {
            public void run() 
            {
            	LogTool.debug("exportlog","定时导出数据程序开始执行了啊++++++++++++++++++++++++++");
            	soundAlarm();
            }
            private void soundAlarm()
            {

                /**
                 * 每天统计导出昨天的数据内容
                 * 
                 */
              this.exportData();
            }
			private void exportData() {
				// TODO Auto-generated method stub
				List list=XmlReader.getAttrValueList("eachDayExportData", "para", "name","table", "sql");
				for(int i=0;i<list.size();i++)
				{
					List list2= (List) list.get(i);
					String name=list2.get(0).toString();
					String table= list2.get(1).toString();
				    String sql= list2.get(2).toString();
				    try {
						Txt.getTxt(sql, table, name);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						LogTool.debug("exportlog",name+"("+table+")导出报错原因=======================");
						LogTool.debug("exportlog",e.getMessage());
					} 
				}
				
			}
        }, new DailyIterator(Calendar.DATE,1,hourOfDay, minute, second));//通过迭代器模式迭代遍历得到后面一系列的时间点
    }

    public static void main(String[] args) 
    {
      
    }
}
