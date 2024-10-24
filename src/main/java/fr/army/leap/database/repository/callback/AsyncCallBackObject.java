package fr.army.leap.database.repository.callback;

public interface AsyncCallBackObject<T> {

  void done(T result);
}
