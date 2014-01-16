package com.smeedaviation.quadcopter.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import com.smeedaviation.quadcopter.model.MotorModel;

public class MotorGraph extends ApplicationFrame implements Observer {

	private static final long serialVersionUID = -1727948182617521282L;

	private DefaultCategoryDataset dataset;

	public MotorGraph(final String title) {
		super(title);
		this.dataset = createDataset();
		JPanel chartPanel = createDemoPanel(this.dataset);
		chartPanel.setPreferredSize(new Dimension(500, 270));
		setContentPane(chartPanel);
		
		this.pack();
		RefineryUtilities.centerFrameOnScreen(this);
		this.setVisible(true);
	}

	public static JPanel createDemoPanel(DefaultCategoryDataset dataset) {
		JFreeChart chart = createChart(dataset);
		return new ChartPanel(chart);
	}

	private static DefaultCategoryDataset createDataset() {

		// row keys...
		// String series1 = "Front Left";
		// String series2 = "Front Right";
		// String series3 = "Back Left";
		// String series4 = "Back Right";

		String series = "Motor Power";

		// column keys...
		String category1 = "Front Left";
		String category2 = "Front Right";
		String category3 = "Back Left";
		String category4 = "Back Right";

		// create the dataset...
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		dataset.addValue(0.0, series, category1);
		dataset.addValue(0.0, series, category2);
		dataset.addValue(0.0, series, category3);
		dataset.addValue(0.0, series, category4);

		return dataset;
	}

	private static JFreeChart createChart(CategoryDataset dataset) {

		// create the chart...
		JFreeChart chart = ChartFactory.createBarChart(
				"Motor power", // chart title
				"Category", // domain axis label
				"Value", // range axis label
				dataset, // data
				PlotOrientation.VERTICAL, // orientation
				true, // include legend
				true, // tooltips?
				false // URLs?
		);

		// NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...

		// set the background color for the chart...
		chart.setBackgroundPaint(Color.white);

		// get a reference to the plot for further customisation...
		CategoryPlot plot = (CategoryPlot) chart.getPlot();
		plot.setBackgroundPaint(Color.lightGray);
		plot.setDomainGridlinePaint(Color.white);
		plot.setDomainGridlinesVisible(true);
		plot.setRangeGridlinePaint(Color.white);

		// set the range axis to display integers only...
		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

		// disable bar outlines...
		BarRenderer renderer = (BarRenderer) plot.getRenderer();
		renderer.setDrawBarOutline(false);

		// set up gradient paints for series...
		GradientPaint gp0 = new GradientPaint(0.0f, 0.0f, Color.blue, 0.0f, 0.0f, new Color(0, 0, 64));
		GradientPaint gp1 = new GradientPaint(0.0f, 0.0f, Color.green, 0.0f, 0.0f, new Color(0, 64, 0));
		GradientPaint gp2 = new GradientPaint(0.0f, 0.0f, Color.red, 0.0f, 0.0f, new Color(64, 0, 0));
		renderer.setSeriesPaint(0, gp0);
		renderer.setSeriesPaint(1, gp1);
		renderer.setSeriesPaint(2, gp2);

		CategoryAxis domainAxis = plot.getDomainAxis();
		domainAxis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0));
		plot.getRangeAxis().setRange(0, 255);
		// OPTIONAL CUSTOMISATION COMPLETED.

		return chart;
	}

	@Override
	public void update(Observable o, Object arg) {
		MotorModel stateChange = (MotorModel) o;
		int[] motorData = stateChange.getData();
		for (int i = 0; i < motorData.length; i++) {
			this.dataset.setValue(motorData[i], this.dataset.getRowKey(0), this.dataset.getColumnKey(i));
		}
	}

}
