package jphollanti.streaming.test;

import com.google.common.base.Objects;

public class StreamHeader {
    private String valueLabel;

    public String getValueLabel() {
        return valueLabel;
    }

    public void setValueLabel(String valueLabel) {
        this.valueLabel = valueLabel;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this.getClass())
                .add("valueLabel", valueLabel)
                .toString();
    }
}
