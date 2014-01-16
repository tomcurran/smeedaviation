package com.smeedaviation.quadcopter.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
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
import com.smeedaviation.quadcopter.model.GyrationModel;

public class ComplementaryFilterGraph extends ApplicationFrame implements Observer {

	private static final long serialVersionUID = -1727948182617521282L;

	private AccelerationModel acc;
	private GyrationModel gyro;

	private long lastSample;

	private TimeSeries seriesRoll;
	private TimeSeries seriesPitch;

	@SuppressWarnings("deprecation")
	public ComplementaryFilterGraph(final String title, AccelerationModel accelModel, GyrationModel gyroModel) {

		super(title);
		seriesRoll = new TimeSeries("Roll", Millisecond.class);
		seriesPitch = new TimeSeries("Pitch", Millisecond.class);
		final TimeSeriesCollection dataset = new TimeSeriesCollection();
		dataset.addSeries(seriesRoll);
		dataset.addSeries(seriesPitch);
		final JFreeChart chart = createChart(dataset);

		final ChartPanel chartPanel = new ChartPanel(chart);

		final JPanel content = new JPanel(new BorderLayout());
		content.add(chartPanel);
		chartPanel.setPreferredSize(new Dimension(500, 270));
		setContentPane(content);

		this.acc = accelModel;
		this.gyro = gyroModel;
		
		this.pack();
		RefineryUtilities.centerFrameOnScreen(this);
		this.setVisible(true);
	}

	private JFreeChart createChart(final XYDataset dataset) {
		final JFreeChart result = ChartFactory.createTimeSeriesChart(
				"Orientation",
				"Time",
				"Value",
				dataset,
				true,
				true,
				false
		);
		final XYPlot plot = result.getXYPlot();
		ValueAxis axis = plot.getDomainAxis();
//		axis.setAutoRange(true);
		axis.setFixedAutoRange(60000.0); // 60 seconds
		axis = plot.getRangeAxis();
		axis.setRange(-180, 180);
		return result;
	}

	@Override
	public void update(Observable o, Object arg) {
		if (System.currentTimeMillis() - lastSample >= 10) {
			lastSample = System.currentTimeMillis();
			int[] accData = acc.getData();
			int[] gyroData = gyro.getData();
			complementaryFilter(accData[0], accData[1], accData[2],
					gyroData[0], gyroData[1], gyroData[2]);
			seriesRoll.addOrUpdate(new Millisecond(), roll);
			seriesPitch.addOrUpdate(new Millisecond(), pitch);
		}
	}

	@SuppressWarnings("unused")
	private static final double ACCELEROMETER_SENSITIVITY = 8192.0;
	private static final double GYROSCOPE_SENSITIVITY = 65.536;

	private static final double dt = 0.01; // 10 ms sample rate!

	private double pitch = 0.0;
	private double roll = 0.0;

	private void complementaryFilter(int accX, int accY, int accZ, int gyrX, int gyrY, int gryZ) {
		double pitchAcc, rollAcc;
		// Integrate the gyroscope data -> int(angularSpeed) = angle
		pitch += (gyrX / GYROSCOPE_SENSITIVITY) * dt; // Angle around the X-axis
		roll -= (gyrY / GYROSCOPE_SENSITIVITY) * dt; // Angle around the Y-axis

		// Compensate for drift with accelerometer data if !bullshit
		// Sensitivity = -2 to 2 G at 16Bit -> 2G = 32768 && 0.5G = 8192
		int forceMagnitudeApprox = Math.abs(accX) + Math.abs(accY) + Math.abs(accZ);
		if (forceMagnitudeApprox > 8192 && forceMagnitudeApprox < 32768) {
			// Turning around the X axis results in a vector on the Y-axis
			pitchAcc = Math.atan2(accY, accZ) * 180 / Math.PI;
			pitch = pitch * 0.98 + pitchAcc * 0.02;

			// Turning around the Y axis results in a vector on the X-axis
			rollAcc = Math.atan2(accX, accZ) * 180 / Math.PI;
			roll = roll * 0.98 + rollAcc * 0.02;
		}
	}

}
