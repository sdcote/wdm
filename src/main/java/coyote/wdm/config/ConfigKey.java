package coyote.wdm.config;

public class ConfigKey<T> {

  String name;
  Class<T> type;
  T value;
  T defaultValue;

  public ConfigKey(Class<T> type) {
    this.type = type;
  }

  public ConfigKey(String name, Class<T> type) {
    this.name = name;
    this.type = type;
  }

  public ConfigKey(String name, Class<T> type, T value) {
    this.name = name;
    this.type = type;
    this.value = value;
    this.defaultValue = value;
  }

  public String getName() {
    return name;
  }

  public Class<T> getType() {
    return type;
  }

  public T getValue() {
    return value;
  }

  public void reset() {
    value = defaultValue;
  }

  @SuppressWarnings("unchecked")
  public void setValue(Object value) {
    this.value = (T) value;
  }

}
