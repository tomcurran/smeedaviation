package com.smeedaviation.quadcopter.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import com.smeedaviation.quadcopter.model.AccelerationModel;

public class AccelerationGraph extends ApplicationFrame implements Observer {

	private static final long serialVersionUID = -1727948182617521282L;

	private TimeSeries seriesX;
	private TimeSeries seriesY;
	private TimeSeries seriesZ;

	@SuppressWarnings("deprecation")
	public AccelerationGraph(final String title) {

		super(title);
		seriesX = new TimeSeries("Orientation X", Millisecond.class);
		seriesY = new TimeSeries("Orientation Y", Millisecond.class);
		seriesZ = new TimeSeries("Orientation Z", Millisecond.class);
		final TimeSeriesCollection dataset = new TimeSeriesCollection();
		dataset.addSeries(seriesX);
		dataset.addSeries(seriesY);
		dataset.addSeries(seriesZ);
		final JFreeChart chart = createChart(dataset);

		final ChartPanel chartPanel = new ChartPanel(chart);

		final JPanel content = new JPanel(new BorderLayout());
		content.add(chartPanel);
		chartPanel.setPreferredSize(new Dimension(500, 270));
		setContentPane(content);
		
		this.pack();
		RefineryUtilities.centerFrameOnScreen(this);
		this.setVisible(true);
		this.setLocation(new Point(90,40));
		System.out.println(this.getSize().height);
		System.out.println(this.getSize().width);
	}

	private JFreeChart createChart(final XYDataset dataset) {
		final JFreeChart result = ChartFactory.createTimeSeriesChart(
				"Acceleration",
				"Time",
				"Value",
				dataset,
				true,
				true,
				false
		);
		final XYPlot plot = result.getXYPlot();
		ValueAxis axis = plot.getDomainAxis();
		axis.setAutoRange(true);
		axis.setFixedAutoRange(60000.0); // 60 seconds
		axis = plot.getRangeAxis();
		axis.setRange(-32768, 32768);
		return result;
	}

	@Override
	public void update(Observable o, Object arg) {
		AccelerationModel stateChange = (AccelerationModel) o;
		int[] sensorData = stateChange.getData();
		seriesX.addOrUpdate(new Millisecond(), sensorData[0]);
		seriesY.addOrUpdate(new Millisecond(), sensorData[1]);
		seriesZ.addOrUpdate(new Millisecond(), sensorData[2]);
	}

}
