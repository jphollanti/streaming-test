package jphollanti.streaming.test;

import com.google.common.base.Objects;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class StreamData {
    private String id;
    private float value;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public float getValue() {
        return value;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this.getClass())
                .add("id", id)
                .add("value", value)
                .toString();
    }
}
