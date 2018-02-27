package com.viewscenes.util.report.printexcel;

/**
 *
 */
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
//import org.jfree.chart.labels.StandardPieItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.ui.TextAnchor;
//import org.jfree.chart.labels.StandardPieItemLabelGenerator;



public class Chart {
    private Font subTitleFont = new Font("黑体", Font.BOLD, 13);
    private Font dateLabelFont = new Font("黑体", Font.PLAIN ,12);
    /**
     * 图表主标题
     */
    private String MainTitle;

    /**
     * 图表副标题
     */
    private String SecondTitle;

    /**
     * 图表小标题
     */
    private String ThirdTitle;


    /**
     * 设置柱状图数值间隔是否为整形
     */
    private boolean isYaxisInt = false;
    
    /**
	 * 设置X轴日期显示类型 多用于时序图
	 */
	private String DateType;

    /**
     * 设置Y轴最大值
     */
    private double yMaxValue = 0;
    
    /**
     * 设置Y轴最小值
     */
    private double yMixValue = 0;


    /**
     * X轴显示的值
     */
    private ArrayList XCordValue = new ArrayList();

    /**
     * X轴的标题 不赋值时 默认显示时间
     */
    private String XCordUnitName = "时间";

    /**
     * Y轴标题 不赋值时 默认显示数值
     */
    private String YCordUnitName = "数值";


    /**
     * 特殊用的另一个Y轴的单位 一般不用
     */
    private String OthYUnitName = "";

    /**
     * 图表序列的名称
     */
    private ArrayList SeriesName = new ArrayList();

    /**
     * 图表数值序列
     */
    private ArrayList SeriesValue = new ArrayList();
    /**
	 * 图形下方的图例是否显示
	 */
	private boolean LegendVis = true;
    /**
	 * 时序图需要的两个dataset 多轴时需要设置dataset1
	 */
	TimeSeriesCollection dataset = new TimeSeriesCollection();
	TimeSeriesCollection dataset1 = new TimeSeriesCollection();

	private DefaultCategoryDataset DataSet = new DefaultCategoryDataset();


    private DefaultCategoryDataset LineDataSet = new DefaultCategoryDataset();

    public DefaultPieDataset DataSet2 = new DefaultPieDataset(); // 饼图数据集

    public Chart() {

    }

    /**
     *
     * @param maintitle
     *            主标题
     */
    public Chart(String maintitle) {
        MainTitle = maintitle;
    }

    /**
     * // * 添加图表显示的数值序列
     *
     * @param seriesName
     * @param seriesValue
     *            数值序列
     */
    public void addSeries(String seriesName, ArrayList seriesValue) {
        // SeriesName = new ArrayList();
        // SeriesValue = new ArrayList();
        SeriesName.add(seriesName);
        SeriesValue.add(seriesValue);
    }

    /**
     * 创建通用DataSet
     *
     */
    private void createDataSet() {

        String seriesName;
        // 单个序列list
        ArrayList singleSeriesList = null;
        // 单个序列list的元素值
        String singleSeriesValue;
        if (listIsNull(SeriesName, XCordValue, SeriesValue)) {
            // 循环序列的个数
            for (int i = 0; i < SeriesValue.size(); i++) {
                seriesName = (String) SeriesName.get(i);
                singleSeriesList = (ArrayList) SeriesValue.get(i);
                if (singleSeriesList != null) {
                    // 循环每个序列(X轴)
                    for (int j = 0; j < XCordValue.size(); j++) {
                        if (j >= singleSeriesList.size()) {
                            singleSeriesValue = null;
                        } else {
                            singleSeriesValue = (String) singleSeriesList
                                                .get(j);
                        }

                        // 判断是否为数值型
                        if (isNull(singleSeriesValue)) {
                            DataSet.addValue(null, seriesName,
                                             (String) XCordValue.get(j));
                        } else {
                            DataSet.addValue(getStr2Double(singleSeriesValue),
                                             seriesName,
                                             (String) XCordValue.get(j));
                        }
                    }
                }
            }
        } else {
            if (SeriesName == null) {
                System.err.println("图表序列的名称为null!");
            } else if (SeriesValue == null) {
                System.err.println("图表数值序列为null!");
            } else if (SeriesName.size() != SeriesValue.size()) {
                System.err.println("图表序列的名称和图表数值序列个数不相等!");
            }
        }

    }

    /**
     * 创建饼图的DataSet
     *
     */
    public void createPieDataSet() {
        ArrayList singleSeriesList = null;
        String singleSeriesValue;
        if (listIsNull(SeriesName, XCordValue, SeriesValue)) {

            for (int i = 0; i < SeriesValue.size(); i++) {
                singleSeriesList = (ArrayList) SeriesValue.get(i);
                if (singleSeriesList != null) {
                    for (int j = 0; j < XCordValue.size(); j++) {
                        if (singleSeriesList.get(j) == null) {
                            singleSeriesValue = new String("");
                        } else {
                            singleSeriesValue = new String(
                                    (String) singleSeriesList.get(j));
                        }

                        if (isNull(singleSeriesValue)) {
                            DataSet2.setValue((String) XCordValue.get(j), null);
                        } else {
                            DataSet2.setValue((String) XCordValue.get(j),
                                              getStr2Double(singleSeriesValue));
                        }
                    }
                }
            }
        } else {
            if (SeriesName == null) {
                System.err.println("图表序列的名称为null!");
            } else if (SeriesValue == null) {
                System.err.println("图表数值序列为null!");
            } else if (SeriesName.size() != SeriesValue.size()) {
                System.err.println("图表序列的名称和图表数值序列个数不相等!");
            }
        }
    }

    /**
     * 创建折线图
     *
     * @param width
     *            宽度
     * @param height
     *            高度
     * @param info
     *            图形描述信息
     * @param session
     * @return
     */
    public String createLineChart(int width, int height,
                                  ChartRenderingInfo info, HttpSession session) {
        try {
            JFreeChart chart = createLineChart();
            return ServletUtilities.saveChartAsPNG(chart, width, height, info,
                    session);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 创建折线图
     *
     * @return JFreeChart
     */
    public JFreeChart createLineChart() {
        createDataSet();

        // create the chart...
        JFreeChart chart = ChartFactory.createLineChart(MainTitle, // 大标题
                XCordUnitName, // X轴的单位
                YCordUnitName, // Y轴的单位
                DataSet, // 数据
                PlotOrientation.VERTICAL, // orientation
                true, // include legend
                true, // tooltips
                false // urls
                           );
        // 设置整个图表的背景颜色
        GradientPaint bgGP = new GradientPaint(0, 1000, Color.cyan, 0, 0,
                                               Color.WHITE, false);
        chart.setBackgroundPaint(bgGP);

        CategoryPlot plot = chart.getCategoryPlot();
        plot.setDomainGridlinesVisible(true);
        NumberAxis numberaxisbar = (NumberAxis) plot.getRangeAxis();
        if (yMaxValue == 0) {
			numberaxisbar.setAutoRange(true);
		} else {
			numberaxisbar.setUpperBound(yMaxValue);
		}
        numberaxisbar.setLowerBound(yMixValue);
        
        chart.setBorderVisible(true);
        GradientPaint bgBorderGP = new GradientPaint(0, 0, Color.LIGHT_GRAY, 0,
                0, Color.LIGHT_GRAY, false);
        chart.setBorderPaint(bgBorderGP);
        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions
                                             .createUpRotationLabelPositions(
                0.8));
        domainAxis.setMaximumCategoryLabelWidthRatio(20F);
        plot.setNoDataMessage("没有传入相应数据以显示图片");
        if (SecondTitle != null) {
            chart.addSubtitle(new TextTitle(SecondTitle, subTitleFont)); // 添加副标题
        }

        if (ThirdTitle != null) {
            chart.addSubtitle(new TextTitle(ThirdTitle, subTitleFont)); // 添加副标题
        }

        return chart;
    }

    /**
     * 饼型图
     *
     * @return JFreeChart
     */
    public String createPieChart(int width, int height,
                                 ChartRenderingInfo info, HttpSession session) {
        try {
            JFreeChart chart = createPieChart();
            return ServletUtilities.saveChartAsPNG(chart, width, height, info,
                    session);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 创建饼型图
     *
     * @return
     */
    private JFreeChart createPieChart() {
        createPieDataSet();
        JFreeChart chart = ChartFactory.createPieChart(MainTitle, // 大标题
                DataSet2, // 数据
                true, // include legend
                true, // tooltips
                false // urls
                           );
        GradientPaint bgGP = new GradientPaint(0, 1000, Color.cyan, 0, 0,
                                               Color.WHITE, false);
        chart.setBackgroundPaint(bgGP);

        PiePlot pie = (PiePlot) chart.getPlot();
//        pie.setLabelGenerator(new StandardPieItemLabelGenerator("{0}={1}({2})",
//                NumberFormat.getNumberInstance(), new DecimalFormat("0.00%")));
//        pie.setLegendLabelGenerator(new StandardPieItemLabelGenerator("{0}"));
        pie.setNoDataMessage("没有传入相应数据以显示图片");
        if (SecondTitle != null) {
            chart.addSubtitle(new TextTitle(SecondTitle, subTitleFont)); // 添加副标题
        }
        if (ThirdTitle != null) {
            chart.addSubtitle(new TextTitle(ThirdTitle, subTitleFont)); // 添加副标题
        }
        return chart;
    }
    /**
     * 3D饼型图
     *
     * @return JFreeChart
     */
    public String createPieChart3D(int width, int height,
                                 ChartRenderingInfo info, HttpSession session) {
        try {
            JFreeChart chart = createPieChart3D();
            return ServletUtilities.saveChartAsPNG(chart, width, height, info,
                    session);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 创建3D饼型图
     *
     * @return
     */
    private JFreeChart createPieChart3D() {
    	createPieDataSet();
    	JFreeChart chart = ChartFactory.createPieChart3D(MainTitle, // 大标题
    			DataSet2, // 数据
    			true, // include legend
    			true, // tooltips
    			false // urls
    	);
    	GradientPaint bgGP = new GradientPaint(0, 1000, Color.cyan, 0, 0,
    			Color.WHITE, false);
    	chart.setBackgroundPaint(bgGP);
    	
    	PiePlot pie = (PiePlot) chart.getPlot();
//        pie.setLabelGenerator(new StandardPieItemLabelGenerator("{0}={1}({2})",
//                NumberFormat.getNumberInstance(), new DecimalFormat("0.00%")));
//        pie.setLegendLabelGenerator(new StandardPieItemLabelGenerator("{0}"));
    	pie.setNoDataMessage("没有传入相应数据以显示图片");
    	if (SecondTitle != null) {
    		chart.addSubtitle(new TextTitle(SecondTitle, subTitleFont)); // 添加副标题
    	}
    	if (ThirdTitle != null) {
    		chart.addSubtitle(new TextTitle(ThirdTitle, subTitleFont)); // 添加副标题
    	}
    	return chart;
    }

    /**
     * 创建柱状图
     *
     * @return JFreeChart
     */
    public String createBarChart(int width, int height,
                                 ChartRenderingInfo info, HttpSession session) {
        try {
            JFreeChart chart = createBarChart();
            return ServletUtilities.saveChartAsPNG(chart, width, height, info,
                    session);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 创建柱状图
     *
     * @return JFreeChart
     */
    private JFreeChart createBarChart() {
        createDataSet();
        JFreeChart chart = ChartFactory.createBarChart3D(MainTitle, // 大标题
                XCordUnitName, // X轴的单位
                YCordUnitName, // Y轴的单位
                DataSet, // 数据
                PlotOrientation.VERTICAL, true, true, false);

        // 设置整个图表的背景颜色
        GradientPaint bgGP = new GradientPaint(0, 1000, Color.cyan, 0, 0,
                                               Color.WHITE, false);
        chart.setBackgroundPaint(bgGP);
       
        CategoryPlot plot = chart.getCategoryPlot();
        CategoryItemRenderer renderer = (CategoryItemRenderer) plot.getRenderer(); 
        //GradientPaint gp0 = new GradientPaint(0.0f, 0.0f, new Color(255,200, 80), 0.0f, 0.0f, new Color(255, 255, 40)); 
       // GradientPaint gp1 = new GradientPaint(0.0f, 0.0f, new Color(50,255, 50), 0.0f, 0.0f, new Color(100, 255, 100)); 
       // GradientPaint gp2 = new GradientPaint(0.0f, 0.0f, Color.red, 0.0f,0.0f, new Color(255, 100, 100)); 
        GradientPaint gp3 = new GradientPaint(0.0f, 0.0f, new Color(151,202, 0), 0.0f, 0.0f, new Color(71, 94, 0)); 

       // renderer.setSeriesPaint(0, gp0); 
        //renderer.setSeriesPaint(1, gp1); 
        //renderer.setSeriesPaint(2, gp2); 
        renderer.setSeriesPaint(0, gp3); 
        //plot.setBackgroundPaint(Color.blue);//设置里面颜色
        plot.setDomainGridlinesVisible(true);
        NumberAxis numberaxisbar = (NumberAxis) plot.getRangeAxis();
        if (yMaxValue == 0) {
			numberaxisbar.setAutoRange(true);
		} else {
			numberaxisbar.setUpperBound(yMaxValue);
		}
        numberaxisbar.setLowerBound(yMixValue);
        final BarRenderer render = (BarRenderer) plot.getRenderer();
//        render.setItemURLGenerator(new StandardCategoryURLGenerator("#"));
//        render.setToolTipGenerator(new StandardCategoryToolTipGenerator());
        render.setItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        render.setItemLabelFont(dateLabelFont);
        render.setItemLabelsVisible(true);//在每个柱子上面显示数值
//        render.setMaxBarWidth(1.0);
        render.setItemLabelsVisible(true);
        render.setMinimumBarLength(0);
        render.setPositiveItemLabelPosition(new ItemLabelPosition(
        		ItemLabelAnchor.OUTSIDE12,TextAnchor.BASELINE_LEFT));
        plot.setRenderer(render);

        chart.setBorderVisible(true);
        GradientPaint bgBorderGP = new GradientPaint(0, 0, Color.LIGHT_GRAY, 0,
                0, Color.LIGHT_GRAY, false);
        chart.setBorderPaint(bgBorderGP);
        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions
                                             .createUpRotationLabelPositions(
                0.8));
        // domainAxis.setCategoryLabelPositions(CategoryLabelPositions
        // .createDownRotationLabelPositions(1.2));
        domainAxis.setMaximumCategoryLabelWidthRatio(20F);

        ValueAxis valueaxis = plot.getRangeAxis();
        plot.setNoDataMessage("没有传入相应数据以显示图片");
        if (isYaxisInt) {
            valueaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        } else {
            valueaxis.setAutoRange(true);
        }
        plot.setRangeAxis(valueaxis);


        if (SecondTitle != null) {
            chart.addSubtitle(new TextTitle(SecondTitle, subTitleFont)); // 添加副标题
        }
        if (ThirdTitle != null) {
            chart.addSubtitle(new TextTitle(ThirdTitle, subTitleFont)); // 添加副标题
        }

        return chart;
    }
	/**
	 * 创建时序图
	 * 
	 * @param width
	 *            宽度
	 * @param height
	 *            高度
	 * @param info
	 *            图形描述信息
	 * @param session
	 * @return
	 */
	public String createTimeChart(int width, int height,
			ChartRenderingInfo info, HttpSession session) {
		try {
			JFreeChart chart = createTime();
			return ServletUtilities.saveChartAsPNG(chart, width, height, info,
					session);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 创建时序图,单轴时序图
	 * 
	 * @return JFreeChart
	 */
	private JFreeChart createTime() {
		createDataSetTime();
		JFreeChart chart = ChartFactory.createTimeSeriesChart(MainTitle,// 大标题
				XCordUnitName, // X轴的单位
				YCordUnitName, // Y轴的单位
				dataset, // 数据
				LegendVis, // include legend
				true, // tooltips
				false // urls
				);
		GradientPaint bgGP = new GradientPaint(0, 1000, Color.cyan, 0, 0,
				Color.WHITE, false);
		chart.setBackgroundPaint(bgGP);
		chart.setBorderVisible(true);
		GradientPaint bgBorderGP = new GradientPaint(0, 0, Color.LIGHT_GRAY, 0,
				0, Color.LIGHT_GRAY, false);
		chart.setBorderPaint(bgBorderGP);
		try {
			XYPlot plot = chart.getXYPlot();
			plot.setDomainGridlinesVisible(true);
			// XYItemRenderer renderer = plot.getRenderer();
			// if (renderer instanceof XYLineAndShapeRenderer)
			// {
			// XYLineAndShapeRenderer rr = (XYLineAndShapeRenderer) renderer;
			// rr.setDefaultShapesVisible(true);
			// rr.setDefaultShapesFilled(true);
			//	 
			// }
			//	       
			DateAxis axis = (DateAxis) plot.getDomainAxis();
			if (DateType != null) {
				axis.setDateFormatOverride(new SimpleDateFormat(DateType));
			} else {
				axis.setDateFormatOverride(new SimpleDateFormat("HH:mm"));
			}
			// axis.setVerticalTickLabels(true);
			axis.setAutoRange(true);
			ValueAxis valueaxis = plot.getRangeAxis();
			if (isYaxisInt) {
				valueaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
			} else {
				valueaxis.setAutoRange(true);
			}
			plot.setRangeAxis(valueaxis); 
			plot.setNoDataMessage("没有传入相应数据以显示图片");

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (SecondTitle != null)
			// System.out.println("宋体");
			chart.addSubtitle(new TextTitle(SecondTitle, subTitleFont));// 添加副标题
		if (ThirdTitle != null)
			chart.addSubtitle(new TextTitle(ThirdTitle, subTitleFont));// 添加副标题

		return chart;
	}
    /**
	 * 创建时序图的dataset
	 * 
	 */
	private void createDataSetTime() {
		String seriesName;
		ArrayList singleSeriesList = null;
		String singleSeriesValue = null;
		TimeSeries series = null;
		// 确保每次插入的序列都是本次的没有以前的余留的序列
		dataset.removeAllSeries();
		try {
			if (listIsNull(SeriesName, XCordValue, SeriesValue)) {
				for (int i = 0; i < SeriesValue.size(); i++) {
					seriesName = (String) SeriesName.get(i);
					singleSeriesList = (ArrayList) SeriesValue.get(i);
					series = new TimeSeries(seriesName, Second.class);
					for (int j = 0; j < XCordValue.size(); j++) {
						if (j < singleSeriesList.size()) {
							singleSeriesValue = (String) singleSeriesList
									.get(j);
							String record = (String) XCordValue.get(j);
							if (DateType == null) {
								DateType = "yyyy-MM-dd HH:mm";
							}
							SimpleDateFormat format = new SimpleDateFormat(
									DateType);
							Date date;
							if (record != null) {
								date = format.parse(record);
								if (isNull(singleSeriesValue)) {
									series.addOrUpdate(new Second(date), null);
								} else {
									series.addOrUpdate(new Second(date),
											getStr2Double(singleSeriesValue));
								}
							}
						}
					}
					if (series != null) {
						dataset.addSeries(series);
					}
				}
			} else {
				if (SeriesName == null) {
					System.err.println("图表序列的名称为null!");
				} else if (SeriesValue == null) {
					System.err.println("图表数值序列为null!");
				} else if (SeriesName.size() != SeriesValue.size()) {
					System.err.println("图表序列的名称和图表数值序列个数不相等!");
				}
			}
		} catch (ParseException e) {
			System.err.println("日期格式不正确!");
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
		}
	}

//***********************************************************************************




     /**
      * 得到主标题
      *
      * @return
      */
     public String getMainTitle() {
         return MainTitle;
     }

    /**
     * 设置主标题
     *
     * @param mainTitle
     */
    public void setMainTitle(String mainTitle) {
        MainTitle = mainTitle;
    }

    /**
     * 得到副标题
     *
     * @return
     */
    public String getSecondTitle() {
        return SecondTitle;
    }

    /**
     * 设置副标题
     *
     * @param secondTitle
     */
    public void setSecondTitle(String secondTitle) {
        SecondTitle = secondTitle;
    }

    /**
     * 得到副标题字体
     *
     * @return
     */
    public Font getSubTitleFont() {
        return subTitleFont;
    }

    /**
     * 设置副标题字体
     *
     * @param subTitleFont
     */
    public void setSubTitleFont(Font subTitleFont) {
        this.subTitleFont = subTitleFont;
    }

    /**
     * 得到小标题
     *
     * @return
     */
    public String getThirdTitle() {
        return ThirdTitle;
    }

    /**
     * 设置小标题
     *
     * @param thirdTitle
     */
    public void setThirdTitle(String thirdTitle) {
        ThirdTitle = thirdTitle;
    }

    /**
     * 得到X轴单位名称
     *
     * @return
     */
    public String getXCordUnitName() {
        return XCordUnitName;
    }

    /**
     * 设置X轴单位名称
     *
     * @param cordUnitName
     */
    public void setXCordUnitName(String cordUnitName) {
        XCordUnitName = cordUnitName;
    }

    /**
     * 得到X轴数值序列
     *
     * @return
     */
    public ArrayList getXCordValue() {
        return XCordValue;
    }

    /**
     * 设置X轴数值序列
     *
     * @param cordValue
     */
    public void setXCordValue(ArrayList cordValue) {
        XCordValue = cordValue;
    }

    /**
     * 得到Y轴单位名称
     *
     * @return
     */
    public String getYCordUnitName() {
        return YCordUnitName;
    }

    /**
     * 设置Y轴单位名称
     *
     * @param cordUnitName
     */
    public void setYCordUnitName(String cordUnitName) {
        YCordUnitName = cordUnitName;
    }


    /**
     * 得到柱状图数值间隔是否为整形
     *
     * @return
     */
    public boolean isYaxisInt() {
        return isYaxisInt;
    }

    /**
     * 设置柱状图数值间隔是否为整形
     *
     * @param isYaxisInt
     */
    public void setYaxisInt(boolean isYaxisInt) {
        this.isYaxisInt = isYaxisInt;
    }


    /**
     * 从字符串中解析double
     *
     * @param str
     * @return 如果有异常字符串解析为0
     */
    private double getStr2Double(String str) {
        double dou = 0;
        try {
            if (str != null || (str != null && str.trim().length() > 0)) {
                str = str.replaceAll(",", "");
                dou = Double.parseDouble(str);
            }
        } catch (Exception e) {
            System.err.println("不能将非数值的字符串转换成数值!");
        }
        return dou;
    }

    /**
     * 判断字符串是否为空:null,空字符串和空格的字符串都为真
     *
     * @param str
     * @return
     */
    private boolean isNull(String str) {
        boolean isnull = true;
        if (str == null || (str != null && str.trim().length() < 1)) {
            isnull = true;

        } else {
            isnull = false;
        }
        return isnull;
    }

    /**
     * 判断传入的三个list是否为空 以及图形序列名称list的大小是否等于图形序列数值list的大小
     *
     * @param seriesname
     * @param xcordvalue
     * @param seriesvalue
     * @return
     */
    private boolean listIsNull(ArrayList seriesname, ArrayList xcordvalue,
                               ArrayList seriesvalue) {
        boolean listisnull = true;
        if (seriesname != null && xcordvalue != null) {
            listisnull = true;
        } else {
            listisnull = false;
        }
        return listisnull;
    }


    public String getOthYUnitName() {
        return OthYUnitName;
    }

    public void setOthYUnitName(String othYUnitName) {
        OthYUnitName = othYUnitName;
    }

    public double getYMaxValue() {
        return yMaxValue;
    }

    public void setYMaxValue(double maxValue) {
        yMaxValue = maxValue;
    }

	public double getYMixValue() {
		return yMixValue;
	}

	public void setYMixValue(double mixValue) {
		yMixValue = mixValue;
	}

	public void setDateType(String dateType) {
		DateType = dateType;
	}


}
