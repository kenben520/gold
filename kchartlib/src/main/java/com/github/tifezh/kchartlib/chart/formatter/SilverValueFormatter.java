package com.github.tifezh.kchartlib.chart.formatter;

import com.github.tifezh.kchartlib.chart.base.IValueFormatter;

/**
 * Value格式化类
 * Created by tifezh on 2016/6/21.
 */

public class SilverValueFormatter implements IValueFormatter {
    
    @Override
    public String format(float value) {
        return String.format("%.3f", value);
    }
}
